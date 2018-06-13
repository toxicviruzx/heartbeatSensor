package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class realTimeViewingController implements Initializable {
    private boolean isOnline;
    private String id;
    private String firstNameLabel;
    private String lastNameLabel;
    private String genderLabel;
    private String status;

    @FXML
    Button viewRealTimeButton;

    @FXML
    Button backButton;

    @FXML
    Button alarmingButton;

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
    LineChart <Integer,Integer> lineChart;

    public void getID(String id){
        this.id = id;

        DBmanager db = DBmanager.getInstance();
        AccountJDBC account = AccountJDBC.getInstance(db);

        residentId.setText(this.id);
        firstName.setText(account.getFirstName(this.id));
        lastName.setText(account.getLastName(this.id));
        gender.setText(account.getGender(this.id));
        String onlineStatusString = account.getOnlineStatus(this.id);
        if(onlineStatusString.matches("0")){
            onlineStatus.setText("Offline");
        } else {
            onlineStatus.setText("Online");
        }
    }

    public void initialize(URL location, ResourceBundle resources) {
        DBmanager db = DBmanager.getInstance();
        AccountJDBC account = AccountJDBC.getInstance(db);

        System.out.println("Hello this is the real time scene");





//        System.out.println("real time scene");
//        isOnline = false;

//        System.out.println(account.getFirstName(this.id));

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
        if(!isOnline){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("This patient is currently not available");
            alert.setContentText("Please come back later");
            alert.show();
//            System.out.println("Is not online");
        }
    }



    public void handleBackButton(ActionEvent event){
        try {
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
}
