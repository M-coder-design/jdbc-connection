Connection Leaks:

Always close Connection, Statement, and ResultSet in finally blocks or use try-with-resources.
Error Handling:

Catch and log SQLException properly for troubleshooting.

explanation : ---------------------

code:

static {
try {
Class.forName("com.mysql.cj.jdbc.Driver");
} catch (ClassNotFoundException e) {
throw new RuntimeException("Failed to load MySQL driver.", e);
}
}

Why this is here:
JDBC requires the driver to be loaded into memory before it can connect to the database. By using Class.forName("com.mysql.cj.jdbc.Driver"), you're ensuring that the MySQL JDBC driver class is loaded.
The com.mysql.cj.jdbc.Driver is the MySQL 8.x driver (for versions 8.0 and above).
If the driver isn't found, an exception is thrown, and the program crashes with a clear error message. This is crucial for debugging issues related to missing drivers.

public Connection getConnection() throws SQLException {
return DriverManager.getConnection(URL, USERNAME, PASSWORD);
}

getConnection():

This method provides a way to establish a connection to the database using the connection details (URL, username, password).

DriverManager.getConnection(...) is used to get a connection from the DriverManager, which checks the connection pool (if configured) or establishes a new connection directly.

It throws a SQLException if the connection attempt fails, which ensures that any issues are handled or reported at the point of connection.

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

PreparedStatement:

PreparedStatement is used to execute SQL queries with parameters. This helps prevent SQL injection attacks, which can occur if you directly concatenate user input into the query string.

statement.setString(1, user.getName()): This binds the user.getName() value to the first parameter (?).

statement.setString(2, user.getEmail()): This binds the user.getEmail() value to the second parameter.

statement.setInt(3, user.getAge()): This binds the user.getAge() value to the third parameter.








Note: The try-with-resources block ensures that resources implementing the AutoCloseable interface (like Connection, Statement, ResultSet) are automatically closed after the block is executed, even if an exception occurs.
This is the preferred way to manage resources in modern Java applications, introduced in Java 7.git