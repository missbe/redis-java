package cn.missbe.redis.slave.net;

import cn.missbe.redis.slave.App;
import cn.missbe.redis.slave.task.SlaveBackupTaskExecutor;
import cn.missbe.util.PrintUtil;
import cn.missbe.util.SystemLog;

/**
 *   Description:java_code
 *   mail: love1208tt@foxmail.com
 *   Copyright (c) 2018. missbe
 *   This program is protected by copyright laws.
 *   Program Name:redisjava
 *   @Date:18-8-28 上午8:53
 *   @author lyg
 *   @version 1.0
 *   @Description
 **/

public class SlaveBackupsServer {

    public static void main(String[] args) {
        PrintUtil.print("数据备份服务器开始初始化环境" , SystemLog.Level.info);
        App.initialEnv();///初始化参数

        PrintUtil.print("服务器开始初始化环境完成.开始任务调度" , SystemLog.Level.info);

        SlaveBackupTaskExecutor.startTaskExecutor();///开启备份服务器备份任务
    }
}