package Utils;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.Collections;

public class ResultReporter {

    private static final String TESTOBJECT_APPIUM_API_ENDPOINT = "https://app.testobject.com/api/rest/appium/v1/";

    private WebTarget resource;

    public ResultReporter() {
        Client client = ClientBuilder.newClient();
        resource = client.target(TESTOBJECT_APPIUM_API_ENDPOINT);
    }

    public void saveTestStatus(String sessionId, boolean status) {
        resource.path("session")
                .path(sessionId)
                .path("test")
                .request(new MediaType[]{MediaType.APPLICATION_JSON_TYPE}).put(Entity.json(Collections.singletonMap("passed", status)));
    }

}
