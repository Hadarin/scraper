package com.example.scraper.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class JobDto {

    private String positionName;
    private String organizationUrl;
    private String logo;
    private String organizationTitle;
    private String laborFunction;
    private String address;
    private LocalDateTime postedDate;
    private String description;
    private String tags;

}

