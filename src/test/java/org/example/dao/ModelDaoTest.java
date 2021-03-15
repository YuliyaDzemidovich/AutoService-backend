package org.example.dao;

import org.apache.log4j.Logger;
import org.example.model.Brand;
import org.example.model.Model;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.util.Assert;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ModelDaoTest {
    SessionFactory factory;
    final static Logger log = Logger.getLogger(CountryDaoTest.class);
    ModelDao modelDao = new ModelDao();

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
    void getModel() {
        Session session = factory.openSession();
        Model model = modelDao.getModel("X5", session);

        assertAll("Should return Model object retrieved from the database with name 'X5' and positive id",
                () -> Assert.notNull(model, "Model object fetched from database should not be null"),
                () -> assertEquals("X5", model.getName()),
                () -> assertTrue(model.getId() > 0)
        );

        log.info("Got Model object with id: " + model.getId());
    }

    @AfterAll
    void cleanUp() {
        if (factory != null) {
            factory.close();
        }
    }
}