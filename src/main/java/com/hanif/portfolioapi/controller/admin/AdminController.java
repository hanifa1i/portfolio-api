package com.hanif.portfolioapi.controller.admin;

import com.hanif.portfolioapi.dto.admin.DashboardResponse;
import com.hanif.portfolioapi.enums.EntityType;
import com.hanif.portfolioapi.model.ActivityLog;
import com.hanif.portfolioapi.service.ActivityLogService;
import com.hanif.portfolioapi.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    private final ActivityLogService activityLogService;

    @GetMapping("/count")
    public DashboardResponse getDashboard() {
        return adminService.getDashboardData();
    }

    @GetMapping("/recent")
    public List<ActivityLog> getRecentActivity() {
        return activityLogService.getRecentActivityLogs(0, 15);
    }

    @GetMapping("/recent/sketch")
    public List<ActivityLog> getRecentSketchActivity() {
        return activityLogService.getRecentActivityLogsByType(EntityType.SKETCHBOOK_PAGE, 0, 5);
    }
}
