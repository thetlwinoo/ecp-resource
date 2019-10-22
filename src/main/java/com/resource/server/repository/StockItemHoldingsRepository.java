package com.resource.server.repository;
import com.resource.server.domain.StockItemHoldings;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StockItemHoldings entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StockItemHoldingsRepository extends JpaRepository<StockItemHoldings, Long>, JpaSpecificationExecutor<StockItemHoldings> {

}
