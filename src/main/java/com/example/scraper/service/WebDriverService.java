package com.example.scraper.service;

import com.example.scraper.configs.ScraperConstants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Base64;
import java.util.Random;

@Service
@Slf4j
@AllArgsConstructor
public class WebDriverService {
    public Document collectPageData(String jobFunction) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get(encodeUrl(jobFunction));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));

        Thread.sleep(2000);
        WebElement acceptCookiesBtn = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(By.id(ScraperConstants.COOKIE_BUTTON_ID)));
        log.debug("Accepting cookies");
        acceptCookiesBtn.click();

        log.debug("Starting scrolling before Load More Button");
        mouseWheelScroll(driver);

        WebElement loadMoreBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(ScraperConstants.LOAD_MORE_BUTTON_SELECTOR)));
        log.debug("Clicking Load More Button");
        loadMoreBtn.click();

        log.debug("Starting scrolling until all page data is collected");
        mouseWheelScroll(driver);

        Document doc = Jsoup.parse(driver.getPageSource());
        driver.quit();
        log.debug("The page data is collected");
        return doc;
    }

    private String encodeUrl(String jobFunction) {
        String toEncode = "{\"job_functions\":[\"" + jobFunction + "\"]}";
        String encoded = Base64.getEncoder().encodeToString(toEncode.getBytes(StandardCharsets.UTF_8));
        return ScraperConstants.BASE_URL + "?filter=" + encoded;
    }

    private void mouseWheelScroll(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Random random = new Random();

        long lastHeight = (Long) js.executeScript("return document.body.scrollHeight");
        long currentPos = 0;
        long newHeight;

        while (currentPos < lastHeight) {
            int wheelClicks = random.nextInt(3) + 3;
            int pixelsPerClick = random.nextInt(50) + 100;

            for (int i = 0; i < wheelClicks; i++) {
                currentPos += pixelsPerClick;
                js.executeScript("window.scrollBy(0, " + pixelsPerClick + ");");
                randomPause(random);
            }

            newHeight = (Long) js.executeScript("return document.body.scrollHeight");

            if (newHeight > lastHeight) lastHeight = newHeight;
        }
    }

    private void randomPause(Random random) {
        try {
            int pauseTime = random.nextInt(200 - 80) + 80;
            Thread.sleep(pauseTime);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
