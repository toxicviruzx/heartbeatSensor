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
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by TuanDung on 12/1/2017.
 */
public class RegisterPageController implements ControlledScreen, Initializable{
    ScreensController myController;
    private boolean isRestering = false;
    private FadeTransition fadeIn = new FadeTransition(
            Duration.millis(1900)
    );
    private FadeTransition fadeInUserAccount = new FadeTransition(
            Duration.millis(3000)
    );
    private FadeTransition fadeInBlankPassword = new FadeTransition(
            Duration.millis(1900)
    );
    private FadeTransition fadeInBlankUsername = new FadeTransition(
            Duration.millis(1900)
    );
    private FadeTransition fadeInSuccessfulRegister = new FadeTransition(
            Duration.millis(9000)
    );
    private FadeTransition fadeInIDInformation = new FadeTransition(
            Duration.millis(9000)
    );

    @FXML
    public Button loginPageButton;

    @FXML
    public Button registerButton;

    @FXML
    public Button technicalSupportButton;

    @FXML
    public TextField userName;

    @FXML
    public PasswordField passwordField;

    @FXML
    public PasswordField confirmPasswordField;

    @FXML
    public Label confirmPasswordError;

    @FXML
    public Label creatingLabel;

    @FXML
    public ProgressIndicator creatingIndicator;

    @FXML
    public Label usernameError;

    @FXML
    public Label blankPasswordLabel;

    @FXML
    public Label blankUsernameLabel;

    @FXML
    public Label successfulRegisterLabel;

    @FXML
    public DatePicker dateOfBirth;

    @FXML
    public Label IDinformation;

    @FXML
    public CheckBox MaleCheckbox;

    @FXML
    public CheckBox FemaleCheckbox;

    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

    public void initialize(URL location, ResourceBundle resources) {
        fadeInUserAccount.setNode(usernameError);
        usernameError.setVisible(false);
        fadeInUserAccount.setAutoReverse(false);
        fadeInUserAccount.setCycleCount(1);

        fadeIn.setNode(confirmPasswordError);
        confirmPasswordError.setVisible(false);
        fadeIn.setAutoReverse(false);
        fadeIn.setCycleCount(1);

        fadeInBlankPassword.setNode(blankPasswordLabel);
        blankPasswordLabel.setVisible(false);
        fadeInBlankPassword.setAutoReverse(false);
        fadeInBlankPassword.setCycleCount(1);

        fadeInBlankUsername.setNode(blankUsernameLabel);
        blankUsernameLabel.setVisible(false);
        fadeInBlankUsername.setAutoReverse(false);
        fadeInBlankUsername.setCycleCount(1);

        fadeInSuccessfulRegister.setNode(successfulRegisterLabel);
        successfulRegisterLabel.setVisible(false);
        fadeInSuccessfulRegister.setAutoReverse(false);
        fadeInSuccessfulRegister.setCycleCount(1);

        fadeInIDInformation.setNode(IDinformation);
        IDinformation.setVisible(false);
        fadeInIDInformation.setAutoReverse(false);
        fadeInIDInformation.setCycleCount(1);

        creatingIndicator.setVisible(false);
        creatingLabel.setVisible(false);
    }

    @FXML
    public void handleLoginPageButton(ActionEvent event){
       if(!isRestering){
           userName.setText("");
           passwordField.setText("");
           confirmPasswordField.setText("");
           dateOfBirth.setValue(null);
//           myController.setScreen(Main.screen1ID);

           try {
               Stage stage2 = (Stage) ((Node)event.getSource()).getScene().getWindow();
               stage2.hide();
               //to hide the previous page

               FXMLLoader loader = new FXMLLoader(getClass().getResource("/loginPage.fxml"));
               Parent root  = (Parent) loader.load();
               ControllerLoginPage secController = loader.getController();
               Stage stage = new Stage();
               stage.initStyle(StageStyle.UTILITY);
               stage.setScene(new Scene(root, 1160, 710));
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

    public void handleMaleCheckbox(){
        if(MaleCheckbox.isSelected()){
            FemaleCheckbox.setDisable(true);
        } else {
            FemaleCheckbox.setDisable(false);
        }
    }

    public void handleFemaleCheckbox(){
        if(FemaleCheckbox.isSelected()){
            MaleCheckbox.setDisable(true);
        } else {
            MaleCheckbox.setDisable(false);
        }
    }

    public void handleRegisterButton() {
        System.out.println(dateOfBirth.getValue());
        if(!isRestering){
            if(userName.getText().matches("") ){
                blankUsernameLabel.setText("Fill in your user name!");
                blankUsernameLabel.setVisible(true);
                fadeInBlankUsername.setFromValue(1.0);
                fadeInBlankUsername.setToValue(0.0);
                fadeInBlankUsername.play();
            } else if(userName.getText().contains(" ")){
                blankUsernameLabel.setText("Your user name must not contain space!");
                blankUsernameLabel.setVisible(true);
                fadeInBlankUsername.setFromValue(1.0);
                fadeInBlankUsername.setToValue(0.0);
                fadeInBlankUsername.play();
            } else if(userName.getText().length() < 6){
                blankUsernameLabel.setText("Your user name must has at least 6 characters.");
                blankUsernameLabel.setVisible(true);
                fadeInBlankUsername.setFromValue(1.0);
                fadeInBlankUsername.setToValue(0.0);
                fadeInBlankUsername.play();
            } else if(passwordField.getText().matches("")){
                blankPasswordLabel.setText("Fill in your password!");
                blankPasswordLabel.setVisible(true);
                fadeInBlankPassword.setFromValue(1.0);
                fadeInBlankPassword.setToValue(0.0);
                fadeInBlankPassword.play();
            }  else if(passwordField.getText().contains(" ")){
                blankPasswordLabel.setText("Your password must not contain space!");
                blankPasswordLabel.setVisible(true);
                fadeInBlankPassword.setFromValue(1.0);
                fadeInBlankPassword.setToValue(0.0);
                fadeInBlankPassword.play();
            } else if(passwordField.getText().length() < 6){
                blankPasswordLabel.setText("Your password must has at least 6 characters.");
                blankPasswordLabel.setVisible(true);
                fadeInBlankPassword.setFromValue(1.0);
                fadeInBlankPassword.setToValue(0.0);
                fadeInBlankPassword.play();
            } else if(!passwordField.getText().matches(confirmPasswordField.getText())){
                confirmPasswordError.setVisible(true);
                fadeIn.setFromValue(1.0);
                fadeIn.setToValue(0.0);
                fadeIn.play();
            } else {
//                DBmanager db = DBmanager.getInstance();
//                AccountJDBC account = AccountJDBC.getInstance(db);
//                String username = userName.getText();
//                if(!account.getUserName(username)){
//                    isRestering = true;
//                    account.createAccount(userName.getText().toLowerCase(), passwordField.getText());
//                    Thread creating = new Thread(() -> {
//                        creatingIndicator.setVisible(true);
//                        creatingLabel.setVisible(true);
//                        try {
//                            Thread.sleep(7000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        creatingIndicator.setVisible(false);
//                        creatingLabel.setVisible(false);
//                        successfulRegisterLabel.setVisible(true);
//                        fadeInSuccessfulRegister.setFromValue(1.0);
//                        fadeInSuccessfulRegister.setToValue(0.0);
//                        fadeInSuccessfulRegister.play();
//                        isRestering = false;
//                    });
//                    creating.start();
//                } else {
//                    usernameError.setVisible(true);
//                    fadeInUserAccount.setFromValue(3.0);
//                    fadeInUserAccount.setToValue(0.0);
//                    fadeInUserAccount.play();
//                }
            }
        }
    }

    public void handleTechnicalSupportButton(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Technical Support");
        alert.setHeaderText("Our policy:");
        alert.setContentText("By default, all new registered accounts will be set as patient account. If you want to upgrade your account into doctor account, you have to contact us via our customer service at: 433296@student.saxion.nl\nThank you for your co-operation.");
        alert.show();
    }

}
