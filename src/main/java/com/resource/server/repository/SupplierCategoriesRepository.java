package com.resource.server.repository;
import com.resource.server.domain.SupplierCategories;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SupplierCategories entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SupplierCategoriesRepository extends JpaRepository<SupplierCategories, Long>, JpaSpecificationExecutor<SupplierCategories> {

}
