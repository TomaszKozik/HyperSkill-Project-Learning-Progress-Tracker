package tracker.course;

interface CourseInterface {
    String getName();

    void addResult(int studentId, int newResult);

    int getResult(int studentId);
}