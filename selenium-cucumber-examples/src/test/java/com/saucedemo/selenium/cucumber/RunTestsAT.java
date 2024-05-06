package com.saucedemo.selenium.cucumber;

import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;


@CucumberOptions(features = "src/test/resources")
public class RunTestsAT extends AbstractTestNGCucumberTests{
}
