package application;

import java.sql.SQLException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		EmploymentApplicationDao dao = new EmploymentApplicationDao();

		primaryStage.setTitle("Employment Application");

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 25, 25, 25));

		int row = 0;

		Text titleText = new Text("Employment Application");
		titleText.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
		grid.add(titleText, 0, row++, 2, 1);

		// --- Personal Information ---
		Text personalHeader = new Text("Personal Information");
		personalHeader.setFont(Font.font("Tahoma", FontWeight.BOLD, 14));
		grid.add(personalHeader, 0, row++, 2, 1);

		grid.add(new Label("Full Name:"), 0, row);
		TextField fullNameField = new TextField();
		fullNameField.setPromptText("Full Name");
		grid.add(fullNameField, 1, row++);

		grid.add(new Label("Current Address:"), 0, row);
		TextField addressField = new TextField();
		addressField.setPromptText("Current Address");
		grid.add(addressField, 1, row++);

		grid.add(new Label("Contact Number:"), 0, row);
		TextField contactField = new TextField();
		contactField.setPromptText("Contact Number");
		grid.add(contactField, 1, row++);

		grid.add(new Label("Email Address:"), 0, row);
		TextField emailField = new TextField();
		emailField.setPromptText("Email Address");
		grid.add(emailField, 1, row++);

		grid.add(new Label("Highest Educational Attainment:"), 0, row);
		TextField educationField = new TextField();
		educationField.setPromptText("e.g., Bachelor's Degree");
		grid.add(educationField, 1, row++);

		grid.add(new Label("Gender:"), 0, row);
		ToggleGroup genderGroup = new ToggleGroup();
		RadioButton maleRadio = new RadioButton("Male");
		RadioButton femaleRadio = new RadioButton("Female");
		RadioButton otherRadio = new RadioButton("Other");
		maleRadio.setToggleGroup(genderGroup);
		femaleRadio.setToggleGroup(genderGroup);
		otherRadio.setToggleGroup(genderGroup);
		HBox genderBox = new HBox(12, maleRadio, femaleRadio, otherRadio);
		grid.add(genderBox, 1, row++);

		// --- Employment Eligibility ---
		Text eligibilityHeader = new Text("Employment Eligibility");
		eligibilityHeader.setFont(Font.font("Tahoma", FontWeight.BOLD, 14));
		grid.add(eligibilityHeader, 0, row++, 2, 1);

		grid.add(new Label("Date Available:"), 0, row);
		DatePicker dateAvailablePicker = new DatePicker();
		dateAvailablePicker.setPromptText("mm/dd/yyyy");
		grid.add(dateAvailablePicker, 1, row++);

		grid.add(new Label("Desired Position:"), 0, row);
		TextField positionField = new TextField();
		positionField.setPromptText("Desired Position");
		grid.add(positionField, 1, row++);

		grid.add(new Label("Desired Salary:"), 0, row);
		TextField salaryField = new TextField();
		salaryField.setPromptText("Desired Salary");
		grid.add(salaryField, 1, row++);

		grid.add(new Label("Legally authorized to work?"), 0, row);
		ToggleGroup authGroup = new ToggleGroup();
		RadioButton authYes = new RadioButton("Yes");
		RadioButton authNo = new RadioButton("No");
		authYes.setToggleGroup(authGroup);
		authNo.setToggleGroup(authGroup);
		grid.add(new HBox(12, authYes, authNo), 1, row++);

		grid.add(new Label("Relatives working here?"), 0, row);
		ToggleGroup relativesGroup = new ToggleGroup();
		RadioButton relYes = new RadioButton("Yes");
		RadioButton relNo = new RadioButton("No");
		relYes.setToggleGroup(relativesGroup);
		relNo.setToggleGroup(relativesGroup);
		grid.add(new HBox(12, relYes, relNo), 1, row++);

		grid.add(new Label("If yes, please explain:"), 0, row);
		TextField explainField = new TextField();
		explainField.setPromptText("Explanation (if applicable)");
		grid.add(explainField, 1, row++);

		// --- Applicant's Signature ---
		grid.add(new Label("Applicant's Signature:"), 0, row);
		TextField signatureField = new TextField();
		signatureField.setPromptText("Type full name as signature");
		grid.add(signatureField, 1, row++);

		// --- Submit Button ---
		Button submitButton = new Button("Submit");
		HBox buttonBox = new HBox(10, submitButton);
		buttonBox.setAlignment(Pos.BOTTOM_RIGHT);
		grid.add(buttonBox, 1, row++);

		submitButton.setOnAction(event -> {
			String fullName = fullNameField.getText().trim();
			String currentAddress = addressField.getText().trim();
			String contactNumber = contactField.getText().trim();
			String emailAddress = emailField.getText().trim();
			String highestEducation = educationField.getText().trim();
			String gender = genderGroup.getSelectedToggle() != null
					? ((RadioButton) genderGroup.getSelectedToggle()).getText()
					: StringPool.BLANK;
			String dateAvailable = dateAvailablePicker.getValue() != null
					? dateAvailablePicker.getValue().toString()
					: (dateAvailablePicker.getEditor().getText() != null
							? dateAvailablePicker.getEditor().getText().trim()
							: StringPool.BLANK);
			String desiredPosition = positionField.getText().trim();
			String desiredSalary = salaryField.getText().trim();
			String legallyAuthorized = authGroup.getSelectedToggle() != null
					? ((RadioButton) authGroup.getSelectedToggle()).getText()
					: StringPool.BLANK;
			String relativesWorking = relativesGroup.getSelectedToggle() != null
					? ((RadioButton) relativesGroup.getSelectedToggle()).getText()
					: StringPool.BLANK;
			String relativesExplanation = explainField.getText().trim();
			String applicantSignature = signatureField.getText().trim();

			if (!StringPool.BLANK.equals(fullName)
					&& !StringPool.BLANK.equals(currentAddress)
					&& !StringPool.BLANK.equals(contactNumber)
					&& !StringPool.BLANK.equals(emailAddress)
					&& !StringPool.BLANK.equals(highestEducation)
					&& !StringPool.BLANK.equals(gender)
					&& !StringPool.BLANK.equals(dateAvailable)
					&& !StringPool.BLANK.equals(desiredPosition)
					&& !StringPool.BLANK.equals(desiredSalary)
					&& !StringPool.BLANK.equals(legallyAuthorized)
					&& !StringPool.BLANK.equals(relativesWorking)
					&& !StringPool.BLANK.equals(applicantSignature)) {

				try {
					EmploymentApplication applicationObj = createApplicationObject(
							fullName, currentAddress, contactNumber, emailAddress,
							highestEducation, gender, dateAvailable, desiredPosition,
							desiredSalary, legallyAuthorized, relativesWorking,
							relativesExplanation, applicantSignature);

					int insertedId = dao.saveApplication(applicationObj);
					if (insertedId > 0) {
						alert("Submit", "Application saved successfully! (ID: " + insertedId + ")",
								AlertType.INFORMATION);
						clearFields(fullNameField, addressField, contactField, emailField,
								educationField, genderGroup, dateAvailablePicker, positionField,
								salaryField, authGroup, relativesGroup, explainField, signatureField);
					} else {
						alert("Error", "Failed to save application!", AlertType.ERROR);
					}
				} catch (SQLException ex) {
					System.out.println("Exception occurred");
					System.out.println(ex.getMessage());
					System.out.println(ex.getErrorCode());
					System.out.println(ex.getSQLState());
					alert("Database Error", ex.getMessage(), AlertType.ERROR);
				}
			} else {
				alert("Error", "Please complete all required fields!", AlertType.ERROR);
			}
		});

		ScrollPane scrollPane = new ScrollPane(grid);
		scrollPane.setFitToWidth(true);

		Scene scene = new Scene(scrollPane, 520, 620);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private EmploymentApplication createApplicationObject(
			String fullName, String currentAddress, String contactNumber, String emailAddress,
			String highestEducation, String gender, String dateAvailable, String desiredPosition,
			String desiredSalary, String legallyAuthorized, String relativesWorking,
			String relativesExplanation, String applicantSignature) {

		EmploymentApplication app = new EmploymentApplication();
		app.setFullName(fullName);
		app.setCurrentAddress(currentAddress);
		app.setContactNumber(contactNumber);
		app.setEmailAddress(emailAddress);
		app.setHighestEducation(highestEducation);
		app.setGender(gender);
		app.setDateAvailable(dateAvailable);
		app.setDesiredPosition(desiredPosition);
		app.setDesiredSalary(desiredSalary);
		app.setLegallyAuthorized(legallyAuthorized);
		app.setRelativesWorking(relativesWorking);
		app.setRelativesExplanation(relativesExplanation);
		app.setApplicantSignature(applicantSignature);
		return app;
	}

	private void clearFields(TextField fullNameField, TextField addressField, TextField contactField,
			TextField emailField, TextField educationField, ToggleGroup genderGroup,
			DatePicker dateAvailablePicker, TextField positionField, TextField salaryField,
			ToggleGroup authGroup, ToggleGroup relativesGroup, TextField explainField,
			TextField signatureField) {

		fullNameField.clear();
		addressField.clear();
		contactField.clear();
		emailField.clear();
		educationField.clear();
		genderGroup.selectToggle(null);
		dateAvailablePicker.setValue(null);
		dateAvailablePicker.getEditor().clear();
		positionField.clear();
		salaryField.clear();
		authGroup.selectToggle(null);
		relativesGroup.selectToggle(null);
		explainField.clear();
		signatureField.clear();
	}

	public void alert(String title, String message, AlertType alertType) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	public static void main(String[] args) {
		launch(args);
	}
}