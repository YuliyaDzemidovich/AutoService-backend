package org.example.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.Country;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CountryDao {
    final static Logger log = LogManager.getLogger(CountryDao.class);

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Fetch a country object from the database if found by given country name
     * @param countryName country name attribute
     * @return Country object from the database or null if not found
     */
    @Transactional
    public Country getCountry(String countryName) {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM Country WHERE name = :name");
        query.setParameter("name", countryName);
        Country country = (Country)query.getResultList().stream().findFirst().orElse(null);
        return country;
    }

}
