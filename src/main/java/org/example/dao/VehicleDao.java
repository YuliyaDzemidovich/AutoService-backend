package org.example.dao;

import org.apache.log4j.Logger;
import org.example.Main;
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
            session.save(vehicle.getModel().getBrand().getCountry());
            session.save(vehicle.getModel().getBrand());
            session.save(vehicle.getModel());
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
