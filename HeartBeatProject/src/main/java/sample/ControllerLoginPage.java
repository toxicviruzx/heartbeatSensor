package sample;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerLoginPage implements Initializable, ControlledScreen {
    ScreensController myController;
    private boolean isLogin = false;
//    DBmanager db;
//    AccountJDBC account;
    private FadeTransition fadeIn = new FadeTransition(
            Duration.millis(1900)
    );
    private FadeTransition fadeInBlankUsernamePassword = new FadeTransition(
            Duration.millis(1900)
    );

    @FXML
    public PasswordField passWordField;

    @FXML
    public TextField userNameField;

    @FXML
    public Button loginButton;

//    @FXML
//    public Button registerButton;

    @FXML
    public Label wrongPasswordLabel;

    @FXML
    public Label blankUsernamePasswordLabel;

    @FXML
    public ProgressIndicator loginIndicator;

    @FXML
    public Label loginLabel;

    @FXML
    public Hyperlink hyperLinkRegister;

//    @FXML
//    public GridPane gridPaneClock;

//    @FXML
//    public TextFlow textFlowRegister;


    public void handleLoginPageButton(ActionEvent event) {
        if(!isLogin){
            if(userNameField.getText().matches("") || passWordField.getText().matches("")){
                blankUsernamePasswordLabel.setVisible(true);
                fadeInBlankUsernamePassword.setFromValue(1.0);
                fadeInBlankUsernamePassword.setToValue(0.0);
                fadeInBlankUsernamePassword.play();
            } else {
                try {
                    //login by updating the status field from offline to online
//                    File file = new File("D:\\Studying\\Java\\HeartBeatProject_DocterSide\\src\\main\\resources\\heart-beat-icon.png");
//                    Image image = new Image(file.toURI().toString());
                    TrayNotification tray = new TrayNotification();
                    tray.setNotificationType(NotificationType.CUSTOM);
                    tray.setTitle("Login Success");
                    tray.setMessage("Hello doctor! Welcome to the heartbeat monitor station"); //don't forget to get the name of doctor
//                    tray.setImage(image);
                    tray.setAnimationType(AnimationType.FADE);
                    tray.showAndDismiss(Duration.millis(1500));
                    tray.setRectangleFill(Color.valueOf("#ff7b68"));
                    Stage stage2 = (Stage) ((Node)event.getSource()).getScene().getWindow();
                    stage2.hide();
                    //to hide the login page

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/DoctorPage.fxml"));
                    Parent root = (Parent) loader.load();

                    ControllerDoctor secController = loader.getController();
                    secController.myFunction("hello");

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
        }
    }

//    public void handleRegisterButton() {
//        myController.setScreen(Main.screen2ID);
//    }

    public void initialize(URL location, ResourceBundle resources) {
        fadeIn.setNode(wrongPasswordLabel);
        wrongPasswordLabel.setVisible(false);
        fadeIn.setAutoReverse(false);
        fadeIn.setCycleCount(1);

        fadeInBlankUsernamePassword.setNode(blankUsernamePasswordLabel);
        blankUsernamePasswordLabel.setVisible(false);
        fadeInBlankUsernamePassword.setAutoReverse(false);
        fadeInBlankUsernamePassword.setCycleCount(1);

        loginIndicator.setVisible(false);
        loginLabel.setVisible(false);

//        textFlowRegister = new TextFlow(
//                new Text("Don't have an account? "), new Hyperlink("Click here")
//        );
    }

    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

    private void setWrongPasswordLabel(String message){
        wrongPasswordLabel.setText(message);
        wrongPasswordLabel.setVisible(true);
        fadeIn.setFromValue(1.0);
        fadeIn.setToValue(0.0);
        fadeIn.play();
    }

    public void handleHyperLinkRegister(ActionEvent event){
        try{
            Stage stage2 = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage2.hide();
            //to hide the login page

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/registerPage.fxml"));
            Parent root = (Parent) loader.load();

            RegisterPageController secController = loader.getController();
//        secController.myFunction("hello");

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
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
