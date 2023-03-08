package com.bestbuyweek15.serviceinfo;

import com.bestbuyweek15.constants.CategoriesEndPoints;
import com.bestbuyweek15.constants.ServiceEndPoint;
import com.bestbuyweek15.model.ServicePojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class ServiceSteps {

    @Step("getting all information :{0}")
    public ValidatableResponse getAllServiceinfo() {
        return SerenityRest.given()
                .when()
                .get(ServiceEndPoint.GET_ALL_SERVICES)
                .then();
    }

    @Step("creating service with name:{0}")
    public ValidatableResponse createServices(String name) {
        ServicePojo servicePojo = new ServicePojo();

        servicePojo.setName(name);
        return SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(servicePojo)
                .when()
                .post(ServiceEndPoint.CREATE_NEW_SERVICES)
                .then().log().all().statusCode(201);
    }

    @Step("getting services info by name:{0}")
    public HashMap<String, Object> getServiceByname(String name) {
        String part1 = "data.findAll{it.name='";
        String part2 = "'}.get(0)";
        return SerenityRest.given()
                .log().all()
                .when()
                .get(ServiceEndPoint.GET_ALL_SERVICES)
                .then()
                .statusCode(200)
                .extract().path(part1 + name + part2);
    }

    @Step("update services information with servicesID:{0},name:{1}")
    public ValidatableResponse updateServices(int servicesid, String name) {
        ServicePojo servicePojo = new ServicePojo();
        servicePojo.setName(name);

        return SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .pathParam("servicesID", servicesid)
                .body(servicePojo)
                .when()
                .put(ServiceEndPoint.UPDATE_SERVICES_BY_ID)
                .then();
    }

    @Step("deleteing services information with servicesId:{0}")
    public ValidatableResponse deleteServicesinfoBYID(int servicesId) {
        return SerenityRest.given()
                .pathParam("servicesID", servicesId)
                .when()
                .delete(ServiceEndPoint.DELETE_SERVICES_BY_ID)
                .then();

    }

    @Step("getting services info By servicesId:{0}")
    public ValidatableResponse getservicesinfobyservicesID(int servicesId) {
        return SerenityRest.given()
                .pathParam("servicesID", servicesId)
                .when()
                .get(ServiceEndPoint.GET_SINGLE_SERVICES_BY_ID)
                .then();


    }
}
