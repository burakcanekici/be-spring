package com.be.bedemo.controller;

import com.be.bedemo.model.Contact;
import com.be.bedemo.model.ContactRequest;
import com.be.bedemo.service.AddressBookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/addressbook")
public class AddressBookController {

    private final AddressBookService addressBookService;

    public AddressBookController(AddressBookService addressBookService) {
        this.addressBookService = addressBookService;
    }

    @GetMapping("/{id}")
    public Contact getContact(@PathVariable String id){
        return addressBookService.getContactImpl(id);
    }

    @GetMapping("")
    public List<Contact> getAllContacts(){
        return addressBookService.getAllContactsImpl();
    }

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public Contact addContact(@RequestBody ContactRequest contactRequest){
        return addressBookService.addContactImpl(contactRequest);
    }

}
