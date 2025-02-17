package com.application.project.myapiTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.reactive.function.server.MockServerRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.application.project.autoRoute.ArkRequest;
import com.application.project.model.User;
import com.application.project.myapi.function.FlyupdateUser;
import com.application.project.repository.PersonService;
import com.application.project.repository.PersonServiceImp;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
@WebFluxTest(FlyupdateUser.class)
@ContextConfiguration(classes = {
    FlyupdateUser.class,
    PersonService.class,
    PersonServiceImp.class
  })
public class FlyUpdateUserTest {

    @MockBean
    private PersonService userServices;

    @MockBean
    private PersonServiceImp personServiceImp;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private FlyupdateUser flyupdateUser;


    @Test
    @WithMockUser(username = "lezter",password = "12345")
    public void FlyupdateUserTest() throws Exception{

        User user = new User();
        user.setId(6);
        user.setAge("5");
        user.setGender("Male");
        user.setFirstName("Danilo");
        user.setLastName("Adams");

        MockServerRequest ms =  MockServerRequest.builder().pathVariable("id", "3").body(Mono.just(user));
        ArkRequest request = new ArkRequest(ms);
            
        Mono<ServerResponse> response = flyupdateUser.flypatch(request);

        StepVerifier.create(response).equals(null);
        
    }
    
}
