package com.example.controller;

import com.example.dto.MenuRatedDto;
import com.example.service.mapping.MappingService;
import com.example.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.example.controller.util.TestData.*;
import static com.example.controller.util.TestMenuData.*;
import static com.example.controller.util.TestRestaurantData.FIRST_RESTAURANT;
import static com.example.controller.util.TestRestaurantData.SECOND_RESTAURANT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class MenuControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonMapper jsonMapper;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MappingService mappingService;

    @Autowired
    private MenuController menuController;


    @BeforeEach
    public void init() {
        mongoTemplate.save(FIRST_RESTAURANT_FIRST_MENU, "menu");
        mongoTemplate.save(FIRST_RESTAURANT_SECOND_MENU, "menu");
    }


    @Test
    public void shouldControllerBeNotNull() {
        assertThat(menuController).isNotNull();
    }

    @Test
    public void shouldBeStatusOkAndReturnAllMenusForRestaurantWithIdProperly() throws Exception {
        log.info("Test correctness of GET method on the path /restaurants/restaurant/{restId}/menus to get all menus for a restaurant");
        List<MenuRatedDto> menuRatedDtoList = mappingService.fromMenuListToMenuRatedDtoList(
                List.of(FIRST_RESTAURANT_FIRST_MENU, FIRST_RESTAURANT_SECOND_MENU));

        mockMvc.perform(get("/restaurants/restaurant/"+ FIRST_RESTAURANT.getId() +"/menus")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonMapper.getJsonObject(menuRatedDtoList)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void shouldBeStatusOkAndReturnMenuByMenuIdProperly() throws Exception {
        log.info("Test correctness of GET method on the path /restaurants/restaurant/{restId}/menus/menu/{menuId}");
        MenuRatedDto menuRatedDto = mappingService.fromMenuToRatedDto(FIRST_RESTAURANT_FIRST_MENU);

        mockMvc.perform(get("/restaurants/restaurant/" + FIRST_RESTAURANT.getId()
                + "/menus/menu/" + FIRST_RESTAURANT_FIRST_MENU.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonMapper.getJsonObject(menuRatedDto)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void shouldThrowExceptionWhenWrongMenuId() throws Exception {
        log.info("Test throwing MenuNotFoundException when try to receive a menu with a wrong ID");

        mockMvc.perform(get("/restaurants/restaurant/" + FIRST_RESTAURANT.getId() +
                "/menus/menu/" + WRONG_ID_STRING))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonMapper.getJsonObject(MENU_NOT_FOUND)))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void shouldBeStatusOkAndReturnMenuByDefaultDateParamProperly() throws Exception {
        log.info("Test correctness of GET method on the path /restaurants/restaurant/{restId}/menus/menu " +
                "without a param to get today's menu");
        MenuRatedDto menuRatedDto = mappingService.fromMenuToRatedDto(FIRST_RESTAURANT_SECOND_MENU);

        mockMvc.perform(get("/restaurants/restaurant/" + FIRST_RESTAURANT.getId()
                + "/menus/menu")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonMapper.getJsonObject(menuRatedDto)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void shouldBeStatusOkAndReturnMenuByCreatingDateParamProperly() throws Exception {
        log.info("Test correctness of GET method on the path /restaurants/restaurant/{restId}/menus/menu " +
                "with a param to get a menu by date");
        MenuRatedDto menuRatedDto = mappingService.fromMenuToRatedDto(FIRST_RESTAURANT_FIRST_MENU);

        mockMvc.perform(get("/restaurants/restaurant/" + FIRST_RESTAURANT.getId() +
                "/menus/menu")
                .contentType(MediaType.APPLICATION_JSON)
                .param("date", FIRST_RESTAURANT_FIRST_MENU.getCreatingDate().toString()))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonMapper.getJsonObject(menuRatedDto)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void shouldBeStatusCreateAndCreateMenuProperly() throws Exception {
        log.info("Test correctness of POST method on the path /restaurants/restaurant/{restId}/menus/menu " +
                "to store a new menu");
        MenuRatedDto menuRatedDto = mappingService.fromDtoToRatedDTO(SECOND_RESTAURANT_CREATE_DTO, SECOND_RESTAURANT.getId());

        mockMvc.perform(post("/restaurants/restaurant/" + SECOND_RESTAURANT.getId() +
                "/menus/menu")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.getJsonObject(SECOND_RESTAURANT_CREATE_DTO)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonMapper.getJsonObject(menuRatedDto)))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    public void shouldBeStatusOkAndUpdateMenuProperly() throws Exception {
        log.info("Test correctness of PUT method on the path /restaurants/restaurant/{restId}/menus/menu/{menuId}" +
                "to update a menu by its ID and restaurant ID");

        MenuRatedDto menuRatedDto = mappingService.fromDtoToRatedDTO(FIRST_RESTAURANT_UPDATE_DTO,
                FIRST_RESTAURANT.getId());

        mockMvc.perform(put("/restaurants/restaurant/" + FIRST_RESTAURANT.getId() +
                "/menus/menu/" + FIRST_RESTAURANT_FIRST_MENU.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.getJsonObject(FIRST_RESTAURANT_UPDATE_DTO)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonMapper.getJsonObject(menuRatedDto)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void shouldBeStatusOkAndDeleteMenuProperly() throws Exception {
        log.info("Test correctness of DELETE method on the path /restaurants/restaurant/{restId}" +
                "/menus/menu/{menuId}");
        String answer = String.format(REMOVE_MESSAGE,
                FIRST_RESTAURANT_SECOND_MENU.getId(), FIRST_RESTAURANT.getId());

        mockMvc.perform(delete("/restaurants/restaurant/" + FIRST_RESTAURANT.getId() +
                "/menus/menu/" + FIRST_RESTAURANT_SECOND_MENU.getId()))
                .andDo(print())
                .andExpect(content().string(answer))
                .andExpect(status().isOk())
                .andReturn();
    }

}