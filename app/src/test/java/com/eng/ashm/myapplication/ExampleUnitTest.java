package com.eng.ashm.myapplication;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.*;

import com.eng.ashm.myapplication.datamodel.data.WeatherData;
import com.eng.ashm.myapplication.datamodel.repo.WeatherRepo;

import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {


    @Test
    public void testWeatherRepo(){

        WeatherRepo repo = Mockito.mock(WeatherRepo.class);

    }
}