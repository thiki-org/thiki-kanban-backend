package org.thiki.kanban.entry;

import org.hibernate.validator.constraints.Length;
import org.thiki.kanban.card.Card;

import javax.validation.constraints.NotNull;

/**
 * 任务列表
 * Created by xubitao on 04/26/16.
 */
public class Entry {

    private String id;
    /**
     * 列表标题
     */
    @NotNull(message = "列表名称不能为空")
    @Length(min = 1, max = 50, message = "列表名称长度非法,有效长度为1~50个字符。")
    private String title;
    /**
     * 创建者用户Id
     */
    private Integer reporter;

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

    public Integer getReporter() {
        return reporter;
    }

    public void setReporter(Integer reporter) {
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
     * 在entry内添加一个card
     *
     * @param card
     * @return
     */
    public Card addCard(Card card) {
        // card.setEntryId(this.id);
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
