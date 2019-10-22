package com.resource.server.repository;

import com.resource.server.domain.UploadTransactions;

import java.util.List;

public interface UploadTransactionsExtendRepository extends UploadTransactionsRepository {
    List<UploadTransactions> findAllBySupplierId(Long supplierId);
}
