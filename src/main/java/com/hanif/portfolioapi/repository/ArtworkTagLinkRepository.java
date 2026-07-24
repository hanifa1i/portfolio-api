package com.hanif.portfolioapi.repository;

import com.hanif.portfolioapi.model.Artwork;
import com.hanif.portfolioapi.model.ArtworkTagLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtworkTagLinkRepository  extends JpaRepository<ArtworkTagLink, Long> {

    @Query("SELECT atl.artwork FROM ArtworkTagLink atl WHERE atl.tag.id = :tagId")
    public List<Artwork> findByTagId(Long tagId);
}
