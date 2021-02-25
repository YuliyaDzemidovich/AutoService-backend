package org.example;

import org.example.model.*;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.example.config.AppConfig;
import org.example.config.HibernateConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class Main {
    final static Logger log = Logger.getLogger(Main.class);

    static SessionFactory factory;

    public static void main(String[] args) throws IOException {
        Class<?>[] configurations = new Class<?>[] {AppConfig.class, HibernateConfig.class};
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(configurations);

        // BookDao bookDao = (BookDao)context.getBean("bookDao");

        Main m = new Main();
        factory = new Configuration().configure().buildSessionFactory();
        try {
            m.writedownData();
        } catch (ParseException e) {
            System.out.println("error parsing data string");
        }

    }

    public void writedownData() throws ParseException {
        Session session = factory.openSession();

        Country country1 = new Country("Russia");
        Brand brand1 = new Brand("Ford", country1);
        Model model1 = new Model("Focus 2", brand1);
        Vehicle vehicle1 = new Vehicle("XX9FORDVIN", 2011, "Frozen White", model1);

        Country country2 = new Country("Germany");
        Brand brand2 = new Brand("BMW", country2);
        Model model2 = new Model("525i", brand2);
        Vehicle vehicle2 = new Vehicle("WAUZZBMWVIN", 2006, "Black", model2);

        Model model3 = new Model("X5", brand2);
        Vehicle vehicle3 = new Vehicle("WAUZZBMWX5VIN", 2009, "Gray", model3);

        IndividualClient individualClient1 = new IndividualClient("Петров", "Иван", "Вадимович", "Минск", "555-42-81", new Date(1000));
        CompanyClient companyClient1 = new CompanyClient("ZubrCompany", "Vitebsk, Belarus", "555-33-22");

        ServiceAdvisor serviceAdvisor1 = new ServiceAdvisor("Прытько", "Игорь", "Степанович", Date.valueOf("1981-03-21"),
                Date.valueOf("1981-03-21"), Date.valueOf("1981-03-21"), 1500);
        Mechanic mechanic1 = new Mechanic("Умелец", "Александр", "Михайлович", Date.valueOf("1987-08-30"),
                Date.valueOf("1987-08-30"), Date.valueOf("1987-08-30"), "моторист", 5);

        VehiclePassport vehiclePassport1 = new VehiclePassport(vehicle1, "0965 AX-2", individualClient1, Date.valueOf("2015-09-11"), country1, true);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        // Date parsed = format.parse("20110210");
        Order order1 = new Order(vehicle2, companyClient1, serviceAdvisor1, new Date(format.parse("2020-05-11 09:00").getTime()), new Date(format.parse("2020-05-11 11:00").getTime()), new Date(format.parse("2020-05-11 11:15").getTime()), 100, OrderStatus.RUNNING);

        FaultType faultType1 = new FaultType("Неисправность рабочего тормоза");
        Diagnostics diagnostics1 = new Diagnostics(1001);
        Fault fault1 = new Fault(faultType1, diagnostics1, FaultStatus.CLAIMED_BY_CLIENT);
        order1.addFault(fault1);

        WorkType workType1 = new WorkType("Замена тормозных трубок");
        Work work1 = new Work(workType1, fault1, new Date(format.parse("2020-05-11 09:15").getTime()), new Date(format.parse("2020-05-11 11:05").getTime()), 200);
        order1.addWork(work1);

        work1.addWorker(mechanic1, 90);
        work1.addWorker(serviceAdvisor1, 10);

        fault1.setFaultStatus(FaultStatus.CLAIMED_BY_CLIENT_AND_FIXED);
        order1.setStatus(OrderStatus.FINISHED);

        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(country1);
            session.save(country2);
            session.save(brand1);
            session.save(brand2);
            session.save(model1);
            session.save(model2);
            session.save(model3);
            session.save(vehicle1);
            session.save(vehicle2);
            session.save(vehicle3);
            session.save(individualClient1);
            session.save(companyClient1);
            session.save(serviceAdvisor1);
            session.save(mechanic1);
            session.save(vehiclePassport1);
            session.save(order1);
            session.save(faultType1);
            session.save(diagnostics1);
            session.save(fault1);
            session.save(workType1);
            session.save(work1);
            tx.commit();
        }
        catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
        finally {
            session.close();
        }
    }

}
