package com.hanif.portfolioapi.service;

import com.hanif.portfolioapi.dto.tag.MultipleTagRequest;
import com.hanif.portfolioapi.dto.tag.TagRequest;
import com.hanif.portfolioapi.dto.tag.TagResponse;
import com.hanif.portfolioapi.model.Tag;
import com.hanif.portfolioapi.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public List<TagResponse> getAllTags() {
        return tagRepository.findAll().stream()
                .map(this::toResponse).toList();
    }
    public void createMultipleTags(MultipleTagRequest request) {
        List<Tag> tags = request.getTags().stream()
                .map(tag -> Tag.builder()
                        .type(tag.getType().toLowerCase().trim())
                        .name(tag.getTag().toLowerCase().trim())
                        .build()
                )
                .toList();

        List<Tag> filtered = tags.stream()
                .filter(tag -> !tagRepository.existsByTypeAndName(tag.getType(), tag.getName()))
                .toList();

        tagRepository.saveAll(filtered);
    }

    private TagResponse toResponse(Tag tag){
        return TagResponse.builder()
                .id(tag.getId())
                .name(tag.getName())
                .type(tag.getType())
                .build();
    }
}
