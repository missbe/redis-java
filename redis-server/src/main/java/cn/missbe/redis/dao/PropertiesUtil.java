package cn.missbe.redis.dao;

import cn.missbe.redis.App;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 *   Description:java_code
 *   mail: love1208tt@foxmail.com
 *   Copyright (c) 2018. missbe
 *   This program is protected by copyright laws.
 *   Program Name:resources_search_java
 *   @Date:18-8-13 下午1:16
 *   @author lyg
 *   @version 1.0
 *   @Description
 **/

public class PropertiesUtil {
    private static Properties prop = new Properties();
    private static HashMap<String, String> dbProps = new HashMap<String, String>();

    static {
        initPropes();
    }
    private  PropertiesUtil(){}

    private static Map<String, String> initPropes(){
        InputStream ios = null;
        try {
            String path = PropertiesUtil.class.getClassLoader().getResource(App.DB_FILE_NAME).getPath();
//            System.out.println("DB配置文件路径：" + path);
            ios = new FileInputStream(path);
            prop.load(ios);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("数据库配置文件加载失败，数据获取失败！");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("数据库配置文件加载IO异常，数据获取失败！");
        }
        for (Object key : prop.keySet()){
            Object values = prop.get(key);
            if(key instanceof String && values instanceof String)
                dbProps.put((String)key, (String)values);
        }
        return dbProps;
    }

    public static void reloadPropes(){
        initPropes();
    }

    public static HashMap<String, String> getDbProps() {
        return dbProps;
    }
}
