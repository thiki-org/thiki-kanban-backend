package net.thiki.core.persistant;

/**
 * 可以持久化的对象，必须有id
 * @author joeaniu
 *
 */
public interface Entity {
	
	/**
	 * 获取id， 在同一类Entity下，id可以区分不同对象， 即是唯一的
	 * @return
	 */
	public abstract Long getId();
	
}
