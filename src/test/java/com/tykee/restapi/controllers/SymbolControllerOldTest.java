package com.tykee.restapi.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.tykee.restapi.RestApiApplication;
import com.tykee.restapi.models.Symbol;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = RestApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SymbolControllerOldTest {
    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    @Test
    public void testGetSymbols() throws JSONException {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
            createURLWithPort("/symbols"),
            HttpMethod.GET, entity, String.class);

        String expected = "[{\"name\":\"EURGBP\",\"digits\":5,\"symbol_id\":1},"
            + "{\"name\":\"EURJPY\",\"digits\":3,\"symbol_id\":2},"
            + "{\"name\":\"EURUSD\",\"digits\":5,\"symbol_id\":3},"
            + "{\"name\":\"GBPJPY\",\"digits\":3,\"symbol_id\":4},"
            + "{\"name\":\"GBPUSD\",\"digits\":5,\"symbol_id\":5},"
            + "{\"name\":\"USDJPY\",\"digits\":3,\"symbol_id\":6}]";

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void testGetSymbolById() throws JSONException {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
            createURLWithPort("/symbols/1"),
            HttpMethod.GET, entity, String.class);

        String expected = "{\"name\":\"EURGBP\",\"digits\":5,\"symbol_id\":1}";

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void testGetSymbolByTicker() throws JSONException {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
            createURLWithPort("/symbols/ticker/EURGBP"),
            HttpMethod.GET, entity, String.class);

        String expected = "{\"name\":\"EURGBP\",\"digits\":5,\"symbol_id\":1}";

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void testGetSymbolByTickerNotFound() {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
            createURLWithPort("/symbols/ticker/NOTFOUND"),
            HttpMethod.GET, entity, String.class);

        int expected_status = 404;

        assertEquals(expected_status, response.getStatusCodeValue());
    }

    @Test
    public void addSymbol() {
        Symbol symbol = new Symbol();
        symbol.setId(99999L);
        symbol.setName("XXXYYY");
        symbol.setDigits(999);

        HttpEntity<Symbol> entity = new HttpEntity<>(symbol, headers);

        ResponseEntity<String> response = restTemplate.exchange(
            createURLWithPort("/symbols"),
            HttpMethod.POST, entity, String.class
        );

        String actual = response.getHeaders().get(HttpHeaders.LOCATION).get(0);

        assertTrue(actual.contains("/symbols/9999"));
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}