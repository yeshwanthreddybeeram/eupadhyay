package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Guest;
import com.mycompany.myapp.repository.GuestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Guest}.
 */
@Service
@Transactional
public class GuestService {

    private final Logger log = LoggerFactory.getLogger(GuestService.class);

    private final GuestRepository guestRepository;

    public GuestService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    /**
     * Save a guest.
     *
     * @param guest the entity to save.
     * @return the persisted entity.
     */
    public Guest save(Guest guest) {
        log.debug("Request to save Guest : {}", guest);
        return guestRepository.save(guest);
    }

    /**
     * Get all the guests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Guest> findAll(Pageable pageable) {
        log.debug("Request to get all Guests");
        return guestRepository.findAll(pageable);
    }


    /**
     * Get one guest by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Guest> findOne(Long id) {
        log.debug("Request to get Guest : {}", id);
        return guestRepository.findById(id);
    }

    /**
     * Delete the guest by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Guest : {}", id);
        guestRepository.deleteById(id);
    }
}
