package com.example.scraper.configs;

import lombok.Data;
import org.springframework.stereotype.Component;

public final class ScraperConstants {
    public static final String MAIN_URL = "https://jobs.techstars.com/";
    public static final String BASE_URL = "https://jobs.techstars.com/jobs";
    public static final int PAGE_LOAD_TIMEOUT = 30000;
    public static final String COOKIE_BUTTON_ID = "onetrust-accept-btn-handler";
    public static final String LOAD_MORE_BUTTON_SELECTOR = "button[data-testid='load-more'][data-loading='false']";
    public static final String REQ_VALIDATION_MESSAGE = "Only 'Software Engineering' is allowed as function";
    public static final String REQ_PARAMETER = "Software Engineering";

    private ScraperConstants() {}
}