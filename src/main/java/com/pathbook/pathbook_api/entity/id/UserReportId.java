package com.pathbook.pathbook_api.entity.id;

import java.io.Serializable;
import java.util.Objects;

public class UserReportId implements Serializable {
    private String reporter;
    private String reportee;

    public UserReportId() {}

    public UserReportId(String reporter, String reportee) {
        this.reporter = reporter;
        this.reportee = reportee;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof UserReportId)) {
            return false;
        }

        UserReportId that = (UserReportId) object;
        return Objects.equals(reporter, that.reporter) && Objects.equals(reportee, that.reportee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reporter, reportee);
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public String getReportee() {
        return reportee;
    }

    public void setReportee(String reportee) {
        this.reportee = reportee;
    }
}
