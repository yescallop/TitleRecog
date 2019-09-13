package cn.yescallop.recog;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Scallop Ye
 */
public class Main {

    private static final List<String> authors = new ArrayList<>();

    private static Pattern DATE = Pattern.compile("\\d{8}");
    public static void main(String[] args) {
        Path dir = Paths.get("D:\\mymusic\\Sou_mp3");
        TitleParser parser = new TitleParser();
        parser.ignore("cover", "mv", "歌ってみた", "ver", "feat");
        Scanner scanner = new Scanner(System.in);
        try {
            for (Path p : Files.walk(dir)
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList())) {
                String name = p.getFileName().toString();
                int point = name.indexOf('.');
                String ext = name.substring(point);
                String date = name.substring(0, 8);
                if (!DATE.matcher(date).matches())
                    continue;
                name = name.substring(9, point);
                List<String> result = parser.parse(name);
                try {
                    Path to = p.getParent().resolve(date + "-" + process(parser, scanner, name, result) + ext);
                    Files.move(p, to);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String process(TitleParser parser, Scanner scanner, String name, List<String> result) {
        System.out.println(name);
        List<String> curAuthors = new ArrayList<>();
        result.removeIf(s -> {
            boolean r = authors.contains(s);
            if (r) curAuthors.add(s);
            return r;
        });
        while (true) {
            String authorStr = curAuthors.stream().reduce((a, b) -> a + ";" + b).orElse(null);
            if (result.size() == 1) {
                if (authorStr == null) {
                    System.out.println("Failed, enter by yourself.");
                    return customize(scanner, null);
                }
                String res = authorStr + " - " + result.get(0);
                System.out.println("Result: " + res);
                while (true) {
                    System.out.println("- y(es) | c(ustomize) -");
                    String c = scanner.nextLine();
                    switch (c) {
                        case "y":
                            return res;
                        case "c":
                            return customize(scanner, authorStr);
                    }
                }
            }
            if (authorStr != null)
                System.out.println("Author: " + authorStr);
            for (int i = 0; i < result.size(); i++) {
                String s = result.get(i);
                System.out.printf("(%d) %s\n", i, s);
            }
            System.out.println("- a(uthor) | m(ask) | c(ustomize) | s(elect) -");
            String[] cmd = scanner.nextLine().split(" ");
            int[] idxs;
            switch (cmd[0]) {
                case "c":
                    return customize(scanner, authorStr);
                case "m":
                    if (cmd.length == 1) {
                        System.out.println("Error: no params");
                        continue;
                    }
                    idxs = new int[cmd.length - 1];
                    for (int i = 1; i < cmd.length; i++) {
                        idxs[i - 1] = Integer.parseInt(cmd[i]);
                    }
                    Arrays.sort(idxs);
                    for (int i = idxs.length - 1; i >= 0; i--) {
                        parser.ignore(result.get(idxs[i]));
                        result.remove(idxs[i]);
                    }
                    break;
                case "a":
                    if (cmd.length == 1) {
                        System.out.println("Error: no params");
                        continue;
                    }
                    idxs = new int[cmd.length - 1];
                    for (int i = 1; i < cmd.length; i++) {
                        idxs[i - 1] = Integer.parseInt(cmd[i]);
                    }
                    Arrays.sort(idxs);
                    for (int i = idxs.length - 1; i >= 0; i--) {
                        String a = result.get(idxs[i]);
                        authors.add(a);
                    }
                    result.removeIf(s -> {
                        boolean r = authors.contains(s);
                        if (r) curAuthors.add(s);
                        return r;
                    });
                    break;
                case "s":
                    if (cmd.length != 2) {
                        System.out.println("Error: no params");
                        continue;
                    }
                    int idx = Integer.parseInt(cmd[1]);
                    result = List.of(result.get(idx));
                    break;
            }
        }
    }

    private static String customize(Scanner scanner, String author) {
        if (author != null) {
            System.out.printf("Author (%s): ", author);
        } else {
            System.out.print("Author: ");
        }

        String authorNew = scanner.nextLine();
        if (!authorNew.isEmpty()) author = authorNew;
        System.out.print("Title: ");
        String title = scanner.nextLine();
        return author + " - " + title;
    }
}
