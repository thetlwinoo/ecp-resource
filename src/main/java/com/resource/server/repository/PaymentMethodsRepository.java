package com.resource.server.repository;
import com.resource.server.domain.PaymentMethods;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PaymentMethods entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentMethodsRepository extends JpaRepository<PaymentMethods, Long>, JpaSpecificationExecutor<PaymentMethods> {

}
