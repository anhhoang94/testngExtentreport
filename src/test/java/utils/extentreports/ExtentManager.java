package utils.extentreports;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.*;

public class ExtentManager {
    public static final ExtentReports extentReports = new ExtentReports();

    public synchronized static ExtentReports createExtentReports() {
        ExtentSparkReporter reporter = new ExtentSparkReporter("./extent-reports/extent-report.html");
        reporter.config().setReportName("Sample Extent Report");
        extentReports.attachReporter(reporter);
        extentReports.setSystemInfo("Author", "Anh Hoang");
        return extentReports;
    }
}
