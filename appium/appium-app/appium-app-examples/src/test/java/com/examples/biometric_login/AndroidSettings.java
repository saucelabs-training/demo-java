package com.examples.biometric_login;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class AndroidSettings {
    private final String DEFAULT_PIN = "1234";
    private AndroidDriver driver;

    public AndroidSettings(AndroidDriver driver) {
        this.driver = driver;
    }

    public void enableBiometricLogin() {

        executeAdbCommand("am start -a android.settings.SECURITY_SETTINGS && locksettings set-pin " + DEFAULT_PIN);

        waitAndClick("Fingerprint");

        waitForDisplay("Re-enter your PIN");
        executeAdbCommand("input text " + DEFAULT_PIN + " && input keyevent 66");

        waitAndClick("NEXT");

        waitForDisplay("Touch the sensor");
        int touchCode = Integer.parseInt(DEFAULT_PIN);
        driver.fingerPrint(touchCode); //Appium command: https://appium.io/docs/en/commands/device/authentication/finger-print/
        for (int i=0; i<2; i++) {
            waitForDisplay("touch again");
            driver.fingerPrint(touchCode);
        }
        waitAndClick("DONE");
    }

    private void executeAdbCommand(String adbCommand) {
        Map<String, Object> params = new HashMap<>();
        params.put("command", adbCommand);
        driver.executeScript("mobile: shell", params);
    }

    public void waitAndClick(String text){

        WebElement elementLink =  driver.findElement(By.xpath("//*[contains(@text,'" + text + "')]"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        wait.until(ExpectedConditions.visibilityOf(elementLink));

        elementLink.click();
    }

    public void waitForDisplay(String text) {
        WebElement elementLink =  driver.findElement(By.xpath("//*[contains(@text,'" + text + "')]"));

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        wait.until(ExpectedConditions.visibilityOf(elementLink));
    }
}
