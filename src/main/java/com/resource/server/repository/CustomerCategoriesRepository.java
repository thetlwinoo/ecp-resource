package com.resource.server.repository;
import com.resource.server.domain.CustomerCategories;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CustomerCategories entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerCategoriesRepository extends JpaRepository<CustomerCategories, Long>, JpaSpecificationExecutor<CustomerCategories> {

}
