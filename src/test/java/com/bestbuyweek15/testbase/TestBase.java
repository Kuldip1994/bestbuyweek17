package com.bestbuyweek15.testbase;


import com.bestbuyweek15.utils.PropertyReader;
import io.restassured.RestAssured;
import org.junit.BeforeClass;

/**
 * Created by bhavesh
 */
public class TestBase {
    public static PropertyReader propertyReader;

    @BeforeClass
    public static void init() {
        propertyReader = PropertyReader.getInstance();
        RestAssured.baseURI = propertyReader.getProperty("baseUrl");
        RestAssured.port = Integer.parseInt(propertyReader.getProperty("port"));
        //RestAssured.basePath = Path.STUDENT; // dont need for bestBuy
        //http://localhost:8080/student
    }

}
