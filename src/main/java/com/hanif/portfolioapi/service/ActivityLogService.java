package com.hanif.portfolioapi.service;

import com.hanif.portfolioapi.enums.ActionType;
import com.hanif.portfolioapi.enums.EntityType;
import com.hanif.portfolioapi.model.ActivityLog;
import com.hanif.portfolioapi.repository.ActivityLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityLogService {

    private final ActivityLogRepository activityLogRepository;

    public void addActivityLog(Long entityId, EntityType entityType, ActionType actionType, String notes) {

        String description = actionType.getDisplayName() + " " + entityType.getDisplayName() + " with id: " + entityId;

        ActivityLog activityLog = ActivityLog.builder()
                .entityId(entityId)
                .entityType(entityType)
                .actionType(actionType)
                .description(description)
                .notes(notes)
                .build();

        activityLogRepository.save(activityLog);
    }

    public List<ActivityLog> getRecentActivityLogs(int page, int size) {
        return activityLogRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(page,size));
    }

    public List<ActivityLog> getRecentActivityLogsByType(EntityType entityType, int page, int size) {
        return activityLogRepository
                .findAllByEntityTypeOrderByCreatedAtDesc(
                        entityType,
                        PageRequest.of(page, size)
                );
    }
}
