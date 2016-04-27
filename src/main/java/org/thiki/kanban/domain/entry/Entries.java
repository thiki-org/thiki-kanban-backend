package org.thiki.kanban.domain.entry;

import org.springframework.stereotype.Service;
import org.thiki.kanban.persistence.entry.EntriesPersistence;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by xubitao on 04/26/16.
 */
@Service
public class Entries {
    @Resource
    private EntriesPersistence entriesPersistence;
    private List<Entry> entries;

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    public Entry create(final Entry entry) throws Exception {
        entriesPersistence.create(entry);
        return entriesPersistence.findById(entry.getId());
    }

    public Entry findById(Integer id) throws SQLException {
        return entriesPersistence.findById(id);
    }

    public Entries loadAll() throws SQLException {
        Entries entries = new Entries();
        entries.setEntries(entriesPersistence.loadAll());

        return entries;
    }

    public Entry update(Entry entry) throws Exception {
        entriesPersistence.update(entry);
        return entriesPersistence.findById(entry.getId());
    }

    public int deleteById(Integer id) throws SQLException {
        return entriesPersistence.deleteById(id);
    }
}
