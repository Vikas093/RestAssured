package com.spotify.oauth2.api.applicationApi;

import com.spotify.oauth2.api.RestResource;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.ConfigLoader;

import static com.spotify.oauth2.api.SpecBuilder.getRequestSpec;
import static com.spotify.oauth2.api.SpecBuilder.getResponseSpec;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static com.spotify.oauth2.api.TokenManager.getToken;
import static com.spotify.oauth2.api.Route.USERS;
import static com.spotify.oauth2.api.Route.API;
import static com.spotify.oauth2.api.Route.BASE_PATH;
import static com.spotify.oauth2.api.Route.TOKEN;
import static com.spotify.oauth2.api.Route.PLAYLISTS;

import io.qameta.allure.Step;
import io.restassured.response.Response;

public class PlaylistApi {
    @Step("Post Request")
	public static Response post(Playlist requestPlaylist) {
		return RestResource.post(USERS+ConfigLoader.getInstance().getUser()+PLAYLISTS, getToken(), requestPlaylist);

	}
    @Step("Post Request")
	public static Response post(String token, Playlist requestPlaylist) {
		return RestResource.post(USERS+ConfigLoader.getInstance().getUser()+PLAYLISTS, token, requestPlaylist);

	}
    @Step("Get Request")
	public static Response get(String playlistId) {
		return RestResource.get(PLAYLISTS +"/" + playlistId, getToken());

	}
    @Step("Update Request")
	public static Response update(String playlistId, Playlist requestPlaylist) {
		return RestResource.update(PLAYLISTS +"/" + playlistId, getToken(), requestPlaylist);

	}
}
