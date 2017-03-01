package org.thiki.kanban.notification;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.thiki.kanban.assignment.AssignmentMail;
import org.thiki.kanban.foundation.mail.MailService;

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
    private NotificationPersistence notificationPersistence;
    @InjectMocks
    private NotificationService notificationService;

    @Test
    public void should_send_email_after_notifying() throws Exception {
        Notification notification = new Notification();
        when(notificationPersistence.create(notification)).thenReturn(1);
        doNothing().when(mailService).sendMailByTemplate(any(), eq(notification.getTitle()));
        AssignmentMail assignmentMail = new AssignmentMail();
        assignmentMail.setNotificationType(NotificationType.ASSIGNMENT_INVITATION);
        notificationService.sendEmailAfterNotifying(assignmentMail);

        verify(mailService).sendMailByTemplate(any());
    }
}