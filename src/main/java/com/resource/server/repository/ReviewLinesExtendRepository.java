package com.resource.server.repository;

import com.resource.server.domain.ReviewLines;

import java.util.List;

public interface ReviewLinesExtendRepository extends ReviewLinesRepository {
    List<ReviewLines> findAllByStockItemId(Long stockItemId);
}
