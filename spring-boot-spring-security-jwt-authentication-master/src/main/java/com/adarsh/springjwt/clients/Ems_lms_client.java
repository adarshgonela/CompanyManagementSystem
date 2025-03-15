package com.adarsh.springjwt.clients;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.adarsh.springjwt.clientResponses.EmsDtoClient;
import com.adarsh.springjwt.clientResponses.Leavetypedtoclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/ems")
@Transactional
@Async
public class Ems_lms_client {
 @Autowired
    private RestTemplate restTemplate;

 @PostMapping("/product/save")
    // @PreAuthorize("hasRole('ADMIN') ")
    public ResponseEntity<String> createemployee(@RequestBody EmsDtoClient emsDtoClient) {
        String createemployeeendpoint = "http://localhost:8765/EMPLOYEEMANAGEMENTSYSTEM/api/employees/save";
        ResponseEntity<String> response = restTemplate.exchange(
            createemployeeendpoint,
                HttpMethod.POST,
                new HttpEntity<>(emsDtoClient),
                String.class
        );
        return response;
    }
    


    // @PostMapping("/product/save")
    // @PreAuthorize("hasRole('ADMIN') ")
    public ResponseEntity<String> createleaverowafterregister(@RequestBody Leavetypedtoclient leavetypedtoclient) {
        String createemployeeendpoint = "http://localhost:8765/LEAVEMANAGEMENTSYSTEM/leavetype/save";
        ResponseEntity<String> response = restTemplate.exchange(
            createemployeeendpoint,
                HttpMethod.POST,
                new HttpEntity<>(leavetypedtoclient),
                String.class
        );
        return response;
    }
    


    @GetMapping("/name")
    private String nema(){
        return " i am adarsh";
    }
}
