package net.thiki.service.thiki;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.thiki.kanban.domain.entry.Entry;
import net.thiki.kanban.domain.entry.EntryRepo;

@Service
public class EntryService {

    @Autowired
    private EntryRepo entryRepo;
    
	public List<Entry> findAll() {
		
		List<Entry> list = new ArrayList<>();
		Entry entry = new Entry();
		entry.setTitle("锻炼");
		list.add(entry);
		
		return list;
	}

	public Entry find(String id) {
		Entry entry = new Entry();
		entry.setTitle("锻炼");
		return entry;
	}

    public void addEntry(Entry newEntry) {
        entryRepo.saveEntry(newEntry);
    }

}
