package com.adarsh.springjwt.clients;

import com.adarsh.springjwt.clientResponses.SareesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
@Transactional
@Async
public class ClientsResponseMethods {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/product/all")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<String> getallsareescontroller() {
        String productEndpoint = "http://localhost:8765/SAREES-SERVICE/sarees/all";
        ResponseEntity<String> response = restTemplate.getForEntity(
                productEndpoint,
                String.class);
        return response;
    }

    @PostMapping("/product/save")
    @PreAuthorize("hasRole('ADMIN') ")
    public ResponseEntity<String> createProduct(@RequestBody SareesResponse sareesResponse) {
        String createsareeEndpoint = "http://localhost:8765/SAREES-SERVICE/sarees/savesaree";
        ResponseEntity<String> response = restTemplate.exchange(
                createsareeEndpoint,
                HttpMethod.POST,
                new HttpEntity<>(sareesResponse),
                String.class
        );
        return response;
    }

    @PostMapping("/product/getids")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<String> getidssareescontroller(@RequestBody List<Integer> ids) {
        // Define the URL of the external service
        String productEndpoint = "http://localhost:8765/SAREES-SERVICE/sarees/get-sarees-ids";

        // Create a request entity with the list of ids (send as JSON in body)
        HttpEntity<List<Integer>> requestEntity = new HttpEntity<>(ids);

        // Make a POST request to the external service
        ResponseEntity<String> response = restTemplate.exchange(
                productEndpoint,
                HttpMethod.POST,  // Use POST since we're sending data in the body
                requestEntity,
                String.class
        );

        // Return the response received from the external service
        return response;
    }
    @GetMapping("/product/id/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<String> getsareesUsingIDcontroller(@PathVariable("id") int id) {
        // Define the external service endpoint URL, using the 'id' from the path

        String productbtidEndpoint = "http://localhost:8765/SAREES-SERVICE/sarees/id/"+id;
        // Make a GET request to the external service using RestTemplate
        ResponseEntity<String> response = restTemplate.getForEntity(
                productbtidEndpoint,    // External service URL
                String.class        // Response type
        );

        // Return the response received from the external service
        return response;
    }



    //////////////////////////////////// BOOKING ////////////////
@PostMapping("/boooking/save")
@PreAuthorize("hasRole('USER') ")
public ResponseEntity<String> createbOOKING(@RequestBody SareesResponse sareesResponse) {
    String createbookingEndpoint = "http://localhost:8765/BOOKING-SERVICE/booking/save";
    ResponseEntity<String> response = restTemplate.exchange(
            createbookingEndpoint,
            HttpMethod.POST,
            new HttpEntity<>(sareesResponse),
            String.class
    );
    return response;
}
    @GetMapping("/booking/all")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<String> getallbookingcontroller() {
        String productEndpoint = "http://localhost:8765/BOOKING-SERVICE/booking/getall";
        ResponseEntity<String> response = restTemplate.getForEntity(
                productEndpoint,
                String.class);
        return response;
    }


    /////////////////////////////////////// CART  //////////////////
    @PostMapping("/cart/save")
    @PreAuthorize("hasRole('USER') ")
    public ResponseEntity<String> createCart(@RequestBody SareesResponse sareesResponse) {
        String createcartEndpoint = "http://localhost:8765/CARTSERVICE/cart/save";
        ResponseEntity<String> response = restTemplate.exchange(
                createcartEndpoint,
                HttpMethod.POST,
                new HttpEntity<>(sareesResponse),
                String.class
        );
        return response;
    }

    @GetMapping("/cart/all")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<String> getallcartcontroller() {
        String productEndpoint = "http://localhost:8765/CARTSERVICE/cart/all";
        ResponseEntity<String> response = restTemplate.getForEntity
                (productEndpoint,
                String.class);
        return response;
    }

    @GetMapping("/cart/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<String> getbyIdcartcontroller(@PathVariable int id) {
        String productEndpoint = "http://localhost:8765/CARTSERVICE/cart/id/"+id;
        ResponseEntity<String> response = restTemplate.getForEntity
                (productEndpoint,
                        String.class);
        return response;
    }


}
