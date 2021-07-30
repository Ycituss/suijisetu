package org.example;

import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.Listener;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.utils.ExternalResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public final class Plugin extends JavaPlugin {
    public static final Plugin INSTANCE = new Plugin();

    private Plugin() {
        super(new JvmPluginDescriptionBuilder("org.example.plugin", "1.0.0")
                .name("suijisetu")
                .author("Ycitus")
                .build());
    }

    @Override
    public void onEnable() {
        getLogger().info("Plugin loaded!");
        Listener listener = GlobalEventChannel.INSTANCE.subscribeAlways(MessageEvent.class, g -> {
            RunSearch(g.getMessage().contentToString(), g);
        });
    }

    /*
    根据输入的指令发送图片
     */
    private void RunSearch(String input, MessageEvent a){
        HttpGet httpGet = new HttpGet();
        String url = new String("https://api.lolicon.app/setu/v2?");
        if (input.startsWith("++")){
            String str = httpGet.doGet(url + input.substring(2));
            MySendMessage(str, a);
        }
        if (input.equals("搞张色图") || input.equals("来张色图") || input.equals("搞快点") || input.equals("gkd")){
//            a.getSubject().sendMessage("马上来！");
            String str = httpGet.doGet(url + "&size=small");
            MySendMessage(str, a);
        }
        if (input.equals("来张高清色图") || input.equals("gkdss")){
            String str = httpGet.doGet(url + "&size=original");
            MySendMessage(str, a);
        }
        if (input.equals("来张清点的色图") || input.equals("gkds")){
            String str = httpGet.doGet(url + "&size=regular");
            MySendMessage(str, a);
        }
        if (input.equals("r18图片")){
            String str = httpGet.doGet(url + "&size=small" + "&r18=1");
            MySendMessage(str, a);
        }
        if (input.equals("r18图片s")){
            String str = httpGet.doGet(url + "&size=regular" + "&r18=1");
            MySendMessage(str, a);
        }
        if (input.equals("r18图片ss")){
            String str = httpGet.doGet(url + "&size=original" + "&r18=1");
            MySendMessage(str, a);
        }
        if (input.equals("r18") || input.equals("R18")){
            Map<String, String> data = new HashMap<>();
            String str = httpGet.doGet(url + "&size=small" + "&r18=1");
            data = StrToMap(str);
            if (!data.get("error").trim().isEmpty()){
                a.getSubject().sendMessage(data.get("error"));
            }else {
                a.getSubject().sendMessage(data.get("urls"));
                try {
                    httpRequest(data.get("urls"), "r" + data.get("uid"), data.get("ext"));
                } catch (Exception e) {
                    a.getSubject().sendMessage("图片发送错误\n" + "\n" + e);
                }
            }
        }
        if (input.equals("色图指令")){
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("搞张色图").append("\n");
            stringBuffer.append("来张色图").append("\n");
            stringBuffer.append("搞快点").append("\n");
            stringBuffer.append("gkd");
//            stringBuffer.append("输入“色图进阶指令”查看更多指令");
            a.getSubject().sendMessage(stringBuffer.toString());
        }
        if (input.equals("色图进阶指令")){
            if (input.contains("num")){
                a.getSubject().sendMessage("num参数禁用!");
            }else {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("易报错，请小心使用").append("\n");
                stringBuffer.append("指令已“++”为开头识别符，“&”为分隔符").append("\n");
                stringBuffer.append("详细参数用法请参照https://api.lolicon.app/#/setu?id=%e8%af%b7%e6%b1%82").append("\n");
                stringBuffer.append("(num参数禁用)").append("\n");
                stringBuffer.append("示例: ++r18=2&tag=少女&tag=黑丝&size=small");
                a.getSubject().sendMessage(stringBuffer.toString());
            }
        }
    }

    /*
    发送图片
     */
    static public void MySendMessage (String str, MessageEvent a){
        Map<String, String> data;
        data = StrToMap(str);
        if (!data.get("error").trim().isEmpty()){
            a.getSubject().sendMessage(data.get("error"));
        }else {
//                StringBuffer sb = new StringBuffer();
//                sb.append("作品标题：").append(data.get("title")).append("\n");
//                sb.append("作品pid：").append(data.get("pid")).append("\n");
//                sb.append("作者名：").append(data.get("author")).append("\n");
//                sb.append("作者uid：").append(data.get("uid")).append("\n");
//                sb.append("是否R18：").append(data.get("r18")).append("\n");
//                sb.append("tags：").append(data.get("tags")).append("\n");
//                a.getSubject().sendMessage(sb.toString());
            try{
                FileInputStream is = new FileInputStream(httpRequest(data.get("urls"), data.get("uid"), data.get("ext")));
                ExternalResource.sendAsImage(is, a.getSubject());
                is.close();
            }catch (Exception e){
                a.getSubject().sendMessage("图片发送错误\n" + "\n" + e);
            }
        }
    }

    /*
    将api返回的json格式化为map类型
     */
    static public Map<String, String> StrToMap(String str){
        String[] array = str.split(",");
        String[] strings = new String[100];
        int index = 0;
        for (String i : array){
            String[] tmp = i.split(":");
            for (String j : tmp){
                String replaceStr = j.replace("\"", " ")
                        .replace("{", "")
                        .replace("[", "")
                        .replace("}", "")
                        .replace("]", "");
                if(index != 1) strings[index++] = replaceStr.replace(" ", "");
                else strings[index++] = replaceStr;
            }
        }
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < 100; i++){
            if(strings[i] == null) break;
            if (strings[i].equals("error") || strings[i].equals("pid") || strings[i].equals("uid") ||
                    strings[i].equals("title") || strings[i].equals("author") || strings[i].equals("r18")){
                map.put(strings[i], strings[i+1]);
            }
            if (strings[i].equals("tags")){
                StringBuffer stringBuffer = new StringBuffer();
                for (int j = i + 1; j < i + 23; j++){
                    stringBuffer.append(strings[j]).append(",");
                    if(strings[j+1].equals("ext") || strings[j+1].equals("width")) break;
                }
                map.put(strings[i], stringBuffer.toString());
            }
            if (strings[i].equals("urls")){
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(strings[i+2]).append(":").append(strings[i+3]);
                map.put(strings[i], stringBuffer.toString());
            }
            if (strings[i].equals("ext")){
                map.put(strings[i], strings[i+1]);
            }
        }
        return map;
    }

    /*
    对图片链设置referer绕过防盗链
     */
    public static String httpRequest(String uri, String uid, String ext) throws Exception {
        String path = "./data/suijisetu/Image/";
        String filePath = path + uid + "." + ext;
        long startTime = System.currentTimeMillis();
        URL url = new URL(uri);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("referer", ""); //这是破解防盗链添加的参数
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5 * 1000);
        InputStream inStream = conn.getInputStream();//通过输入流获取图片数据
        readInputStream(inStream, filePath, path);
        return filePath;
    }

    /*
    保存图片
     */
    public static void readInputStream(InputStream inStream, String filePath, String path) throws Exception{
        File file = new File(path);
        File file1 = new File(filePath);
        if (!file.exists()) file.mkdirs();
        if (!file1.exists()){
            FileOutputStream fos = new FileOutputStream(new File(filePath));
            byte[] buffer = new byte[102400];
            int len = 0;
            while( (len=inStream.read(buffer)) != -1 ){
                fos.write(buffer, 0, len);
            }
            inStream.close();
            fos.flush();
            fos.close();
        }else {
            inStream.close();
        }
    }
}
