package com.resource.server.repository;
import com.resource.server.domain.ShoppingCartItems;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ShoppingCartItems entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShoppingCartItemsRepository extends JpaRepository<ShoppingCartItems, Long>, JpaSpecificationExecutor<ShoppingCartItems> {

}
