package domain;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {

    @JsonProperty("data")
    List<Record> records;
    private ResultType resultType;

    public Result() {
    }

    public Result(List<Record> records, ResultType resultType) {
        this.records = records;
        this.resultType = resultType;
    }

    public List<Record> getRecords() {
        return records;
    }

    public ResultType getResultType() {
        return resultType;
    }
}
