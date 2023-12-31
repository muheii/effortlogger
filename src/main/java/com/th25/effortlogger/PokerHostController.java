package com.th25.effortlogger;

import com.th25.effortlogger.helpers.HTTPHelper;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

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
        HTTPHelper.sendRequest("http://localhost/Demo/room_handler.php", roomName + "," + storyTextArea.getText());
    }

    @FXML
    protected void reset() throws IOException {
        HTTPHelper.sendRequest("http://localhost/Demo/reset.php", roomName);
    }
}
