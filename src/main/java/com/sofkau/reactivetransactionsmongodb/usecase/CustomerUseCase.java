package com.sofkau.reactivetransactionsmongodb.usecase;

import com.sofkau.reactivetransactionsmongodb.collection.Customer;
import com.sofkau.reactivetransactionsmongodb.repository.ICustomerRepository;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.ReactiveTransaction;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.reactive.TransactionCallback;
import org.springframework.transaction.reactive.TransactionalOperator;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class CustomerUseCase {

    private final ICustomerRepository iCustomerRepository;

    private final TransactionalOperator transactionalOperator;

//    @Transactional
    public Flux<Customer> saveAll(String... emails) {

        Flux<Customer> customerFlux = Flux.just( emails )
                .map(email -> new Customer(null, email))
                .flatMap(this.iCustomerRepository::save)
                .doOnNext(customer -> Assert.isTrue(customer.getEmail().contains("@"), "The email must contain @!"))
                .as(this.transactionalOperator::transactional);

        return customerFlux;
//        return this.transactionalOperator.execute(status -> customerFlux);
    }
}
