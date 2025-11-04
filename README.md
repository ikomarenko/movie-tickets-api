# Movie Tickets API

## Tools
* Java 21
* Gradle 8.14.3
* Junit

## Run Application locally

### Run in the IntelliJ
**Requires a Java 21 or higher and Gradle 8.14.3 installed**

Run command below:
```
gradle clean build
```

Then start a `MovieTicketsApiApplication` as a Spring Boot Application. 
No configuration is required

### Run without IntelliJ
**Requires a Java 21 or a higher version installed**

Download the project. In the movie-tickets-api navigate to build/libs folder.
Open a terminal and run command below:

```
java -jar movie-tickets-api-0.0.1-SNAPSHOT.jar
```

Once application is running, open another terminal and execute the following:

```
curl --location 'http://localhost:8080/transactions/report' \
--header 'Content-Type: application/json' \
--header 'Accept: application/json' \
--data '{
    "transactionId": 1,
    "customers": [
        {
            "name": "John Smith",
            "age": 70
        },
        {
            "name": "Jane Doe",
            "age": 5
        },
        {
            "name": "Bob Doe",
            "age": 6
        }
    ]
}'
```

You should get a successful response back.

