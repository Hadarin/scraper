The application configured for scraping the job cards information filtered by Software engineering.

1. The application accepts GET request by url http://localhost:8088/jobs?function=Software%20Engineering
2. Using selenium web driver (Chrome) opens jobs filtered by Software Engineering, accepts cookies, scrolling down to
Load More button, click on the button, and scrolls down until all jobs cards loaded.
3. After the entire page loaded, the application scraps information from job cards and writes it into DB.
