package com.leanflow.app.repository;

import com.leanflow.app.domain.ProcessStep;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ProcessStep entity.
 */
public interface ProcessStepRepository extends JpaRepository<ProcessStep,Long> {

}
