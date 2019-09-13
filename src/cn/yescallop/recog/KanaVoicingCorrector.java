package cn.yescallop.recog;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

/**
 * @author Scallop Ye
 */
public class KanaVoicingCorrector {

    private static final char VOICED_MARK = '\u3099';
    private static final char SEMI_VOICED_MARK = '\u309A';

    public static void main(String[] args) {
//        Path dir = Paths.get("D:\\mymusic");
//        try {
//            for (Path p : Files.walk(dir)
//                    .filter(Files::isRegularFile)
//                    .collect(Collectors.toList())) {
//                String name = p.getFileName().toString();
//                StringBuilder sb = new StringBuilder();
//                boolean b = correct(name, sb);
//                if (b) {
//                    String c = sb.toString();
//                    System.out.println(sb);
//                    Files.move(p, p.getParent().resolve(c));
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        StringBuilder sb = new StringBuilder();
        correct("[00:22.95]何回目たﾞっけ 忘れてしまうよ\n" +
                "[00:26.67]当たり障りのない日々てﾞ僕は\n" +
                "[00:30.21]正しいフリしてるの\n" +
                "[00:31.84]汚れてしまったんたﾞろう\n" +
                "[00:33.75]それに気付けない\n" +
                "[00:35.19]「お前は誰たﾞ？」\n" +
                "[00:37.16]関係ないたﾞろ 溺れてしまえよ\n" +
                "[00:40.83]有象無象の人混みに紛れては\n" +
                "[00:44.33]怯えてるんてﾞしょ\n" +
                "[00:46.13]誰たﾞってそうなんたﾞよ\n" +
                "[00:48.01]夢の中てﾞ踊りましょう\n" +
                "[00:51.14]不完全てﾞ不安定な心てﾞ\n" +
                "[00:54.90]最大の理想像を描いた\n" +
                "[00:58.38]対照的な自分なんて\n" +
                "[01:01.24]もう殺してしまえよ\n" +
                "[01:04.63]猜疑心に苛まれた少年は\n" +
                "[01:08.66]まやかしの明日を捲るのさ\n" +
                "[01:12.68]単純なんたﾞろうか　明かりは届かない\n" +
                "[01:19.89]常識に囚われて\n" +
                "[01:21.73]はみ出す勇気もなく\n" +
                "[01:23.54]汚れた身体てﾞ綺麗に踊る\n" +
                "[01:26.91]酩酊した思考かﾞ絶え間なく合図を送って\n" +
                "[01:31.76]ほら今を壊していくけとﾞ\n" +
                "[01:34.67]足りない意味ない\n" +
                "[01:35.62]気付いたら僕はもうタﾞメになっていた\n" +
                "[01:39.94]愚者のハﾟレートﾞ\n" +
                "[01:55.44]最低な生活\n" +
                "[01:57.71]君も同しﾞなの\n" +
                "[01:59.12]全てかﾞほﾞやけてて いつもそこに居なくて\n" +
                "[02:02.65]答えはないんてﾞしょ\n" +
                "[02:04.48]僕たﾞってもう分からないよ\n" +
                "[02:06.27]混沌とした街てﾞ一人ゆらり\n" +
                "[02:08.56]溶けていく\n" +
                "[02:09.57]行き交う人々\n" +
                "[02:12.77]その誰もかﾞ　偽りに見えてしまって\n" +
                "[02:16.55]揺れ動く僕の感情かﾞ\n" +
                "[02:19.95]音を立てて崩れてく\n" +
                "[02:23.63]絵空事世界なんて 誰かの偶像たﾞ\n" +
                "[02:31.14]一縷の希望に縋って\n" +
                "[02:32.81]周りを塞きﾞ込んてﾞ\n" +
                "[02:34.68]とﾞれかﾞ本当\n" +
                "[02:35.63]顔かﾞ見えない\n" +
                "[02:37.76]ホﾞロホﾞロになった仮面を\n" +
                "[02:40.40]懲りすﾞまた直しては\n" +
                "[02:42.65]壊してを繰り返して行く\n" +
                "[02:45.79]止まない止まないこの歓声は\n" +
                "[02:47.63]僕らを嘲笑っていた\n" +
                "[02:52.30]並行世界の君は\n" +
                "[02:53.71]黒に紛れ込んてﾞ\n" +
                "[02:55.52]もうとﾞうやったって後悔はﾞっかたﾞ\n" +
                "[02:57.56]味気ないてﾞしょ\n" +
                "[02:59.49]死にきれない劣等感と\n" +
                "[03:01.28]消しきれない背徳感てﾞ\n" +
                "[03:03.08]現実かﾞ停止した\n" +
                "[03:04.46]ねえ誰か救ってよ\n" +
                "[03:20.51]ニセモノたﾞって\n" +
                "[03:22.26]ホンモノたﾞったって\n" +
                "[03:24.11]淘汰していく\n" +
                "[03:27.86]単純なんたﾞろうか　明かりは灯らない\n" +
                "[03:34.98]常識に囚われすﾞ　何度も悩み抜いて\n" +
                "[03:38.66]それてﾞも答えは変わらぬまま\n" +
                "[03:42.03]酩酊した思考かﾞ\n" +
                "[03:44.52]絶え間なく合図を送って\n" +
                "[03:46.95]もう僕を殺してくれよ\n" +
                "[03:49.72]足りない意味無い\n" +
                "[03:50.77]気付いたら僕らもうタﾞメになっていた\n" +
                "[03:56.83]醒めない夢かい\n" +
                "[03:57.87]気付かすﾞに今日もまた踊って狂えよ\n" +
                "[04:02.11]愚者のハﾟレートﾞ\n", sb);
        System.out.println(sb);
    }

    public static boolean correct(String s, StringBuilder sb) {
        char[] chars = s.toCharArray();
        if (chars.length == 1) {
            sb.append(chars[0]);
            return false;
        }
        Character last = chars[0];
        boolean corrected = false;
        for (int i = 1; i < chars.length; i++) {
            char cur = chars[i];
            if (last == null) {
                if (i + 1 == chars.length)
                    sb.append(cur);
                else last = cur;
            } else if (cur == 'ﾞ') {
                sb.append((char) (last + 1));
                corrected = true;
                last = null;
            } else if (cur == 'ﾟ') {
                sb.append((char) (last + 2));
                corrected = true;
                last = null;
            } else  {
                sb.append(last);
                if (i + 1 == chars.length)
                    sb.append(cur);
                last = cur;
            }
        }
        return corrected;
    }
}
