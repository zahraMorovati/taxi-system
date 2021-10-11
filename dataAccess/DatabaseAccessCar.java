package dataAccess;

import model.Car;

import java.sql.*;

public class DatabaseAccessCar extends DatabaseAccess {

    public DatabaseAccessCar() throws ClassNotFoundException, SQLException {
        super();
        if (getConnection() != null) {
            DatabaseMetaData metaData = getConnection().getMetaData();
            ResultSet tables = metaData.getTables(null, null, "car", null);
            if (!tables.next()) {
                createCarTable();
            }
        }
    }

    private void createCarTable() throws SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("CREATE TABLE car (" +
                "    id INT NOT NULL AUTO_INCREMENT," +
                "    plaque VARCHAR(25)," +
                "    owner_first_name VARCHAR(25)," +
                "    owner_last_name VARCHAR(25)," +
                "    color VARCHAR(25)," +
                "    type VARCHAR(25)," +
                "    PRIMARY KEY (id))");
    }

    public int save(Car car) throws SQLException {
        if (getConnection() != null) {
            Statement statement = getConnection().createStatement();
            String sqlQuery = String.format("INSERT INTO car" +
                            " (plaque, owner_first_name,owner_last_name,color,type) " +
                            "VALUES ('%s','%s','%s','%s','%s')",
                    car.getPlaque(), car.getOwnerFirstName(), car.getOwnerLAstName(), car.getVehicleColor(), car.getCarType());
            int i = statement.executeUpdate(sqlQuery);
            return i;
        } else {
            return 0;
        }
    }


    public void searchCarByType(String type) throws SQLException {

        PreparedStatement preparedStatement = getConnection().prepareStatement("select (plaque, owner_first_name,owner_last_name) from car where type=?");
        preparedStatement.setString(1, type);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            System.out.print(resultSet.getString("plaque") + ",  ");
            System.out.print(resultSet.getString("owner_first_name") + " ");
            System.out.println(resultSet.getString("owner_last_name"));
        }

    }

    public int lastCarID() throws SQLException {
        if (getConnection() != null) {
            int id = 0;
            Statement statement = getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("select id from car");
            while (resultSet.next()) {
                id = resultSet.getInt("id");
            }
            return id;
        } else {
            return -1;
        }
    }
}