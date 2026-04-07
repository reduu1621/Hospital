package Entity; //assigning pationt info

public class Patient {
    private String name;
    private int age;
    private String gender;
    private String contact;
    private String selectedDoctor;

    public Patient(String name, int age, String gender, String contact, String selectedDoctor) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.contact = contact;
        this.selectedDoctor = selectedDoctor;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getContact() {
        return contact;
    }

    public String getSelectedDoctor() {
        return selectedDoctor;
    }

    // Optionally: Setters can be added if needed
}
