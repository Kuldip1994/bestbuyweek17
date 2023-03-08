package com.bestbuyweek15.cucumber.steps;

import com.bestbuyweek15.serviceinfo.ServiceSteps;
import com.bestbuyweek15.utils.TestUtils;
import cucumber.api.PendingException;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.ValidatableResponse;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;
import org.junit.Test;
import org.yecht.Data;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

public class ServicesStepdefs {

     ValidatableResponse response;
    static String name = null;
    static int servicesId;

    @Steps
    ServiceSteps serviceSteps;
    @When("^user sends a Get request to list endpoints$")
    public void userSendsAGetRequestToListEndpoints() {
        response = serviceSteps.getAllServiceinfo();
    }


    @Then("^user should get status code (\\d+)$")
    public void userShouldGetStatusCode(int statusCode) {
        response.statusCode(statusCode);
    }

    @When("^Created new service with new \"([^\"]*)\"$")
    public void createdNewServiceWithNew(String _name)   {
        name = TestUtils.getRandomValue()+_name;
        response = serviceSteps.createServices(name);

    }

    @Then("^I verify that service with name is created$")
    public void iVerifyThatServiceWithNameIsCreated() {
        response.statusCode(201);
        HashMap<String,Object> map = serviceSteps.getServiceByname(name);
        Assert.assertThat(map,hasValue(name));
    }

    @When("^New service is updated with new name \"([^\"]*)\"$")
    public void newServiceIsUpdatedWithNewName(String _name) {
        name = TestUtils.getRandomValue()+_name;
        response = serviceSteps.updateServices(servicesId, name);
    }

    @When("^I have deleted service by id$")
    public void iHaveDeletedServiceById() {
        response=serviceSteps.deleteServicesinfoBYID(servicesId);
    }

    @Then("^I verify that service is deleted$")
    public void iVerifyThatServiceIsDeleted() {
        response= serviceSteps.getservicesinfobyservicesID(servicesId);
    }
}
