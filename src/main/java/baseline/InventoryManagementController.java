package baseline;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InventoryManagementController {

    @FXML
    private TableColumn<Product, String> serialNumberCol;

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
    private TableView<Product> tableView;

    @FXML
    private Button updateBtn;

    FileChooser fileChooser;

    //Make an Observable list tabledata = to collecntions and Initialize
    private final ObservableList<Product> tableData = FXCollections.observableArrayList();


    @FXML
    void addProduct(MouseEvent event) {
        //if serialNumber is empty
        if(priceBox.getText().matches("[a-zA-Z][-][0-9a-zA-Z]{3}[-][a-zA-Z0-9]{3}[-][a-zA-Z0-9]{3}$")){
            //Set tablebview items from data
            Product product = new Product(nameBox.getText(), serialBox.getText(), Double.parseDouble(priceBox.getText()));
            tableData.add(product);
            tableView.setItems(tableData);
            //Set text of name
            nameBox.setText("");
            //Set text of serialNumber
            serialBox.setText("");
            //Set text of price
            priceBox.setText("");
        }
        else if(serialBox.getText().matches(""))
        {
            //Display error message
            Alert productAlert = new Alert(Alert.AlertType.ERROR, "Serial Number cannot be empty!");
            productAlert.show();
        }

        //else if name is empty
        else if(nameBox.getText().matches(""))
        {
            //Display Error message
            Alert productAlert = new Alert(Alert.AlertType.ERROR, "Product Name cannot be empty!");
            productAlert.show();
        }

        //else if price is empty
        else if(priceBox.getText().matches(""))
        {
            //Display error message
            Alert productAlert = new Alert(Alert.AlertType.ERROR, "Invalid price!");
            productAlert.show();
        }


        //else make new products add data to products
        else
        {
            Alert productAlert = new Alert(Alert.AlertType.ERROR, "Invalid Format");
            productAlert.show();
        }
    }

    @FXML
    void onOpen(ActionEvent event) throws IOException {
        //Use FileChooser to open files
        FileChooser fileOpener = new FileChooser();
        //Set title
        fileOpener.setTitle("Select Database File");
        //Open Dialog window
        File file = fileOpener.showOpenDialog(filterArea.getScene().getWindow());

        //if file is not empty
        if (file != null) {
            //then get names on that file
            String fileName = file.getName();
            //get File Extension name to file
            String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1, file.getName().length());
            //Using load method load the kind of file
            loadData(file, fileExtension);

        }
    }
    @FXML
    public void loadData(File file, String fileExtension) throws IOException {
        //if file extension is not a txt
            if(!fileExtension.matches("txt")) {
                //Show file type and error message
                System.out.println(fileExtension);
                Alert fileAlert = new Alert(Alert.AlertType.ERROR, "Invalid File Type! Only .txt files allowed");
                fileAlert.show();
            }
                //else buffer read file and while String line is not empty
        else{
                BufferedReader reader  = new BufferedReader(new FileReader(file));
                String line;
                while((line = reader.readLine()) != null){
                    //Parse the products
                    String[] data = line.split("\t");
                    Product product = new Product(data[1], data[0],Double.parseDouble(data[2]));

                    //THen add to table
                    tableData.add(product);
                }
                //Set Data to table
                tableView.setItems(tableData);
                reader.close();
            }

            }



    @FXML
    void removeAll(ActionEvent event) {
        //data.clear product
        tableData.clear();
        //Set items of tableview to data
        tableView.setItems(tableData);
    }

    @FXML
    void removeProduct(ActionEvent event) {
        //Get selected product
        Product product = (Product) tableView.getSelectionModel().getSelectedItem();
        //data.remove product
        tableData.remove(product);
        //Set items of tableview to data
        tableView.setItems(tableData);

    }

    @FXML
    void updateProduct(ActionEvent actionEvent) {
        //Create a new stage
        Stage updatePrompt = new Stage();
        VBox vbox = new VBox();
        //Set elements for new UI
        Label lblUpdate = new Label("Product Price: ");
        TextField txtBox = new TextField();
        Button saveBtn = new Button("Save");
        saveBtn.setOnMouseClicked(event->{});
        //Add elements to box
        vbox.getChildren().addAll(lblUpdate, txtBox, saveBtn);
        //create prompt
        Scene updateScene = new Scene(vbox);
        updatePrompt.setScene(updateScene);
        updatePrompt.show();
    }


    public void onSave(ActionEvent actionEvent) {
        //Use FileChooser to save
        File savesFile = fileChooser.showSaveDialog(null);
        if (savesFile != null) {
            try {
                //Use PrintWriter to write to file
                PrintWriter pw = new PrintWriter(savesFile);
                //Loop for each task in list
                for (Product t: tableData){
                    //Write current task date to file and description
                    pw.write(t.getSerialNumber() + '\n');
                    pw.write(t.getName() + '\n');
                    pw.write((int) (t.getPrice() + '\n'));
                }
                pw.close();
                //Catch Exceptions
            } catch (IOException ex) {

                Logger.getLogger(InventoryManagementApplication.class.getName()).log(Level.SEVERE, null, ex);
            }
                }
    }
}

