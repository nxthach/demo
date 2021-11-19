package com.example.demo.web.rest;

import com.example.demo.client.IdentityServiceScimClient;
import com.example.demo.domain.Customer;
import com.example.demo.domain.Wso2UserRequest;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.web.errors.BadRequestAlertException;
import com.example.demo.web.util.HeaderUtil;
import com.example.demo.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * REST controller for managing customer.
 */
@RestController
@RequestMapping("/api")
public class CustomerResource {

    private final Logger log = LoggerFactory.getLogger(CustomerResource.class);
    private static final String ENTITY_NAME = "customer";
    private static final String APPLICATION_NAME = "demo";

    private final CustomerRepository customerRepository;

    public CustomerResource(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * {@code POST  /customers} : Create a new customer.
     *
     * @param customer the customer to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customer, or with status {@code 400 (Bad Request)} if the customer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customers")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) throws URISyntaxException {

        log.info("REST request to save Customer : {}", customer); //prod

        if (customer.getId() != null) {
            log.debug("REST request to save Customer : {}", customer.getId()); //dev
            throw new BadRequestAlertException("A new customer cannot already have an ID", ENTITY_NAME, "idexists");
        }

        Customer result = customerRepository.save(customer);

        return ResponseEntity.created(new URI("/api/customers/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(APPLICATION_NAME, false, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }


    /**
     * {@code PUT  /customers} : Updates an existing customer.
     *
     * @param customer the customer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customer,
     * or with status {@code 400 (Bad Request)} if the customer is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/customers")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) throws URISyntaxException {
        log.debug("REST request to update Customer : {}", customer);
        if (customer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Customer result = customerRepository.save(customer);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(APPLICATION_NAME, false, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * {@code GET  /customers/:id} : get the customer by "id"
     *
     * @param id the id of the customer to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customer, or with status {@code 404 (Not Found)}.
     */

    @GetMapping("/customers/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Long id){
        log.debug("REST request to get Customer : {}", id);
        Optional<Customer> customer =customerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(customer);
    }

    /**
     * {@code DELETE  /customers/:id} : delete the customer by "id".
     *
     * @param id the id of the customer to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customers/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        log.debug("REST request to delete Customer : {}", id);
        customerRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createAlert(APPLICATION_NAME, "The new Customer is created", id.toString())).build();
    }



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
