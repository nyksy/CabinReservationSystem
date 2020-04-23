import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextField;


public class FunctionsController {

    //TODO Get ROLE in the company
    String role = LoginController.role;

    //FXML
    //Tabs
    @FXML
    private AnchorPane apOfficeControl;
    @FXML
    private AnchorPane apServiceControl;
    @FXML
    private AnchorPane apReservationControl;
    @FXML
    private AnchorPane apCustomerControl;
    @FXML
    private AnchorPane apBillControl;
    @FXML
    private AnchorPane apMonitorOffices;
    @FXML
    private AnchorPane apMonitorServices;
    @FXML
    private AnchorPane apMonitorReservations;
    @FXML
    private AnchorPane apMonitorCustomers;
    @FXML
    private AnchorPane apCharts;

    @FXML
    public void controlOffices() {
        apOfficeControl.toFront();
    }

    @FXML
    public void controlServices() {
        apServiceControl.toFront();
    }

    @FXML
    public void controlReservations() {
        apReservationControl.toFront();
    }

    @FXML
    public void controlCustomers() {
        apCustomerControl.toFront();
    }

    @FXML
    public void controlBills() {
        apBillControl.toFront();
    }

    @FXML
    public void changeTabOffices() {
        apMonitorOffices.toFront();
        //TODO get the data
    }

    @FXML
    public void changeTabServices() {
        apMonitorServices.toFront();
        //TODO get the data
    }

    @FXML
    public void changeTabReservations() {
        apMonitorReservations.toFront();
        //TODO get the data
    }

    @FXML
    public void changeTabCustomers() {
        apMonitorCustomers.toFront();
        //TODO get the data
    }

    @FXML
    public void changeTabCharts() {
        apCharts.toFront();
        //TODO get the data
    }

    @FXML
    private void initialize() {
        apOfficeControl.toFront();
    }

    //TextFields
    @FXML
    private TextField tfOfficeID;
    @FXML
    private TextField tfOfficeName;
    @FXML
    private TextField tfOfficeStreet;
    @FXML
    private TextField tfOfficePostal;
    @FXML
    private TextField tfOfficeCity;
}
