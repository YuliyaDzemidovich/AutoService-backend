package org.example.dao;

import org.example.model.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BookDao {
    @Autowired
    private SessionFactory sessionFactory;

    public List<Book> getBooks() {
        Session session = sessionFactory.openSession();
        String hql = "from Book";
        Query query = session.createQuery(hql);
        List<Book> books = query.list();
        session.close();
        return books;
    }

    public boolean create(Book book) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(book);
            tx.commit();
            return true;
        } catch (Exception e){
            if (tx != null) {
                tx.rollback();
            }
            return false;
        } finally {
            session.close();
        }
    }

    public boolean update(Book book) { // TODO fix
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.saveOrUpdate(book);
            tx.commit();
            return true;
        } catch (Exception e){
            if (tx != null) {
                tx.rollback();
            }
            return false;
        } finally {
            session.close();
        }
    }

    public boolean deleteByISBN(final String ISBN) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            Book book = findByISBN(ISBN);
            if (book != null) {
                tx = session.beginTransaction();
                session.delete(book);
                tx.commit();
                return true;
            } else {
                return false;
            }
        } catch (Exception e){
            if (tx != null) {
                tx.rollback();
            }
            return false;
        } finally {
            session.close();
        }
    }

    public Book findByISBN(String isbn) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from Book where ISBN =:isbn");
        query.setParameter("isbn", isbn);
        Book book = (Book) query.getSingleResult();
        session.close();
        return book;
    }

    /**
     *
     * @param id book's id of desired cover
     * @return relative path of book cover image
     */
    public String getCoverUrlById(int id) {
        Book book = getBookById(id);
        return book.getCoverUrl();
    }

    public Book getBookById(int id) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from Book where id =:id");
        query.setParameter("id", id);
        Book book = (Book) query.getSingleResult();
        session.close();
        return book;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
