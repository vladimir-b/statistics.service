package com.n26.exercise;

import com.n26.exercise.model.Transaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StatisticsApplicationTests {
	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int randomServerPort;

	@Test
	public void addTransactionSucceeded() {
		given()
			.port(randomServerPort)
			.contentType("application/json")
			.body(new Transaction(11.1,  System.currentTimeMillis()))
		.when()
			.post("/transactions")
		.then()
			.statusCode(HttpStatus.CREATED.value());

		given()
			.port(randomServerPort)
			.contentType("application/json")
			.body(new Transaction(17.5,  System.currentTimeMillis()))
		.when()
			.post("/transactions")
		.then()
			.statusCode(HttpStatus.CREATED.value());

		given()
			.port(randomServerPort)
		.when()
			.get("/statistics")
		.then()
			.statusCode(HttpStatus.OK.value())
			.contentType(JSON)
			.body("sum", equalTo(28.6f))
			.body("avg", equalTo(14.3f))
			.body("max", equalTo(17.5f))
			.body("min", equalTo(11.1f))
			.body("count", equalTo(2));
	}


	@Test
	public void addTransactionFailedWhenTimestampPointingToTheFuture() {
		given()
			.port(randomServerPort)
			.contentType("application/json")
			.body(new Transaction(11.1,  System.currentTimeMillis() + 100 * 1000))
		.when()
			.post("/transactions")
		.then()
			.statusCode(HttpStatus.NO_CONTENT.value());
	}
}
