package pages;

import java.io.IOException;

import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestResult;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.sun.net.httpserver.Authenticator.Result;

import utils.PerformOCR;
import utils.Screenshot;
import utils.Setup;

public class Calculator extends Setup{
	
	static Actions action = new Actions(driver);
	static boolean error = false;
	
	/* This method is used send the user input to the keyboard actions to type */
	public static void sendInput (String input) throws IOException {
		for (int i = 0;i < input.length(); i++){
	  	    char keyIn = input.charAt(i);
	  	    // calling the method to type keyboard inputs
	  	    typeInput(keyIn);
	  	    // when inputs contain any character which is not supported
	  	    // ex: user input is: abcd or 1a2c etc. 
	  	    if (error == true) {
	  	    	// test fails
	  	    	test.log(Status.FAIL, "Invalid Input, input provided is: " + input);
	  	    	logStatus = false;
	  	    	driver.quit();
	  	    	break;
	  	    }
	  	}
	}
	
	/* This method is used to type input using Selenium keyboard actions */
	// for now this support 0-9 and +, -, * and /
	private static void typeInput(char keyIn) {
	      switch (keyIn) {
	      case '0': 
	    	  action.sendKeys(Keys.NUMPAD0).build().perform();
	    	  break;
	      case '1': 
	    	  action.sendKeys(Keys.NUMPAD1).build().perform(); 
	    	  break;
	      case '2': 
	    	  action.sendKeys(Keys.NUMPAD2).build().perform(); 
	    	  break;
	      case '3': 
	    	  action.sendKeys(Keys.NUMPAD3).build().perform(); 
	    	  break;
	      case '4': 
	    	  action.sendKeys(Keys.NUMPAD4).build().perform(); 
	    	  break;
	      case '5': 
	    	  action.sendKeys(Keys.NUMPAD5).build().perform(); 
	    	  break;
	      case '6': 
	    	  action.sendKeys(Keys.NUMPAD6).build().perform(); 
	    	  break;
	      case '7': 
	    	  action.sendKeys(Keys.NUMPAD7).build().perform(); 
	    	  break;
	      case '8': 
	    	  action.sendKeys(Keys.NUMPAD8).build().perform(); 
	    	  break;
	      case '9': 
	    	  action.sendKeys(Keys.NUMPAD9).build().perform(); 
	    	  break;
	      case '.': 
	      	  action.sendKeys(Keys.DECIMAL).build().perform(); 
	      	  break;
	      case '+': 
	      	  action.sendKeys(Keys.ADD).build().perform(); 
	      	  break;
	      case '-': 
	      	  action.sendKeys(Keys.SUBTRACT).build().perform();
	      	  break;
	      case '*': 
	      	  action.sendKeys(Keys.MULTIPLY).build().perform();
	      	  break;
	      case '/': 
	      	  action.sendKeys(Keys.DIVIDE).build().perform();
	      	  break;
	      default: 
			  error = true;
	      	  break;		
	      }
	  }
	
	/* This method is used to press EQUALS key using keyboard which works as "=" operator in Calculator application */
	public static void getResult () {
		action.sendKeys(Keys.EQUALS).build().perform();
	}
	
	/* This method is doing the calculation part */
	public static void calculation (String ip1, String ip2, String opr) throws IOException{
		test.log(Status.PASS, "Input1 is : " + ip1);
		test.log(Status.PASS, "Input2 is : " + ip2);
		test.log(Status.PASS, "Operator is : " + opr);
		// This is to handle when Input2 is Zero and user perfoms Division action 
		if (ip2.equals("0")) {
			if (getResultFromApp().trim().contains("Error")) {
				// test fails
				test.log(Status.PASS, "Input2 is equal to Zero so result returns Error message in application, expeted message: 'Error'");
			}
		}
		else {
			double value1 = Double.parseDouble(ip1);
			double value2 = Double.parseDouble(ip2);
			double finalResult = 0;
			//Calling the functions for Addition, Subtraction and Division
			if (opr.equals("+")) {
				finalResult = addition(value1,value2);
			}
			else if (opr.equals("-")) {
				finalResult = subtraction(value1,value2);
			}
			else if (opr.equals	("/")) {
				finalResult = division(value1,value2);
			}
			else {
				// test fails when user input has invalid operator
				// operators apart from + , - and / for now
				test.log(Status.FAIL, "This operation not supported yet, Invalid Operation and operator is: " + opr);
				logStatus = false;
				driver.quit();
			}
			String strResult;
			String convResult = String.valueOf(finalResult);
			if (ip1.contains(".") || ip2.contains(".") || opr.equals("/")) {
				// when division happens and input1 < input and the output type double shows result like this 1.3456789376E-4
				// this happens only in case input1 < input2
				if (opr.equals("/") && value1 < value2 && convResult.contains("E-")) {
					String [] decStr = convResult.split("E-");
					int limit = Integer.parseInt(decStr[1]);
					String strAppend = "0";
					String useString = convResult.replace(".", "");
					for (int i=1; i<limit; i++) {
						useString = strAppend + useString;
					}
					strResult = "0." + useString.substring(0,8);
				}
				// other cases with decimal results after division
				else if (opr.equals("/") && !convResult.contains("E-")) {
					strResult = convResult.substring(0,10);
				}
				else {
					// Round off calculation for other decimal value cases
					int decimalPlaces = countDecimalPlaces (ip1,ip2,opr,finalResult);
					double roundResult = roundOff(finalResult,decimalPlaces);
					strResult = String.valueOf(roundResult);
				}	
			}
			// When output result is greater than the number of digits supported by application, here it is Nine digits
			else if (convResult.contains("E") && finalResult > 999999999.0) {
				// This the boundary case when both the inputs are having max values supported i.e 999999999 here in application
				if (finalResult == 1.999999998E9) {
					strResult = "2e+9";
				}
				// this is only one case where output just crosses nine digits
				else if (finalResult == 1.0E9) {
					strResult = "1e+9";
				}
				else {
					// as for long outputs application displays upto 8 digits and then appends e+9 here
					strResult = convResult.substring(0,8) + "e+9";
				}
			}
			else {
				// other cases
				String[] splitResult = convResult.split("\\.");
				strResult = splitResult[0];
			}
			// Validate the calcutaed result with the result shown by application
			if (getResultFromApp().trim().contains(strResult)) {
				test.log(Status.PASS, "Calculated result matched with Application, Expected result is: " + strResult);
			}
			else {
				// test fails
				test.log(Status.FAIL, "Calculated result does not match with Application, Expected result is: " + strResult);
				logStatus = false;
			}
		}
	}
	
	public static double addition (double value1, double value2) {
		 double result = value1 + value2;
		 return result;
	}
	
	public static double subtraction (double value1, double value2) {
		double result = value1 - value2;
		return result;
	}
	
	public static double division (double value1, double value2) {
		double result = value1 / value2;
		return result;
	}
	
	/* This is the method used to get result from the applicatiion */
	public static String getResultFromApp () throws IOException {
		String filePath = System.getProperty("user.dir") + "/screenshots/application.jpg";
		// Taking screenshot of the application
		Screenshot.takeScreenshot(filePath);
		// Method to perform OCR using java on the screenshot image file
		String appText = PerformOCR.getTextFromImage(filePath);			
		return appText;
	}
	
	public static double roundOff (double number, int places) {
		
		return Double.parseDouble(String.format("%." + places + "f", number));
		 
	}
	
	/* This method is used to shift count the decimal places based on the application display
	/ and number limit in the application */
	public static int countDecimalPlaces (String str1, String str2, String opr, double finalResult) {
		String [] splitStr1 = str1.split("\\.");
		String [] splitStr2 = str2.split("\\.");
		String useStr;
		if (splitStr1[1].length() == splitStr2[1].length()) {
			useStr = splitStr1[1];
		}
		else if (splitStr1[1].length() > splitStr2[1].length()) {
			useStr = splitStr1[1];
		}
		else {
			useStr = splitStr2[1];
		}
		int count = useStr.length();
		if (opr.equals("+") && String.valueOf(finalResult).length() > 10) {
			count = count -1;
		}
		else if (opr.equals("-") && (String.valueOf(finalResult).contains("-"))) {
			if (String.valueOf(finalResult).length() > 11) {
				count = count -1;
			}
			else {
				return count;	
			}
		}
		return count;
	}

}
