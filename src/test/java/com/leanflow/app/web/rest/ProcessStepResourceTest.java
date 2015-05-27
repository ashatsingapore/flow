package com.leanflow.app.web.rest;

import com.leanflow.app.Application;
import com.leanflow.app.domain.ProcessStep;
import com.leanflow.app.repository.ProcessStepRepository;

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
 * Test class for the ProcessStepResource REST controller.
 *
 * @see ProcessStepResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ProcessStepResourceTest {

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

    private static final DateTime DEFAULT_ACCEPTED_TIME = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_ACCEPTED_TIME = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_ACCEPTED_TIME_STR = dateTimeFormatter.print(DEFAULT_ACCEPTED_TIME);

    private static final DateTime DEFAULT_COMPLETED_TIME = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_COMPLETED_TIME = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_COMPLETED_TIME_STR = dateTimeFormatter.print(DEFAULT_COMPLETED_TIME);

    @Inject
    private ProcessStepRepository processStepRepository;

    private MockMvc restProcessStepMockMvc;

    private ProcessStep processStep;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProcessStepResource processStepResource = new ProcessStepResource();
        ReflectionTestUtils.setField(processStepResource, "processStepRepository", processStepRepository);
        this.restProcessStepMockMvc = MockMvcBuilders.standaloneSetup(processStepResource).build();
    }

    @Before
    public void initTest() {
        processStep = new ProcessStep();
        processStep.setName(DEFAULT_NAME);
        processStep.setTitle(DEFAULT_TITLE);
        processStep.setDescription(DEFAULT_DESCRIPTION);
        processStep.setCreationTime(DEFAULT_CREATION_TIME);
        processStep.setAcceptedTime(DEFAULT_ACCEPTED_TIME);
        processStep.setCompletedTime(DEFAULT_COMPLETED_TIME);
    }

    @Test
    @Transactional
    public void createProcessStep() throws Exception {
        int databaseSizeBeforeCreate = processStepRepository.findAll().size();

        // Create the ProcessStep
        restProcessStepMockMvc.perform(post("/api/processStep")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(processStep)))
                .andExpect(status().isCreated());

        // Validate the ProcessStep in the database
        List<ProcessStep> processStep = processStepRepository.findAll();
        assertThat(processStep).hasSize(databaseSizeBeforeCreate + 1);
        ProcessStep testProcessStep = processStep.get(processStep.size() - 1);
        assertThat(testProcessStep.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProcessStep.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testProcessStep.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProcessStep.getCreationTime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_CREATION_TIME);
        assertThat(testProcessStep.getAcceptedTime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_ACCEPTED_TIME);
        assertThat(testProcessStep.getCompletedTime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_COMPLETED_TIME);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(processStepRepository.findAll()).hasSize(0);
        // set the field null
        processStep.setName(null);

        // Create the ProcessStep, which fails.
        restProcessStepMockMvc.perform(post("/api/processStep")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(processStep)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<ProcessStep> processStep = processStepRepository.findAll();
        assertThat(processStep).hasSize(0);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(processStepRepository.findAll()).hasSize(0);
        // set the field null
        processStep.setTitle(null);

        // Create the ProcessStep, which fails.
        restProcessStepMockMvc.perform(post("/api/processStep")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(processStep)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<ProcessStep> processStep = processStepRepository.findAll();
        assertThat(processStep).hasSize(0);
    }

    @Test
    @Transactional
    public void checkCreationTimeIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(processStepRepository.findAll()).hasSize(0);
        // set the field null
        processStep.setCreationTime(null);

        // Create the ProcessStep, which fails.
        restProcessStepMockMvc.perform(post("/api/processStep")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(processStep)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<ProcessStep> processStep = processStepRepository.findAll();
        assertThat(processStep).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllProcessSteps() throws Exception {
        // Initialize the database
        processStepRepository.saveAndFlush(processStep);

        // Get all the processStep
        restProcessStepMockMvc.perform(get("/api/processStep"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(processStep.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].creationTime").value(hasItem(DEFAULT_CREATION_TIME_STR)))
                .andExpect(jsonPath("$.[*].acceptedTime").value(hasItem(DEFAULT_ACCEPTED_TIME_STR)))
                .andExpect(jsonPath("$.[*].completedTime").value(hasItem(DEFAULT_COMPLETED_TIME_STR)));
    }

    @Test
    @Transactional
    public void getProcessStep() throws Exception {
        // Initialize the database
        processStepRepository.saveAndFlush(processStep);

        // Get the processStep
        restProcessStepMockMvc.perform(get("/api/processStep/{id}", processStep.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(processStep.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.creationTime").value(DEFAULT_CREATION_TIME_STR))
            .andExpect(jsonPath("$.acceptedTime").value(DEFAULT_ACCEPTED_TIME_STR))
            .andExpect(jsonPath("$.completedTime").value(DEFAULT_COMPLETED_TIME_STR));
    }

    @Test
    @Transactional
    public void getNonExistingProcessStep() throws Exception {
        // Get the processStep
        restProcessStepMockMvc.perform(get("/api/processStep/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProcessStep() throws Exception {
        // Initialize the database
        processStepRepository.saveAndFlush(processStep);

		int databaseSizeBeforeUpdate = processStepRepository.findAll().size();

        // Update the processStep
        processStep.setName(UPDATED_NAME);
        processStep.setTitle(UPDATED_TITLE);
        processStep.setDescription(UPDATED_DESCRIPTION);
        processStep.setCreationTime(UPDATED_CREATION_TIME);
        processStep.setAcceptedTime(UPDATED_ACCEPTED_TIME);
        processStep.setCompletedTime(UPDATED_COMPLETED_TIME);
        restProcessStepMockMvc.perform(put("/api/processStep")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(processStep)))
                .andExpect(status().isOk());

        // Validate the ProcessStep in the database
        List<ProcessStep> processStep = processStepRepository.findAll();
        assertThat(processStep).hasSize(databaseSizeBeforeUpdate);
        ProcessStep testProcessStep = processStep.get(processStep.size() - 1);
        assertThat(testProcessStep.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProcessStep.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testProcessStep.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProcessStep.getCreationTime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_CREATION_TIME);
        assertThat(testProcessStep.getAcceptedTime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_ACCEPTED_TIME);
        assertThat(testProcessStep.getCompletedTime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_COMPLETED_TIME);
    }

    @Test
    @Transactional
    public void deleteProcessStep() throws Exception {
        // Initialize the database
        processStepRepository.saveAndFlush(processStep);

		int databaseSizeBeforeDelete = processStepRepository.findAll().size();

        // Get the processStep
        restProcessStepMockMvc.perform(delete("/api/processStep/{id}", processStep.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ProcessStep> processStep = processStepRepository.findAll();
        assertThat(processStep).hasSize(databaseSizeBeforeDelete - 1);
    }
}
