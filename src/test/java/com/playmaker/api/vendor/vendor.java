package com.playmaker.api.vendor;

import org.testng.Assert;
import org.testng.annotations.Test;

import commons.GlobalContants;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class vendor {
	Response response;
	String jsonString;
	String createVendor = "6Sin";
	String updateVendor = "new_Sin";
	String vendorID = "";
	
	@Test
	public void Test_01_Create_Vendor() {
		RestAssured.baseURI = GlobalContants.BASE_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Authorization", "Bearer " + GlobalContants.TOKEN).header("x-tenant-id", "melinda").header("Content-Type", "application/json");
		response = request.body("{\"name\":\"" + createVendor+"\"}").post("/api/integration-svc/vendors");
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().get("data.task"), "create");
		vendorID = response.jsonPath().get("data.data.id");
		}
	
	
	@Test
	public void Test_02_Update_Vendor() {
		RestAssured.baseURI = GlobalContants.BASE_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Authorization", "Bearer " + GlobalContants.TOKEN).header("x-tenant-id", "melinda").header("Content-Type", "application/json");
		response = request.body("{\"name\":\"" + updateVendor+"\"}").put("/api/integration-svc/vendors/"+ vendorID);
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().get("data.task"), "update");
	}
	
	@Test
	public void Test_03_Get_Info_Of_A_Vendor() {
		RestAssured.baseURI = GlobalContants.BASE_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Authorization", "Bearer " + GlobalContants.TOKEN).header("x-tenant-id", "melinda").header("Content-Type", "application/json");
		response = request.get("/api/integration-svc/vendors/"+ vendorID);
		Assert.assertEquals(response.jsonPath().get("data.id"), vendorID);
		Assert.assertEquals(response.jsonPath().get("data.name"), updateVendor);
		
	}
	
	@Test
	public void Test_04_Delete_Vendor() {
		RestAssured.baseURI = GlobalContants.BASE_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Authorization", "Bearer " + GlobalContants.TOKEN).header("x-tenant-id", "melinda").header("Content-Type", "application/json");
		response = request.body("{\"name\":\"" + updateVendor+"\"}").delete("/api/integration-svc/vendors/"+ vendorID);
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().get("data.task"), "deleted");
	}

}
