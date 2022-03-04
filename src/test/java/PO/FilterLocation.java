package PO;

import org.testng.Assert;
import org.testng.annotations.Test;

import commons.GlobalContants;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class FilterLocation {
	Response response;
	String poNumber = "POTestAPI";
	
	@Test
	public void Test_01_Filter_Location() {
		RestAssured.baseURI = GlobalContants.BASE_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Authorization", "Bearer " + GlobalContants.TOKEN).header("x-tenant-id", "melinda").header("Content-Type", "application/json");
		response = request.get("/api/integration-svc/purchase-orders?sortBy=createdAt&sort=default&status=unarchived&locationId=14874116142&page=1&limit=10");
		Assert.assertEquals(response.jsonPath().get("data.data[0].poNumber"), poNumber);
	}
}
