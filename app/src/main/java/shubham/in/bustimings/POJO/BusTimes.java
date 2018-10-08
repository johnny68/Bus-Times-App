package shubham.in.bustimings.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BusTimes {
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
        @SerializedName("totime")
        private String totime;
        @Expose
        @SerializedName("id")
        private String id;
        @Expose
        @SerializedName("fromtime")
        private String fromtime;
        @Expose
        @SerializedName("tostop")
        private String tostop;
        @Expose
        @SerializedName("fromstop")
        private String fromstop;

        public String getTotime() {
            return totime;
        }

        public void setTotime(String totime) {
            this.totime = totime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFromtime() {
            return fromtime;
        }

        public void setFromtime(String fromtime) {
            this.fromtime = fromtime;
        }

        public String getTostop() {
            return tostop;
        }

        public void setTostop(String tostop) {
            this.tostop = tostop;
        }

        public String getFromstop() {
            return fromstop;
        }

        public void setFromstop(String fromstop) {
            this.fromstop = fromstop;
        }
    }
}
