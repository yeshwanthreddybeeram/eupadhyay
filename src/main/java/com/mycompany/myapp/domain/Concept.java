package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Concept.
 */
@Entity
@Table(name = "concept")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Concept implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "conceptname")
    private String conceptname;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "concept_video_link",
               joinColumns = @JoinColumn(name = "concept_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "video_link_id", referencedColumnName = "id"))
    private Set<VideoLink> videoLinks = new HashSet<>();

    @ManyToMany(mappedBy = "concepts")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Department> departments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConceptname() {
        return conceptname;
    }

    public Concept conceptname(String conceptname) {
        this.conceptname = conceptname;
        return this;
    }

    public void setConceptname(String conceptname) {
        this.conceptname = conceptname;
    }

    public Set<VideoLink> getVideoLinks() {
        return videoLinks;
    }

    public Concept videoLinks(Set<VideoLink> videoLinks) {
        this.videoLinks = videoLinks;
        return this;
    }

    public Concept addVideoLink(VideoLink videoLink) {
        this.videoLinks.add(videoLink);
        videoLink.getConcepts().add(this);
        return this;
    }

    public Concept removeVideoLink(VideoLink videoLink) {
        this.videoLinks.remove(videoLink);
        videoLink.getConcepts().remove(this);
        return this;
    }

    public void setVideoLinks(Set<VideoLink> videoLinks) {
        this.videoLinks = videoLinks;
    }

    public Set<Department> getDepartments() {
        return departments;
    }

    public Concept departments(Set<Department> departments) {
        this.departments = departments;
        return this;
    }

    public Concept addDepartment(Department department) {
        this.departments.add(department);
        department.getConcepts().add(this);
        return this;
    }

    public Concept removeDepartment(Department department) {
        this.departments.remove(department);
        department.getConcepts().remove(this);
        return this;
    }

    public void setDepartments(Set<Department> departments) {
        this.departments = departments;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Concept)) {
            return false;
        }
        return id != null && id.equals(((Concept) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Concept{" +
            "id=" + getId() +
            ", conceptname='" + getConceptname() + "'" +
            "}";
    }
}
