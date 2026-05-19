package com.javeriana.service;

import com.javeriana.model.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CustomerService {

    private List<Customer> customers;

    public CustomerService() {
        this.customers = new ArrayList<>();
    }

    public  void addCustomer(String username, String password, String name, String lastName, int age) {
        Customer customer = new Customer(username, password, name, lastName, age);
        customers.add(customer);
        System.out.println("Cliente creado: " + customer.getId());
    }

    public Customer FindCustomer(UUID id) {
        for (Customer c : customers) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        return null;
    }

    public void deleteCustomer(UUID id) {
        Customer customer = FindCustomer(id);

        if (customer != null) {
            customers.remove(customer);
            System.out.println("Cliente eliminado");
        } else {
            System.out.println("Cliente no encontrado");
        }
    }

    public void showCustomers() {
        if (customers.isEmpty()) {
            System.out.println("No hay clientes");
            return;
        }

        for (Customer c : customers) {
            System.out.println(c);
        }
    }
}