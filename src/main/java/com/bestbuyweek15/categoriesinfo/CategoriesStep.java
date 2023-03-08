package com.bestbuyweek15.categoriesinfo;

import com.bestbuyweek15.constants.CategoriesEndPoints;
import com.bestbuyweek15.model.CategoriesPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;

import javax.xml.ws.Endpoint;
import java.util.HashMap;

public class CategoriesStep {

    @Step("getting all information :{0}")
    public ValidatableResponse getAllCategoriesinfo(){
        return SerenityRest.given()
                .when()
                .get(CategoriesEndPoints.GET_ALL_CATEGORIES)
                .then();
    }

    @Step("creating categories with name:{0}")
    public ValidatableResponse createCategories(String name, String id){

        CategoriesPojo categoriesPojo = new CategoriesPojo();
        categoriesPojo.setName(name);
        categoriesPojo.setId(id);
        return SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(categoriesPojo)
                .when()
                .post(CategoriesEndPoints.CREATE_NEW_CATEGORIES)
                .then().log().all().statusCode(201);
    }
    @Step("getting categories info by name:{0}")
    public HashMap<String,Object> getCategorieByname(String name){
        String part1 ="data.findAll{it.name='";
        String part2 ="'}.get(0)";
        return SerenityRest.given()
                .log().all()
                .when()
                .get(CategoriesEndPoints.GET_ALL_CATEGORIES)
                .then()
                .statusCode(200)
                .extract().path(part1 + name +part2);
    }

    @Step("update categories information with categoriesID:{0},name:{1}")
    public ValidatableResponse updateCategories(String categoriesid,String name){
        CategoriesPojo categoriesPojo = new CategoriesPojo();
        categoriesPojo.setName(name);
        //categoriesPojo.setId(id);

        return SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .pathParam("categoriesID",categoriesid)
                .body(categoriesPojo)
                .when()
                .put(CategoriesEndPoints.UPDATE_SINGLE_CATEGORIES_BY_ID)
                .then();
    }

    @Step("deleteing categories information with categoriesId:{0}")
    public ValidatableResponse deleteCategoriesinfoBYID(String categoriesId){
        return SerenityRest.given()
                .pathParam("categoriesID",categoriesId)
                .when()
                .delete(CategoriesEndPoints.DELETE_CATEGORIES_BY_ID)
                .then();
    }
    @Step("getting categories info By categorieId:{0}")
    public ValidatableResponse getCategoriesinfobyCategoriesID(String categoriesId){
        return SerenityRest.given()
                .pathParam("categoriesID",categoriesId)
                .when()
                .get(CategoriesEndPoints.GET_SINGLE_CATEGORIES_BY_ID)
                .then();
    }

}
