package com.sofkau.reactivetransactionsmongodb.repository;

import com.sofkau.reactivetransactionsmongodb.collection.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ICustomerRepository extends ReactiveCrudRepository<Customer, String> {
}
