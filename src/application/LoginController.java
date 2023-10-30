package application;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginController {
	private Stage stage;
	private Scene scene;
	
	@FXML private TextField userTextField;
	@FXML private PasswordField passwordField;
	@FXML private Text errorText;
	
	@FXML
	public void loginValidation(ActionEvent event) throws IOException {
		Pattern p = Pattern.compile("^[a-zA-Z0-9_.-]*$");
		Matcher m = p.matcher(userTextField.getText());
		
		boolean b = m.matches();
		
		if(userTextField.getText().length() < 5) {
			loginFailure(0);
		} else if(!b) {
			loginFailure(1);
//		} else if(passwordField.getText() == null) {
//			loginFailure(2);
		} else {
			loginSuccess(event);
		}
	}
	
	private void loginFailure(int flag) {
		String errorMessage;
		
		if (flag == 0) { 
			errorMessage = " Username too short";
		} else if(flag == 1) {
			errorMessage = "   Invalid username";
		} else if(flag == 2) {
			errorMessage = "Please enter a password";
		} else {
			errorMessage = "";
		}
		
		errorText.setText(errorMessage);
	}
	
	@FXML
	public void loginSuccess(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("EffortLogger.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}
