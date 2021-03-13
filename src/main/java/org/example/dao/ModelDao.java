package org.example.dao;

import org.apache.log4j.Logger;
import org.example.model.Brand;
import org.example.model.Country;
import org.example.model.Model;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

public class ModelDao {
    final static Logger log = Logger.getLogger(ModelDao.class);

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Fetch a Model object from the database if found by given Model name
     * @param modelName Model name attribute
     * @return Model object from the database or null if not found
     */
    public Model getModel(String modelName) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM Model WHERE name = :name");
        query.setParameter("name", modelName);
        Model model = (Model)query.getResultList().stream().findFirst().orElse(null);
        session.close();
        return model;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    // for testing purposes
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

}
