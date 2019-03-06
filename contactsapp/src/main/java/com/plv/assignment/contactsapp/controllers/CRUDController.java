package com.plv.assignment.contactsapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.plv.assignment.contactsapp.exceptions.ContactsServiceException;
import com.plv.assignment.contactsapp.models.Contact;
import com.plv.assignment.contactsapp.services.ContactsService;

@RestController
public class CRUDController {

	@Autowired
	private ContactsService contactsService;
	
	@RequestMapping(value = "/contact", method = RequestMethod.POST)
	public ResponseEntity<Void> createContact(Contact contact) {
		contactsService.createNewContact(contact);
		return ResponseEntity.ok().build();
	}
	
	/**
	 * Spring MVC automatically fetches and populates the contact object based on the contact id from the request.
	 */
	@RequestMapping(value = "/contact", method = RequestMethod.GET)
	public ResponseEntity<Contact> getContact(@ModelAttribute Contact contact) throws ContactsServiceException {
		contact = contactsService.getContact(contact);
		return ResponseEntity.status(HttpStatus.CREATED).body(contact);
	}
	
	/**
	 * Spring MVC automatically fetches and populates the contact object based on the contact id from the request.
	 */
	@RequestMapping(value = "/contact", method = RequestMethod.PUT)
	public ResponseEntity<Contact> updateContact(Contact contact) throws ContactsServiceException {
		contact = contactsService.updateExistingContact(contact);
		return ResponseEntity.ok(contact);
	}
	
	/**
	 * Spring MVC automatically fetches and populates the contact object based on the contact id from the request.
	 */
	@RequestMapping(value = "/contact", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteContact(@ModelAttribute Contact contact) throws ContactsServiceException {
		contactsService.deleteContact(contact);
		return ResponseEntity.ok().build(); 
	}
}
