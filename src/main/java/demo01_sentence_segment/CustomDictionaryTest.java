package demo01_sentence_segment;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.BaseSearcher;
import com.hankcs.hanlp.dictionary.CustomDictionary;
import com.hankcs.hanlp.tokenizer.SpeedTokenizer;

import java.util.Map;

/**
 * Created by fansy on 2017/8/24.
 */
public class CustomDictionaryTest {
    public static void main(String[] args) {

        //
        String text = "攻城狮逆袭单身狗，迎娶白富美，走上人生巅峰";  // 怎么可能噗哈哈！
//        System.out.println("默认分词："+HanLP.segment(text));
//        System.out.println("极速分词："+ SpeedTokenizer.segment(text));

        // 动态增加
        CustomDictionary.add("攻城狮");
        CustomDictionary.add("单身狗");
        // 标准分词
        System.out.println("默认分词："+HanLP.segment(text));
        System.out.println("极速分词："+ SpeedTokenizer.segment(text));
    }
}
