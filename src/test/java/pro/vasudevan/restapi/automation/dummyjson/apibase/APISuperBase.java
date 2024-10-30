package pro.vasudevan.restapi.automation.dummyjson.apibase;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.javatuples.Pair;
import org.json.simple.JSONObject;
import pro.vasudevan.restapi.automation.dummyjson.misc.Common;
import pro.vasudevan.restapi.automation.dummyjson.pojos.User;

import java.util.*;

/*
Created By: Vasudevan Sampath

APISuperBase.java has common methods for REST API
*/
public class APISuperBase {

    public static User getRandomBasicUserData() {
        LinkedHashMap<String, ArrayList> listLinkedHashMap =
        RestAssured
                .given()
                .spec(Common.conformToRequestSpec())
                .when()
                .get("/users")
                .then()
                .extract().response().jsonPath().get();
        var users = listLinkedHashMap.get("users");
        var user = (LinkedHashMap<String, ?>) users.get(new Random().nextInt(users.size()));
        return new User((int)user.get("id"), user.get("username").toString(), user.get("firstName").toString(), user.get("lastName").toString(), user.get("password").toString());
    }

    public static Pair<String, String> getAuthorizedUser(User user) {
        JSONObject userObject = new JSONObject();
        userObject.put("username", user.userName());
        userObject.put("password", user.password());
        JsonPath jsonPath = RestAssured
                .given()
                .spec(Common.conformToRequestSpec())
                .body(userObject.toJSONString())
                .when()
                .post("/auth/login")
                .then()
                .spec(Common.conformToResponseSpec(200, "application/json"))
                .extract().response().jsonPath();
        return new Pair<>(jsonPath.getString("accessToken"), jsonPath.getString("refreshToken"));
    }
}
