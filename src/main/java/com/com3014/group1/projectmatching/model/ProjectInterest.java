package com.com3014.group1.projectmatching.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Database representation of a @Project Interest
 *
 * @author Daniel
 */
@Entity
@Table(name = "project_interests")
public class ProjectInterest implements Serializable {

    @Id
    @Column(name = "interest_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer interestId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id")
    private ProjectEntity project;

    @NotNull
    @Column(name = "interest")
    private String interest;

    public ProjectInterest() {
    }

    public Integer getInterestId() {
        return interestId;
    }

    public void setInterestId(Integer interestId) {
        this.interestId = interestId;
    }

    public void setProject(ProjectEntity project) {
        this.project = project;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.interestId);
        hash = 37 * hash + Objects.hashCode(this.project);
        hash = 37 * hash + Objects.hashCode(this.interest);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProjectInterest other = (ProjectInterest) obj;
        if (!Objects.equals(this.interest, other.interest)) {
            return false;
        }
        if (!Objects.equals(this.interestId, other.interestId)) {
            return false;
        }
        if (!Objects.equals(this.project, other.project)) {
            return false;
        }
        return true;
    }

}
