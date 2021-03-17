package org.example.dao;

import org.apache.log4j.Logger;
import org.example.model.Brand;
import org.example.model.Country;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

public class BrandDao {
    final static Logger log = Logger.getLogger(BrandDao.class);

    @Autowired
    private SessionFactory sessionFactory;
    private CountryDao countryDao = new CountryDao();

    /**
     * Fetch a Brand object from the database if found by given Brand name
     * @param brandName Brand name attribute
     * @return Brand object from the database or null if not found
     */
    public Brand getBrand(String brandName) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM Brand WHERE name = :name");
        query.setParameter("name", brandName);
        Brand brand = (Brand)query.getResultList().stream().findFirst().orElse(null);
        session.close();
        return brand;
    }

    /**
     * Fetch a Brand object from the database if found by given Brand name<br>
     * Used as a part of query - uses given Session object<br>
     * It is assumed that Session object is already initialized
     * @param brandName Brand name attribute
     * @param session Hibernate session
     * @return Brand object from the database or null if not found
     */
    public Brand getBrand(String brandName, Session session) {
        Query query = session.createQuery("FROM Brand WHERE name = :name");
        query.setParameter("name", brandName);
        return (Brand)query.getResultList().stream().findFirst().orElse(null);
    }

    public Brand getBrandByCountry(Brand brandData, Session session) {
        Country country = countryDao.getCountry(brandData.getCountry().getName(), session);
        if (country == null) {
            return null;
        }
        Query query = session.createQuery("FROM Brand WHERE name = :name AND country = :country");
        query.setParameter("name", brandData.getName());
        query.setParameter("country", country);
        return (Brand)query.getResultList().stream().findFirst().orElse(null);
    }

}
