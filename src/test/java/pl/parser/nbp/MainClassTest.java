package pl.parser.nbp;

import junit.framework.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class MainClassTest {

    @Test(dataProvider = "provideStandardData")
    public void test(String[] input, String expectedOutput) {
        String outPut = MainClass.doAllWork(input);
        Assert.assertEquals(expectedOutput, outPut);
    }

    @DataProvider
    public Object[][] provideStandardData() {
        return new Object[][]{
        {
                new String[]{"2016-08-01", "2016-08-10", "EUR"}, "4.3522\n0.0344"
        }, {
                new String[]{"2016-08-01", "2016-08-05", "EUR"}, "4.3729\n0.0267"
        }, {
                new String[]{"2016-08-04", "2016-08-08", "EUR"}, "4.3378\n0.0119"
        }

        };
    }
}