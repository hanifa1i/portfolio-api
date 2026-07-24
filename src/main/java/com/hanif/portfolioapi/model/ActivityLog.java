package com.hanif.portfolioapi.model;

import com.hanif.portfolioapi.enums.ActionType;
import com.hanif.portfolioapi.enums.EntityType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "activity_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long entityId;

    @Enumerated(EnumType.STRING)
    private EntityType entityType;

    @Enumerated(EnumType.STRING)
    private ActionType actionType;

    private String description;

    private LocalDateTime createdAt;

    private String notes;

    @PrePersist
    protected  void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
