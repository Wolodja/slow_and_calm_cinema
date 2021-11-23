# Slow & Calm Cinema

Cinema backend application.

---
### Application details

* Language: Kotlin
* Framework: Spring Boot
* Database: PostgreSQL
* Database migration: Liquibase
* Security: Basic Authentication
* API documentation: OpenAPI

 ---

### How to run

* To start database run `docker-compose up`
* Run application with argument `omdb.api-key={your_key_here}`
* Owner user credentials: `username: calm_owner, password: calm_password`
* Base URL: `http://localhost:8081`
* OpenAPI: `/v3/api-docs`
* SwaggerUI: `/swagger-ui.html`

---

### Implementation details and future plans

#### General currently
* OMDB API used to fetch movies data
* Movies are fetch only once on startup
* Used PostgreSQL that runs in docker
* Database schema is created on startup with liquibase

#### General future
* Profiles
* DDD?

#### Security currently
* To secure endpoints used Spring Security with Basic Authentication. 
* Owner account is created on startup

#### Security future
* Migrate to JWT

#### Movie currently
* Possibility to get movie by Id

#### Movie future
* Possibility to get movie by other properties
* Possibility to get movies with pagination
* More validation

#### Users currently
* Possibility to register a VIEWER account
* User roles: VIEWER and OWNER.

#### Users future
* Login functionality
* Possibility for users to have multiple roles
* Possibility to add OWNER user
* ADMIN role

#### Showings currently
* Get showings for movie
* OWNER user can create showing
* OWNER user can update showing

#### Showings future
* Housekeeping old showings
* Currency for price
* More validation

#### Voting currently
* VIEWER can add a vote for a movie

#### Voting future
* Possibility to update and delete vote for VIEWER
* Possibility to manage votes by OWNER/ADMIN

#### Tests currently
* Unit tests

#### Tests future
* Increase test coverage
* Integration tests
* Controller test/E2E