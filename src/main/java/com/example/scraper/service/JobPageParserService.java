package com.example.scraper.service;

import com.example.scraper.configs.ScraperConstants;
import com.example.scraper.dto.JobDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class JobPageParserService {

    private final WebDriverService webDriverService;

    public List<JobDto> parallelPageDataToDto(String jobFunction) throws InterruptedException {
        Elements jobCards = webDriverService.collectPageData(jobFunction).select("div.job-card");
        log.debug("Job cards size is " + jobCards.size());
        List<CompletableFuture<JobDto>> futures = new ArrayList<>();

        log.debug("Start collecting Job card dtos");
        for (Element card : jobCards) {
            CompletableFuture<JobDto> future = CompletableFuture.supplyAsync(() -> {
                try {
                    JobDto jobDto = new JobDto();
                    jobDto.setPositionName(card.select("h4").text());

                    Element container = card.selectFirst("div.sc-beqWaB.sc-gueYoa.jIjsZd.MYFxR");
                    if (container != null) {
                        Elements tags = container.select("div[data-testid=tag] > div");
                        jobDto.setTags(tags.stream()
                                .map(Element::text)
                                .collect(Collectors.joining(", ")));
                    }

                    String descriptionUrl = card.selectFirst("a[data-testid=job-title-link]").attr("href");
                    if (!descriptionUrl.startsWith("/companies")) {
                        return null;
                    }

                    Document fullJobCard = getFullJobCardUrlWithRetry(ScraperConstants.MAIN_URL + descriptionUrl);
                    if (fullJobCard == null) {
                        return null;
                    }

                    Element orgButton = fullJobCard.selectFirst("a[data-testid=button]");
                    jobDto.setOrganizationUrl(orgButton != null ? orgButton.attr("href") : null);

                    Element orgTitleElem = fullJobCard.selectFirst("p.sc-beqWaB.bpXRKw");
                    jobDto.setOrganizationTitle(orgTitleElem != null ? orgTitleElem.text() : null);

                    Element logoElem = fullJobCard.selectFirst("img[data-testid=image]");
                    jobDto.setLogo(logoElem != null ? logoElem.attr("src") : null);

                    Element parent = fullJobCard.selectFirst("div.fhZPis");
                    Elements fields = (parent != null) ? parent.select("div.bpXRKw") : new Elements();

                    if (!fields.isEmpty()) {
                        jobDto.setLaborFunction(fields.get(0).text());
                    }

                    if (fields.size() > 1) {
                        jobDto.setAddress(fields.get(1).text());
                    }

                    jobDto.setPostedDate(formatPostedDate(parent));
                    Element description = fullJobCard.selectFirst("[data-testid=careerPage]");
                    jobDto.setDescription(
                            description != null
                                    ? description.html()
                                    : fullJobCard.getElementsByClass("sc-beqWaB fmCCHr").html()
                    );

                    log.debug("The job-card added " + jobDto.getPositionName());
                    return jobDto;

                } catch (Exception e) {
                    log.error("Error processing card: ", e);
                    log.error(card.text());
                    return null;
                }
            });

            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        return futures.stream()
                .map(CompletableFuture::join)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private LocalDateTime formatPostedDate (Element parent) {
        if (parent == null) {
            return null;
        }
        String stringDate = parent
                .getElementsByClass("sc-beqWaB gRXpLa")
                .text()
                .replace("Posted on ", "")
                .trim();
        if (stringDate.equals("Posted 6+ months ago")) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy");
        LocalDate localDate = LocalDate.parse(stringDate, formatter);
        return localDate.atStartOfDay();
    }

    private Document getFullJobCardUrlWithRetry(String url) {
        Document fullJobCard = null;
        int maxRetries = 3;
        int attempt = 0;

        while (attempt < maxRetries) {
            try {
                fullJobCard = Jsoup.connect(url)
                        .timeout(ScraperConstants.PAGE_LOAD_TIMEOUT)
                        .get();
                break;
            } catch (IOException e) {
                attempt++;
                log.warn("Attempt " + attempt + " failed for URL: " + url);

                if (attempt == maxRetries) {
                    log.error("Failed to fetch after " + maxRetries + " attempts: " + url);
                    return null;
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    return null;
                }
            }
        }
        return fullJobCard;
    }

}
