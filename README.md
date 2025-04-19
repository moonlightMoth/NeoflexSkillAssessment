# NeoflexSkillAssessment
This is an application assignment for Neoflex study center.

## API
Api consists of one endpoint
`/calculate` in two forms:
- `/calculate?avgSalary=<salary>&vacationLength=<length>` - 
returns leave payment amount paid to employee with average monthly salary `<salary>` 
and vacation length `<length>`. Holidays and weekends are considered paid days.
- `/calculate?avgSalary=<salary>&startDate=<startDate>&endDate=<endDate>` - 
returns leave payment amount paid to employee with average monthly salary `<salary>`
for period from `<startDate>` to `<endDate>`. Holidays and weekends are considered unpaid days.

On success response body consists of fields `message` and `leavePayment`.
Example of success response listed below.

```
{
    "message": "The amount of leave payment for employee",
    "leavePayment": "81911.26"
}
```

On failure response body contains description message.

```
{
    "message":"Either vacationLength must be > 0 or both startDate and endDate present"
}
```

## Build & Run

### Maven
In project root

```
maven clean compile package
java -jar target/neoflexskillassessment-0.1.jar
```



### Docker
In project root

```
docker build --tag <tag> .
docker run -p <port>:8080 <tag>
```