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


    public void initialize(URL location, ResourceBundle resources) {
        fileChooser.setInitialDirectory(new File("C:"));
    }


    @FXML
    void addProduct(MouseEvent event) {
        //if serialNumber is empty
        if (serialBox.getText().matches("")) {
            //Display error message
            Alert productAlert = new Alert(Alert.AlertType.ERROR, "Serial Number cannot be empty!");
            productAlert.show();
        }

        //else if name is empty
        else if (nameBox.getLength() <= 1 || nameBox.getLength() >= 257) {
            //Display Error message
            Alert productAlert = new Alert(Alert.AlertType.ERROR, "Product Name must be 2 or more characters and less than 256!");
            productAlert.show();
        }

        //else if price is empty
        else if (priceBox.getText().matches("[0]")) {
            //Display error message
            Alert productAlert = new Alert(Alert.AlertType.ERROR, "Can not be empty!");
            productAlert.show();
        } else if (!priceBox.getText().matches("^(0|[1-9][0-9]*)$")) {
            Alert productAlert = new Alert(Alert.AlertType.ERROR, "Must enter a Number!");
            productAlert.show();
        }

        //else make new products add data to products
        else {
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
        if (!fileExtension.matches("txt")) {
            //Show file type and error message
            System.out.println(fileExtension);
            Alert fileAlert = new Alert(Alert.AlertType.ERROR, "Invalid File Type! Only .txt files allowed");
            fileAlert.show();
        }
        //else buffer read file and while String line is not empty
        else {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                //Parse the products
                String[] data = line.split("\t");
                Product product = new Product(data[1], data[0], Double.parseDouble(data[2]));

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
        saveBtn.setOnMouseClicked(event -> {
        });
        //Add elements to box
        vbox.getChildren().addAll(lblUpdate, txtBox, saveBtn);
        //create prompt
        Scene updateScene = new Scene(vbox);
        updatePrompt.setScene(updateScene);
        updatePrompt.show();
    }

    @FXML
    public void onSave(ActionEvent actionEvent) {

        FileChooser fileChooser = new FileChooser();
        //Set title
        fileChooser.setTitle("Save Dialog");
        //Open Dialog window

        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("text file", "*.txt"),
                new FileChooser.ExtensionFilter("HTML", "*.html"), new FileChooser.ExtensionFilter("JSON", "*.json"),
                new FileChooser.ExtensionFilter("tab-separated value", "*.tsv"));
        File file = fileChooser.showSaveDialog(filterArea.getScene().getWindow());
        if (file != null) {
            try {

                fileChooser.setInitialDirectory(file.getParentFile());

                PrintWriter printWriter = new PrintWriter(file);
                for(Product p :tableData) {
                    printWriter.write(p.getSerialNumber());
                    printWriter.write(p.getName() + '\n');
                    printWriter.write(String.valueOf(p.getPrice() + '\n'));

                }
                printWriter.close();



            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }
}

