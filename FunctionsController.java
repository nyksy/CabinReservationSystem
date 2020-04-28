import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller of the Functions.fxml
 *
 * @author Juho Nykänen
 * @author Taneli Gröhn
 * @version 0.1
 */

public class FunctionsController {

    //SQL haku-Stringit
    private final String sqlAsiakas = "SELECT Asiakas_ID FROM Asiakas";
    private final String sqlToimipiste = "SELECT Toimipiste_ID FROM Toimipiste";
    private final String sqlPalvelu = "SELECT Palvelu_ID FROM Palvelu";
    private final String sqlHuone = "SELECT Huone_ID FROM Huone";
    private final String sqlVaraus = "SELECT Varaus_id FROM Varaus";
    private final String sqlLasku = "SELECT Lasku_ID FROM Lasku";
    String role = LoginController.role;
    //Listoja joilla täytetään ChoiceBoxit
    //Lista kaikista toimipisteistä
    ObservableList<String> cbOfficeList = FXCollections.observableArrayList();
    //Lista kaikista asiakkaista
    ObservableList<String> cbCustomerList = FXCollections.observableArrayList();
    //Lista kaikista huoneista
    ObservableList<String> cbRoomList = FXCollections.observableArrayList();
    //Lista kaikista varauksista
    ObservableList<String> cbReservationList = FXCollections.observableArrayList();
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
    public void controlAccommodations() {
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
        //get data to choiceBoxes
        buildData(cbR_customerID, sqlAsiakas, cbCustomerList);
        buildData(cbA_officeID, sqlToimipiste, cbOfficeList);
        cbS_OfficeID.setItems(cbOfficeList);
        buildData(cbR_roomID, sqlHuone, cbRoomList);
        buildData(cbB_reservationID, sqlVaraus, cbReservationList);

        changeTabReports();
    }

    /**
     * Metodi monitor osion TableView taulun tietojen asettamiselle
     *
     * @param table Haluttu tietokannan taulu
     * @param tbw   Kohteena oleva TableView fx:id
     */
    private void setMonitorTableview(String table, TableView tbw) {
        httpController http = new httpController();
        String[][] values = null;
        String[] headers = null;

        try {
            values = http.useDB("select", table, "");
            headers = http.getHeaders(table);

        } catch (Exception e) {
            e.printStackTrace();
        }
        printMatrix(tbw, values, headers);
    }

    /**
     * Metodi TableView taulun täyttämiseksi 2d datamatriisilla. Runko löydetty www.StackOverFlow.com
     *
     * @param target  Kohteena oleva TableView olio
     * @param source  2d datamatriisi, joka sisältää halutun datan
     * @param headers Matriisi, joka sisältää kolumnien nimet
     */
    private void printMatrix(TableView<String[]> target, String[][] source, String[] headers) {

        target.getColumns().clear();
        target.getItems().clear();

        int numRows = source.length;
        if (numRows == 0) return;

        int numCols = source[0].length;

        for (int i = 0; i < numCols; i++) {
            TableColumn<String[], String> column = new TableColumn<>(headers[i]);
            final int columnIndex = i;
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

        for (String[] strings : source) {
            target.getItems().add(strings);
        }
    }

    /**
     * Metodi, joka vaihtaa scenen samaan ikkunaan. Functions -> Login
     * @param event ActionEvent
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

    /**
     * Metodi jolla saadaan täytettyä Choiceboxit datalla palvelimelta.
     *
     * @param cb   Choicebox-attribuutti
     * @param sql  String, joka sisältää sql-hakusanat
     * @param list Observablelist, johon kaikki tulokset lisätään
     */
    public void buildData(ChoiceBox<String> cb, String sql, ObservableList<String> list) {
        String[][] data;
        httpController hc = new httpController();
        try {
            data = hc.runSQL(sql);
            for (String[] datum : data) {
                list.add(datum[0]);
            }
            cb.setItems(list);
        } catch (IOException ie) {
            System.out.println("TempleOS is malfunctioning.");
        }
    }
}
