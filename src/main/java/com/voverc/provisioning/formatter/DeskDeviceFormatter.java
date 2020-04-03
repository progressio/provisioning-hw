package com.voverc.provisioning.formatter;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Works with .properties
 */
@Service("DESK")
public class DeskDeviceFormatter implements DeviceFormatter {
    @Override
    public void updateFromFragment(Map<String, String> map, String fragment) {
        String[] parsedFragment = fragment.split("\\n");
        for (String pair : parsedFragment) {
            String[] parsedPair = pair.split("=");
            map.put(parsedPair[0], parsedPair[1]);
        }
    }

    @Override
    public String serialize(Map<String, String> map) {
        return map.keySet().stream()
                .map(key -> key + "=" + map.get(key))
                .collect(Collectors.joining("\n"));
    }
}
