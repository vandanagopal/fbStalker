package domain;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class From {

    private String name;

    public String getName() {
        return name;
    }
}
