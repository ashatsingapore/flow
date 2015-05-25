package com.leanflow.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.leanflow.app.domain.util.CustomDateTimeDeserializer;
import com.leanflow.app.domain.util.CustomDateTimeSerializer;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ProcessStep.
 */
@Entity
@Table(name = "PROCESSSTEP")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProcessStep implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 5)
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Size(min = 5)
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "created_by")
    private String createdBy;

    @NotNull
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "creation_time", nullable = false)
    private DateTime creationTime;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "accepted_time", nullable = false)
    private DateTime acceptedTime;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "completed_time", nullable = false)
    private DateTime completedTime;

    @ManyToOne
    @JoinColumn(name="process", referencedColumnName = "title", insertable = false, updatable = false)
    private Process process;

    @OneToMany
    @JoinColumn(name = "authority", referencedColumnName="name", insertable = false, updatable = false)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Authority> authoritys = new HashSet<Authority>();

    @OneToOne
    @JoinColumn(name="createdUser", referencedColumnName = "id", insertable = false, updatable = false)
    private User createdUser;

    @OneToOne
    @JoinColumn(name="acceptedBy", referencedColumnName = "id", insertable = false, updatable = false)
    private User acceptedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public DateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(DateTime creationTime) {
        this.creationTime = creationTime;
    }

    public DateTime getAcceptedTime() {
        return acceptedTime;
    }

    public void setAcceptedTime(DateTime acceptedTime) {
        this.acceptedTime = acceptedTime;
    }

    public DateTime getCompletedTime() {
        return completedTime;
    }

    public void setCompletedTime(DateTime completedTime) {
        this.completedTime = completedTime;
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public Set<Authority> getAuthoritys() {
        return authoritys;
    }

    public void setAuthoritys(Set<Authority> authoritys) {
        this.authoritys = authoritys;
    }

    public User getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(User user) {
        this.createdUser = user;
    }

    public User getAcceptedBy() {
        return acceptedBy;
    }

    public void setAcceptedBy(User user) {
        this.acceptedBy = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProcessStep processStep = (ProcessStep) o;

        if ( ! Objects.equals(id, processStep.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProcessStep{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", title='" + title + "'" +
                ", description='" + description + "'" +
                ", createdBy='" + createdBy + "'" +
                ", creationTime='" + creationTime + "'" +
                ", acceptedTime='" + acceptedTime + "'" +
                ", completedTime='" + completedTime + "'" +
                '}';
    }
}
