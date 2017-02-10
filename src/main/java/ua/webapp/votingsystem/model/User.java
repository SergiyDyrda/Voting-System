package ua.webapp.votingsystem.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.Set;


@Entity
@NamedQueries({
        @NamedQuery(name = User.DELETE, query = "DELETE FROM User u WHERE u.id=:id"),
        @NamedQuery(name = User.GET_BY_EMAIL, query = "SELECT u FROM User u WHERE u.email=:email"),
        @NamedQuery(name = User.GET_ALL, query = "SELECT u FROM User u ORDER BY u.name")
})
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "email", name = "users_unique_email_idx")})
public class User extends NamedEntity {

    public static final String DELETE = "User.delete";
    public static final String GET_BY_EMAIL = "User.getByEmail";
    public static final String GET_ALL = "User.getAll";

    @Column(name = "email", nullable = false, unique = true)
    @Email
    @NotEmpty
    @SafeHtml
    private String email;

    @Column(name = "password", nullable = false)
    @Length(min = 5)
    @NotEmpty
    @SafeHtml
    private String password;

    @Column(name = "enabled", nullable = false)
    private boolean enabled = true;


    @Column(name = "registered", nullable = false, columnDefinition = "timestamp default now()")
    private LocalDateTime registered = LocalDateTime.now();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "voted_restaurant_id")
    private Restaurant votedRestaurant;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    public User() {
    }

    public User(Integer id, String name, String email, String password, boolean enabled, Role role, Role... roles) {
        this(id, name, email, password, enabled, EnumSet.of(role, roles));
    }

    public User(Integer id, String name, String email, String password, Role role, Role... roles) {
        this(id, name, email, password, true, EnumSet.of(role, roles));
    }


    public User(Integer id, String name, String email, String password, boolean enabled, Set<Role> roles) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public LocalDateTime getRegistered() {
        return registered;
    }

    public void setRegistered(LocalDateTime registered) {
        this.registered = registered;
    }

    public Restaurant getVotedRestaurant() {
        return votedRestaurant;
    }

    public void setVotedRestaurant(Restaurant votedRestaurant) {
        this.votedRestaurant = votedRestaurant;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", registered=" + registered +
                ", roles=" + roles +
                '}';
    }
}
