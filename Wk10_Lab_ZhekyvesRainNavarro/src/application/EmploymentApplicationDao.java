package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EmploymentApplicationDao {

	public int saveApplication(EmploymentApplication app) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = Database.getDBConnection();
			if (connection == null) {
				return 0;
			}

			connection.setAutoCommit(false);

			String query = "INSERT INTO employment_application ("
					+ "full_name, current_address, contact_number, email_address, "
					+ "highest_education, gender, date_available, desired_position, "
					+ "desired_salary, legally_authorized, relatives_working, "
					+ "relatives_explanation, applicant_signature"
					+ ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			int counter = 1;
			statement.setString(counter++, app.getFullName());
			statement.setString(counter++, app.getCurrentAddress());
			statement.setString(counter++, app.getContactNumber());
			statement.setString(counter++, app.getEmailAddress());
			statement.setString(counter++, app.getHighestEducation());
			statement.setString(counter++, app.getGender());
			statement.setString(counter++, app.getDateAvailable());
			statement.setString(counter++, app.getDesiredPosition());
			statement.setString(counter++, app.getDesiredSalary());
			statement.setString(counter++, app.getLegallyAuthorized());
			statement.setString(counter++, app.getRelativesWorking());
			statement.setString(counter++, app.getRelativesExplanation());
			statement.setString(counter++, app.getApplicantSignature());

			statement.executeUpdate();
			connection.commit();

			resultSet = statement.getGeneratedKeys();
			if (resultSet.next()) {
				return resultSet.getInt(1);
			}
		} catch (SQLException exception) {
			if (connection != null) {
				connection.rollback();
			}
			throw exception;
		} finally {
			if (resultSet != null) {
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}

		return 0;
	}
}