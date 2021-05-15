package com.be.bedemo.mapper;

import com.be.bedemo.mapper.qualifier.CalculateAnnualGrossQualifier;
import com.be.bedemo.model.Contact;
import com.be.bedemo.model.ContactRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { CalculateAnnualGrossQualifier.class })
public interface IContactMapper extends IMapper<ContactRequest, Contact> {

    @Override
    @Mapping(source = "contact", target = "mail")
    @Mapping(source = "averageGross", target = "annualGross", qualifiedByName = {"CalculateAnnualGrossQualifier", "CalculateAnnualGross"})
    Contact createFrom(ContactRequest source);
}
