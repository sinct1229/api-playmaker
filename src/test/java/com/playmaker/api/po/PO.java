package com.playmaker.api.po;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;

import commons.GlobalContants;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PO {
	Response response;
	String jsonString;
	String vendorId = "539a2980-6ca7-45a0-84b6-622c4861d2fe";
	String locationId = "14874116142";
	String etaAt = "1648796400000";

	String vendorUpdate = "38796fc3-4e35-4ac6-a893-2e8298025faa";
	String LocationUpdate = "36906696750";
	String dateUpdate = "1651302000000";
	String note = "test";

	String variantId = "39646984568878";
	String orderQuantity = "20";
	String recieved = "15";
	String idRecieved;

	public RequestSpecification init() {
		RestAssured.baseURI = GlobalContants.BASE_URL;
		RequestSpecification request = RestAssured.given();
		return request.header("Authorization", "Bearer " + GlobalContants.TOKEN).header("x-tenant-id", "melinda")
				.header("Content-Type", "application/json");
	}

	public List<String> getListPoNumber() {
		List<String> listPoNumber = new ArrayList<String>();
		List<Map<String, String>> poNumbers = response.jsonPath().get("data.data.poNumber");
		for (int i = 0; i < poNumbers.size(); i++) {
			listPoNumber.add(response.jsonPath().get("data.data[" + i + "].poNumber").toString());
		}
		return listPoNumber;
	}

	@Test
	public void Test_01_Get_PO_List() {
		response = init().get(
				"/api/integration-svc/purchase-orders?sortBy=createdAt&sort=default&status=unarchived&page=1&limit=10");
		Assert.assertEquals(response.getStatusCode(), 200);
	}

	@Test
	public void Test_02_Create_PO() {
		response = init().body("{\"poNumber\": \"" + GlobalContants.PO + "\", \"vendorId\": \"" + vendorId
				+ "\", \"locationId\":" + Long.parseLong(locationId) + ", \"etaAt\": " + Long.parseLong(etaAt) + "}")
				.post("/api/integration-svc/purchase-orders");
		Assert.assertEquals(response.getStatusCode(), 200);
		response = init().get(
				"/api/integration-svc/purchase-orders?sortBy=createdAt&sort=default&status=unarchived&page=1&limit=10");
		Assert.assertTrue(getListPoNumber().contains(GlobalContants.PO));
	}

	/*
	 * @Test public void Test_03_Update_Vendor_PO() { response =
	 * init().body("{\"vendorId\": \"" + vendorId + "\"}")
	 * .put("/api/integration-svc/purchase-orders/" + GlobalContants.PO);
	 * Assert.assertEquals(response.getStatusCode(), 200);
	 * Assert.assertEquals(response.jsonPath().get("data.task"), "update"); }
	 * 
	 * @Test public void Test_04_Update_Expected_Date() { response =
	 * init().body("{\"etaAt\": " + Long.parseLong(etaAt) + "}")
	 * .put("/api/integration-svc/purchase-orders/" + GlobalContants.PO);
	 * Assert.assertEquals(response.getStatusCode(), 200); }
	 * 
	 * @Test public void Test_05_Update_Location() { response =
	 * init().body("{\"locationId\": " + Long.parseLong(locationId) + "}")
	 * .put("/api/integration-svc/purchase-orders/" + GlobalContants.PO);
	 * Assert.assertEquals(response.getStatusCode(), 200); }
	 * 
	 * @Test public void Test_06_Update_Note_PO() { response =
	 * init().body("{\"note\": \"" + note + "\"}")
	 * .put("/api/integration-svc/purchase-orders/" + GlobalContants.PO);
	 * Assert.assertEquals(response.getStatusCode(), 200);
	 * Assert.assertEquals(response.jsonPath().get("data.task"), "update"); }
	 */

	@Test
	public void Test_07_Add_Product_To_PO() {
		response = init()
				.body("{\"poNumber\":\"" + GlobalContants.PO + "\",\"variantId\":\"" + variantId
						+ "\",\"orderQuantity\":" + Integer.parseInt(orderQuantity) + "}")
				.post("/api/integration-svc/purchase-orders/" + GlobalContants.PO + "/items");
		idRecieved = response.jsonPath().get("data.data.id").toString();
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().get("data.task"), "create");
		response = init().get("/api/integration-svc/purchase-orders/" + GlobalContants.PO + "/items?undefined");
		Assert.assertEquals(response.jsonPath().get("data.data[0].product.variantId"), variantId);
		Assert.assertEquals(response.jsonPath().get("data.data[0].orderQuantity").toString(), orderQuantity);
	}

	@Test
	public void Test_08_Partly_Recieved() {
		response = init().body("{\"quantity\":" + Integer.parseInt(recieved) + "}").put("/api/integration-svc/purchase-orders/"+ GlobalContants.PO + "/items/" + idRecieved + "/partly-received");
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().get("data.partlyReceivedItem.orderQuantity").toString(),recieved);
		Assert.assertEquals(response.jsonPath().get("data.partlyReceivedItem.status").toString(), "received");
		Assert.assertEquals(response.jsonPath().get("data.backOrderItem.status").toString(), "back_order");
	}

	@AfterSuite
	public void Test_Delete_PO() {
		response = init().delete("/api/integration-svc/purchase-orders/" + GlobalContants.PO);
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().get("data.task"), "deleted");
	}

}
