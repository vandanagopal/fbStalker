package utils;


import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpUtil {

    public static String getLatestUpdate(String url) throws IOException {
        String response = executeHttpGet(url);
        return reformatJson(response);
    }

    private static String reformatJson(String response) {
        Pattern p = Pattern.compile("\\/\\**\\/ ___GraphExplorerAsyncCallback___\\((.*)\\)");
        Matcher matcher = p.matcher(response);
        if (!matcher.find())
            throw new RuntimeException("Match not found");

        return matcher.group(1);
    }


    private static String executeHttpGet(String url) throws IOException, RestClientException {
        ResponseEntity<String> response = new RestTemplate().getForEntity(url, String.class, null);
        return response.getBody();
    }
}
