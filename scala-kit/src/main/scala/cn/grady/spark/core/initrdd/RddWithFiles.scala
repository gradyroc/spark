package cn.grady.spark.core.initrdd

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author rociss 
 * @version 1.0, on 23:33 2022/7/29.
 */
object RddWithFiles {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf()
      .setAppName("ParallelizeCollection")
      .setMaster("local")

    val sc = new SparkContext(conf)

    val lines = sc.textFile("")


    val count = lines.map(
      line => line.split(" ").length
    ).reduce(_ + _)

    println("file's words count :" + count)
  }

}
