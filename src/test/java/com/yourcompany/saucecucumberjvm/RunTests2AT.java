package com.yourcompany.saucecucumberjvm;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions(tags = {"@guineapig2"},format={"pretty", "html:target/cucumber"})
public class RunTests2AT extends AbstractTestNGCucumberTests{
}
