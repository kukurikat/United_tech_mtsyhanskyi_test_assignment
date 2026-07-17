package tests;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer {

    private int count = 0;
    private static final int MAX_LIMIT = 2;

    @Override
    public boolean retry(ITestResult result) {
        if (!result.isSuccess()) {
            if (count < MAX_LIMIT) {
                count++;
                System.out.println("Test '" + result.getName() + "' failed. Retrying attempt #" + count);

                return true;
            }
        }
        return false;
    }
}