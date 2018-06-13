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

    DBmanager db;
    AccountJDBC account;

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
            Duration.millis(1900)
    );
    private FadeTransition fadeInIDInformation = new FadeTransition(
            Duration.millis(1900)
    );
    private FadeTransition fadeInFirstName = new FadeTransition(
            Duration.millis(1900)
    );
    private FadeTransition fadeInLastName = new FadeTransition(
            Duration.millis(1900)
    );
    private FadeTransition fadeInGender = new FadeTransition(
            Duration.millis(1900)
    );
    private FadeTransition fadeInDateOfBirth = new FadeTransition(
            Duration.millis(1900)
    );

    private boolean isLogin = false;

    @FXML
    public Button loginPageButton;

    @FXML
    public Button registerButton;

    @FXML
    public Button technicalSupportButton;

    @FXML
    public TextField userName;

    @FXML
    public TextField idNumber;

    @FXML
    public TextField firstName;

    @FXML
    public TextField lastName;

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
    public Label IDinformationLabel;

    @FXML
    public Label firstNameRemind;

    @FXML
    public Label lastNameRemind;

    @FXML
    public Label genderRemind;

    @FXML
    public Label dateOfBirthRemind;

    @FXML
    public CheckBox MaleCheckbox;

    @FXML
    public CheckBox FemaleCheckbox;


    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Hello this is the register scene");
        db = DBmanager.getInstance();
        account = AccountJDBC.getInstance(db);

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

        fadeInIDInformation.setNode(IDinformationLabel);
        IDinformationLabel.setVisible(false);
        fadeInIDInformation.setAutoReverse(false);
        fadeInIDInformation.setCycleCount(1);

        fadeInFirstName.setNode(firstNameRemind);
        firstNameRemind.setVisible(false);
        fadeInFirstName.setAutoReverse(false);
        fadeInFirstName.setCycleCount(1);

        fadeInLastName.setNode(lastNameRemind);
        lastNameRemind.setVisible(false);
        fadeInLastName.setAutoReverse(false);
        fadeInLastName.setCycleCount(1);

        fadeInGender.setNode(genderRemind);
        genderRemind.setVisible(false);
        fadeInGender.setAutoReverse(false);
        fadeInGender.setCycleCount(1);

        fadeInDateOfBirth.setNode(dateOfBirthRemind);
        dateOfBirthRemind.setVisible(false);
        fadeInDateOfBirth.setAutoReverse(false);
        fadeInDateOfBirth.setCycleCount(1);

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
            } else if(idNumber.getText().matches("")){
                IDinformationLabel.setText("Fill in your resident ID");
                IDinformationLabel.setVisible(true);
                fadeInIDInformation.setFromValue(3.0);
                fadeInIDInformation.setToValue(0.0);
                fadeInIDInformation.play();
            } else if(firstName.getText().matches("")){
                firstNameRemind.setVisible(true);
                fadeInFirstName.setFromValue(3.0);
                fadeInFirstName.setToValue(0.0);
                fadeInFirstName.play();
            } else if(lastName.getText().matches("")){
                lastNameRemind.setVisible(true);
                fadeInLastName.setFromValue(3.0);
                fadeInLastName.setToValue(0.0);
                fadeInLastName.play();
            } else if(!MaleCheckbox.isSelected()&&!FemaleCheckbox.isSelected()){
                genderRemind.setVisible(true);
                fadeInGender.setFromValue(3.0);
                fadeInGender.setToValue(0.0);
                fadeInGender.play();
            } else if(dateOfBirth.getValue() == null){
                dateOfBirthRemind.setVisible(true);
                fadeInDateOfBirth.setFromValue(3.0);
                fadeInDateOfBirth.setToValue(0.0);
                fadeInDateOfBirth.play();
            } else if(idNumber.getText().length() > 12){
                IDinformationLabel.setText("This ID is too long");
                IDinformationLabel.setVisible(true);
                fadeInIDInformation.setFromValue(3.0);
                fadeInIDInformation.setToValue(0.0);
                fadeInIDInformation.play();
            } else {
                DBmanager db = DBmanager.getInstance();
                AccountJDBC account = AccountJDBC.getInstance(db);
                String username = userName.getText();
                if(!account.getUserName(username)){
                    isRestering = true;
                    String gender;
                    if(MaleCheckbox.isSelected()&&(!FemaleCheckbox.isSelected())){
                        gender = "Male";
                    } else {
                        gender = "Female";
                    }
                    if(!account.getIdNumber(idNumber.getText())){
                        account.createAccount(idNumber.getText().toLowerCase(), userName.getText().toLowerCase(), passwordField.getText(), firstName.getText(), lastName.getText(), gender, dateOfBirth.getValue().toString());
                        Thread creating = new Thread(() -> {
                            registerButton.setDisable(true);
                            loginPageButton.setDisable(true);
                            creatingIndicator.setVisible(true);
                            creatingLabel.setVisible(true);
                            try {
                                Thread.sleep(7000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            creatingIndicator.setVisible(false);
                            creatingLabel.setVisible(false);
                            successfulRegisterLabel.setVisible(true);
                            fadeInSuccessfulRegister.setFromValue(1.0);
                            fadeInSuccessfulRegister.setToValue(0.0);
                            fadeInSuccessfulRegister.play();
                            isRestering = false;
                            registerButton.setDisable(false);
                            loginPageButton.setDisable(false);
                        });
                        creating.start();
                    } else {
                        IDinformationLabel.setText("This id has been used by other user");
                        IDinformationLabel.setVisible(true);
                        fadeInIDInformation.setFromValue(3.0);
                        fadeInIDInformation.setToValue(0.0);
                        fadeInIDInformation.play();
                    }

                } else {
                    usernameError.setVisible(true);
                    fadeInUserAccount.setFromValue(3.0);
                    fadeInUserAccount.setToValue(0.0);
                    fadeInUserAccount.play();
                }
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
