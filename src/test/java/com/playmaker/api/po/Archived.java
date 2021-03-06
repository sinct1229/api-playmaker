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

public class Archived {
	Response response;
	
	@Test
	public void Test_01_Archieved_PO() {
		RestAssured.baseURI = GlobalContants.BASE_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Authorization", "Bearer " + GlobalContants.TOKEN).header("x-tenant-id", "melinda").header("Content-Type", "application/json");
		response = request.body("{\"status\":\"" + "archived" + "\"}").put("/api/integration-svc/purchase-orders/" + GlobalContants.PO);
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().get("data.task"), "update");
		
		response = request.get("/api/integration-svc/purchase-orders?sortBy=createdAt&sort=default&status=archived&page=1&limit=10");
		List <String> listPoNumber = new ArrayList<String>();
		List<Map<String, String>> poNumbers =  response.jsonPath().get("data.data.poNumber");
		for(int i =0; i< poNumbers.size(); i++) {
			listPoNumber.add(response.jsonPath().get("data.data[" + i + "].poNumber").toString());
		}
		System.out.println(listPoNumber);
		Assert.assertTrue(listPoNumber.contains(GlobalContants.PO));
		
	}
	
	@Test
	public void Test_02_Unarchieved_PO() {
		RestAssured.baseURI = GlobalContants.BASE_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Authorization", "Bearer " + GlobalContants.TOKEN).header("x-tenant-id", "melinda").header("Content-Type", "application/json");
		response = request.body("{\"status\":\"" + "unarchived" + "\"}").put("/api/integration-svc/purchase-orders/" + GlobalContants.PO);
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().get("data.task"), "update");
		
		response = request.get(
				"/api/integration-svc/purchase-orders?sortBy=createdAt&sort=default&status=unarchived&page=1&limit=10");
		List <String> listPoNumber = new ArrayList<String>();
		List<Map<String, String>> poNumbers =  response.jsonPath().get("data.data.poNumber");
		for(int i =0; i< poNumbers.size(); i++) {
			listPoNumber.add(response.jsonPath().get("data.data[" + i + "].poNumber").toString());
		}
		Assert.assertTrue(listPoNumber.contains(GlobalContants.PO));
		
	}
}
