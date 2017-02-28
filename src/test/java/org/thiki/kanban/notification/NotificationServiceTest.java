package org.thiki.kanban.notification;

import freemarker.template.TemplateException;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.thiki.kanban.foundation.mail.MailService;
import org.thiki.kanban.user.UsersService;

import javax.mail.MessagingException;
import java.io.IOException;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * Created by xubt on 28/02/2017.
 */
public class NotificationServiceTest {
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    @Mock
    private MailService mailService;
    @Mock
    private UsersService usersService;
    @Mock
    private NotificationPersistence notificationPersistence;
    @InjectMocks
    private NotificationService notificationService;

    @Test
    public void should_send_email_if_notification_email_filed_is_set() throws TemplateException, IOException, MessagingException {
        Notification notification = new Notification();
        notification.needSentEmail(true);
        when(notificationPersistence.create(notification)).thenReturn(1);
        doNothing().when(mailService).sendMailByTemplate(any(), eq(notification.getTitle()));

        notificationService.notify(notification);

        verify(mailService).sendMailByTemplate(any(), eq(notification.getTitle()));
    }
}