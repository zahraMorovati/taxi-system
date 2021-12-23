package dataAccess;

import model.Travel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

import static model.Travel.TravelStatus.ONGOING;
import static model.Travel.TravelStatus.WAITING_FOR_PAYMENT;
import static validData.ConsoleColors.GREEN;
import static validData.ConsoleColors.RESET;

public class DaoTravel {


    public void save(Travel travel) throws SQLException {
        Session session = MySessionFactory.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(travel);
        transaction.commit();
        session.close();
    }

    public static List<Travel> findAllTravels() throws SQLException {
        Session session = MySessionFactory.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<Travel> query = session.createQuery("from Travel ");
        List<Travel> results = query.getResultList();
        if (results.isEmpty())
            throw new RuntimeException("there is no travel!");
        transaction.commit();
        session.close();
        return results;
    }

    public void printAllTravel() throws SQLException {
        List<Travel> travelList = findAllTravels();
        System.out.println("travel list: ");
        for (int i = 0; i < travelList.size(); i++) {
            System.out.println(travelList.get(i).toString());
        }
    }

    public void printOngoingTravels() throws SQLException {
        Session session = MySessionFactory.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<Travel> query = session.createQuery("from Travel t where t.travelStatus=:status")
                .setParameter("status", ONGOING);
        List<Travel> results = query.getResultList();
        if (results.isEmpty())
            throw new RuntimeException("there is no ongoing travel!");
        transaction.commit();
        session.close();
    }

    public boolean printWaitingForPayment(int driverUserID) throws SQLException {
        Session session = MySessionFactory.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<Travel> query = session.createQuery("from Travel t where t.driver.id=:id and t.travelStatus=:status")
                .setParameter("id", driverUserID).setParameter("status", WAITING_FOR_PAYMENT);
        List<Travel> results = query.getResultList();
        transaction.commit();
        session.close();
        if (results.isEmpty())
            return false;
        else return true;
    }


    public void confirmPaymentByCash(int userIdTravel) throws SQLException {

        Session session = MySessionFactory.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<Travel> query = session.createQuery("update Travel t set t.travelStatus=:status where t.id=:id")
                .setParameter("status", ONGOING).setParameter("id", userIdTravel);
        List<Travel> results = query.getResultList();
        transaction.commit();
        session.close();

        if (!results.isEmpty())
            System.out.println(GREEN + "operation has done successfully." + RESET);
    }

    public void updateStatus(int id, Travel.TravelStatus status) throws SQLException {
        Session session = MySessionFactory.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<Travel> query = session.createQuery("update Travel t set t.travelStatus=:status where t.id=:id")
                .setParameter("status", status).setParameter("id", id);
        List<Travel> results = query.getResultList();
        transaction.commit();
        session.close();

    }


}
