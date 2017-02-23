package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.PatientUser;
import model.Prescription;

public enum PatientUserDAO {
  instance;

  public static Connection getConnection() {

    Connection connection = null;
    try {
      Class.forName("com.mysql.jdbc.Driver");
      connection = DriverManager.getConnection(
    		  "jdbc:mysql://34.250.167.112:3306/UserDB", "root", "root");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return connection;
  }

  public void save(PatientUser user) {
    Connection connection = getConnection();

    try {
      PreparedStatement psmt = connection
          .prepareStatement("INSERT INTO patient (email, password) VALUES (?, ?)");
      psmt.setString(1, user.getEmail());
      psmt.setString(2, user.getPassword());

      psmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static PatientUser checkLogin(String email, String password) {
    Connection connection = getConnection();
    PatientUser user = null ;

    try {
      PreparedStatement psmt = connection
          .prepareStatement("SELECT * FROM patient WHERE EMAIL = ? AND PASSWORD = ?");
      psmt.setString(1, email);
      psmt.setString(2, password);
      ResultSet rs = psmt.executeQuery();
      if (rs.next()) {
        user = new PatientUser(rs.getInt("id"), rs.getString("email"), rs.getString("password")) ;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return user ;
  }
  
  public static Prescription getPrescription(int pid) {
	  Connection connection = getConnection();
	  Prescription prescription = null ;

	  try {
		  PreparedStatement psmt = connection
				  .prepareStatement("SELECT * FROM prescription WHERE p_id = ?"); // Use UUID // Use Prescription
		  psmt.setInt(1, pid);
		  ResultSet rs = psmt.executeQuery();
		  if (rs.next()) {
			  prescription = new Prescription(rs.getString("method"), rs.getString("medicine")) ;
		  }
	  } catch (SQLException e) {
		  e.printStackTrace();
	  }
	  return prescription;
  }

}