package com.luv2code.springdemo.rest;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.springdemo.entity.Customer;
import com.luv2code.springdemo.service.CustomerService;

@RestController
@RequestMapping("/api")
public class CustomerRestController {
	
	private List<Customer>customers;
	
	@Autowired
	private CustomerService customerService;
	
	@PostConstruct
	@GetMapping("/customers")
	public List<Customer>customers(){
		customers=new ArrayList<Customer>();
		customers=customerService.getCustomers();
		return customers;
	}
	@GetMapping("/customers/{customerId}")
	public Customer customer(@PathVariable int customerId) {
		if(customerId>=customers.size()||customerId<0) {
			throw new CustomerNotFoundException("Customer not found id -"+customerId);
		}
		return customers.get(customerId-1);
	}
	@PostMapping("/customers")
	public Customer addCustomer(@RequestBody() Customer customer) {
		customer.setId(0);
		customerService.saveCustomer(customer);
		return customer;
	}
	@PutMapping("/customers")
	public Customer updateCustomer(@RequestBody()Customer customer) {
		customerService.saveCustomer(customer);
		return customer;
	}
	
	@DeleteMapping("/customers/{customerId}")
	public String deleteCustomer(@PathVariable int customerId) {
		Customer theCustomer=customerService.getCustomer(customerId);
		if(theCustomer==null) {
			throw new CustomerNotFoundException("Customer not found id - "+customerId);
		}
		customerService.deleteCustomer(customerId);
		
		return "Customer has been deleted id-customer-"+customerId;
	}
	
}













