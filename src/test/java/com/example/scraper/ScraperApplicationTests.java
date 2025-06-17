package com.example.scraper;

import com.example.scraper.dto.JobDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.stream.Collectors;


class ScraperApplicationTests {

    @Test
	void parseJobCard() throws IOException {

        String testCard = """
                <div class="sc-beqWaB gupdsY job-card" data-testid="job-list-item">
                			 <div width="1" itemscope itemtype="https://schema.org/JobPosting" class="sc-beqWaB sc-gueYoa diHipZ MYFxR">
                			  <div class="sc-beqWaB ghlhjW company-thumbnail" width="40px,72px">
                			   <a data-testid="company-logo-link" class="sc-beqWaB kvfNlp theme_only" href="/companies/backpack-healthcare-2?filter=eyJqb2JfZnVuY3Rpb25zIjpbIkxlZ2FsIl19#content">
                			    <div data-testid="profile-picture" class="sc-beqWaB sc-gueYoa eYExPF MYFxR">
                			     <img tx="profilePicture" data-testid="image" variant="square.image" alt="Backpack Healthcare" sx="[object Object]" loading="lazy" width="72" height="72" decoding="async" data-nimg="1" class="sc-beqWaB inltEN" style="color:transparent" src="https://cdn.getro.com/companies/c9c35d7f-f381-5073-b1cf-03b23cae7e01-1748482559">
                			    </div></a>
                			  </div>
                			  <div width="0.5" class="sc-beqWaB sc-gueYoa lpllVF MYFxR job-info">
                			   <meta itemprop="description" content="School Counselor at Backpack Healthcare">
                			   <h4 font-size="2,3" width="1" class="sc-beqWaB kKIsob"><a display="inline-block" class="sc-beqWaB daCQKK theme_only" target="_blank" href="/companies/backpack-healthcare-2/jobs/52627687-school-counselor#content" data-testid="job-title-link">
                			     <div class="sc-beqWaB sc-gueYoa iUlpOy MYFxR">
                			      <div itemprop="title" font-size="2,3" color="text.dark" font-weight="medium" class="sc-beqWaB kToBwF">
                			       School Counselor
                			      </div>
                			     </div></a></h4>
                			   <div class="sc-beqWaB bpXRKw">
                			    <div itemprop="hiringOrganization" itemscope itemtype="https://schema.org/Organization">
                			     <meta itemprop="logo" content="https://cdn.getro.com/companies/c9c35d7f-f381-5073-b1cf-03b23cae7e01-1748482559">
                			     <meta itemprop="name" content="Backpack Healthcare"><a data-testid="link" class="sc-beqWaB hwOgCj theme_only" font-size="1,2" display="inline-block" href="/companies/backpack-healthcare-2?filter=eyJqb2JfZnVuY3Rpb25zIjpbIkxlZ2FsIl19#content">Backpack Healthcare</a>
                			    </div>
                			    <div class="sc-beqWaB sc-gueYoa hHxbzX MYFxR">
                			     <div itemprop="jobLocation" itemscope itemtype="https://schema.org/Place">
                			      <meta itemprop="address" content="Maryland, USA">
                			      <div>
                			       <div color="neutral.400" class="sc-beqWaB sc-gueYoa ictnPY MYFxR">
                			        <svg xmlns="http://www.w3.org/2000/svg" width="16px" height="16px" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="sc-beqWaB dPDyZZ" aria-hidden="true">
                			         <path d="M20 10c0 6-8 12-8 12s-8-6-8-12a8 8 0 0 1 16 0Z"></path><circle cx="12" cy="10" r="3"></circle>
                			        </svg>
                			        <div font-size="1" color="text.main" class="sc-beqWaB ewYjoF">
                			         <span class="sc-beqWaB vIGjl">Maryland, USA</span>
                			        </div>
                			       </div>
                			      </div>
                			     </div>
                			     <div color="neutral.400" class="sc-beqWaB sc-gueYoa eotCAe MYFxR">
                			      <svg xmlns="http://www.w3.org/2000/svg" width="16px" height="16px" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="sc-beqWaB dPDyZZ" aria-hidden="true">
                			       <rect width="20" height="12" x="2" y="6" rx="2"></rect><circle cx="12" cy="12" r="2"></circle><path d="M6 12h.01M18 12h.01"></path>
                			      </svg>
                			      <p font-size="1" color="text.main" class="sc-beqWaB enQFes">USD 55-55 / hour</p>
                			     </div>
                			     <div class="sc-beqWaB sc-gueYoa eotCAe MYFxR added" color="neutral.400">
                			      <svg xmlns="http://www.w3.org/2000/svg" width="16px" height="16px" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="sc-beqWaB dPDyZZ" aria-hidden="true">
                			       <rect width="18" height="18" x="3" y="4" rx="2" ry="2"></rect><line x1="16" x2="16" y1="2" y2="6"></line><line x1="8" x2="8" y1="2" y2="6"></line><line x1="3" x2="21" y1="10" y2="10"></line>
                			      </svg>
                			      <div font-size="1" color="text.main" class="sc-beqWaB enQFes">
                			       1 day
                			       <meta itemprop="datePosted" content="2025-06-14">
                			      </div>
                			     </div>
                			    </div>
                			    <div class="sc-beqWaB sc-gueYoa jIjsZd MYFxR">
                			     <div data-testid="tag" class="sc-dmqHEX OHsAR">
                			      <div class="sc-dmqHEX dncTlc">
                			       Health
                			      </div>
                			     </div>
                			     <div data-testid="tag" class="sc-dmqHEX OHsAR">
                			      <div class="sc-dmqHEX dncTlc">
                			       Mental Health Care
                			      </div>
                			     </div>
                			     <div data-testid="tag" class="sc-dmqHEX OHsAR">
                			      <div class="sc-dmqHEX dncTlc">
                			       51 - 200 employees
                			      </div>
                			     </div>
                			     <div data-testid="tag" class="sc-dmqHEX OHsAR">
                			      <div class="sc-dmqHEX dncTlc">
                			       Mid-Senior Level
                			      </div>
                			     </div>
                			    </div>
                			   </div>
                			  </div>
                			  <div height="0,0,unset" width="0,0,unset" class="sc-beqWaB sc-gueYoa hcVvkM MYFxR">
                			   <a class="sc-beqWaB jCMYfS theme_only" data-testid="read-more" href="/companies/backpack-healthcare-2/jobs/52627687-school-counselor#content" data-jobsource="career_page" target="_blank" width="1" font-size="1">Read more
                			    <div class="sc-beqWaB cflHJR">
                			     about <!-- -->School Counselor<!-- --> at <!-- -->Backpack Healthcare
                			    </div>
                			    <svg xmlns="http://www.w3.org/2000/svg" width="16px" height="16px" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round" class="sc-beqWaB hchhQV fa fa-chevron-right" variant="icon" aria-hidden="true" mt="1" ml="1">
                			     <polyline points="9 18 15 12 9 6"></polyline>
                			    </svg></a>
                			  </div>
                			 </div>
                			</div>
                			""";
        Document doc = Jsoup.parse(testCard);
		Element jobCard = doc.selectFirst("div.job-card");

		JobDto jobDto = new JobDto();

		jobDto.setPositionName(jobCard.select("h4").text());

		Element container = doc.selectFirst("div.sc-beqWaB.sc-gueYoa.jIjsZd.MYFxR");
		if (container != null) {
			Elements tags = container.select("div[data-testid=tag] > div");
			String result = tags.stream()
					.map(Element::text)
					.collect(Collectors.joining(", "));
		}

		String url = "https://jobs.techstars.com";
		url += jobCard.selectFirst("a[data-testid=job-title-link]").attr("href");
		Document fullJobCard = Jsoup.connect("https://jobs.techstars.com/companies/getro/jobs/35739285-senior-backend-engineer#content").get();

		jobDto.setOrganizationTitle(fullJobCard.selectFirst("p.sc-beqWaB.bpXRKw").text());
//		jobDto.setOrganizationUrl(fullJobCard.selectFirst("a[data-testid=button]").attr("href"));
		jobDto.setLogo(fullJobCard.selectFirst("img[data-testid=image]").attr("src"));

		Element parent = fullJobCard.selectFirst("div.fhZPis");
		Elements fields = parent.select("div.bpXRKw");

		jobDto.setLaborFunction(fields.get(0).text());
		jobDto.setAddress(fields.get(1).text());
		//jobDto.setPostedDate(parent.getElementsByClass("sc-beqWaB gRXpLa").text());


		Element description = fullJobCard.selectFirst("[data-testid=careerPage]");

		if(description != null) {
			jobDto.setDescription(description.wholeText());
		} else {
			jobDto.setDescription(fullJobCard.getElementsByClass("sc-beqWaB fmCCHr").text());
		}


		System.out.println("complete");



	}

}
