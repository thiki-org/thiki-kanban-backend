package org.thiki.kanban.entry;

import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.common.Sequence;
import org.thiki.kanban.foundation.exception.ResourceNotFoundException;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xubitao on 04/26/16.
 */
@Service
public class EntriesService {

    @Resource
    private EntriesPersistence entriesPersistence;

    private List<Entry> entries;

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    public Entry create(Integer reporterUserId, final Entry entry) {
        entry.setReporter(reporterUserId);
        entriesPersistence.create(entry);
        return entry;
    }

    public Entry findById(String id) {
        return entriesPersistence.findById(id);
    }

    public List<Entry> loadAll() {
        return entriesPersistence.loadAll();
    }

    public Entry update(Entry entry) {
        entriesPersistence.update(entry);
        return entriesPersistence.findById(entry.getId());
    }

    public int deleteById(String id) {
        Entry entryToDelete = entriesPersistence.findById(id);
        if (entryToDelete == null) {
            throw new ResourceNotFoundException("entry[" + id + "] is not found.");
        }
        return entriesPersistence.deleteById(id);
    }
}
