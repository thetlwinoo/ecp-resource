package com.resource.server.repository;
import com.resource.server.domain.UploadTransactions;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UploadTransactions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UploadTransactionsRepository extends JpaRepository<UploadTransactions, Long>, JpaSpecificationExecutor<UploadTransactions> {

}
