package com.playground.demomultithreading.controller;

import com.playground.demomultithreading.service.MultithreadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api")
public class MultithreadController {

    private final MultithreadService multithreadService;

    public MultithreadController(MultithreadService multithreadService) {
        this.multithreadService = multithreadService;
    }

    // Endpoint for the CompletableFuture approach.
    @GetMapping("/completable")
    public CompletableFuture<ResponseEntity<List<String>>> executeWithCompletable() {
        return multithreadService.executeTasksWithCompletableFuture()
                .thenApply(ResponseEntity::ok);
    }

    // Endpoint for the Callback approach.
    @GetMapping("/callback")
    public ResponseEntity<String> executeWithCallback() {
        // Provide a callback that logs the results to the console.
        String response = multithreadService.executeTasksWithCallback(results ->
                System.out.println("Callback received results: " + results)
        );
        return ResponseEntity.ok(response);
    }
}