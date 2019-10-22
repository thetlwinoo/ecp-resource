package com.resource.server.service;

import com.resource.server.service.dto.UploadTransactionsDTO;

import java.security.Principal;
import java.util.List;

public interface UploadTransactionsExtendService {
    void clearStockItemTemp(Long transactionId);
    List<UploadTransactionsDTO> findAll(Principal principal);
}
