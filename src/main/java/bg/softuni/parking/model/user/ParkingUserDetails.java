package bg.softuni.parking.model.user;//package bg.softuni.parking.model.user;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//
//import java.util.Collection;
//
//public class ParkingUserDetails  extends User {
//
//    private final String firstName;
//    private final String lastName;
//
//    public ParkingUserDetails(String username,
//                              String password,
//                              Collection<? extends GrantedAuthority> authorities,
//                              String firstName,
//                              String lastName) {
//        super(username, password, authorities);
//
//        this.firstName = firstName;
//        this.lastName = lastName;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public String getFullName() {
//
//        StringBuilder fullName = new StringBuilder();
//        if (firstName != null) {
//            fullName.append(firstName);
//        }
//        if (lastName != null) {
//            if (!fullName.isEmpty()) {
//                fullName.append(" ");
//            }
//            fullName.append(lastName);
//        }
//
//        return fullName.toString();
//    }
//
//}


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class ParkingUserDetails extends User {

    private final String firstName;
    private final String lastName;
    private final String phone;
    private final String email;

    public ParkingUserDetails(String username,
                              String password,
                              Collection<? extends GrantedAuthority> authorities,
                              String firstName,
                              String lastName,
                              String phone,
                              String email) {
        super(username, password, authorities);
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        StringBuilder fullName = new StringBuilder();
        if (firstName != null) {
            fullName.append(firstName);
        }
        if (lastName != null) {
            if (!fullName.isEmpty()) {
                fullName.append(" ");
            }
            fullName.append(lastName);
        }
        return fullName.toString();
    }
}
