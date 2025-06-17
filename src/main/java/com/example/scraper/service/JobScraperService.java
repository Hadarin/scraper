package com.example.scraper.service;

import com.example.scraper.dto.JobDto;
import com.example.scraper.repo.JobCardRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class JobScraperService {

    private final JobMapper mapper;
    private final JobPageParserService jobPageParserService;
    private final JobCardRepo repo;
    public void saveJobsByFunction(String jobFunction) throws InterruptedException {
        List<JobDto> jobs = jobPageParserService.parallelPageDataToDto(jobFunction);
        repo.saveAll(mapper.convertAllDtoToEntity(jobs));
    }

}