package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.DevpctApp;
import com.mycompany.myapp.domain.MyVideos;
import com.mycompany.myapp.repository.MyVideosRepository;
import com.mycompany.myapp.service.MyVideosService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MyVideosResource} REST controller.
 */
@SpringBootTest(classes = DevpctApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MyVideosResourceIT {

    private static final String DEFAULT_VIDEO_LINK = "AAAAAAAAAA";
    private static final String UPDATED_VIDEO_LINK = "BBBBBBBBBB";

    private static final String DEFAULT_CONCEPTNAME = "AAAAAAAAAA";
    private static final String UPDATED_CONCEPTNAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_SCHEDULE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SCHEDULE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private MyVideosRepository myVideosRepository;

    @Autowired
    private MyVideosService myVideosService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMyVideosMockMvc;

    private MyVideos myVideos;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MyVideos createEntity(EntityManager em) {
        MyVideos myVideos = new MyVideos()
            .videoLink(DEFAULT_VIDEO_LINK)
            .conceptname(DEFAULT_CONCEPTNAME)
            .scheduleTime(DEFAULT_SCHEDULE_TIME);
        return myVideos;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MyVideos createUpdatedEntity(EntityManager em) {
        MyVideos myVideos = new MyVideos()
            .videoLink(UPDATED_VIDEO_LINK)
            .conceptname(UPDATED_CONCEPTNAME)
            .scheduleTime(UPDATED_SCHEDULE_TIME);
        return myVideos;
    }

    @BeforeEach
    public void initTest() {
        myVideos = createEntity(em);
    }

    @Test
    @Transactional
    public void createMyVideos() throws Exception {
        int databaseSizeBeforeCreate = myVideosRepository.findAll().size();
        // Create the MyVideos
        restMyVideosMockMvc.perform(post("/api/my-videos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(myVideos)))
            .andExpect(status().isCreated());

        // Validate the MyVideos in the database
        List<MyVideos> myVideosList = myVideosRepository.findAll();
        assertThat(myVideosList).hasSize(databaseSizeBeforeCreate + 1);
        MyVideos testMyVideos = myVideosList.get(myVideosList.size() - 1);
        assertThat(testMyVideos.getVideoLink()).isEqualTo(DEFAULT_VIDEO_LINK);
        assertThat(testMyVideos.getConceptname()).isEqualTo(DEFAULT_CONCEPTNAME);
        assertThat(testMyVideos.getScheduleTime()).isEqualTo(DEFAULT_SCHEDULE_TIME);
    }

    @Test
    @Transactional
    public void createMyVideosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = myVideosRepository.findAll().size();

        // Create the MyVideos with an existing ID
        myVideos.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMyVideosMockMvc.perform(post("/api/my-videos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(myVideos)))
            .andExpect(status().isBadRequest());

        // Validate the MyVideos in the database
        List<MyVideos> myVideosList = myVideosRepository.findAll();
        assertThat(myVideosList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMyVideos() throws Exception {
        // Initialize the database
        myVideosRepository.saveAndFlush(myVideos);

        // Get all the myVideosList
        restMyVideosMockMvc.perform(get("/api/my-videos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(myVideos.getId().intValue())))
            .andExpect(jsonPath("$.[*].videoLink").value(hasItem(DEFAULT_VIDEO_LINK)))
            .andExpect(jsonPath("$.[*].conceptname").value(hasItem(DEFAULT_CONCEPTNAME)))
            .andExpect(jsonPath("$.[*].scheduleTime").value(hasItem(DEFAULT_SCHEDULE_TIME.toString())));
    }
    
    @Test
    @Transactional
    public void getMyVideos() throws Exception {
        // Initialize the database
        myVideosRepository.saveAndFlush(myVideos);

        // Get the myVideos
        restMyVideosMockMvc.perform(get("/api/my-videos/{id}", myVideos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(myVideos.getId().intValue()))
            .andExpect(jsonPath("$.videoLink").value(DEFAULT_VIDEO_LINK))
            .andExpect(jsonPath("$.conceptname").value(DEFAULT_CONCEPTNAME))
            .andExpect(jsonPath("$.scheduleTime").value(DEFAULT_SCHEDULE_TIME.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingMyVideos() throws Exception {
        // Get the myVideos
        restMyVideosMockMvc.perform(get("/api/my-videos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMyVideos() throws Exception {
        // Initialize the database
        myVideosService.save(myVideos);

        int databaseSizeBeforeUpdate = myVideosRepository.findAll().size();

        // Update the myVideos
        MyVideos updatedMyVideos = myVideosRepository.findById(myVideos.getId()).get();
        // Disconnect from session so that the updates on updatedMyVideos are not directly saved in db
        em.detach(updatedMyVideos);
        updatedMyVideos
            .videoLink(UPDATED_VIDEO_LINK)
            .conceptname(UPDATED_CONCEPTNAME)
            .scheduleTime(UPDATED_SCHEDULE_TIME);

        restMyVideosMockMvc.perform(put("/api/my-videos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMyVideos)))
            .andExpect(status().isOk());

        // Validate the MyVideos in the database
        List<MyVideos> myVideosList = myVideosRepository.findAll();
        assertThat(myVideosList).hasSize(databaseSizeBeforeUpdate);
        MyVideos testMyVideos = myVideosList.get(myVideosList.size() - 1);
        assertThat(testMyVideos.getVideoLink()).isEqualTo(UPDATED_VIDEO_LINK);
        assertThat(testMyVideos.getConceptname()).isEqualTo(UPDATED_CONCEPTNAME);
        assertThat(testMyVideos.getScheduleTime()).isEqualTo(UPDATED_SCHEDULE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingMyVideos() throws Exception {
        int databaseSizeBeforeUpdate = myVideosRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMyVideosMockMvc.perform(put("/api/my-videos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(myVideos)))
            .andExpect(status().isBadRequest());

        // Validate the MyVideos in the database
        List<MyVideos> myVideosList = myVideosRepository.findAll();
        assertThat(myVideosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMyVideos() throws Exception {
        // Initialize the database
        myVideosService.save(myVideos);

        int databaseSizeBeforeDelete = myVideosRepository.findAll().size();

        // Delete the myVideos
        restMyVideosMockMvc.perform(delete("/api/my-videos/{id}", myVideos.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MyVideos> myVideosList = myVideosRepository.findAll();
        assertThat(myVideosList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
