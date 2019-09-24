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
public class CommonExpense implements Serializable {
    private String house;
    private String [] gc;
    private String difference;
}
