package tracker.student;

public class Student extends Person {
    private final int id;
    private final String name;
    private final String email;

    public Student(String name, String email) {
        this.id = idMin++;
        this.name = name;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return id + " " + name + " " + email;
    }

    public int getId() {
        return id;
    }
}