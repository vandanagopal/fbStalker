package domain;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.joda.time.DateTime;

import static ch.lambdaj.Lambda.joinFrom;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Record {

    private String id;
    @JsonProperty("created_time")
    private DateTime createdTime;
    @JsonProperty("updated_time")
    private DateTime updatedTime;
    private String message;
    private String name;
    private From from;
    private Comments comments;
    private String story;
    private String type;

    public DateTime getTime() {
        return createdTime == null ? updatedTime : createdTime;
    }

    public DateTime getUpdatedTime() {
        return updatedTime;
    }

    public From getFrom() {
        return from;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Comments getComments() {
        return comments;
    }

    public String getName() {
        return name;
    }

    public String getStory() {
        return story;
    }

    public String getDescription() {
        String description = "";
        if (StringUtils.isNotEmpty(name))
            description += name;
        if (StringUtils.isNotEmpty(message))
            description += message;
        if (StringUtils.isNotEmpty(story))
            description += story;
        return description;
    }
}
