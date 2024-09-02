package pack.model.owner;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pack.role.OwnerRole;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Owner implements UserDetails {

    private int ownerId;

    private String ownerUUId;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private String ownerEmail;

    @NotBlank
    @Pattern(regexp = "^(\\d{3}-\\d{2}-\\d{5}|\\d{10})$")
    private String ownerBusinessNum;

    @NotBlank
    @Pattern(regexp = "^(?=.*[!@#$%^&*()_+{}\":;'<>?,./]).{10,}$")
    private String ownerPassword;

    @NotBlank
    @Pattern(regexp = "^[가-힣a-zA-Z]{2,10}$")
    private String ownerName;

    @NotBlank
    @Pattern(regexp = "^01(0|1|[6-9])[0-9]{3,4}[0-9]{4}$")
    private String ownerTel;

    @NotBlank
    @Pattern(regexp = "^[0-9\\p{L}&\\-'.\\s]{2,50}$")
    private String ownerCompanyName;

    private String ownerAddress;

    private LocalDate ownerCreated;

    private LocalDate ownerUpdated;

    private OwnerRole ownerRole;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + getOwnerRole()));
    }

    @Override
    public String getPassword() {
        return getOwnerPassword();
    }

    @Override
    public String getUsername() {
        return getOwnerBusinessNum();
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
