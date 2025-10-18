import java.io.*;


public class Registration {
    private String legalName;
    private String username;
    private String password;
    private String dateOfBirth;

    public Registration(String lN, String u, String p, String dOB) {
        legalName = lN;
        username = u;
        password = p;
        dateOfBirth = dOB;
    }

    // getters and setters
    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String newLegalName) {
        legalName = newLegalName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String newUsername) {
        username = newUsername;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String newPassword){
        password = newPassword;
    }

    public String getDateOfBirth(String newDateofBirth) {
        dateOfBirth = newDateofBirth;
    }

    public Registration signup() {
        Registration account = new Registration(legalName, username, password, dateOfBirth);

        try(FileWriter myWriter = new FileWriter("accounts.txt")) {
            myWriter.write("Legal Name: " + legalName + "\n" + "Username: " + username + "\n" + )
        }

    }


}