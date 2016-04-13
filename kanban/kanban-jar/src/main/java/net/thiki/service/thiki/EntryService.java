package net.thiki.service.thiki;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import net.thiki.kanban.domain.entry.Entry;

@Service
public class EntryService {

	public List<Entry> findAll() {
		
		List<Entry> list = new ArrayList<>();
		Entry Entry = new Entry();
		Entry.setContent("跑15公里");
		Entry.setTitle("锻炼");
		list.add(Entry);
		
		return list;
	}

	public Entry find(String id) {
		Entry Entry = new Entry();
		Entry.setContent("跑15公里");
		Entry.setTitle("锻炼");
		return Entry;
	}

}
