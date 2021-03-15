package org.example.dao;

import org.apache.log4j.Logger;
import org.example.Main;
import org.example.model.Brand;
import org.example.model.Country;
import org.example.model.Model;
import org.example.model.Vehicle;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
public class VehicleDao {
    final static Logger log = Logger.getLogger(Main.class);

    @Autowired
    private SessionFactory sessionFactory;
    private CountryDao countryDao = new CountryDao();
    private BrandDao brandDao = new BrandDao();
    private ModelDao modelDao = new ModelDao();

    public List<Vehicle> getAllVehicles() {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from Vehicle");
        List<Vehicle> res = query.getResultList();
        session.close();
        return res;
    }

    public boolean addVehicle(Vehicle vehicle) {
        boolean successful = false;
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();

            // check if there's already such Country in the database
            Country countryAlreadyExisting = countryDao.getCountry(vehicle.getModel().getBrand().getCountry().getName(), session);
            if (countryAlreadyExisting != null) {
                // attach country to the vehicle
                vehicle.getModel().getBrand().setCountry(countryAlreadyExisting);
            } else {
                // or create new Country row in the database
                session.save(vehicle.getModel().getBrand().getCountry());
            }

            // check if there's already such Brand in the database
            Brand brandAlreadyExisting = brandDao.getBrand(vehicle.getModel().getBrand().getName(), session);
            if (brandAlreadyExisting != null) {
                // attach brand to the vehicle
                vehicle.getModel().setBrand(brandAlreadyExisting);
            } else {
                // or create new Brand row in the database
                session.save(vehicle.getModel().getBrand());
            }

            // check if there's already such Model in the database
            Model modelAlreadyExisting = modelDao.getModel(vehicle.getModel().getName(), session);
            if (modelAlreadyExisting != null) {
                // attach model to the vehicle
                vehicle.setModel(modelAlreadyExisting);
            } else {
                // or create new Model row in the database
                session.save(vehicle.getModel());
            }
            session.save(vehicle);
            session.getTransaction().commit();
            successful = true;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            log.debug("HibernateException on addVehicle()");
        } finally {
            session.close();
        }
        return successful;
    }
}
