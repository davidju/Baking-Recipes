package com.davidju.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import com.davidju.bakingapp.activities.RecipeSelectionActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Class that contains the UI tests for actions performed on the recipe step selection screen.
 * Asserts that selecting a recipe step leads to the correct recipe step description view by asserting that
 * the step description matches the expected text.
 */
public class RecipeDetailsScreenTest {

    @Rule
    public ActivityTestRule<RecipeSelectionActivity> activityRule = new ActivityTestRule<>(RecipeSelectionActivity.class);
    private AppIdlingResource idlingResource;

    @Before
    public void registerIdlingResource() {
        idlingResource = (AppIdlingResource) activityRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(idlingResource);
    }

    @Test
    public void checkRecyclerViewClickAction_NutellaPieStepOne() {
        Espresso.onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.scrollToPosition(0));
        Espresso.onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        Espresso.onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.scrollToPosition(4));
        Espresso.onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(4, click()));
        Espresso.onView(withId(R.id.description)).check(matches(withText("1. Preheat the oven to 350\u00B0F. Butter a 9\" deep dish pie pan.")));
    }

    @Test
    public void checkRecyclerViewClickAction_NutellaPieStepThree() {
        Espresso.onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.scrollToPosition(0));
        Espresso.onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        Espresso.onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.scrollToPosition(6));
        Espresso.onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(6, click()));
        Espresso.onView(withId(R.id.description)).check(matches(withText("3. Press the cookie crumb mixture into the prepared pie pan and bake for 12 minutes. Let crust cool to room temperature.")));
    }

    @Test
    public void checkRecyclerViewClickAction_BrowniesStepThree() {
        Espresso.onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.scrollToPosition(1));
        Espresso.onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        Espresso.onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.scrollToPosition(6));
        Espresso.onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(6, click()));
        Espresso.onView(withId(R.id.description)).check(matches(withText("3. Mix both sugars into the melted chocolate in a large mixing bowl until mixture is smooth and uniform.")));
    }

    @Test
    public void checkRecyclerViewClickAction_BrowniesStepFour() {
        Espresso.onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.scrollToPosition(1));
        Espresso.onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        Espresso.onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.scrollToPosition(7));
        Espresso.onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(7, click()));
        Espresso.onView(withId(R.id.description)).check(matches(withText("4. Sift together the flour, cocoa, and salt in a small bowl and whisk until mixture is uniform and no clumps remain. ")));
    }

    @Test
    public void checkRecyclerViewClickAction_YellowCakeStepThree() {
        Espresso.onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.scrollToPosition(2));
        Espresso.onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        Espresso.onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.scrollToPosition(6));
        Espresso.onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(6, click()));
        Espresso.onView(withId(R.id.description)).check(matches(withText("3. Lightly beat together the egg yolks, 1 tablespoon of vanilla, and 80 grams (1/3 cup) of the milk in a small bowl.")));
    }

    @Test
    public void checkRecyclerViewClickAction_YellowCakeStepFive() {
        Espresso.onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.scrollToPosition(2));
        Espresso.onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        Espresso.onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.scrollToPosition(8));
        Espresso.onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(8, click()));
        Espresso.onView(withId(R.id.description)).check(matches(withText("5. Scrape down the sides of the bowl. Add the egg mixture to the batter in three batches, beating for 20 seconds each time and then scraping down the sides.")));
    }

    @Test
    public void checkRecyclerViewClickAction_CheesecakeStepOne() {
        Espresso.onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.scrollToPosition(3));
        Espresso.onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
        Espresso.onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.scrollToPosition(4));
        Espresso.onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(4, click()));
        Espresso.onView(withId(R.id.description)).check(matches(withText("1. Preheat the oven to 350\u00b0F. Grease the bottom of a 9-inch round springform pan with butter. ")));
    }

    @Test
    public void checkRecyclerViewClickAction_CheesecakeStepThree() {
        Espresso.onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.scrollToPosition(3));
        Espresso.onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
        Espresso.onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.scrollToPosition(6));
        Espresso.onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(6, click()));
        Espresso.onView(withId(R.id.description)).check(matches(withText("3. Fill a large roasting pan with a few inches of hot water and place it on the bottom rack of the oven.")));
    }

    @After
    public void unregisterIdlingResource() {
        if (idlingResource != null) {
            IdlingRegistry.getInstance().unregister(idlingResource);
        }
    }
}
