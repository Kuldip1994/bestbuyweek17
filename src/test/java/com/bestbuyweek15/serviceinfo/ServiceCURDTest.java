package com.bestbuyweek15.serviceinfo;

import com.bestbuyweek15.constants.ServiceEndPoint;
import com.bestbuyweek15.model.ServicePojo;
import com.bestbuyweek15.testbase.TestBase;
import com.bestbuyweek15.utils.TestUtils;
import io.restassured.http.ContentType;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.yecht.Data;

import java.util.HashMap;


import static org.hamcrest.Matchers.hasValue;
@RunWith(SerenityRunner.class)

public class ServiceCURDTest extends TestBase {
    static String name = "pluming" + TestUtils.getRandomValue();
    static Object servicesId;

    @Title("This will create new Service")
    @Test
    public void test001(){
        ServicePojo pojo = new ServicePojo();
        pojo.setName(name);

        SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(pojo)
                .when()
                .post(ServiceEndPoint.CREATE_NEW_SERVICES)
                .then().log().all().statusCode(201);
    }
    @Title("Verify if services was Created")
    @Test
    public void test002(){
        String part1 ="data.findAll{it.name='";
        String part2 ="'}.get(0)";

        HashMap<String,?>ServicesMapData = SerenityRest.given()
                .log().all()
                .when()
                .get(ServiceEndPoint.GET_ALL_SERVICES)
                .then().statusCode(200).extract().path(part1+name+part2);
        Assert.assertThat(ServicesMapData,hasValue(name));
        servicesId = ServicesMapData.get("id");
        System.out.println(servicesId);
    }
    @Title("update the name and verify the information")
    @Test
    public void test003(){
        name = name +"ben;";
        ServicePojo pojo = new ServicePojo();
        pojo.setName(name);

        SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .pathParam("servicesID",servicesId)
                .body(pojo)
                .when()
                .patch(ServiceEndPoint.UPDATE_SERVICES_BY_ID)
                .then().statusCode(200);
        String part1 ="data.findAll{it.name='";
        String part2 ="'}.get(0)";

        HashMap<String,?>  ServicesMapData = SerenityRest.given()
                .when()
                .get(ServiceEndPoint.GET_ALL_SERVICES)
                .then().statusCode(200).extract().path(part1 + name +part2);
        Assert.assertThat(ServicesMapData,hasValue(name));
    }

    @Title("Delete record")
    @Test
    public void test004(){
        SerenityRest.given()
                .pathParam("servicesID",servicesId)
                .when()
                .delete(ServiceEndPoint.DELETE_SERVICES_BY_ID)
                .then().log().all().statusCode(200);

        SerenityRest.given()
                .contentType(ContentType.JSON)
                .pathParam("servicesID",servicesId)
                .when()
                .get(ServiceEndPoint.GET_SINGLE_SERVICES_BY_ID)
                .then().log().all().statusCode(404);
    }
}
