# Cayetano Entry Task

## Table of Contents

- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Usage](#usage)

## Project Structure
All the relevant code for the project is inside the `/src` folder.
Under main is the actual backend logic and under test are a few test classes with
tests for the most important methods.

Inside of `main/java/com/cayetano/entrytask/`, there are 3 folders:
 - **controller** - consists of a controller class and custom exception handling classes 
 - **entity** - consists of a class for a card
 - **service** - consists of the service implementation, handling the methods from the controllers

## Prerequisites
The project has been built with the following languages and frameworks:
- JDK 17 
- Spring boot 3.1.4
- Maven

The project was created in [start.spring.io](https://start.spring.io/). The dependencies used are
Spring Web and Spring DevTools, with the latter being used for development purposes only.

## Usage
I have tested and played around with the project using Postman, since I find it easy to send
HTTP request through it. Example requests:

- localhost:8080/start?balance=-5 - this will throw an exception, since a game can not be started with a negative balance
- localhost:8080/start?balance=100 - this requests will start a new game

After you have successfully started a game, you can try out the shuffle and bet requets:
- localhost:8080/bet?stake=10&higher=true
- localhost:8080/shuffle