package testcases;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pages.Calculator;
import utils.Setup;

public class TestCalculatorFeatures extends Setup{
	
  @Parameters({ "input1", "input2", "operator"})
  @Test
  public void ValidateCalculatorFeatures(String input1, String input2, String operator) throws IOException {
	  	test = extent.createTest("ValidateCalculatorFeatures");
		Assert.assertEquals(driver.getTitle(),"Full Screen Calculator - Online Calculator");
	  	Calculator.sendInput(input1);
	  	Calculator.sendInput(operator);
	  	Calculator.sendInput(input2);
	  	Calculator.getResult();
	  	Calculator.calculation(input1, input2, operator);  	
  }
 
}

