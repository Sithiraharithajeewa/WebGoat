/*
 * SPDX-FileCopyrightText: Copyright © 2017 WebGoat authors
 * SPDX-License-Identifier: GPL-2.0-or-later
 */
package org.owasp.webgoat.lessons.ssrf;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.owasp.webgoat.container.plugins.LessonTest;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class SSRFTest2 extends LessonTest {

  @BeforeEach
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
  }

  @ParameterizedTest
  @ValueSource(strings = {
    "http://ifconfig.pro",
    "http://127.0.0.1",
    "http://169.254.169.254/latest/meta-data/",
    "file:///etc/passwd",
    "http://localhost@external.example"
  })
  public void externalAndUnapprovedUrlsAreBlocked(String url) throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.post("/SSRF/task2").param("url", url))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.lessonCompleted", is(false)))
        .andExpect(
            jsonPath(
                "$.output",
                is("External URLs are blocked. Only approved local resources are permitted.")));
  }

  @Test
  public void malformedUrlIsRejected() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.post("/SSRF/task2").param("url", "http://["))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.lessonCompleted", is(false)))
        .andExpect(jsonPath("$.output", is("Invalid URL.")));
  }

  @Test
  public void unavailableLocalResourceDoesNotCompleteAssignment() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.post("/SSRF/task2").param("url", "http://localhost"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.lessonCompleted", is(false)))
        .andExpect(
            jsonPath(
                "$.output",
                is("The requested local resource is not available for this exercise.")));
  }

  @Test
  public void modifyUrlCat() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.post("/SSRF/task2").param("url", "images/cat.jpg"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.lessonCompleted", is(false)));
  }
}
