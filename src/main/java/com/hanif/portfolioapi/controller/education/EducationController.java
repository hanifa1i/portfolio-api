package com.hanif.portfolioapi.controller.education;

import com.hanif.portfolioapi.dto.education.EducationResponse;
import com.hanif.portfolioapi.service.EducationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/education")
@RequiredArgsConstructor
public class EducationController {

    private final EducationService educationService;

    @GetMapping
    public ResponseEntity<List<EducationResponse>> getAllEducation() {
        return ResponseEntity.ok(educationService.getAllEducation());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EducationResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(educationService.getEducationById(id));
    }
}
