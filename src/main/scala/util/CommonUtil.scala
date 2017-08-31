package util

import com.hankcs.hanlp.dictionary.stopword.CoreStopWordDictionary
import com.hankcs.hanlp.tokenizer.StandardTokenizer
import scala.collection.JavaConversions._
/**
 * Created by fansy on 2017/8/30.
 */
object CommonUtil {
  /**
   * String 分词
   * 增加规则：
   * 1. 单词长度需要大于1
   * @param sentense
   * @return
   */
  def transform(sentense:String):List[String] ={
    val list = StandardTokenizer.segment(sentense)
    CoreStopWordDictionary.apply(list)
    list.map(x => x.word.replaceAll(" ","")).filter(_.size>1).toList
  }
}
