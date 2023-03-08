package com.bestbuyweek15.cucumber.steps;

import com.bestbuyweek15.productinfo.ProductSteps;
import com.bestbuyweek15.utils.TestUtils;
import cucumber.api.PendingException;
import cucumber.api.java.ca.I;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.ValidatableResponse;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;
import org.junit.Test;
import org.yecht.Data;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

public class ProductStepdef {

    static ValidatableResponse response;
    static  String name = null;
    static String model = null;

    static int id;

    @Steps
    ProductSteps productSteps;

    @When("^User sends a Get request to list endpoints$")
    public void userSendsAGetRequestToListEndpoints() {
        response = productSteps.getAllProductinfo();
    }

    @Then("^user should get status code (\\d+)$")
    public void userShouldGetStatusCode(int statusCode) {
        response.statusCode(statusCode);

    }

    @When("^I create a new product by providing information \"([^\"]*)\"  \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" <\"([^\"]*)\" \"([^\"]*)\"$")
    public void iCreateANewProductByProvidingInformation(String _name, String type, String _model, String Description, String Url, String Image, String Manufacturer, String Upc)  {
        name = TestUtils.getRandomValue()+_name;
        model = TestUtils.getRandomValue()+_model;
        response = productSteps.createProduct(name,type,model,Description,Url,Image,Manufacturer,Upc);

    }

    @Then("^I verify that the product with name is created$")
    public void iVerifyThatTheProductWithNameIsCreated() {
        response.statusCode(201);
        HashMap<String, Object> map2 = productSteps.getProductInforByName(name);
        Assert.assertThat(map2,hasValue(name));
    }

    @When("^new product is updated with \"([^\"]*)\"$")
    public void newProductIsUpdatedWith(String _name)  {
        name = TestUtils.getRandomValue()+_name;
        response= productSteps.update(id,name);
    }

    @When("^I have deleted product by id$")
    public void iHaveDeletedProductById() {
        response=productSteps.deleteProductinfoBYID(id);
    }

    @Then("^I verify that product is deleted$")
    public void iVerifyThatProductIsDeleted() {
        response = productSteps.getProductinfobyProductID(id);
    }
}
