package com.example.controller;


import com.example.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.controller.TestData.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Sql(scripts = { "classpath:db/init.sql", "classpath:db/populate.sql" })
class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonMapper jsonMapper;

    @Autowired
    private RestaurantController restaurantController;

    @Test
    @Order(1)
    public void shouldBeStatusOkAndReturnAllRestaurantsProperly() throws Exception {
        log.info("Test correctness of GET method by path /restaurants to get all restaurants");

        mockMvc.perform(get("/restaurants"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonMapper.getJsonObject(MAIN_PAGE_RESTAURANTS)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @Order(2)
    public void shouldBeStatusOkAndRestaurantByIdProperly() throws Exception {
        log.info("Test correctness of GET method by path /restaurants/restaurant to get a restaurant by ID");

        mockMvc.perform(get("/restaurants/restaurant/" + FIRST_RESTAURANT.getId()))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonMapper.getJsonObject(FIRST_RESTAURANT)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @Order(3)
    public void shouldBeStatusOkAndRestaurantByNameProperly() throws Exception {
        log.info("Test correctness of GET method by path /restaurants/restaurant to get a restaurant by @param NAME");

        mockMvc.perform(get("/restaurants/restaurant")
                .param(NAME_PARAM, SECOND_RESTAURANT.getName()))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonMapper.getJsonObject(SECOND_RESTAURANT)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @Order(4)
    public void shouldStatusCreateAndCreatedRestaurantProperly() throws Exception {
        log.info("Test correctness of POST method by path /restaurants/restaurant to store a restaurant");

        mockMvc.perform(post("/restaurants/restaurant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.getJsonObject(CREATE_RESTAURANT)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonMapper.getJsonObject(CREATE_RESTAURANT)))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    @Order(5)
    public void shouldStatusOkAndUpdateRestaurantProperly() throws Exception {
        log.info("Test correctness of PUT method by path /restaurants/restaurant/{restId} to update a restaurant");

        mockMvc.perform(put("/restaurants/restaurant/" + CREATE_RESTAURANT.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.getJsonObject(UPDATE_RESTAURANT)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonMapper.getJsonObject(UPDATED_RESTAURANT)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @Order(6)
    public void shouldStatusOkAndDeleteRestaurantProperly() throws Exception {
        log.info("Test correctness of DELETE method by path /restaurants/restaurant/{restId} to remove a restaurant by ID");

        mockMvc.perform(delete("/restaurants/restaurant/" + UPDATED_RESTAURANT.getId()))
                .andDo(print())
                .andExpect(content().string(DELETE_MESSAGE))
                .andExpect(status().isOk())
                .andReturn();
    }

}