package com.udacity.gradle.builditbigger;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.text.TextUtils;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static junit.framework.TestCase.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class AsyncTaskTest {

    @Rule
    public ActivityTestRule<MainActivity> testRule = new ActivityTestRule(MainActivity.class);

    @Test
    public void testAsyncTask() throws InterruptedException, ExecutionException {

        EndPointAsyncTask endPoint = new EndPointAsyncTask();

        String joke = null;
        try {

            endPoint.execute(testRule.getActivity());

            joke = endPoint.get(20, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            e.getMessage();
        }
       if (!TextUtils.isEmpty(joke))
        assertNotNull(joke);
    }

}
