package com.hanif.portfolioapi.model;

import com.hanif.portfolioapi.enums.ExperienceLocation;
import com.hanif.portfolioapi.enums.SkillType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "skill")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private SkillType skillType;

    @ElementCollection(targetClass = ExperienceLocation.class)
    @CollectionTable(name = "skill_experience_location", joinColumns = @JoinColumn(name = "skill_id"))
    @Column(name = "experience_location")
    @Enumerated(EnumType.STRING)
    private Set<ExperienceLocation> experienceLocations;

    @Builder.Default
    @OneToMany(mappedBy = "skill", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SkillExample> examples = new ArrayList<>();

    private LocalDateTime updatedAt;

    private Boolean visible;

    @PrePersist
    protected  void onCreate() {
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected  void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
