package baseline;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
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

    @FXML
    private ComboBox combo;

    @FXML
    public TextField searchBox;

    @FXML
    public ComboBox formatCombo;


    FileChooser fileChooser;

    //Make an Observable list tabledata = to collecntions and Initialize
    private final ObservableList<Product> tableData = FXCollections.observableArrayList();

    @FXML
    void addProduct(MouseEvent event) {
        //unique serial number
        for (Product tableDatum : tableData) {
            if (tableDatum.getSerialNumber().matches(serialBox.getText())) {
                Alert productAlert = new Alert(Alert.AlertType.ERROR, "Serial number must " +
                        "unique! Please try again.");
                productAlert.show();
                return;
            }
        }
        //if serialNumber is not valid format
        if(!serialBox.getText().matches("^[a-zA-Z][-][0-9a-zA-Z]{3}[-][a-zA-Z0-9]{3}[-][a-zA-Z0-9]{3}$"))
        {
            Alert productAlert = new Alert(Alert.AlertType.ERROR, "Serial number must be A-XXX-XXX-XXX format where X is a digit or alphabet!");
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
        Product product = tableView.getSelectionModel().getSelectedItem();
        //data.remove product
        tableData.remove(product);
        //Set items of tableview to data
        tableView.setItems(tableData);

    }

    @FXML
    void updateProduct(ActionEvent actionEvent) {
        Product product =(Product) tableView.getSelectionModel().getSelectedItem();

        //If a selection wasnt made display error message
        if(tableView.getSelectionModel().getSelectedItem() == null) {
            Alert error = new Alert(Alert.AlertType.ERROR, "Please select a record!");
            error.show();
            return;
        }
        //Create a new stage
        Stage updatePrompt = new Stage();
        VBox vbox = new VBox();
        //Set elements for new UI
        Label lblUpdate = new Label("Product Price: ");
        TextField txtBox = new TextField();
        Button saveBtn = new Button("Save");
        saveBtn.setOnMouseClicked(event->{
            System.out.println(txtBox.getText().matches("^[0-9.]*$"));
            if(!txtBox.getText().matches("^[0-9.]*$"))
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid price! Please try again.");
                alert.show();
            }
            else if(Double.parseDouble(txtBox.getText()) <= 0)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Price must be greater than 0.");
                alert.show();
            }
            else
            {
                Double price= Double.parseDouble(txtBox.getText());
                updatePrompt.close();
                tableData.remove(product);
                product.setPrice(price);
                tableData.add(product);
                tableView.setItems(tableData);

            }

        });

        //Add elements to the box
        vbox.getChildren().addAll(lblUpdate, txtBox, saveBtn);

        //Create prompt
        Scene updateScene = new Scene(vbox);
        updatePrompt.setScene(updateScene);
        updatePrompt.show();

    }
    public void searchProduct(KeyEvent actionEvent) {

        ObservableList<Product> list = FXCollections.observableArrayList();
        //If no search was activated Display error message
        if(combo.getValue()==null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a search criteria!");
            alert.show();
        }
        //Else loop through table name if matches search box
        else if(combo.getValue().toString().matches("Name")  && searchBox.getText().length()>1) {
            for(int cnt=0;cnt<tableData.size();cnt++) {
                if(tableData.get(cnt).getName().toLowerCase().contains(searchBox.getText().toLowerCase())) {
                    list.add(tableData.get(cnt));
                }
            }
            //Show Items
            tableView.setItems(list);
        }
            //Else loop through table serial Numbers if matches search box
        else if(combo.getValue().toString().matches("Serial Number") && searchBox.getText().length()>1) {
            for(int cnt=0;cnt<tableData.size();cnt++) {
                if(tableData.get(cnt).getSerialNumber().toLowerCase().contains(searchBox.getText().toLowerCase())) {
                    list.add(tableData.get(cnt));
                }
            }
            //Show Items
            tableView.setItems(list);
        }
        //else show Items
        else {
            tableView.setItems(tableData);
        }

    }

    @FXML
    public void onSave(ActionEvent actionEvent) throws IOException {

        String inputFile = "";
        FileWriter fileWriter = new FileWriter(inputFile);

        //Loop through table
        for(int cnt=0;cnt<tableData.size();cnt++) {

            //for each loop write data down to file
            Product product = tableData.get(cnt);
            fileWriter.write(product.getSerialNumber() + "\t" + product.getName() + "\t" + product.getPrice() + "\n");
        }
        fileWriter.close();
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Records saved successfully!");
        alert.show();

        }
    public void exportFile(ActionEvent actionEvent) throws IOException {
        //If file type isnt select Display error message
        if(formatCombo.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a file format!");
            alert.show();
        }
        //else Open Window
        else {
            FileChooser fileOpener = new FileChooser();
            //Set Title
            fileOpener.setTitle("Select Database File");
            File file = fileOpener.showOpenDialog(filterArea.getScene().getWindow());

            String fileName = file.getName();
            //get file type
            String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1, file.getName().length());
            //if .txt write file looping through the file
            if(fileExtension.matches("txt")) {
                FileWriter objWriter = new FileWriter(file.getAbsolutePath());

                //write file data to table
                for (Product product : tableData) {
                    objWriter.write(product.getSerialNumber() + "\t" + product.getName() + "\t" + product.getPrice() + "\n");
                }
                objWriter.close();

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "File exported successfully!");
                alert.show();
            }
            //else if file type is html
            else if(fileExtension.matches("html")) {
                //Parse code with html template tags
                String template = "<html><head><title>Inventory Manager Database</title></head><body>$body</body></html>";
                //table data Serial number, name, and price using th
                StringBuilder dataTable= new StringBuilder("<table><tr><th>Serial Number</th><th>Product Name</th><th>Price</th></tr>");

                for (Product product : tableData) {
                    dataTable.append("<tr><td>").append(product.getSerialNumber()).append("</td><td>").append(product.getName()).append("</td><td>").append(product.getPrice()).append("</td></tr>");

                }
                dataTable.append("</table>");
                //replace template with data
                template = template.replace("$body", dataTable.toString());
                //write down data to file
                FileWriter objWriter = new FileWriter(file.getAbsolutePath());
                objWriter.write(template);
                objWriter.close();

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "File exported successfully!");
                alert.show();

            }
            else {
                //else file is not valid Display error message
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid file type!");
                alert.show();
            }
        }

    }


}

