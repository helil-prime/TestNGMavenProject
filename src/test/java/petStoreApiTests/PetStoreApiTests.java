package petStoreApiTests;

import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PetStoreApiTests {

	@Test(dependsOnMethods = "postAPet")
	public void getPetById() {

		RestAssured.given().accept(ContentType.JSON).when().get("https://petstore.swagger.io/v2/pet/2323226").then()
				.statusCode(200);

	}

	@Test
	public void findPetByStatus() {

		RestAssured.given().accept(ContentType.JSON).contentType("application/json").param("status", "pending").when()
				.get("https://petstore.swagger.io/v2/pet/findByStatus").then().statusCode(200)
				.contentType("application/json");
	}

	@Test (dependsOnMethods = "postACat")
	public void getById() {

		Response myResponse = RestAssured.given().accept(ContentType.JSON).when()
				.get("https://petstore.swagger.io/v2/pet/232323");

		myResponse.prettyPrint();
		// verifying the status code.
		myResponse.then().assertThat().statusCode(200).and().contentType("application/json");

		String petName = myResponse.path("name");
		System.out.println("Pet name is: " + petName);
		Assert.assertEquals(petName, "Ember");

		int petId = myResponse.body().path("id");
		System.out.println("Pet id is: " + petId);
		Assert.assertEquals(petId, 232323);

		int tagsId = myResponse.path("tags[0].id");
		System.out.println("Pet tags id is: " + tagsId);
		Assert.assertEquals(tagsId, 18);

		// how to access the tags name from the second object
		String tags2Name = myResponse.path("tags[1].name");
		System.out.println("Pet second tag name  is: " + tags2Name);
		Assert.assertEquals(tags2Name, "Anatolian");

		// using jsonpath function

		String categoryName = myResponse.jsonPath().get("category.name");
		System.out.println("Pet category name  is: " + categoryName);
		Assert.assertEquals(categoryName, "cat");

	}

	@Test
	public void postACat() {
		String catRequestBody = "{\n" + "    \"id\": 232323,\n" + "    \"category\": {\n" + "        \"id\": 21,\n"
				+ "        \"name\": \"cat\"\n" + "    },\n" + "    \"name\": \"Ember\",\n" + "    \"photoUrls\": [\n"
				+ "        \"string\"\n" + "    ],\n" + "    \"tags\": [\n" + "        {\n"
				+ "            \"id\": 18,\n" + "            \"name\": \"persian\"\n" + "        },\n" + "        {\n"
				+ "            \"id\": 2,\n" + "            \"name\": \"Anatolian\"\n" + "        }\n" + "    ],\n"
				+ "    \"status\": \"available\"\n" + "}";

		Response myResponse = RestAssured.given().accept(ContentType.JSON).contentType("application/json")
				.body(catRequestBody).when().post("https://petstore.swagger.io/v2/pet");

		myResponse.then().statusCode(200).and().contentType("application/json");

		myResponse.prettyPrint();
	}

	@Test
	public void postAPet() {

		String requestBody = "{\n" + "    \"id\": 2323226,\n" + "    \"category\": {\n" + "        \"id\": 21,\n"
				+ "        \"name\": \"dog\"\n" + "    },\n" + "    \"name\": \"Toby\",\n" + "    \"photoUrls\": [\n"
				+ "        \"string\"\n" + "    ],\n" + "    \"tags\": [\n" + "        {\n"
				+ "            \"id\": 29,\n" + "            \"name\": \"Sheperd\"\n" + "        },\n" + "        {\n"
				+ "            \"id\": 31,\n" + "            \"name\": \"Husky\"\n" + "        }\n" + "    ],\n"
				+ "    \"status\": \"available\"\n" + "}";

		Response myResponse = RestAssured.given().accept(ContentType.JSON).contentType("application/json")
				.body(requestBody).when().post("https://petstore.swagger.io/v2/pet");

		myResponse.then().statusCode(200).and().contentType("application/json");

		myResponse.prettyPrint();

	}

}
