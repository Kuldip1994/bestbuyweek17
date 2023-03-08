package com.bestbuyweek15.productinfo;

import com.bestbuyweek15.constants.ProductEndPoints;
import com.bestbuyweek15.model.ProductPojo;
import com.bestbuyweek15.testbase.TestBase;
import com.bestbuyweek15.utils.TestUtils;
import io.restassured.http.ContentType;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;


@RunWith(SerenityRunner.class)
public class ProductCRUDTest extends TestBase {
    static String name = "Powerbank" + TestUtils.getRandomValue();
    static String type = "battery" +TestUtils.getRandomValue();
    static String model = "double"+TestUtils.getRandomValue();

    static Object productsid;

    @Title("This will create new categories")
    @Test
    public void test001(){
        ProductPojo pojo = new ProductPojo();
        pojo.setName(name);
        pojo.setType(type);
//        pojo.setPrice(15);
//        pojo.setShipping(2);
        pojo.setUpc("amyf");
        pojo.setDescription("batterytype");
        pojo.setManufacturer("top");
        pojo.setModel(model);
        pojo.setUrl("www.top.com");
        pojo.setImage("img");



        SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(pojo)
                .when()
                .post(ProductEndPoints.CREATE_NEW_PRODUCTS)
                .then().log().all().statusCode(201);

    }
    @Title("verify if product was created")
    @Test
    public void test002(){
        String part1 = "data.findAll{it.name='";
        String part2 ="'}.get(0)";

        HashMap<String, ?> ProductsMapData = SerenityRest.given()
                .log().all()
                .when()
                .get(ProductEndPoints.GET_ALL_PRODUCTS)
                .then().statusCode(200).extract().path(part1+name+part2);
        Assert.assertThat(ProductsMapData,hasValue(name));
        productsid = ProductsMapData.get("id");
        System.out.println(productsid);
    }
    @Title("update the name and verify the information")
    @Test
    public void test003(){
        name = name +"ExtraLife";
        ProductPojo pojo = new ProductPojo();
        pojo.setName(name);

        SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .pathParam("productsID",productsid)
                .body(pojo)
                .when()
                .patch(ProductEndPoints.UPDATE_PRODUCTS_BY_ID)
                .then().statusCode(200);
        String part1 ="data.findAll{it.name='";
        String part2 ="'}.get(0)";

        HashMap<String,?> ProductsMapData = SerenityRest.given()
                .when()
                .get(ProductEndPoints.GET_ALL_PRODUCTS)
                .then().statusCode(200).extract().path(part1 +name +part2);
        Assert.assertThat(ProductsMapData,hasValue(name));

    }
    @Title("Delete record")
    @Test
    public void test004(){
        SerenityRest.given()
                .pathParam("productsID",productsid)
                .when()
                .delete(ProductEndPoints.DELETE_PRODUCTS_BY_ID)
                .then().log().all().statusCode(200);

        SerenityRest.given()
                .contentType(ContentType.JSON)
                .pathParam("productsID",productsid)
                .when()
                .get(ProductEndPoints.GET_SINGLE_PRODUCTS_BY_ID)
                .then().log().all().statusCode(404);
    }

}
