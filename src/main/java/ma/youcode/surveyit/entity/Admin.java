package ma.youcode.surveyit.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "ADMINS")
public class Admin extends User{
    @Override
    protected String getUserRole() {
        return "ADMIN";
    }
}
