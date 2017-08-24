package demo01_sentence_segment;

import com.hankcs.hanlp.tokenizer.SpeedTokenizer;

/**
 * 极速分词不能使用自定义词典
 * Created by fansy on 2017/8/24.
 */
public class HighSpeedSegmentTest {
    public static void main(String[] args) {
        String text = "江西鄱阳湖干枯，中国最大淡水湖变成大草原";
        System.out.println(SpeedTokenizer.segment(text));
    }
}
