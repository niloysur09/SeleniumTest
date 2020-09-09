# SeleniumTest

This Automation Framework for "Online Calculator" is built on "Selenium TestNG" using "Java" language. Please find below more details about this framework:

1. Go to "testng.xml" and provide inputs for calculation and provide the operator. Everything is configurable from this xml ex: like URL of the application, Browser to use. Also configure the test suite/ test cases to be run from this xml only. this "testng.xml" can be run from command prompt or by using IDEs like Eclipse.
2. Under "src" -> "pages" : this folder consists of classes where all the methods specific to this Online Calculator is written. Here it is "Calculator.java" class
3. Under "src" -> "testcases" : The actual TestNG test cases are in this folder. Test cases are denoted with "@Test" and it does not consist of any method body, only steps and method calls will be made from testcases
4. Under "src" -> "utils" : this folder consists of classes where all the required methods specific to the framework is written (not specific to the Online Calculator)
5. results: this folder consists of the "html" results files which will be saved after every execution and it uses Extent Reports. For now I have provided few results of my test runs.
6. screenshots: any images / screenshots taken will be stored in this folder.

# Please Note:

1. I have tried all kinds of approaches for this Online Calculator Automation, including the one provided in xendit-framework where it takes the screenshot of the “canvas” and then tries to draw canvas for validating the result. But that can be done using javascript, using java its difficult to do that.
2. Also tried base64 encoding, java Graphics class but I feel using the "java-ocr" is the easiest and better approach for now. Though I would say it is not 100% precise but keeping in mind the timeline it's the one I have chosen and submitted. Hope you will like the approach.
3. I have focused more on the test cases covering corner / boundary cases and doing the calculation based on that. All the calculations and methods are under the “pages“ folder. Cases like division by Zero, very large inputs and outputs, negative results etc. are all covered in the logic including so particular cases where the calculator displayed the result in a particular way. Hope you will like the logic as well.

# I have run some test cases with different combinations of inputs, pass and failed test cases. All are available under the “results” folder please have a look at them.

My solution might not be the best but focus on test coverage is more, could have tried some more approaches but for now I have it for you. Fingers crossed and hope the reviewers will like it as well. 

# Thank you and it is a very good experience working in this assignment.
For any details or queries email me : 
# niloysur@gmail.com
