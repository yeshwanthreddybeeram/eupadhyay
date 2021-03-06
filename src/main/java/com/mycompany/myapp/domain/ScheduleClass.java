package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A ScheduleClass.
 */
@Entity
@Table(name = "schedule_class")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ScheduleClass implements Serializable, Comparable<ScheduleClass> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "schedulelink", nullable = false)
    private String schedulelink;

    @NotNull
    @Column(name = "schedule_time", nullable = false)
    private Instant scheduleTime;

    @NotNull
    @Column(name = "studentname", nullable = false)
    private String studentname;

    @NotNull
    @Column(name = "employeename", nullable = false)
    private String employeename;

    @Column(name = "videolink")
    private String videolink;

    @Column(name = "complete")
    private Boolean complete;

    @Column(name = "remove")
    private Boolean remove;

    @Column(name = "concept")
    private String concept;

    @Column(name = "overview")
    private String overview;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "schedule_class_student",
               joinColumns = @JoinColumn(name = "schedule_class_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"))
    private Set<Student> students = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "schedule_class_employee",
               joinColumns = @JoinColumn(name = "schedule_class_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id"))
    private Set<Employee> employees = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSchedulelink() {
        return schedulelink;
    }

    public ScheduleClass schedulelink(String schedulelink) {
        this.schedulelink = schedulelink;
        return this;
    }

    public void setSchedulelink(String schedulelink) {
        this.schedulelink = schedulelink;
    }

    public Instant getScheduleTime() {
        return scheduleTime;
    }

    public ScheduleClass scheduleTime(Instant scheduleTime) {
        this.scheduleTime = scheduleTime;
        return this;
    }

    public void setScheduleTime(Instant scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    public String getStudentname() {
        return studentname;
    }

    public ScheduleClass studentname(String studentname) {
        this.studentname = studentname;
        return this;
    }

    public void setStudentname(String studentname) {
        this.studentname = studentname;
    }

    public String getEmployeename() {
        return employeename;
    }

    public ScheduleClass employeename(String employeename) {
        this.employeename = employeename;
        return this;
    }

    public void setEmployeename(String employeename) {
        this.employeename = employeename;
    }

    public String getVideolink() {
        return videolink;
    }

    public ScheduleClass videolink(String videolink) {
        this.videolink = videolink;
        return this;
    }

    public void setVideolink(String videolink) {
        this.videolink = videolink;
    }

    public Boolean isComplete() {
        return complete;
    }

    public ScheduleClass complete(Boolean complete) {
        this.complete = complete;
        return this;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }

    public Boolean isRemove() {
        return remove;
    }

    public ScheduleClass remove(Boolean remove) {
        this.remove = remove;
        return this;
    }

    public void setRemove(Boolean remove) {
        this.remove = remove;
    }

    public String getConcept() {
        return concept;
    }

    public ScheduleClass concept(String concept) {
        this.concept = concept;
        return this;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public String getOverview() {
        return overview;
    }

    public ScheduleClass overview(String overview) {
        this.overview = overview;
        return this;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public ScheduleClass students(Set<Student> students) {
        this.students = students;
        return this;
    }

    public ScheduleClass addStudent(Student student) {
        this.students.add(student);
        student.getScheduleClasses().add(this);
        return this;
    }

    public ScheduleClass removeStudent(Student student) {
        this.students.remove(student);
        student.getScheduleClasses().remove(this);
        return this;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public ScheduleClass employees(Set<Employee> employees) {
        this.employees = employees;
        return this;
    }

    public ScheduleClass addEmployee(Employee employee) {
        this.employees.add(employee);
        employee.getScheduleClasses().add(this);
        return this;
    }

    public ScheduleClass removeEmployee(Employee employee) {
        this.employees.remove(employee);
        employee.getScheduleClasses().remove(this);
        return this;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ScheduleClass)) {
            return false;
        }
        return id != null && id.equals(((ScheduleClass) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ScheduleClass{" +
            "id=" + getId() +
            ", schedulelink='" + getSchedulelink() + "'" +
            ", scheduleTime='" + getScheduleTime() + "'" +
            ", studentname='" + getStudentname() + "'" +
            ", employeename='" + getEmployeename() + "'" +
            ", videolink='" + getVideolink() + "'" +
            ", complete='" + isComplete() + "'" +
            ", remove='" + isRemove() + "'" +
            ", concept='" + getConcept() + "'" +
            ", overview='" + getOverview() + "'" +
            "}";
    }
    @Override
    public int compareTo(ScheduleClass o) {
        Instant scheduleTime=((ScheduleClass)o).getScheduleTime();
        /* For Ascending order*/
        return scheduleTime.compareTo(this.scheduleTime);
    }}
