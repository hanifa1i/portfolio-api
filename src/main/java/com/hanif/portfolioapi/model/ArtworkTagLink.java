package com.hanif.portfolioapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "artwork_tag_link")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArtworkTagLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "artwork_id")
    private Artwork artwork;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;
}
