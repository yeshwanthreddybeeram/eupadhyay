package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.DevpctApp;
import com.mycompany.myapp.domain.ScheduleClass;
import com.mycompany.myapp.repository.ScheduleClassRepository;
import com.mycompany.myapp.service.ScheduleClassService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ScheduleClassResource} REST controller.
 */
@SpringBootTest(classes = DevpctApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ScheduleClassResourceIT {

    private static final String DEFAULT_SCHEDULELINK = "AAAAAAAAAA";
    private static final String UPDATED_SCHEDULELINK = "BBBBBBBBBB";

    private static final Instant DEFAULT_SCHEDULE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SCHEDULE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_STUDENTNAME = "AAAAAAAAAA";
    private static final String UPDATED_STUDENTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMPLOYEENAME = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEENAME = "BBBBBBBBBB";

    private static final String DEFAULT_VIDEOLINK = "AAAAAAAAAA";
    private static final String UPDATED_VIDEOLINK = "BBBBBBBBBB";

    private static final Boolean DEFAULT_COMPLETE = false;
    private static final Boolean UPDATED_COMPLETE = true;

    private static final Boolean DEFAULT_REMOVE = false;
    private static final Boolean UPDATED_REMOVE = true;

    @Autowired
    private ScheduleClassRepository scheduleClassRepository;

    @Mock
    private ScheduleClassRepository scheduleClassRepositoryMock;

    @Mock
    private ScheduleClassService scheduleClassServiceMock;

    @Autowired
    private ScheduleClassService scheduleClassService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restScheduleClassMockMvc;

    private ScheduleClass scheduleClass;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ScheduleClass createEntity(EntityManager em) {
        ScheduleClass scheduleClass = new ScheduleClass()
            .schedulelink(DEFAULT_SCHEDULELINK)
            .scheduleTime(DEFAULT_SCHEDULE_TIME)
            .studentname(DEFAULT_STUDENTNAME)
            .employeename(DEFAULT_EMPLOYEENAME)
            .videolink(DEFAULT_VIDEOLINK)
            .complete(DEFAULT_COMPLETE)
            .remove(DEFAULT_REMOVE);
        return scheduleClass;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ScheduleClass createUpdatedEntity(EntityManager em) {
        ScheduleClass scheduleClass = new ScheduleClass()
            .schedulelink(UPDATED_SCHEDULELINK)
            .scheduleTime(UPDATED_SCHEDULE_TIME)
            .studentname(UPDATED_STUDENTNAME)
            .employeename(UPDATED_EMPLOYEENAME)
            .videolink(UPDATED_VIDEOLINK)
            .complete(UPDATED_COMPLETE)
            .remove(UPDATED_REMOVE);
        return scheduleClass;
    }

    @BeforeEach
    public void initTest() {
        scheduleClass = createEntity(em);
    }

    @Test
    @Transactional
    public void createScheduleClass() throws Exception {
        int databaseSizeBeforeCreate = scheduleClassRepository.findAll().size();
        // Create the ScheduleClass
        restScheduleClassMockMvc.perform(post("/api/schedule-classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scheduleClass)))
            .andExpect(status().isCreated());

        // Validate the ScheduleClass in the database
        List<ScheduleClass> scheduleClassList = scheduleClassRepository.findAll();
        assertThat(scheduleClassList).hasSize(databaseSizeBeforeCreate + 1);
        ScheduleClass testScheduleClass = scheduleClassList.get(scheduleClassList.size() - 1);
        assertThat(testScheduleClass.getSchedulelink()).isEqualTo(DEFAULT_SCHEDULELINK);
        assertThat(testScheduleClass.getScheduleTime()).isEqualTo(DEFAULT_SCHEDULE_TIME);
        assertThat(testScheduleClass.getStudentname()).isEqualTo(DEFAULT_STUDENTNAME);
        assertThat(testScheduleClass.getEmployeename()).isEqualTo(DEFAULT_EMPLOYEENAME);
        assertThat(testScheduleClass.getVideolink()).isEqualTo(DEFAULT_VIDEOLINK);
        assertThat(testScheduleClass.isComplete()).isEqualTo(DEFAULT_COMPLETE);
        assertThat(testScheduleClass.isRemove()).isEqualTo(DEFAULT_REMOVE);
    }

    @Test
    @Transactional
    public void createScheduleClassWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = scheduleClassRepository.findAll().size();

        // Create the ScheduleClass with an existing ID
        scheduleClass.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restScheduleClassMockMvc.perform(post("/api/schedule-classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scheduleClass)))
            .andExpect(status().isBadRequest());

        // Validate the ScheduleClass in the database
        List<ScheduleClass> scheduleClassList = scheduleClassRepository.findAll();
        assertThat(scheduleClassList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkSchedulelinkIsRequired() throws Exception {
        int databaseSizeBeforeTest = scheduleClassRepository.findAll().size();
        // set the field null
        scheduleClass.setSchedulelink(null);

        // Create the ScheduleClass, which fails.


        restScheduleClassMockMvc.perform(post("/api/schedule-classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scheduleClass)))
            .andExpect(status().isBadRequest());

        List<ScheduleClass> scheduleClassList = scheduleClassRepository.findAll();
        assertThat(scheduleClassList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkScheduleTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = scheduleClassRepository.findAll().size();
        // set the field null
        scheduleClass.setScheduleTime(null);

        // Create the ScheduleClass, which fails.


        restScheduleClassMockMvc.perform(post("/api/schedule-classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scheduleClass)))
            .andExpect(status().isBadRequest());

        List<ScheduleClass> scheduleClassList = scheduleClassRepository.findAll();
        assertThat(scheduleClassList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStudentnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = scheduleClassRepository.findAll().size();
        // set the field null
        scheduleClass.setStudentname(null);

        // Create the ScheduleClass, which fails.


        restScheduleClassMockMvc.perform(post("/api/schedule-classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scheduleClass)))
            .andExpect(status().isBadRequest());

        List<ScheduleClass> scheduleClassList = scheduleClassRepository.findAll();
        assertThat(scheduleClassList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmployeenameIsRequired() throws Exception {
        int databaseSizeBeforeTest = scheduleClassRepository.findAll().size();
        // set the field null
        scheduleClass.setEmployeename(null);

        // Create the ScheduleClass, which fails.


        restScheduleClassMockMvc.perform(post("/api/schedule-classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scheduleClass)))
            .andExpect(status().isBadRequest());

        List<ScheduleClass> scheduleClassList = scheduleClassRepository.findAll();
        assertThat(scheduleClassList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllScheduleClasses() throws Exception {
        // Initialize the database
        scheduleClassRepository.saveAndFlush(scheduleClass);

        // Get all the scheduleClassList
        restScheduleClassMockMvc.perform(get("/api/schedule-classes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scheduleClass.getId().intValue())))
            .andExpect(jsonPath("$.[*].schedulelink").value(hasItem(DEFAULT_SCHEDULELINK)))
            .andExpect(jsonPath("$.[*].scheduleTime").value(hasItem(DEFAULT_SCHEDULE_TIME.toString())))
            .andExpect(jsonPath("$.[*].studentname").value(hasItem(DEFAULT_STUDENTNAME)))
            .andExpect(jsonPath("$.[*].employeename").value(hasItem(DEFAULT_EMPLOYEENAME)))
            .andExpect(jsonPath("$.[*].videolink").value(hasItem(DEFAULT_VIDEOLINK)))
            .andExpect(jsonPath("$.[*].complete").value(hasItem(DEFAULT_COMPLETE.booleanValue())))
            .andExpect(jsonPath("$.[*].remove").value(hasItem(DEFAULT_REMOVE.booleanValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllScheduleClassesWithEagerRelationshipsIsEnabled() throws Exception {
        when(scheduleClassServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restScheduleClassMockMvc.perform(get("/api/schedule-classes?eagerload=true"))
            .andExpect(status().isOk());

        verify(scheduleClassServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllScheduleClassesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(scheduleClassServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restScheduleClassMockMvc.perform(get("/api/schedule-classes?eagerload=true"))
            .andExpect(status().isOk());

        verify(scheduleClassServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getScheduleClass() throws Exception {
        // Initialize the database
        scheduleClassRepository.saveAndFlush(scheduleClass);

        // Get the scheduleClass
        restScheduleClassMockMvc.perform(get("/api/schedule-classes/{id}", scheduleClass.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(scheduleClass.getId().intValue()))
            .andExpect(jsonPath("$.schedulelink").value(DEFAULT_SCHEDULELINK))
            .andExpect(jsonPath("$.scheduleTime").value(DEFAULT_SCHEDULE_TIME.toString()))
            .andExpect(jsonPath("$.studentname").value(DEFAULT_STUDENTNAME))
            .andExpect(jsonPath("$.employeename").value(DEFAULT_EMPLOYEENAME))
            .andExpect(jsonPath("$.videolink").value(DEFAULT_VIDEOLINK))
            .andExpect(jsonPath("$.complete").value(DEFAULT_COMPLETE.booleanValue()))
            .andExpect(jsonPath("$.remove").value(DEFAULT_REMOVE.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingScheduleClass() throws Exception {
        // Get the scheduleClass
        restScheduleClassMockMvc.perform(get("/api/schedule-classes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateScheduleClass() throws Exception {
        // Initialize the database
        scheduleClassService.save(scheduleClass);

        int databaseSizeBeforeUpdate = scheduleClassRepository.findAll().size();

        // Update the scheduleClass
        ScheduleClass updatedScheduleClass = scheduleClassRepository.findById(scheduleClass.getId()).get();
        // Disconnect from session so that the updates on updatedScheduleClass are not directly saved in db
        em.detach(updatedScheduleClass);
        updatedScheduleClass
            .schedulelink(UPDATED_SCHEDULELINK)
            .scheduleTime(UPDATED_SCHEDULE_TIME)
            .studentname(UPDATED_STUDENTNAME)
            .employeename(UPDATED_EMPLOYEENAME)
            .videolink(UPDATED_VIDEOLINK)
            .complete(UPDATED_COMPLETE)
            .remove(UPDATED_REMOVE);

        restScheduleClassMockMvc.perform(put("/api/schedule-classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedScheduleClass)))
            .andExpect(status().isOk());

        // Validate the ScheduleClass in the database
        List<ScheduleClass> scheduleClassList = scheduleClassRepository.findAll();
        assertThat(scheduleClassList).hasSize(databaseSizeBeforeUpdate);
        ScheduleClass testScheduleClass = scheduleClassList.get(scheduleClassList.size() - 1);
        assertThat(testScheduleClass.getSchedulelink()).isEqualTo(UPDATED_SCHEDULELINK);
        assertThat(testScheduleClass.getScheduleTime()).isEqualTo(UPDATED_SCHEDULE_TIME);
        assertThat(testScheduleClass.getStudentname()).isEqualTo(UPDATED_STUDENTNAME);
        assertThat(testScheduleClass.getEmployeename()).isEqualTo(UPDATED_EMPLOYEENAME);
        assertThat(testScheduleClass.getVideolink()).isEqualTo(UPDATED_VIDEOLINK);
        assertThat(testScheduleClass.isComplete()).isEqualTo(UPDATED_COMPLETE);
        assertThat(testScheduleClass.isRemove()).isEqualTo(UPDATED_REMOVE);
    }

    @Test
    @Transactional
    public void updateNonExistingScheduleClass() throws Exception {
        int databaseSizeBeforeUpdate = scheduleClassRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScheduleClassMockMvc.perform(put("/api/schedule-classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scheduleClass)))
            .andExpect(status().isBadRequest());

        // Validate the ScheduleClass in the database
        List<ScheduleClass> scheduleClassList = scheduleClassRepository.findAll();
        assertThat(scheduleClassList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteScheduleClass() throws Exception {
        // Initialize the database
        scheduleClassService.save(scheduleClass);

        int databaseSizeBeforeDelete = scheduleClassRepository.findAll().size();

        // Delete the scheduleClass
        restScheduleClassMockMvc.perform(delete("/api/schedule-classes/{id}", scheduleClass.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ScheduleClass> scheduleClassList = scheduleClassRepository.findAll();
        assertThat(scheduleClassList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
