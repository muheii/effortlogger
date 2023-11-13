package com.th25.effortlogger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class PokerHomeController {
    HttpClient httpClient = HttpClientBuilder.create().build();

    @FXML
    private TextField roomName;

    @FXML
    private Label errorLabel;

    @FXML
    protected void createRoom(ActionEvent event) throws IOException {
        HttpPost request = new HttpPost("http://localhost/Demo/create_room_handler.php");

        StringEntity params = new StringEntity(roomName.getText());

        request.addHeader("content-type", "application/x-www-form-urlencoded");
        request.setEntity(params);

        HttpResponse response = httpClient.execute(request);
        HttpEntity entity = response.getEntity();
        String responseString = EntityUtils.toString(entity, "UTF-8");

        if(responseString.equals("SUCCESS")) {
            createHostScene(event, roomName.getText());
        } else if(responseString.equals("ROOM_EXISTS")) {
            errorLabel.setText("Room already exists.");
        }
    }

    @FXML
    protected void joinRoom(ActionEvent event) throws IOException {
        HttpPost request = new HttpPost("http://localhost/Demo/join_room_handler.php");

        StringEntity params = new StringEntity(roomName.getText());

        request.addHeader("content-type", "application/x-www-form-urlencoded");
        request.setEntity(params);

        HttpResponse response = httpClient.execute(request);
        HttpEntity entity = response.getEntity();
        String responseString = EntityUtils.toString(entity, "UTF-8");

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
        stage.show();
    }
}
