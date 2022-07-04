#Description
ATM emulator simple app to do simple bank operations 


#Requirements
**Java 8**

**Maven 3.6.3**

**Docker**


#Dependencies
Depends on Bank Service if not deployed no operation could be done.


#Build and deploy
* run docker command **docker-compose up** to set up environment
* OR
* Run AtmServiceApplication

#User Manual
* to connect this docker container with bank-service container you need to create network and connect those two dockers in the network. or just use external ip in bankServiceUrl
* Visit Swagger configuration to see endpoints.
* http://localhost:8081/swagger-ui/index.html
* Steps to follow once application deployed.
* First operation should be card validation
* Second Authorization
* Third doing operations.

