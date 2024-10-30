package pro.vasudevan.restapi.automation.dummyjson.misc;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

/*
Created By: Vasudevan Sampath

 Common.java has common methods for REST API operations
 */
public class Common {
    public static RequestSpecification conformToRequestSpec() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();
    }

    public static ResponseSpecification conformToResponseSpec(int statusCode, String contentType) {
        return new ResponseSpecBuilder()
                .expectContentType(contentType)
                .expectStatusCode(statusCode)
                .build();
    }
}