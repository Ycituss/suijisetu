package com.ycitus.command.commands;

import com.ycitus.PluginMain;
import com.ycitus.command.RobotCommand;
import com.ycitus.command.RobotCommandChatType;
import com.ycitus.command.RobotCommandUser;
import com.ycitus.files.FileManager;
import com.ycitus.framework.MessageManager;
import com.ycitus.utils.HttpGet;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.utils.ExternalResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class RandomImagesCommand extends RobotCommand {

    public RandomImagesCommand(String rule) {
        super(rule);
//        getRange().add(RobotCommandChatType.FRIEND_CHAT);
//        getRange().add(RobotCommandChatType.GROUP_TEMP_CHAT);
        getRange().add(RobotCommandChatType.GROUP_CHAT);
//        getRange().add(RobotCommandChatType.STRANGER_CHAT);

        getUser().add(RobotCommandUser.NORMAL_USER);
        getUser().add(RobotCommandUser.GROUP_ADMINISTRATOR);
        getUser().add(RobotCommandUser.GROUP_OWNER);
        getUser().add(RobotCommandUser.BOT_ADMINISTRATOR);
    }

    @Override
    public void runCommand(int msgType, int time, long fromGroup, long fromQQ, MessageChain messageChain) {

        String msg = messageChain.contentToString();
        String[] strings = msg.split(" ");

        if (strings.length >= 2 && (strings[1].equals("setu") || strings[1].equals("色图"))){
            lolicon(fromGroup, fromQQ, strings);
        }else if (strings.length >= 2 && (strings[1].equals("风景") || strings[1].equals("风景图"))){
            sendImage(fromGroup, "jpg", "风景", "https://api.vvhan.com/api/view");
        }else if (strings.length >= 2 && (strings[1].equals("真人") || strings[1].equals("三次元"))){
            sendImage(fromGroup, "png", "三次元", "https://api.vvhan.com/api/mobil.girl");
        }else if (strings.length >= 2 && (strings[1].equals("lol") || strings[1].equals("英雄联盟"))){
            sendImage(fromGroup, "png", "lol", "https://api.vvhan.com/api/lolskin");
        }else {
            sendImage(fromGroup, "png", "三次元", "https://api.vvhan.com/api/girl");
        }
    }

    /*
    风景图,真人,lol图片(韩小韩api https://api.vvhan.com/)
     */
    private static void sendImage(long fromGroup, String ext, String name, String url){
        String uid = Long.toString(System.currentTimeMillis()) + name;
        try {
            FileInputStream is = new FileInputStream(httpRequest(url, uid, ext, name));
            Image uploadImage = ExternalResource.uploadAsImage(is,
                    PluginMain.getCurrentBot().getGroups().stream().findAny().get());
            MessageManager.sendMessageToQQGroup(fromGroup, "[mirai:image:" + uploadImage.getImageId() + "]");
        }catch (Exception e){
            e.printStackTrace();
            MessageManager.sendMessageToQQGroup(fromGroup, "获取图片失败");
        }
    }

    /*
    lolicon二次元色图
     */
    private static void lolicon(long fromGroup, long fromQQ, String[] strings){
        HttpGet httpGet = new HttpGet();
        String loliconUrl = "https://api.lolicon.app/setu/v2?";
        if (FileManager.applicationConfig_File.getSpecificDataInstance().RandomImages.setu){
            String str = new String();
            if (strings.length == 4){
                if (strings[2].equals("r18") || strings[2].equals("R18")){
                    if (FileManager.applicationConfig_File.getSpecificDataInstance().RandomImages.r18){
                        switch (strings[3]){
                            case "0": str = httpGet.doGet(loliconUrl + "&size=mini" + "&r18=1");break;
                            case "1": str = httpGet.doGet(loliconUrl + "&size=thumb" + "&r18=1");break;
                            case "2": str = httpGet.doGet(loliconUrl + "&size=small" + "&r18=1");break;
                            case "3": str = httpGet.doGet(loliconUrl + "&size=regular" + "&r18=1");break;
                            case "4": str = httpGet.doGet(loliconUrl + "&size=original" + "&r18=1");break;
                            default: str = "error:无效的参数";
                        }
                    }else {
                        MessageManager.sendMessageToQQGroup(fromGroup, "当前未开启此权限");
                    }
                }else if (strings[3].equals("r18") || strings[3].equals("R18")){
                    if (FileManager.applicationConfig_File.getSpecificDataInstance().RandomImages.r18){
                        switch (strings[2]){
                            case "0": str = httpGet.doGet(loliconUrl + "&size=mini" + "&r18=1");break;
                            case "1": str = httpGet.doGet(loliconUrl + "&size=thumb" + "&r18=1");break;
                            case "2": str = httpGet.doGet(loliconUrl + "&size=small" + "&r18=1");break;
                            case "3": str = httpGet.doGet(loliconUrl + "&size=regular" + "&r18=1");break;
                            case "4": str = httpGet.doGet(loliconUrl + "&size=original" + "&r18=1");break;
                            default: str = "error:无效的参数";
                        }
                    }else {
                        MessageManager.sendMessageToQQGroup(fromGroup, "当前未开启此权限");
                    }
                }
                loliconSend(str, fromGroup);
                return;
            }
            if (strings.length == 3){
                switch (strings[2]){
                    case "0": str = httpGet.doGet(loliconUrl + "&size=mini");break;
                    case "1": str = httpGet.doGet(loliconUrl + "&size=thumb");break;
                    case "2": str = httpGet.doGet(loliconUrl + "&size=small");break;
                    case "3": str = httpGet.doGet(loliconUrl + "&size=regular");break;
                    case "4": str = httpGet.doGet(loliconUrl + "&size=original");break;
                    case "r18":{
                        if (FileManager.applicationConfig_File.getSpecificDataInstance().RandomImages.r18)
                            str = httpGet.doGet(loliconUrl + "&size=small" + "&r18=1");
                        else str = "当前未开启此权限";
                        break;
                    }
                    default: str = "error:无效的参数";
                }
                loliconSend(str, fromGroup);
                return;
            }
            if (strings.length > 4) MessageManager.sendMessageBySituation(fromGroup, fromQQ, "无效的参数");
            str = httpGet.doGet(loliconUrl + "&size=small");
            loliconSend(str, fromGroup);
        }else {
            MessageManager.sendMessageToQQGroup(fromGroup, "随机色图未开启，请联系管理员");
        }
    }

    /*
    lolicon专用图片发送
     */
    private static void loliconSend(String str, long fromGroup){
        Map<String, String> data = StrToMap(str);
        if (!data.get("error").trim().isEmpty()){
            MessageManager.sendMessageToQQGroup(fromGroup, data.get("error"));
        }else {
            try {
                FileInputStream is = new FileInputStream(httpRequest(data.get("urls"),
                        data.get("uid"), data.get("ext"), "setu"));
                Image uploadImage = ExternalResource.uploadAsImage(is,
                        PluginMain.getCurrentBot().getGroups().stream().findAny().get());
                String sendMessage =  "[mirai:image:" + uploadImage.getImageId() + "]";
                MessageManager.sendMessageToQQGroup(fromGroup, sendMessage);
            } catch (Exception e) {
                MessageManager.sendMessageToQQGroup(fromGroup, "图片发送错误");
            }
        }
    }

    /*
    将lolicon返回的json格式化为map类型
     */
    private static Map<String, String> StrToMap(String str){
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
    private static String httpRequest(String uri, String uid, String ext, String name) throws Exception {
        String path = "./data/Image/" + name + "/";
        String filePath = path + uid + "." + ext;
        long startTime = System.currentTimeMillis();
        URL url = new URL(uri);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("referer", ""); //这是破解防盗链添加的参数
        conn.addRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36 Edg/91.0.864.67");
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5 * 1000);
        InputStream inStream = conn.getInputStream();//通过输入流获取图片数据
        readInputStream(inStream, filePath, path);
        return filePath;
    }

    /*
    lolicon保存图片
     */
    private static void readInputStream(InputStream inStream, String filePath, String path) throws Exception{
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
