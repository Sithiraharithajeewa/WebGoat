/*
 * SPDX-FileCopyrightText: Copyright © 2020 WebGoat authors
 * SPDX-License-Identifier: GPL-2.0-or-later
 */
package org.owasp.webgoat.integration;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class SSRFIntegrationTest extends IntegrationTest {

  @Test
  public void localImageCompletesTask1() {
    startLesson("SSRF");

    Map<String, Object> params = new HashMap<>();
    params.clear();
    params.put("url", "images/jerry.png");

    checkAssignment(webGoatUrlConfig.url("SSRF/task1"), params, true);
  }

  @ParameterizedTest
  @ValueSource(strings = {
    "http://ifconfig.pro",
    "http://127.0.0.1",
    "http://169.254.169.254/latest/meta-data/",
    "file:///etc/passwd",
    "http://localhost@external.example",
    "http://[",
    "http://localhost"
  })
  public void task2DoesNotCompleteForBlockedOrUnavailableResources(String url) {
    startLesson("SSRF");

    checkAssignment(webGoatUrlConfig.url("SSRF/task2"), Map.of("url", url), false);
  }
}
