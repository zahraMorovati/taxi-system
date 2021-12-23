package dataAccess;

import model.Car;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.sql.*;
import java.util.List;

import static dataAccess.MySessionFactory.sessionFactory;

public class DaoCar{

    public void save(Car car) throws SQLException {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(car);
        transaction.commit();
        session.close();
    }

    public int lastCarID() throws SQLException {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<Car> query = session.createQuery("from Car");
        List<Car> results = query.getResultList();
        Car car=results.get(results.size()-1);
        transaction.commit();
        session.close();
        return car.getId();
    }
}