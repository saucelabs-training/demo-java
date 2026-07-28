package com.saucelabs.playwright;

/**
 * Build name/number shared by every session in a run. Computed once via JVM class-loading (a single
 * {@code private static final} field), which is exactly-once and thread-safe without needing any
 * explicit synchronization - unlike a wall-clock timestamp read independently by each
 * thread/process, this can't drift apart across parallel execution units within one JVM.
 */
public final class BuildInfo {
  public static final String BUILD_NAME = resolveBuildName();
  public static final String BUILD_NUMBER = resolveBuildNumber();

  private BuildInfo() {}

  private static String resolveBuildName() {
    String workflow = System.getenv("GITHUB_WORKFLOW");
    return workflow != null ? workflow : "Playwright Java Sauce Demo (local)";
  }

  private static String resolveBuildNumber() {
    String runNumber = System.getenv("GITHUB_RUN_NUMBER");
    return runNumber != null ? runNumber : String.valueOf(System.currentTimeMillis());
  }
}
