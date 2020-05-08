import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.json.JSONException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Functions.fxml controller
 *
 * @author Juho Nykänen
 * @author Taneli Gröhn
 * @version 1.0
 */

public class FunctionsController {

    static httpController http = new httpController();

    static Bill bill = new Bill();
    //SQL haku-Stringit
    public String sqlAsiakas = "SELECT Asiakas_ID FROM Asiakas ORDER BY Asiakas_ID";
    public String sqlToimipiste = "SELECT Nimi FROM Toimipiste ORDER BY Nimi";
    public String sqlVaraus = "SELECT Varaus_ID FROM Varaus ORDER BY Varaus_ID";
    public String sqlPalvelu = "SELECT Palvelu_ID FROM Palvelu ORDER BY Palvelu_ID";
    public String sqlVarauksenLasku = "SELECT Varaus_ID FROM Lasku";
    String role = LoginController.role;
    //Lista kaikista toimipisteistä
    ObservableList<String> cbOfficeList = FXCollections.observableArrayList();
    //Lista kaikista asiakkaista
    ObservableList<String> cbCustomerList = FXCollections.observableArrayList();
    //Lista kaikista varauksista
    ObservableList<String> cbReservationList = FXCollections.observableArrayList();
    //Lista kaikista palveluista
    ObservableList<String> cbServiceList = FXCollections.observableArrayList();
    //Lista kaikista valitun toimipisteen huoneista
    ObservableList<String> cbRoomNumbers = FXCollections.observableArrayList();
    //Lista kaikista varauksista joilla on lasku
    ObservableList<String> VarauksenLaskut = FXCollections.observableArrayList();
    //Buttons
    @FXML
    private Button btnOffice;
    @FXML
    private Button btnService;
    @FXML
    private Button btnRoom;
    @FXML
    private Button btnReservation;
    @FXML
    private Button btnCustomer;
    @FXML
    private Button btnBill;

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
    @FXML
    private Button btnSearchOffice;
    @FXML
    private Button btnEditOffice;
    @FXML
    private Button btnDeleteOffice;
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
    @FXML
    private Button btnSearchService;
    @FXML
    private Button btnDeleteService;
    @FXML
    private Button btnEditService;

    //ACCOMMODATION CONTROL
    @FXML
    private ChoiceBox<String> cbA_officeID;
    @FXML
    private TextField tfRoomID;
    @FXML
    private TextField tfRoomDayPrice;
    @FXML
    private TextField tfRoomNumber;
    @FXML
    private Button btnSearchRoom;
    @FXML
    private Button btnEditRoom;
    @FXML
    private Button btnDeleteRoom;
    //RESERVATION CONTROL
    @FXML
    private ChoiceBox<String> cbR_roomID;
    @FXML
    private ChoiceBox<String> cbR_officeName;
    @FXML
    private ChoiceBox<String> cbR_customerID;
    @FXML
    private TextField tfReservationID;
    @FXML
    private DatePicker dpArriving;
    @FXML
    private DatePicker dpLeaving;
    @FXML
    private Button btnSearchRes;
    @FXML
    private Button btnEditRes;
    @FXML
    private Button btnDeleteRes;
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
    @FXML
    private Button btnSearchCust;
    @FXML
    private Button btnEditCust;
    @FXML
    private Button btnDeleteCust;

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
    @FXML
    private Button btnSearchBill;
    @FXML
    private Button btnEditBill;
    @FXML
    private Button btnDeleteBill;
    @FXML
    private ChoiceBox<String> cbBillReservationID;
    @FXML
    private RadioButton rbPaper;
    @FXML
    private RadioButton rbEmail;

    //TableView FXML
    @FXML
    private TableView<String> tbwCustomer;
    @FXML
    private TableView<String> tbwCustomer2;
    @FXML
    private TableView<String> tbwOffice;
    @FXML
    private TableView<String> tbwOffice2;
    @FXML
    private TableView<String> tbwService;
    @FXML
    private TableView<String> tbwService2;
    @FXML
    private TableView<String> tbwReservation;
    @FXML
    private TableView<String> tbwReservation2;
    @FXML
    private TableView<String> tbwRoom;
    @FXML
    private TableView<String> tbwRoom2;
    @FXML
    private TableView<String> tbwBill;
    @FXML
    private TableView<String> tbwBill2;
    @FXML
    private TableView<String> tbwSoldService;
    @FXML
    private TableView<String> tbwSoldService2;

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
    private TextField searchSoldServices;

    //Reports-tab fx:id
    @FXML
    private DatePicker dpFrom;
    @FXML
    private DatePicker dpTo;
    @FXML
    private ChoiceBox<String> cbOffice;
    @FXML
    private TableView<String[]> tbwReportReservations;
    @FXML
    private TableView<String[]> tbwReportServices;
    @FXML
    private LineChart<String, Integer> lcServices;
    @FXML
    private CategoryAxis xService;
    @FXML
    private NumberAxis yService;
    @FXML
    private Button btnGenerate;

    /**
     * Haetaan kaikki päivät kahden päivänmäärän väliltä listana
     *
     * @param startDate localDate
     * @param endDate   localDate
     * @return lista päivämääristä
     */
    public static List<LocalDate> getDatesBetween(LocalDate startDate, LocalDate endDate) {
        long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        return IntStream.iterate(0, i -> i + 1)
                .limit(numOfDaysBetween)
                .mapToObj(startDate::plusDays)
                .collect(Collectors.toList());
    }

    @FXML
    public void controlOffices() {
        setMonitorTableview("Toimipiste", tbwOffice2);
        apOfficeControl.toFront();
    }

    @FXML
    public void controlAccommodations() {
        setMonitorTableview("Huone", tbwRoom2);
        buildData(cbA_officeID, sqlToimipiste, cbOfficeList);
        apAccommodationControl.toFront();
    }

    @FXML
    public void controlServices() {
        setMonitorTableview("Palvelu", tbwService2);
        setMonitorTableview("Palveluvaraus", tbwSoldService2);
        buildData(cbS_OfficeID, sqlToimipiste, cbOfficeList);
        buildData(cbSellService, sqlPalvelu, cbServiceList);
        buildData(cbSellReservation, sqlVaraus, cbReservationList);
        apServiceControl.toFront();
    }

    @FXML
    public void controlReservations() {
        setMonitorTableview("Varaus", tbwReservation2);
        buildData(cbR_officeName, sqlToimipiste, cbOfficeList);
        buildData(cbR_customerID, sqlAsiakas, cbCustomerList);
        apReservationControl.toFront();
    }

    @FXML
    public void controlCustomers() {
        setMonitorTableview("Asiakas", tbwCustomer2);
        apCustomerControl.toFront();
    }

    @FXML
    public void controlBills() {
        setMonitorTableview("Lasku", tbwBill2);
        buildData(cbB_reservationID, sqlVaraus, cbReservationList);
        buildData(cbBillReservationID, sqlVarauksenLasku, VarauksenLaskut);
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
        buildData(cbOffice, sqlToimipiste, cbOfficeList);
        apReports.toFront();
    }

    /**
     * Metodi, joka täyttää kaikki choiceboxit ym. scenen käynnistyessä
     */
    @FXML
    private void initialize() {
        LocalDate ld1 = LocalDate.parse("2020-01-01");
        LocalDate ld2 = LocalDate.parse("2020-01-02");
        ToggleGroup tgRadiobutton = new ToggleGroup();

        rbEmail.setToggleGroup(tgRadiobutton);
        rbPaper.setToggleGroup(tgRadiobutton);

        //Disabloidaan tietyt editointi-napit ym.
        btnDeleteOffice.setDisable(true);
        btnEditOffice.setDisable(true);

        btnDeleteBill.setDisable(true);
        btnEditBill.setDisable(true);

        btnDeleteCust.setDisable(true);
        btnEditCust.setDisable(true);

        btnDeleteRes.setDisable(true);
        btnEditRes.setDisable(true);

        btnDeleteRoom.setDisable(true);
        btnEditRoom.setDisable(true);

        btnDeleteService.setDisable(true);
        btnEditService.setDisable(true);

        rbPaper.setDisable(true);
        rbEmail.setDisable(true);

        btnGenerate.setDisable(true);

        generateChart(ld1, ld2);

        //Päättää mitkä ominaisuudet ovat käytössä roolin mukaan
        if (role.equals("Customer Service")) {
            btnOffice.setDisable(true);
            btnOffice.setManaged(false);
            btnRoom.setDisable(true);
            btnRoom.setManaged(false);
        }

        buildData(cbR_customerID, sqlAsiakas, cbCustomerList);
        buildData(cbA_officeID, sqlToimipiste, cbOfficeList);
        cbS_OfficeID.setItems(cbOfficeList);
        cbOffice.setItems(cbOfficeList);
        cbR_officeName.setItems(cbOfficeList);
        buildData(cbB_reservationID, sqlVaraus, cbReservationList);
        cbSellReservation.setItems(cbReservationList);
        buildData(cbBillReservationID, sqlVarauksenLasku, VarauksenLaskut);
        buildData(cbSellService, sqlPalvelu, cbServiceList);

        //Metodit nappien aktivoimiselle Lasku-välilehdessä
        cbBillReservationID.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue)
                -> activateButtons());
        rbPaper.selectedProperty().addListener((v, old, newValue) -> activateGeneration());
        rbEmail.selectedProperty().addListener((v, old, newValue) -> activateGeneration());

        cbR_officeName.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue)
                -> reservationFillRoomNumber());

        changeTabReports();
    }

    private void activateButtons() {
        rbEmail.setDisable(false);
        rbPaper.setDisable(false);
    }

    private void activateGeneration() {
        btnGenerate.setDisable(false);
    }

    @FXML
    public void generateBill() {
        String format = "";
        if (rbPaper.isSelected()) {
            format = "Kirje";
        } else if (rbEmail.isSelected()) {
            format = "Sähköposti";
        }
        bill.CreatePDF(cbBillReservationID.getValue(), format);
    }

    @FXML
    private void reservationFillRoomNumber() {
        String officeName = cbR_officeName.getValue();

        String sql = String.format("SELECT Huone.Huonenumero FROM Huone " +
                        "INNER JOIN Toimipiste " +
                        "ON Huone.Toimipiste_ID = Toimipiste.Toimipiste_ID " +
                        "WHERE Toimipiste.Nimi = \"%s\";",
                officeName);
        if (!cbRoomNumbers.isEmpty()) {
            cbRoomNumbers.clear();
        }
        buildData(cbR_roomID, sql, cbRoomNumbers);
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
                    "You may not insert empty fields."
            );
        }
        setMonitorTableview("Toimipiste", tbwOffice2);
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
                    "You may not insert empty fields."
            );
        }
        setMonitorTableview("Asiakas", tbwCustomer2);
    }

    /**
     * Metodi, jolla lisätään Huone/majoitus tietokantaan
     */
    @FXML
    private void insertAccommodation() {
        String price = tfRoomDayPrice.getText();
        String rnum = tfRoomNumber.getText();
        String officeName = cbA_officeID.getValue();

        String[][] officeID = null;
        try {
            String sql = String.format("SELECT Toimipiste_ID FROM Toimipiste " +
                    "WHERE Nimi = \"%s\";", officeName);
            officeID = http.runSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            assert officeID != null;
            String values = String.format("\"%s\", \"%s\", \"%s\"",
                    price, rnum, officeID[0][0]);
            System.out.println(values);
            http.setValues("Huone", values);
        } catch (Exception e) {
            showAlert("Insert failed",
                    "You may not insert empty fields."
            );
        }
        setMonitorTableview("Huone", tbwRoom2);
    }

    /**
     * Metodi, jolla lisätään varaus tietokantaan
     */
    @FXML
    private void insertReservation() {
        String arriving = dpArriving.getValue().toString();
        String leaving = dpLeaving.getValue().toString();
        String customerID = cbR_customerID.getValue();
        String roomNumber = cbR_roomID.getValue();
        String OfficeName = cbR_officeName.getValue();
        String roomID;
        String[][] data;
        String sql = String.format("SELECT Huone.Huone_ID FROM Huone " +
                " INNER JOIN Toimipiste ON Huone.Toimipiste_ID = Toimipiste.Toimipiste_ID " +
                " WHERE Huone.Huonenumero = \"%s\" AND Toimipiste.Nimi = \"%s\"", roomNumber, OfficeName);
        try {
            data = http.runSQL(sql);
            roomID = data[0][0];
            String values = String.format("\"%s\", \"%s\", \"%s\", \"%s\"",
                    arriving, leaving, customerID, roomID);
            System.out.println(values);
            http.setValues("Varaus", values);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Insert failed",
                    "Check if there are overlapping reservations. Also you may not insert empty fields."
            );
        }
        setMonitorTableview("Varaus", tbwReservation2);
    }

    /**
     * Metodi, jolla lisätään palvelu tietokantaan
     */
    @FXML
    private void insertService() {
        String name = tfServiceName.getText();
        String price = tfServicePrice.getText();
        String officeName = cbS_OfficeID.getValue();
        String[][] officeID = null;
        try {
            String sql = String.format("SELECT Toimipiste_ID FROM Toimipiste " +
                    "WHERE Nimi = \"%s\";", officeName);
            officeID = http.runSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            assert officeID != null;
            String values = String.format("\"%s\", \"%s\", \"%s\"",
                    name, price, officeID[0][0]);
            System.out.println(values);
            http.setValues("Palvelu", values);
        } catch (Exception e) {
            showAlert("Insert failed",
                    "You may not insert empty fields."
            );
        }
        setMonitorTableview("Palvelu", tbwService2);
    }

    @FXML
    private void insertSoldService() {
        String serviceID = cbSellService.getValue();
        String reservationID = cbSellReservation.getValue();
        try {
            String values = String.format("\"%s\", \"%s\"",
                    reservationID, serviceID);
            System.out.println(values);
            http.setValues("Palveluvaraus", values);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Insert failed",
                    "You may not insert empty fields."
            );
        }
        setMonitorTableview("Palveluvaraus", tbwSoldService2);
        buildData(cbSellService, sqlPalvelu, cbServiceList);
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
        String paid = "0";

        if (checkPaid.isSelected()) {
            paid = "1";
        }
        try {
            String values = String.format("\"%s\", \"%s\", \"%s\", \"%s\", \"%s\"",
                    reservationID, sum, due, sentDate, paid);
            System.out.println(paid);
            System.out.println(values);
            http.setValues("Lasku", values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setMonitorTableview("Lasku", tbwBill2);
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
            String values = String.format("Nimi=\"%s\", Katuosoite=\"%s\", Postinumero=\"%s\", Postitoimipaikka=\"%s\"",
                    name, address, pcode, pcity);
            System.out.println(values);
            http.updateValues("Toimipiste", values, officeID);
            btnEditOffice.setDisable(true);
            btnDeleteOffice.setDisable(true);
        } catch (Exception e) {
            showAlert("Update failed",
                    "Check Office ID. You may not insert empty fields."
            );
        }
        setMonitorTableview("Toimipiste", tbwOffice2);
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
            btnEditCust.setDisable(true);
            btnDeleteCust.setDisable(true);
        } catch (Exception e) {
            showAlert("Update failed",
                    "Check Customer ID. You may not insert empty fields."
            );
        }
        setMonitorTableview("Asiakas", tbwCustomer2);
    }

    /**
     * Metodi, jolla päivitetään olemassaolevan huoneen tietoja
     */
    @FXML
    private void updateAccommodation() {
        String roomID = tfRoomID.getText();
        String price = tfRoomDayPrice.getText();
        String rnum = tfRoomNumber.getText();
        String officeName = cbA_officeID.getValue();

        String[][] officeID = null;
        try {
            String sql = String.format("SELECT Toimipiste_ID FROM Toimipiste " +
                    "WHERE Nimi = \"%s\";", officeName);
            officeID = http.runSQL(sql);
            btnEditRoom.setDisable(true);
            btnDeleteRoom.setDisable(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            assert officeID != null;
            String values = String.format("Paivahinta=\"%s\", Huonenumero=\"%s\", Toimipiste_ID=\"%s\"",
                    price, rnum, officeID[0][0]);
            System.out.println(values);
            http.updateValues("Huone", values, roomID);
        } catch (Exception e) {
            showAlert("Update failed",
                    "Check Room ID. You may not insert empty fields."
            );
        }
        setMonitorTableview("Huone", tbwRoom2);

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
        String OfficeName = cbR_officeName.getValue();
        String roomNumber = cbR_roomID.getValue();

        String roomID = null;
        String[][] data = null;
        String sql = String.format("SELECT Huone.Huone_ID FROM Huone " +
                " INNER JOIN Toimipiste ON Huone.Toimipiste_ID = Toimipiste.Toimipiste_ID " +
                " WHERE Huone.Huonenumero = \"%s\" AND Toimipiste.Nimi = \"%s\"", roomNumber, OfficeName);
        try {
            data = http.runSQL(sql);
            roomID = data[0][0];
            btnEditRes.setDisable(true);
            btnDeleteRes.setDisable(true);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        assert data != null;
        try {
            String values = String.format("Alkupvm=\"%s\", Loppupvm=\"%s\", Asiakas_ID=\"%s\", Huone_ID=\"%s\"",
                    arriving, leaving, customerID, roomID);
            System.out.println(values);
            http.updateValues("Varaus", values, reservationID);
        } catch (Exception e) {
            showAlert("Update failed",
                    "Check Reservation ID. You may not insert empty fields."
            );
        }
        setMonitorTableview("Varaus", tbwReservation2);
    }

    /**
     * Metodi, jolla päivitetään olemassaolevan palvelun tietoja
     */
    @FXML
    private void updateService() {
        String serviceID = tfServiceID.getText();
        String name = tfServiceName.getText();
        String price = tfServicePrice.getText();
        String OfficeName = cbS_OfficeID.getValue();

        String officeID = null;
        String[][] data;
        String sql = String.format("SELECT Toimipiste_ID FROM Toimipiste WHERE Nimi = \"%s\"", OfficeName);
        try {
            data = http.runSQL(sql);
            officeID = data[0][0];
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        try {
            String values = String.format("Nimi=\"%s\", Hinta=\"%s\", Toimipiste_ID=\"%s\"",
                    name, price, officeID);
            System.out.println(values);
            http.updateValues("Palvelu", values, serviceID);
            btnEditService.setDisable(true);
            btnDeleteService.setDisable(true);
        } catch (Exception e) {
            showAlert("Update failed",
                    "Check Service ID. You may not insert empty fields."
            );
        }
        setMonitorTableview("Palvelu", tbwService2);

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
        String paid = "0";
        if (checkPaid.isSelected()) {
            paid = "1";
        }

        try {
            String values = String.format("Varaus_ID=\"%s\", Loppusumma=\"%s\", Erapaiva=\"%s\", Lahetyspvm=\"%s\", " +
                            "Maksu_suoritettu=\"%s\"",
                    reservationID, sum, due, sentDate, paid);
            System.out.println(values);
            http.updateValues("Lasku", values, billID);
            btnEditBill.setDisable(true);
            btnDeleteBill.setDisable(true);
        } catch (Exception e) {
            showAlert("Update failed",
                    "Check Bill ID. You may not insert empty fields."
            );
        }
        setMonitorTableview("Lasku", tbwBill2);

    }

    /**
     * Metodi toimiston tietokannasta poistamiselle
     */
    @FXML
    private void deleteOffice() {
        String officeID = tfOfficeID.getText();
        try {
            http.deleteValues("Toimipiste", officeID, "");
            btnDeleteOffice.setDisable(true);
            btnEditOffice.setDisable(true);
        } catch (Exception e) {
            showAlert("Deletion aborted",
                    "Check Office ID. The office may not have any services or accommodations assigned."
            );
        }
        setMonitorTableview("Toimipiste", tbwOffice2);
    }

    /**
     * Metodi asiakkaan tietokannasta poistamiselle
     */
    @FXML
    private void deleteCustomer() {
        String customerID = tfCustomerID.getText();
        try {
            http.deleteValues("Asiakas", customerID, "");
            btnDeleteCust.setDisable(true);
            btnEditCust.setDisable(true);
        } catch (Exception e) {
            showAlert("Deletion aborted",
                    "Check Customer ID. The Customer may not have any active reservations."
            );
        }
        setMonitorTableview("Asiakas", tbwCustomer2);
    }

    /**
     * Metodi huoneen tietokannasta poistamiselle
     */
    @FXML
    private void deleteAccommodation() {
        String roomID = tfRoomID.getText();
        try {
            http.deleteValues("Huone", roomID, "");
            btnDeleteRoom.setDisable(true);
            btnEditRoom.setDisable(true);
        } catch (Exception e) {
            showAlert("Deletion aborted",
                    "Check Room ID. The Room may not have any active reservations."
            );
        }
        setMonitorTableview("Huone", tbwRoom2);
    }

    /**
     * Metodi varauksen tietokannasta poistamiselle
     */
    @FXML
    private void deleteReservation() {
        String reservationID = tfReservationID.getText();
        try {
            http.deleteValues("Varaus", reservationID, "");
            btnDeleteRes.setDisable(true);
            btnEditRes.setDisable(true);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Deletion aborted",
                    "Check Reservation ID. The Reservation may not have any active services."
            );
        }
        setMonitorTableview("Varaus", tbwReservation2);
    }

    /**
     * Metodi palvelun tietokannasta poistamiselle
     */
    @FXML
    private void deleteService() {
        String serviceID = tfServiceID.getText();
        try {
            http.deleteValues("Palvelu", serviceID, "");
            btnDeleteService.setDisable(true);
            btnEditService.setDisable(true);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Deletion aborted",
                    "Check Service ID. The Service may not have any active reservations."
            );
        }
        setMonitorTableview("Palvelu", tbwService2);
    }

    /**
     * Metodi palvelumyynnin tietokannasta poistamiselle
     */
    @FXML
    private void deleteSoldService() {
        String serviceID = cbSellService.getValue();
        String reservationID = cbSellReservation.getValue();
        try {
            http.deleteValues("Palveluvaraus", reservationID, serviceID);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Deletion aborted",
                    "Check Service/reservation ID."
            );
        }
        setMonitorTableview("Palveluvaraus", tbwSoldService2);
    }

    /**
     * Metodi laskun tietokannasta poistamiselle
     */
    @FXML
    private void deleteBill() {
        String billID = tfBillID.getText();
        try {
            http.deleteValues("Lasku", billID, "");
            btnDeleteBill.setDisable(true);
            btnEditBill.setDisable(true);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Deletion aborted",
                    "Check Bill ID."
            );
        }
        setMonitorTableview("Lasku", tbwBill2);
    }

    /**
     * Aseta Office control sarakkeen textfield-kenttiin arvot OfficeID-kentän arvon perusteella
     */
    @FXML
    private void setOfficeText() {
        String[][] data = null;
        String officeID = tfOfficeID.getText();
        String sql = String.format("SELECT * FROM Toimipiste WHERE Toimipiste_ID=%s",
                officeID);
        try {
            data = http.runSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Search aborted",
                    "Check Office ID."
            );
        }
        assert data != null;
        tfOfficeName.setText(data[0][1]);
        tfOfficeStreet.setText(data[0][2]);
        tfOfficePostal.setText(data[0][3]);
        tfOfficeCity.setText(data[0][4]);
        btnEditOffice.setDisable(false);
        btnDeleteOffice.setDisable(false);
    }

    /**
     * Aseta Service control sarakkeen textfield-kenttiin arvot ServiceID-kentän arvon perusteella
     */
    @FXML
    private void setServiceText() {
        String[][] data = null;
        String serviceID = tfServiceID.getText();
        String sql = String.format("SELECT Palvelu.Nimi, Palvelu.Hinta, Toimipiste.Nimi FROM Palvelu " +
                        "INNER JOIN Toimipiste " +
                        "ON Palvelu.Toimipiste_ID = Toimipiste.Toimipiste_ID " +
                        "WHERE Palvelu.Palvelu_ID = %s;",
                serviceID);
        try {
            data = http.runSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Search aborted",
                    "Check Service ID."
            );
        }
        assert data != null;
        cbS_OfficeID.setValue(data[0][2]);
        tfServiceName.setText(data[0][0]);
        tfServicePrice.setText(data[0][1]);
        btnEditService.setDisable(false);
        btnDeleteService.setDisable(false);
    }

    /**
     * Aseta Accommodation control sarakkeen textfield-kenttiin arvot RoomID-kentän arvon perusteella
     */
    @FXML
    private void setAccommodationText() {
        String[][] data = null;
        String roomID = tfRoomID.getText();
        String sql = String.format("SELECT Huone.Paivahinta, Huone.Huonenumero, Toimipiste.Nimi FROM Huone " +
                        "INNER JOIN Toimipiste " +
                        "ON Huone.Toimipiste_ID = Toimipiste.Toimipiste_ID " +
                        "WHERE Huone_ID = %s;",
                roomID);
        try {
            data = http.runSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Search aborted",
                    "Check Room ID."
            );
        }
        assert data != null;
        cbA_officeID.setValue(data[0][2]);
        tfRoomDayPrice.setText(data[0][0]);
        tfRoomNumber.setText(data[0][1]);
        btnEditRoom.setDisable(false);
        btnDeleteRoom.setDisable(false);
    }

    /**
     * Aseta Reservation control sarakkeen textfield-kenttiin arvot ReservationID-kentän arvon perusteella
     */
    @FXML
    private void setReservationText() {
        String[][] data = null;
        String reservationID = tfReservationID.getText();
        String sql = String.format("SELECT * FROM Varaus WHERE Varaus_ID=%s",
                reservationID);
        try {
            data = http.runSQL(sql);
            btnEditRes.setDisable(false);
            btnDeleteRes.setDisable(false);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Search aborted",
                    "Check Reservation ID."
            );
        }
        assert data != null;
        for (String[] datum : data) {
            for (String s : datum) {
                System.out.println(s);
            }
        }

        //Choiceboxien täyttö
        String[][] huonedata = null;
        String huoneSQL = String.format("SELECT Toimipiste.Nimi FROM Huone INNER JOIN Toimipiste ON " +
                "Huone.Toimipiste_ID = Toimipiste.Toimipiste_ID WHERE Huone.Huone_ID = \"%s\"", data[0][4]);
        try {
            huonedata = http.runSQL(huoneSQL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert huonedata != null;
        for (String[] datum : huonedata) {
            for (String s : datum) {
                System.out.println(s);
            }
        }
        cbR_customerID.setValue(data[0][3]);
        cbR_officeName.setValue(huonedata[0][0]);
        cbR_roomID.setValue(data[0][4]);
        DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate arrDate = LocalDate.parse(data[0][0], dt);
        LocalDate depDate = LocalDate.parse(data[0][1], dt);
        dpArriving.setValue(arrDate);
        dpLeaving.setValue(depDate);
        btnEditRes.setDisable(false);
        btnDeleteRes.setDisable(false);
    }

    /**
     * Aseta Customer control sarakkeen textfield-kenttiin arvot CustomerID-kentän arvon perusteella
     */
    @FXML
    private void setCustomerText() {
        String[][] data = null;
        String customerID = tfCustomerID.getText();
        String sql = String.format("SELECT * FROM Asiakas WHERE Asiakas_ID=%s",
                customerID);
        try {
            data = http.runSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Search aborted",
                    "Check Customer ID."
            );
        }
        assert data != null;
        tfFirstName.setText(data[0][1]);
        tfLastName.setText(data[0][2]);
        tfPhone.setText(data[0][3]);
        tfEmail.setText(data[0][4]);
        tfAddress.setText(data[0][5]);
        tfPostal.setText(data[0][6]);
        tfCity.setText(data[0][7]);
        btnEditCust.setDisable(false);
        btnDeleteCust.setDisable(false);
    }

    /**
     * Aseta Bill control sarakkeen textfield-kenttiin arvot BillID-kentän arvon perusteella
     */
    @FXML
    private void setBillText() {
        String[][] data = null;
        String billID = tfBillID.getText();
        String sql = String.format("SELECT * FROM Lasku WHERE Lasku_ID=%s",
                billID);
        try {
            data = http.runSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Search aborted",
                    "Check Bill ID."
            );
        }
        System.out.println(Arrays.deepToString(data));
        assert data != null;
        tfSumTotal.setText(data[0][2]);
        DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dueDate = LocalDate.parse(data[0][3], dt);
        LocalDate sentDate = LocalDate.parse(data[0][4], dt);
        dpDueDate.setValue(dueDate);
        dpSent.setValue(sentDate);
        cbB_reservationID.setValue(data[0][1]);
        checkPaid.setSelected(data[0][5].equals("1"));
        btnEditBill.setDisable(false);
        btnDeleteBill.setDisable(false);
    }

    /**
     * Hae valitun toimipisteen varaukset valitulta aikajaksolta ja aseta saadut tiedot tableview taulukkoon.
     */
    @FXML
    private void searchOfficeReservations() {
        LocalDate arrDate = dpFrom.getValue();
        LocalDate depDate = dpTo.getValue();
        String officeID = cbOffice.getValue();
        String[][] values = null;
        String[] headers = {"Reservation ID", "Date of Arrival", "Date of Departure", "Reservant"};

        String sql = String.format(
                "SELECT Varaus.Varaus_ID, Varaus.Alkupvm, Varaus.Loppupvm, " +
                        "CONCAT(Asiakas.Etunimi, ' ', Asiakas.Sukunimi) FROM Varaus " +
                        "INNER JOIN Asiakas " +
                        "ON Asiakas.Asiakas_ID = Varaus.Asiakas_ID " +
                        "INNER JOIN Huone " +
                        "ON Varaus.Huone_ID = Huone.Huone_ID " +
                        "INNER JOIN Toimipiste " +
                        "ON Huone.Toimipiste_ID = Toimipiste.Toimipiste_ID " +
                        "WHERE NOT (Alkupvm > \"%s\" OR Loppupvm < \"%s\") AND Toimipiste.Nimi = \"%s\";",
                depDate, arrDate, officeID);
        System.out.println(sql);

        try {
            values = http.runSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert values != null;
        printMatrix(tbwReportReservations, values, headers);
        searchSoldServices();
        generateChart(arrDate, depDate);
    }

    /**
     * Hae valitun toimipisteen varaukset valitulta aikajaksolta ja aseta saadut tiedot tableview taulukkoon.
     */
    @FXML
    private void searchSoldServices() {
        LocalDate arrDate = dpFrom.getValue();
        LocalDate depDate = dpTo.getValue();
        String officeID = cbOffice.getValue();
        String[][] values = null;
        String[] headers = {"Reservation ID", "Service ID"};

        String sql = String.format(
                "SELECT Palveluvaraus.Varaus_ID, Palveluvaraus.Palvelu_ID FROM Palveluvaraus " +
                        "INNER JOIN Varaus " +
                        " ON Palveluvaraus.Varaus_ID = Varaus.Varaus_ID " +
                        " INNER JOIN Huone " +
                        " ON Varaus.Huone_ID = Huone.Huone_ID " +
                        " INNER JOIN Toimipiste " +
                        " ON Huone.Toimipiste_ID = Toimipiste.Toimipiste_ID " +
                        " WHERE NOT (Varaus.Alkupvm > \"%s\" OR Varaus.Loppupvm < \"%s\") AND Toimipiste.Nimi = \"%s\";",
                depDate, arrDate, officeID);
        System.out.println(sql);

        try {
            values = http.runSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert values != null;
        printMatrix(tbwReportServices, values, headers);

        generateChart(arrDate, depDate);
    }

    /**
     * Metodi jolla saadaan data kaavioihin
     *
     * @param arrDate tulopäivä
     * @param depDate lähtöpäivä
     */
    public void generateChart(LocalDate arrDate, LocalDate depDate) {
        lcServices.getData().clear();

        String[][] data;
        String xArvo;
        int yArvo = 0;

        //Tilastot reports-välilehteen
        XYChart.Series reservations = new XYChart.Series();
        reservations.setName("Reservations");

        XYChart.Series services = new XYChart.Series();
        services.setName("Services");

        reservations.getData().add(new XYChart.Data("2020-01-03", 0));
        services.getData().add(new XYChart.Data("2020-01-03", 0));

        lcServices.getYAxis().setTickLabelsVisible(true);
        lcServices.getXAxis().setTickLabelsVisible(false);
        yService.setAutoRanging(false);
        yService.setLowerBound(0);
        yService.setUpperBound(15);
        yService.setTickUnit(1);

        //Lista päivämääristä kahden päivämäärän välillä
        List<LocalDate> dates = getDatesBetween(arrDate, depDate);

        try {
            for (Object date : dates) {
                String sql = String.format("SELECT COUNT(Varaus_ID) FROM Varaus WHERE Alkupvm = \"%s\"", date);
                data = http.runSQL(sql);
                yArvo = Integer.parseInt(data[0][0]);
                xArvo = date.toString();
                reservations.getData().add(new XYChart.Data(xArvo, yArvo));
            }
            lcServices.getData().addAll(reservations);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        //Palvelujen hakeminen kaavioon
        try {
            for (Object date : dates) {
                String palveluSQL = String.format("SELECT COUNT(Palveluvaraus.Palvelu_ID) FROM Palveluvaraus " +
                        "INNER JOIN Varaus ON Palveluvaraus.Varaus_ID = Varaus.Varaus_ID " +
                        "WHERE Varaus.Alkupvm = \"%s\"", date);
                data = http.runSQL(palveluSQL);
                yArvo = Integer.parseInt(data[0][0]);
                xArvo = date.toString();
                services.getData().add(new XYChart.Data(xArvo, yArvo));
            }
            lcServices.getData().addAll(services);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * Metodi monitor osion TableView taulun tietojen asettamiselle
     *
     * @param table Haluttu tietokannan taulu
     * @param tbw   Kohteena oleva TableView fx:id
     */
    private void setMonitorTableview(String table, TableView tbw) {
        String[][] values = null;
        String[] headers = null;

        try {
            values = http.getValues("select", table);
            headers = http.getHeaders(table);

        } catch (Exception e) {
            e.printStackTrace();
        }
        assert values != null;
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

            column.setMinWidth(220);
            target.getColumns().add(column);
        }

        for (String[] strings : source) {
            target.getItems().add(strings);
        }
    }

    /**
     * Method which changes the scene to the same window
     *
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
            window.getIcons().add(new Image("file:valkoinentaustakuvkae.png"));
            window.centerOnScreen();
            window.setMaximized(false);
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
        cb.getItems().clear();
        list.clear();
        String[][] data;
        try {
            data = http.runSQL(sql);
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

    @FXML
    public void searchSoldService() {
        String value = searchSoldServices.getText();
        String sql = String.format("SELECT * FROM Palveluvaraus WHERE Palvelu_ID LIKE \"%s\" OR Varaus_ID LIKE \"%s\"",
                value, value);
        search(tbwSoldService, "Palveluvaraus", sql, searchSoldServices);
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

    /**
     * Alert-ilmoitus joka näkyy tietojenkäsittelyn eri vaiheissa
     *
     * @param header Otsikko
     * @param text   Teksti
     */
    private void showAlert(String header, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(header);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }
}
