package shubham.in.bustimings.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserResponse {
    @Expose
    @SerializedName("updated_rows")
    private String updated_rows;
    @Expose
    @SerializedName("status_code")
    private String status_code;
    @Expose
    @SerializedName("status")
    private String status;

    public String getUpdated_rows() {
        return updated_rows;
    }

    public void setUpdated_rows(String updated_rows) {
        this.updated_rows = updated_rows;
    }

    public String getStatus_code() {
        return status_code;
    }

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
