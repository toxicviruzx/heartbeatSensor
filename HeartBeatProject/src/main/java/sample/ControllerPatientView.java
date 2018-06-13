package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerPatientView implements Initializable {
    private String id;
    private String firstNameText;
    private String lastNameText;

    @FXML
    Button logOutButton;

    @FXML
    Button viewRealTimeButton;

    @FXML
    Label firstName;

    @FXML
    Label lastName;

    @FXML
    Label gender;

    @FXML
    Label residentId;

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
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void handleViewRealTimeButton(){

    }

    public void handleLogoutButton(ActionEvent event){
        try {
//                File file = new File("D:\\Studying\\Java\\HeartBeatProject_DocterSide\\src\\main\\resources\\heart-beat-icon.png");
//                Image image = new Image(file.toURI().toString());
            TrayNotification tray = new TrayNotification();
            tray.setNotificationType(NotificationType.CUSTOM);
            tray.setTitle("Logout Success");
            tray.setMessage("Goodbye "+ firstNameText + " " + lastNameText +"! See you again!");
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
