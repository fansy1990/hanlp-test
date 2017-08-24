package demo01_sentence_segment;

import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;

import java.util.List;

/**
 * Created by fansy on 2017/8/24.
 */
public class StandardTokenizerTest {
    public static void main(String[] args) {
        List<Term> termList = StandardTokenizer.segment("商品和服务");
        System.out.println(termList);
    }
}
