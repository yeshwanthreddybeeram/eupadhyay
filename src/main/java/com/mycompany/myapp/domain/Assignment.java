package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.mycompany.myapp.domain.enumeration.Assignmentstatus;

/**
 * A Assignment.
 */
@Entity
@Table(name = "assignment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Assignment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "subject")
    private String subject;

    @Column(name = "description")
    private String description;

    @Column(name = "studentloginname")
    private String studentloginname;

    @Column(name = "employeeloginname")
    private String employeeloginname;

    @Column(name = "status")
    private String status;

    @Column(name = "submitdate")
    private Instant submitdate;

    @Column(name = "marks")
    private Integer marks;

    @Column(name = "remarks")
    private String remarks;

    @Enumerated(EnumType.STRING)
    @Column(name = "asgnstatus")
    private Assignmentstatus asgnstatus;

    @Column(name = "assignmentlink")
    private String assignmentlink;

    @Column(name = "submitlink")
    private String submitlink;

    @Lob
    @Column(name = "assignmentpdf")
    private byte[] assignmentpdf;

    @Column(name = "assignmentpdf_content_type")
    private String assignmentpdfContentType;

    @Lob
    @Column(name = "submitpdf")
    private byte[] submitpdf;

    @Column(name = "submitpdf_content_type")
    private String submitpdfContentType;

    @Column(name = "assignment_number")
    private String assignmentNumber;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public Assignment subject(String subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public Assignment description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStudentloginname() {
        return studentloginname;
    }

    public Assignment studentloginname(String studentloginname) {
        this.studentloginname = studentloginname;
        return this;
    }

    public void setStudentloginname(String studentloginname) {
        this.studentloginname = studentloginname;
    }

    public String getEmployeeloginname() {
        return employeeloginname;
    }

    public Assignment employeeloginname(String employeeloginname) {
        this.employeeloginname = employeeloginname;
        return this;
    }

    public void setEmployeeloginname(String employeeloginname) {
        this.employeeloginname = employeeloginname;
    }

    public String getStatus() {
        return status;
    }

    public Assignment status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getSubmitdate() {
        return submitdate;
    }

    public Assignment submitdate(Instant submitdate) {
        this.submitdate = submitdate;
        return this;
    }

    public void setSubmitdate(Instant submitdate) {
        this.submitdate = submitdate;
    }

    public Integer getMarks() {
        return marks;
    }

    public Assignment marks(Integer marks) {
        this.marks = marks;
        return this;
    }

    public void setMarks(Integer marks) {
        this.marks = marks;
    }

    public String getRemarks() {
        return remarks;
    }

    public Assignment remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Assignmentstatus getAsgnstatus() {
        return asgnstatus;
    }

    public Assignment asgnstatus(Assignmentstatus asgnstatus) {
        this.asgnstatus = asgnstatus;
        return this;
    }

    public void setAsgnstatus(Assignmentstatus asgnstatus) {
        this.asgnstatus = asgnstatus;
    }

    public String getAssignmentlink() {
        return assignmentlink;
    }

    public Assignment assignmentlink(String assignmentlink) {
        this.assignmentlink = assignmentlink;
        return this;
    }

    public void setAssignmentlink(String assignmentlink) {
        this.assignmentlink = assignmentlink;
    }

    public String getSubmitlink() {
        return submitlink;
    }

    public Assignment submitlink(String submitlink) {
        this.submitlink = submitlink;
        return this;
    }

    public void setSubmitlink(String submitlink) {
        this.submitlink = submitlink;
    }

    public byte[] getAssignmentpdf() {
        return assignmentpdf;
    }

    public Assignment assignmentpdf(byte[] assignmentpdf) {
        this.assignmentpdf = assignmentpdf;
        return this;
    }

    public void setAssignmentpdf(byte[] assignmentpdf) {
        this.assignmentpdf = assignmentpdf;
    }

    public String getAssignmentpdfContentType() {
        return assignmentpdfContentType;
    }

    public Assignment assignmentpdfContentType(String assignmentpdfContentType) {
        this.assignmentpdfContentType = assignmentpdfContentType;
        return this;
    }

    public void setAssignmentpdfContentType(String assignmentpdfContentType) {
        this.assignmentpdfContentType = assignmentpdfContentType;
    }

    public byte[] getSubmitpdf() {
        return submitpdf;
    }

    public Assignment submitpdf(byte[] submitpdf) {
        this.submitpdf = submitpdf;
        return this;
    }

    public void setSubmitpdf(byte[] submitpdf) {
        this.submitpdf = submitpdf;
    }

    public String getSubmitpdfContentType() {
        return submitpdfContentType;
    }

    public Assignment submitpdfContentType(String submitpdfContentType) {
        this.submitpdfContentType = submitpdfContentType;
        return this;
    }

    public void setSubmitpdfContentType(String submitpdfContentType) {
        this.submitpdfContentType = submitpdfContentType;
    }


    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Assignment)) {
            return false;
        }
        return id != null && id.equals(((Assignment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Assignment{" +
            "id=" + getId() +
            ", subject='" + getSubject() + "'" +
            ", description='" + getDescription() + "'" +
            ", studentloginname='" + getStudentloginname() + "'" +
            ", employeeloginname='" + getEmployeeloginname() + "'" +
            ", status='" + getStatus() + "'" +
            ", submitdate='" + getSubmitdate() + "'" +
            ", marks=" + getMarks() +
            ", remarks='" + getRemarks() + "'" +
            ", asgnstatus='" + getAsgnstatus() + "'" +
            ", assignmentlink='" + getAssignmentlink() + "'" +
            ", submitlink='" + getSubmitlink() + "'" +
            ", assignmentpdf='" + getAssignmentpdf() + "'" +
            ", assignmentpdfContentType='" + getAssignmentpdfContentType() + "'" +
            ", submitpdf='" + getSubmitpdf() + "'" +
            ", submitpdfContentType='" + getSubmitpdfContentType() + "'" +
            "}";
    }
}
