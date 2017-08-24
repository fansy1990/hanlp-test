package demo01_sentence_segment;

import com.hankcs.hanlp.HanLP;

/**
 * Created by fansy on 2017/8/24.
 */
public class HanLPTest {
    public static void main(String[] args) {
        System.out.println(HanLP.segment("你好，欢迎使用HanLP汉语处理包！"));
    }

}
