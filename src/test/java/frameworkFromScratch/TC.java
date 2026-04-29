package frameworkFromScratch;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

@Listeners(Utility1.class)
public class TC extends BC {

	// @Parameters("Browser")
	
	@Test(dataProvider = "getData")
	public void executeFlow(HashMap<String, Object> data) throws Exception {

	    getBrowser(); // only chrome, as you want

	    // extract data properly
	    HashMap<String, String> productData =
	            (HashMap<String, String>) data.get("products");

	    HashMap<String, String> userData =
	            (HashMap<String, String>) data.get("user");

	    // 🔍 DEBUG (important)
	    System.out.println("PRODUCT DATA: " + productData);
	    System.out.println("USER DATA: " + userData);

	    LoginPage lp = new LoginPage(getD());
	    lp.loginApp("standard_user", "secret_sauce");

	    Page_Cart pc = new Page_Cart(getD());

	    Assert.assertTrue(pc.isProductPresent(productData));
	    pc.addProducts(productData);

	    Assert.assertEquals(pc.getCartCount(), productData.size());

	    double expectedTotal = pc.getExpectedTotal(productData);

	    Cart_Page cp = new Cart_Page(getD());
	    cp.clickCart();

	    Assert.assertTrue(cp.validateProductAndPrice(productData));
	    cp.clickToCheckout();

	    ChechOut ch = new ChechOut(getD());

	    ch.getUserDetails(
	            userData.get("name"),
	            userData.get("last"),
	            userData.get("code")
	    );

	    Assert.assertTrue(ch.validateTotal(expectedTotal));
	    Assert.assertTrue(ch.finish());
	}
	@DataProvider(parallel = true)
	public Object[][] getData() throws IOException {

//        HashMap<String, String> map1 = new HashMap<>();
//        map1.put("Sauce Labs Backpack", "$29.99");
//        map1.put("Sauce Labs Bike Light", "$9.99");
//
//        HashMap<String, String> user1 = new HashMap<>();
//        user1.put("name", "kalpesh");
//        user1.put("last", "chavhan");
//        user1.put("code", "12345");
//
//        HashMap<String, String> map2 = new HashMap<>();
//        map2.put("Sauce Labs Fleece Jacket", "$49.99"); 
//        map2.put("Sauce Labs Onesie", "$7.99");
//
//        HashMap<String, String> user2 = new HashMap<>(); 
//        user2.put("name", "aaryan");
//        user2.put("last", "khan");
//        user2.put("code", "37493");
//
//        return new Object[][] {
//                { "chrome", map1, user1 },
//                { "edge", map2, user2 } 
//                

		

		    List<HashMap<String, Object>> data =
		            Utility1.readJson(System.getProperty("user.dir") + "/src/test/resources/data.json");

		    return new Object[][] {
		            { data.get(0) },
		            { data.get(1) }
		    };
		}
}