package utils;

import domain.ResultType;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class PropertyUtil {

    private static Properties properties;
    private static HashMap<ResultType,String> urlMap=new HashMap<ResultType,String>(){{put(ResultType.Photo,getPhotoUrl());put(ResultType.Feed, getFeedUrl());put(ResultType.Status, getStatusUrl());}};;

    public static String getProperty(String key) {
        if(properties==null)
            initProperties();
        return (String) properties.get(key);
    }

    public static String getPhotoUrl() {
        return getUrl("photos");
    }

    public static HashMap getPollingUrls(){
        return urlMap;
    }

    private static String getUrl(final String actionParameter) {
        return "https://graph.facebook.com/"+getProperty("fb.stalkee")+ "/" + actionParameter + "?method=GET&format=json&callback=___GraphExplorerAsyncCallback___&access_token=" + getProperty("access.token");
    }

    public static String getFeedUrl() {
        return getUrl("feed");
    }

    public static String getStatusUrl() {
        return getUrl("statuses");
    }

    public static Integer getPollIntervalInMillis() {
        return Integer.parseInt(getProperty("poll.interval.in.millis"));
    }

    private static void initProperties() {
        properties=new Properties();
        try {
            properties.load(new ClasspathResourceLoader().getResourceStream("application.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Could not init properties "+e.getMessage());
        }
    }
}
