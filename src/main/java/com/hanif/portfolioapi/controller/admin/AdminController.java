package com.hanif.portfolioapi.controller.admin;

import com.hanif.portfolioapi.dto.admin.DashboardResponse;
import com.hanif.portfolioapi.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/dashboard")
    public DashboardResponse getDashboard() {
        return adminService.getDashboardData();
    }
}
