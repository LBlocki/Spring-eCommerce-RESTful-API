# Spring-eCommerce-example [![CircleCI](https://circleci.com/gh/LBlocki/Spring-eCommerce-example.svg?style=shield)](https://circleci.com/gh/LBlocki/Spring-eCommerce-example) [![codecov](https://codecov.io/gh/LBlocki/Spring-eCommerce-example/branch/master/graph/badge.svg)](https://codecov.io/gh/LBlocki/Spring-eCommerce-example)
This is my spring boot application representing basic e-commerce application example. 
It still requires user authentication and shipping options to make it usefull ( and a front-end client ) but it's a good starting point.
You can manage users, orders and products using CRUD operations using RESTful API.

## Generating API documentation
   API documentation is generated during verify/install phase using Spring REST Docs. Make sure you are in eCommerce-server directory
   and excecute following command
   ```bash
   mvn clean verify
   ```
   This will perform unit and integration tests and generate documentation in html form that will avaliable under
   ```bash
   {root}/eCommerce-server/target/generated-docs/api-guide.html
   ```
## Steps to setup server side of the application ( eCommerce-server ) - for now it is entire application
   ### Using Maven ( remember that you need to be in eCommerce-server directory when excecuting maven commands )
   1.**Clone repository**
        ```bash
        https://github.com/LBlocki/Spring-eCommerce-example.git
        ```
   2. **Choose database**
        **If you wish to persist data use MySQL database:** 
        Create new database:
        ```bash
        create database e_commerce_app
        ```
        Uncomment properties in
        ```bash
        {root}/eCommerce-server/src/main/resources/application.properties
        ```
        Feel free to configure them. Also if you dont want
        application to add initial data at runtime change active profile from dev to anything else
        **Using H2 embedded database:**
        It is fast and auto configured but does not persist data after shut down. Also if you dont want
        application to add initial data at runtime change active profile from dev to anything else
    3. **Excecute**
        Run the application using maven command:
        ```bash
        mvn spring-boot:run
        ```
        You can package it into the jar file as well and then run it
        ```bash
        mvn package
        java -jar target/{name_of_snapshot}.jar
        ```
   ### Using Docker
   1.**Clone repository**
        ```bash
        https://github.com/LBlocki/Spring-eCommerce-example.git
        ```
   2. **Choose database**
        Docker is configured to use MySQL automatically so make sure to uncomment properties under
        ```bash
        {root}/eCommerce-server/src/main/resources/application.properties
        ```
        Feel free to configure them. Also if you dont want
        application to add initial data at runtime change active profile from dev to anything else
   3. **Excecute**
        Make sure you are in root directory. Excecute following commands
        ```bash
        docker-compose build
        docker-compose up
        ```
        If you want docker to run silently use -d

By default application will use 8080 port.

Feedback is appreciated!
