package org.example.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.Brand;
import org.example.model.Model;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ModelDao {
    final static Logger log = LogManager.getLogger(ModelDao.class);

    @Autowired
    private SessionFactory sessionFactory;
    private BrandDao brandDao = new BrandDao();

    /**
     * Fetch a Model object from the database if found by given Model name
     * @param modelName Model name attribute
     * @return Model object from the database or null if not found
     */
    @Transactional
    public Model getModel(String modelName) {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM Model WHERE name = :name");
        query.setParameter("name", modelName);
        Model model = (Model)query.getResultList().stream().findFirst().orElse(null);
        return model;
    }

    @Transactional
    public Model getModelByBrand(Model modelData) {
        Session session = sessionFactory.getCurrentSession();
        Brand brand = brandDao.getBrandByCountry(modelData.getBrand(), session);
        if (brand == null) {
            return null;
        }
        Query query = session.createQuery("FROM Model WHERE name = :name AND brand = :brand");
        query.setParameter("name", modelData.getName());
        query.setParameter("brand", brand);
        return (Model)query.getResultList().stream().findFirst().orElse(null);
    }
}
