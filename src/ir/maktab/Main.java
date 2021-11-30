package ir.maktab;

import ir.maktab.model.Course;
import ir.maktab.model.Information;
import ir.maktab.model.Student;
import ir.maktab.model.StudentCourseRating;
import ir.maktab.service.CourseService;
import ir.maktab.service.StudentCourseRatingsService;
import ir.maktab.service.StudentService;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Main {

    public static void main(String[] args) {
        String csvFilePath = "D:\\barnamenevisi\\bootcamp\\homeworks\\projects\\hw13\\io-student\\resources\\student.csv";

        ICsvBeanReader beanReader = null;
        CellProcessor[] processors = new CellProcessor[]{
                new NotNull(), // course name
                new NotNull(), // student name
                new ParseTimestamp(), // timestamp
                new ParseDouble(), // rating
                new Optional()// comment
        };

        try {
            StudentService studentService = new StudentService();
            CourseService courseService = new CourseService();
            StudentCourseRatingsService studentCourseRatingsService = new StudentCourseRatingsService();

            beanReader = new CsvBeanReader(new FileReader(csvFilePath), CsvPreference.STANDARD_PREFERENCE);
            beanReader.getHeader(true); // skip header line
            String[] header = {"courseName", "studentName", "timestamp", "rating", "comment"};
            Information information = null;

            while ((information = beanReader.read(Information.class, header, processors)) != null) {
                String courseName = information.getCourseName();
                String studentName = information.getStudentName();
                Timestamp timestamp = information.getTimestamp();
                double rating = information.getRating();
                String comment = information.getComment();

                Student student = new Student(studentName);
                studentService.saveToDao(student);
                int studentId = studentService.getStudentId(student);
                Course course = new Course(courseName,timestamp,studentId);
                courseService.saveToDao(course);
                int courseId = courseService.getCourseId(course);
                StudentCourseRating studentCourseRating = new StudentCourseRating(rating,comment,studentId,courseId);
                studentCourseRatingsService.saveToDao(studentCourseRating);
            }

            beanReader.close();

        } catch (IOException | ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }
}

