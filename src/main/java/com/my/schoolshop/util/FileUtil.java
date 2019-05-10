package com.my.schoolshop.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class FileUtil {
    private static String seperator = File.separator;//获取系统分割符号
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Random r = new Random();

    public static String getImgBasePath(){
        //获取系统名称
        String os = System.getProperty("os.name");
        String basePath = "";
        if(os.toLowerCase().startsWith("win")){
            basePath = "D:/projectdev/img";
        }else {
            basePath = "/home/xiangzepro";
        }

        basePath = basePath.replace("/",seperator);//替换路径分割符号
        return basePath;
    }

    //获取不同类别图片的系统存放路径
    public static String getHeadLineImagePath(){
        String headLineImagePath = "/upload/images/item/headline/";
        headLineImagePath = headLineImagePath.replace("/",seperator);
        return headLineImagePath;
    }

    public static String getShopCategoryImagePath(){
        String shopCategoryImagePath = "/upload/images/item/shopcategory/";
        shopCategoryImagePath = shopCategoryImagePath.replace("/",seperator);
        return shopCategoryImagePath;
    }

    public static String getPersonInfoImagePath(){
        String personInfoImagePath = "/upload/images/item/personinfo/";
        personInfoImagePath = personInfoImagePath.replace("/",seperator);
        return personInfoImagePath;
    }
    public static String getShopImagePath(long shopId) {
        StringBuilder shopImagePathBuilder = new StringBuilder();
        shopImagePathBuilder.append("upload/images/item/shop/");
        shopImagePathBuilder.append(shopId);
        shopImagePathBuilder.append("/");
        System.out.println(seperator);
        String shopImagePath = shopImagePathBuilder.toString().replace("/",seperator);
        return shopImagePath;
    }

    public static String getRandomFileName() {
        // 生成随机文件名：当前年月日时分秒+五位随机数（为了在实际项目中防止文件同名而进行的处理）
        int rannum = (int) (r.nextDouble() * (99999 - 10000 + 1)) + 10000; // 获取随机数
        String nowTimeStr = simpleDateFormat.format(new Date()); // 当前时间
        return nowTimeStr + rannum;
    }

    public static void deleteFile(String storePath) {
        File file = new File(getImgBasePath() + storePath);
        if (file.exists()) {
            if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    files[i].delete();
                }
            }
            file.delete();
        }
    }



}
