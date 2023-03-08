package com.bestbuyweek15.cucumber.steps;

import com.bestbuyweek15.categoriesinfo.CategoriesStep;
import com.bestbuyweek15.utils.TestUtils;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.ValidatableResponse;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

public class CategoriesStepdefs {

    static ValidatableResponse response;
    static String name = null;
    static String id = null;

    @Steps
    CategoriesStep categoriesStep;
    @When("^user sends Get request to list endpoints$")
    public void userSendsGetRequestToListEndpoints() {
        response = categoriesStep.getAllCategoriesinfo();
    }


    @Then("^user should get status code (\\d+) in return$")
    public void userShouldGetStatusCodeInReturn(int statusCode) {
        response.statusCode(statusCode);
    }

    @When("^I create a new categories with name \"([^\"]*)\" id \"([^\"]*)\"$")
    public void iCreateANewCategoriesWithNameIde(String _name, String _id)  {
    name = TestUtils.getRandomValue() +_name;
    id = TestUtils.getRandomValue()+_id;
    response = categoriesStep.createCategories(name,id);
    }

    @Then("^I verify that the categories with name$")
    public void iVerifyThatTheCategoriesWithName() {
        response.statusCode(201);
        HashMap<String, Object>map = categoriesStep.getCategorieByname(name);
        Assert.assertThat(map,hasValue(name));

    }

    @When("^I updated the categories with \"([^\"]*)\"$")
    public void iUpdatedTheCategoriesWith(String _name)  {
        name = TestUtils.getRandomValue()+_name;
        response = categoriesStep.updateCategories(name,id);
    }

    @When("^I deleted the categories by id$")
    public void iDeletedTheCategoriesById() {
        response = categoriesStep.deleteCategoriesinfoBYID(id);
    }

    @Then("^I should verify that the categories is deleted$")
    public void iShouldVerifyThatTheCategoriesIsDeleted() {
        response = categoriesStep.getCategoriesinfobyCategoriesID(id);
    }




    }

