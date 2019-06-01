package android.example.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.RootMatchers;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.RecyclerView;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.regex.Matcher;

import static android.support.test.espresso.action.ViewActions.click;
import static org.junit.Assert.*;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityActivityTestRule=new ActivityTestRule<MainActivity>(MainActivity.class);

    @Before
    public void setUp() throws Exception {

    }
    @Test
    public void testClick(){
        // this was taken from https://medium.com/@wasim__/espresso-test-cases-for-android-recyclerview-eb2ceaddc74f
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_main))
                .inRoot(RootMatchers.withDecorView(
                Matchers.is(mActivityActivityTestRule.getActivity().getWindow().getDecorView())))
                .perform(RecyclerViewActions.actionOnItemAtPosition
                (0,click()));
    }

    @After
    public void tearDown() throws Exception {
    }
}