package demo01_sentence_segment;

import com.hankcs.hanlp.dictionary.CustomDictionary;
import com.hankcs.hanlp.dictionary.stopword.CoreStopWordDictionary;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;
import com.hankcs.hanlp.tokenizer.SpeedTokenizer;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;

import java.util.List;

/**
 * NLP和Standard的都可以，分词效果都可以
 * HighSpeed的分词效果比较差；
 * Created by fansy on 2017/8/24.
 */
enum SegmentType{
    Standard,HighSpeed,NLP
}
public class SegmentDemo {

    public static void main(String[] args) {
        String[]sentenses = {
            "你好，欢迎使用HanLP汉语处理包！",
                "江西鄱阳湖干枯，中国最大淡水湖变成大草原",
                "中国科学院计算技术研究所的宗成庆教授正在教授自然语言处理课程",
                "商品和服务"
        };

        for(String sentense :sentenses){

            System.out.println("NLP:"+process(sentense,SegmentType.NLP));
            System.out.println("Standard:"+process(sentense,SegmentType.Standard));
            System.out.println("Speed:"+process(sentense,SegmentType.HighSpeed));
            System.out.println();
        }

        for(String sentense :sentenses){
            CustomDictionary.add("自然语言处理");
            System.out.println("NLP:"+process(sentense,SegmentType.NLP));
            System.out.println("Standard:"+process(sentense,SegmentType.Standard));
            System.out.println("Speed:"+process(sentense,SegmentType.HighSpeed));
            System.out.println();
        }

        CustomDictionary.remove("自然语言处理");
        for(String sentense :sentenses){

            System.out.println("NLP:"+process(sentense,SegmentType.NLP));
            System.out.println("Standard:"+process(sentense,SegmentType.Standard));
            System.out.println("Speed:"+process(sentense,SegmentType.HighSpeed));
            System.out.println();
        }
    }

    private static String process(String sentense,SegmentType segmentType){
        List<Term> list;
        switch (segmentType){
            case HighSpeed:
                list= SpeedTokenizer.segment(sentense);
                CoreStopWordDictionary.apply(list);
                return list.toString();
            case NLP:
                list =NLPTokenizer.segment(sentense);
                CoreStopWordDictionary.apply(list);
                return list.toString();
            case Standard:
                list = StandardTokenizer.segment(sentense);
                CoreStopWordDictionary.apply(list);
                return list.toString();
            default:
                return null;
        }
    }
}
