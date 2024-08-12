package smartcast.uz.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import smartcast.uz.enums.UserStatus;

import java.util.Collection;
import java.util.List;

@Entity(name = "users")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity<Long>  implements UserDetails {
    @Column(unique = true, length = 32, nullable = false)
    String username;
    String password;
    String name;
    @Column(length = 320, unique = true)
    String email;
    @Column(length = 12)
    String phoneNumber;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 64)
    UserStatus status = UserStatus.ACTIVE;
    @Column(unique = true, length = 14)
    String pinfl;

    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
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
