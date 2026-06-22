package spring_boot_react_auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String firstName;
    private String lastName;
    private String email;
}
