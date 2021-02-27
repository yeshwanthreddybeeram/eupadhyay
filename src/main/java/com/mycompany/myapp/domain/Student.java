package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * The Employee entity.
 */
@ApiModel(description = "The Employee entity.")
@Entity
@Table(name = "student")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The firstname attribute.
     */
    @NotNull
    @ApiModelProperty(value = "The firstname attribute.", required = true)
    @Column(name = "user_name", nullable = false)
    private String userName;

    @NotNull
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "joindate")
    private Instant joindate;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "student_my_videos",
               joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "my_videos_id", referencedColumnName = "id"))
    private Set<MyVideos> myVideos = new HashSet<>();

    @ManyToMany(mappedBy = "students")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<ScheduleClass> scheduleClasses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public Student userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public Student fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public Student email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Student phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Instant getJoindate() {
        return joindate;
    }

    public Student joindate(Instant joindate) {
        this.joindate = joindate;
        return this;
    }

    public void setJoindate(Instant joindate) {
        this.joindate = joindate;
    }

    public Set<MyVideos> getMyVideos() {
        return myVideos;
    }

    public Student myVideos(Set<MyVideos> myVideos) {
        this.myVideos = myVideos;
        return this;
    }

    public Student addMyVideos(MyVideos myVideos) {
        this.myVideos.add(myVideos);
        myVideos.getStudents().add(this);
        return this;
    }

    public Student removeMyVideos(MyVideos myVideos) {
        this.myVideos.remove(myVideos);
        myVideos.getStudents().remove(this);
        return this;
    }

    public void setMyVideos(Set<MyVideos> myVideos) {
        this.myVideos = myVideos;
    }

    public Set<ScheduleClass> getScheduleClasses() {
        return scheduleClasses;
    }

    public Student scheduleClasses(Set<ScheduleClass> scheduleClasses) {
        this.scheduleClasses = scheduleClasses;
        return this;
    }

    public Student addScheduleClass(ScheduleClass scheduleClass) {
        this.scheduleClasses.add(scheduleClass);
        scheduleClass.getStudents().add(this);
        return this;
    }

    public Student removeScheduleClass(ScheduleClass scheduleClass) {
        this.scheduleClasses.remove(scheduleClass);
        scheduleClass.getStudents().remove(this);
        return this;
    }

    public void setScheduleClasses(Set<ScheduleClass> scheduleClasses) {
        this.scheduleClasses = scheduleClasses;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Student)) {
            return false;
        }
        return id != null && id.equals(((Student) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Student{" +
            "id=" + getId() +
            ", userName='" + getUserName() + "'" +
            ", fullName='" + getFullName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", joindate='" + getJoindate() + "'" +
            "}";
    }
}
