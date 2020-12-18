# Spring boot CQRS - Two database (Command Service)
CQRS suggests dividing the Application Layer into two sides, the commands side, and the queries side.
- The queries side should be responsible and optimized for reading data. 
- The commands side should be responsible and optimized for writing data.

In the **Two-database** approach, we have two dedicated databases, one for writing operations and one for reading operations. 

Commands side has Write Database optimized for writing operations. Query side has Read Database optimized for reading operations.

The **POC Command Service**  - With every state changed by the command, the modified data has to be pushed from Write Database into the Read Database using the Events (using the  Eventual Consistency Pattern).
##
### Prerequisites
- JDK 1.8
- Maven
- Mysql
- AWS - SNS (Topic : Customer_Created)

## Quick Start

### Clone source
```
git clone https://github.com/vinodvpillai/springboot-cqrs-two-database-command.git
cd springboot-cqrs-two-database-command
```

```
MySQL START
```

### Build
```
mvn clean package
```

### Run
```
java -jar target/springboot-cqrs-two-database-command.jar
```

### Endpoint details:

#### 1. CustomerCommandRestController - Add new customer (CURL Request):

```
POST /customers HTTP/1.1
Host: localhost:8080
Content-Type: application/json
cache-control: no-cache
Postman-Token: 63ddba60-84bd-4ab5-8d5c-c4856c7e1527
{
	"firstName":"Ashok",
	"lastName":"Pillai",
	"emailId":"ashok@yopmail.com",
	"password":"ashok"
}------WebKitFormBoundary7MA4YWxkTrZu0gW--
```
