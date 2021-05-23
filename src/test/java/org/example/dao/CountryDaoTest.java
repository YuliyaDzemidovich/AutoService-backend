package org.example.dao;

import org.apache.log4j.Logger;
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
class CountryDaoTest {
    SessionFactory factory;
    final static Logger log = Logger.getLogger(CountryDaoTest.class);
    CountryDao countryDao = new CountryDao();

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
    void getCountry() {
        Session session = factory.openSession();
        Country country = countryDao.getCountry("Russia");

        assertAll("Should return Country object retrieved from the database with name 'Russia' and positive id",
                () -> Assert.notNull(country, "country object fetched from database should not be null"),
                () -> assertEquals("Russia", country.getName()),
                () -> assertTrue(country.getId() > 0)
        );

        log.info("Got Country object with id: " + country.getId());
    }

    @AfterAll
    void cleanUp() {
        if (factory != null) {
            factory.close();
        }
    }
}