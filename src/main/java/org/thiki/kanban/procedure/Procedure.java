package org.thiki.kanban.procedure;

import org.hibernate.validator.constraints.Length;
import org.thiki.kanban.card.Card;

import javax.validation.constraints.NotNull;

/**
 * 卡片工序
 * Created by xubitao on 04/26/16.
 */
public class Procedure {

    private String id;

    @NotNull(message = ProcedureCodes.titleIsRequired)
    @Length(min = 1, max = 10, message = ProcedureCodes.titleIsInvalid)
    private String title;

    private String reporter;

    @NotNull(message = "boardId不能为空")
    private String boardId;

    private Integer orderNumber;

    private String creationTime;
    private String modificationTime;

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getModificationTime() {
        return modificationTime;
    }

    public void setModificationTime(String modificationTime) {
        this.modificationTime = modificationTime;
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 在procedure内添加一个card
     *
     * @param card
     * @return
     */
    public Card addCard(Card card) {
        // card.setProcedureId(this.id);
        return card;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }
}
