package com.playmaker.api.po;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import commons.GlobalContants;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PO {
	Response response;
	String jsonString;
	String poNumber = "123";
	String vendorId = "539a2980-6ca7-45a0-84b6-622c4861d2fe";
	String locationId = "14874116142";
	String etaAt = "1648796400000";

	String vendorUpdate = "38796fc3-4e35-4ac6-a893-2e8298025faa";
	String LocationUpdate = "36906696750";
	String dateUpdate = "1651302000000";
	String note = "test";

	//@Test
	public void Test_01_Get_PO_List() {
		RestAssured.baseURI = GlobalContants.BASE_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Authorization", "Bearer " + GlobalContants.TOKEN).header("x-tenant-id", "melinda")
				.header("Content-Type", "application/json");
		response = request.get(
				"/api/integration-svc/purchase-orders?sortBy=createdAt&sort=default&status=unarchived&page=1&limit=10");
		Assert.assertEquals(response.getStatusCode(), 200);
	}

	@Test
	public void Test_02_Create_PO() {
		RestAssured.baseURI = GlobalContants.BASE_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Authorization", "Bearer " + GlobalContants.TOKEN).header("x-tenant-id", "melinda")
				.header("Content-Type", "application/json");
		response = request
				.body("{\"poNumber\": \"" + poNumber + "\", \"vendorId\": \"" + vendorId + "\", \"locationId\":"+ Long.parseLong(locationId) + ", \"etaAt\": " + Long.parseLong(etaAt) + "}")
				.post("/api/integration-svc/purchase-orders");
		Assert.assertEquals(response.getStatusCode(), 200);
		
		
		
		response = request.get(
				"/api/integration-svc/purchase-orders?sortBy=createdAt&sort=default&status=unarchived&page=1&limit=10");
		List <String> listPoNumber = new ArrayList<String>();
		List<Map<String, String>> poNumbers =  response.jsonPath().get("data.data.poNumber");
		for(int i =0; i< poNumbers.size(); i++) {
			listPoNumber.add(response.jsonPath().get("data.data[" + i + "].poNumber").toString());
		}
		Assert.assertTrue(listPoNumber.contains(poNumber));
	}

	//@Test
	public void Test_03_Update_Vendor_PO() {
		RestAssured.baseURI = GlobalContants.BASE_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Authorization", "Bearer " + GlobalContants.TOKEN).header("x-tenant-id", "melinda")
				.header("Content-Type", "application/json");
		response = request.body("{\"vendorId\": \"" + vendorId + "\"}")
				.put("/api/integration-svc/purchase-orders/" + poNumber);
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().get("data.task"), "update");
	}

	//@Test
	public void Test_04_Update_Expected_Date() {
		RestAssured.baseURI = GlobalContants.BASE_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Authorization", "Bearer " + GlobalContants.TOKEN).header("x-tenant-id", "melinda")
				.header("Content-Type", "application/json");
		response = request.body("{\"etaAt\": " + Long.parseLong(etaAt) + "}")
				.put("/api/integration-svc/purchase-orders/" + poNumber);
		Assert.assertEquals(response.getStatusCode(), 200);
	}

	@Test
	public void Test_05_Update_Location() {
		RestAssured.baseURI = GlobalContants.BASE_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Authorization", "Bearer " + GlobalContants.TOKEN).header("x-tenant-id", "melinda")
				.header("Content-Type", "application/json");
		response = request.body("{\"locationId\": " + Long.parseLong(locationId) + "}")
				.put("/api/integration-svc/purchase-orders/" + poNumber);
		Assert.assertEquals(response.getStatusCode(), 200);
	}

	 //@Test
	public void Test_06_Update_Note_PO() {
		RestAssured.baseURI = GlobalContants.BASE_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Authorization", "Bearer " + GlobalContants.TOKEN).header("x-tenant-id", "melinda")
				.header("Content-Type", "application/json");
		response = request.body("{\"note\": \"" + note + "\"}").put("/api/integration-svc/purchase-orders/" + poNumber);
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().get("data.task"), "update");
	}

	// @Test
	public void Test_06_Delete_PO() {
		RestAssured.baseURI = GlobalContants.BASE_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Authorization", "Bearer " + GlobalContants.TOKEN).header("x-tenant-id", "melinda")
				.header("Content-Type", "application/json");
		response = request.delete("/api/integration-svc/purchase-orders/" + poNumber);
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().get("data.task"), "deleted");
	}

}
