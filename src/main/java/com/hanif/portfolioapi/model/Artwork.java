package com.hanif.portfolioapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "artwork")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Artwork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Boolean visible;

    private Boolean bookPage;

    private Integer pageNumber;

    private String tool;

    @Builder.Default
    @OneToMany(mappedBy = "artwork", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArtworkImage> images = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "artwork", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArtworkTagLink> artworkTagLinks = new ArrayList<>();

    @PrePersist
    protected  void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected  void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void addImage(ArtworkImage image) {
        images.add(image);
        image.setArtwork(this);
    }

    public void addTagLink(ArtworkTagLink link) {
        artworkTagLinks.add(link);
        link.setArtwork(this);
    }
}
