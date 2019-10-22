package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.PeopleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link People} and its DTO {@link PeopleDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PeopleMapper extends EntityMapper<PeopleDTO, People> {


    @Mapping(target = "cart", ignore = true)
    @Mapping(target = "wishlist", ignore = true)
    @Mapping(target = "compare", ignore = true)
    People toEntity(PeopleDTO peopleDTO);

    default People fromId(Long id) {
        if (id == null) {
            return null;
        }
        People people = new People();
        people.setId(id);
        return people;
    }
}
