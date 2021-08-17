package com.ycitus.command.commands;

import com.ycitus.command.RobotCommand;
import com.ycitus.command.RobotCommandChatType;
import com.ycitus.command.RobotCommandUser;
import com.ycitus.framework.BotManager;
import com.ycitus.framework.MessageManager;
import net.mamoe.mirai.contact.ContactList;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.MessageChain;

import java.util.regex.Pattern;

public class SendtoOtherGroupCommand extends RobotCommand {

    private static final Pattern pattern = Pattern
            .compile("^(?:(?:stg)|(?:STG)|(?:sendtogroup)|(?:SendtoGroup))\\s?([\\s\\S]*)$");

    public SendtoOtherGroupCommand(String rule){
        super(rule);
        getRange().add(RobotCommandChatType.FRIEND_CHAT);
        getRange().add(RobotCommandChatType.GROUP_CHAT);

//        getUser().add(RobotCommandUser.NORMAL_USER);
//        getUser().add(RobotCommandUser.GROUP_ADMINISTRATOR);
//        getUser().add(RobotCommandUser.GROUP_OWNER);
        getUser().add(RobotCommandUser.BOT_ADMINISTRATOR);
    }

    @Override
    public void runCommand(int msgType, int time, long fromGroup, long fromQQ, MessageChain messageChain){

        String msg = messageChain.toString();
        ContactList<Group> groups = BotManager.getAllQQGroups();

        String[] strings = msg.split(" ");

        if (strings.length >= 3 && Pattern.matches("\\d+", strings[1])){
            int groupindex = Integer.parseInt(strings[1]);
            if (groupindex >= 0 && groupindex < groups.size()){
                String sendMsg = new String();
                for (int i = 2; i < strings.length; i++){
                    sendMsg = sendMsg + strings[i];
                    if (i != strings.length-1) sendMsg = sendMsg + " ";
                }
                for (Group group : groups){
                    if (groupindex-- == 0){
                        MessageManager.sendMessageToQQGroup(group.getId(), sendMsg);
                        break;
                    }
                }
            }else {
                String indexError = "没有对应的群编号\n"
                        + "请输入\"群编号\"或\"grouplist\"或\"gl\"查看";
                MessageManager.sendMessageBySituation(fromGroup, fromQQ, indexError);
            }
            return;
        }

        String help = "使用请以以下格式发送\n"
                + "\"指令 群编号 要发送的消息\"" + "\n"
                + "(群编号请输入\"群编号\"或\"grouplist\"或\"gl\"查看)\n"
                + "用法示例：\n"
                + "\"stg 1 测试消息\"" + "\n"
                + "\"stg 2 test message\"\n\n"
                + "更多帮助请查看http://www.ycitus.cn/other/index.html";

        MessageManager.sendMessageBySituation(fromGroup, fromQQ, help);
    }
}
