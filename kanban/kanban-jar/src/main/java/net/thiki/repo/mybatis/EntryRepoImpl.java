package net.thiki.repo.mybatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.thiki.kanban.domain.entry.Entry;
import net.thiki.kanban.domain.entry.EntryExample;
import net.thiki.kanban.domain.entry.EntryRepo;
import net.thiki.repo.mybatis.mappers.EntryMapper;

@Repository
public class EntryRepoImpl implements EntryRepo {
    
    @Autowired
    private EntryMapper entryMapper;

    @Override
    public void saveEntry(Entry entry) {
        if (entry.getId() == null){
            entryMapper.insert(entry); 
        }else{
            EntryExample example = new EntryExample();
            example.createCriteria().andIdEqualTo(entry.getId());
            entryMapper.updateByExample(entry, example);
        }
        
    }

}
