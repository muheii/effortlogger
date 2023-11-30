package com.th25.effortlogger;

import com.th25.effortlogger.helpers.HTTPHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class PokerHomeController {
    @FXML
    private TextField roomName;

    @FXML
    private Label errorLabel;

    @FXML
    protected void createRoom(ActionEvent event) throws IOException {
        String responseString = HTTPHelper.sendRequest("http://localhost/Demo/create_room_handler.php", roomName.getText());

        if(responseString.equals("SUCCESS")) {
            createHostScene(event, roomName.getText());
        } else if(responseString.equals("ROOM_EXISTS")) {
            errorLabel.setText("Room already exists.");
        }
    }

    @FXML
    protected void joinRoom(ActionEvent event) throws IOException {
        String responseString = HTTPHelper.sendRequest("http://localhost/Demo/join_room_handler.php", roomName.getText());

        if(responseString.equals("ROOM_EXISTS")) {
            createUserScene(event, roomName.getText());
        } else if(responseString.equals("ROOM_DOESNT_EXIST")) {
            errorLabel.setText("Room doesn't exist.");
        }
    }

    private void createUserScene(ActionEvent event, String roomName) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PokerUser.fxml"));
        Parent root = loader.load();

        PokerUserController ctrl = loader.getController();
        ctrl.setRoomName(roomName);

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Planning Poker - " + roomName);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void createHostScene(ActionEvent event, String roomName) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PokerHost.fxml"));
        Parent root = loader.load();

        PokerHostController ctrl = loader.getController();
        ctrl.setRoomName(roomName);

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Planning Poker - " + roomName);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
