package org.example.dao;

import org.example.model.Vehicle;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
public class VehicleDao {
    @Autowired
    private SessionFactory sessionFactory;

    public List<Vehicle> getAllVehicles() {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from Vehicle");
        List<Vehicle> res = query.getResultList();
        session.close();
        return res;
    }
}
