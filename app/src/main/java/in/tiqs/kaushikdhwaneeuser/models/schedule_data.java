package in.tiqs.kaushikdhwaneeuser.models;

/**
 * Created by Administrator on 7/30/2018.
 */
public class schedule_data
{
    public String getBatch_name() {
        return batch_name;
    }

    public void setBatch_name(String batch_name) {
        this.batch_name = batch_name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    private String batch_name;
    private String time;
    private String teacher_name;


}
