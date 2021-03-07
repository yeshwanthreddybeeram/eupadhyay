package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.DevpctApp;
import com.mycompany.myapp.domain.AssignmentQA;
import com.mycompany.myapp.repository.AssignmentQARepository;
import com.mycompany.myapp.service.AssignmentQAService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AssignmentQAResource} REST controller.
 */
@SpringBootTest(classes = DevpctApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AssignmentQAResourceIT {

    private static final String DEFAULT_QUESTION = "AAAAAAAAAA";
    private static final String UPDATED_QUESTION = "BBBBBBBBBB";

    private static final String DEFAULT_ANSWER = "AAAAAAAAAA";
    private static final String UPDATED_ANSWER = "BBBBBBBBBB";

    @Autowired
    private AssignmentQARepository assignmentQARepository;

    @Autowired
    private AssignmentQAService assignmentQAService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAssignmentQAMockMvc;

    private AssignmentQA assignmentQA;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AssignmentQA createEntity(EntityManager em) {
        AssignmentQA assignmentQA = new AssignmentQA()
            .question(DEFAULT_QUESTION)
            .answer(DEFAULT_ANSWER);
        return assignmentQA;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AssignmentQA createUpdatedEntity(EntityManager em) {
        AssignmentQA assignmentQA = new AssignmentQA()
            .question(UPDATED_QUESTION)
            .answer(UPDATED_ANSWER);
        return assignmentQA;
    }

    @BeforeEach
    public void initTest() {
        assignmentQA = createEntity(em);
    }

    @Test
    @Transactional
    public void createAssignmentQA() throws Exception {
        int databaseSizeBeforeCreate = assignmentQARepository.findAll().size();
        // Create the AssignmentQA
        restAssignmentQAMockMvc.perform(post("/api/assignment-qas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(assignmentQA)))
            .andExpect(status().isCreated());

        // Validate the AssignmentQA in the database
        List<AssignmentQA> assignmentQAList = assignmentQARepository.findAll();
        assertThat(assignmentQAList).hasSize(databaseSizeBeforeCreate + 1);
        AssignmentQA testAssignmentQA = assignmentQAList.get(assignmentQAList.size() - 1);
        assertThat(testAssignmentQA.getQuestion()).isEqualTo(DEFAULT_QUESTION);
        assertThat(testAssignmentQA.getAnswer()).isEqualTo(DEFAULT_ANSWER);
    }

    @Test
    @Transactional
    public void createAssignmentQAWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = assignmentQARepository.findAll().size();

        // Create the AssignmentQA with an existing ID
        assignmentQA.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssignmentQAMockMvc.perform(post("/api/assignment-qas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(assignmentQA)))
            .andExpect(status().isBadRequest());

        // Validate the AssignmentQA in the database
        List<AssignmentQA> assignmentQAList = assignmentQARepository.findAll();
        assertThat(assignmentQAList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAssignmentQAS() throws Exception {
        // Initialize the database
        assignmentQARepository.saveAndFlush(assignmentQA);

        // Get all the assignmentQAList
        restAssignmentQAMockMvc.perform(get("/api/assignment-qas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assignmentQA.getId().intValue())))
            .andExpect(jsonPath("$.[*].question").value(hasItem(DEFAULT_QUESTION)))
            .andExpect(jsonPath("$.[*].answer").value(hasItem(DEFAULT_ANSWER)));
    }
    
    @Test
    @Transactional
    public void getAssignmentQA() throws Exception {
        // Initialize the database
        assignmentQARepository.saveAndFlush(assignmentQA);

        // Get the assignmentQA
        restAssignmentQAMockMvc.perform(get("/api/assignment-qas/{id}", assignmentQA.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(assignmentQA.getId().intValue()))
            .andExpect(jsonPath("$.question").value(DEFAULT_QUESTION))
            .andExpect(jsonPath("$.answer").value(DEFAULT_ANSWER));
    }
    @Test
    @Transactional
    public void getNonExistingAssignmentQA() throws Exception {
        // Get the assignmentQA
        restAssignmentQAMockMvc.perform(get("/api/assignment-qas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAssignmentQA() throws Exception {
        // Initialize the database
        assignmentQAService.save(assignmentQA);

        int databaseSizeBeforeUpdate = assignmentQARepository.findAll().size();

        // Update the assignmentQA
        AssignmentQA updatedAssignmentQA = assignmentQARepository.findById(assignmentQA.getId()).get();
        // Disconnect from session so that the updates on updatedAssignmentQA are not directly saved in db
        em.detach(updatedAssignmentQA);
        updatedAssignmentQA
            .question(UPDATED_QUESTION)
            .answer(UPDATED_ANSWER);

        restAssignmentQAMockMvc.perform(put("/api/assignment-qas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAssignmentQA)))
            .andExpect(status().isOk());

        // Validate the AssignmentQA in the database
        List<AssignmentQA> assignmentQAList = assignmentQARepository.findAll();
        assertThat(assignmentQAList).hasSize(databaseSizeBeforeUpdate);
        AssignmentQA testAssignmentQA = assignmentQAList.get(assignmentQAList.size() - 1);
        assertThat(testAssignmentQA.getQuestion()).isEqualTo(UPDATED_QUESTION);
        assertThat(testAssignmentQA.getAnswer()).isEqualTo(UPDATED_ANSWER);
    }

    @Test
    @Transactional
    public void updateNonExistingAssignmentQA() throws Exception {
        int databaseSizeBeforeUpdate = assignmentQARepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssignmentQAMockMvc.perform(put("/api/assignment-qas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(assignmentQA)))
            .andExpect(status().isBadRequest());

        // Validate the AssignmentQA in the database
        List<AssignmentQA> assignmentQAList = assignmentQARepository.findAll();
        assertThat(assignmentQAList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAssignmentQA() throws Exception {
        // Initialize the database
        assignmentQAService.save(assignmentQA);

        int databaseSizeBeforeDelete = assignmentQARepository.findAll().size();

        // Delete the assignmentQA
        restAssignmentQAMockMvc.perform(delete("/api/assignment-qas/{id}", assignmentQA.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AssignmentQA> assignmentQAList = assignmentQARepository.findAll();
        assertThat(assignmentQAList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
