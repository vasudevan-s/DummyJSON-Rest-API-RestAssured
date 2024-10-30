package pro.vasudevan.restapi.automation.dummyjson.tests.carts;

import io.restassured.RestAssured;
import org.javatuples.Pair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import pro.vasudevan.restapi.automation.dummyjson.apibase.APISuperBase;
import pro.vasudevan.restapi.automation.dummyjson.apibase.TestBase;
import pro.vasudevan.restapi.automation.dummyjson.misc.Common;
import pro.vasudevan.restapi.automation.dummyjson.pojos.User;

import static org.hamcrest.Matchers.*;

/*
Created By: Vasudevan Sampath

APITests.java has all the API test methods
*/
public class APITests extends TestBase {

    @Test(priority = 0)
    public void addNewCartTest(ITestContext testContext) {
        User user = APISuperBase.getRandomBasicUserData();
        Pair pair = APISuperBase.getAuthorizedUser(user);
        JSONObject shoppingCart = new JSONObject();
        JSONArray shoppingCartArray = new JSONArray();
        shoppingCart.put("userId", user.id());
            JSONObject productDetails = new JSONObject();
            productDetails.put("id", 144);
            productDetails.put("quantity", 4);
        shoppingCartArray.add(productDetails);
            productDetails = new JSONObject();
            productDetails.put("id", 98);
            productDetails.put("quantity", 1);
        shoppingCartArray.add(productDetails);
        shoppingCart.put("products", shoppingCartArray);
        int cartId = RestAssured
        .given()
        .header("Authorization", "Bearer " + pair.getValue0())
        .spec(Common.conformToRequestSpec())
        .body(shoppingCart.toJSONString())
        .when()
        .post("/carts/add")
        .then()
        .spec(Common.conformToResponseSpec(201, "application/json"))
                .body("products.id", hasItems(98, 144))
                .extract().jsonPath().get("id");
        testContext.setAttribute("cartId", cartId);
    }
}
