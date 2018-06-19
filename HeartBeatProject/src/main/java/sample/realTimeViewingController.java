package sample;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class realTimeViewingController implements Initializable {
    private boolean isOnline;
    private String id;
    private String firstNameLabel;
    private String lastNameLabel;
    private String genderLabel;
    private String status;
    private boolean isRunning = false;

    private FadeTransition fadeIn = new FadeTransition(
            Duration.millis(1900)
    );

    @FXML
    public Button viewRealTimeButton;

    @FXML
    public Button backButton;

    @FXML
    public Button alarmingButton;

    @FXML
    Label firstName;

    @FXML
    Label lastName;

    @FXML
    Label onlineStatus;

    @FXML
    Label gender;

    @FXML
    Label residentId;

    @FXML
    Label sendmessagelabel;

    @FXML
    TextArea doctorComment;

    @FXML
    Button subitcommentbutton;

    @FXML
    LineChart <String,Integer> lineChart;

    @FXML
    private NumberAxis bpmLineChart;

    @FXML
    private CategoryAxis timeLineChart;

    @FXML
    public ImageView heartImage;

    @FXML
    public Label bpmLabel;

    @FXML
    public Button viewPreviousDataButton;



    public void getID(String id){
        this.id = id;

        DBmanager db = DBmanager.getInstance();
        AccountJDBC account = AccountJDBC.getInstance(db);
        bpmLabel.setText(account.getLatestBpm(id));
        residentId.setText(this.id);
        firstName.setText(account.getFirstName(this.id));
        lastName.setText(account.getLastName(this.id));
        gender.setText(account.getGender(this.id));
        Platform.runLater(() -> createLineChart());
        String onlineStatusString = account.getOnlineStatus(this.id);
        if(onlineStatusString.matches("0")){
            onlineStatus.setText("Offline");
        } else {
            onlineStatus.setText("Online");
        }
    }

    public void initialize(URL location, ResourceBundle resources) {
        lineChart.setCreateSymbols(false);
        System.out.println("Hello this is the real time scene");
        bpmLineChart.setAutoRanging(true);
        bpmLineChart.setLowerBound(0);
        timeLineChart.setAutoRanging(true);
//        timeLineChart.setLowerBound(0);

        fadeIn.setNode(sendmessagelabel);
        sendmessagelabel.setVisible(false);
        fadeIn.setAutoReverse(false);
        fadeIn.setCycleCount(1);

        //pseudo code:
        //query all the previous day data to form a line chart
        //if the doctor click view current
        //check if the status field is online or not
        //if query_status == online
        //isOnline = true
        //start forming line chart with a thread every 1.2 seconds
        //check also the status of the online field in the database

    }

    public void handleViewRealTimeButton(){
//        if(!isOnline){
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Warning");
//            alert.setHeaderText("This patient is currently not available");
//            alert.setContentText("Please come back later");
//            alert.show();
////            System.out.println("Is not online");
//        }
        DBmanager db = DBmanager.getInstance();
        AccountJDBC account = AccountJDBC.getInstance(db);
        Thread thread = new Thread(() -> {
            synchronized (this){
                isRunning = true;
                Platform.runLater(() -> lineChart.getData().clear());
                XYChart.Series<String,Integer> seriesLineChart = new XYChart.Series<>();
                seriesLineChart.setName("Heart Beat per minute");
                Platform.runLater(() -> lineChart.getData().addAll(seriesLineChart));
                while (isRunning){
                    int preNumberOfBpm = account.getNumberOfBpm(id);
                    try {
                        heartImage.setVisible(false);
                        Thread.sleep(1000);
                        heartImage.setVisible(true);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(preNumberOfBpm < account.getNumberOfBpm(id)){ //this means there is new data being added
                        Platform.runLater(() -> {
                            String latestBpm = account.getLatestBpm(id);
                            seriesLineChart.getData().add(new XYChart.Data<String,Integer>((String) account.getLatestTime(id),Integer.parseInt(String.valueOf(latestBpm))));
                            bpmLabel.setText(latestBpm);
                        });
                    }
                }
            }
        });
        thread.start();
    }



    public void handleBackButton(ActionEvent event){
        try {
            isRunning = false;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DoctorPage.fxml"));
            Parent root = (Parent) loader.load();
            ControllerDoctor secController = loader.getController();
//            secController.myFunction("hello");
            Stage stage2 = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage2.hide();

            Stage stage = new Stage();
            stage.initStyle(StageStyle.UTILITY);
            stage.setScene(new Scene(root, 1160, 710));
//                    stage.setScene(new Scene(root));
            stage.setTitle("Heart Beat");
            stage.show();
            stage.setMinHeight(710);
            stage.setMinWidth(1160);
            stage.setMaxHeight(710);
            stage.setMaxWidth(1160);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleAlarmingButton(){
        Toolkit.getDefaultToolkit().beep();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation dialog");
        alert.setHeaderText("How this button work?");
        alert.setContentText("This button should only be used when you need to warn patients about their health status.");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            //pseudo code
            //update the alarming field in the database
        }
    }

    public void handleSendCommentButton(){
        if(doctorComment.getText().length() > 256){
            sendmessagelabel.setText("The maximum number of characters is 256");
            sendmessagelabel.setVisible(true);
            fadeIn.setFromValue(1.0);
            fadeIn.setToValue(0.0);
            fadeIn.play();
        } else if(doctorComment.getText().length() < 5){
            sendmessagelabel.setText("The minimum number of characters is 6");
            sendmessagelabel.setVisible(true);
            fadeIn.setFromValue(1.0);
            fadeIn.setToValue(0.0);
            fadeIn.play();
        } else {
            DBmanager db = DBmanager.getInstance();
            AccountJDBC account = AccountJDBC.getInstance(db);
            account.submitComment(doctorComment.getText(),id);
        }
    }

    public void handleViewPreviousDataButton(){
        isRunning = false;
        createLineChart();
    }

    public void createLineChart(){
        lineChart.getData().clear();
        DBmanager db = DBmanager.getInstance();
        AccountJDBC account = AccountJDBC.getInstance(db);
        XYChart.Series<String,Integer> seriesLineChart = new XYChart.Series<>();
        seriesLineChart.setName("Heart Beat per minute");
        ArrayList<Integer> bpm = account.getAllbpm(id);
        ArrayList time = account.getAllTime(id);
        for(int index = 0; index < bpm.size(); index++){
            seriesLineChart.getData().add(new XYChart.Data<String,Integer>((String) time.get(index),Integer.parseInt(String.valueOf(bpm.get(index)))));
        }
        lineChart.getData().addAll(seriesLineChart);
    }
}
