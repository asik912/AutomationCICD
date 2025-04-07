package frameworkPractice.TestComponents;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryTestCase implements IRetryAnalyzer {

    int retryCount = 0;
    int maxCount = 1;

    @Override
    public boolean retry(ITestResult iTestResult) {

        if (retryCount < maxCount) {
            retryCount++;
            return true;
        }
        return false;
    }
}
