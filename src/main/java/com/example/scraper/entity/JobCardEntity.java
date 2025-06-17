package com.example.scraper.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.SEQUENCE;


@Entity
@Table(name = "job_card")
@Data
@NoArgsConstructor
public class JobCardEntity {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "seqGen")
    @SequenceGenerator(name = "seqGen", sequenceName = "seq")
    private Long id;
    @Column(name = "position_name", length = 1000)
    private String positionName;
    @Column(name = "organization_url", length = 1000)
    private String organizationUrl;
    @Column(name = "logo", length = 1000)
    private String logo;
    @Column(name = "organization_title", length = 1000)
    private String organizationTitle;
    @Column(name = "labor_function", length = 1000)
    private String laborFunction;
    @Column(name = "location", length = 1000)
    private String location;
    @Column(name = "posted_date")
    private LocalDateTime postedDate;
    @Column(name = "description", length = 100000)
    private String description;
    @Column(name = "tags_names")
    private String tagsNames;

}
