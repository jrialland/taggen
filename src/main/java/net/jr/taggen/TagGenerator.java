
package net.jr.taggen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class TagGenerator {

  private static final TagGenerator Instance = new TagGenerator();

  private Random random = new Random();

  private List<String> adjectives;

  private List<String> nouns;

  private TagGenerator() {
    adjectives = readTxtFile("adjectives.txt");
    nouns = readTxtFile("nouns.txt");
  }

  private List<String> readTxtFile(String resourceName) {
    try (InputStream in = TagGenerator.class.getClassLoader().getResourceAsStream(resourceName)) {
      List<String> set = new ArrayList<>();
      BufferedReader br = new BufferedReader(new InputStreamReader(in, Charset.forName("utf-8")));
      String line;
      while ((line = br.readLine()) != null) {
        line = line.trim();
        if (!(line.isEmpty() || line.startsWith("#"))) {
          set.add(line);
        }
      }
      return set;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private String[] makeNewTag() {
    String adjective = adjectives.get(random.nextInt(adjectives.size()));
    String noun = nouns.get(random.nextInt(nouns.size()));
    return new String[] { adjective, noun };
  }

  /**
   * Generate a random name, using the [adjective]-[noun] pattern.
   *
   * Here are some examples :
   * 
   * <pre>
   * informal-thunder
   * unhealthy-limit
   * fatal-face
   * tan-throat
   * eminent-pet
   * premium-ghost
   * spotted-pencil
   * high-level-coach
   * instinctive-toad
   * vague-dock
   * pungent-pail
   * </pre>
   *
   * @return
   */
  public static final String newLowercaseTag() {
    String[] t = Instance.makeNewTag();
    return (t[0].toLowerCase() + "-" + t[1].toLowerCase()).replaceAll(" ", "");
  }

  /**
   * Generate a random name, using the AdjectiveNoun pattern, aka 'CamelCase'.
   *
   * Here are some examples :
   * 
   * <pre>
   * TreasuredSpade
   * TalkativeSeashore
   * BeneficialOil
   * ZestyBeef
   * GenerousBirth
   * FewFiction
   * AnxiousSelection
   * RoughCobweb
   * AbnormalCelery
   * GlaringSwing
   * SwelteringTooth
   * RaggedCows
   * </pre>
   *
   * @return
   */
  public static final String newCamelcaseTag() {
    boolean needUppercase = true;
    StringWriter sw = new StringWriter();
    for (char c : newLowercaseTag().toCharArray()) {
      if (c == '-') {
        needUppercase = true;
      } else {
        if (needUppercase) {
          sw.append(Character.toUpperCase(c));
          needUppercase = false;
        } else {
          sw.append(c);
        }
      }
    }
    return sw.toString();
  }
}
