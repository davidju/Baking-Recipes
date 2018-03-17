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

/**
 * Class that contains UI tests for the recipe selection screen and actions performed on the screen.
 */
@RunWith(AndroidJUnit4.class)
public class RecipeSelectionScreenTest {

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

    /* Verifies that recipes are loaded correctly from network. */
    @Test
    public void checkRecyclerViewRecipeContent() {
        Espresso.onView(withRecyclerView(R.id.recycler_view).atPosition(0)).check(matches(withText("Nutella Pie")));
        Espresso.onView(withRecyclerView(R.id.recycler_view).atPosition(1)).check(matches(withText("Brownies")));
        Espresso.onView(withRecyclerView(R.id.recycler_view).atPosition(2)).check(matches(withText("Yellow Cake")));
        Espresso.onView(withRecyclerView(R.id.recycler_view).atPosition(3)).check(matches(withText("Cheesecake")));
    }

    /* Verifies that selecting the Nutella Pie recipe leads to the correct view containing the expected data. */
    @Test
    public void checkRecyclerViewClickAction_NutellaPie() {
        Espresso.onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        Espresso.onView(withRecyclerView(R.id.recycler_view).atPosition(0)).check(matches(withText("Nutella Pie")));
        Espresso.onView(withRecyclerView(R.id.recycler_view).atPosition(4)).check(matches(withText("1. Starting prep")));
        Espresso.onView(withRecyclerView(R.id.recycler_view).atPosition(5)).check(matches(withText("2. Prep the cookie crust.")));
    }

    /* Verifies that selecting the Brownies recipe leads to the correct view containing the expected data. */
    @Test
    public void checkRecyclerViewClickAction_Brownies() {
        Espresso.onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        Espresso.onView(withRecyclerView(R.id.recycler_view).atPosition(0)).check(matches(withText("Brownies")));
        Espresso.onView(withRecyclerView(R.id.recycler_view).atPosition(4)).check(matches(withText("1. Starting prep")));
        Espresso.onView(withRecyclerView(R.id.recycler_view).atPosition(5)).check(matches(withText("2. Melt butter and bittersweet chocolate.")));
    }

    /* Verifies that selecting the Yellow Cake recipe leads to the correct view containing the expected data. */
    @Test
    public void checkRecyclerViewClickAction_YellowCake() {
        Espresso.onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        Espresso.onView(withRecyclerView(R.id.recycler_view).atPosition(0)).check(matches(withText("Yellow Cake")));
        Espresso.onView(withRecyclerView(R.id.recycler_view).atPosition(4)).check(matches(withText("1. Starting prep")));
        Espresso.onView(withRecyclerView(R.id.recycler_view).atPosition(5)).check(matches(withText("2. Combine dry ingredients.")));
    }

    /* Verifies that selecting the Cheesecake recipe leads to the correct view containing the expected data. */
    @Test
    public void checkRecyclerViewClickAction_Cheesecake() {
        Espresso.onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
        Espresso.onView(withRecyclerView(R.id.recycler_view).atPosition(0)).check(matches(withText("Cheesecake")));
        Espresso.onView(withRecyclerView(R.id.recycler_view).atPosition(4)).check(matches(withText("1. Starting prep.")));
        Espresso.onView(withRecyclerView(R.id.recycler_view).atPosition(5)).check(matches(withText("2. Prep the cookie crust.")));
    }

    @After
    public void unregisterIdlingResource() {
        if (idlingResource != null) {
            IdlingRegistry.getInstance().unregister(idlingResource);
        }
    }
}
