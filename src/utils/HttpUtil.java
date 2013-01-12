package utils;


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

    private static String executeHttpGet(String url) throws IOException {
        URLConnection urlConnection = new URL(url).openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        InputStreamReader in = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(in);
        String line, result = "";
        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }
        bufferedReader.close();
        return result;
    }
}
