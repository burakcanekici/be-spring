package com.be.bedemo.service;

import com.be.bedemo.mapper.IContactMapper;
import com.be.bedemo.model.Contact;
import com.be.bedemo.model.ContactRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AddressBookService {

    private final IContactMapper iContactMapper;

    public Map<String, Contact> addressBook = new HashMap<>();

    public AddressBookService(IContactMapper iContactMapper) {
        this.iContactMapper = iContactMapper;
    }

    public Contact getContactImpl(String id){
        return addressBook.get(id);
    }

    public List<Contact> getAllContactsImpl() {
        return new ArrayList<Contact>(addressBook.values());
    }

    public Contact addContactImpl(ContactRequest contactRequest){
        Contact contact = iContactMapper.createFrom(contactRequest);
        addressBook.put(contact.getUserName(), contact);
        return contact;
    }
}
