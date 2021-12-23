package dataAccess;

import model.Passenger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class DaoPassenger {

    public void save(Passenger passenger) throws SQLException {
        Session session = MySessionFactory.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(passenger);
        transaction.commit();
        session.close();
    }

    public List<Passenger> findAllPassengers() throws SQLException {
        Session session = MySessionFactory.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<Passenger> query = session.createQuery("from Passenger ");
        List<Passenger> results = query.getResultList();
        if (results.isEmpty())
            throw new RuntimeException("there is no passenger!");
        transaction.commit();
        session.close();
        return results;
    }

    public void printAllPassenger() throws SQLException {
        List<Passenger> passengerList = findAllPassengers();
        System.out.println("passengers list: ");
        for (int i = 0; i < passengerList.size(); i++) {
            System.out.println(passengerList.get(i).toString());
        }
    }

    public boolean findPassengerByID(int userID) throws SQLException {
        Session session = MySessionFactory.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Passenger passenger = session.get(Passenger.class, userID);
        transaction.commit();
        session.close();

        if (passenger != null)
            return true;
        else return false;
    }

    public Passenger getPassengerByID(int userID)  {
        Session session = MySessionFactory.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Passenger passenger = session.get(Passenger.class, userID);
        transaction.commit();
        session.close();

        if (passenger != null)
            return passenger;
        else return null;
    }

    public void printPassengerInfo(int userID) throws SQLException {
        List<Passenger> passengerList = findAllPassengers();
        if (findPassengerByID(userID)) {
            System.out.println(passengerList.get(userID).toString());
        }
    }

    public void increaseBalance(int userID, double amount) throws SQLException {

        Session session = MySessionFactory.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Passenger passenger = session.get(Passenger.class, userID);
        double newBalance = passenger.getBalance() + amount;
        passenger.setBalance(newBalance);
        passenger.setStatus(true);
        session.update(passenger);
        transaction.commit();
        session.close();

    }

    public void paymentTravelByBalance(int userID, double amount) throws SQLException {

        Session session = MySessionFactory.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Passenger passenger = session.get(Passenger.class, userID);
        double newBalance = passenger.getBalance() - amount;
        passenger.setBalance(newBalance);
        passenger.setStatus(true);
        session.update(passenger);
        transaction.commit();
        session.close();
    }

    public boolean checkBalanceIsEnough(int userID, double amount) throws SQLException {
        Session session = MySessionFactory.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Passenger passenger = session.get(Passenger.class, userID);
        transaction.commit();
        session.close();
        if (passenger.getBalance() > amount)
            return true;
        else return false;
    }

    public boolean checkPassengerStatus(int userID) throws SQLException {
        Session session = MySessionFactory.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<Passenger> query = session.createQuery("from Passenger p where p.userID=:id and p.status=:status");
        query.setParameter("id", userID);
        query.setParameter("status", false);
        List<Passenger> results = query.getResultList();
        transaction.commit();
        session.close();
        if (results.isEmpty())
            return false;
        else return true;
    }

    public void updateStatus(int userID, boolean status) throws SQLException {
        Session session = MySessionFactory.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<Passenger> query = session.createQuery("update Passenger p set p.status=:status where p.userID=:id ");
        query.setParameter("id", userID);
        query.setParameter("status", status);
        List<Passenger> results = query.getResultList();
        transaction.commit();
        session.close();

    }


}
