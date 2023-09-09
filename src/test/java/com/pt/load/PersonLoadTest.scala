package com.pt.load

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.concurrent.duration._
import scala.language.postfixOps

class PersonLoadTest extends Simulation {

  val protocol: HttpProtocolBuilder = http.baseUrl("http://localhost:8080")

  val getPersonScenario: ScenarioBuilder = scenario("GetPersonWithRampUpUsers")
    .exec(http("GetPerson").get("/person"))

  val createPersonScenario: ScenarioBuilder = scenario("CreatePersonWithRampUpUsers")
    .exec(
      http("CreatePerson")
        .post("/person")
        .header("accept", "application/json")
        .header("content-type", "application/json")
        .body(StringBody("""{ "name": "John", "age": 45 }""".stripMargin))
    )

  setUp(
    createPersonScenario.inject(
      incrementUsersPerSec(20)
        .times(5)
        .eachLevelLasting(5 seconds)
        .separatedByRampsLasting(5 seconds)
        .startingFrom(20)
    )
    , getPersonScenario.inject(
      incrementUsersPerSec(20)
        .times(5)
        .eachLevelLasting(5 seconds)
        .separatedByRampsLasting(5 seconds)
        .startingFrom(20)
    )).protocols(protocol)
    .assertions(
      global.successfulRequests.percent.is(100),
      global.responseTime.max.lt(100)
    )

}