package com.bestbuyweek15.serviceinfo;

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
public class ServiceCURDSteps extends TestBase {
    static String name ="LondisDelivery" +TestUtils.getRandomValue();
    static int servicesID;

    @Steps
    ServiceSteps servicesSteps;

    @Title("this will create a new services")
    @Test
    public void test001(){
        ValidatableResponse response = servicesSteps.createServices(name);
        response.statusCode(201);

    }
    @Title("verify if services is created")
    @Test
    public void test002(){
        HashMap<String,Object> servicesMapData = servicesSteps.getServiceByname(name);
        Assert.assertThat(servicesMapData,hasValue(name));
        servicesID= (int) servicesMapData.get("id");
        System.out.println(servicesID);

    }

    @Title("update the services information")
    @Test
    public void test003(){
        name = name +"localarea";

        servicesSteps.updateServices(servicesID,name);
        HashMap<String,Object> serviceMap = servicesSteps.getServiceByname(name);
        Assert.assertThat(serviceMap,hasValue(name));
    }

    @Title("Delete service info by serviceID and verify its deleted")
    @Test
    public void test005(){
        servicesSteps.deleteServicesinfoBYID(servicesID).statusCode(200);
        servicesSteps.getservicesinfobyservicesID(servicesID).statusCode(404);
    }




}
