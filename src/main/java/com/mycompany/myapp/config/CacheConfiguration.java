package com.mycompany.myapp.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import io.github.jhipster.config.cache.PrefixedKeyGenerator;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {
    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.mycompany.myapp.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.mycompany.myapp.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.mycompany.myapp.domain.User.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Authority.class.getName());
            createCache(cm, com.mycompany.myapp.domain.User.class.getName() + ".authorities");
            createCache(cm, com.mycompany.myapp.domain.Department.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Department.class.getName() + ".concepts");
            createCache(cm, com.mycompany.myapp.domain.Department.class.getName() + ".employees");
            createCache(cm, com.mycompany.myapp.domain.Student.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Student.class.getName() + ".myVideos");
            createCache(cm, com.mycompany.myapp.domain.Student.class.getName() + ".scheduleClasses");
            createCache(cm, com.mycompany.myapp.domain.Employee.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Employee.class.getName() + ".scheduleClasses");
            createCache(cm, com.mycompany.myapp.domain.Employee.class.getName() + ".departments");
            createCache(cm, com.mycompany.myapp.domain.Concept.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Concept.class.getName() + ".videoLinks");
            createCache(cm, com.mycompany.myapp.domain.Concept.class.getName() + ".departments");
            createCache(cm, com.mycompany.myapp.domain.VideoLink.class.getName());
            createCache(cm, com.mycompany.myapp.domain.VideoLink.class.getName() + ".concepts");
            createCache(cm, com.mycompany.myapp.domain.ScheduleClass.class.getName());
            createCache(cm, com.mycompany.myapp.domain.ScheduleClass.class.getName() + ".students");
            createCache(cm, com.mycompany.myapp.domain.ScheduleClass.class.getName() + ".employees");
            createCache(cm, com.mycompany.myapp.domain.MyVideos.class.getName());
            createCache(cm, com.mycompany.myapp.domain.MyVideos.class.getName() + ".students");
            createCache(cm, com.mycompany.myapp.domain.Assignment.class.getName());
            createCache(cm, com.mycompany.myapp.domain.AssignmentQA.class.getName());
            createCache(cm, com.mycompany.myapp.domain.ClassIdentity_.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Guest.class.getName());
            createCache(cm, com.mycompany.myapp.domain.StudyMaterials.class.getName());
            createCache(cm, com.mycompany.myapp.domain.StudyMaterials.class.getName() + ".videoLinks");
            createCache(cm, com.mycompany.myapp.domain.VideoLink.class.getName() + ".studyMaterials");
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
