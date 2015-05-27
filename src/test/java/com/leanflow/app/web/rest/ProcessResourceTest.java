package com.leanflow.app.web.rest;

import com.leanflow.app.Application;
import com.leanflow.app.domain.Process;
import com.leanflow.app.repository.ProcessRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ProcessResource REST controller.
 *
 * @see ProcessResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ProcessResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_TITLE = "SAMPLE_TEXT";
    private static final String UPDATED_TITLE = "UPDATED_TEXT";
    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";

    private static final DateTime DEFAULT_CREATION_TIME = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_CREATION_TIME = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_CREATION_TIME_STR = dateTimeFormatter.print(DEFAULT_CREATION_TIME);

    @Inject
    private ProcessRepository processRepository;

    private MockMvc restProcessMockMvc;

    private Process process;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProcessResource processResource = new ProcessResource();
        ReflectionTestUtils.setField(processResource, "processRepository", processRepository);
        this.restProcessMockMvc = MockMvcBuilders.standaloneSetup(processResource).build();
    }

    @Before
    public void initTest() {
        process = new Process();
        process.setName(DEFAULT_NAME);
        process.setTitle(DEFAULT_TITLE);
        process.setDescription(DEFAULT_DESCRIPTION);
        process.setCreationTime(DEFAULT_CREATION_TIME);
    }

    @Test
    @Transactional
    public void createProcess() throws Exception {
        int databaseSizeBeforeCreate = processRepository.findAll().size();

        // Create the Process
        restProcessMockMvc.perform(post("/api/process")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(process)))
                .andExpect(status().isCreated());

        // Validate the Process in the database
        List<Process> process = processRepository.findAll();
        assertThat(process).hasSize(databaseSizeBeforeCreate + 1);
        Process testProcess = process.get(process.size() - 1);
        assertThat(testProcess.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProcess.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testProcess.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProcess.getCreationTime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_CREATION_TIME);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(processRepository.findAll()).hasSize(0);
        // set the field null
        process.setName(null);

        // Create the Process, which fails.
        restProcessMockMvc.perform(post("/api/process")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(process)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Process> process = processRepository.findAll();
        assertThat(process).hasSize(0);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(processRepository.findAll()).hasSize(0);
        // set the field null
        process.setTitle(null);

        // Create the Process, which fails.
        restProcessMockMvc.perform(post("/api/process")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(process)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Process> process = processRepository.findAll();
        assertThat(process).hasSize(0);
    }

    @Test
    @Transactional
    public void checkCreationTimeIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(processRepository.findAll()).hasSize(0);
        // set the field null
        process.setCreationTime(null);

        // Create the Process, which fails.
        restProcessMockMvc.perform(post("/api/process")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(process)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Process> process = processRepository.findAll();
        assertThat(process).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllProcesss() throws Exception {
        // Initialize the database
        processRepository.saveAndFlush(process);

        // Get all the process
        restProcessMockMvc.perform(get("/api/process"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(process.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].creationTime").value(hasItem(DEFAULT_CREATION_TIME_STR)));
    }

    @Test
    @Transactional
    public void getProcess() throws Exception {
        // Initialize the database
        processRepository.saveAndFlush(process);

        // Get the process
        restProcessMockMvc.perform(get("/api/process/{id}", process.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(process.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.creationTime").value(DEFAULT_CREATION_TIME_STR));
    }

    @Test
    @Transactional
    public void getNonExistingProcess() throws Exception {
        // Get the process
        restProcessMockMvc.perform(get("/api/process/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProcess() throws Exception {
        // Initialize the database
        processRepository.saveAndFlush(process);

		int databaseSizeBeforeUpdate = processRepository.findAll().size();

        // Update the process
        process.setName(UPDATED_NAME);
        process.setTitle(UPDATED_TITLE);
        process.setDescription(UPDATED_DESCRIPTION);
        process.setCreationTime(UPDATED_CREATION_TIME);
        restProcessMockMvc.perform(put("/api/process")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(process)))
                .andExpect(status().isOk());

        // Validate the Process in the database
        List<Process> process = processRepository.findAll();
        assertThat(process).hasSize(databaseSizeBeforeUpdate);
        Process testProcess = process.get(process.size() - 1);
        assertThat(testProcess.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProcess.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testProcess.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProcess.getCreationTime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_CREATION_TIME);
    }

    @Test
    @Transactional
    public void deleteProcess() throws Exception {
        // Initialize the database
        processRepository.saveAndFlush(process);

		int databaseSizeBeforeDelete = processRepository.findAll().size();

        // Get the process
        restProcessMockMvc.perform(delete("/api/process/{id}", process.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Process> process = processRepository.findAll();
        assertThat(process).hasSize(databaseSizeBeforeDelete - 1);
    }
}
