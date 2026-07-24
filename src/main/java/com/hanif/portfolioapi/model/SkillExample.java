package com.hanif.portfolioapi.model;

import com.hanif.portfolioapi.enums.ExampleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table (name = "skill_example")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SkillExample {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id")
    private Skill skill;

    @Enumerated(EnumType.STRING)
    private ExampleType type;

    private String url;

    private String note;
}
