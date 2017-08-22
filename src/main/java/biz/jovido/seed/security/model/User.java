package biz.jovido.seed.security.model;

import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

/**
 * @author Stephan Grundner
 */
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role")
    @MapKey(name = "authority")
    private final Map<String, Role> roles = new HashMap<>();

    private String password;

    @Column(unique = true)
    private String name;

    private boolean accountExpired;
    private boolean accountLocked;
    private boolean credentialsExpired;
    private boolean disabled;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<String, Role> getRoles() {
        return Collections.unmodifiableMap(roles);
    }

    public void addRole(Role role) {
        roles.put(role.getAuthority(), role);
    }

    public void removeRole(Role role) {
        roles.remove(role.getAuthority());
    }

    public boolean hasRole(String authority) {
        return roles.containsKey(authority);
    }

    @Override
    public Collection<Role> getAuthorities() {
        return Collections.unmodifiableCollection(roles.values());
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getUsername() {
        return name;
    }

    public boolean isAccountExpired() {
        return accountExpired;
    }

    public void setAccountExpired(boolean accountExpired) {
        this.accountExpired = accountExpired;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !accountExpired;
    }

    public boolean isAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    public boolean isCredentialsExpired() {
        return credentialsExpired;
    }

    public void setCredentialsExpired(boolean credentialsExpired) {
        this.credentialsExpired = credentialsExpired;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !credentialsExpired;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    @Override
    public boolean isEnabled() {
        return !disabled;
    }
}
