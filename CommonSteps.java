package com.fepoc.test.steps;

import com.fepoc.test.utils.PlaywrightFactory;
import com.fepoc.test.utils.PlaywrightElementActions;
import com.fepoc.test.utils.ObjectMap;
import io.cucumber.java.en.*;
import com.microsoft.playwright.Page;

public class CommonSteps {
    
    PlaywrightElementActions action = new PlaywrightElementActions();
    ObjectMap loginMap = new ObjectMap("login_locators.json");

    @Given("user logs into ")
    public void login() {
        PlaywrightFactory.getPage().navigate("login url");
        action.type(loginMap.getLocator("username"), "testUser");
        action.type(loginMap.getLocator("password"), "testPass");
        action.click(loginMap.getLocator("loginBtn"));
    }

    @When("user clicks the {string} and switches to new tab")
    public void clickAndSwitch(String linkName) {
        String selector = loginMap.getLocator(linkName);

        // *** MAGIC HAPPENS HERE ***
        // We tell Playwright: "Watch for a new page to be created while I run this lambda function"
        Page newChatbotPage = PlaywrightFactory.getContext().waitForPage(() -> {
            action.click(selector);
        });
        
        newChatbotPage.waitForLoadState();
        
        // Update the factory so all subsequent steps use this new tab
        PlaywrightFactory.switchPage(newChatbotPage);
    }
}