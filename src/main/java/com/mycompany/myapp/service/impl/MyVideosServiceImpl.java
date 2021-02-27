package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.MyVideosService;
import com.mycompany.myapp.domain.MyVideos;
import com.mycompany.myapp.repository.MyVideosRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link MyVideos}.
 */
@Service
@Transactional
public class MyVideosServiceImpl implements MyVideosService {

    private final Logger log = LoggerFactory.getLogger(MyVideosServiceImpl.class);

    private final MyVideosRepository myVideosRepository;

    public MyVideosServiceImpl(MyVideosRepository myVideosRepository) {
        this.myVideosRepository = myVideosRepository;
    }

    @Override
    public MyVideos save(MyVideos myVideos) {
        log.debug("Request to save MyVideos : {}", myVideos);
        return myVideosRepository.save(myVideos);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MyVideos> findAll() {
        log.debug("Request to get all MyVideos");
        return myVideosRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<MyVideos> findOne(Long id) {
        log.debug("Request to get MyVideos : {}", id);
        return myVideosRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MyVideos : {}", id);
        myVideosRepository.deleteById(id);
    }
}
