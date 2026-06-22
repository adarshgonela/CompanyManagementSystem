package com.adarsh.springjwt.clients;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.adarsh.springjwt.clientResponses.EmsDtoClient;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/ems")
@Transactional
@Async
public class Ems_lms_client {

    private final EmsClient emsClient;

    public Ems_lms_client(EmsClient emsClient) {
        this.emsClient = emsClient;
    }

    @PostMapping("/employee/save")
    public ResponseEntity<EmsDtoClient> createEmployee(EmsDtoClient emsDtoClient) {
        return emsClient.createEmployee(emsDtoClient);
    }

    @GetMapping("/employee/all")
    public ResponseEntity<List<EmsDtoClient>> getPaginatedEmployees(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return emsClient.getPaginatedEmployees(page, size);
    }

    @GetMapping("/name")
    // @PreAuthorize("hasRole('USER') ")
    private String name() {
        return " i am adarsh";
    }
}
