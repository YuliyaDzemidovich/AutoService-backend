package org.example.dao;

import org.apache.log4j.Logger;
import org.example.model.Brand;
import org.example.model.Country;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
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
    @Transactional
    public Brand getBrand(String brandName) {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM Brand WHERE name = :name");
        query.setParameter("name", brandName);
        Brand brand = (Brand)query.getResultList().stream().findFirst().orElse(null);
        return brand;
    }

    public Brand getBrandByCountry(Brand brandData, Session session) {
        Country country = countryDao.getCountry(brandData.getCountry().getName());
        if (country == null) {
            return null;
        }
        Query query = session.createQuery("FROM Brand WHERE name = :name AND country = :country");
        query.setParameter("name", brandData.getName());
        query.setParameter("country", country);
        return (Brand)query.getResultList().stream().findFirst().orElse(null);
    }

}
