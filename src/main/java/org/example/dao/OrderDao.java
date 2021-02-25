package org.example.dao;

import org.example.model.Order;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class OrderDao {
    @Autowired
    private SessionFactory sessionFactory;

    public List<Order> getAllOrders() {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from Order");
        List<Order> res = query.getResultList();
        session.close();
        return res;
    }
}
