package tracker.course;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourseComposite {
    private final List<Course> children = new ArrayList<>();

    public CourseComposite(List<Course> courseList) {
        children.addAll(courseList);
    }

    public String getMostPopular() {
        List<String> result = children.stream()
                .filter(a -> a.getCountOfStudents() > 0)
                .sorted((e1, e2) -> Integer.compare(e2.getCountOfStudents(), e1.getCountOfStudents()))
                .limit(3)
                .map(Course::getName)
                .toList();
        return (!result.isEmpty()) ? String.join(", ", result) : "n/a";
    }

    public String getLeastPopular() {
        Optional<Course> result = children.stream()
                .filter(a -> a.getCountOfStudents() > 0)
                .min((e1, e2) -> Integer.compare(e1.getCountOfStudents(), e2.getCountOfStudents()));
        return (result.isPresent()) ? result.get().getName() : "n/a";
    }

    public String getHighestActivity() {
        Optional<Course> result = children.stream()
                .filter(a -> a.getCountOfAttempts() > 0)
                .max((e1, e2) -> Integer.compare(e1.getCountOfAttempts(), e2.getCountOfAttempts()));
        return (result.isPresent()) ? result.get().getName() : "n/a";
    }

    public String getLowestActivity() {
        Optional<Course> result = children.stream()
                .filter(a -> a.getCountOfAttempts() > 0)
                .min((e1, e2) -> Integer.compare(e1.getCountOfAttempts(), e2.getCountOfAttempts()));
        return (result.isPresent()) ? result.get().getName() : "n/a";
    }

    public String getEasiestCourse() {
        Optional<Course> result = children.stream()
                .filter(a -> a.getAverage() > 0)
                .max((e1, e2) -> Double.compare(e1.getAverage(), e2.getAverage()));
        return (result.isPresent()) ? result.get().getName() : "n/a";
    }

    public String getHardestCourse() {
        Optional<Course> result = children.stream()
                .filter(a -> a.getAverage() > 0)
                .min((e1, e2) -> Double.compare(e1.getAverage(), e2.getAverage()));
        return (result.isPresent()) ? result.get().getName() : "n/a";
    }

}
