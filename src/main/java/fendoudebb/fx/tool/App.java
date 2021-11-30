package fendoudebb.fx.tool;

import fendoudebb.fx.tool.util.Resource;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
public class App extends Application implements Initializable {

    private double screenWidth;
    private double screenHeight;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void init() throws Exception {
        Screen screen = Screen.getPrimary();
        Rectangle2D screenBounds = screen.getBounds();
        screenWidth = screenBounds.getWidth();
        screenHeight = screenBounds.getHeight();
        log.info("screen width#{}, height#{}", screenWidth, screenHeight);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL fxmlURL = getClass().getResource("/fxml/app.fxml");
        URL cssURL = getClass().getResource("/css/style.css");
        URL iconURL = getClass().getResource("/assets/icon.png");
        fxmlLoader.setLocation(fxmlURL);
        fxmlLoader.setResources(Resource.i18n());
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        assert cssURL != null;
        scene.getStylesheets().add(cssURL.toExternalForm());
        assert iconURL != null;
        primaryStage.getIcons().add(new Image(iconURL.toExternalForm()));
        primaryStage.setScene(scene);
        primaryStage.setTitle(Resource.config().getString("app_name"));
        primaryStage.setWidth(screenWidth / 1.2);
        primaryStage.setHeight(screenHeight / 1.2);
        primaryStage.setMinWidth(300);
        primaryStage.setMinHeight(200);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
