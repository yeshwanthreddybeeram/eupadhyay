package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A AssignmentQA.
 */
@Entity
@Table(name = "assignmentqa")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AssignmentQA implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question")
    private String question;

    @Column(name = "answer")
    private String answer;

    @Column(name = "assignment_number")
    private String assignmentNumber;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public AssignmentQA question(String question) {
        this.question = question;
        return this;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public AssignmentQA answer(String answer) {
        this.answer = answer;
        return this;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAssignmentNumber() {
        return assignmentNumber;
    }

    public AssignmentQA assignmentNumber(String assignmentNumber) {
        this.assignmentNumber = assignmentNumber;
        return this;
    }

    public void setAssignmentNumber(String assignmentNumber) {
        this.assignmentNumber = assignmentNumber;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AssignmentQA)) {
            return false;
        }
        return id != null && id.equals(((AssignmentQA) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AssignmentQA{" +
            "id=" + getId() +
            ", question='" + getQuestion() + "'" +
            ", answer='" + getAnswer() + "'" +
            ", assignmentNumber='" + getAssignmentNumber() + "'" +
            "}";
    }
}
