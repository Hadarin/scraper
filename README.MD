The application configured for scraping the job cards information filtered by software engineering from https://jobs.techstars.com/jobs.

1. The application accepts GET requests by URL http://localhost:8088/jobs?function=Software%20Engineering
2. Using Selenium WebDriver (Chrome), opens jobs from https://jobs.techstars.com/jobs filtered by Software Engineering, accepts cookies, and scrolls down to
   Load More button, click on the button, and scroll down until all job cards are loaded.
3. After the entire page loads, the application scrapes information from job cards and writes it into the DB.
