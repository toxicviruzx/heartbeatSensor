package sample;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ControllerDoctor implements Initializable, ControlledScreen{
    ScreensController myController;
    private FadeTransition fadeIn = new FadeTransition(
            Duration.millis(1900)
    );

    @FXML
    Button viewDetailsData;

    @FXML
    Button searchButton;

    @FXML
    TextField searchForPatient;

    @FXML
    Label searchErrorLabel;

    @FXML
    Button logoutButton;

    private ObservableList<TableData> tableDataCells = FXCollections.observableArrayList();

    public void myFunction(String text){
        System.out.println(text);
    }

    public void initialize(URL location, ResourceBundle resources) {
        fadeIn.setNode(searchErrorLabel);
        searchErrorLabel.setVisible(false);
        fadeIn.setAutoReverse(false);
        fadeIn.setCycleCount(1);

//        viewDetailsData.setDisable(true);
    }

    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

    private String searchBy(String keyword){
        if(keyword.matches(".*\\d+.*") && !(keyword.contains("/") || keyword.contains("-"))){ //make sure that the entered string contains number
            return "id";
        } else if((keyword.contains("/") || keyword.contains("-")) && keyword.matches(".*\\d+.*")) {
            return "birthday";
        }
        else {
            searchErrorLabel.setVisible(true);
            fadeIn.setFromValue(1.0);
            fadeIn.setToValue(0.0);
            fadeIn.play();
            return "unknown";
        }
    }

    public void handleLogoutButton(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation dialog");
        alert.setHeaderText("Want to log out?");
        alert.setContentText("Are you sure that you want to log out?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            //pseudo code:
            //log-out via update the status field in the database: online to be offline
            //performing the scene movement
            try {
//                File file = new File("D:\\Studying\\Java\\HeartBeatProject_DocterSide\\src\\main\\resources\\heart-beat-icon.png");
//                Image image = new Image(file.toURI().toString());
                TrayNotification tray = new TrayNotification();
                tray.setNotificationType(NotificationType.CUSTOM);
                tray.setTitle("Logout Success");
                tray.setMessage("Goodbye doctor! See you again!");
//                tray.setImage(image);
                tray.setAnimationType(AnimationType.FADE);
                tray.showAndDismiss(Duration.millis(1500));
                tray.setRectangleFill(Color.valueOf("#ff7b68"));
                Stage stage2 = (Stage) ((Node)event.getSource()).getScene().getWindow();
                stage2.hide();
                //to hide the previous page

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/loginPage.fxml"));
                Parent root = (Parent) loader.load();
                ControllerLoginPage secController = loader.getController();
//                secController.getID("123456789");

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
    }

    public void handleSearchButton(){
        System.out.println(searchBy(searchForPatient.getText()));
    }

    public void handleViewDetailsData(ActionEvent event){

        try {
            Stage stage2 = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage2.hide();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/realTimeHeartBeat.fxml"));
            Parent root = (Parent) loader.load();
            realTimeViewingController secController = loader.getController();
            secController.getID("123456789");

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
}