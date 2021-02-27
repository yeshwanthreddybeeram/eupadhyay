package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.MyVideos;
import com.mycompany.myapp.service.MyVideosService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.MyVideos}.
 */
@RestController
@RequestMapping("/api")
public class MyVideosResource {

    private final Logger log = LoggerFactory.getLogger(MyVideosResource.class);

    private static final String ENTITY_NAME = "myVideos";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MyVideosService myVideosService;

    public MyVideosResource(MyVideosService myVideosService) {
        this.myVideosService = myVideosService;
    }

    /**
     * {@code POST  /my-videos} : Create a new myVideos.
     *
     * @param myVideos the myVideos to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new myVideos, or with status {@code 400 (Bad Request)} if the myVideos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/my-videos")
    public ResponseEntity<MyVideos> createMyVideos(@RequestBody MyVideos myVideos) throws URISyntaxException {
        log.debug("REST request to save MyVideos : {}", myVideos);
        if (myVideos.getId() != null) {
            throw new BadRequestAlertException("A new myVideos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MyVideos result = myVideosService.save(myVideos);
        return ResponseEntity.created(new URI("/api/my-videos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /my-videos} : Updates an existing myVideos.
     *
     * @param myVideos the myVideos to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated myVideos,
     * or with status {@code 400 (Bad Request)} if the myVideos is not valid,
     * or with status {@code 500 (Internal Server Error)} if the myVideos couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/my-videos")
    public ResponseEntity<MyVideos> updateMyVideos(@RequestBody MyVideos myVideos) throws URISyntaxException {
        log.debug("REST request to update MyVideos : {}", myVideos);
        if (myVideos.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MyVideos result = myVideosService.save(myVideos);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, myVideos.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /my-videos} : get all the myVideos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of myVideos in body.
     */
    @GetMapping("/my-videos")
    public List<MyVideos> getAllMyVideos() {
        log.debug("REST request to get all MyVideos");
        return myVideosService.findAll();
    }

    /**
     * {@code GET  /my-videos/:id} : get the "id" myVideos.
     *
     * @param id the id of the myVideos to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the myVideos, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/my-videos/{id}")
    public ResponseEntity<MyVideos> getMyVideos(@PathVariable Long id) {
        log.debug("REST request to get MyVideos : {}", id);
        Optional<MyVideos> myVideos = myVideosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(myVideos);
    }

    /**
     * {@code DELETE  /my-videos/:id} : delete the "id" myVideos.
     *
     * @param id the id of the myVideos to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/my-videos/{id}")
    public ResponseEntity<Void> deleteMyVideos(@PathVariable Long id) {
        log.debug("REST request to delete MyVideos : {}", id);
        myVideosService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
