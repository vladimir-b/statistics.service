package com.n26.exercise;

import com.n26.exercise.model.Statistics;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

// TODO: restassured

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionApplicationTests {
	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void contextLoads() {
		Statistics body = this.restTemplate.getForObject("/statistics", Statistics.class);
		// assertThat(body).isEqualTo("Hello World");

	}


}
