package com.bestbuyweek15.storeinfo;


import com.bestbuyweek15.constants.CategoriesEndPoints;
import com.bestbuyweek15.constants.StoreEndPoints;
import com.bestbuyweek15.model.StorePojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import org.yecht.Data;

import java.util.HashMap;


public class StoreSteps {

    @Step("getting all information :{0}")
    public ValidatableResponse getAllStoreInfo(){
        return SerenityRest.given()
                .when()
                .get(StoreEndPoints.GET_ALL_STORES)
                .then();
    }
    @Step("creating store with name:{0},type:{1},address:{2},address2:{3},city:{4},state:{5},zip:{6},hours:{7}")
    public ValidatableResponse createStore(String name, String type, String address, String address2, String city, String state, String zip , String hours){
        StorePojo storePojo = new StorePojo();
        storePojo.setName(name);
        storePojo.setType(type);
        storePojo.setAddress(address);
        storePojo.setAddress2(address2);
        storePojo.setCity(city);
        storePojo.setState(state);
        storePojo.setZip(zip);
        storePojo.setHours(hours);
        return SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(storePojo)
                .when()
                .post(StoreEndPoints.CREATE_NEW_STORES)
                .then().log().all().statusCode(201);
    }

    @Step("getting categories info by name:{0}")
    public HashMap<String, Object> getStoreInfoByName(String name){
        String part1 ="data.findAll{it.name='";
        String part2 ="'}.get(0)";

        return SerenityRest.given()
                .log().all()
                .when()
                .get(StoreEndPoints.GET_ALL_STORES)
                .then()
                .statusCode(200)
                .extract().path(part1 + name +part2);

    }
    @Step("update categoires information with storesID:{0},name:{1}")
    public ValidatableResponse updateStores (int storesid, String name){
        StorePojo storePojo = new StorePojo();
        storePojo.setName(name);

        return SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .pathParam("storesID",storesid)
                .body(storePojo)
                .when()
                .put(StoreEndPoints.UPDATE_STORES_BY_ID)
                .then();

    }
    @Step("deleting stores information with storesId:{0}")
    public ValidatableResponse deleteStoresinfoBYID(int storesId){
        return SerenityRest.given()
                .pathParam("storesID",storesId)
                .when()
                .delete(StoreEndPoints.DELETE_STORES_BY_ID)
                .then();
    }
    @Step("getting categories infor By categoriesID:{0}")
    public ValidatableResponse getStoresinfobyStoresID(int storesId){
        return  SerenityRest.given()
                .pathParam("storesID", storesId)
                .when()
                .get(StoreEndPoints.GET_SINGLE_STORES_BY_ID)
                .then();
    }







}
