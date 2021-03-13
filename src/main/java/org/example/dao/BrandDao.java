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

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    // for testing purposes
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

}
