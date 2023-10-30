package application.old;
	
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class EffortLogger extends Application {
	public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("EffortLogger V2");
        
        final Label titleLabel = new Label("Effort Logger");
        titleLabel.getStyleClass().add("title-label");
        
        // Step 1
        final Label oneLabel = new Label("1. When you start an activity, press the 'Start an Activity' button.");
		oneLabel.getStyleClass().add("step-label");
        
        final Button startActivityButton = new Button();
        startActivityButton.setText("Start an Activity");
        startActivityButton.getStyleClass().add("button");
        
        final Button defectConsoleButton = new Button();
        defectConsoleButton.setText("Proceed to Defect Log Console");
        
        // Step 2
        final Label twoLabel = new Label("2. Select the project, life cycle step, effort category, and deliverable from the following lists:");
        twoLabel.getStyleClass().add("step-label");
        
        // Dropdown 1
        final Label projectLabel = new Label("Project:");
        ObservableList<String> projectOptions = 
        		FXCollections.observableArrayList(
        			"Business Project",
        			"Development Project"
        		);
        final ComboBox<String> projectComboBox = new ComboBox<String>(projectOptions);
        
        // Dropdown 2
        final Label lifeCycleLabel = new Label("Life Cycle Step:");
        ObservableList<String> lifeCycleOptions = 
        		FXCollections.observableArrayList(
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
        final ComboBox<String> lifeCycleComboBox = new ComboBox<String>(lifeCycleOptions);
        
        // Dropdown 3
        final Label effortCategoryLabel = new Label("Effort Category:");
        ObservableList<String> effortCategoryOptions = 
        		FXCollections.observableArrayList(
        			"lol"
        		);
        final ComboBox<String> effortCategoryComboBox = new ComboBox<String>(effortCategoryOptions);
        
        // Dropdown 4
        final Label dependentLabel = new Label("Plan:");
        ObservableList<String> dependentOptions = 
        		FXCollections.observableArrayList(
        			"lol"
        		);
        final ComboBox<String> dependentComboBox = new ComboBox<String>(dependentOptions);
        
        // Step 3
        final Label threeLabel = new Label("3. Press the 'Stop this Activity' to generate an effort log entry using the attributes above.");
        threeLabel.getStyleClass().add("step-label");
        
        final Button stopActivityButton = new Button();
        stopActivityButton.setText("Stop this Activity");
        
        final Button effortEditorButton = new Button();
        effortEditorButton.setText("Proceed to Effort Log Editor");
        
        // Step 4
        final Label fourLabel = new Label("4. Unless you are done for the day, it is best to perform steps 1 and 2 above before resuming work.");
        fourLabel.getStyleClass().add("step-label");
        
        // LAYOUT MANAGENT
        
        // Step 1 Layout
        HBox oneHbox = new HBox();
        VBox.setMargin(oneHbox, new Insets(0, 150, 0, 25));
        oneHbox.getChildren().add(startActivityButton);
        oneHbox.getChildren().add(defectConsoleButton);
        
        // Step 2 Layout
        // Top Dropdowns
        VBox projectVbox = new VBox();
        HBox.setMargin(projectVbox, new Insets(0, 150, 0, 25));
        projectVbox.getChildren().add(projectLabel);
        projectVbox.getChildren().add(projectComboBox);
        
        VBox lifeCycleVbox = new VBox();
        lifeCycleVbox.getChildren().add(lifeCycleLabel);
        lifeCycleVbox.getChildren().add(lifeCycleComboBox);
        
        HBox topDropdownHbox = new HBox();
        topDropdownHbox.getChildren().add(projectVbox);
        topDropdownHbox.getChildren().add(lifeCycleVbox);
        
        // Bottom Dropdowns
        VBox effortCategoryVbox = new VBox();
        HBox.setMargin(effortCategoryVbox, new Insets(0, 150, 0, 25));
        effortCategoryVbox.getChildren().add(effortCategoryLabel);
        effortCategoryVbox.getChildren().add(effortCategoryComboBox);
        
        VBox dependentVbox = new VBox();
        dependentVbox.getChildren().add(dependentLabel);
        dependentVbox.getChildren().add(dependentComboBox);
        
        HBox bottomDropdownHbox = new HBox();
        bottomDropdownHbox.getChildren().add(effortCategoryVbox);
        bottomDropdownHbox.getChildren().add(dependentVbox);
        
        // Step 3 Layout
        HBox threeHbox = new HBox();
        VBox.setMargin(threeHbox, new Insets(0, 150, 0, 25));
        threeHbox.getChildren().add(stopActivityButton);
        threeHbox.getChildren().add(effortEditorButton);
        
        /*final Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello!");
            }
        });*/
        
        VBox root = new VBox();
        root.getChildren().add(titleLabel);
        root.getChildren().add(oneLabel);
        root.getChildren().add(oneHbox);
        root.getChildren().add(twoLabel);
        root.getChildren().add(topDropdownHbox);
        root.getChildren().add(bottomDropdownHbox);
        root.getChildren().add(threeLabel);
        root.getChildren().add(threeHbox);
        root.getChildren().add(fourLabel);
        
        root.getStylesheets().add(this.getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();
    }
}
