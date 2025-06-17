package com.example.scraper.service;

import com.example.scraper.dto.JobDto;
import com.example.scraper.entity.JobCardEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobMapper {

    public List<JobCardEntity> convertAllDtoToEntity(List<JobDto> dtoList) {
        return dtoList.stream()
                .map(JobMapper::JobCardEntity)
                .collect(Collectors.toList());
    }

    private static JobCardEntity JobCardEntity(JobDto dto) {
        JobCardEntity entity = new JobCardEntity();
        entity.setPositionName(dto.getPositionName());
        entity.setOrganizationUrl(dto.getOrganizationUrl());
        entity.setLogo(dto.getLogo());
        entity.setOrganizationTitle(dto.getOrganizationTitle());
        entity.setLaborFunction(dto.getLaborFunction());
        entity.setLocation(dto.getAddress());
        entity.setPostedDate(dto.getPostedDate());
        entity.setDescription(dto.getDescription());
        entity.setTagsNames(dto.getTags());
        return entity;
    }

}
