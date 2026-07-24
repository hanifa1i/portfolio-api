package com.hanif.portfolioapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "experience")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jobTitle;

    private String companyName;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String location;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDateTime updatedAt;

    @Builder.Default
    @OneToMany(mappedBy = "experience", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkProject> projects = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "experience", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkActivity> activities = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "experience", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkSkill> skills = new ArrayList<>();

    @PrePersist
    protected  void onCreate() {
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected  void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void addProject(WorkProject project) {
        projects.add(project);
        project.setExperience(this);
    }
    public void addActivity(WorkActivity activity) {
        activities.add(activity);
        activity.setExperience(this);
    }
    public void addSkill(WorkSkill skill) {
        skills.add(skill);
        skill.setExperience(this);
    }

}
