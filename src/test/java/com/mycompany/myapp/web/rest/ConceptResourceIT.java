package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.DevpctApp;
import com.mycompany.myapp.domain.Concept;
import com.mycompany.myapp.repository.ConceptRepository;
import com.mycompany.myapp.service.ConceptService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ConceptResource} REST controller.
 */
@SpringBootTest(classes = DevpctApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ConceptResourceIT {

    private static final String DEFAULT_CONCEPTNAME = "AAAAAAAAAA";
    private static final String UPDATED_CONCEPTNAME = "BBBBBBBBBB";

    @Autowired
    private ConceptRepository conceptRepository;

    @Mock
    private ConceptRepository conceptRepositoryMock;

    @Mock
    private ConceptService conceptServiceMock;

    @Autowired
    private ConceptService conceptService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConceptMockMvc;

    private Concept concept;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Concept createEntity(EntityManager em) {
        Concept concept = new Concept()
            .conceptname(DEFAULT_CONCEPTNAME);
        return concept;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Concept createUpdatedEntity(EntityManager em) {
        Concept concept = new Concept()
            .conceptname(UPDATED_CONCEPTNAME);
        return concept;
    }

    @BeforeEach
    public void initTest() {
        concept = createEntity(em);
    }

    @Test
    @Transactional
    public void createConcept() throws Exception {
        int databaseSizeBeforeCreate = conceptRepository.findAll().size();
        // Create the Concept
        restConceptMockMvc.perform(post("/api/concepts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(concept)))
            .andExpect(status().isCreated());

        // Validate the Concept in the database
        List<Concept> conceptList = conceptRepository.findAll();
        assertThat(conceptList).hasSize(databaseSizeBeforeCreate + 1);
        Concept testConcept = conceptList.get(conceptList.size() - 1);
        assertThat(testConcept.getConceptname()).isEqualTo(DEFAULT_CONCEPTNAME);
    }

    @Test
    @Transactional
    public void createConceptWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = conceptRepository.findAll().size();

        // Create the Concept with an existing ID
        concept.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConceptMockMvc.perform(post("/api/concepts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(concept)))
            .andExpect(status().isBadRequest());

        // Validate the Concept in the database
        List<Concept> conceptList = conceptRepository.findAll();
        assertThat(conceptList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllConcepts() throws Exception {
        // Initialize the database
        conceptRepository.saveAndFlush(concept);

        // Get all the conceptList
        restConceptMockMvc.perform(get("/api/concepts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(concept.getId().intValue())))
            .andExpect(jsonPath("$.[*].conceptname").value(hasItem(DEFAULT_CONCEPTNAME)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllConceptsWithEagerRelationshipsIsEnabled() throws Exception {
        when(conceptServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restConceptMockMvc.perform(get("/api/concepts?eagerload=true"))
            .andExpect(status().isOk());

        verify(conceptServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllConceptsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(conceptServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restConceptMockMvc.perform(get("/api/concepts?eagerload=true"))
            .andExpect(status().isOk());

        verify(conceptServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getConcept() throws Exception {
        // Initialize the database
        conceptRepository.saveAndFlush(concept);

        // Get the concept
        restConceptMockMvc.perform(get("/api/concepts/{id}", concept.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(concept.getId().intValue()))
            .andExpect(jsonPath("$.conceptname").value(DEFAULT_CONCEPTNAME));
    }
    @Test
    @Transactional
    public void getNonExistingConcept() throws Exception {
        // Get the concept
        restConceptMockMvc.perform(get("/api/concepts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConcept() throws Exception {
        // Initialize the database
        conceptService.save(concept);

        int databaseSizeBeforeUpdate = conceptRepository.findAll().size();

        // Update the concept
        Concept updatedConcept = conceptRepository.findById(concept.getId()).get();
        // Disconnect from session so that the updates on updatedConcept are not directly saved in db
        em.detach(updatedConcept);
        updatedConcept
            .conceptname(UPDATED_CONCEPTNAME);

        restConceptMockMvc.perform(put("/api/concepts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedConcept)))
            .andExpect(status().isOk());

        // Validate the Concept in the database
        List<Concept> conceptList = conceptRepository.findAll();
        assertThat(conceptList).hasSize(databaseSizeBeforeUpdate);
        Concept testConcept = conceptList.get(conceptList.size() - 1);
        assertThat(testConcept.getConceptname()).isEqualTo(UPDATED_CONCEPTNAME);
    }

    @Test
    @Transactional
    public void updateNonExistingConcept() throws Exception {
        int databaseSizeBeforeUpdate = conceptRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConceptMockMvc.perform(put("/api/concepts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(concept)))
            .andExpect(status().isBadRequest());

        // Validate the Concept in the database
        List<Concept> conceptList = conceptRepository.findAll();
        assertThat(conceptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConcept() throws Exception {
        // Initialize the database
        conceptService.save(concept);

        int databaseSizeBeforeDelete = conceptRepository.findAll().size();

        // Delete the concept
        restConceptMockMvc.perform(delete("/api/concepts/{id}", concept.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Concept> conceptList = conceptRepository.findAll();
        assertThat(conceptList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
