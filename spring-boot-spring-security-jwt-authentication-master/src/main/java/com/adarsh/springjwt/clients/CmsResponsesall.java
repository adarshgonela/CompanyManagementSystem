// package com.adarsh.springjwt.clients;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpEntity;
// import org.springframework.http.HttpMethod;
// import org.springframework.http.ResponseEntity;
// import org.springframework.scheduling.annotation.Async;
// import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.transaction.annotation.Transactional;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.client.RestTemplate;

// import com.adarsh.springjwt.clientResponses.SareesResponse;

// @CrossOrigin(origins = "*")
// @RestController
// @RequestMapping("/auth")
// @Transactional
// @Async
// public class CmsResponsesall {
//  @Autowired
//     private RestTemplate restTemplate;

//     @GetMapping("/product/all")
//     @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//     public ResponseEntity<String> getallsareescontroller() {
//         String productEndpoint = "http://localhost:8765/SAREES-SERVICE/sarees/all";
//         ResponseEntity<String> response = restTemplate.getForEntity(
//                 productEndpoint,
//                 String.class);
//         return response;
//     }

//      @PostMapping("/product/save")
//     @PreAuthorize("hasRole('USER') ")
//     public ResponseEntity<String> createProduct(@RequestBody SareesResponse sareesResponse) {
//         String createsareeEndpoint = "http://localhost:8765/SAREES-SERVICE/sarees/savesaree";
//         ResponseEntity<String> response = restTemplate.exchange(
//                 createsareeEndpoint,
//                 HttpMethod.POST,
//                 new HttpEntity<>(sareesResponse),
//                 String.class
//         );
//         return response;
//     }

// }
