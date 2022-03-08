package com.playmaker.api.po;

import org.testng.Assert;
import org.testng.annotations.Test;

import commons.GlobalContants;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Archived {
	Response response;
	String poNumber = "POTestAPI";
	
	//@Test
	public void Test_01_Archieved_PO() {
		RestAssured.baseURI = GlobalContants.BASE_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Authorization", "Bearer " + GlobalContants.TOKEN).header("x-tenant-id", "melinda").header("Content-Type", "application/json");
		response = request.body("{\"status\":\"" + "archived" + "\"}").put("/api/integration-svc/purchase-orders/POTestAPI");
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().get("data.task"), "update");
		response = request.get("https://demo-ai.getplaymaker.com/api/integration-svc/purchase-orders?sortBy=createdAt&sort=default&status=archived&page=1&limit=10");
		Assert.assertEquals(response.jsonPath().get("data.data[0].poNumber"), poNumber);
		
	}
	
	//@Test
	public void Test_02_Unarchieved_PO() {
		RestAssured.baseURI = GlobalContants.BASE_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Authorization", "Bearer " + GlobalContants.TOKEN).header("x-tenant-id", "melinda").header("Content-Type", "application/json");
		response = request.body("{\"status\":\"" + "unarchived" + "\"}").put("/api/integration-svc/purchase-orders/POTestAPI");
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().get("data.task"), "update");
		response = request.get("https://demo-ai.getplaymaker.com/api/integration-svc/purchase-orders?sortBy=createdAt&sort=default&status=unarchived&page=1&limit=10");
		Assert.assertEquals(response.jsonPath().get("data.data[0].poNumber"), poNumber);
		
	}
}
