package com.bestbuyweek15.storeinfo;

import com.bestbuyweek15.constants.ProductEndPoints;
import com.bestbuyweek15.constants.StoreEndPoints;
import com.bestbuyweek15.model.ProductPojo;
import com.bestbuyweek15.model.StorePojo;
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
public class StoreCURDTest extends TestBase {

    static String name = "Neasden" + TestUtils.getRandomValue();
    static String city ="london" + TestUtils.getRandomValue();

    static String zip = "NW" +TestUtils.getRandomValue();
    static Object storesid;

    @Title("This will create new Store")
    @Test
    public void test001(){
        StorePojo pojo = new StorePojo();
        pojo.setName(name);
        pojo.setType("Anyu");
        pojo.setAddress("154 neasden lane");
        pojo.setAddress2("55sidedoor");
        pojo.setCity(city);
        pojo.setState("middlesex");
        pojo.setZip(zip);
//        pojo.setLat(11127);
//        pojo.setLng(44455);
        pojo.setHours("mon:10-5");

        SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(pojo)
                .when()
                .post(StoreEndPoints.CREATE_NEW_STORES)
                .then().log().all().statusCode(201);

    }
    @Title("verify if product was created")
    @Test
    public void test002() {
        String part1 = "data.findAll{it.name='";
        String part2 = "'}.get(0)";

        HashMap<String, ?> StoresMapData = SerenityRest.given()
                .log().all()
                .when()
                .get(StoreEndPoints.GET_ALL_STORES)
                .then().statusCode(200).extract().path(part1 + name + part2);
        Assert.assertThat(StoresMapData, hasValue(name));
        storesid = StoresMapData.get("id");
        System.out.println(storesid);

    }
    @Title("update the name and verify the information")
    @Test
    public void test003() {
        name = name + "Willsden";
        ProductPojo pojo = new ProductPojo();
        pojo.setName(name);

        SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .pathParam("storesID", storesid)
                .body(pojo)
                .when()
                .patch(StoreEndPoints.UPDATE_STORES_BY_ID)
                .then().statusCode(200);
        String part1 = "data.findAll{it.name='";
        String part2 = "'}.get(0)";

        HashMap<String, ?> StoresMapData = SerenityRest.given()
                .when()
                .get(ProductEndPoints.GET_ALL_PRODUCTS)
                .then().statusCode(200).extract().path(part1 + name + part2);
        Assert.assertThat(StoresMapData, hasValue(name));

    }
    @Title("Delete record")
    @Test
    public void test004(){
        SerenityRest.given()
                .pathParam("storesID",storesid)
                .when()
                .delete(StoreEndPoints.DELETE_STORES_BY_ID)
                .then().log().all().statusCode(200);

        SerenityRest.given()
                .pathParam("storesID",storesid)
                .when()
                .get(StoreEndPoints.GET_SINGLE_STORES_BY_ID)
                .then().log().all().statusCode(404);
    }


    }
