package tests.forms.incident;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.concurrent.TimeoutException;

import org.junit.Test;

public class H_IncidentToClosed extends H_IncidentToWorkInProgress {
	

	public H_IncidentToClosed(String environment) {
		super(environment);
	}

	public void testProcess() throws TimeoutException {
		super.testProcess();
		serviceNow.searchFor(this.IncNumber);
		serviceNow.populateFieldByLabel("State", "Closed");
		serviceNow.populateFieldByLabel("Short description", "Moved incident to Closed");
		serviceNow.clickTabByLabel("Closure Information");
		serviceNow.populateFieldByLabel("Close code", "Solved (Work Around)");
		serviceNow.populateFieldByLabel("Close notes", "This is a test incident that is now being closed.");
		serviceNow.waitUntilMandatoryReferenceFieldsPopulate(10);
		serviceNow.clickButtonByLabel("Update");

	}

	@Test
	public void validate() {
		try {
			testProcess();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}

		//Run Validation to make sure things happened correctly
		serviceNow.searchFor(this.IncNumber);
		if(serviceNow.getFieldValueByLabel("Short description").equalsIgnoreCase("Moved incident to Closed") &&
			serviceNow.getFieldValueByLabel("State").equalsIgnoreCase("Closed") &&
			serviceNow.getFieldValueByLabel("Close code").equalsIgnoreCase("Solved (Work Around)") &&
			serviceNow.getFieldValueByLabel("Close notes").equalsIgnoreCase("This is a test incident that is now being closed.")) {
			assertTrue("The Incident ["+this.IncNumber+"] was updated successfully",true);
		}
		else {
			fail("The Incident ["+this.IncNumber+"] was NOT updated successfully");
		}
	}


}
