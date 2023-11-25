package com.th25.effortlogger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EffortLoggerController implements Initializable {
	
	// Actual values for each of the timer segments.
	private int elapsedTime = 0;
	private int seconds = 0;
	private int minutes = 0;
	private int hours = 0;
	
	// Formatted strings used to output the timer onto the user interface.
	private String secondString, minuteString, hourString;
	
	// Ensures that the timer can't be started while it's already running.
	private boolean effortInProgress = false;

	Timer t = new Timer();
	
	// Will contain the options for each of the four drop-down menus.
	ObservableList<String> projectOptions;
	ObservableList<String> lifeCycleOptions;
	ObservableList<String> effortCategoryOptions;
	ObservableList<String> dependentOptions;
	
	@FXML Text timerText;
	@FXML ComboBox<String> projectComboBox;
	@FXML ComboBox<String> lifeCycleComboBox;
	@FXML ComboBox<String> effortCategoryComboBox;
	@FXML ComboBox<String> dependentComboBox;
	
	
	@Override
	// Runs at the very beginning of when EffortLogger.fxml is loaded.
	public void initialize(URL url, ResourceBundle rb) {
		Timer effortLogBackup = new Timer();
		
		// Initializes the drop-down menus into the default states that were in the original Excel workbook.
		projectOptions = 
        		FXCollections.observableArrayList(
        			"Business Project",
        			"Development Project"
        		);
		projectComboBox.setItems(projectOptions);
		
		effortCategoryOptions = 
				FXCollections.observableArrayList(
					"Plans",
					"Deliverables",
					"Interruptions",
					"Defects",
					"Others"
				);
		effortCategoryComboBox.setItems(effortCategoryOptions);
		
		// Copies the data in the current logs into a new backup file.
		effortLogBackup.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				InputStream is = null;
			    OutputStream os = null;
			    
			    try {
					is = new FileInputStream("effortLogs");
			        os = new FileOutputStream("effortLogs.bak");
			        
			        byte[] buffer = new byte[1024];
			        int length;
			        while ((length = is.read(buffer)) > 0) {
			            os.write(buffer, 0, length);
			        }
			        
			        is.close();
			    	os.close();
			    } catch (IOException e) {
					e.printStackTrace();
			    }
			}
		}, 0, 900000);
	}
	
	
	@FXML
	// Modifies the life cycle combo-box data based on the selected project type.
	void updateProjectDropdowns() {
		if(projectComboBox.getValue() == "Business Project") {
			lifeCycleOptions = FXCollections.observableArrayList(
	        			"Planning",
	        			"Information Gathering",
	        			"Information Understanding",
	        			"Verifying",
	        			"Outlining",
	        			"Drafting",
	        			"Finalizing",
	        			"Team Meeting",
	        			"Coach Meeting",
	        			"Stakeholder Meeting"
	        		);
		} else if(projectComboBox.getValue() == "Development Project") {
			lifeCycleOptions = FXCollections.observableArrayList(
	        			"Problem Understanding",
	        			"Conceptual Design Plan",
	        			"Requirements",
	        			"Conceptual Design",
	        			"Conceptual Design Review",
	        			"Detailed Design Plan",
	        			"Detailed Design/Prototype",
	        			"Detailed Design Review",
	        			"Implementation Plan",
	        			"Test Case Generation",
	        			"Solution Specification",
	        			"Solution Review",
	        			"Solution Implementation",
	        			"Unit/System Test",
	        			"Reflection",
	        			"Repository Update"
	        		);
		}
		
		lifeCycleComboBox.setValue(lifeCycleOptions.get(0));
		lifeCycleComboBox.setItems(lifeCycleOptions);
	}
	
	@FXML
	// Modifies the final combo-box data based on the selected effort category.
	void updateDependentDropdowns() {
		switch (effortCategoryComboBox.getValue()) {
			case "Plans" -> dependentOptions = FXCollections.observableArrayList(
					"Project Plan",
					"Risk Managment Plan",
					"Conceptual Design Plan",
					"Detailed Design Plan",
					"Implementation Plan");
			case "Deliverables" -> dependentOptions = FXCollections.observableArrayList(
					"Conceptual Design",
					"Detailed Design",
					"Test Cases",
					"Solution",
					"Reflection",
					"Outline",
					"Draft",
					"Report",
					"User Defined",
					"Other");
			case "Interruptions" -> dependentOptions = FXCollections.observableArrayList(
					"Break",
					"Phone",
					"Teammate",
					"Visitor",
					"Other");
			case "Defects" -> dependentOptions = FXCollections.observableArrayList("");
		}
		
		dependentComboBox.setValue(dependentOptions.get(0));
		dependentComboBox.setItems(dependentOptions);
	}
	
	@FXML
	// Starts the timer and updates the strings every second.
	void startActivity(ActionEvent event) {
		if(!effortInProgress) {
			t = new Timer();
			effortInProgress = true;
			
	 		t.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					elapsedTime += 1000;
					
					hours = elapsedTime/3600000;
					minutes = elapsedTime/60000 % 60;
					seconds = elapsedTime/1000 % 60;
					
					hourString = String.format("%02d", hours);
					minuteString = String.format("%02d", minutes);
					secondString = String.format("%02d", seconds);
					
					timerText.setText(hourString + ":" + minuteString + ":" + secondString);
				}
				
			}, 1000, 1000);
		}
	}
	
	@FXML
	// Stops the timer and saves all of the effort data into the effort log file.
	void stopActivity(ActionEvent event) {
		if(effortInProgress) {
			effortInProgress = false;
			
			try {
				FileWriter logWriter = new FileWriter("effortLogs", true);
				logWriter.write(projectComboBox.getValue() + "," 
						+ lifeCycleComboBox.getValue() + "," 
						+ effortCategoryComboBox.getValue() + "," 
						+ dependentComboBox.getValue() + ","
						+ hourString + ":" + minuteString + ":" + secondString + "\n");
				logWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			elapsedTime = 0;
			seconds = 0;
			minutes = 0;
			hours = 0;
			
			t.cancel();
		}
	}
	
	@FXML
	void editEffort(ActionEvent event) {
		StackPane newWindowLayout = new StackPane();
		Scene newScene = new Scene(newWindowLayout, 720, 400);
		Stage newWindow = new Stage();
		
		newWindow.setTitle("Second Window");
		newWindow.setScene(newScene);
		newWindow.setResizable(false);
		newWindow.show();

		//newWindowLayout.getChildren().add(logEntryLoader.load());
	}

	@FXML
	void openPlanningPoker(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("PokerHome.fxml"));
		Scene scene = new Scene(root, 600, 400);
		Stage stage = new Stage();

		stage.setScene(scene);
		stage.setTitle("Planning Poker");
		stage.setResizable(false);
		stage.show();
	}
}
