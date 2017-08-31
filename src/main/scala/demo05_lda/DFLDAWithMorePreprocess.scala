package demo05_lda

import com.hankcs.hanlp.dictionary.CustomDictionary
import org.apache.spark.mllib.clustering.{DistributedLDAModel, LDA}
import org.apache.spark.mllib.linalg.{Vector, Vectors}
import org.apache.spark.rdd.RDD
import util.{CommonUtil, SparkUtil}

import scala.collection.mutable

/**
 * 循环次数调整低了后，算法可以继续运行，不过效果不好，先考虑删除一些词：
 * 1. 长度为1的删掉；
 * 2.
 * 使用DF来构建LDA模型
 *
 * Created by fansy on 2017/8/30.
 */
object DFLDAWithMorePreprocess {
  def main(args: Array[String]) {
    val data = "data/allinone/data.txt"
    val (input_data,maxIteration,numTopics,testOrNot) =(data,50,10,true)

    val sc = SparkUtil.getSparkContext("LDA use DF",testOrNot)
    val sqlContext = SparkUtil.getSQLContext(sc)

    // 1. 添加自定义词典
    CustomDictionary.add("日  期")
    CustomDictionary.add("版  号")
    CustomDictionary.add("标  题")
    CustomDictionary.add("作  者")
    CustomDictionary.add("正  文")

    // 2. 读取数据并分词
    println("segment sentences ...")
    val docs = sc.textFile(input_data).map{x => val t = x.split(".txt\t");(t(0),CommonUtil.transform(t(1)))}.zipWithIndex()

    // 3. 构建词汇表
    // termCounts: 按照(term, termCount) 排序，termCount越大排序越前
    val termCounts: Array[(String, Long)] =  docs.flatMap(_._1._2.map(_ -> 1L))
      .reduceByKey(_ + _).collect().sortBy(-_._2)
    // 把构建好的词汇表进行编码
    val vocabArray = termCounts.map(_._1)// 用于反编码
    val vocab = vocabArray.zipWithIndex.toMap

    println(s"vocab size :${vocab.size}")

    // 4. 把文档转换为词向量DF，同时使用编码后的文档名
    println("construct doc array...")
    val documents: RDD[(Long, Vector)] =  docs.map(x => (x._1._2,x._2)).map {
      case (tokens, id) =>
        val counts = new mutable.HashMap[Int, Double]()
        tokens.foreach { term =>
          if (vocab.contains(term)) {
            val idx = vocab(term)
            counts(idx) = counts.getOrElse(idx, 0.0) + 1.0
          }
        }
        (id, Vectors.sparse(termCounts.size, counts.toSeq))
    }.cache
    println("creating model ...")
    // 5. 构建LDA模型
    val lda = new LDA().setK(numTopics).setMaxIterations(maxIteration)
    val ldaModel = lda.run(documents)

    // 6. 模型评价
    println("model evaluation ...")
    val distLDAModel = ldaModel.asInstanceOf[DistributedLDAModel]
    val avgLogLikelihood = distLDAModel.logLikelihood / docs.count().toDouble
    println(s"\t Training data average log likelihood: $avgLogLikelihood")
    println()

    // 7. 打印所有主题及每个主题的前10个关键字（按照权重排序）.
    val topicIndices = ldaModel.describeTopics(maxTermsPerTopic = 10)
    topicIndices.foreach { case (terms, termWeights) =>
      println("TOPIC:")
      terms.zip(termWeights).foreach { case (term, weight) =>
        println(s"${vocabArray(term.toInt)}\t$weight")
      }
      println()
    }
  }
}
