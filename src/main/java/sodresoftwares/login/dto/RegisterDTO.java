package sodresoftwares.login.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import sodresoftwares.login.model.user.UserRole;

public record RegisterDTO(
        @NotBlank(message = "Login is required")
        String login,

        @NotBlank(message = "Password is required")
        String password,

        @NotNull(message = "Role is required")
        UserRole role
) {
}
