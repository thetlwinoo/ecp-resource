package com.resource.server.repository;

import com.resource.server.domain.SupplierImportedDocument;

import java.util.Optional;

public interface SupplierImportedDocumentExtendRepository extends SupplierImportedDocumentRepository {
    Optional<SupplierImportedDocument> findFirstByUploadTransactionId(Long id);
}
