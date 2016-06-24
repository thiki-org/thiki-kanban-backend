package org.thiki.kanban.entry;

import com.google.common.collect.ImmutableMap;
import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.exception.ResourceNotFoundException;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by xubitao on 04/26/16.
 */
@Service
public class EntriesService {

    @Resource
    private EntriesPersistence entriesPersistence;

    public Entry create(Integer reporterUserId, final Entry entry) {
        entry.setReporter(reporterUserId);
        entriesPersistence.create(entry);
        return entriesPersistence.findById(entry.getId());
    }

    public Entry findById(String id) {
        return entriesPersistence.findById(id);
    }

    public List<Entry> loadByBoardId(String boardId) {
        return entriesPersistence.loadByBoardId(boardId);
    }

    public Entry update(Entry entry) {
        Entry foundEntry = checkingWhetherEntryIsExists(entry.getId());
        entriesPersistence.update(entry);
        if (foundEntry.getOrderNumber() != entry.getOrderNumber()) {
            int increment = entry.getOrderNumber() > foundEntry.getOrderNumber() ? 1 : 0;
            Map<String, Object> resort = ImmutableMap.<String, Object>builder()
                    .put("boardId", entry.getBoardId())
                    .put("originOrderNumber", foundEntry.getOrderNumber())
                    .put("currentOrderNumber", entry.getOrderNumber())
                    .put("increment", increment)
                    .put("id", entry.getId())
                    .build();
            entriesPersistence.resort(resort);
        }
        return entriesPersistence.findById(entry.getId());
    }

    private Entry checkingWhetherEntryIsExists(String id) {
        Entry foundEntry = entriesPersistence.findById(id);
        if (foundEntry == null) {
            throw new ResourceNotFoundException("entry[" + id + "] is not found.");
        }
        return foundEntry;
    }

    public int deleteById(String id) {
        checkingWhetherEntryIsExists(id);
        return entriesPersistence.deleteById(id);
    }
}
