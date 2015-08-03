package net.thiki.domain.tasklist;

import net.thiki.core.persistant.Entity;

/**
 * 任务卡片
 * @author joeaniu
 *
 */
public class TaskCard implements Entity {

	private Long id;
	/** 摘要，显示在卡片上 */
	private String summary;
	/** 详情，打开才能显示 */
	private String detail;
	/** 被指派的责任人 */
	private Long assigneeUserId;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public Long getAssigneeUserId() {
		return assigneeUserId;
	}
	public void setAssigneeUserId(Long assigneeUserId) {
		this.assigneeUserId = assigneeUserId;
	}
	
	
	
}
