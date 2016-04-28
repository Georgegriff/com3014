package com.com3014.group1.projectmatching.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 * Database representation of a @User Match
 *
 * @author Daniel
 */
@Entity
@Table(name = "user_matches")
public class UserMatch implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "set_id")
    private Integer setId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @NotNull
    @Column(name = "cache_expire")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cacheExpire;

    @Column(name = "status_control")
    private String statusControl;

    public UserMatch() {
    }

    public Integer getSetId() {
        return setId;
    }

    public void setSetId(Integer setId) {
        this.setId = setId;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Date getCacheExpire() {
        return cacheExpire;
    }

    public void setCacheExpire(Date cacheExpire) {
        this.cacheExpire = cacheExpire;
    }

    public String getStatusControl() {
        return statusControl;
    }

    public void setStatusControl(String statusControl) {
        this.statusControl = statusControl;
    }

    public UserEntity getUser() {
        return user;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + Objects.hashCode(this.setId);
        hash = 13 * hash + Objects.hashCode(this.user);
        hash = 13 * hash + Objects.hashCode(this.cacheExpire);
        hash = 13 * hash + Objects.hashCode(this.statusControl);
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
        final UserMatch other = (UserMatch) obj;
        if (!Objects.equals(this.statusControl, other.statusControl)) {
            return false;
        }
        if (!Objects.equals(this.setId, other.setId)) {
            return false;
        }
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        if (!Objects.equals(this.cacheExpire, other.cacheExpire)) {
            return false;
        }
        return true;
    }

}
