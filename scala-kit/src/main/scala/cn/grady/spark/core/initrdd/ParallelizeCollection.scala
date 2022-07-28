package cn.grady.spark.core.initrdd

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author rociss 
 * @version 1.0, on 23:37 2022/7/28.
 */
object ParallelizeCollection {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("ParallelizeCollection")
      .setMaster("local")

    val sc = new SparkContext(conf)

    val numbers = Array(1,2,3,4,5,6,7,8,9,10)
    //设置5个partition
    val numberRDD = sc.parallelize(numbers,5)
    val sum = numberRDD.reduce(_+_)
    println(sum)
  }

}
