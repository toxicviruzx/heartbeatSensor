package sample;

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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerPatientView implements Initializable {
    private String id;
    private String firstNameText;
    private String lastNameText;
    private boolean isRunning = false;

    @FXML
    Button logOutButton;

    @FXML
    Button viewRealTimeButton;

    @FXML
    Button viewPreviousDataButton;

    @FXML
    Label firstName;

    @FXML
    Label lastName;

    @FXML
    Label gender;

    @FXML
    Label residentId;

    @FXML
    LineChart<String,Integer> lineChart;

    @FXML
    private NumberAxis bpmLineChart;

    @FXML
    private CategoryAxis timeLineChart;

    @FXML
    public Label doctorCommentText;

    @FXML
    public Label bpmLabel;

    @FXML
    public ImageView heartImage;


    public void getId(String id){
        this.id = id;
        DBmanager db = DBmanager.getInstance();
        AccountJDBC account = AccountJDBC.getInstance(db);
        residentId.setText(this.id);
        firstNameText = account.getFirstName(this.id);
        lastNameText = account.getLastName(this.id);
        firstName.setText(firstNameText);
        lastName.setText(lastNameText);
        gender.setText(account.getGender(this.id));
        bpmLabel.setText(account.getLatestBpm(id));
        doctorCommentText.setText(account.getDoctorComment(id));
        createLineChart();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lineChart.setCreateSymbols(false);
        bpmLineChart.setAutoRanging(true);
        bpmLineChart.setLowerBound(0);
        timeLineChart.setAutoRanging(true);
    }

    public void handleViewRealTimeButton(){
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
                            seriesLineChart.getData().add(new XYChart.Data<>(account.getLatestTime(id), Integer.parseInt(String.valueOf(latestBpm))));
                            bpmLabel.setText(latestBpm);
                        });
                    }
                }
            }
        });
        thread.start();
    }

    public void handleLogoutButton(ActionEvent event){
        DBmanager db = DBmanager.getInstance();
        AccountJDBC account = AccountJDBC.getInstance(db);
        account.updateOnlineStatus(false,this.id);
        try {
            TrayNotification tray = new TrayNotification();
            tray.setNotificationType(NotificationType.CUSTOM);
            tray.setTitle("Logout Success");
            tray.setMessage("Goodbye "+ firstNameText + " " + lastNameText +"! See you again!");
            tray.setAnimationType(AnimationType.FADE);
            tray.showAndDismiss(Duration.millis(1500));
            tray.setRectangleFill(Color.valueOf("#ff7b68"));
            Stage stage2 = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage2.hide();
            //to hide the previous page

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/loginPage.fxml"));
            Parent root = (Parent) loader.load();
            ControllerLoginPage secController = loader.getController();

            Stage stage = new Stage();
            stage.initStyle(StageStyle.UTILITY);
            stage.setScene(new Scene(root, 1160, 710));
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
