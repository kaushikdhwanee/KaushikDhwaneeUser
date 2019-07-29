package in.tiqs.kaushikdhwaneeuser.utils;

/**
 * Created by TechIq on 3/17/2017.
 */

public class RslvItem {

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    String batch;
    String time;
    boolean status;
}
