package org.purl.wf4ever.astrotaverna.pdl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.sf.taverna.t2.activities.testutils.ActivityInvoker;
import net.sf.taverna.t2.workflowmodel.OutputPort;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityInputPort;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.purl.wf4ever.astrotaverna.utils.MyUtils;

public class ValidationPDLClientActivityTest {

	private ValidationPDLClientActivityConfigurationBean configBean;

	//these variables must be the same than the ones defined in the activity class
	private static final String IN_FIRST_INPUT = "votable1";
	private static final String IN_SECOND_INPUT = "votable2";
	private static final String IN_OUTPUT_TABLE_NAME = "outputFileNameIn";
	private static final String OUT_SIMPLE_OUTPUT = "outputFileOut";
	private static final String OUT_REPORT = "report";
	
	private ValidationPDLClientActivity activity = new ValidationPDLClientActivity();

	@Ignore("Not ready to run")
	@BeforeClass
	public static void createTableFiles(){
		//create files with votables
	}

	@Ignore("Not ready to run")
	@AfterClass
	public static void deleteTableFiles(){
		//delete files with votables
	}
	
	//this method is invoked before each test method
	@Ignore
	@Before
	public void makeConfigBean() throws Exception {
		configBean = new ValidationPDLClientActivityConfigurationBean();
		
		configBean.setPdlDescriptionFile("/home/julian/otherworkspaces/pdlworkspace/testPDLcmdLineTool/PDL-Description.xml");

	}

	
	@Test(expected = ActivityConfigurationException.class)
	public void invalidConfiguration() throws ActivityConfigurationException {
		ValidationPDLClientActivityConfigurationBean invalidBean = new ValidationPDLClientActivityConfigurationBean();
		invalidBean.setPdlDescriptionFile("/home/PDL-Description.xml");
		// Should throw ActivityConfigurationException
		activity.configure(invalidBean);
	}
	
	

	//this test is valid only with the right folders

	
	@Test
	public void executeAsynchValid() throws Exception {
		InputStream is = this.getClass().getResourceAsStream("/org/purl/wf4ever/astrotaverna/pdl/PDL-DescriptionTest.xml");
	    String pdlContent = MyUtils.convertStreamToString(is);
	    File tmpFile = MyUtils.writeStringAsTmpFile(pdlContent);
		configBean.setPdlDescriptionFile(tmpFile.getAbsolutePath());
		activity.configure(configBean);

		Map<String, Object> inputs = new HashMap<String, Object>();
		Float value = new Float(1/15.0);
		inputs.put("Ne", value.toString());
		inputs.put("Si", value.toString());
		inputs.put("Mg", value.toString());
		inputs.put("Cr", value.toString());
		inputs.put("Na", value.toString());
		inputs.put("Ar", value.toString());
		inputs.put("Al", value.toString());
		inputs.put("Ca", value.toString());
		inputs.put("Fe", value.toString());
		inputs.put("C", value.toString());
		inputs.put("N", value.toString());
		inputs.put("S", value.toString());
		inputs.put("Mn", value.toString());
		inputs.put("O", value.toString());
		inputs.put("Ni", value.toString());
		inputs.put("email", "email@iaa.es");
		
		

		Map<String, Class<?>> expectedOutputTypes = new HashMap<String, Class<?>>();
		//expectedOutputTypes.put(OUT_SIMPLE_OUTPUT, String.class);
		expectedOutputTypes.put(OUT_REPORT, String.class);

		Map<String, Object> outputs = ActivityInvoker.invokeAsyncActivity(
				activity, inputs, expectedOutputTypes);

		assertEquals("Unexpected outputs", 1, outputs.size());
		assertEquals("Valid", outputs.get(OUT_REPORT));
		
		//assertEquals(Arrays.asList("Value 1", "Value 2"), outputs
		//		.get("moreOutputs"));

	}
	
	
	@Test(expected = Exception.class)
	public void executeAsynchŃotValid() throws Exception {
		InputStream is = this.getClass().getResourceAsStream("/org/purl/wf4ever/astrotaverna/pdl/PDL-DescriptionTest.xml");
	    String pdlContent = MyUtils.convertStreamToString(is);
	    File tmpFile = MyUtils.writeStringAsTmpFile(pdlContent);
		configBean.setPdlDescriptionFile(tmpFile.getAbsolutePath());
		activity.configure(configBean);

		Map<String, Object> inputs = new HashMap<String, Object>();
		Float value = new Float(1/12.0);
		inputs.put("Ne", value.toString());
		inputs.put("Si", value.toString());
		inputs.put("Mg", value.toString());
		inputs.put("Cr", value.toString());
		inputs.put("Na", value.toString());
		inputs.put("Ar", value.toString());
		inputs.put("Al", value.toString());
		inputs.put("Ca", value.toString());
		inputs.put("Fe", value.toString());
		inputs.put("C", value.toString());
		inputs.put("N", value.toString());
		inputs.put("S", value.toString());
		inputs.put("Mn", value.toString());
		inputs.put("O", value.toString());
		inputs.put("Ni", value.toString());
		inputs.put("email", "email@iaa.es");
		
		

		Map<String, Class<?>> expectedOutputTypes = new HashMap<String, Class<?>>();
		//expectedOutputTypes.put(OUT_SIMPLE_OUTPUT, String.class);
		expectedOutputTypes.put(OUT_REPORT, String.class);

		Map<String, Object> outputs = ActivityInvoker.invokeAsyncActivity(
				activity, inputs, expectedOutputTypes);

		assertEquals("Unexpected outputs", 1, outputs.size());
		assertEquals("Valid", outputs.get(OUT_REPORT));

	}
	
	
	@Test
	public void reConfiguredActivity() throws Exception {
		
		
		assertEquals("Unexpected inputs", 0, activity.getInputPorts().size());
		assertEquals("Unexpected outputs", 0, activity.getOutputPorts().size());
		
		InputStream is = this.getClass().getResourceAsStream("/org/purl/wf4ever/astrotaverna/pdl/PDL-DescriptionTest.xml");
	    String pdlContent = MyUtils.convertStreamToString(is);
	    File tmpFile = MyUtils.writeStringAsTmpFile(pdlContent);
		configBean.setPdlDescriptionFile(tmpFile.getAbsolutePath());
		activity.configure(configBean);
		
		assertEquals("Unexpected inputs", 16, activity.getInputPorts().size());
		assertEquals("Unexpected outputs", 1, activity.getOutputPorts().size());

		activity.configure(configBean);
		// Should not change on reconfigure
		assertEquals("Unexpected inputs", 16, activity.getInputPorts().size());
		assertEquals("Unexpected outputs", 1, activity.getOutputPorts().size());
		Iterator<ActivityInputPort> it = activity.getInputPorts().iterator();
	}
	
	
}
