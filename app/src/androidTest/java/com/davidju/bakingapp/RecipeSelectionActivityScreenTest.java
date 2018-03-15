package com.davidju.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.davidju.bakingapp.activities.RecipeSelectionActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class RecipeSelectionActivityScreenTest {

    @Rule
    public ActivityTestRule<RecipeSelectionActivity> activityRule = new ActivityTestRule<>(RecipeSelectionActivity.class);
    private AppIdlingResource idlingResource;

    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }

    @Before
    public void registerIdlingResource() {
        idlingResource = (AppIdlingResource) activityRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(idlingResource);
    }

    @Test
    public void checkRecyclerViewRecipeContent() {
        Espresso.onView(withRecyclerView(R.id.recycler_view).atPosition(0)).check(matches(withText("Nutella Pie")));
        Espresso.onView(withRecyclerView(R.id.recycler_view).atPosition(1)).check(matches(withText("Brownies")));
        Espresso.onView(withRecyclerView(R.id.recycler_view).atPosition(2)).check(matches(withText("Yellow Cake")));
        Espresso.onView(withRecyclerView(R.id.recycler_view).atPosition(3)).check(matches(withText("Cheesecake")));
    }

    @Test
    public void checkRecyclerViewClickActionOne() {
        Espresso.onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        Espresso.onView(withRecyclerView(R.id.recycler_view).atPosition(0)).check(matches(withText("Nutella Pie")));
        Espresso.onView(withRecyclerView(R.id.recycler_view).atPositionOnView(3, R.id.step)).check(matches(withText("1. Starting prep")));
        Espresso.onView(withRecyclerView(R.id.recycler_view).atPositionOnView(4, R.id.step)).check(matches(withText("2. Prep the cookie crust.")));
    }

    @Test
    public void checkRecyclerViewClickActionTwo() {
        Espresso.onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        Espresso.onView(withRecyclerView(R.id.recycler_view).atPosition(0)).check(matches(withText("Brownies")));
        Espresso.onView(withRecyclerView(R.id.recycler_view).atPositionOnView(3, R.id.step)).check(matches(withText("1. Starting prep")));
        Espresso.onView(withRecyclerView(R.id.recycler_view).atPositionOnView(4, R.id.step)).check(matches(withText("2. Melt butter and bittersweet chocolate.")));
    }

    @Test
    public void checkRecyclerViewClickActionThree() {
        Espresso.onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        Espresso.onView(withRecyclerView(R.id.recycler_view).atPosition(0)).check(matches(withText("Yellow Cake")));
        Espresso.onView(withRecyclerView(R.id.recycler_view).atPositionOnView(3, R.id.step)).check(matches(withText("1. Starting prep")));
        Espresso.onView(withRecyclerView(R.id.recycler_view).atPositionOnView(4, R.id.step)).check(matches(withText("2. Combine dry ingredients.")));
    }

    @Test
    public void checkRecyclerViewClickActionFour() {
        Espresso.onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
        Espresso.onView(withRecyclerView(R.id.recycler_view).atPosition(0)).check(matches(withText("Cheesecake")));
        Espresso.onView(withRecyclerView(R.id.recycler_view).atPositionOnView(3, R.id.step)).check(matches(withText("1. Starting prep.")));
        Espresso.onView(withRecyclerView(R.id.recycler_view).atPositionOnView(4, R.id.step)).check(matches(withText("2. Prep the cookie crust.")));
    }

    @After
    public void unregisterIdlingResource() {
        if (idlingResource != null) {
            IdlingRegistry.getInstance().unregister(idlingResource);
        }
    }
}
