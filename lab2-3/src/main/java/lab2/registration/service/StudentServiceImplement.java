package lab2.registration.service;

import lab2.registration.model.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

public class StudentServiceImplement implements StudentService {

    private Instructor[] instructors;
    private Student[] bachelorStudents, masterStudents;
    private CourseInfo[] courseInfos;
    private CourseInstance[] courseInstances;
    private HashMap<Long, List<Long>> registration;

    @Override
    public ActionStatus subscribe(long studentId, long courseId) {
        long amountTakenPlaces = registration.values().stream().flatMap(Collection::stream)
                .filter(i -> i == courseId).count();
        CourseInstance ourCourseInstance = Arrays.stream(courseInstances)
                .filter(i -> i.getCourseId() == courseId) // существует искомый курс?
                .filter(i -> i.getStartDate().isAfter(LocalDate.now())) // курс стартовал?
                .filter(i -> i.getCapacity() >= amountTakenPlaces) // есть ли места?
                .findFirst().orElse(null);
        if (ourCourseInstance == null)
            return ActionStatus.NOK;

        CourseInfo courseInfo = Arrays.stream(courseInfos).filter(i -> i.getId() == ourCourseInstance.getCourseId())
                .findFirst().orElse(null);
        if (courseInfo == null)     // такого курса не существует
            return ActionStatus.NOK;

        List<Long> list = registration.computeIfAbsent(studentId, k -> new ArrayList<>());

        Student student = Arrays.stream(courseInfo.getStudentCategories())
                .flatMap(x -> {
                    switch (x) {
                        case BACHELOR:
                            return Arrays.stream(bachelorStudents);
                        case MASTER:
                            return Arrays.stream(masterStudents);
                    }
                    return Stream.empty();
                })
                .filter(i -> i.getId() == studentId)
                .filter(i -> registration.get(i.getId()).stream().noneMatch(x -> x == ourCourseInstance.getId()))
                .findFirst().orElse(null); // поиск студента, еще не записанного на курс
        if (student == null)
            return ActionStatus.NOK;

        long[] prerequisites = courseInfo.getPrerequisites();
        if (prerequisites != null) {
            if (!Arrays.stream(prerequisites).allMatch(i ->
                    Arrays.stream(student.getCompletedCourses()).anyMatch(c -> c == i)))
                return ActionStatus.NOK;
        }

        list.add(ourCourseInstance.getId());
        return ActionStatus.OK;
    }

    @Override
    public ActionStatus unsubscribe(long studentId, long courseId) {
        CourseInstance courseInstance = Arrays.stream(courseInstances)
                .filter(c -> c.getId() == courseId).findFirst().orElse(null);
        if (courseInstance == null)
            return ActionStatus.NOK;
        if (courseInstance.getStartDate().isAfter(LocalDate.now()))
            return ActionStatus.NOK;
        if (registration.get(studentId).remove(courseId))
            return ActionStatus.NOK;
        return ActionStatus.OK;
    }

    @Override
    public CourseInstance[] findAllSubscriptionsByStudentId(long studentId) {
        return Arrays.stream(courseInstances)
                .filter(course -> registration.get(studentId).stream().anyMatch(s -> s == course.getId()))
                .toArray(CourseInstance[]::new);
    }
}