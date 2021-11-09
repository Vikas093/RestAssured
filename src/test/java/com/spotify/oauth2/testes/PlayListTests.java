package com.spotify.oauth2.testes;

import org.testng.annotations.Test;

import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.DataLoader;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Link;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import com.spotify.oauth2.api.StatusCode;
import com.spotify.oauth2.api.applicationApi.PlaylistApi;
import com.spotify.oauth2.pojo.Error;
import static com.spotify.oauth2.api.SpecBuilder.getRequestSpec;
import static com.spotify.oauth2.api.SpecBuilder.getResponseSpec;
import static com.spotify.oauth2.utils.FakerUtils.generateDescription;
import static com.spotify.oauth2.utils.FakerUtils.generateName;
@Epic("Spotify Oauth 2.0")
@Feature("Playlist API")
public class PlayListTests extends BaseTest {
    @Story("Create a playlist story")
	@Link(name = "allure", type = "mylink")
	@TmsLink("12345")
	@Issue("1234567")
	@Description("this is create playlist description")
	@Test(description = "should be able to create a playlist")
	public void ShouldBeAbleToCreateAPlaylist() {

		Playlist requestPlaylist = playlistBuilder(generateName(), generateDescription(), false);
		Response response = PlaylistApi.post(requestPlaylist);
		assertStatusCode(response.statusCode(), StatusCode.CODE_201);
		assertPlaylistEqual(response.as(Playlist.class), requestPlaylist);

	}
    @Story("Get a playlist story")
	@Link(name = "allure", type = "mylink")
	@TmsLink("12345")
	@Issue("1234567")
	@Description("this is get playlist description")
	@Test(description = "should be able get a playlist")
	public void ShouldBeAbleToGetAPlaylist() {
		Playlist requestPlaylist = playlistBuilder("Updated Playlist Name", "Updated playlist description", false);
		Response response = PlaylistApi.get(DataLoader.getInstance().getGetPlaylistId());
		assertStatusCode(response.statusCode(), StatusCode.CODE_200);
		assertPlaylistEqual(response.as(Playlist.class), requestPlaylist);

	}
    @Story("Update a playlist story")
	@Link(name = "allure", type = "mylink")
	@TmsLink("12345")
	@Issue("1234567")
	@Description("this is update playlist description")
	@Test(description = "should be able to update a playlist")
	public void ShouldBeAbleToUpdateAPlaylist() {
		Playlist requestPlaylist = playlistBuilder("Updated Playlist Name", "Updated playlist description", false);
		Response response = PlaylistApi.update(DataLoader.getInstance().getUpdatePlaylistId(), requestPlaylist);
		assertStatusCode(response.statusCode(), StatusCode.CODE_200);
	}
    @Story("Create a playlist story")
	@Link(name = "allure", type = "mylink")
	@TmsLink("12345")
	@Issue("1234567")
	@Description("this is create  playlist description without name")
	@Test(description = "should be able create a playlist without name")
	public void ShouldNotBeAbleToCreateAPlaylistWithOutName() {
		Playlist requestPlaylist = playlistBuilder("", generateDescription(), false);
		Response response = PlaylistApi.post(requestPlaylist);
		assertStatusCode(response.statusCode(), StatusCode.CODE_400);
		assertError(response.as(Error.class), StatusCode.CODE_400);

	}
    @Story("Create a playlist story")
	@Link(name = "allure", type = "mylink")
	@TmsLink("12345")
	@Issue("1234567")
	@Description("this is create playlist description with expires token")
	@Test(description = "should be able to create a playlist with expires token")
	public void ShouldNotBeAbleToCreateAPlaylistWithExpiredToken() {
		String invalidToke = "52782178";
		Playlist requestPlaylist = playlistBuilder(generateName(), generateDescription(), false);
		Response response = PlaylistApi.post(invalidToke, requestPlaylist);
		assertStatusCode(response.statusCode(), StatusCode.CODE_401);
		assertError(response.as(Error.class), StatusCode.CODE_401);
	}
    @Step("Use to set the request payload")
	public Playlist playlistBuilder(String name, String description, boolean _public) {
		return Playlist.builder().name(name).description(description)._public(_public).build();

	}
    @Step("Comparing actual and expected response body")
	public void assertPlaylistEqual(Playlist responsePlaylist, Playlist requestPlaylist) {
		assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
		assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
		assertThat(responsePlaylist.get_public(), equalTo(requestPlaylist.get_public()));
	}

	@Step("Comparing actual and expected status code")
	public void assertStatusCode(int actualStatusCode, StatusCode statusCode) {
		assertThat(actualStatusCode, equalTo(statusCode.code));
	}
	@Step("Comparing actual and expected response body with invalid request")
	public void assertError(Error responseErr, StatusCode statusCode) {
		assertThat(responseErr.getError().getStatus(), equalTo(statusCode.code));
		assertThat(responseErr.getError().getMessage(), equalTo(statusCode.msg));
	}
}
