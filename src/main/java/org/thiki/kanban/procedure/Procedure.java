package org.thiki.kanban.procedure;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.thiki.kanban.card.Card;

import javax.validation.constraints.NotNull;

/**
 * 卡片工序
 * Created by xubitao on 04/26/16.
 */
public class Procedure {

    private String id;
    @NotEmpty(message = ProcedureCodes.titleIsRequired)
    @NotNull(message = ProcedureCodes.titleIsRequired)
    @Length(max = 30, message = ProcedureCodes.titleIsInvalid)
    private String title;

    private String author;
    private String boardId;
    private Integer sortNumber;

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public Integer getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(Integer sortNumber) {
        this.sortNumber = sortNumber;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    @Override
    public String toString() {
        return "Procedure{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", boardId='" + boardId + '\'' +
                ", sortNumber=" + sortNumber +
                ", creationTime='" + creationTime + '\'' +
                ", modificationTime='" + modificationTime + '\'' +
                '}';
    }
}
