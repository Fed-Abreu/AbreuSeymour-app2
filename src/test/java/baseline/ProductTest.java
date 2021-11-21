package baseline;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    @Test
    void getName(){
        //Create new product and set "Name" and serial and price
        Product p = new Product("Name", "", 0);
        //Assert Equal getName and "Name"
        assertEquals("Name",p.getName());

    }
    @Test
    void setName(){
        //Create new product and set "" and serial and price
        Product p = new Product("", "", 0);

        //setName "Name"
        p.setName("Name");
        //Assert Equal getName and "Name"
        assertEquals("Name",p.getName());

    }
    @Test
    void getSerialNumber(){
        //Create new product and set name and "A-###-###-###" and price
        Product p = new Product("", "A-333-333-333", 0);

        //Assert Equal getSerialNumber and "A-###-###-###"
        assertEquals("A-333-333-333",p.getSerialNumber());


    }
    @Test
    void getPrice(){
        //Create new product and set name and serial and "3"
        Product p = new Product("", "", 3);

        //Assert Equal getPrice and "3"
        assertEquals(3,p.getPrice());

    }
    @Test
    void setPrice(){
        //Create new product and set name and serial and ""
        Product p = new Product("", "", 0);

        //setPrice "3"
        p.setPrice(3);

        //Assert Equal getPrice and "3"
        assertEquals(3,p.getPrice());
    }


}