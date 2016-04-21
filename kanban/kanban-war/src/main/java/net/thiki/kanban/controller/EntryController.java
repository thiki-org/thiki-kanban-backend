package net.thiki.kanban.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.thiki.core.endpoint.RestfulBaseService;
import net.thiki.kanban.domain.entry.Entry;
import net.thiki.service.thiki.EntryService;

/**
 * 
 * @author joeaniu
 *
 */
@RestController
@RequestMapping( value = "/v1" )
public class EntryController extends RestfulBaseService{
    
    @Autowired
    private EntryService entryService;
    
	/**
	 * 所有的entry
     * @return
     */
    @RequestMapping(value = "entries", method = RequestMethod.GET)
    public List<?> list(HttpServletRequest httpServletRequest, @RequestHeader Map<String, String> header) {
        List<Entry> entryList = entryService.findAll();
        return super.returnJson(entryList);
    }
    
    @RequestMapping(value="entry", method = RequestMethod.POST)
    public ResponseEntity<?> add(@RequestBody Entry input, 
             @RequestHeader Map<String, String> header){
        Entry newEntry = new Entry(input);
        entryService.addEntry(newEntry);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
