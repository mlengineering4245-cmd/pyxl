package com.fepoc.test.utils;

import com.microsoft.playwright.*;

public class PlaywrightFactory {
    // ThreadLocal ensures parallel execution safety
    private static ThreadLocal<Playwright> playwright = new ThreadLocal<>();
    private static ThreadLocal<Browser> browser = new ThreadLocal<>();
    private static ThreadLocal<BrowserContext> context = new ThreadLocal<>();
    private static ThreadLocal<Page> page = new ThreadLocal<>();

    public static void init() {
        playwright.set(Playwright.create());
        // Headless is false so you can see the execution
        browser.set(playwright.get().chromium().launch(new BrowserType.LaunchOptions().setHeadless(false)));
        
        // Context represents a single user session (incognito-like)
        context.set(browser.get().newContext(new Browser.NewContextOptions().setViewportSize(1920, 1080)));
        
        // Start with one blank page
        page.set(context.get().newPage());
    }

    public static Page getPage() {
        return page.get();
    }

    public static BrowserContext getContext() {
        return context.get();
    }

    // *** CRITICAL: Call this when the chatbot opens in a new tab ***
    public static void switchPage(Page newPage) {
        page.get().close(); // Optional: Close the old login tab if no longer needed
        page.set(newPage);
        newPage.bringToFront();
    }

    public static void quit() {
        if (page.get() != null) page.get().close();
        if (context.get() != null) context.get().close();
        if (browser.get() != null) browser.get().close();
        if (playwright.get() != null) playwright.get().close();
    }
}