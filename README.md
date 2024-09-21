![](https://img.shields.io/github/actions/workflow/status/moonlightmoth/neoflexskillassessment/test-workflow.yml)

# NeoflexSkillAssessment
This repository contains completed skill assessment task for Neoflex Study center.

README is avaliable in russian [here](https://github.com/moonlightMoth/NeoflexSkillAssessment/blob/main/README_RU.md)

<details>
  <summary><h3>Table of Contents<h3></summary>
  <ol>
    <li><a href="#general-information">General information</a></li>
    <li>
      <a href="#build-and-execution">Build and execution</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li> <a href="#build">Build</a></li>
        <li><a href="#execution">Execution</a></li>
      </ul>
    </li>
    <li>
	    <a href="#request-implementation">Request implementation</a>
	    <ul>
	    <li><a href="#ports-and-endpoints">Ports and endpoints</a></li>
	    <li><a href="#short-form-request">Short form request</a></li>
	    <li><a href="#full-form-request">Full form request</a></li>
	    <li><a href="#bad-request">Bad request</a></li>
	    </ul>
    </li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>


## General information

In accordance with the task, Spring Boot microservice which has one GET mapping `/calculate` [^1] was implemented.
In short, application accepts `GET /calculate` request with **query** parameters containing sum of salary for last 365 days and length of vacation in different forms and return full leave payment for this vacation. Forms of requests and query parameters are described in [Request implementation](#request-implementation) section.
Application uses Spring Boot as web framework, Maven as build system, JUnit and Spring Boot testing.
Additionally GitHub Actions workflow file for build tests and Dockerfile were implemented.
  
[^1]: Task says `GET "/calculacte"`, but i assumed `/calculate` was meant


## Build and execution
### Prerequisites
* Java 11 (Developed on Eclipse Temurin)
* Maven or Docker

### Build
There are two ways to build application form sources:
1. Using Maven
2. Using Docker
#### Maven
```
git clone https://github.com/moonlightmoth/neoflexskillassessment
cd neoflexskillassessment
mvn clean compile install spring-boot:repackage
```
Executable jar `neoflexskillassessment-<version>.jar` will be under `target/` directory.

#### Docker
```
git clone https://github.com/moonlightmoth/neoflexskillassessment
cd neoflexskillassessment
docker build . --tag <your_tag>
```

### Execution

#### Maven
```
java -jar target/neoflexskillassessment-<version>.jar
```

#### Docker
```
docker run -d -p <any_port>:8080 <your_tag>
```

## Request Implementation

### Ports and endpoints
On startup application listens http port 8080 and accepts requests to `/calculate` endpoint.
Request **must** provide **query** parameters on one of two forms: [short](#short-form-request) form or [full] (#full-form-request) form.

### Short form request

Short form request **must** contain query parameters:
 *  `avgSalary` double representing sum salary for last 365 days 
 *  `vacationLength` integer representing number of vacation days

This type of request assumes that all given days are paid, so uses following formula:
$$
leavePayment = \frac{avgSalary}{365} * vacationDays
$$
Be aware thar cents are always rounded in favour of employee.
Example short form request on localhost:
```
http://localhost:8080/calculate?avgSalary=36500&vacationLength=1
``` 
This request should return 100.00.

### Full Form request

Full form request **must** contain query parameters:
  * `avgSalary` representing sum salary for last 365 days 
  * `fromDate` in format `dd.MM.yy` representing vacation starting date
  * `dueToDate`  in format `dd.MM.yy` representing last day of vacation

`fromDate` **must** be less or equal to `dueToDate`, both support **only** format `dd.MM.yy`.
This type of request returns leave payment assuming that weekends and state holidays are unpaid.
Information about holidays is given at build time via `src/main/resources/holidays` file in format `dd.MM.yy\n` for each holiday. [^2]

Example full form request on localhost:
```
http://localhost:8080/calculate?avgSalary=36500&fromDate=01.01.24&dueToDate=12.01.24
``` 
This request should return 400.00 with default holidays configuration.

[^2]: This file approach is a bit ugly as i can say, but i assume that i can't rely on external resources to retrieve holidays during this skill assessment. I suppose that in production there will be another maintaned service for holidays tracking.

### Bad request

Any request that does not follow full or short form templates are considered bad and are not processed.
`/calculate` endpoint gives small hint of correct params format, but other endpoints return `404` default Spring not found message.


## Contacts

Feel free to contact me either via [Telegram](https://t.me/moonlightmoth) (more preferably) or vasilev.iv.dev@mail.ru.
