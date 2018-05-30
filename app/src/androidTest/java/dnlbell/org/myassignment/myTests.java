package dnlbell.org.myassignment;

//import android.support.test.espresso.intent.Intents;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isFocusable;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static dnlbell.org.myassignment.TestUtils.rotateScreen;
import static dnlbell.org.myassignment.TestUtils.waitFor;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.Is.is;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;



//import static android.support.test.espresso.action.ViewActions.scrollTo;
//import static android.support.test.espresso.intent.Intents.intended;
//import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
//import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;

@RunWith(AndroidJUnit4.class)
public class myTests {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void submitGoodData(){
        onView(withId(R.id.name)).perform(typeText("Daniel Bell"));
        onView(withId(R.id.email)).perform(typeText("dan@dan.dan"));
        onView(withId(R.id.userName)).perform(typeText("Dan"));
        onView(withId(R.id.occupation)).perform(typeText("Technical Support Specialist"));
        onView(withId(R.id.description)).perform(typeText("Lorem ipsum"));

        onView(withId(R.id.year)).perform(click());
        onData(allOf(is(instanceOf(Integer.class)))).atPosition(29).perform(click());

        onView(withId(R.id.month)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(1).perform(click());

        onView(withId(R.id.day)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(1).perform(click());

        closeSoftKeyboard();
        onView(withId(R.id.submit)).perform(click());

        onView(withId(R.id.userName)).check(matches(withText("Dan, 29")));
    }

    @Test
    public void submitNoData() {
        closeSoftKeyboard();
        onView(withId(R.id.submit)).perform(click());

        String errorString = "Error:\n" +
                             "Invalid Name. Name must be between 1 and 32 characters\n" +
                             "Invalid email address\n" +
                             "Invalid user name. User name must be between 1 and 32 characters\n" +
                             "Those under 18 years of age are not permitted\n" +
                             "Invalid occupation. occupation must be between 1 and 32 characters\n" ;

        String viewString = TestUtils.getText(withId(R.id.error));

        assertEquals(errorString,viewString);
    }

    @Test
    public void rotateScreenWithText() {
        onView(withId(R.id.name)).perform(typeText("Daniel Bell"));

        rotateScreen(activityTestRule.getActivity());

        onView(withId(R.id.name)).check(matches(withText("Daniel Bell")));
    }

    @Test
    public void lookAtTabs(){
        onView(withId(R.id.name)).perform(typeText("Daniel Bell"));
        onView(withId(R.id.email)).perform(typeText("dan@dan.dan"));
        onView(withId(R.id.userName)).perform(typeText("Dan"));
        onView(withId(R.id.occupation)).perform(typeText("Technical Support Specialist"));
        onView(withId(R.id.description)).perform(typeText("Lorem ipsum"));
        onView(withId(R.id.year)).perform(click());
        onData(allOf(is(instanceOf(Integer.class)))).atPosition(29).perform(click());

        onView(withId(R.id.month)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(0).perform(click());

        onView(withId(R.id.day)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(2).perform(click());

        closeSoftKeyboard();
        onView(withId(R.id.submit)).perform(click());

        onView(withId(R.id.viewpager)).perform(swipeLeft());
        onView(withId(R.id.viewpager)).perform(swipeLeft());
        onView(withId(R.id.viewpager)).perform(swipeRight());
        onView(withId(R.id.viewpager)).perform(swipeRight());

        onView(withId(R.id.userName)).check(matches(withText("Dan, 29")));
    }

    @Test
    public void invalidDate(){
        onView(withId(R.id.name)).perform(typeText("Daniel Bell"));
        onView(withId(R.id.email)).perform(typeText("dan@dan.dan"));
        onView(withId(R.id.userName)).perform(typeText("Dan"));
        onView(withId(R.id.occupation)).perform(typeText("Technical Support Specialist"));
        onView(withId(R.id.description)).perform(typeText("Lorem ipsum"));

        onView(withId(R.id.year)).perform(click());
        onData(allOf(is(instanceOf(Integer.class)))).atPosition(29).perform(click());

        onView(withId(R.id.month)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(1).perform(click());

        onView(withId(R.id.day)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(30).perform(click());

        closeSoftKeyboard();
        onView(withId(R.id.submit)).perform(click());

        String errorString = "Error:\n" + "Invalid date\n";

        String viewString = TestUtils.getText(withId(R.id.error));

        assertEquals(errorString,viewString);

    }

    @Test
    public void clickLike() {
        onView(withId(R.id.name)).perform(typeText("Daniel Bell"));
        onView(withId(R.id.email)).perform(typeText("dan@dan.dan"));
        onView(withId(R.id.userName)).perform(typeText("Dan"));
        onView(withId(R.id.occupation)).perform(typeText("Technical Support Specialist"));
        onView(withId(R.id.description)).perform(typeText("Lorem ipsum"));

        onView(withId(R.id.year)).perform(click());
        onData(allOf(is(instanceOf(Integer.class)))).atPosition(29).perform(click());

        onView(withId(R.id.month)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(1).perform(click());

        onView(withId(R.id.day)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(1).perform(click());

        closeSoftKeyboard();
        onView(withId(R.id.submit)).perform(click());
        onView(withId(R.id.viewpager)).perform(swipeLeft());

        onView(withId(R.id.my_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, TestUtils.clickChildViewWithId(R.id.like_button)));
        //onView(withText("You liked Jane Doe!").inRoot(withDecorView(not(is(getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));

    }

    @Test
    public void saveSettings(){
        submitGoodData();

        onView(withId(R.id.viewpager)).perform(swipeLeft());
        onView(withId(R.id.viewpager)).perform(swipeLeft());

        onView(isRoot()).perform(waitFor(1000));
        onView(withId(R.id.hour)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(2).perform(click());

        onView(isRoot()).perform(waitFor(1000));
        onView(withId(R.id.minute)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(2).perform(click());

        onView(isRoot()).perform(waitFor(1000));
        onView(withId(R.id.meridiem)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(1).perform(click());

        onView(isRoot()).perform(waitFor(1000));
        onView(withId(R.id.radius)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(2).perform(click());

        onView(isRoot()).perform(waitFor(1000));
        onView(withId(R.id.sexuality)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(3).perform(click());

        onView(isRoot()).perform(waitFor(1000));
        onView(withId(R.id.gender)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(3).perform(click());

        onView(withId(R.id.save)).perform(click());
    }

}