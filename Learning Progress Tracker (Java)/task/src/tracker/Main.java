package tracker;

import tracker.course.*;
import tracker.menu.MainMenu;
import tracker.menu.Menu;
import tracker.menu.MenuItem;
import tracker.student.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static java.lang.System.out;
import static tracker.Main.scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);
    public static List<Course> courseList = List.of(new CourseJava(), new CourseDsa(), new CourseDatabase(), new CourseSpring());

    public static void main(String[] args) {

        StudentList studentList = new StudentList(courseList);

        out.println("Learning Progress Tracker");

        Menu mainMenu = new MainMenu("main");
        mainMenu.addItem(new MenuItem("add students",
                () -> {
                    out.println("Enter student credentials or 'back' to return:");
                    studentList.addStudents(mainMenu, 0);
                },
                true));
        mainMenu.addItem(new MenuItem("list",
                studentList::showListOfStudents,
                false));
        mainMenu.addItem(new MenuItem("add points",
                () -> {
                    out.println("Enter an id and points or 'back' to return:");
                    studentList.addPoints(mainMenu);
                },
                true));
        mainMenu.addItem(new MenuItem("find",
                () -> {
                    out.println("Enter an id or 'back' to return:");
                    studentList.find(mainMenu);
                },
                false));
        mainMenu.addItem(new MenuItem("statistics",
                () -> statistics(mainMenu),
                false));
        mainMenu.addItem(new MenuItem("back",
                () -> out.println("Enter 'exit' to exit the program."),
                false));
        mainMenu.addItem(new MenuItem("exit",
                () -> {
                    out.println("Bye!");
                    System.exit(0);
                },
                true));
        mainMenu.run();
    }

    public static void statistics(Menu menu) {
        out.println("Type the name of a course to see details or 'back' to quit:");
        CourseComposite courseComposite = new CourseComposite(courseList);
        out.printf("Most popular: %s\n", courseComposite.getMostPopular()); // najwiecej zapisanych
        out.printf("Least popular: %s\n", courseComposite.getLeastPopular());
        out.printf("Highest activity: %s\n", courseComposite.getHighestActivity()); //najczesciej uczeszczany
        out.printf("Lowest activity: %s\n", courseComposite.getLowestActivity());
        out.printf("Easiest course: %s\n", courseComposite.getEasiestCourse()); //najwyzsza srednia
        out.printf("Hardest course: %s\n", courseComposite.getHardestCourse());

        courseDetailStatistic(menu);
    }

    public static void courseDetailStatistic(Menu menu) {
        String input = scanner.nextLine().trim();
        if (input.equals("back")) {
            menu.run();
        }
        out.println(input);
        Optional<Course> result = courseList.stream().filter(course1 -> course1.getName().equals(input)).findFirst();

        if (result.isPresent()) {
            out.println("id    points    completed");
            result.get().showListResultsPerStudent();
        } else {
            out.println("Unknown course.");
        }
        courseDetailStatistic(menu);
    }
}

class StudentList {
    private final List<Student> studentsList = new ArrayList<>();
    private final List<Course> courseList;

    public StudentList(List<Course> courseList) {
        this.courseList = courseList;
    }

    public void addStudents(Menu menu, int newStudentsCount) {
        String input = scanner.nextLine().trim();
        if (input.equals("back")) {
            out.printf("Total %d students have been added.\n", newStudentsCount);
            menu.run();
        }

        String[] studentData = input.split(" ", 3);
        String name = studentData[0] + " " + studentData[1];
        String email = studentData[2];

        if (studentsList.stream().anyMatch(student -> student.getEmail().equals(email))) {
            out.println("This email is already taken.");
        } else {
            Student newStudent = new Student(name, email);
            studentsList.add(newStudent);
            out.println("The student has been added.");
            newStudentsCount++;
        }
        addStudents(menu, newStudentsCount);
    }

    public void showListOfStudents() {
        out.println("Students:");
        studentsList.forEach(student -> out.println(student.getId()));
    }

    public void addPoints(Menu menu) {
        String input = scanner.nextLine().trim();
        if (input.equals("back")) {
            menu.run();
        }

        String[] inputValues = input.split(" ", 5);
        int studentId = Integer.parseInt(inputValues[0]);
        try {
            int courseJavaPoints = Integer.parseInt(inputValues[1]);
            int courseDsaPoints = Integer.parseInt(inputValues[2]);
            int courseDatabasesPoints = Integer.parseInt(inputValues[3]);
            int courseSpringPoints = Integer.parseInt(inputValues[4]);
            if (courseJavaPoints < 0 || courseDsaPoints < 0 || courseDatabasesPoints < 0 || courseSpringPoints < 0) {
                throw new NumberFormatException();
            }

            if (studentsList.stream().noneMatch(student -> student.getId() == studentId)) {
                out.printf("No student is found for id=%d.\n", studentId);
            } else {
                courseList.forEach(course -> {
                    switch (course.getName()) {
                        case "Java" -> course.addResult(studentId, courseJavaPoints);
                        case "DSA" -> course.addResult(studentId, courseDsaPoints);
                        case "Database" -> course.addResult(studentId, courseDatabasesPoints);
                        case "Spring" -> course.addResult(studentId, courseSpringPoints);
                    }
                });

                out.println("Points updated.");
            }
        } catch (NumberFormatException e) {
            out.println("Incorrect points format.");
        }
        addPoints(menu);
    }

    public void find(Menu menu) {
        String input = scanner.nextLine().trim();
        if (input.equals("back")) {
            menu.run();
        }

        int studentId = Integer.parseInt(input);
        if (studentsList.stream().noneMatch(student -> student.getId() == studentId)) {
            out.printf("No student is found for id=%d.\n", studentId);
        } else {
            out.printf("%d points: Java=%d; DSA=%d; Databases=%d; Spring=%d\n",
                    studentId,
                    courseList.get(0).getResult(studentId),
                    courseList.get(1).getResult(studentId),
                    courseList.get(2).getResult(studentId),
                    courseList.get(3).getResult(studentId));
        }
        find(menu);
    }
}