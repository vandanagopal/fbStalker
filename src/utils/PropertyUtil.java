package utils;

import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.IOException;
import java.util.Properties;

public class PropertyUtil {

    private static Properties properties;

    public static String getProperty(String key) {
        if(properties==null)
            initProperties();
        return (String) properties.get(key);
    }

    public static String getPhotoUrl() {
        return "https://graph.facebook.com/"+getProperty("fb.stalkee")+"/photos?method=GET&format=json&callback=___GraphExplorerAsyncCallback___&access_token=" + getProperty("access.token");
    }
    public static String getFeedUrl() {
        return "https://graph.facebook.com/"+getProperty("fb.stalkee")+"/feed?method=GET&format=json&callback=___GraphExplorerAsyncCallback___&access_token=" + getProperty("access.token");
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
