import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class LoginController {

    //Role, which assigns available tasks
    public static String role;
    //ChoiceBox values
    ObservableList<String> cbList = FXCollections.observableArrayList("Customer Service", "Administration");

    //FXML
    //ChoiceBox
    @FXML
    private ChoiceBox<String> cbRole;

    @FXML
    private void initialize() {
        cbRole.setItems(cbList);
        cbRole.setValue(cbList.get(0));
    }

    @FXML
    public void ChangeStage(ActionEvent event) {
        role = cbRole.getValue();
        try {
            Parent loader = FXMLLoader.load(getClass().getResource("Functions.fxml"));
            Scene scene = new Scene(loader);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.setTitle("MÖKK1 software");
            window.centerOnScreen();
            window.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
