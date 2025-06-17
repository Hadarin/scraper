package com.example.scraper.repo;

import com.example.scraper.entity.JobCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobCardRepo extends JpaRepository<JobCardEntity, Long> {
}
