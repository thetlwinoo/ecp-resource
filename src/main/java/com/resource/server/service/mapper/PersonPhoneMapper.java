package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.PersonPhoneDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PersonPhone} and its DTO {@link PersonPhoneDTO}.
 */
@Mapper(componentModel = "spring", uses = {PeopleMapper.class, PhoneNumberTypeMapper.class})
public interface PersonPhoneMapper extends EntityMapper<PersonPhoneDTO, PersonPhone> {

    @Mapping(source = "person.id", target = "personId")
    @Mapping(source = "person.fullName", target = "personFullName")
    @Mapping(source = "phoneNumberType.id", target = "phoneNumberTypeId")
    @Mapping(source = "phoneNumberType.phoneNumberTypeName", target = "phoneNumberTypePhoneNumberTypeName")
    PersonPhoneDTO toDto(PersonPhone personPhone);

    @Mapping(source = "personId", target = "person")
    @Mapping(source = "phoneNumberTypeId", target = "phoneNumberType")
    PersonPhone toEntity(PersonPhoneDTO personPhoneDTO);

    default PersonPhone fromId(Long id) {
        if (id == null) {
            return null;
        }
        PersonPhone personPhone = new PersonPhone();
        personPhone.setId(id);
        return personPhone;
    }
}
