package PO;

import org.testng.Assert;
import org.testng.annotations.Test;

import commons.GlobalContants;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PO {
	Response response;
	String jsonString;
	
	@Test
	public void Test_01_Get_PO_List() {
		RestAssured.baseURI = GlobalContants.BASE_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Authorization", "Bearer " + GlobalContants.TOKEN).header("x-tenant-id", "melinda").header("Content-Type", "application/json");
		response = request.get("/api/integration-svc/purchase-orders?sortBy=createdAt&sort=default&status=unarchived&page=1&limit=10");
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	
	
}
