package dnlbell.org.myassignment;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.AllOf.allOf;

public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void enterExpectedData(){
        onView(withId(R.id.name)).perform(typeText("Daniel Bell"));
        onView(withId(R.id.email)).perform(typeText("dan@dan.dan"));
        onView(withId(R.id.userName)).perform(typeText("Dan"));
        onView(withId(R.id.occupation)).perform(typeText("Technical Support Specialist"));
        onView(withId(R.id.description)).perform(typeText("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."));

        // Clicks into the year drop down but doesn't actually do anything with it
        //onView(withId(R.id.year)).perform(click());
        // Broken, not sure what this does anyway?
        //onData(allOf(is(instanceOf(String.class)),  is("2000"))).perform(click());

        onView(withId(R.id.name)).check(matches(withText("Daniel Bell")));
    }

}