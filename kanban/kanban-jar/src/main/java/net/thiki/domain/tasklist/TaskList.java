package net.thiki.domain.tasklist;

import net.thiki.core.persistant.Entity;

/**
 * 任务列表
 * @author joeaniu
 *
 */
public class TaskList implements Entity {

	private Long id;
	/** 标题 */
	private String title;
	/** 内容 */
	private String content;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
