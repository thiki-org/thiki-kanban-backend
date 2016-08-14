package org.thiki.kanban.foundation.mail;

import com.alibaba.fastjson.JSON;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Map;

/**
 * Created by xubt on 8/7/16.
 */

@Service
public class MailService {
    /**
     * 根据模板名称查找模板，加载模板内容后发送邮件
     *
     * @param receiver     收件人地址
     * @param subject      邮件主题
     * @param map          邮件内容与模板内容转换对象
     * @param templateName 模板文件名称
     * @return void
     * @throws IOException
     * @throws TemplateException
     * @throws MessagingException
     */
    public static void sendMailAndFileByTemplate(String receiver,
                                                 String subject, String filePath, Map<String, String> map,
                                                 String templateName) throws IOException, TemplateException,
            MessagingException {
        String maiBody = "";
        String server = ConfigLoader.getServer();
        String sender = ConfigLoader.getSender();
        String username = ConfigLoader.getUsername();
        String password = ConfigLoader.getPassword();
        String nickname = ConfigLoader.getNickname();
        MailSender mail = new MailSender(server);
        mail.setNeedAuth(true);
        mail.setNamePass(username, password, nickname);
        maiBody = TemplateFactory.generateHtmlFromFtl(templateName, map);
        mail.setSubject(subject);
        mail.addFileAffix(filePath);
        mail.setBody(maiBody);
        mail.setReceiver(receiver);
        mail.setSender(sender);
        mail.sendout();
    }

    /**
     * 普通方式发送邮件内容
     *
     * @param receiver 收件人地址
     * @param subject  邮件主题
     * @param maiBody  邮件正文
     * @return void
     * @throws IOException
     * @throws MessagingException
     */
    public static void sendMail(String receiver, String subject, String maiBody)
            throws IOException, MessagingException {
        String server = ConfigLoader.getServer();
        String sender = ConfigLoader.getSender();
        String username = ConfigLoader.getUsername();
        String password = ConfigLoader.getPassword();
        String nickname = ConfigLoader.getNickname();
        MailSender mail = new MailSender(server);
        mail.setNeedAuth(true);
        mail.setNamePass(username, password, nickname);
        mail.setSubject(subject);
        mail.setBody(maiBody);
        mail.setReceiver(receiver);
        mail.setSender(sender);
        mail.sendout();
    }

    /**
     * 普通方式发送邮件内容，并且附带文件附件
     *
     * @param receiver 收件人地址
     * @param subject  邮件主题
     * @param filePath 文件的绝对路径
     * @param maiBody  邮件正文
     * @return void
     * @throws IOException
     * @throws MessagingException
     */
    public static void sendMailAndFile(String receiver, String subject,
                                       String filePath, String maiBody) throws IOException,
            MessagingException {
        String server = ConfigLoader.getServer();
        String sender = ConfigLoader.getSender();
        String username = ConfigLoader.getUsername();
        String password = ConfigLoader.getPassword();
        String nickname = ConfigLoader.getNickname();
        MailSender mail = new MailSender(server);
        mail.setNeedAuth(true);
        mail.setNamePass(username, password, nickname);
        mail.setSubject(subject);
        mail.setBody(maiBody);
        mail.addFileAffix(filePath);
        mail.setReceiver(receiver);
        mail.setSender(sender);
        mail.sendout();
    }

    /**
     * 根据模板名称查找模板，加载模板内容后发送邮件
     *
     * @param receiver     收件人地址
     * @param subject      邮件主题
     * @param map          邮件内容与模板内容转换对象
     * @param templateName 模板文件名称
     * @return void
     * @throws IOException
     * @throws TemplateException
     * @throws MessagingException
     */
    public void sendMailByTemplate(String receiver, String subject,
                                   Map<String, String> map, String templateName) throws IOException,
            TemplateException, MessagingException {
        sendMail(receiver, subject, templateName, map);
    }

    /**
     * 根据模板名称查找模板，加载模板内容后发送邮件
     *
     * @param receiver     收件人地址
     * @param subject      邮件主题
     * @param entity       邮件内容与模板内容转换对象
     * @param templateName 模板文件名称
     * @return void
     * @throws IOException
     * @throws TemplateException
     * @throws MessagingException
     */
    public void sendMailByTemplate(String receiver, String subject,
                                   Object entity, String templateName) throws IOException,
            TemplateException, MessagingException {
        Map dataMap = JSON.parseObject(JSON.toJSONString(entity));
        sendMail(receiver, subject, templateName, dataMap);
    }

    private void sendMail(String receiver, String subject, String templateName, Map dataMap) throws IOException, TemplateException, MessagingException {
        String maiBody = "";
        String server = ConfigLoader.getServer();
        String sender = ConfigLoader.getSender();
        String username = ConfigLoader.getUsername();
        String password = ConfigLoader.getPassword();
        String nickname = ConfigLoader.getNickname();
        MailSender mail = new MailSender(server);
        mail.setNeedAuth(true);
        mail.setNamePass(username, password, nickname);
        maiBody = TemplateFactory.generateHtmlFromFtl(templateName, dataMap);
        mail.setSubject(subject);
        mail.setBody(maiBody);
        mail.setReceiver(receiver);
        mail.setSender(sender);
        mail.sendout();
    }

}
