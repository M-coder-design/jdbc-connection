package org.example.repository;

import org.example.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private static final String URL = "jdbc:mysql://localhost:3306/native_jdbc_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Deeptavo@2708";


    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load MySQL driver.", e);
        }
    }

    /*
      JDBC requires the driver to be loaded into memory before it can connect to the database. By using Class.forName("com.mysql.cj.jdbc.Driver"), you're ensuring that the MySQL JDBC driver class is loaded.

      The com.mysql.cj.jdbc.Driver is the MySQL 8.x driver (for versions 8.0 and above).

      If the driver isn't found, an exception is thrown, and the program crashes with a clear error message. This is crucial for debugging issues related to missing drivers.
     */

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    /*
    This method provides a way to establish a connection to the database using the connection details (URL, username, password).

    DriverManager.getConnection(...) is used to get a connection from the DriverManager, which checks the connection pool (if configured) or establishes a new connection directly.

    It throws a SQLException if the connection attempt fails, which ensures that any issues are handled or reported at the point of connection.
    */

    public void addUser(User user) {
        String sql = "INSERT INTO users (name, email, age) VALUES (?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setInt(3, user.getAge());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUserById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setAge(resultSet.getInt("age"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> getAllUsers() {
        String sql = "SELECT * FROM users";
        List<User> users = new ArrayList<>();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setAge(resultSet.getInt("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void updateUser(User user) {
        String sql = "UPDATE users SET name = ?, email = ?, age = ? WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setInt(3, user.getAge());
            statement.setInt(4, user.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
