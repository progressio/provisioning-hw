package com.voverc.provisioning.formatter;

import java.util.Map;

public interface DeviceFormatter {

    void updateFromFragment(Map<String, String> map, String fragment);

    String serialize(Map<String, String> map);
}
