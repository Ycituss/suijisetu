package com.ycitus.command.commands;

import com.ycitus.PluginMain;
import com.ycitus.command.RobotCommand;
import com.ycitus.command.RobotCommandChatType;
import com.ycitus.command.RobotCommandManager;
import com.ycitus.command.RobotCommandUser;
import com.ycitus.files.FileManager;
import com.ycitus.framework.MessageManager;
import net.mamoe.mirai.message.data.MessageChain;

public class RebotCommand extends RobotCommand {
    public RebotCommand(String rule) {
        super(rule);
        getRange().add(RobotCommandChatType.FRIEND_CHAT);
        getRange().add(RobotCommandChatType.GROUP_TEMP_CHAT);
        getRange().add(RobotCommandChatType.GROUP_CHAT);
        getRange().add(RobotCommandChatType.STRANGER_CHAT);

        getUser().add(RobotCommandUser.BOT_ADMINISTRATOR);
    }

    @Override
    public void runCommand(int msgType, int time, long fromGroup, long fromQQ, MessageChain messageChain) {

        PluginMain.commandManager = new RobotCommandManager();
        FileManager.applicationConfig_File.reloadFile();

        MessageManager.sendMessageBySituation(fromGroup, fromQQ, "Rebot Successfully!");
    }
}
