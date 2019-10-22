package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.BuyingGroupsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link BuyingGroups} and its DTO {@link BuyingGroupsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BuyingGroupsMapper extends EntityMapper<BuyingGroupsDTO, BuyingGroups> {



    default BuyingGroups fromId(Long id) {
        if (id == null) {
            return null;
        }
        BuyingGroups buyingGroups = new BuyingGroups();
        buyingGroups.setId(id);
        return buyingGroups;
    }
}
