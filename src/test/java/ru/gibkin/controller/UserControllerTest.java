package ru.gibkin.controller;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;


public class UserControllerTest {


    private static final String REST_URL = "/users";
    private static final String APPLICATION_JSON = "application/json";
    private static int port = 9999;
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(port);


    @Test
    public void testGetAll() throws IOException {
        stubFor(get(urlPathMatching(REST_URL + "/1")).willReturn(aResponse()
                .withStatus(200).withHeader("Content-Type", APPLICATION_JSON).withBody("\"testing-library\": \"Wiremock\"")));

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(String.format("http://localhost:%s/users/1", port));
        HttpResponse httpResponse = httpClient.execute(request);
        String stringResponse = convertHttpRestonseToString(httpResponse);
        verify(getRequestedFor(urlEqualTo(REST_URL + "/1")));
        assertEquals(200, httpResponse.getStatusLine().getStatusCode());
        assertEquals(APPLICATION_JSON, httpResponse.getFirstHeader("Content-Type").getValue());
        assertEquals("\"testing-library\": \"Wiremock\"", stringResponse);


    }

    @Test
    public void givenJUnitManagedServer_whenMatchingBody_thenCorrect() throws IOException {
        stubFor(post(urlEqualTo(REST_URL))
                .withHeader("Content-Type", equalTo(APPLICATION_JSON))
                .withRequestBody(containing("\"name\": \"Ivan\""))
                .withRequestBody(containing("\"email\": \"ivan@gmail.com\""))
                .withRequestBody(containing("\"url\": \"/343.jpg\""))
                .withRequestBody(containing("\"is_online\": true"))
                .withRequestBody(containing("\"status_time\": 555"))
                .willReturn(aResponse().withStatus(201)));

        InputStream jsonInputStream = this.getClass().getClassLoader().getResourceAsStream("wiremock_intro.json");
        String jsonString = convertInputStreamToString(jsonInputStream);
        StringEntity entity = new StringEntity(jsonString);

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost(String.format("http://localhost:%s/users", port));
        request.addHeader("Content-Type", APPLICATION_JSON);
        request.setEntity(entity);
        HttpResponse response = httpClient.execute(request);

        verify(postRequestedFor(urlEqualTo(REST_URL))
                .withHeader("Content-Type", equalTo(APPLICATION_JSON)));
        assertEquals(200, response.getStatusLine().getStatusCode());
    }


    private static String convertHttpRestonseToString(HttpResponse httpResponse) throws IOException {
        InputStream inputStream = httpResponse.getEntity().getContent();
        return convertInputStreamToString(inputStream);
    }

    private static String convertInputStreamToString(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream, "UTF-8");
        String string = scanner.useDelimiter("\\Z").next();
        scanner.close();
        return string;
    }


}
