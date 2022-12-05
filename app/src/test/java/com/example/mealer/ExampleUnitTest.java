package com.example.mealer;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

import com.example.mealer.Accounts.Client;
import com.example.mealer.Accounts.Cook;
import com.example.mealer.Accounts.Meal;
import com.example.mealer.Accounts.Request;
import com.example.mealer.Accounts.SuspendAccounts;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void checkSuspendAccounts() {
        SuspendAccounts suspendAccount = new SuspendAccounts("Tomorrow", "321");
        assertEquals("Check if the suspendAccount" +
                " date", "Tomorrow", suspendAccount.getSuspendDate());
    }

    @Test
    public void checkCookStatus_1() {
        Cook cook = new Cook();
        assertEquals("Check if the new Cook status is active", "active", cook.getIsActive());
    }

    @Test
    public void checkRequestStatus() {
        Request request = new Request("123", "321", "Burger", "pending", "Tomorrow");
        assertEquals("Check if the request's status is correct", "pending", request.getStatus());
    }

    @Test
    public void checkCuisineType() {
        Meal meal = new Meal("Burger", "fast food", "Western", "Cook1");
        assertEquals("Check if the cuisine correct", "Western", meal.getCuisineType());
    }
}