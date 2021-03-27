package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.mycompany.myapp.domain.enumeration.FolderType;

/**
 * A StudyMaterials.
 */
@Entity
@Table(name = "study_materials")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class StudyMaterials implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "folder_name")
    private String folderName;

    @Column(name = "folder_description")
    private String folderDescription;

    @Enumerated(EnumType.STRING)
    @Column(name = "foldertype")
    private FolderType foldertype;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "study_materials_video_link",
               joinColumns = @JoinColumn(name = "study_materials_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "video_link_id", referencedColumnName = "id"))
    private Set<VideoLink> videoLinks = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFolderName() {
        return folderName;
    }

    public StudyMaterials folderName(String folderName) {
        this.folderName = folderName;
        return this;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getFolderDescription() {
        return folderDescription;
    }

    public StudyMaterials folderDescription(String folderDescription) {
        this.folderDescription = folderDescription;
        return this;
    }

    public void setFolderDescription(String folderDescription) {
        this.folderDescription = folderDescription;
    }

    public FolderType getFoldertype() {
        return foldertype;
    }

    public StudyMaterials foldertype(FolderType foldertype) {
        this.foldertype = foldertype;
        return this;
    }

    public void setFoldertype(FolderType foldertype) {
        this.foldertype = foldertype;
    }

    public Set<VideoLink> getVideoLinks() {
        return videoLinks;
    }

    public StudyMaterials videoLinks(Set<VideoLink> videoLinks) {
        this.videoLinks = videoLinks;
        return this;
    }

    public StudyMaterials addVideoLink(VideoLink videoLink) {
        this.videoLinks.add(videoLink);
        videoLink.getStudyMaterials().add(this);
        return this;
    }

    public StudyMaterials removeVideoLink(VideoLink videoLink) {
        this.videoLinks.remove(videoLink);
        videoLink.getStudyMaterials().remove(this);
        return this;
    }

    public void setVideoLinks(Set<VideoLink> videoLinks) {
        this.videoLinks = videoLinks;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StudyMaterials)) {
            return false;
        }
        return id != null && id.equals(((StudyMaterials) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StudyMaterials{" +
            "id=" + getId() +
            ", folderName='" + getFolderName() + "'" +
            ", folderDescription='" + getFolderDescription() + "'" +
            ", foldertype='" + getFoldertype() + "'" +
            "}";
    }
}
