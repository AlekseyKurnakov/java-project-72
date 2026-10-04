package hexlet.code.model;

import lombok.Getter;
import lombok.Setter;
import java.time.Instant;

@Getter
public class Url {
    @Setter
    private Long id;
    private String name;
    @Setter
    private Instant createdAt;


    public Url(String name) {
        this.name = name;
    }
}
