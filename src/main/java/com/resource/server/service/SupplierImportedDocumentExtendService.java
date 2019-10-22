package com.resource.server.service;

import com.resource.server.service.dto.SupplierImportedDocumentDTO;

import java.util.Optional;

public interface SupplierImportedDocumentExtendService {
    Optional<SupplierImportedDocumentDTO> getOneByUploadTransactionId(Long transactionId);
}
