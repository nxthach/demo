package com.example.demo.web.rest;

import com.example.demo.client.IdentityServiceScimClient;
import com.example.demo.domain.Customer;
import com.example.demo.domain.Wso2UserRequest;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.web.errors.BadRequestAlertException;
import com.example.demo.web.util.HeaderUtil;
import com.example.demo.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing customer.
 */
@RestController
@RequestMapping("/api")
public class Wso2TestResource {

    @Autowired
    IdentityServiceScimClient identityServiceScimClient;

    @PostMapping("/scim2/Users")
    public ResponseEntity<?> testCallWso2(@RequestBody Wso2UserRequest request){

        System.out.println(identityServiceScimClient);
        System.out.println(request);

        identityServiceScimClient.createUser(request);

        return ResponseEntity.of(Optional.of("testCallWso2 End"));
    }

}
