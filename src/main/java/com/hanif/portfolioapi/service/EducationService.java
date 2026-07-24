package com.hanif.portfolioapi.service;

import com.hanif.portfolioapi.dto.common.ImageResponse;
import com.hanif.portfolioapi.dto.common.UpdateImagesRequest;
import com.hanif.portfolioapi.dto.education.EducationRequest;
import com.hanif.portfolioapi.dto.education.EducationResponse;
import com.hanif.portfolioapi.enums.ActionType;
import com.hanif.portfolioapi.enums.EntityType;
import com.hanif.portfolioapi.exceptions.NotFoundException;
import com.hanif.portfolioapi.model.Certificate;
import com.hanif.portfolioapi.model.Education;
import com.hanif.portfolioapi.repository.EducationRepository;
import com.hanif.portfolioapi.validation.ResponseMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EducationService {

    private final EducationRepository educationRepository;
    private final ActivityLogService activityLogService;
    private final S3Service s3Service;

    public List<EducationResponse> getAllEducation() {
        return educationRepository.findAll().stream()
                .map(this::toResponse).toList();
    }

    public EducationResponse getEducationById(Long id) {
        return educationRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new NotFoundException(String.format(ResponseMessages.EDUCATION_NOT_FOUND, id)));
    }
    public Long createEducation(EducationRequest request) {
        Education education = mapToEntity(request, 0);
        educationRepository.save(education);
        activityLogService.addActivityLog(education.getId(), EntityType.EDUCATION, ActionType.CREATE, null);

        return education.getId();
    }

    public void updateEducation(Long id, EducationRequest request) {
        educationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(ResponseMessages.EDUCATION_NOT_FOUND, id)));

        Education education = mapToEntity(request, id);
        education.setId(id);
        educationRepository.save(education);
        activityLogService.addActivityLog(education.getId(), EntityType.EDUCATION, ActionType.UPDATE, null);

    }

    public String uploadCertificate(Long id, MultipartFile file) throws IOException {

        String url = s3Service.uploadFile("certificate", id, file);

        Education updatedEducation = updateEducationCertificate(id, url);

        educationRepository.save(updatedEducation);

        return url;
    }

    public List<String> updateCertificateImages(Long id, UpdateImagesRequest request) {

        for (String url : request.getImages()) {
            Education updatedEducation = updateEducationCertificate(id, url);
            educationRepository.save(updatedEducation);
        }

        return request.getImages();
    }



    public void deleteEducation(Long id) {
        Education education = educationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(ResponseMessages.EDUCATION_NOT_FOUND, id)));

        education.getCertificates().stream()
                .map(Certificate::getUrl)
                .map(this::extractKey)
                .forEach(s3Service::deleteFile);
        educationRepository.deleteById(id);

        activityLogService.addActivityLog(education.getId(), EntityType.EDUCATION, ActionType.DELETE, null);

    }

    public String deleteCertificateImage(Long educationId, Long imageId) {

        Education education = educationRepository.findById(educationId)
                .orElseThrow(() -> new NotFoundException(String.format(ResponseMessages.EDUCATION_NOT_FOUND, educationId)));

        Certificate certificate = getCertificateImage(education, imageId);

        String key = extractKey(certificate.getUrl());

        s3Service.deleteFile(key);

        education.getCertificates().remove(certificate);

        educationRepository.save(education);

        return key;
    }

    private Certificate getCertificateImage(Education education, Long imageId) {
        return education.getCertificates()
                .stream()
                .filter(img -> img.getId().equals(imageId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(
                        String.format(ResponseMessages.IMAGE_NOT_FOUND, imageId)
                ));
    }
    private String extractKey(String imageUrl) {
        int index = imageUrl.indexOf(".com/") + 5;

        return imageUrl.substring(index);
    }

    private Education updateEducationCertificate(Long id, String url) {

        Education education = educationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(ResponseMessages.EDUCATION_NOT_FOUND, id)));

        Certificate certificate = Certificate.builder()
                .education(education)
                .url(url)
                .build();

        List<Certificate> certificates = education.getCertificates();

        certificates.add(certificate);

        return education;
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
                .certificates(
                        education.getCertificates().stream()
                                .map(image -> ImageResponse.builder()
                                        .id(image.getId())
                                        .imageUrl(image.getUrl())
                                        .build())
                                .toList())
                .updatedAt(education.getUpdatedAt())
                .build();
    }

    private Education mapToEntity(EducationRequest request, long id) {
        Education education = Education.builder()
                .qualification(request.getQualification())
                .institution(request.getInstitution())
                .level(request.getLevel())
                .grade(request.getGrade())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .description(request.getDescription())
                .build();

        if (id != 0 && request.getCertificates() != null) {
            for (String certificate : request.getCertificates()) {
                Certificate certificates = Certificate.builder()
                        .id(id)
                        .education(education)
                        .url(certificate).build();

                education.addCertificate(certificates);
            }
        }
        return education;
    }

}
