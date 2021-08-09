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

public class GroupListCommand extends RobotCommand {

    private static final Pattern pattern = Pattern
            .compile("^(?:(?:gl)|(?:GL)|(?:grouplist)|(?:GroupList)|(?:群列表))\\s?([\\s\\S]*)$");

    public GroupListCommand(String rule){
        super(rule);
        getRange().add(RobotCommandChatType.FRIEND_CHAT);
        getRange().add(RobotCommandChatType.GROUP_CHAT);

        getUser().add(RobotCommandUser.NORMAL_USER);
        getUser().add(RobotCommandUser.GROUP_ADMINISTRATOR);
        getUser().add(RobotCommandUser.GROUP_OWNER);
        getUser().add(RobotCommandUser.BOT_ADMINISTRATOR);
    }

    @Override
    public void runCommand(int msgType, int time, long fromGroup, long fromQQ, MessageChain messageChain){

        String msg = messageChain.contentToString();
        ContactList<Group> groups = BotManager.getAllQQGroups();

        if (msg.matches("^(?:(?:gl)|(?:GL)|(?:grouplist)|(?:GroupList)|(?:群列表))\\s?([\\s\\S]*)$")) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("本机器人添加的所有群：\n");
            int index = 0;
            for (Group group : groups){
                stringBuffer.append(index++).append("\t").append(group.getId()).append("\t")
                        .append(group.getName()).append("\n");
            }

            String groupString = stringBuffer.toString();
            MessageManager.sendMessageBySituation(fromGroup, fromQQ, groupString);

            return;
        }


    }
}
