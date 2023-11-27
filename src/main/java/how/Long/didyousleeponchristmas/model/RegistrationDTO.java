package how.Long.didyousleeponchristmas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDTO {          // Class that Registers the username and password as Data-Transfer-Object, to a String
    private String username;
    private String password;
    private int maxHoursSlept;
    private WeekDay weekday;

    @Override
    public String toString() {
        return "Registration info: Username: " + this.username + " Password: " + this.password + " Max Hours slept: " + this.maxHoursSlept + " Weekday: " + this.weekday;
    }
}