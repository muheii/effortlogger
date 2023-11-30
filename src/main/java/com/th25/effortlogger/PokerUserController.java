package com.th25.effortlogger;

import com.th25.effortlogger.helpers.HTTPHelper;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class PokerUserController implements Initializable {

    String roomName;
    String userID;
    int estimate;

    final Timer timer = new Timer();

    @FXML
    private Label selectedText;
    @FXML
    private Label meanText;
    @FXML
    private Label storyLabel;

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public double mean(int[] m) {
        double sum = 0;
        for (int j : m) {
            sum += j;
        }

        return sum / m.length;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userID = UUID.randomUUID().toString();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    String response = HTTPHelper.sendRequest("http://localhost/Demo/get_info_handler.php", roomName);
                    String[] responseArray = response.split("~");

                    if (responseArray.length == 1) {
                        Platform.runLater(() -> storyLabel.setText(responseArray[0]));
                    } else if (responseArray.length == 2) {
                        int[] numbers = Arrays.stream(responseArray[1].split(",")).mapToInt(Integer::parseInt).toArray();

                        Platform.runLater(() -> {
                            meanText.setText("Mean: " + mean(numbers));
                            storyLabel.setText(responseArray[0]);
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 2000);
    }

    @FXML
    protected void estimateSelection(ActionEvent event) {
        estimate = Integer.parseInt(((Button)event.getSource()).getText());
        selectedText.setText("Estimate: " + estimate);
    }

    @FXML
    protected void sendEstimate() throws IOException {
        HTTPHelper.sendRequest("http://localhost/Demo/estimate_handler.php", roomName + "," + userID + "," + estimate);
    }
}