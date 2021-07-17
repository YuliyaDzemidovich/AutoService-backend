package org.example.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VehicleDao {
    final static Logger log = LogManager.getLogger(Main.class);

    @Autowired
    private SessionFactory sessionFactory;
    private CountryDao countryDao = new CountryDao();
    private BrandDao brandDao = new BrandDao();
    private ModelDao modelDao = new ModelDao();

    @Transactional
    public List<Vehicle> getAllVehicles() {
        Query query = sessionFactory.getCurrentSession().createQuery("from Vehicle");
        List<Vehicle> res = query.getResultList();
        return res;
    }

    @Transactional(rollbackFor = HibernateException.class)
    public boolean addVehicle(Vehicle vehicle) {
        Session session = sessionFactory.getCurrentSession();
        // check if there's already such Model in the database (with the same Brand and Country records)
        Model modelAlreadyExisting = modelDao.getModelByBrand(vehicle.getModel());
        if (modelAlreadyExisting != null) {
            // attach Model (and Brand, and Country) to the vehicle
            vehicle.setModel(modelAlreadyExisting);
        } else { // or new Model will be created
            Model model = vehicle.getModel();
            // check if there's already such Brand in the database (with the same Country record)
            Brand brandAlreadyExisting = brandDao.getBrandByCountry(model.getBrand(), session);
            if (brandAlreadyExisting != null) {
                // attach Brand (and Country) to the new Model
                model.setBrand(brandAlreadyExisting);
            } else { // or new Brand will be created
                Brand brand = model.getBrand();
                // check if there's already such Country in the database
                Country countryAlreadyExisting = countryDao.getCountry(brand.getCountry().getName());
                if (countryAlreadyExisting != null) {
                    // attach Country to the Brand
                    brand.setCountry(countryAlreadyExisting);
                } else { // or new country will be created
                    Country country = brand.getCountry();
                    // save it and attach it to the brand
                    session.save(country);
                    brand.setCountry(country);
                }
                session.save(brand);
                model.setBrand(brand);
            }
            session.save(model);
            vehicle.setModel(model);
        }
        session.save(vehicle);
        return true;
    }

    @Transactional(rollbackFor = HibernateException.class)
    public boolean editVehicle(long id, Vehicle vehicleDataHolder) {
        Session session = sessionFactory.getCurrentSession();
        Vehicle vehicleFromDB = getVehicle(id);
        if (vehicleFromDB == null) {
            return false;
        }

        session.persist(vehicleFromDB);

        // check if there's already such Country in the database
        Country countryAlreadyExisting = countryDao.getCountry(vehicleDataHolder.getModel().getBrand().getCountry().getName());
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
                Model modelAlreadyExisting = modelDao.getModelByBrand(vehicleDataHolder.getModel());
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

        return true;
    }

    @Transactional(rollbackFor = HibernateException.class)
    public boolean deleteVehicle(long id) {
        Session session = sessionFactory.getCurrentSession();
        Vehicle vehicle = getVehicle(id);
        if (vehicle == null) {
            return false;
        }
        session.delete(vehicle);
        return true;
    }

    /**
     * Returns Vehicle object from the database if found by given Id<br>
     * @param id vehicle's id to be searched for
     * @return Vehicle object from the database if found by given Id
     */
    public Vehicle getVehicle(long id) {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM Vehicle WHERE id = :id");
        query.setParameter("id", id);
        return (Vehicle)query.getResultList().stream().findFirst().orElse(null);
    }

}
