package pl.edu.pwr.healthycar.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginInfo {
    String email;
    String password;
}
