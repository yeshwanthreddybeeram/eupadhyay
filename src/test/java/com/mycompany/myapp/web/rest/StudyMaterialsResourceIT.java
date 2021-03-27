package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.DevpctApp;
import com.mycompany.myapp.domain.StudyMaterials;
import com.mycompany.myapp.repository.StudyMaterialsRepository;
import com.mycompany.myapp.service.StudyMaterialsService;

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

import com.mycompany.myapp.domain.enumeration.FolderType;
/**
 * Integration tests for the {@link StudyMaterialsResource} REST controller.
 */
@SpringBootTest(classes = DevpctApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class StudyMaterialsResourceIT {

    private static final String DEFAULT_FOLDER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FOLDER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FOLDER_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_FOLDER_DESCRIPTION = "BBBBBBBBBB";

    private static final FolderType DEFAULT_FOLDERTYPE = FolderType.VIDEOS;
    private static final FolderType UPDATED_FOLDERTYPE = FolderType.ASSIGNMENTS;

    @Autowired
    private StudyMaterialsRepository studyMaterialsRepository;

    @Mock
    private StudyMaterialsRepository studyMaterialsRepositoryMock;

    @Mock
    private StudyMaterialsService studyMaterialsServiceMock;

    @Autowired
    private StudyMaterialsService studyMaterialsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStudyMaterialsMockMvc;

    private StudyMaterials studyMaterials;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StudyMaterials createEntity(EntityManager em) {
        StudyMaterials studyMaterials = new StudyMaterials()
            .folderName(DEFAULT_FOLDER_NAME)
            .folderDescription(DEFAULT_FOLDER_DESCRIPTION)
            .foldertype(DEFAULT_FOLDERTYPE);
        return studyMaterials;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StudyMaterials createUpdatedEntity(EntityManager em) {
        StudyMaterials studyMaterials = new StudyMaterials()
            .folderName(UPDATED_FOLDER_NAME)
            .folderDescription(UPDATED_FOLDER_DESCRIPTION)
            .foldertype(UPDATED_FOLDERTYPE);
        return studyMaterials;
    }

    @BeforeEach
    public void initTest() {
        studyMaterials = createEntity(em);
    }

    @Test
    @Transactional
    public void createStudyMaterials() throws Exception {
        int databaseSizeBeforeCreate = studyMaterialsRepository.findAll().size();
        // Create the StudyMaterials
        restStudyMaterialsMockMvc.perform(post("/api/study-materials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(studyMaterials)))
            .andExpect(status().isCreated());

        // Validate the StudyMaterials in the database
        List<StudyMaterials> studyMaterialsList = studyMaterialsRepository.findAll();
        assertThat(studyMaterialsList).hasSize(databaseSizeBeforeCreate + 1);
        StudyMaterials testStudyMaterials = studyMaterialsList.get(studyMaterialsList.size() - 1);
        assertThat(testStudyMaterials.getFolderName()).isEqualTo(DEFAULT_FOLDER_NAME);
        assertThat(testStudyMaterials.getFolderDescription()).isEqualTo(DEFAULT_FOLDER_DESCRIPTION);
        assertThat(testStudyMaterials.getFoldertype()).isEqualTo(DEFAULT_FOLDERTYPE);
    }

    @Test
    @Transactional
    public void createStudyMaterialsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = studyMaterialsRepository.findAll().size();

        // Create the StudyMaterials with an existing ID
        studyMaterials.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudyMaterialsMockMvc.perform(post("/api/study-materials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(studyMaterials)))
            .andExpect(status().isBadRequest());

        // Validate the StudyMaterials in the database
        List<StudyMaterials> studyMaterialsList = studyMaterialsRepository.findAll();
        assertThat(studyMaterialsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllStudyMaterials() throws Exception {
        // Initialize the database
        studyMaterialsRepository.saveAndFlush(studyMaterials);

        // Get all the studyMaterialsList
        restStudyMaterialsMockMvc.perform(get("/api/study-materials?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studyMaterials.getId().intValue())))
            .andExpect(jsonPath("$.[*].folderName").value(hasItem(DEFAULT_FOLDER_NAME)))
            .andExpect(jsonPath("$.[*].folderDescription").value(hasItem(DEFAULT_FOLDER_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].foldertype").value(hasItem(DEFAULT_FOLDERTYPE.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllStudyMaterialsWithEagerRelationshipsIsEnabled() throws Exception {
        when(studyMaterialsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restStudyMaterialsMockMvc.perform(get("/api/study-materials?eagerload=true"))
            .andExpect(status().isOk());

        verify(studyMaterialsServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllStudyMaterialsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(studyMaterialsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restStudyMaterialsMockMvc.perform(get("/api/study-materials?eagerload=true"))
            .andExpect(status().isOk());

        verify(studyMaterialsServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getStudyMaterials() throws Exception {
        // Initialize the database
        studyMaterialsRepository.saveAndFlush(studyMaterials);

        // Get the studyMaterials
        restStudyMaterialsMockMvc.perform(get("/api/study-materials/{id}", studyMaterials.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(studyMaterials.getId().intValue()))
            .andExpect(jsonPath("$.folderName").value(DEFAULT_FOLDER_NAME))
            .andExpect(jsonPath("$.folderDescription").value(DEFAULT_FOLDER_DESCRIPTION))
            .andExpect(jsonPath("$.foldertype").value(DEFAULT_FOLDERTYPE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingStudyMaterials() throws Exception {
        // Get the studyMaterials
        restStudyMaterialsMockMvc.perform(get("/api/study-materials/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudyMaterials() throws Exception {
        // Initialize the database
        studyMaterialsService.save(studyMaterials);

        int databaseSizeBeforeUpdate = studyMaterialsRepository.findAll().size();

        // Update the studyMaterials
        StudyMaterials updatedStudyMaterials = studyMaterialsRepository.findById(studyMaterials.getId()).get();
        // Disconnect from session so that the updates on updatedStudyMaterials are not directly saved in db
        em.detach(updatedStudyMaterials);
        updatedStudyMaterials
            .folderName(UPDATED_FOLDER_NAME)
            .folderDescription(UPDATED_FOLDER_DESCRIPTION)
            .foldertype(UPDATED_FOLDERTYPE);

        restStudyMaterialsMockMvc.perform(put("/api/study-materials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedStudyMaterials)))
            .andExpect(status().isOk());

        // Validate the StudyMaterials in the database
        List<StudyMaterials> studyMaterialsList = studyMaterialsRepository.findAll();
        assertThat(studyMaterialsList).hasSize(databaseSizeBeforeUpdate);
        StudyMaterials testStudyMaterials = studyMaterialsList.get(studyMaterialsList.size() - 1);
        assertThat(testStudyMaterials.getFolderName()).isEqualTo(UPDATED_FOLDER_NAME);
        assertThat(testStudyMaterials.getFolderDescription()).isEqualTo(UPDATED_FOLDER_DESCRIPTION);
        assertThat(testStudyMaterials.getFoldertype()).isEqualTo(UPDATED_FOLDERTYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingStudyMaterials() throws Exception {
        int databaseSizeBeforeUpdate = studyMaterialsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudyMaterialsMockMvc.perform(put("/api/study-materials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(studyMaterials)))
            .andExpect(status().isBadRequest());

        // Validate the StudyMaterials in the database
        List<StudyMaterials> studyMaterialsList = studyMaterialsRepository.findAll();
        assertThat(studyMaterialsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStudyMaterials() throws Exception {
        // Initialize the database
        studyMaterialsService.save(studyMaterials);

        int databaseSizeBeforeDelete = studyMaterialsRepository.findAll().size();

        // Delete the studyMaterials
        restStudyMaterialsMockMvc.perform(delete("/api/study-materials/{id}", studyMaterials.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StudyMaterials> studyMaterialsList = studyMaterialsRepository.findAll();
        assertThat(studyMaterialsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
