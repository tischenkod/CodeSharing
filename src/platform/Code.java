package platform;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sun.istack.NotNull;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
public class Code {
    private static final String DATE_FORMATTER = "yyyy-MM-dd HH:mm:ss";

    @Id
    @GeneratedValue
    @JsonIgnore
    Integer id;

    @NotNull
    @JsonIgnore
    String uuid;

    Integer time;

    Integer views;

    @NotNull
    String code;

    @NotNull
    LocalDateTime date;

    public Integer getId() {
        return id;
    }

    public String  getUuid() {
        return uuid;
    }

    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate() {
        return DateTimeFormatter.ofPattern(DATE_FORMATTER).format(date);
    }

    public Code() {

    }

    public void init() {
        uuid = UUID.randomUUID().toString();
        date = LocalDateTime.now();
    }

}
