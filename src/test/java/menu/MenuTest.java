package menu;

import org.junit.Test;
import org.junit.Assert;
import org.junit.Before;

import java.util.ArrayList;

public class MenuTest {
    ArrayList<String> options;
    Menu menu;
    @Before
    public void setUp() {
        options = new ArrayList<>();
        options.add("chips");
        menu = new Menu(options);
    }

    @Test
    public void getOptionsTest()  {
        //Given
        //When
        Integer expected = 1;
        ArrayList<String> actualList = menu.getOptions();
        Integer actual = actualList.size();
        //Then
        Assert.assertEquals(expected, actual);
    }

}