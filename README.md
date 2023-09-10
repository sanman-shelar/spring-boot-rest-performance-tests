# Performance Tests for API endpoints

Load tests for API endpoints.

### Requirements

* [Open JDK 17](https://adoptium.net/)

### Frameworks

* [Spring Boot 3](https://spring.io/)
* [Gatling](https://gatling.io/)

### Running Tests Locally

* Run the Spring Boot Application locally through IDE using [SpringBootRestPerformanceTestsApplication.java](src%2Fmain%2Fjava%2Fcom%2Fpt%2FSpringBootRestPerformanceTestsApplication.java) or use `mvn spring-boot:run` command in the terminal.
* API will be running on port `8080`, use http://localhost:8080/person to access the API endpoints (GET and POST supported).
* Use `mvn gatling:test` to run the performance tests. Results will be published under `target/gatling` directory.

### Open API Spec
```yaml
openapi: 3.0.1
info:
  title: OpenAPI definition
  version: v0
servers:
  - url: http://localhost:8080
    description: Generated server url
paths:
  /person:
    get:
      tags:
        - person-controller
      operationId: findAll
      parameters:
        - name: pageable
          in: query
          required: true
          schema:
            $ref: '#/components/schemas/Pageable'
      responses:
        "200":
          description: OK
          content:
            '*/*':
              schema:
                $ref: '#/components/schemas/PagePersonDto'
    post:
      tags:
        - person-controller
      operationId: createPerson
      requestBody:
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/PersonDto'
        required: true
      responses:
        "200":
          description: OK
          content:
            '*/*':
              schema:
                $ref: '#/components/schemas/PersonDto'
components:
  schemas:
    PersonDto:
      type: object
      properties:
        name:
          type: string
        age:
          type: integer
          format: int32
    Pageable:
      type: object
      properties:
        page:
          minimum: 0
          type: integer
          format: int32
        size:
          minimum: 1
          type: integer
          format: int32
        sort:
          type: array
          items:
            type: string
    PagePersonDto:
      type: object
      properties:
        totalPages:
          type: integer
          format: int32
        totalElements:
          type: integer
          format: int64
        size:
          type: integer
          format: int32
        content:
          type: array
          items:
            $ref: '#/components/schemas/PersonDto'
        number:
          type: integer
          format: int32
        sort:
          $ref: '#/components/schemas/SortObject'
        first:
          type: boolean
        last:
          type: boolean
        numberOfElements:
          type: integer
          format: int32
        pageable:
          $ref: '#/components/schemas/PageableObject'
        empty:
          type: boolean
    PageableObject:
      type: object
      properties:
        offset:
          type: integer
          format: int64
        sort:
          $ref: '#/components/schemas/SortObject'
        pageNumber:
          type: integer
          format: int32
        pageSize:
          type: integer
          format: int32
        unpaged:
          type: boolean
        paged:
          type: boolean
    SortObject:
      type: object
      properties:
        empty:
          type: boolean
        sorted:
          type: boolean
        unsorted:
          type: boolean

```