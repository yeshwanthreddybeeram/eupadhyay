package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.DevpctApp;
import com.mycompany.myapp.domain.ClassIdentity;
import com.mycompany.myapp.repository.ClassIdentityRepository;
import com.mycompany.myapp.service.ClassIdentityService;

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
 * Integration tests for the {@link ClassIdentityResource} REST controller.
 */
@SpringBootTest(classes = DevpctApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ClassIdentityResourceIT {

    private static final String DEFAULT_CLASS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CLASS_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ClassIdentityRepository classIdentityRepository;

    @Autowired
    private ClassIdentityService classIdentityService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClassIdentityMockMvc;

    private ClassIdentity classIdentity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClassIdentity createEntity(EntityManager em) {
        ClassIdentity classIdentity = new ClassIdentity()
            .className(DEFAULT_CLASS_NAME)
            .description(DEFAULT_DESCRIPTION);
        return classIdentity;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClassIdentity createUpdatedEntity(EntityManager em) {
        ClassIdentity classIdentity = new ClassIdentity()
            .className(UPDATED_CLASS_NAME)
            .description(UPDATED_DESCRIPTION);
        return classIdentity;
    }

    @BeforeEach
    public void initTest() {
        classIdentity = createEntity(em);
    }

    @Test
    @Transactional
    public void createClassIdentity() throws Exception {
        int databaseSizeBeforeCreate = classIdentityRepository.findAll().size();
        // Create the ClassIdentity
        restClassIdentityMockMvc.perform(post("/api/class-identities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classIdentity)))
            .andExpect(status().isCreated());

        // Validate the ClassIdentity in the database
        List<ClassIdentity> classIdentityList = classIdentityRepository.findAll();
        assertThat(classIdentityList).hasSize(databaseSizeBeforeCreate + 1);
        ClassIdentity testClassIdentity = classIdentityList.get(classIdentityList.size() - 1);
        assertThat(testClassIdentity.getClassName()).isEqualTo(DEFAULT_CLASS_NAME);
        assertThat(testClassIdentity.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createClassIdentityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = classIdentityRepository.findAll().size();

        // Create the ClassIdentity with an existing ID
        classIdentity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClassIdentityMockMvc.perform(post("/api/class-identities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classIdentity)))
            .andExpect(status().isBadRequest());

        // Validate the ClassIdentity in the database
        List<ClassIdentity> classIdentityList = classIdentityRepository.findAll();
        assertThat(classIdentityList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllClassIdentities() throws Exception {
        // Initialize the database
        classIdentityRepository.saveAndFlush(classIdentity);

        // Get all the classIdentityList
        restClassIdentityMockMvc.perform(get("/api/class-identities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classIdentity.getId().intValue())))
            .andExpect(jsonPath("$.[*].className").value(hasItem(DEFAULT_CLASS_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getClassIdentity() throws Exception {
        // Initialize the database
        classIdentityRepository.saveAndFlush(classIdentity);

        // Get the classIdentity
        restClassIdentityMockMvc.perform(get("/api/class-identities/{id}", classIdentity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(classIdentity.getId().intValue()))
            .andExpect(jsonPath("$.className").value(DEFAULT_CLASS_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingClassIdentity() throws Exception {
        // Get the classIdentity
        restClassIdentityMockMvc.perform(get("/api/class-identities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClassIdentity() throws Exception {
        // Initialize the database
        classIdentityService.save(classIdentity);

        int databaseSizeBeforeUpdate = classIdentityRepository.findAll().size();

        // Update the classIdentity
        ClassIdentity updatedClassIdentity = classIdentityRepository.findById(classIdentity.getId()).get();
        // Disconnect from session so that the updates on updatedClassIdentity are not directly saved in db
        em.detach(updatedClassIdentity);
        updatedClassIdentity
            .className(UPDATED_CLASS_NAME)
            .description(UPDATED_DESCRIPTION);

        restClassIdentityMockMvc.perform(put("/api/class-identities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedClassIdentity)))
            .andExpect(status().isOk());

        // Validate the ClassIdentity in the database
        List<ClassIdentity> classIdentityList = classIdentityRepository.findAll();
        assertThat(classIdentityList).hasSize(databaseSizeBeforeUpdate);
        ClassIdentity testClassIdentity = classIdentityList.get(classIdentityList.size() - 1);
        assertThat(testClassIdentity.getClassName()).isEqualTo(UPDATED_CLASS_NAME);
        assertThat(testClassIdentity.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingClassIdentity() throws Exception {
        int databaseSizeBeforeUpdate = classIdentityRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClassIdentityMockMvc.perform(put("/api/class-identities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classIdentity)))
            .andExpect(status().isBadRequest());

        // Validate the ClassIdentity in the database
        List<ClassIdentity> classIdentityList = classIdentityRepository.findAll();
        assertThat(classIdentityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClassIdentity() throws Exception {
        // Initialize the database
        classIdentityService.save(classIdentity);

        int databaseSizeBeforeDelete = classIdentityRepository.findAll().size();

        // Delete the classIdentity
        restClassIdentityMockMvc.perform(delete("/api/class-identities/{id}", classIdentity.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClassIdentity> classIdentityList = classIdentityRepository.findAll();
        assertThat(classIdentityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
