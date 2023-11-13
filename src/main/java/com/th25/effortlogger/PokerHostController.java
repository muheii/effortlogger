package com.th25.effortlogger;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

public class PokerHostController {
    private String roomName;

    @FXML
    private TextArea storyTextArea;

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    @FXML
    protected void sendStory() throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost("http://localhost/Demo/room_handler.php");

        StringEntity params = new StringEntity(roomName + "," + storyTextArea.getText());

        request.addHeader("content-type", "application/x-www-form-urlencoded");
        request.setEntity(params);

        httpClient.execute(request);
    }
}
