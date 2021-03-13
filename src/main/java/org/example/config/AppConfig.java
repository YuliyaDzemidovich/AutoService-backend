package org.example.config;

import org.example.TempBean;
import org.example.dao.OrderDao;
import org.example.dao.VehicleDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    // for testing only
    @Bean (name = "tempBean")
    public TempBean getTempBean() {
        return new TempBean();
    }

    @Bean (name = "orderDao")
    public OrderDao getOrderDao() {
        return new OrderDao();
    }

    @Bean (name = "vehicleDao")
    public VehicleDao getVehicleDao() {
        return new VehicleDao();
    }
}
