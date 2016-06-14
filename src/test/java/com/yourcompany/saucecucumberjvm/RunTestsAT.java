package com.yourcompany.saucecucumberjvm;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions(tags = {"@guineapig"},format={"pretty", "html:target/cucumber"})
public class RunTestsAT extends AbstractTestNGCucumberTests{
}
