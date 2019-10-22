package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.UploadActionTypesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UploadActionTypes} and its DTO {@link UploadActionTypesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UploadActionTypesMapper extends EntityMapper<UploadActionTypesDTO, UploadActionTypes> {



    default UploadActionTypes fromId(Long id) {
        if (id == null) {
            return null;
        }
        UploadActionTypes uploadActionTypes = new UploadActionTypes();
        uploadActionTypes.setId(id);
        return uploadActionTypes;
    }
}
