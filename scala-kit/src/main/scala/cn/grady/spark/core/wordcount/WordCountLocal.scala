package cn.grady.spark.core.wordcount

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author rociss
 * @version 1.0, on 22:38 2022/7/23.
 */
object WordCountLocal {


  def main(args: Array[String]): Unit = {

    val conf = new SparkConf()
      .setAppName("wordCount")
      .setMaster("local")

    val sc = new SparkContext(conf)

    val lines = sc.textFile("D:\\Projects\\github\\spark-kit\\scala-kit\\src\\main\\resources\\wordCountFile")
    val words = lines.flatMap(line => line.split(" "))
    val pairs = words.map(word => (word, 1))
    val wordCounts = pairs.reduceByKey(_ + _)

    wordCounts.foreach(wordCount => {
      println(s"  ${wordCount._1}  appeared  ${wordCount._2} times ")
    })


  }
}
