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
        assignmentMail.setReceiverUserName(assignment.getAssignee());
        assignmentMail.setSender(assignment.getAssigner());
        assignmentMail.setReceiverNickName(receiver.getProfile().getNickName());
        assignmentMail.setSenderNickName(sender.getProfile().getNickName());
        assignmentMail.setBoardName(board.getName());
        assignmentMail.setCardCode(card.getCode());
        assignmentMail.setTemplateName(isCancelAssignment ? AssignmentCodes.CARD_CANCEL_ASSIGNMENT : AssignmentCodes.CARD_ASSIGNMENT_TEMPLATE);
        assignmentMail.setNotificationType(isCancelAssignment ? NotificationType.CANCEL_ASSIGNMENT_INVITATION : NotificationType.ASSIGNMENT_INVITATION);
        String content = String.format("%s邀请您参与协作处理看板【%s】中的卡片：%s。请知悉。", sender.getProfile().getNickName(), board.getName(), card.getCode());
        String cancelContent = String.format("%s已经将您从看板【%s】中编号为【%s】的卡片上移出，您不必再参与该卡片的相关处理。请知悉。", sender.getProfile().getNickName(), board.getName(), card.getCode());
        assignmentMail.setContent(isCancelAssignment ? cancelContent : content);
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
