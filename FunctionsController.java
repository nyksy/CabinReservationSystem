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

import java.io.IOException;
import java.util.Arrays;

/**
 * Controller of the Functions.fxml
 *
 * @author Juho Nykänen
 * @author Taneli Gröhn
 * @version 0.1
 */

public class FunctionsController {

    String role = LoginController.role;

    //SQL haku-Stringit
    private final String sqlAsiakas = "SELECT Asiakas_ID FROM Asiakas";
    private final String sqlToimipiste = "SELECT Toimipiste_ID FROM Toimipiste";
    private final String sqlHuone = "SELECT Huone_ID FROM Huone";
    private final String sqlVaraus = "SELECT Varaus_ID FROM Varaus";

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
    private CheckBox checkPaid;
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

    //Hakukentät
    @FXML
    private TextField searchOffices;

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

        //Buildataan data Choiceboxeihin
        buildData(cbR_customerID, sqlAsiakas, cbCustomerList);
        buildData(cbA_officeID, sqlToimipiste, cbOfficeList);
        cbS_OfficeID.setItems(cbOfficeList);
        buildData(cbR_roomID, sqlHuone, cbRoomList);
        buildData(cbB_reservationID, sqlVaraus, cbReservationList);

        changeTabReports();
    }

    /**
     * Metodi, jolla lisätään toimipiste tietokantaan
     */
    @FXML
    private void insertOffice() {
        //Hae kaikkien textfieldien arvot
        String name = tfOfficeName.getText();
        String address = tfOfficeStreet.getText();
        String pcode = tfOfficePostal.getText();
        String pcity = tfOfficeCity.getText();
        try {
            //Luo values merkkijono ja kutsu http.setValues metodia
            String values = String.format("\"%s\", \"%s\", \"%s\", \"%s\"",
                    name, address, pcode, pcity);
            System.out.println(values);
            httpController http = new httpController();
            http.setValues("Toimipiste", values);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodi, jolla lisätään asiakas tietokantaan
     */
    @FXML
    private void insertCustomer() {
        String firstName = tfFirstName.getText();
        String lastName = tfLastName.getText();
        String pnum = tfPhone.getText();
        String email = tfEmail.getText();
        String address = tfAddress.getText();
        String pcode = tfPostal.getText();
        String city = tfCity.getText();
        try {
            String values = String.format("\"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\"",
                    firstName, lastName, pnum, email, address, pcode, city);
            System.out.println(values);
            httpController http = new httpController();
            http.setValues("Asiakas", values);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodi, jolla lisätään Huone/majoitus tietokantaan
     */
    @FXML
    private void insertAccommodation() {
        String price = tfRoomDayPrice.getText();
        String rnum = tfRoomNumber.getText();
        String officeID = cbA_officeID.getValue();
        try {
            String values = String.format("\"%s\", \"%s\", \"%s\"",
                    price, rnum, officeID);
            System.out.println(values);
            httpController http = new httpController();
            http.setValues("Huone", values);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodi, jolla lisätään varaus tietokantaan
     */
    @FXML
    private void insertReservation() {
        String arriving = dpArriving.getValue().toString();
        String leaving = dpLeaving.getValue().toString();
        String customerID = cbR_customerID.getValue();
        String roomID = cbR_roomID.getValue();
        try {
            String values = String.format("\"%s\", \"%s\", \"%s\", \"%s\"",
                    arriving, leaving, customerID, roomID);
            System.out.println(values);
            httpController http = new httpController();
            http.setValues("Varaus", values);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodi, jolla lisätään palvelu tietokantaan
     */
    @FXML
    private void insertService() {
        String name = tfServiceName.getText();
        String price = tfServicePrice.getText();
        String officeID = cbS_OfficeID.getValue();
        try {
            String values = String.format("\"%s\", \"%s\", \"%s\"",
                    name, price, officeID);
            System.out.println(values);
            httpController http = new httpController();
            http.setValues("Palvelu", values);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodi, jolla lisätään lasku tietokantaan
     */
    @FXML
    private void insertBill() {
        String reservationID = cbB_reservationID.getValue();
        String sum = tfSumTotal.getText();
        String due = dpDueDate.getValue().toString();
        String sentDate = dpSent.getValue().toString();
        String paid;
        if (checkPaid.isSelected()) {
            paid = "TRUE";
        } else {
            paid = "FALSE";
        }

        try {
            String values = String.format("\"%s\", \"%s\", \"%s\", \"%s\", \"%s\"",
                    reservationID, sum, due, sentDate, paid);
            System.out.println(values);
            httpController http = new httpController();
            http.setValues("Lasku", values);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void updateOffice() {
        String officeID = tfOfficeID.getText();
        String name = tfOfficeName.getText();
        String address = tfOfficeStreet.getText();
        String pcode = tfOfficePostal.getText();
        String pcity = tfOfficeCity.getText();

        try {
            //Luo values merkkijono ja kutsu http.setValues metodia
            String values = String.format("Nimi=\'%s\', Katuosoite=\'%s\', Postinumero=\'%s\', Postitoimipaikka=\'%s\'",
                    name, address, pcode, pcity);
            System.out.println(values);
            httpController http = new httpController();
            http.updateValues("Toimipiste", values, officeID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void updateCustomer() {
        String customerID = tfCustomerID.getText();
        String firstName = tfFirstName.getText();
        String lastName = tfLastName.getText();
        String pnum = tfPhone.getText();
        String email = tfEmail.getText();
        String address = tfAddress.getText();
        String pcode = tfPostal.getText();
        String city = tfCity.getText();
        try {
            String values = String.format("Etunimi=\"%s\", Sukunimi=\"%s\", Puhelinnumero=\"%s\", " +
                            "Sahkoposti=\"%s\", Katuosoite=\"%s\", Postinumero=\"%s\", Postitoimipaikka=\"%s\"",
                    firstName, lastName, pnum, email, address, pcode, city);
            System.out.println(values);
            httpController http = new httpController();
            http.updateValues("Asiakas", values, customerID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void updateAccommodation() {
        String roomID = tfRoomID.getText();
        String price = tfRoomDayPrice.getText();
        String rnum = tfRoomNumber.getText();
        String officeID = cbA_officeID.getValue();
        try {
            String values = String.format("Paivahinta=\"%s\", Huonenumero=\"%s\", Toimipiste_ID=\"%s\"",
                    price, rnum, officeID);
            System.out.println(values);
            httpController http = new httpController();
            http.updateValues("Huone", values, roomID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML private void updateReservation() {
        String reservationID = tfReservationID.getText();
        String arriving = dpArriving.getValue().toString();
        String leaving = dpLeaving.getValue().toString();
        String customerID = cbR_customerID.getValue();
        String roomID = cbR_roomID.getValue();
        try {
            String values = String.format("Alkupvm=\"%s\", Loppupvm=\"%s\", Asiakas_ID=\"%s\", Huone_ID=\"%s\"",
                    arriving, leaving, customerID, roomID);
            System.out.println(values);
            httpController http = new httpController();
            http.updateValues("Varaus", values, reservationID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void updateService() {
        String serviceID = tfServiceID.getText();
        String name = tfServiceName.getText();
        String price = tfServicePrice.getText();
        String officeID = cbS_OfficeID.getValue();
        try {
            String values = String.format("Nimi=\"%s\", Hinta=\"%s\", Toimipiste_ID=\"%s\"",
                    name, price, officeID);
            System.out.println(values);
            httpController http = new httpController();
            http.updateValues("Palvelu", values, serviceID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void updateBill() {
        String billID = tfBillID.getText();
        String reservationID = cbB_reservationID.getValue();
        String sum = tfSumTotal.getText();
        String due = dpDueDate.getValue().toString();
        String sentDate = dpSent.getValue().toString();
        String paid;
        if (checkPaid.isSelected()) {
            paid = "TRUE";
        } else {
            paid = "FALSE";
        }

        try {
            String values = String.format("Varaus_ID=\"%s\", Loppusumma=\"%s\", Erapaiva=\"%s\", Lahetyspvm=\"%s\", " +
                            "Maksu_suoritettu=\"%s\"",
                    reservationID, sum, due, sentDate, paid);
            System.out.println(values);
            httpController http = new httpController();
            http.updateValues("Lasku", values, billID);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
     * Method which changes the scene to the same window
     *
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

    @FXML
    public void searchOffice() {
        search(tbwOffice, "Toimipiste");
    }

    public void search(TableView tbw, String table) {
        String[][] data = null;
        String[] headers = null;
        httpController hc = new httpController();

        String sql = "SELECT * FROM Toimipiste WHERE Toimipiste_ID = " + searchOffices.getText();
        try {
            data = hc.runSQL(sql);
            headers = hc.getHeaders(table);
        } catch (IOException io) {
            System.out.println("Error");
        }
        printMatrix(tbw, data, headers);
    }
}
