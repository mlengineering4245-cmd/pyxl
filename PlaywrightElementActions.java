package com.fepoc.test.utils;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public class PlaywrightElementActions {

    private Page getPage() {
        return PlaywrightFactory.getPage();
    }

    public void click(String selector) {
        getPage().locator(selector).click();
    }

    public void type(String selector, String text) {
        getPage().locator(selector).fill(text);
    }
    
    public void selectDropdown(String selector, String optionText) {
        // Option 1: Standard Select
        // getPage().locator(selector).selectOption(new SelectOption().setLabel(optionText));
        
        // Option 2: Click-to-open (common in modern UIs)
        getPage().locator(selector).click();
        getPage().getByRole(com.microsoft.playwright.options.AriaRole.OPTION, 
            new Page.GetByRoleOptions().setName(optionText)).click();
    }

    public String getText(String selector) {
        return getPage().locator(selector).innerText();
    }

    // Chatbot specific: Wait for "Typing..." to vanish
    public void waitForResponse() {
        // Adjust ".typing-indicator" to match your app's actual CSS
        try {
            getPage().locator(".typing-indicator").waitFor(
                new Locator.WaitForOptions().setState(WaitForSelectorState.DETACHED).setTimeout(10000)
            );
        } catch (Exception e) {
            // Ignore if indicator was too fast to catch
        }
    }
}