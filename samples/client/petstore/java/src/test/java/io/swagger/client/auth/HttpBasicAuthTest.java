package io.swagger.client.auth;

import java.util.Map;
import java.util.HashMap;

import static org.junit.Assert.*;
import org.junit.*;

public class HttpBasicAuthTest {
  HttpBasicAuth auth = null;

  @Before
  public void setup() {
    auth = new HttpBasicAuth();
  }

  @Test
  public void testApplyToParams() {
    Map<String, String> queryParams = new HashMap<String, String>();
    Map<String, String> headerParams = new HashMap<String, String>();

    auth.setUsername("my-username");
    auth.setPassword("my-password");
    auth.applyToParams(queryParams, headerParams);

    // no changes to query parameters
    assertEquals(0, queryParams.size());
    assertEquals(1, headerParams.size());
    // the string below is base64-encoded result of "my-username:my-password" with the "Basic " prefix
    String expected = "Basic bXktdXNlcm5hbWU6bXktcGFzc3dvcmQ=";
    assertEquals(expected, headerParams.get("Authorization"));

    // null username should be treated as empty string
    auth.setUsername(null);
    auth.applyToParams(queryParams, headerParams);
    // the string below is base64-encoded result of ":my-password" with the "Basic " prefix
    expected = "Basic Om15LXBhc3N3b3Jk";
    assertEquals(expected, headerParams.get("Authorization"));

    // null password should be treated as empty string
    auth.setUsername("my-username");
    auth.setPassword(null);
    auth.applyToParams(queryParams, headerParams);
    // the string below is base64-encoded result of "my-username:" with the "Basic " prefix
    expected = "Basic bXktdXNlcm5hbWU6";
    assertEquals(expected, headerParams.get("Authorization"));
  }
}