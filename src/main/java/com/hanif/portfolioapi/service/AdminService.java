package com.hanif.portfolioapi.service;

import com.hanif.portfolioapi.dto.admin.DashboardResponse;
import com.hanif.portfolioapi.repository.ArtworkRepository;
import com.hanif.portfolioapi.repository.EducationRepository;
import com.hanif.portfolioapi.repository.ExperienceRepository;
import com.hanif.portfolioapi.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final ArtworkRepository artworkRepository;
    private final SkillRepository skillRepository;
    private final EducationRepository educationRepository;
    private final ExperienceRepository experienceRepository;

    public DashboardResponse getDashboardData(){
        return DashboardResponse.builder()
                .totalArtworks(artworkRepository.countDigitalArt())
                .totalSketchbookPages(artworkRepository.countSketchbookArt())
                .totalSkills(skillRepository.count())
                .totalExperience(experienceRepository.count())
                .totalQualifications(educationRepository.count())
                .build();
    }

}
