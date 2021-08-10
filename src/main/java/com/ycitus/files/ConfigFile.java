package com.ycitus.files;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ycitus.debug.LoggerManager;
import com.ycitus.utils.FileUtil;

import java.io.*;

public class ConfigFile {

    /**
     * @return 该应用程序的[配置文件存储路径].
     */
    public static String getApplicationConfigPath() {

        String result = null;

        result = FileUtil.getJavaRunPath();
        result = result + "config" + File.separator + "Setu" + File.separator;

        return result;
    }


    /**
     * 用于<反射>的<配置文件Data对象>
     */
    private Class configDataClass = null;

    private String filePath = null;
    private String fileName = null;

    /**
     * 标注该对象是否已完成初始化
     */
    private boolean hasInit = false;


    public boolean isHasInit() {
        return hasInit;
    }

    public void setHasInit(boolean hasInit) {
        this.hasInit = hasInit;
    }

    /**
     * 存储<Data类的实例对象>
     */
    private Object configDataClassInstance = null;

    public ConfigFile(String filePath, String fileName, Class<?> configDataClass)
            throws IllegalArgumentException {
        super();
        this.filePath = filePath;
        this.fileName = fileName;
        this.configDataClass = configDataClass;
    }

    /**
     * 创建<该Data类的实例对象>
     */
    public void createConfigDataClassInstance() {

        LoggerManager.logDebug("FileSystem",
                "Use Reflect to Create the instance of Data Class >> " + configDataClass.getSimpleName());
        try {
            this.configDataClassInstance = this.configDataClass.newInstance();
        } catch (Exception e) {
            LoggerManager.reportException(e);
        }

    }

    /**
     * 在<本地存储>中<创建空文件>.
     */
    public void createFile() {
        File file = new File(filePath + fileName);

        file.getParentFile().mkdirs();

        try {
            file.createNewFile();
        } catch (IOException e) {
            LoggerManager.reportException(e);
        }
    }

    public Class<?> getConfigDataClass() {
        return configDataClass;
    }

    /**
     * @return 获取<该Data类的实例对象>
     */
    public Object getConfigDataClassInstance() {

        if (this.configDataClassInstance == null) {
            this.createConfigDataClassInstance();
        }

        return this.configDataClassInstance;
    }

    /**
     * @return 获取<该配置文件>的<File对象>
     */
    public File getFile() {
        return new File(filePath + fileName);
    }

    /**
     * @return 获取<该配置文件>的<文件名>
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @return 获取<该配置文件>的<路径名>
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * 初始化方法. 一般在创建完该对象后, 立即调用init().
     */
    public void init() throws IllegalArgumentException, IllegalAccessException,
            IOException {

        // 调用方法, 给该File的Data的静态变量进行赋值
        if (isExist() == false) {
            createFile();
            writeNormalFile();
        }

        LoggerManager.logDebug("FileSystem",
                "Load Local File to Memory >> " + this.getFileName(), true);

        // 从本地存储加载相应的配置文件
        loadFile();

        // Set Flag.
        this.hasInit = true;
    }

    /**
     * @return 判断该<配置文件>是否已经存在.
     */
    public boolean isExist() {
        File file = new File(filePath + fileName);
        return file.exists();
    }

    /**
     * 从<本地存储>加载<数据>到<内存>.
     */
    @SuppressWarnings("unchecked")
    public void loadFile() throws IllegalArgumentException,
            IllegalAccessException {

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(this.getFile()));
            // 从<本地存储>中<读取JSON配置文件数据>
            this.configDataClassInstance = new Gson().fromJson(reader,
                    this.configDataClass);
        } catch (FileNotFoundException e) {
            LoggerManager.reportException(e);
        } finally {

            // Load完毕立即close掉本地文件流, 以免资源占用.
            try {
                reader.close();
            } catch (IOException e) {
                LoggerManager.reportException(e);
            }
        }
    }

    /**
     * 重新从<本地存储>加载<数据>到<内存>. 该方法会<覆盖><内存>中<已有的数据>.
     */
    public void reloadFile() {
        LoggerManager.logDebug("FileSystem", "Start to Reload Config: " + this.getFileName());
        try {
            loadFile();
        } catch (IllegalArgumentException | IllegalAccessException e) {
            LoggerManager.reportException(e);
        }
    }

    /**
     * 保存<配置文件>到本地存储.
     */
    public void saveFile() {

        LoggerManager.logDebug("FileSystem",
                "Save Memory Data to Local File >> " + this.getFileName());

        // 定义要写出的本地配置文件
        File file = new File(filePath + fileName);
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(file);
            Gson gson = new GsonBuilder().serializeNulls().create();
            String nowJson = gson.toJson(this.getConfigDataClassInstance());

            fos.write(nowJson.getBytes());
            fos.flush();
            fos.close();

        } catch (Exception e) {
            LoggerManager.reportException(e);
        }

    }

    /**
     * 写出<该配置文件>的<默认配置文件数据>.
     */
    public void writeNormalFile() throws IllegalArgumentException,
            IllegalAccessException, IOException {

        LoggerManager.logDebug("FileSystem", "Start to Write Default ConfigFile Data >> " + this.getFileName());

        // 定义要写出的本地配置文件
        File file = new File(filePath + fileName);
        FileOutputStream fos = new FileOutputStream(file);

        Gson gson = new GsonBuilder().serializeNulls().create();
        String defaultJson = gson.toJson(this.getConfigDataClassInstance());

        fos.write(defaultJson.getBytes());
        fos.flush();
        fos.close();

    }

}
