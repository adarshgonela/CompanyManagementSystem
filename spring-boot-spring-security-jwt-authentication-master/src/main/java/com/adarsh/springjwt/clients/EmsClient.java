package com.adarsh.springjwt.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.adarsh.springjwt.clientResponses.EmsDtoClient;

@FeignClient(name = "EMPLOYEEMANAGEMENTSYSTEM", url = "http://localhost:8765/EMPLOYEEMANAGEMENTSYSTEM/api/employees")
public interface EmsClient {
    @PostMapping 
    public ResponseEntity<EmsDtoClient> createEmployee(@RequestBody EmsDtoClient emsDtoClient);
    @GetMapping("/page")
     public ResponseEntity<List<EmsDtoClient>> getPaginatedEmployees(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) ;

}
