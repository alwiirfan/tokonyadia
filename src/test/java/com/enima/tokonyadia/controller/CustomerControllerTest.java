package com.enima.tokonyadia.controller;

import com.enima.tokonyadia.entity.Customer;
import com.enima.tokonyadia.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.MockMvcBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {

    @MockBean
    private CustomerService customerService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void isShouldCreateCustomerAndReturnCustomerResponseOfCustomerAndStatusCode() throws Exception {
        // data dummy customer
        Customer dummyCustomer = new Customer();
        dummyCustomer.setId("1");
        dummyCustomer.setName("Alwi");

        // mock behavior
        when(customerService.create(any(Customer.class))).thenReturn(dummyCustomer);

        // verify untuk mengirimkan permintaan HTTP ke endpoint controller
        mockMvc.perform(
            post("/api/v1/customers")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dummyCustomer))
        ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusCode").value(201))
                .andExpect(jsonPath("$.message").value("Successfully create new customer"))
                .andExpect(jsonPath("$.data.id").value(dummyCustomer.getId()))
                .andExpect(jsonPath("$.data.name").value(dummyCustomer.getName())
                );

        // verify
        verify(customerService, times(1)).create(any(Customer.class));
    }

    @Test
    void itShouldGetAllCustomerAndStatusOk() throws Exception {
        // data dummy
        Customer dummyCustomer = new Customer();
        dummyCustomer.setId("1");
        dummyCustomer.setName("Alwi");

        Customer dummyCustomer2 = new Customer();
        dummyCustomer.setId("2");
        dummyCustomer.setName("Irfani");

        // toList
        List<Customer> dummyCustomers = Arrays.asList(dummyCustomer,dummyCustomer2);

        // mock
        when(customerService.getAll()).thenReturn(dummyCustomers);

        mockMvc.perform(
                get("/api/v1/customers")
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("Successfully get all customer"))
                .andExpect(jsonPath("$.data[0].id").value(dummyCustomer.getId()))
                .andExpect(jsonPath("$.data[0].name").value(dummyCustomer.getName()))
                .andExpect(jsonPath("$.data[1].id").value(dummyCustomer2.getId()))
                .andExpect(jsonPath("$.data[1].name").value(dummyCustomer2.getName()));

        // verify
        verify(customerService, times(1)).getAll();
    }

    @Test
    void itShouldCustomerResponseWhenGetByIdAndStatusOk() throws Exception {
        // dummy
        Customer dummyCustomer = new Customer();
        dummyCustomer.setId("1");
        dummyCustomer.setName("Alwi");

        // mock
        when(customerService.getById(dummyCustomer.getId())).thenReturn(dummyCustomer);

        mockMvc.perform(
                get("/api/v1/customers/" + dummyCustomer.getId())
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("Successfully get customer by id"))
                .andExpect(jsonPath("$.data.id").value(dummyCustomer.getId()))
                .andExpect(jsonPath("$.data.name").value(dummyCustomer.getName())
                );

        verify(customerService, times(1)).getById(dummyCustomer.getId());
    }

//    @Test
//    void itShouldUpdateCustomerWhenReturnCustomerResponseOfCustomerAndStatusOk() throws Exception {
//        // dummy
//        String customerId = "1";
//
//        Customer dummyCustomer = new Customer(customerId, "Alwi", "jalan buntu", "0989879833", "test@example.com");
//        Customer updateCustomer = new Customer(customerId, "Irfani", "jalan-jalan", "9798687568", "testing@example.com");
//
//        // mock
//        when(customerService.getById(customerId)).thenReturn(dummyCustomer);
//        when(customerService.update(any(Customer.class))).thenReturn(updateCustomer);
//
//        mockMvc.perform(
//                put("/api/v1/customers")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(updateCustomer))
//        ).andExpect(status().isOk())
//                .andExpect(jsonPath("$.statusCode").value(200))
//                .andExpect(jsonPath("$.message").value("Successfully update customer"))
//                .andExpect(jsonPath("$.data.id").value(customerId))
//                .andExpect(jsonPath("$.data.name").value(updateCustomer.getName()))
//                .andExpect(jsonPath("$.data.address").value(updateCustomer.getAddress()))
//                .andExpect(jsonPath("$.data.mobilePhone").value(updateCustomer.getMobilePhone()))
//                .andExpect(jsonPath("$.data.email").value(updateCustomer.getEmail())
//                );
//
//        verify(customerService, times(1)).update(any(Customer.class));
//    }

    @Test
    void itShouldDeleteCustomerAndReturnStringMessageWhenDeleteMustById() throws Exception {
        // dummy
        String customerId = "1";

        // mock
        //doNothing().when(customerService).deleteById(customerId);
        willDoNothing().given(customerService).deleteById(customerId);

        mockMvc.perform(
                delete("/api/v1/customers/" + customerId)
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("Successfully delete customer")
                );

        verify(customerService, times(1)).deleteById(customerId);
    }
}