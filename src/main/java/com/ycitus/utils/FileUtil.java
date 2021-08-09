package com.ycitus.utils;

import java.io.File;

public class FileUtil {

    /**
     * 可能的输出结果:
     * D:\LocalWorkSpace\Java\workspace\Silicon
     *
     * @return 应用程序的运行路径.
     */
    public static String getJavaRunPath() {

        /**
         * 该方法也有以下几种实现原理:
         * String result = Class.class.getClass().getResource("/").getPath();
         * String result = System.getProperty("user.dir");
         */
        // 利用 new File()相对路径原理
        return new File("").getAbsolutePath() + File.separator;
    }

}
