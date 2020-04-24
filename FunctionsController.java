import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
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
        httpController http = new httpController();
        String[][] values = null;
        String[] headers = null;

        try {
            //TODO get the data
            //values = http.getValues("Asiakas");
            //headers = http.getHeaders("Asiakas");

        } catch (Exception e) {
            e.printStackTrace();
        }
        printMatrix(tbwCustomer, values, headers);

        apMonitorReservations.toFront();
    }

    @FXML
    public void changeTabCustomers() {

        httpController http = new httpController();
        String[][] values = null;
        String[] headers = null;

        try {
            values = http.getValues("Asiakas");
            headers = http.getHeaders("Asiakas");

        } catch (Exception e) {
            e.printStackTrace();
        }
        printMatrix(tbwCustomer, values, headers);
        apMonitorCustomers.toFront();
    }
    @FXML
    public void changeTabAccommodations() {
        apMonitorAccommodations.toFront();
        //TODO get the data
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
        //TODO get data
        cbOfficeList.addAll();
        cbCustomerList.addAll();
        cbRoomList.addAll();

        apOfficeControl.toFront();
    }



    /**
    @FXML
    private void addDataToTBW(String[][] array, TableView tw) {
        ObservableList<String[]> data = FXCollections.observableArrayList();
        data.addAll(Arrays.asList(array));

        for (int i = 0; i < array[0].length; i++) {
            TableColumn tc = new TableColumn(array[0][i]);
            final int colNo = i;
            tc.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> p) {
                    return new SimpleStringProperty((p.getValue()[colNo]));
                }
            });
            tc.setPrefWidth(150);
            tw.getColumns().add(tc);

        }
        tw.setItems(data);
    }
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
            target.getColumns().add(column);
        }

        for (int i = 0 ; i < numRows ; i++) {
            target.getItems().add(source[i]);
        }
    }
}
