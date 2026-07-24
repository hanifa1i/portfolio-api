package com.hanif.portfolioapi.repository;

import com.hanif.portfolioapi.enums.EntityType;
import com.hanif.portfolioapi.model.ActivityLog;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {

    List<ActivityLog> findAllByOrderByCreatedAtDesc(PageRequest pageRequest);

    List<ActivityLog> findAllByEntityTypeOrderByCreatedAtDesc(EntityType entityType, Pageable pageable);
}
