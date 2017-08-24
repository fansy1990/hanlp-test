package util

import java.io.File

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkContext,SparkConf}

/**
 * Spark 工具类
 * Created by fansy on 2017/8/24.
 */
object SparkUtil {
  val OS = "Windows"// 运行平台系统
//  def main(args:Array[String])={
//    println(winutil_path)
//  }

  val winutil_path = new File("src/main/scala/util").getAbsolutePath //winutils.exe所在地址
  /**
   * 获取SparkContext
   * @param appName
   * @param testOrNot
   * @return
   */
  def getSparkContext(appName:String , testOrNot:Boolean):SparkContext = {
    if("Windows".equals(OS)){
      System.setProperty("hadoop.home.dir", winutil_path)
    }
    val conf = if(testOrNot){
      new SparkConf().setAppName(appName).setMaster("local[4]")
    }else{
      new SparkConf().setAppName(appName)
    }
    new SparkContext(conf)
  }

  /**
   * 获取SQLContext
   * @param appName
   * @param testOrNot
   * @return
   */
  def getSQLContext(appName:String , testOrNot:Boolean):SQLContext ={
    new SQLContext(getSparkContext(appName,testOrNot))
  }
}
