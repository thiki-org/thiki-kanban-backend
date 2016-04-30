package org.thiki.kanban.entry;


/**
 * 任务列表
 * Created by xubitao on 04/26/16.
 */
public class Entry {

    private Integer id;
    /** 列表标题 */
    private String title;
    /** 创建者用户Id */
    private Integer reporter;

    public Integer getReporter() {
        return reporter;
    }

    public void setReporter(Integer reporter) {
        this.reporter = reporter;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
}
