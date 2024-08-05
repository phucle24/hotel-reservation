package service;

import model.Customer;
import model.IRoom;
import model.Room;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomerService {

    private static final CustomerService instance = new CustomerService();
    private final Map<String, Customer> customers = new HashMap<>();

    private CustomerService(){}

    // Static method to get the singleton instance
    public static CustomerService getInstance() {
        return instance;
    }

    public void addCustomer(String email, String firstName, String lastName){
        Customer customer =  new Customer(firstName, lastName, email);
        customers.put(email, customer);
    }

    public Customer getCustomer(String customerEmail){
        return customers.get(customerEmail);
    };

    public Collection<Customer> getAllCustomers() {
        return customers.values();
    }

}
