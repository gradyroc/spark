package cn.grady.spark.core.transformation

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author rociss 
 * @version 1.0, on 0:15 2022/8/5.
 */
object LineCount {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("LineCount")
      .setMaster("local")

    val sc = new SparkContext(conf)

    val lines = sc.textFile("D:\\Projects\\github\\spark-kit\\javaspark-kit\\src\\main\\resources\\LineCountFile")
    val linePair = lines.map{
      line => (line,1)
    }


    val lineCount = linePair.reduceByKey(_+_)
    lineCount.foreach(
      line => println(line._1 +" appears "+line._2+ " times")
    )


  }
}
