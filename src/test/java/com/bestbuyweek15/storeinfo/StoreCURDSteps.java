package com.bestbuyweek15.storeinfo;

import com.bestbuyweek15.testbase.TestBase;
import com.bestbuyweek15.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;


import com.bestbuyweek15.testbase.TestBase;
import com.bestbuyweek15.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

   @RunWith(SerenityRunner.class)
    public class StoreCURDSteps extends TestBase {
        static String name = "Neasden"+ TestUtils.getRandomValue();
        static String type = "foodnwine"+TestUtils.getRandomValue();
        static String address = "140neasder" +TestUtils.getRandomValue();
        static String address2 = "northwest"+TestUtils.getRandomValue();
        static String city = "london"+ TestUtils.getRandomValue();
        static String state = "middlese" +TestUtils.getRandomValue();
        static String zip = "N1" +TestUtils.getRandomValue();
        static String hours = "Mon: 10-5" +TestUtils.getRandomValue();

        static int storesId;

        @Steps
        StoreSteps storeSteps;

        @Title("This will create a new student")
        @Test
        public void test001(){
            ValidatableResponse response = storeSteps.createStore(name,type,address,address2,city,state,zip,hours);
            response.statusCode(201);
        }
        @Title("verify if product is created ")
        @Test
        public void test002(){
            HashMap<String,Object> storeMapData = storeSteps.getStoreInfoByName(name);
            Assert.assertThat(storeMapData,hasValue(name));
            storesId = (int) storeMapData.get("id");
            System.out.println(storesId);
        }

        @Title("update the product information")
        @Test
        public void test003(){
            name = name+"Willsden";

            storeSteps.updateStores(storesId,name);
            HashMap<String,Object> storeMap = storeSteps.getStoreInfoByName(name);
            Assert.assertThat(storeMap,hasValue(name));

        }

        @Title("delete store infor by storeId and verify deleted")
        @Test
        public void test004(){
            storeSteps.deleteStoresinfoBYID(storesId).statusCode(200);
            storeSteps.getStoresinfobyStoresID(storesId).statusCode(404);
        }

    }


