REST API calculate statistics  from the last 60 second.

POST /transactions 

Everytime a new transaction happened this endpoint will be called.
Body

{
    "amount": 12.3,
    "timestamp": 1478192204000
}

Returns empty body with either 201 or 204.

where:

* amount is a double specifying the amount
* time is a long specifying unix timestamp in milliseconds.

GET /statistics

This is the main endpoint of this task, this endpoint is executed in constant time nad memory (O(1)).
It returns the statistics, which related to the transactions which happened in the last 60 seconds.

Returns:

{
    "sun": 1000,
    "avg": 100,
    "max": 200,
    "min": 50,
    "count": 10
}

where:

* sum is a double specifying the total sum of transaction values in the last 60 seconds.
* avg is a double specifying the average amount of transaction values in the last 60 second.
* max is a double specifying single highest transaction value in the last 60 seconds.
* min is a double specifying single lowest transaction value in the last 60 second.
* count is a long specifying the total amount of transaction happened in the last 60 seconds.

To run service locally:
mvn spring-boot:run
Service is listening on port 8080 by default.

To execute tests:
mvn test