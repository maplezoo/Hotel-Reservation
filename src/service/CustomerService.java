package service;

import mode1.Customer;

import java.util.*;

public class CustomerService {
    private static CustomerService customerService;
    private static Map<String, Customer> customers = new HashMap<>();

    public static CustomerService getCustomerService() {
        if (customerService == null) {
            System.out.println("CUSTOMER SERVICE: NULL");
            customerService = new CustomerService();
        }
        return customerService;
    }

    public static void addCustomer(String email, String firstName, String lastName){
        if(!customers.containsKey(email)) {
            Customer newCustomer = new Customer(firstName, lastName, email);
            customers.put(newCustomer.getEmail(), newCustomer);
        } else {
            System.out.println("Email is already taken!");
        }
    }

    public static Customer getCustomer(String customerEmail) {
        return customers.get(customerEmail);
    }

    public Collection<Customer> getAllCustomers() {
        return (Collection<Customer>) customers;
        }
    }

