package com.spotify.oauth2.api;

import static com.spotify.oauth2.api.SpecBuilder.getRequestSpec;
import static com.spotify.oauth2.api.SpecBuilder.getResponseSpec;
import static com.spotify.oauth2.api.SpecBuilder.getAccountRequestSpec;
import static io.restassured.RestAssured.given;
import java.util.HashMap;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static com.spotify.oauth2.api.Route.TOKEN;
import static com.spotify.oauth2.api.Route.API;

public class RestResource {

	public static Response post(String path, String token, Object requestPlaylist) {
		return given(getRequestSpec()).body(requestPlaylist).auth().oauth2(token).when()
				.post(path).then().spec(getResponseSpec()).extract().response();
	}

	public static Response postAccount(HashMap<String, String> formParams) {
		return given(getAccountRequestSpec()).formParams(formParams)
				.log().all().when().post(API+TOKEN).then().spec(getResponseSpec()).extract().response();
	}

	public static Response get(String path, String token) {
		return given(getRequestSpec()).auth().oauth2(token).when().get(path).then()
				.spec(getResponseSpec()).extract().response();
	}

	public static Response update(String path, String token, Object requestPlaylist) {
		return given(getRequestSpec()).auth().oauth2(token).body(requestPlaylist).when().put(path)
				.then().spec(getResponseSpec()).extract().response();
	}
}
