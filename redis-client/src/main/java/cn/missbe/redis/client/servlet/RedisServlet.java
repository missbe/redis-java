package cn.missbe.redis.client.servlet;

import cn.missbe.redis.client.App;
import cn.missbe.redis.client.bean.HashBean;
import cn.missbe.redis.client.dto.JsonBaseResult;
import cn.missbe.redis.client.util.CommandProcessUtil;
import cn.missbe.util.IOUtils;
import cn.missbe.util.PrintUtil;
import cn.missbe.util.SystemLog;
import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.Socket;
import java.util.zip.CRC32;

/**
 *   Description:java_code
 *   mail: love1208tt@foxmail.com
 *   Copyright (c) 2018. missbe
 *   This program is protected by copyright laws.
 *   Program Name:redisjava
 *   @Date:18-8-29 上午10:50
 *   @author lyg
 *   @version 1.0
 *   @Description
 **/
@WebServlet(urlPatterns = {"/redis/data/cached"})
public class RedisServlet extends HttpServlet {
    private CRC32 crc32 = new CRC32();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       String msg;
       String command = req.getParameter("command");
       boolean isCommand =  CommandProcessUtil.isCommand(command);
       if(!isCommand){
           msg = "提示:你输入的命令不合法.";
       }
       String key = req.getParameter("key");
       String value = req.getParameter("value");

       crc32.update(key.getBytes());
       HashBean bean  = getServerHashBean((int) (crc32.getValue() % App.HASH_LENGTH));
       command = command + " " + key +" " + value;

       msg = processServerCommand(bean, command);///向服务器发请求得到回应

        ///向前端发送JSON数据
        PrintStream ps = new PrintStream(resp.getOutputStream());
        ps.println(JSON.toJSONString(new JsonBaseResult(msg,true)));
        ps.flush();
        ps.close();

    }
    public String processServerCommand(HashBean bean, String command) throws IOException {
        ////与服务器通信部分
        Socket socket = new Socket(bean.getIp(), bean.getPort());
        PrintStream ps = new PrintStream(socket.getOutputStream());
        ps.println(command);///发送命令到服务器
        String msg = null ;

        try {
            msg = IOUtils.parseStream(socket.getInputStream(), App.SERVER_OK);
        } catch (IOException e) {
            PrintUtil.print("服务器端已经关闭或出现错误.结束访问", SystemLog.Level.error);
        }
        return msg;
    }
    /**
     * 根据哈希值得到对应服务器IP地址和端口
     * @param hash hash值
     * @return 服务器信息
     */
    public HashBean getServerHashBean(int hash){
        for(HashBean bean : App.getHashBeans()){
            if(bean.isHash(hash)){
                bean.setCount(bean.getCount()+1);
                return bean;
            }
        }
        return null;
    }
}
