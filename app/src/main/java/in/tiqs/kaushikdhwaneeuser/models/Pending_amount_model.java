package in.tiqs.kaushikdhwaneeuser.models;

/**
 * Created by Sohail on 9/6/2017.
 */
public class Pending_amount_model
{
    private String student_name;

    public String getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(String invoice_id) {
        this.invoice_id = invoice_id;
    }

    public String getInvoice_year() {
        return invoice_year;
    }

    public void setInvoice_year(String invoice_year) {
        this.invoice_year = invoice_year;
    }

    private String invoice_id;

    public boolean isSelection_status() {
        return selection_status;
    }

    public void setSelection_status(boolean selection_status) {
        this.selection_status = selection_status;
    }

    private boolean selection_status;
    private String invoice_year;
    private String enroll_student_id;

    public String getInvoice_month() {
        return invoice_month;
    }

    public void setInvoice_month(String invoice_month) {
        this.invoice_month = invoice_month;
    }

    public String getEnroll_student_id() {
        return enroll_student_id;
    }

    public void setEnroll_student_id(String enroll_student_id) {
        this.enroll_student_id = enroll_student_id;
    }

    private String invoice_month;

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name_amount(String class_name_amount) {
        this.class_name = class_name_amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPaid_amount() {
        return paid_amount;
    }

    public void setPaid_amount(String paid_amount) {
        this.paid_amount = paid_amount;
    }

    public String getStart_date() {
        return start_date;
    }
    public void setStart_date(String start_date){this.start_date=start_date; }

    public String getTotal_sessions() {
        return total_sessions;
    }
    public void setTotal_sessions(String total_sessions){this.total_sessions=total_sessions; }

    public String getSessions_week() {
        return sessions_week;
    }
    public void setSessions_week(String sessions_week){this.sessions_week=sessions_week; }

    public String getPlan() {
        return plan;
    }
    public void setPlan(String plan){this.plan=plan; }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date){this.end_date=end_date; }

    private String class_name;
    private String total_amount;
    private String paid_amount;
    private String date;
    private String start_date;
    private String end_date;
    private String total_sessions;
    private String sessions_week;
    private String plan;

    public String getPenidn_amount() {
        return Penidn_amount;
    }

    public void setPenidn_amount(String penidn_amount) {
        Penidn_amount = penidn_amount;
    }

    private String Penidn_amount;
}
