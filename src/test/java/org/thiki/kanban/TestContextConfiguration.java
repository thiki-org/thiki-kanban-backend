package org.thiki.kanban;

import freemarker.template.TemplateException;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.thiki.kanban.foundation.mail.MailService;

import javax.mail.MessagingException;
import javax.sql.DataSource;
import java.io.IOException;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

/**
 * Created by xubt on 5/11/16.
 */
public class TestContextConfiguration {

    @Bean
    public MailService mailService() throws TemplateException, IOException, MessagingException {
        MailService mailService = mock(MailService.class);
        doNothing().when(mailService).sendMailByTemplate(anyString(), anyString(), any(), anyString());
        return mailService;
    }
}
