package baseline;

public class Product {
    //Initialize private name, serialNumber, and price
    private String name;
    private String serialNumber;
    private double price;

    //Make a Constructor for name serialNumber price
    Product(String tName, String tSerial, double tPrice) {
        name = tName;
        serialNumber = tSerial;
        price = tPrice;
    }
        //Create getName method and return name
        public String getName() {
            return name;
        }
        //Set Name
        public void setName(String name) {
            this.name = name;
        }

        //Create getSerialNumber method and return serialNumber
        public String getSerialNumber() {
        return serialNumber;
        }

        //Create getPrice method and return price
        public double getPrice() {
            return price;
        }

        //Set Price
        public void setPrice(double price) {
            this.price = price;
        }

}


