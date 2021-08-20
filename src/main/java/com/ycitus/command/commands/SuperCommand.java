package com.ycitus.command.commands;

import com.ycitus.PluginMain;
import com.ycitus.command.RobotCommand;
import com.ycitus.command.RobotCommandChatType;
import com.ycitus.command.RobotCommandManager;
import com.ycitus.command.RobotCommandUser;
import com.ycitus.files.FileManager;
import com.ycitus.framework.MessageManager;
import net.mamoe.mirai.message.data.MessageChain;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class SuperCommand extends RobotCommand {

    public SuperCommand(String rule) {
        super(rule);
        getRange().add(RobotCommandChatType.FRIEND_CHAT);
        getRange().add(RobotCommandChatType.GROUP_TEMP_CHAT);
        getRange().add(RobotCommandChatType.GROUP_CHAT);
        getRange().add(RobotCommandChatType.STRANGER_CHAT);

        getUser().add(RobotCommandUser.BOT_ADMINISTRATOR);
    }

    @Override
    public void runCommand(int msgType, int time, long fromGroup, long fromQQ, MessageChain messageChain) {

        String msg = messageChain.contentToString();
        String[] strings = msg.split(" ");
        ArrayList<String> BasicCommands = new ArrayList<String>(){
            {
                this.add("#重载配置");
                this.add("seturebot");
                this.add("sendtogroup");
                this.add("SendtoGroup");
                this.add("stg");
                this.add("STG");
                this.add("gl");
                this.add("GL");
                this.add("grouplist");
                this.add("GroupList");
                this.add("群列表");
                this.add("super");
                this.add("权限");
            }
        };
        String help = "\n使用\"super list\"查看拥有super权限的成员\n\n"
                + "使用以下格式添加或删除成员权限：\n"
                + "\"super add\\\\del QQ号码\"\n"
                + "示例：\n"
                + "\"super add 2799282971\"\n\n"
                + "更多帮助请查看http://www.ycitus.cn/other/index.html";

        if (strings.length >= 2 && strings[1].equals("list")){
            ArrayList<Long> qqs = FileManager.applicationConfig_File
                    .getSpecificDataInstance().Admin.botAdministrators;
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("所有拥有super权限的用户:\n");
            for (Long qq : qqs){
                stringBuffer.append(qq.toString()).append("\n");
            }
            MessageManager.sendMessageBySituation(fromGroup, fromQQ, stringBuffer.toString());
            return;
        }

        if (strings.length >= 3 && Pattern.matches("\\d+", strings[2])){
            ArrayList<Long> qqs = FileManager.applicationConfig_File
                    .getSpecificDataInstance().Admin.botAdministrators;
            if (strings[1].equals("add")){
                if (strings[2].length() > 11 || strings[2].length() < 5){
                    MessageManager.sendMessageBySituation(fromGroup, fromQQ, "无效的qq号");
                }else if (qqs.contains(Long.parseLong(strings[2]))){
                    MessageManager.sendMessageBySituation(fromGroup, fromQQ
                            , "此成员已拥有super权限");
                }else {
                    FileManager.applicationConfig_File.getSpecificDataInstance()
                            .Admin.botAdministrators.add(Long.parseLong(strings[2]));
                    FileManager.applicationConfig_File.saveFile();
                    FileManager.applicationConfig_File.reloadFile();
                    MessageManager.sendMessageBySituation(fromGroup, fromQQ, "添加成功");
                }
            }else if (strings[1].equals("del")){
                if (strings[2].length() > 11 || strings[2].length() < 5){
                    MessageManager.sendMessageBySituation(fromGroup, fromQQ, "无效的qq号");
                }else if (qqs.contains(Long.parseLong(strings[2]))){
                    if (qqs.size() == 1){
                        MessageManager.sendMessageBySituation(fromGroup, fromQQ
                                , "需至少保留一位拥有super权限的用户");
                    }else {
                        FileManager.applicationConfig_File.getSpecificDataInstance()
                                .Admin.botAdministrators.remove(Long.parseLong(strings[2]));
                        FileManager.applicationConfig_File.saveFile();
                        FileManager.applicationConfig_File.reloadFile();
                        MessageManager.sendMessageBySituation(fromGroup, fromQQ, "删除成功");
                    }
                }else {
                    MessageManager.sendMessageBySituation(fromGroup, fromQQ
                            , "输入的qq号不在super权限列表中");
                }
            }else {
                MessageManager.sendMessageBySituation(fromGroup, fromQQ, help);
            }
            return;
        }

        if (strings.length >= 3 && strings[1].equals("set")){
            if (strings.length >= 4 && strings[2].equals("recallDelay")
                    && Pattern.matches("\\d+", strings[3])){
                if (Integer.parseInt(strings[3]) <= 150000 && Integer.parseInt(strings[3]) > 0){
                    FileManager.applicationConfig_File.getSpecificDataInstance()
                            .RandomImages.recallDelay = Integer.parseInt(strings[3]);
                    FileManager.applicationConfig_File.saveFile();
                    FileManager.applicationConfig_File.reloadFile();
                }else {
                    MessageManager.sendMessageBySituation(fromGroup, fromQQ, "输入的数值不合法");
                }
            }else if (strings.length >= 4 && strings[2].equals("imageQuality")){
                int imageQuality;
                String Quality = new String();
                switch (strings[3]){
                    case "0": imageQuality = 0; Quality = "mini";break;
                    case "1": imageQuality = 1; Quality = "thumb";break;
                    case "2": imageQuality = 2; Quality = "small";break;
                    case "3": imageQuality = 3; Quality = "regular";break;
                    case "4": imageQuality = 4; Quality = "original";break;
                    default: imageQuality = -1;
                }
                if (imageQuality == -1){
                    String helpString = "输入的参数不正确\n支持的参数:\"0,1,2,3,4\"";
                    MessageManager.sendMessageBySituation(fromGroup, fromQQ, helpString);
                }else {
                    FileManager.applicationConfig_File.getSpecificDataInstance()
                            .RandomImages.defaultSetuQuality = imageQuality;
                    FileManager.applicationConfig_File.saveFile();
                    FileManager.applicationConfig_File.reloadFile();
                    MessageManager.sendMessageBySituation(fromGroup, fromQQ, "setu默认画质已成功设置为" + Quality);
                }
            }else {
                MessageManager.sendMessageBySituation(fromGroup, fromQQ, help);
            }
            return;
        }

        if (strings.length >= 2 && strings[1].equals("open")){
            if (strings.length >= 3 && strings[2].equals("recall")){
                if (FileManager.applicationConfig_File.getSpecificDataInstance().RandomImages.recallEnable){
                    MessageManager.sendMessageBySituation(fromGroup, fromQQ, "setu自动撤回已打开，请勿重复操作");
                }else {
                    FileManager.applicationConfig_File.getSpecificDataInstance().RandomImages.recallEnable = true;
                    FileManager.applicationConfig_File.saveFile();
                    FileManager.applicationConfig_File.reloadFile();
                    MessageManager.sendMessageBySituation(fromGroup, fromQQ, "setu自动撤回打开成功");
                }
            }else if (strings.length >= 3 && strings[2].equals("setu")){
                if (strings.length >= 4 && strings[3].equals("all")){
                    FileManager.applicationConfig_File.getSpecificDataInstance().RandomImages.setuAll = true;
                    FileManager.applicationConfig_File.saveFile();
                    FileManager.applicationConfig_File.reloadFile();
                    MessageManager.sendMessageBySituation(fromGroup, fromQQ, "全局随机色图打开成功!");
                }else {
                    if (FileManager.applicationConfig_File.getSpecificDataInstance()
                            .RandomImages.groupSetu.contains(fromGroup)){
                        MessageManager.sendMessageBySituation(fromGroup
                                , fromQQ, "本群色图发送已打开，请勿重复操作!");
                    }else {
                        FileManager.applicationConfig_File.getSpecificDataInstance()
                                .RandomImages.groupSetu.add(fromGroup);
                        FileManager.applicationConfig_File.saveFile();
                        FileManager.applicationConfig_File.reloadFile();
                        MessageManager.sendMessageBySituation(fromGroup, fromQQ, "随机色图开启成功!");
                    }
                }
            }else if (strings.length >= 3 && (strings[2].equals("r18") || strings[2].equals("R18"))){
                if (strings.length >= 4 && strings[3].equals("all")){
                    FileManager.applicationConfig_File.getSpecificDataInstance().RandomImages.r18All = true;
                    FileManager.applicationConfig_File.saveFile();
                    FileManager.applicationConfig_File.reloadFile();
                    MessageManager.sendMessageBySituation(fromGroup, fromQQ, "全局r18色图打开成功!");
                }else {
                    if (FileManager.applicationConfig_File.getSpecificDataInstance()
                            .RandomImages.groupSetu.contains(fromGroup)){
                        if (FileManager.applicationConfig_File.getSpecificDataInstance()
                                .RandomImages.groupR18.contains(fromGroup)){
                            MessageManager.sendMessageBySituation(fromGroup
                                    , fromQQ, "本群r18色图发送已打开，请勿重复操作!");
                        }else {
                            FileManager.applicationConfig_File.getSpecificDataInstance()
                                    .RandomImages.groupR18.add(fromGroup);
                            FileManager.applicationConfig_File.saveFile();
                            FileManager.applicationConfig_File.reloadFile();
                            MessageManager.sendMessageBySituation(fromGroup, fromQQ, "r18色图开启成功!");
                        }
                    }else {
                        MessageManager.sendMessageBySituation(fromGroup, fromQQ, "请先开启本群随机色图!");
                    }
                }
            }else if (strings.length >= 3 && strings[2].equals("all")){
                FileManager.applicationConfig_File.getSpecificDataInstance().RandomImages.enableAll = true;
                FileManager.applicationConfig_File.saveFile();
                FileManager.applicationConfig_File.reloadFile();
                MessageManager.sendMessageBySituation(fromGroup, fromQQ, "随机图片发送已全局打开!");
            }else if (strings.length == 2){
                if (!FileManager.applicationConfig_File.getSpecificDataInstance()
                        .RandomImages.enableGroup.contains(fromGroup)){
                    FileManager.applicationConfig_File.getSpecificDataInstance().RandomImages.enableGroup.add(fromGroup);
                    FileManager.applicationConfig_File.saveFile();
                    FileManager.applicationConfig_File.reloadFile();
                    MessageManager.sendMessageBySituation(fromGroup, fromQQ, "本群随机图片发送打开成功!");
                }else {
                    MessageManager.sendMessageBySituation(fromGroup, fromQQ, "本群随机图片发送已打开,请勿重复操作!");
                }
            }else {
                MessageManager.sendMessageBySituation(fromGroup, fromQQ, help);
            }
            return;
        }

        if (strings.length >= 2 && strings[1].equals("close")){
            if (strings.length >= 3 && strings[2].equals("recall")){
                if (!FileManager.applicationConfig_File.getSpecificDataInstance().RandomImages.recallEnable){
                    MessageManager.sendMessageBySituation(fromGroup, fromQQ, "setu自动撤回已关闭，请勿重复操作");
                }else {
                    FileManager.applicationConfig_File.getSpecificDataInstance().RandomImages.recallEnable = false;
                    FileManager.applicationConfig_File.saveFile();
                    FileManager.applicationConfig_File.reloadFile();
                    MessageManager.sendMessageBySituation(fromGroup, fromQQ, "setu自动撤回关闭成功");
                }
            }else if (strings.length >= 3 && strings[2].equals("setu")){
                FileManager.applicationConfig_File.getSpecificDataInstance().RandomImages.setuAll = false;
                FileManager.applicationConfig_File.saveFile();
                FileManager.applicationConfig_File.reloadFile();
                if (strings.length >= 4 && strings[3].equals("all")){
                    FileManager.applicationConfig_File.getSpecificDataInstance()
                            .RandomImages.groupSetu = new ArrayList<>();
                    FileManager.applicationConfig_File.saveFile();
                    FileManager.applicationConfig_File.reloadFile();
                    MessageManager.sendMessageBySituation(fromGroup, fromQQ, "随机色图已全局关闭!");
                }else {
                    if (!FileManager.applicationConfig_File.getSpecificDataInstance()
                            .RandomImages.groupSetu.contains(fromGroup)){
                        MessageManager.sendMessageBySituation(fromGroup
                                , fromQQ, "本群色图发送已关闭，请勿重复操作!");
                    }else {
                        FileManager.applicationConfig_File.getSpecificDataInstance()
                                .RandomImages.groupSetu.remove(fromGroup);
                        if (FileManager.applicationConfig_File.getSpecificDataInstance()
                                .RandomImages.groupR18.contains(fromGroup)){
                            FileManager.applicationConfig_File.getSpecificDataInstance()
                                    .RandomImages.groupR18.remove(fromGroup);
                        }
                        FileManager.applicationConfig_File.saveFile();
                        FileManager.applicationConfig_File.reloadFile();
                        MessageManager.sendMessageBySituation(fromGroup, fromQQ, "随机色图关闭成功!");
                    }
                }
            }else if (strings.length >= 3 && (strings[2].equals("r18") || strings[2].equals("R18"))){
                FileManager.applicationConfig_File.getSpecificDataInstance().RandomImages.r18All = false;
                FileManager.applicationConfig_File.saveFile();
                FileManager.applicationConfig_File.reloadFile();
                if (strings.length >= 4 && strings[3].equals("all")){
                    FileManager.applicationConfig_File.getSpecificDataInstance()
                            .RandomImages.groupR18 = new ArrayList<>();
                    FileManager.applicationConfig_File.saveFile();
                    FileManager.applicationConfig_File.reloadFile();
                    MessageManager.sendMessageBySituation(fromGroup, fromQQ, "r18色图已全局关闭!");
                }else {
                    if (!FileManager.applicationConfig_File.getSpecificDataInstance()
                            .RandomImages.groupR18.contains(fromGroup)){
                        MessageManager.sendMessageBySituation(fromGroup
                                , fromQQ, "本群r18色图发送已关闭，请勿重复操作");
                    }else {
                        FileManager.applicationConfig_File.getSpecificDataInstance()
                                .RandomImages.groupR18.add(fromGroup);
                        FileManager.applicationConfig_File.saveFile();
                        FileManager.applicationConfig_File.reloadFile();
                        MessageManager.sendMessageBySituation(fromGroup, fromQQ, "r18色图关闭成功!");
                    }
                }
            }else if (strings.length >= 3 && strings[2].equals("all")){
                FileManager.applicationConfig_File.getSpecificDataInstance().RandomImages.enableAll = false;
                FileManager.applicationConfig_File.getSpecificDataInstance()
                        .RandomImages.enableGroup = new ArrayList<>();
                FileManager.applicationConfig_File.saveFile();
                FileManager.applicationConfig_File.reloadFile();
                MessageManager.sendMessageBySituation(fromGroup, fromQQ, "随机图片发送已全局关闭!");
            }else if (strings.length == 2){
                if (FileManager.applicationConfig_File.getSpecificDataInstance().RandomImages.enableAll
                        ||FileManager.applicationConfig_File.getSpecificDataInstance()
                            .RandomImages.enableGroup.contains(fromGroup)){
                    FileManager.applicationConfig_File.getSpecificDataInstance().RandomImages.enableAll = false;
                    if (FileManager.applicationConfig_File.getSpecificDataInstance()
                            .RandomImages.enableGroup.contains(fromGroup)) {
                        FileManager.applicationConfig_File.getSpecificDataInstance()
                                .RandomImages.enableGroup.remove(fromGroup);
                        if (FileManager.applicationConfig_File.getSpecificDataInstance()
                                .RandomImages.groupSetu.contains(fromGroup)) {
                            FileManager.applicationConfig_File.getSpecificDataInstance()
                                    .RandomImages.groupSetu.remove(fromGroup);
                        }
                        if (FileManager.applicationConfig_File.getSpecificDataInstance()
                                .RandomImages.groupR18.contains(fromGroup)) {
                            FileManager.applicationConfig_File.getSpecificDataInstance()
                                    .RandomImages.groupR18.remove(fromGroup);
                        }
                    }
                    FileManager.applicationConfig_File.saveFile();
                    FileManager.applicationConfig_File.reloadFile();
                    MessageManager.sendMessageBySituation(fromGroup, fromQQ, "本群随机图片发送关闭成功!");
                }else {
                    MessageManager.sendMessageBySituation(fromGroup, fromQQ, "本群随机图片发送已关闭,请问重复操作!");
                }
            }else {
                MessageManager.sendMessageBySituation(fromGroup, fromQQ, help);
            }
            return;
        }

        if (strings.length >= 3 && strings[1].equals("command")){
            ArrayList<String> setuCommands = FileManager.applicationConfig_File
                    .getSpecificDataInstance().RandomImages.setuCommands;
            if (strings[2].equals("list")){
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("当前召唤图片可用指令头:\n");
                for (String setuCommand : setuCommands){
                    stringBuffer.append(setuCommand).append("\n");
                }
                String list = stringBuffer.toString();
                list = list.substring(0, list.length() - 1);
                MessageManager.sendMessageBySituation(fromGroup, fromQQ, list);
            }else if (strings[2].equals("add")){
                if (BasicCommands.contains(strings[3])){
                    MessageManager.sendMessageBySituation(fromGroup, fromQQ, "插件保留指令头，不可用!");
                }else {
                    FileManager.applicationConfig_File.getSpecificDataInstance().RandomImages
                            .setuCommands.add(strings[3]);
                    FileManager.applicationConfig_File.saveFile();
                    FileManager.applicationConfig_File.reloadFile();
                    PluginMain.commandManager = new RobotCommandManager();
                    FileManager.applicationConfig_File.reloadFile();
                    MessageManager.sendMessageBySituation(fromGroup, fromQQ, "添加成功!");
                }
            }else if (strings[2].equals("del")){
                if (setuCommands.contains(strings[3])){
                    if (setuCommands.size() == 1){
                        MessageManager.sendMessageBySituation(fromGroup, fromQQ, "需至少保留一个可用的指令头");
                    }else {
                        FileManager.applicationConfig_File.getSpecificDataInstance()
                                .RandomImages.setuCommands.remove(strings[3]);
                        FileManager.applicationConfig_File.saveFile();
                        FileManager.applicationConfig_File.reloadFile();
                        PluginMain.commandManager = new RobotCommandManager();
                        FileManager.applicationConfig_File.reloadFile();
                        MessageManager.sendMessageBySituation(fromGroup, fromQQ, "删除成功!");

                    }
                }else {
                    MessageManager.sendMessageBySituation(fromGroup, fromQQ, "要删除的指令头不存在!");
                }
            }else {
                MessageManager.sendMessageBySituation(fromGroup, fromQQ, help);
            }
            return;
        }

        MessageManager.sendMessageBySituation(fromGroup, fromQQ, help);
    }

}
