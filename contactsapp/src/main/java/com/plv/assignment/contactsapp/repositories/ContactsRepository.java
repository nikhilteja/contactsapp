package com.plv.assignment.contactsapp.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.plv.assignment.contactsapp.models.Contact;

@Repository
public interface ContactsRepository extends JpaRepository<Contact, Long> {
	
	public List<Contact> findByEmail(String email, Pageable pageable);
	public List<Contact> findByName(String name, Pageable pageable);
	
}
