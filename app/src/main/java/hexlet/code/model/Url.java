package hexlet.code.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.Instant;

@Getter
@Setter
public class Url {
    private long id;
    private String name;
    private Timestamp createdAt;

    public Url(String name) {
        this.name = name;
        this.createdAt = Timestamp.from(Instant.now());
    }
}
