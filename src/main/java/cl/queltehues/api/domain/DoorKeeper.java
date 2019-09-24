package cl.queltehues.api.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoorKeeper implements Serializable {
    private String name;
    private String role;
    private String image;
    private String schedule;
}
