package cn.yescallop.recog;

import java.util.*;

/**
 * @author Scallop Ye
 */
public class TitleParser {

    private static final String SEPARATORS = "／/_-[]【】（）()[]［］「」『』＆&.× 　,";
    private final Set<String> ignoredWords = new HashSet<>();

    public void ignore(String... words) {
        Collections.addAll(ignoredWords, words);
    }


    public List<String> parse(String title) {
        List<String> parts = new ArrayList<>();

        StringBuilder part = new StringBuilder();
        boolean lastDelimiter = false;
        int len = title.length();
        for (int i = 0; i < len; i++) {
            char ch = title.charAt(i);
            if (SEPARATORS.indexOf(ch) >= 0) {
                if (lastDelimiter)
                    continue;
                lastDelimiter = true;
            } else {
                if (lastDelimiter && part.length() != 0) {
                    add(part, parts);
                    part = new StringBuilder();
                }
                part.append(ch);
                lastDelimiter = false;
            }
        }
        add(part, parts);
        return parts;
    }

    private void add(StringBuilder b, List<String> list) {
        String s = b.toString().strip();
        if (!s.isEmpty() && !ignoredWords.contains(s.toLowerCase()) && !list.contains(s)) list.add(s);
    }
}
