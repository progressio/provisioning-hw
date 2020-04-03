package com.voverc.provisioning.formatter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Works with JSON
 */
@Service("CONFERENCE")
public class ConferenceDeviceFormatter implements DeviceFormatter {

    Logger logger = LoggerFactory.getLogger(ConferenceDeviceFormatter.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void updateFromFragment(Map<String, String> map, String fragment) {
        try {
            TypeReference<HashMap<String, String>> typeRef = new TypeReference<>() {};
            Map<String, String> parsedFragment = objectMapper.readValue(fragment, typeRef);
            parsedFragment.forEach(map::put);
        } catch (JsonProcessingException e) {
            logger.error("Can't parse json fragment" + fragment, e);
        }
    }

    @Override
    public String serialize(Map<String, String> map) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
        } catch (JsonProcessingException e) {
            logger.error("Can't jsonify map" + map, e);
            return null;
        }
    }
}
