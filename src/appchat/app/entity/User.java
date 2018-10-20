package appchat.app.entity;

import java.sql.Date;
import java.util.HashMap;

public class User {
    private int id;
    private String userName;
    private String passWord;
    private String confirmPassword;
    private String fullName;
    private String birthDate;
    private int gender;
    private String address;
    private String email;
    private String phone;
    private int status;

    public User() {
    }

    public User(String userName, String passWord, String confirmPassword, String fullName, String birthDate, int gender, String address, String email, String phone) {
        this.userName = userName;
        this.passWord = passWord;
        this.confirmPassword = confirmPassword;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.address = address;
        this.email = email;
        this.phone = phone;
    }

    public User(int id, String userName, String passWord, String confirmPassword, String fullName, String birthDate, int gender, String address, String email, String phone, int status) {
        this.id = id;
        this.userName = userName;
        this.passWord = passWord;
        this.confirmPassword = confirmPassword;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public HashMap<String, String> isValid() {
        HashMap<String, String> errors = new HashMap<>();
        if (userName.length() == 0 || userName == null) {
            errors.put("username", "Username can't be null or empty");
        } else if (userName.length() < 5) {
            errors.put("username", "Username is too short");
        } else if (passWord.length() == 0 || passWord == null) {
            errors.put("password", "Password can't be null or empty");
        } else if (passWord.length() < 8) {
            errors.put("password", "Password must be more than 8 characters");
        } else if (!(passWord.equals(confirmPassword))) {
            errors.put("password", "Password does not match");
        } else if (fullName.length() == 0 || fullName == null) {
            errors.put("fullName", "Your name can't be null or empty");
        } else if (birthDate.length() == 0 || birthDate.equals("")) {
            errors.put("date", "Please choose your date of birth");
        } else if (address.length() == 0 || address == null) {
            errors.put("address", "Address can't be null or empty");
        } else if (phone.length() == 0 || phone == null) {
            errors.put("phone", "Phone can't be null or empty");
        } else if (email.matches("\t^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$")) {
            errors.put("email", "Email is not in correct format");
        } else if (email.length() == 0 || email == null) {
            errors.put("email", "Email can't be null or empty");
        } else if (gender == 0) {
            errors.put("gender", "Please choose your gender");
        }
        return errors;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", fullName='" + fullName + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", gender=" + gender +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
