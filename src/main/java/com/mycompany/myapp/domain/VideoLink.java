package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A VideoLink.
 */
@Entity
@Table(name = "video_link")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class VideoLink implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "classlink", nullable = false)
    private String classlink;

    @ManyToMany(mappedBy = "videoLinks")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Concept> concepts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClasslink() {
        return classlink;
    }

    public VideoLink classlink(String classlink) {
        this.classlink = classlink;
        return this;
    }

    public void setClasslink(String classlink) {
        this.classlink = classlink;
    }

    public Set<Concept> getConcepts() {
        return concepts;
    }

    public VideoLink concepts(Set<Concept> concepts) {
        this.concepts = concepts;
        return this;
    }

    public VideoLink addConcept(Concept concept) {
        this.concepts.add(concept);
        concept.getVideoLinks().add(this);
        return this;
    }

    public VideoLink removeConcept(Concept concept) {
        this.concepts.remove(concept);
        concept.getVideoLinks().remove(this);
        return this;
    }

    public void setConcepts(Set<Concept> concepts) {
        this.concepts = concepts;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VideoLink)) {
            return false;
        }
        return id != null && id.equals(((VideoLink) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VideoLink{" +
            "id=" + getId() +
            ", classlink='" + getClasslink() + "'" +
            "}";
    }
}
