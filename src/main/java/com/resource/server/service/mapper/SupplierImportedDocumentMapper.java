package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.SupplierImportedDocumentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SupplierImportedDocument} and its DTO {@link SupplierImportedDocumentDTO}.
 */
@Mapper(componentModel = "spring", uses = {UploadTransactionsMapper.class})
public interface SupplierImportedDocumentMapper extends EntityMapper<SupplierImportedDocumentDTO, SupplierImportedDocument> {

    @Mapping(source = "uploadTransaction.id", target = "uploadTransactionId")
    SupplierImportedDocumentDTO toDto(SupplierImportedDocument supplierImportedDocument);

    @Mapping(source = "uploadTransactionId", target = "uploadTransaction")
    SupplierImportedDocument toEntity(SupplierImportedDocumentDTO supplierImportedDocumentDTO);

    default SupplierImportedDocument fromId(Long id) {
        if (id == null) {
            return null;
        }
        SupplierImportedDocument supplierImportedDocument = new SupplierImportedDocument();
        supplierImportedDocument.setId(id);
        return supplierImportedDocument;
    }
}
