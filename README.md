# Spring Boot Multithreading Sample Project

This project demonstrates two approaches to multithreading in a Spring Boot application using Java 21 and Gradle. The project uses a custom thread pool executor to run tasks concurrently in two different ways:

1. **CompletableFuture Approach:**  
   Uses `CompletableFuture.supplyAsync()` with a thread pool to run tasks concurrently. Once all tasks finish, it aggregates and returns the results as a JSON response.

2. **Callback Approach:**  
   Uses a thread pool along with a `CountDownLatch` to execute tasks concurrently. Once all tasks complete, it triggers a callback (printing the results to the console) and returns a confirmation message.

## Project Structure

- **build.gradle:**  
  Gradle build file configured for Java 21 and Spring Boot.

- **src/main/java/com/example/demo/DemoApplication.java:**  
  Main application entry point.

- **src/main/java/com/example/demo/config/ThreadPoolConfig.java:**  
  Configures a `ThreadPoolTaskExecutor` for managing threads.

- **src/main/java/com/example/demo/service/MultithreadService.java:**  
  Contains the logic for executing tasks using both the CompletableFuture and callback approaches.

- **src/main/java/com/example/demo/controller/MultithreadController.java:**  
  Exposes REST endpoints to trigger each multithreading approach:
    - `/api/completable`
    - `/api/callback`

## How to Run

1. **Prerequisites:**
    - Java 21 installed.
    - Gradle installed (or use the provided Gradle wrapper).

2. **Running the Application:**

   Open a terminal in the project directory and run:
   ```bash
   ./gradlew bootRun