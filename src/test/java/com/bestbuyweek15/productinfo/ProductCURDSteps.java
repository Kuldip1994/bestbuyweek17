package com.bestbuyweek15.productinfo;

import com.bestbuyweek15.testbase.TestBase;
import com.bestbuyweek15.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.yecht.Data;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class ProductCURDSteps extends TestBase {
    static String name = "Powerbank" + TestUtils.getRandomValue();
    static String type = "battery" +TestUtils.getRandomValue();
    static String model = "double"+TestUtils.getRandomValue();

    static String Description = "Powerfull" +TestUtils.getRandomValue();
    static String Url = "www.apple.com" +TestUtils.getRandomValue();
    static String Image = "mmw"+TestUtils.getRandomValue();
    static String Manufacturer = "doublebanks"+TestUtils.getRandomValue();
    static String Upc ="noone"+TestUtils.getRandomValue();

    static int productsId;

    @Steps
    ProductSteps productSteps;

    @Title("this will create a new student")
    @Test
    public void test001(){
        ValidatableResponse response = productSteps.createProduct(name,type,model,Description,Url,Image,Manufacturer,Upc);
        response.statusCode(201);
    }

    @Title("verify if product is created")
    @Test
    public void test002(){
        HashMap<String,Object> productMapData = productSteps.getProductInforByName(name);
        Assert.assertThat(productMapData,hasValue(name));
       productsId =(int) productMapData.get("id");
        System.out.println(productsId);
    }

    @Title("update the product information")
    @Test
    public void test003(){
        name = name + "triplebank";
        type = type +" doublebattery";

        productSteps.update(productsId,name);
        HashMap<String,Object> productMap = productSteps.getProductInforByName(name);
        Assert.assertThat(productMap,hasValue(name));

    }
    @Title("delete product info by ProductId and verify deleted")
    @Test
    public void test004(){
        productSteps.deleteProductinfoBYID(productsId).statusCode(200);
        productSteps.getProductinfobyProductID(productsId).statusCode(404);
    }


}
