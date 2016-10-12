package pl.parser.nbp;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pl.parser.nbp.utils.exceptions.RatesNotPublishedException;


public class MainClass_NetworkTest {

    @Test(dataProvider = "provideData")
    public void test(String[] input, String expectedOutput) throws Exception {
        MainClass systemUnderTest = new MainClass();
        String outPut = systemUnderTest.getAndCalculateData(input);
        Assert.assertEquals(outPut, expectedOutput);
    }

    @DataProvider
    public Object[][] provideData() {
        return new Object[][]{
                {
                    new String[]{"EUR", "2013-01-28", "2013-01-31"}, "4.1505\n0.0125"
                },{
                    new String[]{"EUR", "2013-01-01", "2013-01-31"}, "4.0963\n0.0402"
                }
        };
    }

    @Test(dataProvider = "provideUnexpectedData")
    public void cornerCaseTest(String[] input, Class<Exception> exceptionClass, String expectedErrorMessage) {
        try {
            MainClass systemUnderTest = new MainClass();
            systemUnderTest.getAndCalculateData(input);
            Assert.fail();
        } catch (Exception e) {
            Class<? extends Exception> aClass = e.getClass();
            Assert.assertEquals(aClass, exceptionClass);
            Assert.assertEquals(e.getMessage(), expectedErrorMessage);
        }
    }

    @DataProvider
    public Object[][] provideUnexpectedData() {
        return new Object[][]{
                {
                    new String[]{"EUR", "2016-01-01", "2016-01-02"},
                    RatesNotPublishedException.class,
                    "Rates were not published in this days"
                }
        };
    }
}