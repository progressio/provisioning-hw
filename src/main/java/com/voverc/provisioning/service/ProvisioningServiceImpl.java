package com.voverc.provisioning.service;

import com.voverc.provisioning.entity.Device;
import com.voverc.provisioning.formatter.DeviceFormatter;
import com.voverc.provisioning.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ProvisioningServiceImpl implements ProvisioningService {

    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private Map<String, DeviceFormatter> deviceFormatters;

    @Value("${provisioning.domain}")
    private String provisioningDomain;
    @Value("${provisioning.port}")
    private String provisioningPort;
    @Value("${provisioning.codecs}")
    private String provisioningCodecs;

    public String getProvisioningFile(String macAddress) {
        // get from DB
        Optional<Device> dbResult = deviceRepository.findById(macAddress);
        if (dbResult.isEmpty()) {
            throw new DeviceNotFoundException();
        }

        // map to response
        Device device = dbResult.get();
        Map<String, String> result = new HashMap<>();
        result.put("username", device.getUsername());
        result.put("password", device.getPassword());
        result.put("domain", provisioningDomain);
        result.put("port", provisioningPort);
        result.put("codecs", provisioningCodecs);

        // override by fragment
        if (device.getOverrideFragment() != null) {
            deviceFormatters.get(device.getModel().name()).updateFromFragment(result, device.getOverrideFragment());
        }

        // format response
        return deviceFormatters.get(device.getModel().name()).serialize(result);
    }
}
