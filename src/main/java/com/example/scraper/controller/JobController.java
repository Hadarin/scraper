package com.example.scraper.controller;

import com.example.scraper.configs.ScraperConstants;
import com.example.scraper.service.JobScraperService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jobs")
@AllArgsConstructor
@Validated
public class JobController {

    private final JobScraperService jobScraperService;

    @GetMapping
    public ResponseEntity<?> getJobs(@RequestParam String function) throws InterruptedException {
        if (!ScraperConstants.REQ_PARAMETER.equals(function)) {
            return ResponseEntity.badRequest().body(ScraperConstants.REQ_VALIDATION_MESSAGE);
        }

        jobScraperService.saveJobsByFunction(function);
        return ResponseEntity.ok("Job cards have been saved");
    }
}
