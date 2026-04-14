package ntnu.idi.idatt2106.pilt.features.auth;

import ntnu.idi.idatt2106.pilt.features.user.model.Student;
import ntnu.idi.idatt2106.pilt.features.user.model.Teacher;
import ntnu.idi.idatt2106.pilt.features.user.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails {

    private final User user;

    public UserPrincipal(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(user.getRole().getAuthority()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        // This is where you solve the Teacher vs Student login difference
        if (user instanceof Teacher teacher) {
            return teacher.getSchoolEmail();
        }
        if (user instanceof Student student) {
            return student.getFeideUsername();
        }
        throw new IllegalStateException("Unknown user type");
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }


    public User getUser() {
        return user;
    }

    public Long getUserId() {
        return user.getId();
    }
}