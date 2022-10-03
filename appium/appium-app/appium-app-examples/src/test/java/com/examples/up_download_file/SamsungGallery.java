package com.examples.up_download_file;

import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;


public class SamsungGallery {

    private AndroidDriver driver;

    private String photos = "com.sec.android.gallery3d:id/recycler_view_item";
    private String deleteButton ="com.sec.android.gallery3d:id/btn_delete";
    private String confirmDeleteButton = "com.sec.android.gallery3d:id/button1";

    public SamsungGallery(AndroidDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.startActivity(new Activity("com.sec.android.gallery3d", "com.samsung.android.gallery.app.activity.GalleryActivity"));

    }

    public void openPhoto(String which){
        int whichPhoto = (which == "first" ? 0 : this.amountOfPhotos()-1);

        // open the photo
        List<WebElement> photosList =  driver.findElements(By.id(photos));
        photosList.get(whichPhoto).click();
    }

    /**
     *
     * @param which which photo to delete: first or last
     */
    public void deletePhoto(String which){

        this.openPhoto(which);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        final WebElement deleteImgBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(deleteButton)));
        deleteImgBtn.click();
        driver.findElement(By.id(confirmDeleteButton)).click();

    }

    public int amountOfPhotos(){
        return (driver.findElements(By.id(photos)).size());
    }

    /**
     *
     * @param currentPhotos - how many photos are currently in the gallery
     * @param waitingTimeSec - how long to wait for the upload
     * @return true if photo was uploaded and false if there the photo was not uploaded
     */
    public boolean waitUploadPhoto(int currentPhotos, int waitingTimeSec){
        int waitingTime = waitingTimeSec;
        boolean bPhotoUpload = false;
        for (int i = waitingTimeSec; i > 0; i--) {
            if (this.amountOfPhotos() > currentPhotos){
                bPhotoUpload = true;
                break;
            }
            try
            {
                Thread.sleep(1000);
            }
            catch(InterruptedException ex)
            {
                Thread.currentThread().interrupt();
            }
        }
        return bPhotoUpload;
    }

}
