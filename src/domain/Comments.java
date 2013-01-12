package domain;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Comments {

    @JsonProperty("data")
    List<Record> records;

    public List<Record> getRecords() {
        return records;
    }
}
