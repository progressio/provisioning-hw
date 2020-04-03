package com.voverc.provisioning.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "device not found")
public class DeviceNotFoundException extends RuntimeException {
}
