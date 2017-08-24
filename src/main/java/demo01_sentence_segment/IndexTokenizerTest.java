package demo01_sentence_segment;

import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.IndexTokenizer;

import java.util.List;

/**
 * Created by fansy on 2017/8/24.
 */
public class IndexTokenizerTest {
    public static void main(String[] args) {
        List<Term> termList = IndexTokenizer.segment("主副食品");
        for (Term term : termList)
        {
            System.out.println(term + " [" + term.offset + ":" + (term.offset + term.word.length()) + "]");
        }
    }
}
