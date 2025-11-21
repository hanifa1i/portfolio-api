package com.hanif.portfolioapi.controller.education;

import com.hanif.portfolioapi.dto.education.EducationRequest;
import com.hanif.portfolioapi.service.EducationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/education")
@RequiredArgsConstructor
public class AdminEducationController {

    private final EducationService educationService;

    @PostMapping("/create")
    public void createEducation(@RequestBody EducationRequest educationRequest) {
        educationService.createEducation(educationRequest);
    }

    @PatchMapping("/{id}/update")
    public void updateEducation(@PathVariable Long id, @RequestBody EducationRequest educationRequest) {
        educationService.updateEducation(id, educationRequest);
    }

    @DeleteMapping("/{id}/delete")
    public void deleteEducation(@PathVariable Long id) {
        educationService.deleteEducation(id);
    }

}
