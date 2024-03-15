package StepDefinitions;

import static org.junit.Assert.assertEquals;
import java.time.Duration;
//import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import io.cucumber.java.en.*;
import org.openqa.selenium.NoSuchElementException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ContactUsPageSteps 
{

	private WebDriver driver;

	@Given("User opens browser and enters URL")
	public void user_opens_browser_and_enters_url() {
		System.setProperty("webdriver.chrome.driver", "SF_Test/src/test/resources/Drivers/geckodriver");
		driver= new FirefoxDriver();
		driver.navigate().to("https://webdriveruniversity.com/Contact-Us/contactus.html");
		//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(40));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

	}


	@When("User enters first name as {string}")
	public void user_enters_first_name_as(String firstName) {
		enterText(firstName, By.name("first_name"));
	}

	@Then("User enters the last name as {string}")
	public void user_enters_the_last_name_as(String lastName) {
		enterText(lastName, By.name("last_name"));
	}

	@Then("User enters a valid email {string}")
	public void user_enters_a_valid_email(String email) {
		enterText(email, By.name("email"));
	}

	@Then ("User enters {string} in comments")
	public void user_enters_in_comments(String comment) {
		enterText(comment, By.name("message"));
	}

	@Then ("user clicks  the Submit button")
	public void user_clicks_the_submit_button() {
		WebElement submitButton= driver.findElement(By.xpath("//input[@type='submit']"));
		submitButton.click();
	}

	@Then ("User is redirected to Thank You Page")
	public void user_is_redirected_to_Thank_You_Page() {
		WebElement thankYouPage= driver.findElement(By.xpath("//*[@id=\"contact_reply\"]"));
		assertEquals("Thank You for your Message!", thankYouPage.getText());
		String expectedMessage = "Thank You for your Message!";
		String actualMessage = thankYouPage.getText();


		assertEquals(expectedMessage, actualMessage);

		System.out.println("Test passed");
	}


	//Invalid

	@Given("a user is on the Contact Us page")
	public void contact_us_page_is_open() {
		System.setProperty("webdriver.chrome.driver", "SF_Test/src/test/resources/Drivers/geckodriver");
		driver= new FirefoxDriver();
		driver.navigate().to("https://webdriveruniversity.com/Contact-Us/contactus.html");
		System.out.println("User is on the Contact Us page");
	}

	@When("the user enters {string}, {string}, {string}, and {string}")

	public void userEntersDetails(String firstName, String lastName, String email, String comment) {


		System.out.println("User enters details - First Name: " + firstName +
				", Last Name: " + lastName +
				", Comment: " + comment +
				", Email: " + email);

		user_enters_first_name_as(firstName);
		user_enters_the_last_name_as(lastName);
		user_enters_a_valid_email(email);
		user_enters_in_comments(comment);

	}

	public void enterText(String text, By locator) {
		WebElement field = driver.findElement(locator);
		field.sendKeys(text);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}



	@And("User clicks on Submit")
	public void user_clicks_the_submit() {
		WebElement submitButton= driver.findElement(By.xpath("//input[@type='submit']"));
		submitButton.click();

	}

	@Then("User sees the page that data is invalid")
	public void user_sees_the_page_that_data_is_invalid() {


		try {
			Thread.sleep(2500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		try {

			WebElement bodyElement = driver.findElement(By.tagName("body"));

			String bodyHtmlContent = bodyElement.getAttribute("innerHTML");

			Document bodyDocument = Jsoup.parse(bodyHtmlContent);

			// Extract error messages
			Elements errorElements = bodyDocument.select("br");

			StringBuilder actualErrorMessages = new StringBuilder();

			for (int i = 0; i < errorElements.size(); i++) {
				String errorMessage = errorElements.get(i).nextSibling().toString().trim();
				System.out.println("Error Message " + (i + 1) + ": " + errorMessage);
				actualErrorMessages.append(errorMessage).append("\n");
			}

			// Expected error messages
			String expectedErrorMessage1 = "Error: all fields are required";
			String expectedErrorMessage2 = "Error: Invalid email address";


			if (errorElements.size() == 1) {
				System.out.println("only one error");
				String actualErrorMessage = actualErrorMessages.toString().trim();
				Assert.assertTrue("Error message does not match either of the expected messages.",
						actualErrorMessage.equals(expectedErrorMessage1) || actualErrorMessage.equals(expectedErrorMessage2));
			} else if (errorElements.size() == 2) {
				Assert.assertEquals("First error message does not match.", expectedErrorMessage1, errorElements.get(0).nextSibling().toString().trim());
				Assert.assertEquals("Second error message does not match.", expectedErrorMessage2,
						errorElements.get(1).nextSibling().toString().trim());
			} else {
				Assert.fail("Unexpected number of error messages.");
			}

		} catch (NoSuchElementException e) {
			System.out.println("Element not found on the page.");
		}

	}
}