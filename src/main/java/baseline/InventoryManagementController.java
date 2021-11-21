package baseline;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class InventoryManagementController {

    @FXML
    private Button addBtn;

    @FXML
    private HBox filterArea;

    @FXML
    private Label lblName;

    @FXML
    private TextField nameBox;

    @FXML
    private MenuItem openOption;

    @FXML
    private TextField priceBox;

    @FXML
    private Button removeAllBtn;

    @FXML
    private Button removeBtn;

    @FXML
    private TextField serialBox;

    @FXML
    private TableView<?> tableView;

    @FXML
    private Button updateBtn;

    //Make an Observable list tabledata = to collecntions and Initialize it


    @FXML
    void addProduct(MouseEvent event) {
        //if serialNumber is empty
        //Display error message
        //else if name is empty
        //Display Error message
        //else if price is empty
        //Display error message
        //else make new products add data to products
        //Set tablebview items from data
        //Set text of name
        //Set text of serialNumber
        //Set text of price
    }

    @FXML
    void openFile(ActionEvent event) {
        //Use FileChooser to open files
        //Set title
        //Open Dialog window
        //if file is not empty
        //then get names on that file
        //get File Extension name to file
        //Using load method load the kind of file
    }
    @FXML
    public void loadData(){
        //if file extension is not a txt
        //Show file type and error message
        //else buffer read file and while String line is not empty
        //Parse the products
        //THen add to table
        //Set Data to table
    }

    @FXML
    void removeAll(ActionEvent event) {
        //data.clear product
        //Set items of tableview to data
    }

    @FXML
    void removeProduct(ActionEvent event) {
        //Get selected product
        //data.remove product
        //Set items of tableview to data

    }

    @FXML
    void updateProduct(ActionEvent event) {
        //Create a new stage
        //Set elements for new UI
        //Add elements to box
        //create prompt
    }

}

