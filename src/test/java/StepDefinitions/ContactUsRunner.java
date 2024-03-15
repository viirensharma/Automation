package StepDefinitions;

import org.junit.runner.RunWith;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.Cucumber;


//cucumber annotations and options

@RunWith(Cucumber.class)

@CucumberOptions(features="src/test/resources//Feature", glue= {"StepDefinitions"},
monochrome= true,

//Execution report
plugin={"pretty","html:target/HtmlReports", "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"})
public class ContactUsRunner {


}
