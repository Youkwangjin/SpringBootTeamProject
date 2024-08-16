package pack.model.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pack.role.UserRole;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    private int userId;

    private String userUUId;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private String userEmail;

    @NotBlank
    @Pattern(regexp = "^(?=.*[!@#$%^&*()_+{}\":;'<>?,./]).{10,}$")
    private String userPassword;

    @NotBlank
    @Pattern(regexp = "^[가-힣a-zA-Z]{2,10}$")
    private String userDisplayName;

    @NotBlank
    @Pattern(regexp = "^01(0|1|[6-9])[0-9]{3,4}[0-9]{4}$")
    private String userTel;

    private String userAddress;

    private LocalDate userCreated;

    private UserRole userRole;

    @Override
    public String getUsername() {
        return getUserEmail();
    }

    @Override
    public String getPassword() {
        return getUserPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + getUserRole()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}