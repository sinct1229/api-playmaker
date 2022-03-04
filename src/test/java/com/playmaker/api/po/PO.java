package com.playmaker.api.po;

import org.testng.Assert;
import org.testng.annotations.Test;

import commons.GlobalContants;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PO {
	Response response;
	String jsonString;
	String poNumber = "cbi10";
	String vendorID = "ef1140cd-76f0-4442-bfa3-9aca4572b531";
	String note = "test";
	
	@Test
	public void Test_01_Get_PO_List() {
		RestAssured.baseURI = GlobalContants.BASE_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Authorization", "Bearer " + GlobalContants.TOKEN).header("x-tenant-id", "melinda").header("Content-Type", "application/json");
		response = request.get("/api/integration-svc/purchase-orders?sortBy=createdAt&sort=default&status=unarchived&page=1&limit=10");
		Assert.assertEquals(response.getStatusCode(), 200);
	}

	@Test
	public void Test_02_Update_Vendor_PO() {
		RestAssured.baseURI = GlobalContants.BASE_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Authorization", "Bearer " + GlobalContants.TOKEN).header("x-tenant-id", "melinda").header("Content-Type", "application/json");
		response = request.body("{\"vendorId\": \"" + vendorID + "\"}").put("/api/integration-svc/purchase-orders/" + poNumber);
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().get("data.task"), "update");
	}
	
	@Test
	public void Test_03_Update_Note_PO() {
		RestAssured.baseURI = GlobalContants.BASE_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Authorization", "Bearer " + GlobalContants.TOKEN).header("x-tenant-id", "melinda").header("Content-Type", "application/json");
		response = request.body("{\"note\": \"" + note + "\"}").put("/api/integration-svc/purchase-orders/"+ poNumber);
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().get("data.task"), "update");
	}
	
	//@Test
	public void Test_04_Delete_PO() {
		RestAssured.baseURI = GlobalContants.BASE_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Authorization", "Bearer " + GlobalContants.TOKEN).header("x-tenant-id", "melinda").header("Content-Type", "application/json");
		response = request.delete("/api/integration-svc/purchase-orders/" + poNumber);
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().get("data.task"), "deleted");
	}
	
	
}
