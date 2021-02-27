package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.VideoLink;
import com.mycompany.myapp.service.VideoLinkService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.VideoLink}.
 */
@RestController
@RequestMapping("/api")
public class VideoLinkResource {

    private final Logger log = LoggerFactory.getLogger(VideoLinkResource.class);

    private static final String ENTITY_NAME = "videoLink";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VideoLinkService videoLinkService;

    public VideoLinkResource(VideoLinkService videoLinkService) {
        this.videoLinkService = videoLinkService;
    }

    /**
     * {@code POST  /video-links} : Create a new videoLink.
     *
     * @param videoLink the videoLink to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new videoLink, or with status {@code 400 (Bad Request)} if the videoLink has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/video-links")
    public ResponseEntity<VideoLink> createVideoLink(@Valid @RequestBody VideoLink videoLink) throws URISyntaxException {
        log.debug("REST request to save VideoLink : {}", videoLink);
        if (videoLink.getId() != null) {
            throw new BadRequestAlertException("A new videoLink cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VideoLink result = videoLinkService.save(videoLink);
        return ResponseEntity.created(new URI("/api/video-links/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /video-links} : Updates an existing videoLink.
     *
     * @param videoLink the videoLink to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated videoLink,
     * or with status {@code 400 (Bad Request)} if the videoLink is not valid,
     * or with status {@code 500 (Internal Server Error)} if the videoLink couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/video-links")
    public ResponseEntity<VideoLink> updateVideoLink(@Valid @RequestBody VideoLink videoLink) throws URISyntaxException {
        log.debug("REST request to update VideoLink : {}", videoLink);
        if (videoLink.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VideoLink result = videoLinkService.save(videoLink);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, videoLink.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /video-links} : get all the videoLinks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of videoLinks in body.
     */
    @GetMapping("/video-links")
    public List<VideoLink> getAllVideoLinks() {
        log.debug("REST request to get all VideoLinks");
        return videoLinkService.findAll();
    }

    /**
     * {@code GET  /video-links/:id} : get the "id" videoLink.
     *
     * @param id the id of the videoLink to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the videoLink, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/video-links/{id}")
    public ResponseEntity<VideoLink> getVideoLink(@PathVariable Long id) {
        log.debug("REST request to get VideoLink : {}", id);
        Optional<VideoLink> videoLink = videoLinkService.findOne(id);
        return ResponseUtil.wrapOrNotFound(videoLink);
    }

    /**
     * {@code DELETE  /video-links/:id} : delete the "id" videoLink.
     *
     * @param id the id of the videoLink to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/video-links/{id}")
    public ResponseEntity<Void> deleteVideoLink(@PathVariable Long id) {
        log.debug("REST request to delete VideoLink : {}", id);
        videoLinkService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
