package com.saucedemo.selenium.junit4;

import com.saucelabs.saucebindings.Prerun;
import com.saucelabs.saucebindings.SauceOptions;
import com.saucelabs.saucebindings.SaucePlatform;
import com.saucelabs.saucebindings.SauceSession;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.HashMap;
import java.util.Map;

public class WindowsAuthentication {

    private SauceSession session;
    private SauceOptions sauceOptions;

    @After
    public void tearDown() {
        session.stop(true);
    }

    @Test
    public void basicAuthTest() {
        sauceOptions = new SauceOptions();
        sauceOptions.setPlatformName(SaucePlatform.WINDOWS_10);

        session = new SauceSession(sauceOptions);
        RemoteWebDriver driver = session.start();
        driver.get("http://admin:admin@the-internet.herokuapp.com/basic_auth");
        driver.findElement(By.id("content"));
    }

    @Test
    @Ignore("doesn't work")
    public void autoItScriptTest() {
        //Good AutoIt docs: https://support.saucelabs.com/hc/en-us/articles/360049978374-Sample-AutoIT-Example-to-Handle-Integrated-Windows-Authentication-Dialog-IWA-
        sauceOptions = new SauceOptions();
        sauceOptions.setPlatformName(SaucePlatform.WINDOWS_10);

        Map<Prerun, Object> prerun = new HashMap<>();
        prerun.put(Prerun.EXECUTABLE, "sauce-storage:login.zip");
        prerun.put(Prerun.ARGS, "--silent");
        prerun.put(Prerun.ARGS, "-a");
        prerun.put(Prerun.ARGS, "-q");
        prerun.put(Prerun.BACKGROUND, true);
        sauceOptions.setPrerun(prerun);

        session = new SauceSession(sauceOptions);
        RemoteWebDriver driver = session.start();
        driver.get("http://the-internet.herokuapp.com/basic_auth");
    }

    @Test
    @Ignore("need to try")
    public void autoItScriptTest2() {
//        public static final String URL = "https://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:443/wd/hub";
//        DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
//        caps.setCapability("platform", "Windows 7");
//        caps.setCapability("version", "11.0");
//        caps.setCapability("seleniumVersion","3.141.0");
//        caps.setCapability("name", "SampleAutoItSauceSampleWinSec");
        //import org.json.JSONObject;
//        JSONObject jsonObj = new JSONObject();
//        jsonObj.put("executable", "sauce-storage:SampleWinSec.exe");
//        jsonObj.put("background", true);
//        //import org.json.JSONArray;
//        JSONArray arr = new JSONArray();
//        arr.put("--silent");
//        arr.put("-a");
//        arr.put("-q");
//        jsonObj.put("args", arr);
//        caps.setCapability("prerun",jsonObj);
//        RemoteWebDriver driver = new RemoteWebDriver(new URL(URL), caps);
//        driver.get("https://the-internet.herokuapp.com/basic_auth");
//        driver.quit();
    }
}
