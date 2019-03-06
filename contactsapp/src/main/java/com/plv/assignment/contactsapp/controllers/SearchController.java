package com.plv.assignment.contactsapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.plv.assignment.contactsapp.models.Contact;
import com.plv.assignment.contactsapp.services.ContactsService;

@RestController
public class SearchController {
	
	@Autowired
	private ContactsService contactsService;
	
	private static final int PAGE_SIZE = 10;
	
	/**
	 * 
	 * @param name
	 * @param email
	 * @param page - starts with 0.
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/contact/search", method = RequestMethod.GET)
	public ResponseEntity<List<Contact>> search(@RequestParam(required = false) String name, @RequestParam(required = false) String email, @RequestParam int page) {
		
		List<Contact> searchResults = null;
		
		if(name == null && email == null) {
			return new ResponseEntity("Either name or email is required.", HttpStatus.BAD_REQUEST);
		}
		
		else if(name != null) {
			searchResults = contactsService.searchByName(name, PageRequest.of(page, PAGE_SIZE));
		}
		
		else if(email != null) {
			searchResults = contactsService.searchByEmail(email, PageRequest.of(page, PAGE_SIZE));
		}
		return ResponseEntity.ok(searchResults);
	}
}
