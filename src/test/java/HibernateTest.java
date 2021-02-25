import org.example.Main;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.junit.jupiter.api.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HibernateTest {
    SessionFactory factory;
    final static Logger log = Logger.getLogger(Main.class);

    @BeforeAll
    void init() {
        Properties props = new Properties();
        FileInputStream fis = null;
        Configuration config = null;
        try {
            fis = new FileInputStream(DatabaseTest.class.getClassLoader().getResource("db.properties")
                    .getPath());
            props.load(fis);
            config = new Configuration().configure();
            config.setProperty("hibernate.connection.username", props.getProperty("MYSQL_DB_USERNAME"));
            config.setProperty("hibernate.connection.password", props.getProperty("MYSQL_DB_PASSWORD"));
            config.setProperty("hibernate.connection.url", props.getProperty("MYSQL_DB_URL"));
            factory = config.buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testHibernateConn() {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer employeeID = null;

        try {
            tx = session.beginTransaction();
            TestObj obj = new TestObj(5);
            session.save(obj);
            tx.commit();

            String hql = "FROM TestObj o WHERE o.someNumber = 5";
            Query query = session.createQuery(hql);
            List results = query.list();
            TestObj obj2 = (TestObj) results.get(0);
            log.info("got testObj with someNumber = " + obj2.getSomeNumber());
            Assertions.assertEquals(obj, obj2);
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @AfterAll
    void cleanUp() {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String hql = "DELETE FROM TestObj WHERE someNumber = 5";
            Query query = session.createQuery(hql);
            int result = query.executeUpdate();
            tx.commit();
            log.info("hibernate cleaning result: " + result);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
            factory.close();
        }
    }
}
