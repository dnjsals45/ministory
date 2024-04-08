package seongmin.ministory.common.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import seongmin.ministory.domain.user.entity.User;

import java.util.Collection;

@Getter
public class CustomUserDetails implements UserDetails {

    private Long userId;
    private String oauthId;
    private String role;

    @JsonIgnore
    private boolean enabled;
    @JsonIgnore
    private boolean password;
    @JsonIgnore
    private boolean username;
    @JsonIgnore
    private boolean authorities;
    @JsonIgnore
    private boolean credentialsNonExpired;
    @JsonIgnore
    private boolean accountNonExpired;
    @JsonIgnore
    private boolean accountNonLocked;

    private CustomUserDetails() {}

    @Builder
    private CustomUserDetails(Long userId, String oauthId, String role) {
        this.userId = userId;
        this.oauthId = oauthId;
        this.role = role;
    }

    public static UserDetails of(User user) {
        return new CustomUserDetails(user.getId(), user.getOauthId(), user.getRole());
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
