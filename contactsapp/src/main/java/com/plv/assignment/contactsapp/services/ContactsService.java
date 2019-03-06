package com.plv.assignment.contactsapp.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.plv.assignment.contactsapp.exceptions.ContactsServiceException;
import com.plv.assignment.contactsapp.models.Contact;
import com.plv.assignment.contactsapp.repositories.ContactsRepository;

@Service
public class ContactsService {

	private ContactsRepository contactsRepository;
	
	@Autowired
	public ContactsService(ContactsRepository contactsRepository) {
		this.contactsRepository = contactsRepository;
	}
	
	public Contact createNewContact(Contact contact) {
		// validation goes here if any.
		return contactsRepository.save(contact);
	}
	
	public Contact getContact(Contact contact) throws ContactsServiceException {
		try {
			return contactsRepository.findById(contact.getId()).get();
		}
		catch(NoSuchElementException ex) {
			throw new ContactsServiceException("Contact with id " + contact.getId() + " doesn't exist.");
		}
	}
	
	public Contact updateExistingContact(Contact contact) throws ContactsServiceException {
		try {
			contactsRepository.findById(contact.getId()).get();
		}
		catch(NoSuchElementException ex) {
			throw new ContactsServiceException("Contact with id " + contact.getId() + " doesn't exist.");
		}
		return contactsRepository.save(contact);
	}
	
	public void deleteContact(Contact contact) throws ContactsServiceException {
		try {
			contactsRepository.findById(contact.getId()).get();
		}
		catch(NoSuchElementException ex) {
			throw new ContactsServiceException("Contact with id " + contact.getId() + " doesn't exist.");
		}
		contactsRepository.delete(contact);
	}
	
	public List<Contact> searchByEmail(String email, PageRequest pageRequest) {
		return contactsRepository.findByEmail(email, pageRequest);
	}

	public List<Contact> searchByName(String name, PageRequest pageRequest) {
		return contactsRepository.findByName(name, pageRequest);
	}
}
