package com.resource.server.repository;
import com.resource.server.domain.ProductBrand;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProductBrand entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductBrandRepository extends JpaRepository<ProductBrand, Long>, JpaSpecificationExecutor<ProductBrand> {

}
