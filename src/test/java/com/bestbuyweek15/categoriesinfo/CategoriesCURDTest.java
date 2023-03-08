package com.bestbuyweek15.categoriesinfo;

import com.bestbuyweek15.constants.CategoriesEndPoints;
import com.bestbuyweek15.model.CategoriesPojo;
import com.bestbuyweek15.testbase.TestBase;
import com.bestbuyweek15.utils.PropertyReader;
import com.bestbuyweek15.utils.TestUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.xml.ws.Endpoint;
import java.util.HashMap;

import static io.restassured.http.Cookie.PATH;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class CategoriesCURDTest extends TestBase {


    static String name="Tommy"+ TestUtils.getRandomValue();
    static String id ="abc12345786"+TestUtils.getRandomValue();
    static Object categoriesid;

    @Title("This will create a new categories")
    @Test
    public void test001(){
        CategoriesPojo pojo = new CategoriesPojo();
        pojo.setName(name);
        pojo.setId(id);

        SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(pojo)
                .when()
                .post(CategoriesEndPoints.CREATE_NEW_CATEGORIES)
                .then().log().all().statusCode(201);

    }
    @Title("Verify if categories was categories")
    @Test
    public void test002(){


        String part1 ="data.findAll{it.name='";
        String part2 = "'}.get(0)";

        HashMap<String,?>categorieMapData = SerenityRest.given()
                .log().all()
                .when()
                .get(CategoriesEndPoints.GET_ALL_CATEGORIES)
                .then().statusCode(200).extract().path(part1+name+part2);
        Assert.assertThat(categorieMapData,hasValue(name));
       categoriesid = categorieMapData.get("id");
        System.out.println(categoriesid);
    }
    @Title("Update the name and id and verify the updated information")
    @Test
    public void test003(){
        name = name +"ben";
        //categoriesid = +"k";

        CategoriesPojo pojo = new CategoriesPojo();
        pojo.setName(name);
        pojo.setId(id);

        SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .pathParam("categoriesID",categoriesid)
                .body(pojo)
                .when()
                .patch(CategoriesEndPoints.UPDATE_SINGLE_CATEGORIES_BY_ID)
                .then().statusCode(200);
        String part1 ="data.findAll{it.name='";
        String part2 ="'}.get(0)";

        HashMap<String,?>categorieMapData = SerenityRest.given()
                .when()
                .get(CategoriesEndPoints.GET_ALL_CATEGORIES)
                .then().statusCode(200).extract().path(part1 + name + part2);
        Assert.assertThat(categorieMapData,hasValue(name));
//        categoriesid = categorieMapData.get("id");
//        System.out.println(categoriesid);


    }
    @Title("Delete record")
    @Test
    public void test004(){
        SerenityRest.given()
                .pathParam("categoriesID",categoriesid)
                .when()
                .delete(CategoriesEndPoints.DELETE_CATEGORIES_BY_ID)
                .then().log().all().statusCode(200);

        SerenityRest.given()
                .contentType(ContentType.JSON)
                .pathParam("categoriesID",categoriesid)
                .when()
                .get(CategoriesEndPoints.GET_SINGLE_CATEGORIES_BY_ID)
                .then().log().all().statusCode(404);
    }


}
