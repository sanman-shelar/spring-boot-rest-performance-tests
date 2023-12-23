package com.pt.load;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.StringBody;
import static io.gatling.javaapi.core.CoreDsl.global;
import static io.gatling.javaapi.core.CoreDsl.incrementUsersPerSec;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class LoadTestPerson extends Simulation {

	private static final HttpProtocolBuilder protocol = http.baseUrl("http://localhost:8080");

	private static final ScenarioBuilder GET_PERSON_SCENARIO = scenario("GetPersonWithRampUpUsers")
		.exec(http("GetPerson").get("/person").check(status().is(200)));

	private static final ScenarioBuilder CREATE_PERSON_SCENARIO = scenario("CreatePersonWithRampUpUsers")
		.exec(http("CreatePerson").post("/person")
			.header("Content-Type", "application/json")
			.header("Accept", "application/json")
			.body(StringBody("{ \"name\": \"John\", \"age\": 45 }"))
			.check(status().is(200)));

	public LoadTestPerson() {
		setUp(CREATE_PERSON_SCENARIO.injectOpen(incrementUsersPerSec(20).times(5)
			.eachLevelLasting(Duration.ofSeconds(5L))
			.separatedByRampsLasting(Duration.ofSeconds(5L))
			.startingFrom(20)),
				GET_PERSON_SCENARIO.injectOpen(incrementUsersPerSec(20).times(5)
					.eachLevelLasting(Duration.ofSeconds(5L))
					.separatedByRampsLasting(Duration.ofSeconds(5L))
					.startingFrom(20)))
			.protocols(protocol)
			.assertions(global().successfulRequests().percent().is(100.00), global().responseTime().max().lte(100));
	}

}
