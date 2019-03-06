package com.plv.assignment.contactsapp.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.plv.assignment.contactsapp.exceptions.ContactsServiceException;
import com.plv.assignment.contactsapp.models.Contact;
import com.plv.assignment.contactsapp.repositories.ContactsRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ContactsServiceCRUDUnitTests {
	
	private static ContactsService contactsService;
	private static ContactsRepository mockContactsRepository;
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@BeforeClass
	public static void init() {
		mockContactsRepository = Mockito.mock(ContactsRepository.class);
		contactsService = new ContactsService(mockContactsRepository);
	}
	
	@After
	public void clearMockInvocations() {
		Mockito.clearInvocations(mockContactsRepository);
	}
	
	@Test
	public void creatingNewContactShouldReturnTheSame() {
		Contact contact = new Contact(1L);
		
		Mockito.when(mockContactsRepository.save(contact)).thenReturn(contact);
		
		Contact createdContact = contactsService.createNewContact(contact);
		
		Mockito.verify(mockContactsRepository, Mockito.times(1)).save(contact);

		assertThat(createdContact).isEqualTo(contact);
	}
	
	@Test
	public void gettingContactWithNullIdShouldThrowContactsServiceException() throws ContactsServiceException {
		Contact contact = new Contact();
		expectedException.expect(ContactsServiceException.class);
		contactsService.getContact(contact);
	}
	
	@Test
	public void gettingContactShouldReturnTheSame() throws ContactsServiceException {
		Contact contact = new Contact(2L);
		
		Mockito.when(mockContactsRepository.findById(contact.getId())).thenReturn(Optional.of(contact));
		Contact createdContact = contactsService.getContact(contact);
		assertThat(createdContact).isEqualTo(contact);
	}
	
	@Test
	public void updatingContactShouldReturnTheSame() throws ContactsServiceException {
		Contact contact = new Contact(3L);
		
		Mockito.when(mockContactsRepository.findById(contact.getId())).thenReturn(Optional.of(contact));
		Mockito.when(mockContactsRepository.save(contact)).thenReturn(contact);
		
		Contact createdContact = contactsService.updateExistingContact(contact);
		
		Mockito.verify(mockContactsRepository, Mockito.times(1)).save(contact);

		assertThat(createdContact).isEqualTo(contact);
	}
	
	@Test
	public void updatingNonExistentContactShouldThrowContactsServiceException() throws ContactsServiceException {
		Contact nonExistentContact = new Contact();
		
		Mockito.when(mockContactsRepository.findById(nonExistentContact.getId())).thenReturn(Optional.empty());
		
		expectedException.expect(ContactsServiceException.class);
		contactsService.updateExistingContact(nonExistentContact);
	}
	
	@Test
	public void deletingNonExistentContactShouldThrowContactsServiceException() throws ContactsServiceException {
		Contact nonExistentContact = new Contact();
		
		Mockito.when(mockContactsRepository.findById(nonExistentContact.getId())).thenReturn(Optional.empty());
		
		expectedException.expect(ContactsServiceException.class);
		contactsService.deleteContact(nonExistentContact);
	}
}
