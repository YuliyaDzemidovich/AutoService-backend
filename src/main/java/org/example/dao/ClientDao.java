package org.example.dao;

import org.example.model.Client;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClientDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public List<Client> getAllClients() {
        Query query = sessionFactory.getCurrentSession().createQuery("from Client");
        List<Client> res = query.getResultList();
        return res;
    }
}
