package com.example.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


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

        public String getJsonObject(Object... serializationObjects) throws JsonProcessingException {
                return mapper.writeValueAsString(serializationObjects);
        }
}
