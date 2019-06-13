package cl.queltehues.api.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class Vecino implements Serializable {

    private String username;
    private String password;
    private Boolean rememberMe;
}
