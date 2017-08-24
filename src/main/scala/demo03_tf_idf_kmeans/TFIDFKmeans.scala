package demo03_tf_idf_kmeans

import util.SparkUtil

/**
 * 使用TF-IDF对文件进行向量化，并使用kmeans来对文本进行聚类
 * 太多文件处理速度太慢，考虑合并成一个文件输入
 * Created by fansy on 2017/8/24.
 */
object TFIDFKmeans {
  val data = "data/all"
  def main(args: Array[String]) {
    val sc = SparkUtil.getSparkContext("Kmeans use tf-idf",true)

    val docs = sc.wholeTextFiles(data)
    println(docs.count())
    println(docs.first())
  }
}
