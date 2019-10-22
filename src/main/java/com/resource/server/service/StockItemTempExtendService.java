package com.resource.server.service;

import com.resource.server.service.dto.StockItemTempDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StockItemTempExtendService {
    Page<StockItemTempDTO> getAllStockItemTempByTransactionId(Long transactionId, Pageable pageable);
}
