package com.bestbuyweek15.productinfo;

import com.bestbuyweek15.constants.ProductEndPoints;
import com.bestbuyweek15.model.ProductPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;

import java.util.HashMap;

public class ProductSteps {

    @Step("getting all the informaiton :{0}")
    public ValidatableResponse getAllProductinfo(){
        return SerenityRest.given()
                .when()
                .get(ProductEndPoints.GET_ALL_PRODUCTS)
                .then();
    }
    @Step("creating product with name :{0},type:{1},model:{2},Description:{3},Url:{4},Image:{5},Manufacturer:{6},Upc:{7}")
    public ValidatableResponse createProduct(String name,String type, String model,String Description, String Url, String Image,String Manufacturer, String Upc){
        ProductPojo productPojo = new ProductPojo();
        productPojo.setName(name);
        productPojo.setType(type);
        productPojo.setModel(model);
        productPojo.setDescription(Description);
        productPojo.setUrl(Url);
        productPojo.setImage(Image);
        productPojo.setManufacturer(Manufacturer);
        productPojo.setUpc(Upc);
        return SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(productPojo)
                .when()
                .post(ProductEndPoints.CREATE_NEW_PRODUCTS)
                .then().log().all().statusCode(201);
    }
    @Step("getting product info by name:{0}")
    public HashMap<String, Object>getProductInforByName(String name){
        String part1 ="data.findAll{it.name='";
        String part2 ="'}.get(0)";
        return  SerenityRest.given()
                .log().all()
                .when()
                .get(ProductEndPoints.GET_ALL_PRODUCTS)
                .then()
                .statusCode(200)
                .extract().path(part1 + name +part2);
    }
    @Step("update product infor with productId:{0},name:{1}")
    public ValidatableResponse update (int productId,String name){
        ProductPojo productPojo = new ProductPojo();
        productPojo.setName(name);

        return SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .pathParam("productsID",productId)
                .body(productPojo)
                .when()
                .put(ProductEndPoints.UPDATE_PRODUCTS_BY_ID)
                .then();

    }
    @Step("deleteing student information with productId:{0}")
    public ValidatableResponse deleteProductinfoBYID(int productId){
        return SerenityRest.given()
                .pathParam("productsID",productId)
                .when()
                .get(ProductEndPoints.DELETE_PRODUCTS_BY_ID)
                .then();

    }
    @Step("getting product info by productID:{0}")
    public ValidatableResponse getProductinfobyProductID(int productId){
        return SerenityRest.given()
                .pathParam("productsID",productId)
                .when()
                .get(ProductEndPoints.GET_SINGLE_PRODUCTS_BY_ID)
                .then();
    }

}
