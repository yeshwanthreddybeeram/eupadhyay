package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.ScheduleClassService;
import com.mycompany.myapp.domain.ScheduleClass;
import com.mycompany.myapp.repository.ScheduleClassRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ScheduleClass}.
 */
@Service
@Transactional
public class ScheduleClassServiceImpl implements ScheduleClassService {

    private final Logger log = LoggerFactory.getLogger(ScheduleClassServiceImpl.class);

    private final ScheduleClassRepository scheduleClassRepository;

    public ScheduleClassServiceImpl(ScheduleClassRepository scheduleClassRepository) {
        this.scheduleClassRepository = scheduleClassRepository;
    }

    @Override
    public ScheduleClass save(ScheduleClass scheduleClass) {
        log.debug("Request to save ScheduleClass : {}", scheduleClass);
        return scheduleClassRepository.save(scheduleClass);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleClass> findAll() {
        log.debug("Request to get all ScheduleClasses");
        return scheduleClassRepository.findAllWithEagerRelationships();
    }


    public Page<ScheduleClass> findAllWithEagerRelationships(Pageable pageable) {
        return scheduleClassRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ScheduleClass> findOne(Long id) {
        log.debug("Request to get ScheduleClass : {}", id);
        return scheduleClassRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ScheduleClass : {}", id);
        scheduleClassRepository.deleteById(id);
    }
}
