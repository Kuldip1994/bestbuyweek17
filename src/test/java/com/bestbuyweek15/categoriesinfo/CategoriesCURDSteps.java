package com.bestbuyweek15.categoriesinfo;

import com.bestbuyweek15.testbase.TestBase;
import com.bestbuyweek15.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasValue;

import java.util.HashMap;



@RunWith(SerenityRunner.class)
public class CategoriesCURDSteps extends TestBase {
    static String name = "TVandSpeackers" + TestUtils.getRandomValue();
    static String id = "TVS1" +TestUtils.getRandomValue();

    static String categoriesID;

    @Steps
    CategoriesStep categoriesSteps;
    @Title("This will create a new categories")
    @Test
    public void test001(){
        ValidatableResponse response = categoriesSteps.createCategories(name,id);
        response.statusCode(201);
    }

    @Title("verify if categories is created")
    @Test
    public void test002(){
        HashMap<String,Object> categorieMapData = categoriesSteps.getCategorieByname(name);
        Assert.assertThat(categorieMapData,hasValue(name));
        categoriesID= (String) categorieMapData.get("id");
        System.out.println(categoriesID);
    }
    @Title("update the categories information")
    @Test
    public void test003(){
        name = name +"speakers";
//        id = id +"abid";

        categoriesSteps.updateCategories(categoriesID,name);
        HashMap<String,Object> categorieMap = categoriesSteps.getCategorieByname(name);
        Assert.assertThat(categorieMap,hasValue(name));

    }

    @Title("Delete categori info by CategorioesID and verify its deleted")
    @Test
    public void test004(){
        categoriesSteps.deleteCategoriesinfoBYID(categoriesID).statusCode(200);
        categoriesSteps.getCategoriesinfobyCategoriesID(categoriesID).statusCode(404);
    }

}
