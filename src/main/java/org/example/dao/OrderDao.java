package org.example.dao;

import org.example.model.Order;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public List<Order> getAllOrders() {
        Query query = sessionFactory.getCurrentSession().createQuery("from Order");
        List<Order> res = query.getResultList();
        // TODO this code is hot fix of LazyInitializationException - find proper solution
        for (Order order : res) {
            order.setFaults(new ArrayList<>());
            order.setWorks(new ArrayList<>());
        }
        return res;
    }
}
