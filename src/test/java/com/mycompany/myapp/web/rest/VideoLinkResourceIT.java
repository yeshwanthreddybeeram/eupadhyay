package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.DevpctApp;
import com.mycompany.myapp.domain.VideoLink;
import com.mycompany.myapp.repository.VideoLinkRepository;
import com.mycompany.myapp.service.VideoLinkService;

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
 * Integration tests for the {@link VideoLinkResource} REST controller.
 */
@SpringBootTest(classes = DevpctApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class VideoLinkResourceIT {

    private static final String DEFAULT_CLASSLINK = "AAAAAAAAAA";
    private static final String UPDATED_CLASSLINK = "BBBBBBBBBB";

    @Autowired
    private VideoLinkRepository videoLinkRepository;

    @Autowired
    private VideoLinkService videoLinkService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVideoLinkMockMvc;

    private VideoLink videoLink;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VideoLink createEntity(EntityManager em) {
        VideoLink videoLink = new VideoLink()
            .classlink(DEFAULT_CLASSLINK);
        return videoLink;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VideoLink createUpdatedEntity(EntityManager em) {
        VideoLink videoLink = new VideoLink()
            .classlink(UPDATED_CLASSLINK);
        return videoLink;
    }

    @BeforeEach
    public void initTest() {
        videoLink = createEntity(em);
    }

    @Test
    @Transactional
    public void createVideoLink() throws Exception {
        int databaseSizeBeforeCreate = videoLinkRepository.findAll().size();
        // Create the VideoLink
        restVideoLinkMockMvc.perform(post("/api/video-links")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(videoLink)))
            .andExpect(status().isCreated());

        // Validate the VideoLink in the database
        List<VideoLink> videoLinkList = videoLinkRepository.findAll();
        assertThat(videoLinkList).hasSize(databaseSizeBeforeCreate + 1);
        VideoLink testVideoLink = videoLinkList.get(videoLinkList.size() - 1);
        assertThat(testVideoLink.getClasslink()).isEqualTo(DEFAULT_CLASSLINK);
    }

    @Test
    @Transactional
    public void createVideoLinkWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = videoLinkRepository.findAll().size();

        // Create the VideoLink with an existing ID
        videoLink.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVideoLinkMockMvc.perform(post("/api/video-links")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(videoLink)))
            .andExpect(status().isBadRequest());

        // Validate the VideoLink in the database
        List<VideoLink> videoLinkList = videoLinkRepository.findAll();
        assertThat(videoLinkList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkClasslinkIsRequired() throws Exception {
        int databaseSizeBeforeTest = videoLinkRepository.findAll().size();
        // set the field null
        videoLink.setClasslink(null);

        // Create the VideoLink, which fails.


        restVideoLinkMockMvc.perform(post("/api/video-links")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(videoLink)))
            .andExpect(status().isBadRequest());

        List<VideoLink> videoLinkList = videoLinkRepository.findAll();
        assertThat(videoLinkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVideoLinks() throws Exception {
        // Initialize the database
        videoLinkRepository.saveAndFlush(videoLink);

        // Get all the videoLinkList
        restVideoLinkMockMvc.perform(get("/api/video-links?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(videoLink.getId().intValue())))
            .andExpect(jsonPath("$.[*].classlink").value(hasItem(DEFAULT_CLASSLINK)));
    }
    
    @Test
    @Transactional
    public void getVideoLink() throws Exception {
        // Initialize the database
        videoLinkRepository.saveAndFlush(videoLink);

        // Get the videoLink
        restVideoLinkMockMvc.perform(get("/api/video-links/{id}", videoLink.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(videoLink.getId().intValue()))
            .andExpect(jsonPath("$.classlink").value(DEFAULT_CLASSLINK));
    }
    @Test
    @Transactional
    public void getNonExistingVideoLink() throws Exception {
        // Get the videoLink
        restVideoLinkMockMvc.perform(get("/api/video-links/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVideoLink() throws Exception {
        // Initialize the database
        videoLinkService.save(videoLink);

        int databaseSizeBeforeUpdate = videoLinkRepository.findAll().size();

        // Update the videoLink
        VideoLink updatedVideoLink = videoLinkRepository.findById(videoLink.getId()).get();
        // Disconnect from session so that the updates on updatedVideoLink are not directly saved in db
        em.detach(updatedVideoLink);
        updatedVideoLink
            .classlink(UPDATED_CLASSLINK);

        restVideoLinkMockMvc.perform(put("/api/video-links")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedVideoLink)))
            .andExpect(status().isOk());

        // Validate the VideoLink in the database
        List<VideoLink> videoLinkList = videoLinkRepository.findAll();
        assertThat(videoLinkList).hasSize(databaseSizeBeforeUpdate);
        VideoLink testVideoLink = videoLinkList.get(videoLinkList.size() - 1);
        assertThat(testVideoLink.getClasslink()).isEqualTo(UPDATED_CLASSLINK);
    }

    @Test
    @Transactional
    public void updateNonExistingVideoLink() throws Exception {
        int databaseSizeBeforeUpdate = videoLinkRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVideoLinkMockMvc.perform(put("/api/video-links")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(videoLink)))
            .andExpect(status().isBadRequest());

        // Validate the VideoLink in the database
        List<VideoLink> videoLinkList = videoLinkRepository.findAll();
        assertThat(videoLinkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVideoLink() throws Exception {
        // Initialize the database
        videoLinkService.save(videoLink);

        int databaseSizeBeforeDelete = videoLinkRepository.findAll().size();

        // Delete the videoLink
        restVideoLinkMockMvc.perform(delete("/api/video-links/{id}", videoLink.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VideoLink> videoLinkList = videoLinkRepository.findAll();
        assertThat(videoLinkList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
