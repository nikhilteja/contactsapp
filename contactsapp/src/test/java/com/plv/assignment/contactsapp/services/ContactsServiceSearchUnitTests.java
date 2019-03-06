package com.plv.assignment.contactsapp.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.plv.assignment.contactsapp.models.Contact;
import com.plv.assignment.contactsapp.repositories.ContactsRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ContactsServiceSearchUnitTests {
	
	private static ContactsService contactsService;
	private static ContactsRepository mockContactsRepository;
	
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
	public void searchShouldReturnMockedData() {
		List<Contact> mockedContactsList = new ArrayList<>();
		
		for(long i=0; i<5; i++) {
			mockedContactsList.add(new Contact(i));
		}
		
		Mockito.when(mockContactsRepository.findByEmail(Mockito.anyString(), Mockito.any())).thenReturn(mockedContactsList);
		Mockito.when(mockContactsRepository.findByName(Mockito.anyString(), Mockito.any())).thenReturn(mockedContactsList);
		List<Contact> searchByEmailResult = contactsService.searchByEmail("testEmail", PageRequest.of(1, 5));
		List<Contact> searchByNameResult = contactsService.searchByName("testEmail", PageRequest.of(1, 5));
		
		for(int i=0; i<5; i++) {
			Mockito.verify(mockContactsRepository, Mockito.times(1)).findByEmail(Mockito.anyString(), Mockito.any());
			assertThat(searchByEmailResult.get(i)).isEqualTo(mockedContactsList.get(i));
			assertThat(searchByNameResult.get(i)).isEqualTo(mockedContactsList.get(i));
		}
	}
}
