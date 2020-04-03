package com.voverc.provisioning.service;

import com.voverc.provisioning.entity.Device;
import com.voverc.provisioning.formatter.DeskDeviceFormatter;
import com.voverc.provisioning.formatter.DeviceFormatter;
import com.voverc.provisioning.repository.DeviceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
public class ProvisioningServiceImplTest {

    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {

        @Bean
        public ProvisioningService provisioningService() {
            return new ProvisioningServiceImpl();
        }

        @Bean(name = "DESK")
        public DeviceFormatter deviceFormatter() {
            return new DeskDeviceFormatter();
        }
    }

    @Autowired
    private ProvisioningService provisioningService;
    @MockBean
    private DeviceRepository deviceRepository;

    @Test
    public void testGetProvisioningFile() {
        Device fakeDevice = new Device();
        fakeDevice.setMacAddress("abc");
        fakeDevice.setModel(Device.DeviceModel.DESK);
        fakeDevice.setUsername("username");
        fakeDevice.setPassword("password");
        Mockito.when(deviceRepository.findById(fakeDevice.getMacAddress())).thenReturn(Optional.of(fakeDevice));

        String result = provisioningService.getProvisioningFile(fakeDevice.getMacAddress());
        assertTrue(result.contains(fakeDevice.getUsername()));
        assertTrue(result.contains(fakeDevice.getPassword()));
    }
}
