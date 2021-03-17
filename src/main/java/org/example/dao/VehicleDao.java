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

    public boolean editVehicle(long id, Vehicle vehicleDataHolder) {
        boolean successful = false;
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();

            Vehicle vehicleFromDB = getVehicle(id, session);
            if (vehicleFromDB == null) {
                return false;
            }

            session.persist(vehicleFromDB);

            // check if there's already such Country in the database
            Country countryAlreadyExisting = countryDao.getCountry(vehicleDataHolder.getModel().getBrand().getCountry().getName(), session);
            log.info("countryAlreadyExisting = " + countryAlreadyExisting);
            if (countryAlreadyExisting != null) {
                // attach existing country to the vehicle
                vehicleDataHolder.getModel().getBrand().setCountry(countryAlreadyExisting);

                // check if there's already such Brand with given country in the database
                Brand brandAlreadyExisting = brandDao.getBrandByCountry(vehicleDataHolder.getModel().getBrand(), session);
                log.info("brandAlreadyExisting = " + brandAlreadyExisting);
                if (brandAlreadyExisting != null) {
                    // attach existing brand to the vehicle
                    vehicleDataHolder.getModel().setBrand(brandAlreadyExisting);
                    //vehicleFromDB.getModel().setBrand(brandAlreadyExisting);

                    // check if there's already such Model in the database
                    Model modelAlreadyExisting = modelDao.getModelByBrand(vehicleDataHolder.getModel(), session);
                    log.info("modelAlreadyExisting = " + modelAlreadyExisting);
                    if (modelAlreadyExisting != null) {
                        // attach existing model to the vehicle
                        vehicleDataHolder.setModel(modelAlreadyExisting);
                        brandAlreadyExisting.setCountry(countryAlreadyExisting);
                        modelAlreadyExisting.setBrand(brandAlreadyExisting);
                        vehicleFromDB.setModel(modelAlreadyExisting);
                    } else {
                        // or create new Model row in the database
                        // and attach it to the vehicle
                        Model model = vehicleDataHolder.getModel();
                        brandAlreadyExisting.setCountry(countryAlreadyExisting);
                        model.setBrand(brandAlreadyExisting);
                        session.save(model);
                        vehicleFromDB.setModel(model);
                    }
                } else {
                    // or create new Brand and Model rows in the database
                    // and attach it to the vehicle
                    Brand brand = vehicleDataHolder.getModel().getBrand();
                    brand.setCountry(countryAlreadyExisting);
                    session.save(brand);
                    Model model = vehicleDataHolder.getModel();
                    model.setBrand(brand);
                    session.save(model);
                    vehicleFromDB.setModel(model);
                }
            } else {
                // or create new Country, Brand, Model rows in the database
                Country country = vehicleDataHolder.getModel().getBrand().getCountry();
                session.save(country);
                Brand brand = vehicleDataHolder.getModel().getBrand();
                brand.setCountry(country);
                session.save(brand);
                Model model = vehicleDataHolder.getModel();
                model.setBrand(brand);
                session.save(model);
                // and attach it to the vehicle
                vehicleFromDB.setModel(model);
            }

            // change all other attributes in Vehicle entity
            vehicleFromDB.setColor(vehicleDataHolder.getColor());
            vehicleFromDB.setDateOfProduction(vehicleDataHolder.getDateOfProduction());
            vehicleFromDB.setVin(vehicleDataHolder.getVin());

            session.getTransaction().commit();
            successful = true;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            log.debug("HibernateException on editVehicle()");
        } finally {
            session.close();
        }
        return successful;
    }

    /**
     * Returns Vehicle object from the database if found by given Id<br>
     * Used as a part of query - uses given Session object<br>
     * It is assumed that Session object is already initialized
     * @param id vehicle's id to be searched for
     * @param session Hibernate session
     * @return Vehicle object from the database if found by given Id
     */
    public Vehicle getVehicle(long id, Session session) {
        Query query = session.createQuery("FROM Vehicle WHERE id = :id");
        query.setParameter("id", id);
        return (Vehicle)query.getResultList().stream().findFirst().orElse(null);
    }
}
