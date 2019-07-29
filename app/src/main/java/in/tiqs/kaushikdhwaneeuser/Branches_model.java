package in.tiqs.kaushikdhwaneeuser;

/**
 * Created by Sohail on 6/29/2017.
 */
public class Branches_model
{
    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public String getReg_fee() {
        return reg_fee;
    }

    public void setReg_fee(String reg_fee) {
        this.reg_fee = reg_fee;
    }

    private String branch_name;
    private String reg_fee;
    private String branch_id;

    public String getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(String branch_id) {
        this.branch_id = branch_id;
    }
}
