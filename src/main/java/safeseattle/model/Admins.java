package safeseattle.model;

public class Admins extends Persons {
    protected EditAccess editAccess;

    public Admins(int userId, String username, String password, String firstName, String lastName, String phoneNumber, String email, String address, String neighborhood, EditAccess editAccess) {
        super(userId, username, password, firstName, lastName, phoneNumber, email, address, neighborhood);
        this.editAccess = editAccess;
    }

    public Admins(String username, String password, String firstName, String lastName, String phoneNumber, String email, String address, String neighborhood, EditAccess editAccess) {
        super(username, password, firstName, lastName, phoneNumber, email, address, neighborhood);
        this.editAccess = editAccess;
    }

    public Admins(Persons person, EditAccess editAccess) {
        super(person.getUserId(), person.getUsername(), person.getPassword(), person.getFirstName(), person.getLastName(), person.getPhoneNumber(), person.getEmail(), person.getAddress(),
                person.getNeighborhood());
        this.editAccess = editAccess;
    }

    public EditAccess getEditAccess() {
        return editAccess;
    }

    public void setEditAccess(EditAccess editAccess) {
        this.editAccess = editAccess;
    }
}
