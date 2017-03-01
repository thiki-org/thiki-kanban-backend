package org.thiki.kanban.assignment;

import org.thiki.kanban.board.Board;
import org.thiki.kanban.card.Card;
import org.thiki.kanban.foundation.mail.MailEntity;
import org.thiki.kanban.notification.NotificationType;
import org.thiki.kanban.user.User;

/**
 * Created by xubt on 01/03/2017.
 */
public class AssignmentMail extends MailEntity {
    private String boardName;
    private String cardCode;

    public static AssignmentMail newMail(Assignment assignment, User sender, User receiver, Card card, Board board, boolean isCancelAssignment) {
        AssignmentMail assignmentMail = new AssignmentMail();
        assignmentMail.setSubject(sender.getProfile().getNickName() + "邀请您处理卡片:" + card.getCode());
        if (isCancelAssignment) {
            assignmentMail.setSubject("您不必再处理卡片:" + card.getCode());
        }
        assignmentMail.setReceiver(receiver.getEmail());
        assignmentMail.setSender(assignment.getAssigner());
        assignmentMail.setReceiverUserName(receiver.getProfile().getNickName());
        assignmentMail.setSenderUserName(sender.getProfile().getNickName());
        assignmentMail.setBoardName(board.getName());
        assignmentMail.setCardCode(card.getCode());
        assignmentMail.setTemplateName(isCancelAssignment ? AssignmentCodes.CARD_CANCEL_ASSIGNMENT : AssignmentCodes.CARD_ASSIGNMENT_TEMPLATE);
        assignmentMail.setNotificationType(isCancelAssignment ? NotificationType.CANCEL_ASSIGNMENT_INVITATION : NotificationType.ASSIGNMENT_INVITATION);
        return assignmentMail;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }
}
