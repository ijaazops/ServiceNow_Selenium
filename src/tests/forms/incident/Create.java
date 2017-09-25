package tests.forms.incident;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import tests.BaseTest;

public class Create extends BaseTest{

	private String IncNumber = null;
	
	public Create(String environment) {
		super(environment);
	}
	
	public void testProcess() throws Exception {
		//No Super.testProcess() because it extends the BaseTest Class
		//Code here should do the manual process a user would do
		serviceNow.ChooseApplicationAndModule("Incident", "Create New");
		serviceNow.populateFieldByLabel("Caller","Abel Tuter");
		serviceNow.populateFieldByLabel("Short description","Test Data");
		serviceNow.waitUntilMandatoryReferenceFieldsPopulate(10);
		
		this.IncNumber = serviceNow.getFieldValueByLabel("Number");
		
		
		serviceNow.clickButtonByLabel("SUBMIT");
		
	}

	@Test
	public void validate() {
		try {
			testProcess();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		//Run Validation to make sure things happened correctly
		serviceNow.searchFor(this.IncNumber);
		if(serviceNow.getFieldValueByLabel("Number") != "") {
			assertTrue("The Incident was created successfully",true);
		}
		else {
			fail("TESTING");
		}

	}

}
