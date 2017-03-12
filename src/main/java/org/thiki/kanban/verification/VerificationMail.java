package org.thiki.kanban.verification;

import org.thiki.kanban.acceptanceCriteria.AcceptanceCriteria;
import org.thiki.kanban.board.Board;
import org.thiki.kanban.card.Card;
import org.thiki.kanban.foundation.mail.MailEntity;
import org.thiki.kanban.notification.NotificationType;

/**
 * Created by xubt on 03/07/2017.
 */
public class VerificationMail extends MailEntity {
    private String boardName;
    private String cardCode;
    private String acceptanceCriteria;


    public static MailEntity newMail(AcceptanceCriteria acceptanceCriteria, Card card, Board board) {
        VerificationMail verificationMail = new VerificationMail();
        verificationMail.setSubject(String.format("您所参与处理的卡片%s未通过验收", card.getCode()));
        verificationMail.setBoardName(board.getName());
        verificationMail.setCardCode(card.getCode());
        verificationMail.setAcceptanceCriteria(acceptanceCriteria.getSummary());
        verificationMail.setNotificationType(NotificationType.VERIFICATION_IS_NOT_PASSED);
        String content = String.format("验收标准：【%s】未通过验收，所属看板【%s】，卡片编号：【%s】。请知悉。", acceptanceCriteria.getSummary(), board.getName(), card.getCode());
        verificationMail.setContent(content);
        return verificationMail;
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

    public String getAcceptanceCriteria() {
        return acceptanceCriteria;
    }

    public void setAcceptanceCriteria(String acceptanceCriteria) {
        this.acceptanceCriteria = acceptanceCriteria;
    }
}
