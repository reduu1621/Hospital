package Entity; // that contain doctor information

public class Doctor {
    private int doctorId;
    private String name;
    private String specialization;
    private boolean availability;

    public Doctor(int doctorId, String name, String specialization, boolean availability) {
        this.doctorId = doctorId;
        this.name = name;
        this.specialization = specialization;
        this.availability = availability;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public String getName() {//get the name of the doctor
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {//set the availability of the doctor
        this.availability = availability;
    }
}
