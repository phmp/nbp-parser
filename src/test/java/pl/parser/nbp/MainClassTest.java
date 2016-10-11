package pl.parser.nbp;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class MainClassTest {

    @Test(dataProvider = "provideData")
    public void test(String[] input, String expectedOutput) {
        String outPut = MainClass.doAllWork(input);
        Assert.assertEquals(outPut, expectedOutput);
    }

    @DataProvider
    public Object[][] provideData() {
        return new Object[][]{
                {
                    new String[]{"EUR", "2016-08-01", "2016-08-10"}, "4.3522\n0.0344"
                }, {
                    new String[]{"EUR", "2016-08-01", "2016-08-05"}, "4.3729\n0.0267"
                }, {
                    new String[]{"EUR", "2016-08-04", "2016-08-08"}, "4.3378\n0.0119"
                }, {
                    new String[]{"EUR", "2016-01-01", "2016-01-02"}, "Rates were not published in this days"
        }
        };
    }
}