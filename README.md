# Java Spring Demo & Exercises

This repository contains two separate Java projects: a full-stack web application using Spring Boot and React, and a command-line application with a collection of basic Java algorithms.

---

## Project 1: Java Spring Demo (Web API)

A full-stack web application for managing a vehicle inventory. The backend is a REST API built with Java and Spring Boot, and the frontend is a web interface built with React.

### Prerequisites

*   [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/downloads/) (Version 17 or higher)
*   [Apache Maven](https://maven.apache.org/download.cgi)
*   [Docker](https://www.docker.com/products/docker-desktop/)
*   [Node.js and npm](https://nodejs.org/)

### How to Run

The entire application (backend, frontend, and database) is containerized and can be run with a single command using Docker Compose.

1.  **Navigate to the repository's root directory.**

2.  **Build and run the application:**
    ```shell
    docker-compose up --build
    ```

3.  **Access the application:**
    *   The frontend will be available at [http://localhost:3000](http://localhost:3000).
    *   The backend API will be available at [http://localhost:8080](http://localhost:8080).

---

## Project 2: Java Exercises (CLI Application)

A command-line application containing a collection of basic Java algorithms. This project is managed with Maven.

### Prerequisites

*   [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/downloads/) (Version 11 or higher)
*   [Apache Maven](https://maven.apache.org/download.cgi)

### How to Compile, Test, and Run

1.  **Navigate to the project's directory:**
    ```shell
    cd javaexercises
    ```

2.  **Compile the project:**
    ```shell
    mvn compile
    ```

3.  **Run the tests:**
    ```shell
    mvn test
    ```

4.  **Package the application into an executable JAR:**
    ```shell
    mvn package
    ```

5.  **Run the application:**
    ```shell
    java -jar target/java-exercises-1.0-SNAPSHOT.jar
    ```
    A menu will be displayed in the console, allowing you to choose which algorithm to execute.