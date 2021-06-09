package com.example.util;

import com.example.restaurant.Restaurant;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;


@Component
public class JsonMapper {

        private ObjectMapper mapper;

        @Autowired
        public JsonMapper(ObjectMapper mapper) {
            this.mapper = mapper;
        }

        public String getJsonObject(Object serializationObject) throws JsonProcessingException {
                return mapper.writeValueAsString(serializationObject);
        }
}
