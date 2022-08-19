package cn.grady.spark.core.transformation

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author rociss 
 * @version 1.0, on 1:33 2022/8/12.
 */
object TransformationOperation {
  val conf = new SparkConf()
    .setAppName("LineCount")
    .setMaster("local")

  val sc = new SparkContext(conf)



  def main(args: Array[String]): Unit = {

//    map()

//    filter()

//    flatMap()

    groupByKey()
  }

  def groupByKey(): Unit = {
   val scoreList = Array(Tuple2("class1",80),
     Tuple2("class2",90),
     Tuple2("class1",100),
     Tuple2("class2",60))
    val scores = sc.parallelize(scoreList)
    val groupedScores = scores.groupByKey()
    groupedScores.foreach(group=>{
      println(group._1)
      group._2.foreach(score=>{
        println(score)
      })
      println("=================")
    })


  }

  def flatMap(): Unit ={
    val lineArray = Array("hello you","hello me","hello world")
    val lines = sc.parallelize(lineArray)
    val words = lines.flatMap(s=>s.split(" "))
    words.foreach(println)

  }

  def filter(): Unit ={
    val numbers = Array(1,2,3,4,5,6,7,8,9)
    val numberRDD = sc.parallelize(numbers)

    val evenRDD =  numberRDD.filter(num=> num%2==0)
    evenRDD.foreach(println)

  }

  def  map(): Unit ={
    val numbers = Array(1,2,3,4,5)
    val numberRDD = sc.parallelize(numbers)

//    val mapRDD = numberRDD.map(_*2)
    val mapRDD = numberRDD.map(s=> s*2)
//    mapRDD.foreach(println)
    mapRDD.foreach(num=> println(num))
  }
}
