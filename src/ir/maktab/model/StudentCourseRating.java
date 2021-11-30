package ir.maktab.model;

public class StudentCourseRating {
    private double rating;
    private String comment;
    private int studentId;
    private int courseId;

    public StudentCourseRating(double rating, String comment, int studentId, int courseId) {
        this.rating = rating;
        this.comment = comment;
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
