package safeseattle.model;

public class Users extends Persons {
    protected UserType userType;

    public Users(int userId, String username, String password, String firstName, String lastName, String phoneNumber, String email, String address, String neighborhood, UserType userType) {
        super(userId, username, password, firstName, lastName, phoneNumber, email, address, neighborhood);
        this.userType = userType;
    }

    public Users(String username, String password, String firstName, String lastName, String phoneNumber, String email, String address, String neighborhood, UserType userType) {
        super(username, password, firstName, lastName, phoneNumber, email, address, neighborhood);
        this.userType = userType;
    }

    public Users(Persons person, UserType userType) {
        super(person.getUserId(), person.getUsername(), person.getPassword(), person.getFirstName(), person.getLastName(), person.getPhoneNumber(), person.getEmail(), person.getAddress(),
                person.getNeighborhood());
        this.userType = userType;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
