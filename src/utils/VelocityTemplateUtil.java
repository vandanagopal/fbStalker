package utils;

import domain.Record;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringWriter;
import java.util.List;
import java.util.Properties;

public class VelocityTemplateUtil {

    private static Template template;

    public static String constructEmailBody(List<Record> photoRecords, List<Record> feedRecords){
        Template template = getTemplate();
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("photoRecords",photoRecords);
        velocityContext.put("feedRecords",feedRecords);
        StringWriter writer = new StringWriter();
        template.merge(velocityContext, writer);
        return writer.toString();
    }

    private static Template getTemplate() {
        if(template!=null) return template;
        Properties props = new Properties();
        props.setProperty("resource.loader", "class");
        props.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        VelocityEngine velocityEngine = new VelocityEngine(props);
        template = velocityEngine.getTemplate("emailBody.vm");
        return template;
    }
}
