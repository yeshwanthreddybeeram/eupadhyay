package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A MyVideos.
 */
@Entity
@Table(name = "my_videos")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MyVideos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "video_link")
    private String videoLink;

    @Column(name = "conceptname")
    private String conceptname;

    @Column(name = "schedule_time")
    private Instant scheduleTime;

    @ManyToMany(mappedBy = "myVideos")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Student> students = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public MyVideos videoLink(String videoLink) {
        this.videoLink = videoLink;
        return this;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public String getConceptname() {
        return conceptname;
    }

    public MyVideos conceptname(String conceptname) {
        this.conceptname = conceptname;
        return this;
    }

    public void setConceptname(String conceptname) {
        this.conceptname = conceptname;
    }

    public Instant getScheduleTime() {
        return scheduleTime;
    }

    public MyVideos scheduleTime(Instant scheduleTime) {
        this.scheduleTime = scheduleTime;
        return this;
    }

    public void setScheduleTime(Instant scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public MyVideos students(Set<Student> students) {
        this.students = students;
        return this;
    }

    public MyVideos addStudent(Student student) {
        this.students.add(student);
        student.getMyVideos().add(this);
        return this;
    }

    public MyVideos removeStudent(Student student) {
        this.students.remove(student);
        student.getMyVideos().remove(this);
        return this;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MyVideos)) {
            return false;
        }
        return id != null && id.equals(((MyVideos) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MyVideos{" +
            "id=" + getId() +
            ", videoLink='" + getVideoLink() + "'" +
            ", conceptname='" + getConceptname() + "'" +
            ", scheduleTime='" + getScheduleTime() + "'" +
            "}";
    }
}
