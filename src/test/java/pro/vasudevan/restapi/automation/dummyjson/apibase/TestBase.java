package pro.vasudevan.restapi.automation.dummyjson.apibase;

import io.restassured.RestAssured;
import org.json.simple.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import pro.vasudevan.restapi.automation.dummyjson.misc.Common;

import java.util.List;

/*
Created By: Vasudevan Sampath

TestBase.java is the base class for REST API. Extends from APISuperBase
*/
public class TestBase extends APISuperBase {

    @BeforeSuite(alwaysRun = true)
    @Parameters("APIBaseURL")
    public void beforeSuite(String APIBaseURL) {
        RestAssured.baseURI = APIBaseURL;
    }

   // @BeforeTest(alwaysRun = true)
    @Deprecated
    public void beforeTest(ITestContext testContext) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", "emilys");
        jsonObject.put("password", "emilyspass");
        List<String> tokens = RestAssured
                .given()
                .spec(Common.conformToRequestSpec())
                .body(jsonObject.toJSONString())
                .when()
                .post("/auth/login")
                .then()
                .spec(Common.conformToResponseSpec(200, "application/json"))
                .extract().response().path("accessToken", "refreshToken");
        testContext.setAttribute("Authorization", "Bearer " + tokens.getFirst());
    }
}
