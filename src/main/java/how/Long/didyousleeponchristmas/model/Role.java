package how.Long.didyousleeponchristmas.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

@Data
@Document(collection = "Roles")
public class Role implements GrantedAuthority {

    @Id
    private String roleId;
    private Integer customRoleId;
    private String authority;

    public Role() {
        super();
    }

    public Role(String authority) {
        this.authority = authority;
    }

    public Role(Integer customRoleId, String authority) {
        this.customRoleId = customRoleId;
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
}