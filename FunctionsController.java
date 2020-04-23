import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextField;
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

    @FXML
    private TableView tbwCustomer;

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
    private AnchorPane apCharts;

    @FXML
    public void controlOffices() {
        apOfficeControl.toFront();
    }

    @FXML
    public void controlAccommodations(){
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
