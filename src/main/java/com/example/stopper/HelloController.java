package com.example.stopper;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
    @FXML
    private Button btnStartStop;
    @FXML
    private Button btnResetReszido;
    @FXML
    private Label stopper;
    @FXML
    private ListView<String> reszidoLista;
    @FXML
    private boolean futAStopper;
    private Timer stopperTimer;
    private LocalDateTime startTime;
    private Duration leallitasIdeje;

    @FXML
    public void initialize(){
        futAStopper = false;
        leallitasIdeje = Duration.ZERO;
    }


    public void startStopClick(ActionEvent actionEvent) {
        if(futAStopper){
            stopperStop();
        } else{
            stopperStart();
        }
    }


    public void resetReszidoClick(ActionEvent actionEvent) {
        if(futAStopper){
            reszido();
        } else{
            reset();
        }
    }

    private void reset() {
        stopper.setText("00:00.000");
        reszidoLista.getItems().clear();
        leallitasIdeje = Duration.ZERO;
    }

    private void reszido() {
        String reszido = stopper.getText();
        reszidoLista.getItems().add(reszido);
    }

    private void stopperStart() {
        btnStartStop.setText("Stop");
        btnResetReszido.setText("Részidő");
        futAStopper = true;
        stopperTimer = new Timer();
        startTime = LocalDateTime.now();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                LocalDateTime aktualisIdopont = LocalDateTime.now();
                Duration elteltIdo = Duration.between(startTime, aktualisIdopont);
                elteltIdo = elteltIdo.plus(leallitasIdeje);
                int perc = elteltIdo.toMinutesPart();
                int masodperc = elteltIdo.toSecondsPart();
                int ezredMasodperc = elteltIdo.toMillisPart();
                Platform.runLater(() -> stopper.setText(String.format("%02d:%02d.%03d",perc, masodperc, ezredMasodperc)));
            }
        };
        stopperTimer.schedule(timerTask, 1, 1);
    }

    private void stopperStop() {
        btnStartStop.setText("Start");
        btnResetReszido.setText("Reset");
        Duration elteltIdo = Duration.between(startTime, LocalDateTime.now());
        leallitasIdeje = leallitasIdeje.plus(elteltIdo);
        futAStopper = false;
        stopperTimer.cancel();
    }




}