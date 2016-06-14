package Utils;

import com.saucelabs.saucerest.SauceREST;
import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mehmetgerceker on 6/13/16.
 */
public class SauceUtils {

    private static SauceREST sauceRESTClient;

    private static SauceREST getSauceRestClient(String username, String accessKey){
        if (sauceRESTClient == null) {
            sauceRESTClient = new SauceREST(username, accessKey);
        }
        return sauceRESTClient;
    }

    public static void UpdateResults(String username, String accessKey, boolean testResults, String sessionId)
            throws JSONException, IOException {
        SauceREST client = getSauceRestClient(username, accessKey);
        Map<String, Object> updates = new HashMap<String, Object>();
        addBuildNumberToUpdate(updates);
        updates.put("passed", testResults);
        client.updateJobInfo(sessionId, updates);
    }
    /**
     * Populates the <code>updates</code> map with the value of the system property/environment variable
     * with the following name:
     * <ol>
     *     <li>SAUCE_BAMBOO_BUILDNUMBER</li>
     *     <li>JENKINS_BUILD_NUMBER</li>
     *     <li>BUILD_TAG</li>
     *     <li>BUILD_NUMBER</li>
     *     <li>TRAVIS_BUILD_NUMBER</li>
     *     <li>CIRCLE_BUILD_NUM</li>
     * </ol>
     * @param updates String,Object pair containing job updates
     */
    private static void addBuildNumberToUpdate(Map<String, Object> updates) {
        //try Bamboo
        String buildNumber = readPropertyOrEnv("SAUCE_BAMBOO_BUILDNUMBER", null);
        if (buildNumber == null || buildNumber.equals("")) {
            //try Jenkins
            buildNumber = readPropertyOrEnv("JENKINS_BUILD_NUMBER", null);
        }

        if (buildNumber == null || buildNumber.equals("")) {
            //try BUILD_TAG
            buildNumber = readPropertyOrEnv("BUILD_TAG", null);
        }

        if (buildNumber == null || buildNumber.equals("")) {
            //try BUILD_NUMBER
            buildNumber = readPropertyOrEnv("BUILD_NUMBER", null);
        }
        if (buildNumber == null || buildNumber.equals("")) {
            //try TRAVIS_BUILD_NUMBER
            buildNumber = readPropertyOrEnv("TRAVIS_BUILD_NUMBER", null);
        }
        if (buildNumber == null || buildNumber.equals("")) {
            //try CIRCLE_BUILD_NUM
            buildNumber = readPropertyOrEnv("CIRCLE_BUILD_NUM", null);
        }

        if (buildNumber != null && !(buildNumber.equals(""))) {
            updates.put("build", buildNumber);
        }

    }
    private static String readPropertyOrEnv(String key, String defaultValue) {
        String v = System.getProperty(key);
        if (v == null)
            v = System.getenv(key);
        if (v == null)
            v = defaultValue;
        return v;
    }
}
