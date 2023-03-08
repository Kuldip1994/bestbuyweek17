package com.bestbuyweek15.cucumber.steps;

import com.bestbuyweek15.storeinfo.StoreSteps;
import com.bestbuyweek15.utils.TestUtils;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.ValidatableResponse;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

public class StoreStepdefs {

    static ValidatableResponse response;
    static String name = null;

    static String type = null;

    static int storeid;

    @Steps
    StoreSteps storeSteps;

    @When("^user send a Get request to list endpoints$")
    public void userSendAGetRequestToListEndpoints() {
        response =  storeSteps.getAllStoreInfo();
    }
    @Then("^user should get status code (\\d+)$")
    public void userShouldGetStatusCode(int statusCode) {
        response.statusCode(statusCode);
    }


    @When("^creating new store with new \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void creatingNewStoreWithNew(String _name, String _type, String address, String address2, String city, String state,String zip, String hours )  {
        name = TestUtils.getRandomValue()+_name;
        type = TestUtils.getRandomValue()+_type;
        response=storeSteps.createStore(name,type,address,address2,city,state,zip,hours);

    }

    @Then("^I verify that store with name is created$")
    public void iVerifyThatStoreWithNameIsCreated() {
        response.statusCode(201);
        HashMap<String, Object> map1 = storeSteps.getStoreInfoByName(name);
        Assert.assertThat(map1,hasValue(name));
    }

    @When("^New store is updated with new \"([^\"]*)\"$")
    public void newStoreIsUpdatedWithNew(String _name) {
        name=TestUtils.getRandomValue()+_name;
        response= storeSteps.updateStores(storeid,name);

    }

    @When("^I have deleted store by id$")
    public void iHaveDeletedStoreById() {
        response= storeSteps.deleteStoresinfoBYID(storeid);
    }

    @Then("^I verify that store is deleted$")
    public void iVerifyThatStoreIsDeleted() {
        response= storeSteps.getStoresinfobyStoresID(storeid);
    }

    }
