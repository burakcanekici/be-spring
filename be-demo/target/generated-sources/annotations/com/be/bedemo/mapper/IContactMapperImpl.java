package com.be.bedemo.mapper;

import com.be.bedemo.mapper.qualifier.CalculateAnnualGrossQualifier;
import com.be.bedemo.model.Contact;
import com.be.bedemo.model.ContactRequest;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-05-15T15:26:31+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 16 (AdoptOpenJDK)"
)
@Component
public class IContactMapperImpl implements IContactMapper {

    @Override
    public Contact createFrom(ContactRequest source) {
        if ( source == null ) {
            return null;
        }

        Contact contact = new Contact();

        contact.setMail( source.getContact() );
        contact.setAnnualGross( CalculateAnnualGrossQualifier.calculateAnnualGross( source.getAverageGross() ) );
        contact.setName( source.getName() );
        contact.setSurname( source.getSurname() );
        contact.setUserName( source.getUserName() );

        return contact;
    }
}
