package net.thiki.repo.mybatis.mappers;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.SelectKey;

import net.thiki.kanban.domain.entry.Entry;
import net.thiki.kanban.domain.entry.Task;

public interface TaskMapperExt {
/*
    insert into th_task (summary, detail, assignee_user_id
      )
    values (#{summary,jdbcType=VARCHAR}, #{detail,jdbcType=VARCHAR}, #{assigneeUserId,jdbcType=INTEGER}
      )
    <selectKey resultType="java.lang.Integer" keyProperty="id" >
      SELECT LAST_INSERT_ID()
    </selectKey>
 */
    @Insert("insert into th_task (summary, detail, assignee_user_id) values (#{summary}, #{detail}, #{assigneeUserId})")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=int.class)
    int insert(Task task);
    
    @Insert("insert into th_entry (title) values (#{title})")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=int.class)
    int insertEntry(Entry entry);
      
}
