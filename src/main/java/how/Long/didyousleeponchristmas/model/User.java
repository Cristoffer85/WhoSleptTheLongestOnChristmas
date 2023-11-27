package how.Long.didyousleeponchristmas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;
import java.util.Set;

@Data
@Document(collection = "users")
public class User implements UserDetails {

    @Id
    private String userId;

    @Indexed(unique = true)
    private String username;
    private String password;
    private int maxHoursSlept = 0;
    private WeekDay weekDay;

    private Set<Role> authorities = new HashSet<>();

    public User() {
        super();
    }

    public User(String userId, String username, String password, Set<Role> authorities, int maxHoursSlept, WeekDay weekDay) {
        super();
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.maxHoursSlept = maxHoursSlept;
        this.weekDay = weekDay;
    }

    @Override
    @JsonIgnore
    public Set<Role> getAuthorities() {
        return this.authorities;
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


    // 2 extra methods, to view only (in the UI) the authority of the user. Not the whole hashset with the customgenerated Mongo-Db value. Thought that was unnecessary.
    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", authorities=" + getAuthorityStrings() +
                ", maxHoursSlept=" + maxHoursSlept +
                ", weekDay=" + weekDay +
                '}';
    }

    public String getAuthorityStrings() {
        StringBuilder result = new StringBuilder("[");
        for (Role role : authorities) {
            result.append(role.getAuthority()).append(", ");
        }
        if (!authorities.isEmpty()) {
            result.delete(result.length() - 2, result.length());
        }
        result.append("]");
        return result.toString();
    }
}