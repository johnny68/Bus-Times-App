package shubham.in.bustimings.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {

    @Expose
    @SerializedName("data")
    private List<DataEntity> data;
    @Expose
    @SerializedName("status_code")
    private String status_code;
    @Expose
    @SerializedName("status")
    private String status;

    public List<DataEntity> getData() {
        return data;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
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

    public static class DataEntity {
        @Expose
        @SerializedName("lastname")
        private String lastname;
        @Expose
        @SerializedName("emailid")
        private String emailid;
        @Expose
        @SerializedName("middlename")
        private String middlename;
        @Expose
        @SerializedName("user_id")
        private String user_id;
        @Expose
        @SerializedName("mobilenumber")
        private String mobilenumber;
        @Expose
        @SerializedName("password")
        private String password;
        @Expose
        @SerializedName("firstname")
        private String firstname;

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getEmailid() {
            return emailid;
        }

        public void setEmailid(String emailid) {
            this.emailid = emailid;
        }

        public String getMiddlename() {
            return middlename;
        }

        public void setMiddlename(String middlename) {
            this.middlename = middlename;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getMobilenumber() {
            return mobilenumber;
        }

        public void setMobilenumber(String mobilenumber) {
            this.mobilenumber = mobilenumber;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }
    }
}