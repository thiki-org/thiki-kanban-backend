package org.thiki.kanban.foundation.mail;

import freemarker.template.TemplateException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thiki.kanban.TestBase;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xubt on 8/7/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class MailUtilTest extends TestBase {
    @Test
    public void testMailTemplate() throws TemplateException, IOException, MessagingException {
        String templateName = "template_demo.ftl";
        Map<String, String> dataMap = new HashMap<String, String>();
        dataMap.put("userName", "王大锤");
        MailUtil.sendMailByTemplate("thiki2016@163.com", "王大锤-邮箱注册认证", dataMap,
                templateName);

    }
}
