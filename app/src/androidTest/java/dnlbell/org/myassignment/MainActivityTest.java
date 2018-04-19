package dnlbell.org.myassignment;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static java.util.EnumSet.allOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void canVerifyGoodTextRejectsBadAge() {
        onView(withId(R.id.name)).perform(typeText("Danny Bell"));
        onView(withId(R.id.email)).perform(typeText("A@A.org"));
        onView(withId(R.id.userName)).perform(typeText("Orbos"));

        onView(withId(R.id.submit)).perform(click());

        onView(withId(R.id.error)).check(matches(withText(R.string.invalidAge)));
    }

    @Test
    public void rejectsBadDate() {
        onView(withId(R.id.name)).perform(typeText("Danny Bell"));
        onView(withId(R.id.email)).perform(typeText("A@A.org"));
        onView(withId(R.id.userName)).perform(typeText("Orbos"));

        onView(withId(R.id.month)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(1).perform(click());

        onView(withId(R.id.day)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(30).perform(click());

        onView(withId(R.id.year)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(20).perform(click());

        onView(withId(R.id.submit)).perform(click());

        onView(withId(R.id.error)).check(matches(withText(R.string.invalidDate)));

    }

}

