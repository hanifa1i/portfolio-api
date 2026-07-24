package com.hanif.portfolioapi.repository;

import com.hanif.portfolioapi.dto.artwork.ArtworkResponse;
import com.hanif.portfolioapi.model.Artwork;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtworkRepository extends JpaRepository<Artwork, Long> {
    @Query("SELECT COUNT(a) FROM Artwork a WHERE a.bookPage = true")
    long countSketchbookArt();
    @Query("SELECT COUNT(a) FROM Artwork a WHERE a.bookPage = false")
    long countDigitalArt();

    List<Artwork> findAllByBookPageFalseOrderByCreatedAtDesc(PageRequest pageRequest);

}
