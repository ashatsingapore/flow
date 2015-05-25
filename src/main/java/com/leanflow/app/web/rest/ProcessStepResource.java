package com.leanflow.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.leanflow.app.domain.ProcessStep;
import com.leanflow.app.repository.ProcessStepRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ProcessStep.
 */
@RestController
@RequestMapping("/api")
public class ProcessStepResource {

    private final Logger log = LoggerFactory.getLogger(ProcessStepResource.class);

    @Inject
    private ProcessStepRepository processStepRepository;

    /**
     * POST  /processSteps -> Create a new processStep.
     */
    @RequestMapping(value = "/processSteps",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody ProcessStep processStep) throws URISyntaxException {
        log.debug("REST request to save ProcessStep : {}", processStep);
        if (processStep.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new processStep cannot already have an ID").build();
        }
        processStepRepository.save(processStep);
        return ResponseEntity.created(new URI("/api/processSteps/" + processStep.getId())).build();
    }

    /**
     * PUT  /processSteps -> Updates an existing processStep.
     */
    @RequestMapping(value = "/processSteps",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody ProcessStep processStep) throws URISyntaxException {
        log.debug("REST request to update ProcessStep : {}", processStep);
        if (processStep.getId() == null) {
            return create(processStep);
        }
        processStepRepository.save(processStep);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /processSteps -> get all the processSteps.
     */
    @RequestMapping(value = "/processSteps",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ProcessStep> getAll() {
        log.debug("REST request to get all ProcessSteps");
        return processStepRepository.findAll();
    }

    /**
     * GET  /processSteps/:id -> get the "id" processStep.
     */
    @RequestMapping(value = "/processSteps/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProcessStep> get(@PathVariable Long id) {
        log.debug("REST request to get ProcessStep : {}", id);
        return Optional.ofNullable(processStepRepository.findOne(id))
            .map(processStep -> new ResponseEntity<>(
                processStep,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /processSteps/:id -> delete the "id" processStep.
     */
    @RequestMapping(value = "/processSteps/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete ProcessStep : {}", id);
        processStepRepository.delete(id);
    }
}
