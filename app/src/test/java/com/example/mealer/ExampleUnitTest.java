package com.example.mealer;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

import com.example.mealer.Accounts.Client;
import com.example.mealer.Accounts.Cook;
import com.example.mealer.Accounts.Meal;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void checkCookStatus_0() {
        Cook cook = new Cook("Anakin", "SkyWalker", "Sand", "501", "Courscant", "Cheque123", "The chosen one");
        cook.setIsActive("Suspend temporarily");
        assertEquals("Check if the Cook status is banned", "Suspend temporarily", cook.getIsActive());
    }

    @Test
    public void checkCookStatus_1() {
        Cook cook = new Cook();
        assertEquals("Check if the new Cook status is active", "active", cook.getIsActive());
    }

    @Test
    public void checkClientName() {
        Client client = new Client("Shuai", "Gao", "cgao034@uottawa.ca", "123", "London686", "1314520");
        assertEquals("Check if the client's address is correct", "London686", client.getAddress());
    }

    @Test
    public void checkMealCookID() {
        Meal meal = new Meal("Burger", "fast food", "Western", "Cook1");
        assertEquals("Check if the meal matches the cook id", "Cook1", meal.getCookID());
    }
}