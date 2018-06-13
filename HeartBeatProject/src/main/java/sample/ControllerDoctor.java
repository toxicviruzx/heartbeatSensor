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
import javafx.scene.control.cell.PropertyValueFactory;
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
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ControllerDoctor implements Initializable, ControlledScreen{
    ScreensController myController;
    private FadeTransition fadeIn = new FadeTransition(
            Duration.millis(1900)
    );

    private String id;

//    DBmanager db;
//    AccountJDBC account;

    private ArrayList<String> idList;
    private ArrayList<String> firstNameList;
    private ArrayList<String> lastNameList;
    private ArrayList<String> genderList;
    private ArrayList<String> dateOfBirthList;
    private ArrayList<String> noteList;

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

    @FXML
    public TableView<TableData> table;

    @FXML
    public TableColumn<TableData, String> idColumn;

    @FXML
    public TableColumn<TableData, String> firstNameColumn;

    @FXML
    public TableColumn<TableData, String> lastNameColumn;

    @FXML
    public TableColumn<TableData, String> genderColumn;

    @FXML
    public TableColumn<TableData, String> dateOfBirthColumn;

    @FXML
    public TableColumn<TableData, String> noteColumn;

    private ObservableList<TableData> tableDataCells = FXCollections.observableArrayList();

    public void getId(String id){
        this.id = id;
    }

    public void initialize(URL location, ResourceBundle resources) {
        DBmanager db = DBmanager.getInstance();
        AccountJDBC account = AccountJDBC.getInstance(db);
        this.idList = account.getPatientIdList();
        this.firstNameList = account.getPatientFirstNameList();
        this.lastNameList = account.getPatientLastNameList();
        this.genderList = account.getPatientGenderList();
        this.dateOfBirthList = account.getPatientDateOfBirthList();
        this.noteList = account.getPatientNoteList();

        createTable();

        System.out.println("Hello this is the doctor scene");

        fadeIn.setNode(searchErrorLabel);
        searchErrorLabel.setVisible(false);
        fadeIn.setAutoReverse(false);
        fadeIn.setCycleCount(1);

        viewDetailsData.setDisable(true);
    }

    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

    private String searchBy(String keyword){
        if(keyword.matches(".*\\d+.*") && !(keyword.contains("/") || keyword.contains("-"))){ //make sure that the entered string contains number
            return "id";
        } else if((keyword.contains("/") || keyword.contains("-")) && keyword.matches(".*\\d+.*")) {
            return "birthday";
        } else if(!(keyword.matches(".*\\d+.*") && !(keyword.contains("/") || keyword.contains("-")))){
            return "name";
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
        DBmanager db = DBmanager.getInstance();
        AccountJDBC account = AccountJDBC.getInstance(db);
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
                tray.setMessage("Goodbye doctor "+ account.getLastName(id) +"! See you again!");
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
        updateTable(searchBy(searchForPatient.getText()));
    }

    public void handleViewDetailsData(ActionEvent event){
        System.out.println(table.getSelectionModel().getSelectedItem().getId());
        try {
            Stage stage2 = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage2.hide();
            //to hide the login page

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/realTimeHeartBeat.fxml"));
            Parent root = (Parent) loader.load();

            realTimeViewingController secController = loader.getController();
            secController.getID(table.getSelectionModel().getSelectedItem().getId());

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

    public void userClickedOnTable() {
        this.viewDetailsData.setDisable(false);
    }

    public void createTable(){
        for(int index = 0; index < idList.size(); index++){
//            System.out.println(idList.get(index));
            tableDataCells.add(new TableData(idList.get(index), firstNameList.get(index), lastNameList.get(index), genderList.get(index), dateOfBirthList.get(index), noteList.get(index)));
        }
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.setStyle("-fx-alignment: CENTER;");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        firstNameColumn.setStyle("-fx-alignment: CENTER;");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        lastNameColumn.setStyle("-fx-alignment: CENTER;");
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        genderColumn.setStyle("-fx-alignment: CENTER;");
        dateOfBirthColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        dateOfBirthColumn.setStyle("-fx-alignment: CENTER;");
        noteColumn.setCellValueFactory(new PropertyValueFactory<>("note"));
        noteColumn.setStyle("-fx-alignment: CENTER;");
        table.setItems(tableDataCells);
    }

    public void updateTable(String searchMethod){
        table.getItems().clear();
        if(searchMethod.matches("id")){
            for(int index = 0; index < idList.size(); index++){
                if(idList.get(index).contains(searchForPatient.getText())){
                    tableDataCells.add(new TableData(idList.get(index), firstNameList.get(index), lastNameList.get(index), genderList.get(index), dateOfBirthList.get(index), noteList.get(index)));
                }
            }
        } else if(searchMethod.matches("birthday")){
            for(int index = 0; index < dateOfBirthList.size(); index++){
                if(dateOfBirthList.get(index).contains(searchForPatient.getText())){
                    tableDataCells.add(new TableData(idList.get(index), firstNameList.get(index), lastNameList.get(index), genderList.get(index), dateOfBirthList.get(index), noteList.get(index)));
                }
            }
        } else if(searchMethod.matches("name")){
            for(int index = 0; index < firstNameList.size(); index++){
                if(firstNameList.get(index).contains(searchForPatient.getText()) || lastNameList.get(index).contains(searchForPatient.getText())){
                    tableDataCells.add(new TableData(idList.get(index), firstNameList.get(index), lastNameList.get(index), genderList.get(index), dateOfBirthList.get(index), noteList.get(index)));
                }
            }
        }
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.setStyle("-fx-alignment: CENTER;");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        firstNameColumn.setStyle("-fx-alignment: CENTER;");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        lastNameColumn.setStyle("-fx-alignment: CENTER;");
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        genderColumn.setStyle("-fx-alignment: CENTER;");
        dateOfBirthColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        dateOfBirthColumn.setStyle("-fx-alignment: CENTER;");
        noteColumn.setCellValueFactory(new PropertyValueFactory<>("note"));
        noteColumn.setStyle("-fx-alignment: CENTER;");
        table.setItems(tableDataCells);
    }
}