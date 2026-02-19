package com.fepoc.test.steps;

import com.fepoc.test.utils.PlaywrightElementActions;
import com.fepoc.test.utils.ObjectMap;
import com.fepoc.test.utils.PlaywrightFactory;
import io.cucumber.java.en.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class ChatbotSteps {

    PlaywrightElementActions action = new PlaywrightElementActions();
    ObjectMap chatMap = new ObjectMap("chatbot_locators.json");

    @When("user selects {string} from Knowledge Base")
    public void selectKB(String kbOption) {
        action.selectDropdown(chatMap.getLocator("kbDropdown"), kbOption);
    }

    @When("user enters {string} in chat")
    public void enterChat(String query) {
        action.type(chatMap.getLocator("chatInput"), query);
        PlaywrightFactory.getPage().keyboard().press("Enter");
    }

    @Then("verify response contains {string}")
    public void verifyResponse(String expectedText) {
        // Wait for typing to stop
        action.waitForResponse();
        
        // Locate the LAST message bubble (assuming it's the bot's response)
        var lastMessage = PlaywrightFactory.getPage().locator(chatMap.getLocator("botMessageBubble")).last();
        
        // Assertion (will retry automatically)
        assertThat(lastMessage).containsText(expectedText);
    }

    @When("user clicks New Session")
    public void newSession() {
        // Handle potential "Are you sure?" dialog
        PlaywrightFactory.getPage().onceDialog(dialog -> dialog.accept());
        action.click(chatMap.getLocator("newSessionBtn"));
    }
}