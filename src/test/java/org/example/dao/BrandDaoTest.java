package org.example.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.Brand;
import org.example.model.Country;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;
import org.springframework.util.Assert;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BrandDaoTest {
    SessionFactory factory;
    final static Logger log = LogManager.getLogger(CountryDaoTest.class);
    BrandDao brandDao = new BrandDao();

    @BeforeAll
    void init() {
        Properties props = new Properties();
        FileInputStream fis = null;
        Configuration config = null;
        try {
            fis = new FileInputStream(CountryDaoTest.class.getClassLoader().getResource("db.properties")
                    .getPath());
            props.load(fis);
            config = new Configuration().configure();
            config.setProperty("hibernate.connection.username", props.getProperty("MYSQL_DB_USERNAME"));
            config.setProperty("hibernate.connection.password", props.getProperty("MYSQL_DB_PASSWORD"));
            config.setProperty("hibernate.connection.url", props.getProperty("MYSQL_DB_URL"));
            factory = config.buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    @Disabled
    void getBrand() {
        Session session = factory.getCurrentSession();
        Brand brand = brandDao.getBrand("BMW");

        assertAll("Should return Brand object retrieved from the database with name 'BMW' and positive id",
                () -> Assert.notNull(brand, "Brand object fetched from database should not be null"),
                () -> assertEquals("BMW", brand.getName()),
                () -> assertTrue(brand.getId() > 0)
        );

        log.info("Got Brand object with id: " + brand.getId());
    }

    @Test
    @Disabled
    void getBrandByNameAndCountry() {
        Brand brandDataHolder = new Brand();
        brandDataHolder.setName("Ford");
        Country countryDataHolder = new Country();
        countryDataHolder.setName("Russia");
        brandDataHolder.setCountry(countryDataHolder);

        Session session = factory.getCurrentSession();
        Brand brand = brandDao.getBrandByCountry(brandDataHolder, session);

        assertAll("Should return Brand object retrieved from the database with name 'Ford', country 'Russia' and positive id",
                () -> Assert.notNull(brand, "Brand object fetched from database should not be null"),
                () -> assertEquals("Ford", brand.getName()),
                () -> assertEquals("Russia", brand.getCountry().getName()),
                () -> assertTrue(brand.getId() > 0),
                () -> assertTrue(brand.getCountry().getId() > 0)
        );

        log.info("Got Brand object with id: " + brand.getId());
        log.info("Connected with Country object with id: " + brand.getCountry().getId());
    }

    @AfterAll
    void cleanUp() {
        if (factory != null) {
            factory.close();
        }
    }
}