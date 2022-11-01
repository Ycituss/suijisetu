package com.ycitus;

import com.ycitus.command.RobotCommandChatType;
import com.ycitus.command.RobotCommandManager;
import com.ycitus.debug.LoggerManager;
import com.ycitus.files.FileManager;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.events.*;

public final class PluginMain extends JavaPlugin {

    // WARNING: INSTANCE字段必须设置为public, 否则mirai-console在反射时会失败.
    public static final PluginMain INSTANCE = new PluginMain();
    private static boolean pluginLoaded = false;
    public static RobotCommandManager commandManager = null;
    private static Bot CURRENT_BOT = null;
    private static String version = "3.6.4";

    public static String getVersion() { return version; }

    public static PluginMain getInstance() { return INSTANCE;  }

    public static Bot getCurrentBot() {
        return CURRENT_BOT;
    }

    public static boolean isPluginLoaded() {
        return pluginLoaded;
    }

    public static RobotCommandManager getCommandManager() {
        return commandManager;
    }

    private PluginMain() {
        super(new JvmPluginDescriptionBuilder("com.ycitus.setu", "3.6.4")
                .name("Setu")
                .author("ycitus")
                .build());
    }

    @Override
    public void onEnable() {

        pluginLoaded = true;
        LoggerManager.logDebug("Setu >> Enable.", true);
        LoggerManager.logDebug("Start Init...", true);

        // Init FileSystem.
        try {
            LoggerManager.logDebug("FileSystem", "Init FileSystem.", true);
            FileManager.getSingleInstance();
        } catch (IllegalArgumentException e) {
            LoggerManager.reportException(e);
        }

        // Init CommandSystem.
        LoggerManager.logDebug("CommandSystem", "Init CommandSystem.", true);
        commandManager = new RobotCommandManager();

        /** 接收群消息事件 **/
        LoggerManager.logDebug("EventSystem", "Start to subscribe events.");
        GlobalEventChannel.INSTANCE.subscribeAlways(GroupMessageEvent.class,
                event -> {
                    {
                        commandManager.receiveMessage(
                                RobotCommandChatType.GROUP_CHAT.getType(),
                                event.getTime(), event.getGroup().getId(),
                                event.getSender().getId(), event.getMessage());

                    }
                });


        /** 接收好友消息事件 **/
        GlobalEventChannel.INSTANCE.subscribeAlways(
                FriendMessageEvent.class,
                event -> {
                    {
                        commandManager.receiveMessage(
                                RobotCommandChatType.FRIEND_CHAT.getType(),
                                event.getTime(), -1,  event.getSender().getId(),
                                event.getMessage());

                    }

                });

        /** 接收陌生人消息事件 **/
        GlobalEventChannel.INSTANCE.subscribeAlways(
                StrangerMessageEvent.class,
                event -> {
                    {
                        commandManager.receiveMessage(
                                RobotCommandChatType.STRANGER_CHAT.getType(),
                                event.getTime(), -1, event.getSender().getId(), event.getMessage());
                    }

                });


        /** 接收群临时消息事件 **/
        GlobalEventChannel.INSTANCE.subscribeAlways(
                GroupTempMessageEvent.class,
                event -> {
                    {

                        commandManager.receiveMessage(
                                RobotCommandChatType.GROUP_TEMP_CHAT
                                        .getType(), event.getTime(),
                                event.getGroup().getId(),
                                event.getSender().getId(), event.getMessage());


                    }
                });


        /** 机器人登陆事件 **/
        GlobalEventChannel.INSTANCE.subscribeAlways(BotOnlineEvent.class, event -> {
            {
                /** 初始化Bot实例 **/
                tryInitBot(event.getBot());
            }
        });

        /** 接收好友添加请求事件 **/
        GlobalEventChannel.INSTANCE
                .subscribeAlways(
                        NewFriendRequestEvent.class,
                        event -> {

                            {
                                // 自动处理好友邀请
                                if (FileManager.applicationConfig_File.getSpecificDataInstance().Admin.InvitationManager.QQFriendInvitation.autoAcceptAddQQFriend) {
                                    // 同意 -> 好友添加请求
                                    LoggerManager.logDebug(
                                            "ContactSystem",
                                            "Accept -> FriendAddRequest: "
                                                    + event.getFromId());
                                    event.accept();


                                } else {
                                    // 拒绝 -> 好友添加请求
                                    event.reject(false);
                                }

                            }

                        });



        /** 邀请入群请求事件 **/
        GlobalEventChannel.INSTANCE
                .subscribeAlways(
                        BotInvitedJoinGroupRequestEvent.class,
                        event -> {
                            {
                                if (FileManager.applicationConfig_File.getSpecificDataInstance().Admin.InvitationManager.QQGroupInvitation.autoAcceptAddQQGroup) {
                                    event.accept();
                                    LoggerManager.logDebug(
                                            "ContactSystem",
                                            "Accept -> InvitedJoinGroupRequest: "
                                                    + event.getGroupId());
                                }

                            }
                        });

        LoggerManager.logDebug("End Init...", true);
    }


    public void tryInitBot(Bot bot) {
        if (CURRENT_BOT == null) {
            CURRENT_BOT = bot;
        }
    }


    @Override
    public void onDisable() {
        super.getLogger().info("Setu >> Disable.");
    }

}
