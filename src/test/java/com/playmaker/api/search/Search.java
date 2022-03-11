package com.playmaker.api.search;

import org.testng.Assert;
import org.testng.annotations.Test;

import commons.GlobalContants;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Search {
	Response response;
	String jsonString;
	String fullSKU = "N5081GWTCZ";
	String aPartSKU = "N5081";
	String uppercaseSKU = "N5081GWTCZ";
	String lowsercaseSKU = "n5081gwtcz";
	String nodata = "sinnnnnn";
	
	String fullName = "Your Royal Highness Necklace";
	String aPartName = "Your Royal";
	String uppercaseName = "YOUR ROYAL HIGHNESS NECKLACE";
	String lowercaseName = "your royal highness necklace";
	
	
	@Test
	public void Test_01_Search_By_Full_SKU() {
		RestAssured.baseURI = GlobalContants.BASE_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Authorization", "Bearer " + GlobalContants.TOKEN).header("x-tenant-id", "melinda").header("Content-Type", "application/json");
		response = request.get("/api/integration-svc/purchase-orders?sortBy=createdAt&sort=default&status=unarchived&product=" +fullSKU+"&page=1&limit=10");
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().get("data.data[0].poNumber"), GlobalContants.PO);
	}
	
	@Test
	public void Test_02_Search_By_A_Part_Of_SKU() {
		RestAssured.baseURI = GlobalContants.BASE_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Authorization", "Bearer " + GlobalContants.TOKEN).header("x-tenant-id", "melinda").header("Content-Type", "application/json");
		response = request.get("/api/integration-svc/purchase-orders?sortBy=createdAt&sort=default&status=unarchived&product=" +aPartSKU+"&page=1&limit=10");
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().get("data.data[0].poNumber"), GlobalContants.PO);
	}
	
	@Test
	public void Test_03_Search_By_Uppercase_SKU() {
		RestAssured.baseURI = GlobalContants.BASE_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Authorization", "Bearer " + GlobalContants.TOKEN).header("x-tenant-id", "melinda").header("Content-Type", "application/json");
		response = request.get("/api/integration-svc/purchase-orders?sortBy=createdAt&sort=default&status=unarchived&product=" +aPartSKU+"&page=1&limit=10");
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().get("data.data[0].poNumber"), GlobalContants.PO);
	}
	
	@Test
	public void Test_04_Search_By_Lowercase_SKU() {
		RestAssured.baseURI = GlobalContants.BASE_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Authorization", "Bearer " + GlobalContants.TOKEN).header("x-tenant-id", "melinda").header("Content-Type", "application/json");
		response = request.get("/api/integration-svc/purchase-orders?sortBy=createdAt&sort=default&status=unarchived&product=" +uppercaseSKU+"&page=1&limit=10");
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().get("data.data[0].poNumber"), GlobalContants.PO);
	}
	
	@Test
	public void Test_05_Search_By_No_Data_SKU() {
		RestAssured.baseURI = GlobalContants.BASE_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Authorization", "Bearer " + GlobalContants.TOKEN).header("x-tenant-id", "melinda").header("Content-Type", "application/json");
		response = request.get("/api/integration-svc/purchase-orders?sortBy=createdAt&sort=default&status=unarchived&product=" +nodata+"&page=1&limit=10");
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	
	@Test
	public void Test_06_Search_By_Full_SKU() {
		RestAssured.baseURI = GlobalContants.BASE_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Authorization", "Bearer " + GlobalContants.TOKEN).header("x-tenant-id", "melinda").header("Content-Type", "application/json");
		response = request.get("/api/integration-svc/purchase-orders?sortBy=createdAt&sort=default&status=unarchived&product=" +fullName+"&page=1&limit=10");
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().get("data.data[0].poNumber"), GlobalContants.PO);
	}
	
	@Test
	public void Test_07_Search_By_A_Part_Name() {
		RestAssured.baseURI = GlobalContants.BASE_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Authorization", "Bearer " + GlobalContants.TOKEN).header("x-tenant-id", "melinda").header("Content-Type", "application/json");
		response = request.get("/api/integration-svc/purchase-orders?sortBy=createdAt&sort=default&status=unarchived&product=" +aPartName+"&page=1&limit=10");
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().get("data.data[0].poNumber"), GlobalContants.PO);
	}
	
	@Test
	public void Test_08_Search_By_Uppercase_Name() {
		RestAssured.baseURI = GlobalContants.BASE_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Authorization", "Bearer " + GlobalContants.TOKEN).header("x-tenant-id", "melinda").header("Content-Type", "application/json");
		response = request.get("/api/integration-svc/purchase-orders?sortBy=createdAt&sort=default&status=unarchived&product=" +uppercaseName+"&page=1&limit=10");
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().get("data.data[0].poNumber"), GlobalContants.PO);
	}
	
	@Test
	public void Test_09_Search_By_Lowercase_Name() {
		RestAssured.baseURI = GlobalContants.BASE_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Authorization", "Bearer " + GlobalContants.TOKEN).header("x-tenant-id", "melinda").header("Content-Type", "application/json");
		response = request.get("/api/integration-svc/purchase-orders?sortBy=createdAt&sort=default&status=unarchived&product=" +lowercaseName+"&page=1&limit=10");
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().get("data.data[0].poNumber"), GlobalContants.PO);
	}
}
