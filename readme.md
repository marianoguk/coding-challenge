# **Real time Transaction Statistics API**

## **1.Resources**

### **1.1.- Transactions**

#### **POST /transactions**

This endpoint is called to create a new transaction. It MUST execute in constant time and memory (O(1)).

Body:

```json
    {
    "amount": "12.3343",
    "timestamp": "2018-07-17T09:59:51.312Z"
    }
```

Where:
- amount – transaction amount; a string of arbitrary length that is parsable as a BigDecimal
- timestamp – transaction time in the ISO 8601 format `YYYY-MM-DDThh:mm:ss.sssZ `in the UTC timezone (this is not the current timestamp)

**Returns**

Empty body with one of the following:
201 – in case of success
204 – if the transaction is older than 60 seconds
400 – if the JSON is invalid
422 – if any of the fields are not parsable or the transaction date is in the future

**DELETE /transactions**

This endpoint causes all existing transactions to be deleted
The endpoint should accept an empty request body and return a 204 status code.

### **1.2.-Statistics**

#### **GET /statistics**
This endpoint returns the statistics based on the transactions that happened in the last 60 seconds.

Returns:
```json
     {
        "sum": "1000.00",
        "avg": "100.53",
        "max": "200000.49",
        "min": "50.23",
        "count": 10
        }
```
Where:
- sum – a BigDecimal specifying the total sum of transaction value in the last 60 seconds
- avg – a BigDecimal specifying the average amount of transaction value in the - last 60 seconds
- max – a BigDecimal specifying single highest transaction value in the last 60 seconds
- min – a BigDecimal specifying single lowest transaction value in the last 60 seconds
- count – a long specifying the total number of transactions that happened in the last 60 seconds
  
All BigDecimal values always contain exactly two decimal places and use `HALF_ROUND_UP` rounding. eg: 10.345 is returned as 10.35, 10.8 is returned as 10.80

**2.- Frameworks versions**
-Java: 11

-Spring Boot: 2.5.1

-JUnit: Since the tests in the src/it are using JUnit 4 ‘junit-vintage-engine’ dependency has been added.

## **3.- Production-ready Features**

Taking advantage of the functionalities provided by Spring Boot Actuators the following endpoint have been exposed:

### **1. Enabled actuator list**

#### **GET http://{server-host}:{server-port}/actuator**

Response sample for http://localhost:8080/actuator:

```json
   {
       "_links": {
           "self": {
                "href": "http://localhost:8080/actuator",
                "templated": false
           },
           "health-path": {
                "href": "http://localhost:8080/actuator/health/{*path}",
                "templated": true
           },
           "health": {
                "href": "http://localhost:8080/actuator/health",
                "templated": false
           },
           "info": {
               "href": "http://localhost:8080/actuator/info",
               "templated": false
           }
       }
   }
```

**3. Application health check**

GET http://{server-host}:{server-port}/actuator/health

Response sample for http://localhost:8080/actuator/health:

```json
    {
           "status": "UP",
           "components": {
               "diskSpace": {
                    "status": "UP",
                   "details": {
                       "total": 982900588544,
                       "free": 825508515840,
                       "threshold": 10485760,
                       "exists": true
                   }
               },
               "ping": {
                    "status": "UP"
               }
           }
       }

```

## **3. Application information**

**GET http://{server-host}:{server-port}/actuator/info**

```json
    {
           "app": {
               "name": "coding-challenge",
               "description": "Real time Transaction Statistics API",
               "version": "1.0.2",
               "encoding": "UTF-8",
               "java": {
                    "version": "11.0.7"
               }
           },
           "build": {
               "artifact": "coding-challenge",
               "name": "coding-challenge",
               "time": "2021-06-28T00:44:40.098Z",
               "version": "1.0.2",
               "group": "com.n26"
           }
       }

```

### **Rest API Documentation**

Rest API documentation has been exposed using Spring Boot - Swagger integration:

**-Swagger UI**: http://{server-host}:{server-port}/swagger-ui/index.html

**-API Docs**: http://{server-host}:{server-port}/v2/api-docs

