package demo02_tf_idf
import org.apache.spark.ml.feature.{HashingTF, IDF, Tokenizer}
import util.SparkUtil

/**
 * Created by fansy on 2017/8/24.
 */
object TFIDFDemo {
  def main(args: Array[String]) {
    val sqlContext = SparkUtil.getSQLContext("tf-idf",true)

    val sentenceData = sqlContext.createDataFrame(Seq(
      (0, "Hi I heard about Spark"),
      (0, "I wish Java could use case classes"),
      (1, "Logistic regression models are neat")
    )).toDF("label", "sentence")

    // 分词
    val tokenizer = new Tokenizer().setInputCol("sentence").setOutputCol("words")
    println("wordsData----------------")
    val wordsData = tokenizer.transform(sentenceData)
    wordsData.show(3)
    // 求TF
    println("featurizedData----------------")
    val hashingTF = new HashingTF()
      .setInputCol("words").setOutputCol("rawFeatures")
    val featurizedData = hashingTF.transform(wordsData)
    featurizedData.show(3)
    // 求IDF
    println("recaledData----------------")
    val idf = new IDF().setInputCol("rawFeatures").setOutputCol("features")
    val idfModel = idf.fit(featurizedData)
    val rescaledData = idfModel.transform(featurizedData)
    rescaledData.show(3)
    println("----------------")
    rescaledData.select("features", "label").take(3).foreach(println)
  }
}
