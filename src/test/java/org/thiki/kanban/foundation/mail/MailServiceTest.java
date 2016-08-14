package org.thiki.kanban.foundation.mail;

import freemarker.template.TemplateException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thiki.kanban.TestBase;
import org.thiki.kanban.foundation.annotations.Scenario;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xubt on 8/7/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class MailServiceTest extends TestBase {
    @Resource
    private MailService mailService;

    @Scenario("通过模版发送邮件,且传入模版的数据是Map类型")
    @Test
    public void testMailTemplate() throws TemplateException, IOException, MessagingException {
        String templateName = "template_demo.ftl";
        Map<String, String> dataMap = new HashMap<String, String>();
        dataMap.put("userName", "王大锤");
        mailService.sendMailByTemplate("766191920@qq.com", "王大锤-邮箱注册认证", dataMap,
                templateName);
        mailService.sendMailByTemplate("thiki2016@163.com", "王大锤-邮箱注册认证", dataMap,
                templateName);

    }

    @Scenario("通过模版发送邮件,且传入模版的数据是实体类型")
    @Test
    public void testEntity() throws TemplateException, IOException, MessagingException {
        String templateName = "template_demo.ftl";
        MailEntity mailEntity = new MailEntity();

        mailEntity.setUserName("小茗");
        mailService.sendMailByTemplate("766191920@qq.com", "王大锤-邮箱注册认证", mailEntity,
                templateName);
        mailService.sendMailByTemplate("thiki2016@163.com", "王大锤-邮箱注册认证", mailEntity,
                templateName);
    }
}
