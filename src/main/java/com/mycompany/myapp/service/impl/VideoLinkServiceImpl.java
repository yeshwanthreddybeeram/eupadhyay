package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.VideoLinkService;
import com.mycompany.myapp.domain.VideoLink;
import com.mycompany.myapp.repository.VideoLinkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link VideoLink}.
 */
@Service
@Transactional
public class VideoLinkServiceImpl implements VideoLinkService {

    private final Logger log = LoggerFactory.getLogger(VideoLinkServiceImpl.class);

    private final VideoLinkRepository videoLinkRepository;

    public VideoLinkServiceImpl(VideoLinkRepository videoLinkRepository) {
        this.videoLinkRepository = videoLinkRepository;
    }

    @Override
    public VideoLink save(VideoLink videoLink) {
        log.debug("Request to save VideoLink : {}", videoLink);
        return videoLinkRepository.save(videoLink);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VideoLink> findAll() {
        log.debug("Request to get all VideoLinks");
        return videoLinkRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<VideoLink> findOne(Long id) {
        log.debug("Request to get VideoLink : {}", id);
        return videoLinkRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete VideoLink : {}", id);
        videoLinkRepository.deleteById(id);
    }
}
