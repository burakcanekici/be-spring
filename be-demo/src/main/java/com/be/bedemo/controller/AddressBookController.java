package com.be.bedemo.controller;

import com.be.bedemo.model.Contact;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/addressbook")
public class AddressBookController {

    public Map<String, Contact> addressBook = new HashMap<>();
    public int counter = 0;

    @GetMapping("/{id}")
    public Contact getContact(@PathVariable String id){
        return addressBook.get(id);
    }

    @GetMapping("")
    public List<Contact> getAllContacts(){
        return new ArrayList<Contact>(addressBook.values());
    }

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public Contact addContact(@RequestBody Contact contact){
        addressBook.put(String.valueOf(counter), contact);
        counter++;
        return contact;
    }

}
