package pl.parser.nbp;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

@Test
public class MainClassTest {

    public void test(){
        MainClass.main(new String[]{"2016-01-01","2017-01-01","EUR"});
    }

}