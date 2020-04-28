import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.Arrays;

/**
 * Controller of the Functions.fxml
 *
 * @author Juho Nykänen
 * @author Taneli Gröhn
 *
 * @version 0.1
 */

public class FunctionsController {

    //TODO Get ROLE in the company
    String role = LoginController.role;

    //Buttons
    @FXML
    private Button btnOffice;
    @FXML
    private Button btnChangeUser;

    //OFFICE CONTROL
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

    //SERVICE CONTROL
    @FXML
    private ChoiceBox<String> cbS_OfficeID;
    @FXML
    private TextField tfServiceID;
    @FXML
    private TextField tfServiceName;
    @FXML
    private TextField tfServicePrice;

    //ACCOMMODATION CONTROL
    @FXML
    private ChoiceBox<String> cbA_officeID;
    @FXML
    private TextField tfRoomID;
    @FXML
    private TextField tfRoomDayPrice;
    @FXML
    private TextField tfRoomNumber;

    //RESERVATION CONTROL
    @FXML
    private ChoiceBox<String> cbR_roomID;
    @FXML
    private ChoiceBox<String> cbR_customerID;
    @FXML
    private TextField tfReservationID;
    @FXML
    private DatePicker dpArriving;
    @FXML
    private DatePicker dpLeaving;

    //CUSTOMER CONTROL
    @FXML
    private TextField tfCustomerID;
    @FXML
    private TextField tfFirstName;
    @FXML
    private TextField tfLastName;
    @FXML
    private TextField tfPhone;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfAddress;
    @FXML
    private TextField tfPostal;
    @FXML
    private TextField tfCity;

    //BILL CONTROL
    @FXML
    private ChoiceBox<String> cbB_reservationID;
    @FXML
    private TextField tfBillID;
    @FXML
    private TextField tfSumTotal;
    @FXML
    private TextField tfPaid;
    @FXML
    private DatePicker dpDueDate;
    @FXML
    private DatePicker dpSent;

    //Listoja joilla täytetään ChoiceBoxit
    //Lista kaikista toimipisteistä
    ObservableList<String> cbOfficeList = FXCollections.observableArrayList();

    //Lista kaikista asiakkaista
    ObservableList<String> cbCustomerList = FXCollections.observableArrayList();

    //Lista kaikista huoneista
    ObservableList<String> cbRoomList = FXCollections.observableArrayList();

    //Lista kaikista varauksista
    ObservableList<String> cbReservationList = FXCollections.observableArrayList();

    //TABLEVIEWIT MONITOROINTI-tabeissa
    @FXML
    private TableView tbwCustomer;
    @FXML
    private TableView tbwOffice;
    @FXML
    private TableView tbwService;
    @FXML
    private TableView tbwReservation;
    @FXML
    private TableView tbwRoom;

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
    private AnchorPane apAccommodationControl;
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
    private AnchorPane apMonitorAccommodations;
    @FXML
    private AnchorPane apReports;

    @FXML
    public void controlOffices() {
        apOfficeControl.toFront();
    }

    @FXML
    public void controlAccommodations(){
        cbA_officeID.setItems(cbOfficeList);
        //cbA_officeID.setValue(cbOfficeList.get(0));
        apAccommodationControl.toFront();
    }
    @FXML
    public void controlServices() {
        cbS_OfficeID.setItems(cbOfficeList);
        //cbS_OfficeID.setValue(cbOfficeList.get(0));

        apServiceControl.toFront();
    }

    @FXML
    public void controlReservations() {
        cbR_customerID.setItems(cbCustomerList);
        //cbR_customerID.setValue(cbCustomerList.get(0));

        cbR_roomID.setItems(cbRoomList);
        //cbR_roomID.setValue(cbRoomList.get(0));

        apReservationControl.toFront();
    }

    @FXML
    public void controlCustomers() {
        apCustomerControl.toFront();
    }

    @FXML
    public void controlBills() {
        cbB_reservationID.setItems(cbReservationList);
        //cbB_reservationID.setValue(cbReservationList.get(0));

        apBillControl.toFront();
    }

    @FXML
    public void changeTabOffices() {
        setMonitorTableview("Toimipiste", tbwOffice);
        apMonitorOffices.toFront();
    }

    @FXML
    public void changeTabServices() {
        setMonitorTableview("Palvelu", tbwService);
        apMonitorServices.toFront();
    }

    @FXML
    public void changeTabReservations() {
        setMonitorTableview("Varaus", tbwReservation);
        apMonitorReservations.toFront();
    }

    @FXML
    public void changeTabCustomers() {
        setMonitorTableview("Asiakas", tbwCustomer);
        apMonitorCustomers.toFront();
    }
    @FXML
    public void changeTabAccommodations() {
        setMonitorTableview("Huone", tbwRoom);
        apMonitorAccommodations.toFront();
    }

    @FXML
    public void changeTabReports() {
        apReports.toFront();
        //TODO get the data
    }

    /**
     * Metodi, joka täyttää kaikki choiceboxit ym. scenen käynnistyessä
     */
    @FXML
    private void initialize() {

        //Päättää mitkä ominaisuudet ovat käytössä roolin mukaan
        if (role.equals("Customer Service")) {
            btnOffice.setDisable(true);
            btnOffice.setManaged(false);
        }

        //TODO get data
        cbOfficeList.addAll();
        cbCustomerList.addAll();
        cbRoomList.addAll();

        changeTabReports();
    }

    @FXML
    private void insertOffice() {
        String name = tfOfficeName.getText();
        String address = tfOfficeStreet.getText();
        String pcode = tfOfficePostal.getText();
        String pcity = tfOfficeCity.getText();

        try {
            String values = String.format("\"%s\", \"%s\", \"%s\", \"%s\"",
                    name,
                    address,
                    pcode,
                    pcity);
            System.out.println(values);
            httpController http = new httpController();
            http.setValues("Toimipiste", values);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodi monitor osion TableView taulun tietojen asettamiselle
     *
     * @param table Haluttu tietokannan taulu
     * @param tbw Kohteena oleva TableView fx:id
     */
    private void setMonitorTableview(String table, TableView tbw) {
        httpController http = new httpController();
        String[][] values = null;
        String[] headers = null;

        try {
            values = http.getValues("select", table);
            headers = http.getHeaders(table);

        } catch (Exception e) {
            e.printStackTrace();
        }
        printMatrix(tbw, values, headers);
    }

    /**
     * Metodi TableView taulun täyttämiseksi 2d datamatriisilla. Runko löydetty www.StackOverFlow.com
     *
     * @param target Kohteena oleva TableView olio
     * @param source 2d datamatriisi, joka sisältää halutun datan
     * @param headers Matriisi, joka sisältää kolumnien nimet
     */
    private void printMatrix(TableView<String[]> target, String[][] source, String[] headers) {

        target.getColumns().clear();
        target.getItems().clear();

        int numRows = source.length ;
        if (numRows == 0) return ;

        int numCols = source[0].length ;

        for (int i = 0 ; i < numCols ; i++) {
            TableColumn<String[], String> column = new TableColumn<>(headers[i]);
            final int columnIndex = i ;
            column.setCellValueFactory(cellData -> {
                String[] row = cellData.getValue();
                return new SimpleStringProperty(row[columnIndex]);
            });
            
            //Kolumnien leveys
            column.setPrefWidth(130);

            //Fixed value
            //target.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

            target.getColumns().add(column);
        }

        for (int i = 0 ; i < numRows ; i++) {
            target.getItems().add(source[i]);
        }
    }

    /**
     * Method which changes the scene to the same window
     * @param event e
     */
    @FXML
    public void ChangeStageToLogin(ActionEvent event) {
        try {
            Parent loader = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Scene scene = new Scene(loader);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.setTitle("Login");
            window.centerOnScreen();
            window.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
