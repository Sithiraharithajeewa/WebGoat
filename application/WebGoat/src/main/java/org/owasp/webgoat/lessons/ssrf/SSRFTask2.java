/*
 * SPDX-FileCopyrightText: Copyright © 2014 WebGoat authors
 * SPDX-License-Identifier: GPL-2.0-or-later
 */
package org.owasp.webgoat.lessons.ssrf;

import static org.owasp.webgoat.container.assignments.AttackResultBuilder.failed;
import static org.owasp.webgoat.container.assignments.AttackResultBuilder.success;

import java.net.URI;
import java.net.URISyntaxException;
import org.owasp.webgoat.container.assignments.AssignmentEndpoint;
import org.owasp.webgoat.container.assignments.AssignmentHints;
import org.owasp.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AssignmentHints({"ssrf.hint3"})
public class SSRFTask2 implements AssignmentEndpoint {

  @PostMapping("/SSRF/task2")
  @ResponseBody
  public AttackResult completed(@RequestParam String url) {
    return furBall(url);
  }

  protected AttackResult furBall(String url) {
    try {
      URI uri = new URI(url);

      /*
       * Security fix:
       * The application must not make arbitrary server-side requests
       * to attacker-controlled destinations.
       *
       * Task 2 is therefore restricted to a local resource.
       */
      if (!"http".equalsIgnoreCase(uri.getScheme())
          || uri.getHost() == null
          || !"localhost".equalsIgnoreCase(uri.getHost())) {
        return getFailedResult(
            "External URLs are blocked. Only approved local resources are permitted.");
      }

      return getFailedResult(
          "The requested local resource is not available for this exercise.");
    } catch (URISyntaxException e) {
      return getFailedResult("Invalid URL.");
    }
  }

  private AttackResult getFailedResult(String errorMsg) {
    return failed(this).feedback("ssrf.failure").output(errorMsg).build();
  }
}
