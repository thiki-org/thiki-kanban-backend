package net.thiki.repo.mybatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.thiki.kanban.domain.entry.Entry;
import net.thiki.kanban.domain.entry.EntryExample;
import net.thiki.kanban.domain.entry.EntryRepo;
import net.thiki.kanban.domain.entry.Task;
import net.thiki.kanban.domain.entry.TaskExample;
import net.thiki.repo.mybatis.mappers.EntryMapper;
import net.thiki.repo.mybatis.mappers.TaskMapper;
import net.thiki.repo.mybatis.mappers.TaskMapperExt;

@Repository
public class EntryRepoImpl implements EntryRepo {
    
    @Autowired
    private EntryMapper entryMapper;
    
    @Autowired
    private TaskMapper taskMapper;
    
    @Autowired
    private TaskMapperExt taskMapperExt;

    @Override
    public void saveEntry(Entry entry) {
        if (entry.getId() == null){
            entryMapper.insert(entry); 
//            taskMapperExt.insertEntry(entry);
        }else{
            EntryExample example = new EntryExample();
            example.createCriteria().andIdEqualTo(entry.getId());
            entryMapper.updateByExample(entry, example);
        }
        
    }

    @Override
    public void saveTask(Task task) {
        if (task.getId() == null){
            taskMapperExt.insert(task);
        }else{
            TaskExample example = new TaskExample();
            example.createCriteria().andIdEqualTo(task.getId());
            taskMapper.updateByExample(task, example);
        }
    }

}
