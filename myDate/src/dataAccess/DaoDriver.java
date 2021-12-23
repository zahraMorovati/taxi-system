package dataAccess;

import model.Coordinate;
import model.Driver;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class DaoDriver {


    public void save(Driver driver) {
        Session session = MySessionFactory.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(driver);
        transaction.commit();
        session.close();
    }

    public List<Driver> findAllDrivers()  {
        Session session = MySessionFactory.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<Driver> query = session.createQuery("from Driver");
        List<Driver> results = query.getResultList();
        if (results.isEmpty())
            throw new RuntimeException("there is no driver!");
        transaction.commit();
        session.close();
        return results;

    }

    public boolean findDriverByID(int userID)  {
        Session session = MySessionFactory.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<Driver> query = session.createQuery("from Driver d where d.id=:id");
        query.setParameter("id", userID);
        List<Driver> results = query.getResultList();
        transaction.commit();
        session.close();
        if (results.isEmpty())
            return false;
        else return true;
    }

    public Driver getDriverByID(int userID)  {
        Session session = MySessionFactory.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<Driver> query = session.createQuery("from Driver d where d.id=:id");
        query.setParameter("id", userID);
        List<Driver> results = query.getResultList();
        transaction.commit();
        session.close();
        if (results.isEmpty())
            return results.get(0);
        else return null;
    }

    public void printDriverInfo(int userID)  {
        List<Driver> driverList = findAllDrivers();
        if (findDriverByID(userID)) {
            System.out.println(driverList.get(userID).toString());
        }
    }

    public void printAllDrivers()  {
        List<Driver> driverList = findAllDrivers();
        for (int i = 0; i < driverList.size(); i++) {
            System.out.println(driverList.get(i).toString());
        }
    }

    public void updateDriver(int userID, String firstName, String lastName, int nationalCode, String phoneNumber)  {
        Session session = MySessionFactory.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<Driver> query = session.createQuery("update Driver d set d.firstName=:firstname" +
                ",d.lastName=:lastName ,d.nationalCode=:naionalCode, d.phoneNumber=:phonNumber where d.userID=:userId");
        query.setParameter("firstname", firstName);
        query.setParameter("lastName", lastName);
        query.setParameter("naionalCode", nationalCode);
        query.setParameter("phonNumber", phoneNumber);
        query.setParameter("userId", userID);
        List<Driver> results = query.getResultList();
        transaction.commit();
        session.close();

    }


    public boolean checkDriverStatus(int userID)  {
        Session session = MySessionFactory.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<Driver> query = session.createQuery("from Driver d where d.userID=:id and d.status=:status");
        query.setParameter("id", userID);
        query.setParameter("status", false);
        List<Driver> results = query.getResultList();
        transaction.commit();
        session.close();
        if (results.isEmpty())
            return false;
        else return true;
    }

    public void updateStatus(int userID, boolean status)  {
        Session session = MySessionFactory.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<Driver> query = session.createQuery("update Driver d set d.status=:status where d.userID=:id ");
        query.setParameter("id", userID);
        query.setParameter("status", status);
        List<Driver> results = query.getResultList();
        transaction.commit();
        session.close();

    }

    public void updateCurrentCoordinate(int userID, String coordinateStr) {
        Coordinate coordinate = Coordinate.stringToCoordinate(coordinateStr);
        Session session = MySessionFactory.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<Driver> query = session.createQuery("update Driver d set d.currentCoordinate=:coordinate where d.userID=:id ");
        query.setParameter("id", userID);
        query.setParameter("coordinate", coordinate);
        List<Driver> results = query.getResultList();
        transaction.commit();
        session.close();
    }

    public boolean getStatusDriver(int userID)  {

        Session session = MySessionFactory.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Driver driver = session.get(Driver.class, userID);
        transaction.commit();
        session.close();
        return driver.getBooleanStatus();
    }

}
