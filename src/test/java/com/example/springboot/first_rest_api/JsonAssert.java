package com.example.springboot.first_rest_api;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

public class JsonAssert {

    @Test
    void jsonAssert_learnBasic() throws JSONException {
        String expectedResponse = """
                {
                    "id":"Question1",
                    "options":["AWS","Azure","Google Cloud","Oracle Cloud"],
                    "correctAnswer":"AWS"
                }
                """;
        String actualResponse = """
                  {
                      "id":"Question1",
                      "description":"Most Popular Cloud Platform Today",
                      "options":["AWS","Azure","Google Cloud","Oracle Cloud"],
                      "correctAnswer":"AWS"
                  }
                """;
        JSONAssert.assertEquals(expectedResponse, actualResponse, false);
    }
}
