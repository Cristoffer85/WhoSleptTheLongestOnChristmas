package how.Long.didyousleeponchristmas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {     // Class that uses the
    private User user;
    private String jwt;
}