package com.resource.server.repository;
import com.resource.server.domain.ReviewLines;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ReviewLines entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReviewLinesRepository extends JpaRepository<ReviewLines, Long>, JpaSpecificationExecutor<ReviewLines> {

}
