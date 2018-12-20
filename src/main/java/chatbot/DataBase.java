package chatbot;

import java.sql.*;
import org.sqlite.JDBC;

public class DataBase {
  public Connection connection;
  
  public DataBase() throws SQLException {
	DriverManager.registerDriver(new JDBC());
	connection = DriverManager.getConnection("jdbc:sqlite:C:database.s3db", "root", "root");
	Statement stmnt = connection.createStatement();
	stmnt.execute("CREATE TABLE if not exists 'movies' ('userId' text, 'movieId' text);");
  }
  
  public void addMovies(String movieId, String userId) throws SQLException {
	try (PreparedStatement statement = connection.prepareStatement(
        "INSERT INTO 'movies' ('userId', 'movieId')" + "VALUES (?, ?)")) {
      statement.setObject(1, userId);
      statement.setObject(2, movieId);
      statement.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}