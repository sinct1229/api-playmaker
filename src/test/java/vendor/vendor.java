package vendor;

import org.testng.Assert;
import org.testng.annotations.Test;

import commons.GlobalContants;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class vendor {
	Response response;
	String jsonString;
//	String poNumber = "SINTEST";
//	String vendorId = "539a2980-6ca7-45a0-84b6-622c4861d2fe";
//	double date = 1648021921044.0;
//	double location = 14874116142.0;
	String createVendor = "1Sin";
	String updateVendor = "new_Sin";
	String vendorId = "";
	
	@Test
	public void Test_01_Create_Vendor() {
		RestAssured.baseURI = GlobalContants.BASE_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Authorization", "Bearer " + GlobalContants.TOKEN).header("x-tenant-id", "melinda").header("Content-Type", "application/json");
		response = request.body("{\"name\":\"" + createVendor+"\"}").post("/api/integration-svc/vendors");
		Assert.assertEquals(response.getStatusCode(), 200);
		String jsonString = response.asString();
		vendorId = JsonPath.from(jsonString).get("id");
		System.out.println(vendorId);
	}
	
	
	@Test
	public void Test_02_Update_Vendor() {
		RestAssured.baseURI = GlobalContants.BASE_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Authorization", "Bearer " + GlobalContants.TOKEN).header("x-tenant-id", "melinda").header("Content-Type", "application/json");
		response = request.body("{\"name\":\"" + updateVendor+"\"}").put("/api/integration-svc/vendors");
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	

	@Test
	public void Test_03_Delete_Vendor() {
		RestAssured.baseURI = GlobalContants.BASE_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Authorization", "Bearer " + GlobalContants.TOKEN).header("x-tenant-id", "melinda").header("Content-Type", "application/json");
		response = request.body("{\"name\":\"" + updateVendor+"\"}").delete("/api/integration-svc/vendors");
		Assert.assertEquals(response.getStatusCode(), 200);
	}

}
