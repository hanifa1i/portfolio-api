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
@Table(name = "education")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String qualification;

    private String institution;

    private String level;

    private String grade;

    private LocalDate startDate;

    private LocalDate endDate;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Builder.Default
    @OneToMany(mappedBy = "education",  cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Certificate> certificates =  new ArrayList<>();

    private LocalDateTime updatedAt;

    @PrePersist
    protected  void onCreate() {
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected  void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void addCertificate(Certificate certificate) {
        certificates.add(certificate);
        certificate.setEducation(this);
    }
}
