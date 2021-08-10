package com.ycitus.command.commands;

import com.ycitus.command.RobotCommand;
import com.ycitus.command.RobotCommandChatType;
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
            }
            return;
        }

        if (strings.length >= 3 && strings[1].equals("open")){
            if (strings[2].equals("setu")){
                if (FileManager.applicationConfig_File.getSpecificDataInstance().RandomImages.setu){
                    MessageManager.sendMessageBySituation(fromGroup, fromQQ, "色图发送已打开，请勿重复操作");
                }else {
                    FileManager.applicationConfig_File.getSpecificDataInstance().RandomImages.setu = true;
                    FileManager.applicationConfig_File.saveFile();
                    FileManager.applicationConfig_File.reloadFile();
                    MessageManager.sendMessageBySituation(fromGroup, fromQQ, "随机色图开启成功");
                }
            }
            return;
        }

        if (strings.length >= 3 && strings[1].equals("close")){
            if (strings[2].equals("setu")){
                if (!FileManager.applicationConfig_File.getSpecificDataInstance().RandomImages.setu){
                    MessageManager.sendMessageBySituation(fromGroup, fromQQ, "色图发送已关闭，请勿重复操作");
                }else {
                    FileManager.applicationConfig_File.getSpecificDataInstance().RandomImages.setu = false;
                    FileManager.applicationConfig_File.saveFile();
                    FileManager.applicationConfig_File.reloadFile();
                    MessageManager.sendMessageBySituation(fromGroup, fromQQ, "随机色图关闭成功");
                }
            }
            return;
        }

        String help = "\n使用\"super list\"查看拥有super权限的成员\n\n"
                + "使用以下格式添加或删除成员权限：\n"
                + "\"super add\\\\del QQ号码\"\n"
                + "示例：\n"
                + "\"super add 2799282971\"\n\n"
                + "使用\"super open setu开启色图发送\""
                + "使用\"super close setu开启色图发送\"";
        MessageManager.sendMessageBySituation(fromGroup, fromQQ, help);
    }

}
