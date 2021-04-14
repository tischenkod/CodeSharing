package platform;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NewCodeResponse {
    @JsonProperty(value = "id")
    String uuid;

    public String getId() {
        return uuid;
    }

    public NewCodeResponse(String uuid) {
        this.uuid = uuid;
    }

}
