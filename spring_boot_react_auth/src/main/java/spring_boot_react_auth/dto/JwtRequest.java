package spring_boot_react_auth.dto;

import lombok.Data;

@Data
public class JwtRequest {
    private String email;
    private String password;
}
