package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Guest;
import com.mycompany.myapp.service.GuestService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Guest}.
 */
@RestController
@RequestMapping("/api")
public class GuestResource {

    private final Logger log = LoggerFactory.getLogger(GuestResource.class);

    private static final String ENTITY_NAME = "guest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GuestService guestService;

    public GuestResource(GuestService guestService) {
        this.guestService = guestService;
    }

    /**
     * {@code POST  /guests} : Create a new guest.
     *
     * @param guest the guest to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new guest, or with status {@code 400 (Bad Request)} if the guest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/guests")
    public ResponseEntity<Guest> createGuest(@RequestBody Guest guest) throws URISyntaxException {
        log.debug("REST request to save Guest : {}", guest);
        if (guest.getId() != null) {
            throw new BadRequestAlertException("A new guest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Guest result = guestService.save(guest);
        return ResponseEntity.created(new URI("/api/guests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /guests} : Updates an existing guest.
     *
     * @param guest the guest to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated guest,
     * or with status {@code 400 (Bad Request)} if the guest is not valid,
     * or with status {@code 500 (Internal Server Error)} if the guest couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/guests")
    public ResponseEntity<Guest> updateGuest(@RequestBody Guest guest) throws URISyntaxException {
        log.debug("REST request to update Guest : {}", guest);
        if (guest.getId() == null) {
            Guest result = guestService.save(guest);
            return ResponseEntity.created(new URI("/api/guests/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
                .body(result);
        }
        Guest result = guestService.save(guest);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, guest.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /guests} : get all the guests.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of guests in body.
     */
    @GetMapping("/guests")
    public ResponseEntity<List<Guest>> getAllGuests(Pageable pageable) {
        log.debug("REST request to get a page of Guests");
        Page<Guest> page = guestService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /guests/:id} : get the "id" guest.
     *
     * @param id the id of the guest to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the guest, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/guests/{id}")
    public ResponseEntity<Guest> getGuest(@PathVariable Long id) {
        log.debug("REST request to get Guest : {}", id);
        Optional<Guest> guest = guestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(guest);
    }

    /**
     * {@code DELETE  /guests/:id} : delete the "id" guest.
     *
     * @param id the id of the guest to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/guests/{id}")
    public ResponseEntity<Void> deleteGuest(@PathVariable Long id) {
        log.debug("REST request to delete Guest : {}", id);
        guestService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
