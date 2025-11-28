package com.hanif.portfolioapi.service;

import com.hanif.portfolioapi.dto.education.EducationRequest;
import com.hanif.portfolioapi.dto.education.EducationResponse;
import com.hanif.portfolioapi.exceptions.NotFoundException;
import com.hanif.portfolioapi.model.Education;
import com.hanif.portfolioapi.repository.EducationRepository;
import com.hanif.portfolioapi.validation.ResponseMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EducationService {

    private final EducationRepository educationRepository;

    public List<EducationResponse> getAllEducation() {
        return educationRepository.findAll().stream()
                .map(this::toResponse).toList();
    }

    public Long createEducation(EducationRequest request) {
        Education education = mapToEntity(request);
        educationRepository.save(education);

        return education.getId();
    }

    public void updateEducation(Long id, EducationRequest request) {
        educationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(ResponseMessages.EDUCATION_NOT_FOUND, id)));

        Education education = mapToEntity(request);
        education.setId(id);
        educationRepository.save(education);
    }

    public void deleteEducation(Long id) {
        educationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(ResponseMessages.EDUCATION_NOT_FOUND, id)));

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
