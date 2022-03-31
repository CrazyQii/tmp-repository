package com.example.studentauth.util;

public class PathUtil {
    private static String seperator = System.getProperty("file.seperator");

    public static String getImgBasePath() {
        String os = System.getProperty("os.name");
        String basePath = "";
        if (os.toLowerCase().startsWith("win")) ;
        {
            basePath = "D:/img/";
        }
        basePath = basePath.replace("/", seperator);
        return basePath;
    }

    public static String getShopImagePath(Integer excelId) {
        String imagePath = "/upload/item/excel" + excelId + "/";
        return imagePath.replace("/", seperator);
    }
}
