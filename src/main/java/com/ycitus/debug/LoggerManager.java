package com.ycitus.debug;


import com.ycitus.PluginMain;
import com.ycitus.files.FileManager;
import net.mamoe.mirai.utils.MiraiLogger;

public class LoggerManager {

    public static final String SAD_FACIAL_EXPRESSION = "(ó﹏ò｡)";

    public static MiraiLogger getLogger() {
        return PluginMain.getInstance().getLogger();
    }

    public static void logDebug(String type, String msg, boolean forceLog) {
        logDebug("[" + type + "] " + msg, forceLog);
    }

    public static void logDebug(String content, boolean forceLog) {

        // Is IDEA Mode ?
        if (!PluginMain.isPluginLoaded()) {
            System.out.println(content);
            return;
        }

        if (forceLog) {
            LoggerManager.getLogger().info(content);
            return;
        }

        // 如果不存在ApplicationConfig.json文件, 则默认开启Debug模式
        if (FileManager.applicationConfig_File == null ||
                !FileManager.applicationConfig_File.isHasInit() ||
                FileManager.applicationConfig_File.getSpecificDataInstance().Debug.enable) {
            LoggerManager.getLogger().info(content);
        }
    }

    public static void logDebug(String content) {
        logDebug(content, false);
    }

    public static void logDebug(String type, String msg) {

        logDebug(type, msg, false);
    }

    public static void logError(Exception e) {
        LoggerManager.getLogger().error(getExceptionInfo(e));
    }

    public static void reportException(Exception e) {
        LoggerManager.getLogger().error(getExceptionInfo(e));
    }


    public static String getExceptionInfo(Exception e) {

        // 添加Exception基础信息
        String result = "错误类型: " + e.getClass() + "\n原因: " + e.getCause() +
                "\n消息: " + e.getMessage() +
                "\n栈追踪: " +

                // 添加栈追踪记录
                "\n" +
                getExceptionStack(e);
        return result;
    }

    public static String getExceptionStack(Exception e) {
        StringBuilder result = new StringBuilder();
        for (StackTraceElement s : e.getStackTrace()) {
            result.append("\tat ").append(s).append("\r\n");
        }
        return result.toString();
    }


}
