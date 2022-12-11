package lab2.registration.service;

import lab2.registration.model.CourseInfo;
import lab2.registration.model.CourseInstance;
import lab2.registration.model.Instructor;
import lab2.registration.model.Student;
import lab2.registration.reader.CourseDataReader;
import lab2.registration.reader.InstructorDataReader;
import lab2.registration.reader.StudentDataReader;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseInstructorServiceImplement implements CourseInstructorService {

    private Instructor[] instructors;
    private Student[] bachelorStudents, masterStudents;
    private CourseInfo[] courseInfos;
    private CourseInstance[] courseInstances;
    private HashMap<Long, List<Long>> registration;

    public CourseInstructorServiceImplement() {
        try {
            StudentDataReader studentDataReader = new StudentDataReader();
            bachelorStudents = studentDataReader.readBachelorStudentData();
            masterStudents = studentDataReader.readMasterStudentData();

            CourseDataReader courseDataReader = new CourseDataReader();
            courseInfos = courseDataReader.readCourseInfoData();
            courseInstances = courseDataReader.readCourseInstanceData();

            InstructorDataReader instructorDataReader = new InstructorDataReader();
            instructors = instructorDataReader.readInstructorData();
        }

        catch (IOException e) {
            System.out.println("[ERROR] Проблема при чтении файлов");
        }

        registration = new HashMap<Long, List<Long>>();
    }
    @Override
    public Student[] findStudentsByCourseId(long courseId) {
        return registration.entrySet().stream().filter(x -> x.getValue().contains(courseId))
                .filter(x -> Arrays.stream(courseInstances)
                        .filter(c -> x.getValue().contains(c.getId())).findFirst().orElse(null) != null)
                .map(Map.Entry::getKey).map(this::findStudentByStudentId)
                .toArray(Student[]::new);
    }

    private Student findStudentByStudentId(long studentId) {
        Student bach = Arrays.stream(bachelorStudents).filter(s -> s.getId() == studentId).findFirst().orElse(null);
        Student master = Arrays.stream(masterStudents).filter(s -> s.getId() == studentId).findFirst().orElse(null);
        if (bach != null)
            return bach;
        if (master != null)
            return master;
        return null;
    }

    @Override
    public Student[] findStudentsByInstructorId(long instructorId) {
        Instructor instructor = Arrays.stream(instructors)
                .filter(i -> i.getId() == instructorId)
                .findFirst()
                .orElse(null);
        if (instructor == null)
            return new Student[0];
        if (instructor.getCanTeach().length == 0)
            return new Student[0];

        return Arrays.stream(courseInstances).filter(i -> i.getInstructorId() == instructorId)
                .map(c -> findStudentsByCourseId(c.getId()))
                .flatMap(x -> Arrays.stream(x).distinct())
                .toArray(Student[]::new);
    }

    @Override
    public Instructor[] findReplacement(long instructorId, long courseId) {
        return Arrays.stream(instructors)
                .filter(i -> Arrays.stream(i.getCanTeach()).anyMatch(c -> c == courseId) && i.getId() != instructorId)
                .toArray(Instructor[]::new);

        //
    }

}
