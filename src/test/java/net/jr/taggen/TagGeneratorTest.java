
package net.jr.taggen;

import org.junit.Assert;
import org.junit.Test;

public class TagGeneratorTest {

  @Test
  public void testSnakecase() {
    for (int i = 0; i < 1000; i++) {
      String tag = TagGenerator.newLowercaseTag();
      if (!tag.matches("^[a-z][a-z-]+-[a-z]+$")) {
        Assert.fail(String.format("Regex error! '%s'", tag));
      }
    }

  }

  @Test
  public void testCamelcase() {
    for (int i = 0; i < 1000; i++) {
      String tag = TagGenerator.newCamelcaseTag();
      if (!tag.matches("^[A-Z][A-Za-z]+$")) {
        Assert.fail(String.format("Regex error! '%s'", tag));
      }
    }
  }
}
