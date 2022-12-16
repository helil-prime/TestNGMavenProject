package petStoreApiTests;

import java.io.File;
import static org.hamcrest.Matchers.*;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PetStoreApiTests {
	
	int catID;
	int petID;
	
	@BeforeTest
	public void setup() {
		RestAssured.baseURI = "https://petstore.swagger.io/v2";
	}
	

	@Test(dependsOnMethods = "postAPet")
	public void getPetById() {

		RestAssured.given().accept(ContentType.JSON)
		.when().get("/pet/2323226")
		.then().statusCode(200);
	}

	@Test
	public void findPetByStatus() {

		RestAssured.given().accept(ContentType.JSON).contentType("application/json").param("status", "pending")
		.when().get("/pet/findByStatus")
		.then().statusCode(200)
		.and().contentType("application/json");
	}

	@Test (dependsOnMethods = {"postACat", "updateCat"})
	public void getById() {

		Response myResponse = RestAssured.given().accept(ContentType.JSON).when()
				.get("/pet/232323");

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
		
		String catStatus = myResponse.body().jsonPath().get("status");
		Assert.assertEquals(catStatus, "pending");

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
				.body(catRequestBody).when().post("/pet");
		
		myResponse.then().statusCode(200).and().contentType("application/json");
		myResponse.prettyPrint();
		
		catID = myResponse.jsonPath().get("id");
	}
	
	
	// update the cat status to pending
	@Test (dependsOnMethods = "postACat")
	public void updateCat() {
		String catRequestBody = "{\n" + "    \"id\": 232323,\n" + "    \"category\": {\n" + "        \"id\": 21,\n"
				+ "        \"name\": \"cat\"\n" + "    },\n" + "    \"name\": \"Ember\",\n" + "    \"photoUrls\": [\n"
				+ "        \"string\"\n" + "    ],\n" + "    \"tags\": [\n" + "        {\n"
				+ "            \"id\": 18,\n" + "            \"name\": \"persian\"\n" + "        },\n" + "        {\n"
				+ "            \"id\": 2,\n" + "            \"name\": \"Anatolian\"\n" + "        }\n" + "    ],\n"
				+ "    \"status\": \"pending\"\n" + "}";
		
		Response catResponse = RestAssured
		.given().accept(ContentType.JSON).contentType("application/json").body(catRequestBody)
		.when().put("/pet");
		
		catResponse.then().statusCode(200).and().contentType("application/json");
		Assert.assertEquals(catResponse.body().jsonPath().get("status"), "pending");
		
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
				.body(requestBody).when().post("/pet");

		myResponse.then().statusCode(200).and().contentType("application/json");

		myResponse.prettyPrint();
		
		// storing the pet id in a global variable (instance variable)
		petID = myResponse.jsonPath().get("id");
	}
	
	
	public void deleteThePet() {
		Response deleteResponse = RestAssured
		.given().accept(ContentType.JSON).contentType("application/json")
		.when().delete("/pet/" + petID);
		
		deleteResponse.then().statusCode(200).contentType("application/json");
		Assert.assertEquals(deleteResponse.body().jsonPath().get("message"), String.valueOf(petID));
		
	}

	public void deleteTheCat() {
		Response deleteResponse = RestAssured
		.given().accept(ContentType.JSON).contentType("application/json")
		.when().delete("/pet/" + catID);
		
		deleteResponse.then().statusCode(200).contentType("application/json");
		Assert.assertEquals(deleteResponse.body().jsonPath().get("message"), String.valueOf(catID));
		
	}
	
	
	// create a cat with request body in json file - example
	
	@Test
	public void createCatWithJsonFile() {
		
		File catRequestBodyFile = new File("./src/test/resources/JsonTestData/createCat.json");
		
		Response myResponse = 
				RestAssured
				.given().accept(ContentType.JSON).contentType("application/json")
				.body(catRequestBodyFile)
				.when().post("/pet");
		
		myResponse.then().statusCode(200).and().contentType("application/json");
		myResponse.prettyPrint();
		
		catID = myResponse.jsonPath().get("id");
	}
	
	// RestAssured chain validation
	@Test
	public void chainValidation() {
        File catRequestBodyFile = new File("./src/test/resources/JsonTestData/createCat.json");
		
		Response myResponse = 
				RestAssured
				.given().accept(ContentType.JSON).contentType("application/json")
				.body(catRequestBodyFile)
				.when().post("/pet");
		
		myResponse
		.then().assertThat().statusCode(200)
		.and().assertThat().contentType("application/json")
		.and().assertThat().body("id", equalTo(345555))
		.and().assertThat().body("category.id", equalTo(21))
		.and().assertThat().body("category.name", equalTo("cat"))
		.and().assertThat().body("name", equalTo("Mimi"))
		.and().assertThat().body("tags[0].id", equalTo(18))
		.and().assertThat().body("tags[0].name", equalTo("persian"))
		.and().assertThat().body("tags[1].id", equalTo(2))
		.and().assertThat().body("tags[1].name", equalTo("Anatolian"))
		.and().assertThat().body("status", equalTo("available"));
		
		myResponse.prettyPrint();
		catID = myResponse.jsonPath().get("id");
	}
	
	@AfterTest
	public void cleanup() {
		deleteTheCat();
		deleteThePet();
	}
	
	// negative test cases 
	@Test
	public void invalidIdUpdateCat() {
		String catRequestBody = "{\n" + "    \"id\": '232323',\n" + "    \"category\": {\n" + "        \"id\": 21,\n"
				+ "        \"name\": \"cat\"\n" + "    },\n" + "    \"name\": \"Ember\",\n" + "    \"photoUrls\": [\n"
				+ "        \"string\"\n" + "    ],\n" + "    \"tags\": [\n" + "        {\n"
				+ "            \"id\": 18,\n" + "            \"name\": \"persian\"\n" + "        },\n" + "        {\n"
				+ "            \"id\": 2,\n" + "            \"name\": \"Anatolian\"\n" + "        }\n" + "    ],\n"
				+ "    \"status\": \"pending\"\n" + "}";
		
		Response catResponse = RestAssured
		.given().accept(ContentType.JSON).contentType("application/json").body(catRequestBody)
		.when().put("/pet");
		
		catResponse.then().statusCode(400).and().contentType("application/json");
		catResponse.prettyPrint();
		Assert.assertEquals(catResponse.body().jsonPath().get("message"), "bad input");
		
	}

}
