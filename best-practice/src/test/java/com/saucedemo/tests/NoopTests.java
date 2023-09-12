package com.saucedemo.tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertTrue;

public class NoopTests {
  /**
   * Test doing nothing used to initialize libraries while generating
   * the sauce-orchestrate docker container
   **/

    @Test()
    public void noopWorks() {
        assertTrue(true);
    }
}
