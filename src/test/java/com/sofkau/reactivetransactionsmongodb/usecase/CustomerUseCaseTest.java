package com.sofkau.reactivetransactionsmongodb.usecase;

import com.sofkau.reactivetransactionsmongodb.repository.ICustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CustomerUseCaseTest {

    @Autowired
    private CustomerUseCase customerUseCase;

    @Autowired
    private ICustomerRepository iCustomerRepository;

    @Test
    public void saveAll() throws Exception {

        StepVerifier
                .create(this.iCustomerRepository.deleteAll())
                .verifyComplete();

        StepVerifier
                .create(this.customerUseCase.saveAll("a@a.com", "b@b.com", "c@c.com", "d@d.com"))
                .expectNextCount(4)
                .verifyComplete();

        StepVerifier
                .create(this.iCustomerRepository.findAll())
                .expectNextCount(4)
                .verifyComplete();

        StepVerifier
                .create( this.customerUseCase.saveAll("e@e.com" , "2"))
                .expectNextCount(1)
                .expectError()
//                .expectErrorMessage("The email must contain @!")
                .verify();

        StepVerifier
                .create(this.iCustomerRepository.findAll())
                .expectNextCount(4)
                .verifyComplete();

    }
}