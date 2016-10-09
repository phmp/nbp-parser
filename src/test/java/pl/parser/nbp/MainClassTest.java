package pl.parser.nbp;

import junit.framework.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

@Test
public class MainClassTest {

    public void test(){
        String outPut = MainClass.doAllWork(new String[]{"2016-01-01", "2017-01-01", "EUR"});

        Assert.assertNotNull(outPut);
    }

}