package com.leanflow.app.repository;

import com.leanflow.app.domain.Process;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Process entity.
 */
public interface ProcessRepository extends JpaRepository<Process,Long> {

}
