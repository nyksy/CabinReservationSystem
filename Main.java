import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Juho Nykänen
 * @author Taneli Gröhn
 * @author Armas Ahlholm
 * @author Heikki Vainio
 * @author Ville Kärkkänen
 * @version 0.1
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Load the primary stage (login screen)
     *
     * @param primaryStage stage
     * @throws Exception e
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}