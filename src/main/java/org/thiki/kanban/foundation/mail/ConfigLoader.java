package org.thiki.kanban.foundation.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

/**
 * Created by xubt on 8/7/16.
 */

public class ConfigLoader {
    //日志记录对象
    private static Logger log = LoggerFactory.getLogger(ConfigLoader.class);
    // 配置文件路径
    private static String mailPath = "mail/mail.properties";
    // 邮件发送SMTP主机
    private static String server;
    // 发件人邮箱地址
    private static String sender;
    // 发件人邮箱用户名
    private static String username;
    // 发件人邮箱密码
    private static String password;
    // 发件人显示昵称
    private static String nickname;

    static {
        // 类初始化后加载配置文件
        InputStream in = ConfigLoader.class.getClassLoader()
                .getResourceAsStream(mailPath);
        Properties props = new Properties();

        try {
            Reader reader = new InputStreamReader(in, "UTF-8");

            props.load(reader);
        } catch (IOException e) {
            log.error("load mail setting error,pleace check the file path:"
                    + mailPath);
            log.error(e.toString(), e);
        }
        server = props.getProperty("mail.server");
        sender = props.getProperty("mail.sender");
        username = props.getProperty("mail.username");
        password = props.getProperty("mail.password");
        nickname = props.getProperty("mail.nickname");
        log.debug("load mail setting success,file path:" + mailPath);
    }

    public static String getServer() {
        return server;
    }

    public static String getSender() {
        return sender;
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }

    public static String getNickname() {
        return nickname;
    }

    public static void setMailPath(String mailPath) {
        ConfigLoader.mailPath = mailPath;
    }
}
