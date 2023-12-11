package pl.edu.pwr.healthycar.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PasswordChange {
    String userId;
    String currentPassword;
    String newPassword;
}
