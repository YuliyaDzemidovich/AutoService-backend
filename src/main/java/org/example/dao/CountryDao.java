package org.example.dao;

import org.apache.log4j.Logger;
import org.example.Main;
import org.example.model.Country;
import org.example.model.Vehicle;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CountryDao {
    final static Logger log = Logger.getLogger(CountryDao.class);

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Fetch a country object from the database if found by given country name
     * @param countryName country name attribute
     * @return Country object from the database or null if not found
     */
    public Country getCountry(String countryName) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM Country WHERE name = :name");
        query.setParameter("name", countryName);
        Country country = (Country)query.getResultList().stream().findFirst().orElse(null);
        session.close();
        return country;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    // for testing purposes
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
