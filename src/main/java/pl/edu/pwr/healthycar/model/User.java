package pl.edu.pwr.healthycar.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class User {

    @Id
    private String id;

    private String firstName;
    private String lastName;

    @Indexed(unique = true)
    private String email;

    private String password;
    private Boolean isAdmin;
    private Boolean isFO;
    private Integer carCount;

    public User(){

    }

    public User(String firstName, String lastName, String email, String password, Boolean isAdmin, Boolean isFO, Integer carCount) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
        this.isFO = isFO;
        this.carCount = carCount;
    }

    public User(String firstName, String lastName, String email, String password, Boolean isFO) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.isAdmin = false;
        this.isFO = isFO;
        this.carCount = 0;
    }
}
