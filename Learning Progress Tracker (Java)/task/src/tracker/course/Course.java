package tracker.course;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

public abstract class Course implements CourseInterface {
    protected String name;
    protected int requirementNumberOfPoints;
    protected int countOfAttempts = 0;
    protected Map<Integer, Integer> courseResults = new HashMap<>();
    protected List<Integer> pointsHistory = new ArrayList<>();

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void addResult(int studentId, int newResult) {
        if (newResult != 0) {
            int beforeResult = 0;
            if (courseResults.containsKey(studentId)) {
                beforeResult = courseResults.get(studentId);
            }
            courseResults.put(studentId, beforeResult + newResult);

            countOfAttempts++;
            pointsHistory.add(requirementNumberOfPoints);
        }
    }

    @Override
    public int getResult(int studentId) {
        return courseResults.getOrDefault(studentId, 0);
    }

    public int getCountOfAttempts() {
        return countOfAttempts;
    }

    public double getAverage() {
        if (countOfAttempts != 0) {
            int sum = pointsHistory.stream().mapToInt(Integer::intValue).sum();
            BigDecimal value = new BigDecimal(sum / countOfAttempts);
            return value.setScale(2, RoundingMode.HALF_UP).doubleValue();
        } else {
            return 0.0;
        }
    }

    public int getCountOfStudents() {
        return courseResults.size();
    }

    public void showListResultsPerStudent() {
        Map<Integer, Integer> sortedList = courseResults.entrySet()
                .stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue())) // Sort by values descending
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new // Maintain insertion order
                ));
        sortedList.forEach((key, value) -> System.out.printf("%s %s        %s%%\n",
                key,
                value,
                new DecimalFormat("#.##").format(Math.round(value * 100 /requirementNumberOfPoints))));
    }
}

