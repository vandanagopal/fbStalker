package domain;

import ch.lambdaj.Lambda;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {

    @JsonProperty("data")
    List<Record> records;

    public Result() {
    }

    public Result(List<Record> photoRecords) {
        this.records = photoRecords;
    }

    public List<Record> getRecords() {
        return records;
    }
}
