package com.ycitus.files;

import com.ycitus.debug.LoggerManager;

import java.io.IOException;

public class FileManager {

    private static FileManager SINGLE_INSTANCE = null;
    public static FileManager getSingleInstance() {

        if (SINGLE_INSTANCE == null) {
            SINGLE_INSTANCE = new FileManager();
        }

        return SINGLE_INSTANCE;
    }

    private FileManager() {
        try {
            this.init();
        } catch (IllegalAccessException | IOException e) {
            LoggerManager.reportException(e);
        }
    }

    /** Config Instances. **/
    public static ApplicationConfig_File applicationConfig_File = null;

    /**
     * 调用本方法来<初始化>配置文件系统.
     */
    public void init() throws IllegalArgumentException,
            IllegalAccessException, IOException {

        LoggerManager.logDebug("FileSystem", "Init All Configs...", true);

        // ApplicationConfig.json
        LoggerManager.logDebug("FileSystem", "Init >> ApplicationConfig.json", true);
        applicationConfig_File = new ApplicationConfig_File(ConfigFile.getApplicationConfigPath(),
                "ApplicationConfig.json", ApplicationConfig_Data.class);
        applicationConfig_File.init();


    }

}
