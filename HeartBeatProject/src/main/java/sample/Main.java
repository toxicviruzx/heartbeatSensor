package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    public static String screen1ID = "loginPage";
    public static String screen1File = "/loginPage.fxml";
    public static String screen2ID = "registerPage";
    public static String screen2File = "/registerPage.fxml";

    @Override
    public void start(Stage primaryStage) throws Exception{
        ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(Main.screen1ID, Main.screen1File);
        mainContainer.loadScreen(Main.screen2ID, Main.screen2File);

        mainContainer.setScreen(Main.screen1ID);

//        Parent root = FXMLLoader.load(getClass().getResource("/loginPage.fxml"));
        primaryStage.initStyle(StageStyle.UTILITY);

        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        primaryStage.setTitle("Heart Beat");
        primaryStage.setScene(new Scene(root, 1160, 710));
//        root.setStyle("-fx-background-color: white");
        primaryStage.show();
        primaryStage.setMinHeight(710);
        primaryStage.setMinWidth(1160);
        primaryStage.setMaxHeight(710);
        primaryStage.setMaxWidth(1160);

    }


    public static void main(String[] args) {
        launch(args);
    }
}
