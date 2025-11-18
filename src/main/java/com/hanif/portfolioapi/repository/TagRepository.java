package com.hanif.portfolioapi.repository;

import com.hanif.portfolioapi.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    @Query("SELECT tag.id FROM Tag tag WHERE tag.name = :name")
    Optional<Long> fingIdByName(String name);

    @Query("SELECT tag FROM Tag tag WHERE tag.name = :name")
    Optional<Tag> fingByName(String name);
}
