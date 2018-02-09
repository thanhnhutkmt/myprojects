package thundertrainingcenter.software.nhut.thundertrainingcenter.DO;

/**
 * Created by Nhut on 9/3/2017.
 */

public class ExamResult {
    String courseName;
    String mark;
    String examDate;
    String openDay;
    String schedule;

    @Override
    public String toString() {
        return "ExamResult{" +
            "courseName='" + courseName + '\'' +
            ", mark='" + mark + '\'' +
            ", examDate='" + examDate + '\'' +
            ", openDay='" + openDay + '\'' +
            ", schedule='" + schedule + '\'' +
            '}';
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }

    public String getOpenDay() {
        return openDay;
    }

    public void setOpenDay(String openDay) {
        this.openDay = openDay;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }
}
