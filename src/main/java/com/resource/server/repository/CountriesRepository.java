package com.resource.server.repository;
import com.resource.server.domain.Countries;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Countries entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CountriesRepository extends JpaRepository<Countries, Long>, JpaSpecificationExecutor<Countries> {

}
