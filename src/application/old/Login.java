package application.old;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Login extends Application {
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		final Label loginLabel = new Label("Login");
		loginLabel.getStyleClass().add("login-label");
		
		final Label userLabel = new Label("User");
		userLabel.getStyleClass().add("p-label");
		final TextField userTextField = new TextField();
		
		final Label passwordLabel = new Label("Password");
		passwordLabel.getStyleClass().add("p-label");
		final PasswordField passwordTextField = new PasswordField();
		
		final Button loginButton = new Button();
		loginButton.setText("Login");
		
		// Layout Management
		final VBox userVbox = new VBox();
		userVbox.getChildren().add(userLabel);
		userVbox.getChildren().add(userTextField);
		
		final VBox passwordVbox = new VBox();
		passwordVbox.getChildren().add(passwordLabel);
		passwordVbox.getChildren().add(passwordTextField);
		
		VBox root = new VBox();
		root.getChildren().add(loginLabel);
		root.getChildren().add(userVbox);
		root.getChildren().add(passwordVbox);
		root.getChildren().add(loginButton);
		
		root.getStylesheets().add(this.getClass().getResource("login.css").toExternalForm());
		primaryStage.setScene(new Scene(root, 1280, 720));
		primaryStage.show();
	}
}
