package com.hanif.portfolioapi.service;

import com.hanif.portfolioapi.dto.education.EducationRequest;
import com.hanif.portfolioapi.dto.education.EducationResponse;
import com.hanif.portfolioapi.model.Education;
import com.hanif.portfolioapi.repository.EducationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EducationService {

    private final EducationRepository educationRepository;

    public List<EducationResponse> getAllEducation() {
        return educationRepository.findAll().stream()
                .map(this::toResponse).toList();
    }

    public void createEducation(EducationRequest request) {
        Education education = mapToEntity(request);
        educationRepository.save(education);
    }

    public void updateEducation(Long id, EducationRequest request) {
        Education education = mapToEntity(request);
        education.setId(id);
        educationRepository.save(education);
    }

    public void deleteEducation(Long id) {
        educationRepository.deleteById(id);
    }

    private EducationResponse toResponse(Education education) {
        return EducationResponse.builder()
                .id(education.getId())
                .qualification(education.getQualification())
                .institution(education.getInstitution())
                .level(education.getLevel())
                .grade(education.getGrade())
                .startDate(education.getStartDate())
                .endDate(education.getEndDate())
                .description(education.getDescription())
                .updatedAt(education.getUpdatedAt())
                .build();
    }

    private Education mapToEntity(EducationRequest request) {
        return Education.builder()
                .qualification(request.getQualification())
                .institution(request.getInstitution())
                .level(request.getLevel())
                .grade(request.getGrade())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .description(request.getDescription())
                .build();
    }

}
