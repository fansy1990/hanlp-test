package demo01

import org.apache.spark.Partitioner
import util.SparkUtil

/**
 * 测试Partition
 * Created by fansy on 2017/8/25.
 */
object ParititionTest {
   def main (args: Array[String]){
    val t = List(
      ("121.txt",0),("122.txt",0),("123.txt",3),("124.txt",0),("125.txt",0),("126.txt",1),
      ("221.txt",3),("222.txt",4),("223.txt",3),("224.txt",3),("225.txt",3),("226.txt",1),
      ("421.txt",4),("422.txt",4),("4.txt",3),("41.txt",3),("43.txt",4),("426.txt",1)
    )
     val sc = SparkUtil.getSparkContext("test partitioner",true)

     val data = sc.parallelize(t)
     val file_index = data.map(_._1.charAt(0)).distinct.zipWithIndex().collect().toMap
     println(file_index)
     val partitionData = data.partitionBy(MyPartitioner(file_index))

     val tt = partitionData.mapPartitionsWithIndex((index: Int, it: Iterator[(String,Int)]) => it.toList.map(x => (index,x)).toIterator)
     tt.collect().foreach(println(_))
     // firstCharInFileName , firstCharInFileName - predictType
     val combined = partitionData.map(x =>( (x._1.charAt(0), Integer.parseInt(x._1.charAt(0)+"") - x._2),1) )
     .mapPartitions{f => var aMap = Map[(Char,Int),Int]();
       for(t <- f){
         if (aMap.contains(t._1)){
           aMap = aMap.updated(t._1,aMap.getOrElse(t._1,0)+1)
         }else{
           aMap = aMap + t
         }
       }
       val aList = aMap.toList
       val total= aList.map(_._2).sum
       val total_right = aList.map(_._2).max
       List((aList.head._1._1,total,total_right)).toIterator
       //       aMap.toIterator //打印各个partition的总结
     }

     val result = combined.collect()
     result.foreach(println(_))
      for(re <- result ){
        println("文档"+re._1+"开头的 文档总数："+ re._2+",分类正确的有："+re._3+",分类正确率是："+(re._3*100.0/re._2)+"%")
      }
     val averageRate = result.map(_._3).sum *100.0 / result.map(_._2).sum
     println("平均正确率为："+averageRate+"%")
     sc.stop()
  }
}

case class MyPartitioner(file_index:Map[Char,Long]) extends Partitioner{
  override def getPartition(key: Any): Int = key match {
    case _ => file_index.getOrElse(key.toString.charAt(0),0L).toInt
  }
  override def numPartitions: Int = file_index.size
}
