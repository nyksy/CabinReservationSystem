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
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.json.JSONException;

import java.io.IOException;
import java.util.Arrays;

/**
 * Functions.fxml controller
 *
 * @author Juho Nykänen
 * @author Taneli Gröhn
 * @version 0.1
 */

public class FunctionsController {

    String role = LoginController.role;
    static httpController http = new httpController();

    //Buttons
    @FXML
    private Button btnOffice;

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
    @FXML
    private ChoiceBox<String> cbSellService;
    @FXML
    private ChoiceBox<String> cbSellReservation;

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

    //Lista kaikista palveluista
    ObservableList<String> cbServiceList = FXCollections.observableArrayList();

    //TABLEVIEWIT MONITOROINTI-tabeissa
    @FXML
    private TableView tbwCustomer;
    @FXML
    private TableView tbwOffice;
    @FXML
    private TableView tbwOffice2;
    @FXML
    private TableView tbwService;
    @FXML
    private TableView tbwReservation;
    @FXML
    private TableView tbwRoom;
    @FXML
    private TableView tbwBill;
    @FXML
    private TableView tbwSoldService;

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
    private AnchorPane apMonitorBills;
    @FXML
    private AnchorPane apReports;
    @FXML
    private AnchorPane apSoldServices;

    //Hakukentät
    @FXML
    private TextField searchOffices;
    @FXML
    private TextField searchServices;
    @FXML
    private TextField searchRooms;
    @FXML
    private TextField searchReservations;
    @FXML
    private TextField searchCustomers;
    @FXML
    private TextField searchBills;

    @FXML
    public void controlOffices() {
        setMonitorTableview("Toimipiste", tbwOffice2);
        apOfficeControl.toFront();
    }

    @FXML
    public void controlAccommodations() {
        apAccommodationControl.toFront();
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
    public void changeTabBills() {
        setMonitorTableview("Lasku", tbwBill);
        apMonitorBills.toFront();
    }

    @FXML
    public void changeTabSoldServices() {
        setMonitorTableview("Palveluvaraus", tbwSoldService);
        apSoldServices.toFront();
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
            //btnOffice.setDisable(true);
            //btnOffice.setManaged(false);
        }

        //Buildataan data Choiceboxeihin
        //SQL haku-Stringit
        String sqlAsiakas = "SELECT Asiakas_ID FROM Asiakas ORDER BY Asiakas_ID";
        String sqlToimipiste = "SELECT Toimipiste_ID FROM Toimipiste ORDER BY Toimipiste_ID";
        String sqlHuone = "SELECT Huone_ID FROM Huone ORDER BY Huone_ID";
        String sqlVaraus = "SELECT Varaus_ID FROM Varaus ORDER BY Varaus_ID";
        String sqlPalvelu = "SELECT Palvelu_ID FROM Palvelu ORDER BY Palvelu_ID";

        buildData(cbR_customerID, sqlAsiakas, cbCustomerList);
        buildData(cbA_officeID, sqlToimipiste, cbOfficeList);
        cbS_OfficeID.setItems(cbOfficeList);
        buildData(cbR_roomID, sqlHuone, cbRoomList);
        buildData(cbB_reservationID, sqlVaraus, cbReservationList);
        cbSellReservation.setItems(cbReservationList);
        buildData(cbSellService, sqlPalvelu, cbServiceList);
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
            showAlert("Insert failed",
                    "You may not insert empty fields.",
                    Alert.AlertType.INFORMATION);
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
            showAlert("Insert failed",
                    "You may not insert empty fields.",
                    Alert.AlertType.INFORMATION);
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
            http.setValues("Huone", values);
        } catch (Exception e) {
            showAlert("Insert failed",
                    "You may not insert empty fields.",
                    Alert.AlertType.INFORMATION);
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
            http.setValues("Varaus", values);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Insert failed",
                    "Check if there are overlapping reservations. Also you may not insert empty fields.",
                    Alert.AlertType.INFORMATION);
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
            http.setValues("Palvelu", values);
        } catch (Exception e) {
            showAlert("Insert failed",
                    "You may not insert empty fields.",
                    Alert.AlertType.INFORMATION);
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
            http.setValues("Lasku", values);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodi, jolla päivitetään olemassaolevan toimiston tietoja
     */
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
            http.updateValues("Toimipiste", values, officeID);
        } catch (Exception e) {
            showAlert("Update failed",
                    "Check Office ID. You may not insert empty fields.",
                    Alert.AlertType.INFORMATION);
        }
    }

    /**
     * Metodi, jolla päivitetään olemassaolevan asiakkaan tietoja
     */
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
            http.updateValues("Asiakas", values, customerID);
        } catch (Exception e) {
            showAlert("Update failed",
                    "Check Customer ID. You may not insert empty fields.",
                    Alert.AlertType.INFORMATION);
        }
    }

    /**
     * Metodi, jolla päivitetään olemassaolevan huoneen tietoja
     */
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
            http.updateValues("Huone", values, roomID);
        } catch (Exception e) {
            showAlert("Update failed",
                    "Check Room ID. You may not insert empty fields.",
                    Alert.AlertType.INFORMATION);
        }
    }

    /**
     * Metodi, jolla päivitetään olemassaolevan varauksen tietoja
     */
    @FXML
    private void updateReservation() {
        String reservationID = tfReservationID.getText();
        String arriving = dpArriving.getValue().toString();
        String leaving = dpLeaving.getValue().toString();
        String customerID = cbR_customerID.getValue();
        String roomID = cbR_roomID.getValue();
        try {
            String values = String.format("Alkupvm=\"%s\", Loppupvm=\"%s\", Asiakas_ID=\"%s\", Huone_ID=\"%s\"",
                    arriving, leaving, customerID, roomID);
            System.out.println(values);
            http.updateValues("Varaus", values, reservationID);
        } catch (Exception e) {
            showAlert("Update failed",
                    "Check Reservation ID. You may not insert empty fields.",
                    Alert.AlertType.INFORMATION);
        }
    }

    /**
     * Metodi, jolla päivitetään olemassaolevan palvelun tietoja
     */
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
            http.updateValues("Palvelu", values, serviceID);
        } catch (Exception e) {
            showAlert("Update failed",
                    "Check Service ID. You may not insert empty fields.",
                    Alert.AlertType.INFORMATION);
        }
    }

    /**
     * Metodi, jolla päivitetään olemassaolevan laskun tietoja
     */
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
            http.updateValues("Lasku", values, billID);
        } catch (Exception e) {
            showAlert("Update failed",
                    "Check Bill ID. You may not insert empty fields.",
                    Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    private void deleteOffice() {
        String officeID = tfOfficeID.getText();
        try {
            http.deleteValues("Toimipiste", officeID, "");
        } catch (Exception e) {
            showAlert("Deletion aborted",
                    "Check Office ID. The office may not have any services or accommodations assigned.",
                    Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    private void deleteCustomer() {
        String customerID = tfCustomerID.getText();
        try {
            http.deleteValues("Asiakas", customerID, "");
        } catch (Exception e) {
            showAlert("Deletion aborted",
                    "Check Customer ID. The Customer may not have any active reservations.",
                    Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    private void deleteAccommodation() {
        String roomID = tfRoomID.getText();
        try {
            http.deleteValues("Huone", roomID, "");
        } catch (Exception e) {
            showAlert("Deletion aborted",
                    "Check Room ID. The Room may not have any active reservations.",
                    Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    private void deleteReservation() {
        String reservationID = tfReservationID.getText();
        try {
            http.deleteValues("Varaus", reservationID, "");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Deletion aborted",
                    "Check Reservation ID. The Reservation may not have any active services.",
                    Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    private void deleteService() {
        String serviceID = tfServiceID.getText();
        try {
            http.deleteValues("Palvelu", serviceID, "");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Deletion aborted",
                    "Check Service ID. The Service may not have any active reservations.",
                    Alert.AlertType.INFORMATION);
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
            column.setPrefWidth(150);
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
            window.getIcons().add(new Image("file:valkoinentaustakuvkae.png"));
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
        String value = searchOffices.getText();
        String sql = String.format("SELECT * FROM Toimipiste WHERE Toimipiste_ID LIKE \"%s\" OR Nimi LIKE \"%s\" " +
                        "OR Katuosoite LIKE \"%s\" OR Postinumero LIKE \"%s\" OR " + "Postitoimipaikka lIKE \"%s\"",
                value, value, value, value, value);
        search(tbwOffice, "Toimipiste", sql, searchOffices);
    }

    @FXML
    public void searchService() {
        String value = searchServices.getText();
        String sql = String.format("SELECT * FROM Palvelu WHERE Palvelu_ID LIKE \"%s\" OR Nimi LIKE \"%s\" " +
                        "OR Hinta LIKE \"%s\" OR Toimipiste_ID LIKE \"%s\"", value, value, value, value);
        search(tbwService, "Palvelu", sql, searchServices);
    }

    @FXML
    public void searchRoom() {
        String value = searchRooms.getText();
        String sql = String.format("SELECT * FROM Huone WHERE Huone_ID LIKE \"%s\" OR Paivahinta LIKE \"%s\" " +
                "OR Huonenumero LIKE \"%s\" OR Toimipiste_ID LIKE \"%s\"", value, value, value, value);
        search(tbwRoom, "Huone", sql, searchRooms);
    }

    @FXML
    public void searchReservation() {
        String value = searchReservations.getText();
        String sql = String.format("SELECT * FROM Varaus WHERE Varaus_ID LIKE \"%s\" OR Alkupvm LIKE \"%s\" " +
                "OR Loppupvm LIKE \"%s\" OR Asiakas_ID LIKE \"%s\" OR Huone_ID LIKE \"%s\"",
                value, value, value, value, value);
        search(tbwReservation, "Varaus", sql, searchReservations);
    }

    @FXML
    public void searchCustomer() {
        String value = searchCustomers.getText();
        String sql = String.format("SELECT * FROM Asiakas WHERE Asiakas_ID LIKE \"%s\" OR Etunimi LIKE \"%s\" " +
                "OR Sukunimi LIKE \"%s\" OR Puhelinnumero LIKE \"%s\" OR Sahkoposti LIKE \"%s\" " +
                "OR Katuosoite LIKE \"%s\" OR Postinumero LIKE \"%s\" OR Postitoimipaikka LIKE \"%s\"",
                value, value, value, value, value, value, value, value);
        search(tbwCustomer, "Asiakas", sql, searchCustomers);
    }

    @FXML
    public void searchBill() {
        String value = searchBills.getText();
        String sql = String.format("SELECT * FROM Lasku WHERE Lasku_ID LIKE \"%s\" OR Varaus_ID LIKE \"%s\" " +
                "OR Loppusumma LIKE \"%s\" OR Erapaiva LIKE \"%s\" OR Lahetyspvm LIKE \"%s\"",
                value, value, value, value, value);
        search(tbwBill, "Lasku", sql, searchBills);
    }

    /**
     * Metodi tietojen hakemiselle ja näyttämiselle TableView-nökymässä
     *
     * @param tbw   Tableview johon tiedot syötetään
     * @param table Taulukon nimi
     * @param haku  Hakusanat (sql)
     * @param tf    TextField, josta haetaan hakusana
     */
    public void search(TableView tbw, String table, String haku, TextField tf) {
        String[][] data = null;
        String[] headers = null;
        httpController hc = new httpController();

        if (!tf.getText().isEmpty()) {
            try {
                data = hc.runSQL(haku);
                headers = hc.getHeaders(table);
            } catch (IOException io) {
                System.out.println("Error");
            }
            try {
                assert data != null;
                if (data[0][0] != null) {
                    printMatrix(tbw, data, headers);
                }
            } catch (JSONException jsonException) {
                System.out.println("Error");
            }
        } else {
            setMonitorTableview(table, tbw);
        }
    }

    private void showAlert(String header, String text, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(header);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }
}
