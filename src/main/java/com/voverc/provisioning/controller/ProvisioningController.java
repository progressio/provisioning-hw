package com.voverc.provisioning.controller;

import com.voverc.provisioning.service.ProvisioningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ProvisioningController {

    @Autowired
    private ProvisioningService service;

    @GetMapping("/provisioning/{macAddress}")
    @ResponseBody
    public String greeting(@PathVariable String macAddress) {
        return service.getProvisioningFile(macAddress);
    }
}