//package fr.isencaen.Gameplatform.controller;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import fr.isencaen.Gameplatform.models.dto.AccountDto;
//import fr.isencaen.Gameplatform.models.dto.CreateAccountDto;
//import fr.isencaen.Gameplatform.models.dto.MyAccountDto;
//import fr.isencaen.Gameplatform.service.AccountService;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.mock.web.MockHttpServletResponse;
//
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//
//@WebMvcTest(AccountController.class)
//public class ConnexionControllerTests {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private AccountService accountService;
//
//
//    @Test
//    void createAccountwithoutOneParameter() throws Exception {
//        // Given
//
//        // When
//        MockHttpServletResponse response = mockMvc.perform(
//                MockMvcRequestBuilders.post("/v1/account")
//                        .content("{}")
//                        .contentType("application/json")
//        ).andReturn().getResponse();
//        // Then
//        Assertions.assertEquals(400, response.getStatus());
//        //Map<String, String> errors = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<>() {
//        //});
//        //Assertions.assertEquals(Map.of("barCode", "BarCode is mandatory", "name", "Name is mandatory"), errors);
//    }
//
//    @Test
//    void createAccount() throws Exception {
//        // Given
//        Mockito.when(accountService.createAccount(new CreateAccountDto("pseudo", "mail","name", "pass", "M1")))
//                .thenReturn(new MyAccountDto(1, "pseudo", "mail","name", "M1", "token"));
//        // When
//        MockHttpServletResponse response = mockMvc.perform(
//                MockMvcRequestBuilders.post("/v1/account")
//                        .content(new ObjectMapper().writeValueAsString(new CreateAccountDto("pseudo", "mail","name",  "pass", "M1")))
//                        .contentType("application/json")
//        ).andReturn().getResponse();
//        // Then
//        Assertions.assertEquals(201, response.getStatus());
//        MyAccountDto result = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<>() {
//        });
//        Assertions.assertEquals(new MyAccountDto(1, "pseudo", "mail","name", "M1", "token"), result);
//    }
//
//}
