package com.leanflow.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.leanflow.app.domain.Process;
import com.leanflow.app.repository.ProcessRepository;
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
 * REST controller for managing Process.
 */
@RestController
@RequestMapping("/api")
public class ProcessResource {

    private final Logger log = LoggerFactory.getLogger(ProcessResource.class);

    @Inject
    private ProcessRepository processRepository;

    /**
     * POST  /processs -> Create a new process.
     */
    @RequestMapping(value = "/processs",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Process process) throws URISyntaxException {
        log.debug("REST request to save Process : {}", process);
        if (process.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new process cannot already have an ID").build();
        }
        processRepository.save(process);
        return ResponseEntity.created(new URI("/api/processs/" + process.getId())).build();
    }

    /**
     * PUT  /processs -> Updates an existing process.
     */
    @RequestMapping(value = "/processs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Process process) throws URISyntaxException {
        log.debug("REST request to update Process : {}", process);
        if (process.getId() == null) {
            return create(process);
        }
        processRepository.save(process);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /processs -> get all the processs.
     */
    @RequestMapping(value = "/processs",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Process> getAll() {
        log.debug("REST request to get all Processs");
        return processRepository.findAll();
    }

    /**
     * GET  /processs/:id -> get the "id" process.
     */
    @RequestMapping(value = "/processs/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Process> get(@PathVariable Long id) {
        log.debug("REST request to get Process : {}", id);
        return Optional.ofNullable(processRepository.findOne(id))
            .map(process -> new ResponseEntity<Process>(
                process,
                HttpStatus.OK))
            .orElse(new ResponseEntity<Process>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /processs/:id -> delete the "id" process.
     */
    @RequestMapping(value = "/processs/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Process : {}", id);
        processRepository.delete(id);
    }
}
