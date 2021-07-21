package com.emusim.biometric_login;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
        waitAndClick("NEXT");

        waitForDisplay("Confirm your PIN");
        executeAdbCommand("input text " + DEFAULT_PIN + " && input keyevent 66");

        waitAndClick("NEXT");

        waitForDisplay("Put your finger");
        int touchCode = Integer.parseInt(DEFAULT_PIN);
        driver.fingerPrint(touchCode);

        waitForDisplay("Move your finger");
        driver.fingerPrint(touchCode);

        waitAndClick("DONE");

        // Open the app again
        driver.launchApp();
    }

    private void executeAdbCommand(String adbCommand) {
        Map<String, Object> params = new HashMap<>();
        params.put("command", adbCommand);
        driver.executeScript("mobile: shell", params);
    }

    public void waitAndClick(String text){
        WebElement fingerprintLink =  driver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().textContains(\"" + text + "\")"));
        WebDriverWait wait = new WebDriverWait(driver, 5);

        wait.until(ExpectedConditions.visibilityOf(fingerprintLink));

        fingerprintLink.click();
    }

    public void waitForDisplay(String text) {
        WebElement fingerprintLink =  driver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().textContains(\"" + text + "\")"));
        WebDriverWait wait = new WebDriverWait(driver, 5);

        wait.until(ExpectedConditions.visibilityOf(fingerprintLink));
    }
}
