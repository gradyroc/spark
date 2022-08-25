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

    //    groupByKey()
    //    reduceByKey()
    //    sortByKey()

    joinAndCogroup()
  }

  def joinAndCogroup(): Unit = {
    val studentList = Array(
      Tuple2(1, "leo")
      , Tuple2(2, "tom")
      , Tuple2(3, "marry")
      , Tuple2(4, "jack"))
    val scoreList = Array(
      Tuple2(1, 80),
      Tuple2(1, 70),
      Tuple2(2, 90),
      Tuple2(2, 100),
      Tuple2(3, 100),
      Tuple2(3, 60),
      Tuple2(4, 60))

    val students = sc.parallelize(studentList)
    val scores = sc.parallelize(scoreList)

    val studentScores = students.join(scores)
//    studentScores.foreach(studentScore => {
//      println("student id :" + studentScore._1)
//      println("student name:" + studentScore._2._1)
//      println("student score:" + studentScore._2._2)
//      println("=========================")
//    })

    val studentScoreCoGroup = students.cogroup(scores)
    studentScoreCoGroup.foreach(studentScore => {
      println("student id :" + studentScore._1)
      println("student name:" + studentScore._2._1)
      println("student score:" + studentScore._2._2)
      println("=========================")
    })


  }

  def sortByKey(): Unit = {
    val scoreList = Array(Tuple2(65, "leo")
      , Tuple2(90, "tom")
      , Tuple2(80, "marry")
      , Tuple2(70, "jack"))
    val scoresRDD = sc.parallelize(scoreList)
    //    val sortRDD = scoresRDD.sortByKey()
    val sortRDD = scoresRDD.sortByKey(false)
    sortRDD.foreach(score => {
      println(score._1 + ":", score._2)
    })
  }


  def reduceByKey(): Unit = {
    val scoreList = Array(Tuple2("class1", 80),
      Tuple2("class2", 90),
      Tuple2("class1", 100),
      Tuple2("class2", 60))
    val scores = sc.parallelize(scoreList)
    val totalScores = scores.reduceByKey(_ + _)
    totalScores.foreach(classScore => {
      println(classScore._1 + ":" + classScore._2)
    })

  }


  def groupByKey(): Unit = {
    val scoreList = Array(Tuple2("class1", 80),
      Tuple2("class2", 90),
      Tuple2("class1", 100),
      Tuple2("class2", 60))
    val scores = sc.parallelize(scoreList)
    val groupedScores = scores.groupByKey()
    groupedScores.foreach(group => {
      println(group._1)
      group._2.foreach(score => {
        println(score)
      })
      println("=================")
    })


  }

  def flatMap(): Unit = {
    val lineArray = Array("hello you", "hello me", "hello world")
    val lines = sc.parallelize(lineArray)
    val words = lines.flatMap(s => s.split(" "))
    words.foreach(println)

  }

  def filter(): Unit = {
    val numbers = Array(1, 2, 3, 4, 5, 6, 7, 8, 9)
    val numberRDD = sc.parallelize(numbers)

    val evenRDD = numberRDD.filter(num => num % 2 == 0)
    evenRDD.foreach(println)

  }

  def map(): Unit = {
    val numbers = Array(1, 2, 3, 4, 5)
    val numberRDD = sc.parallelize(numbers)

    //    val mapRDD = numberRDD.map(_*2)
    val mapRDD = numberRDD.map(s => s * 2)
    //    mapRDD.foreach(println)
    mapRDD.foreach(num => println(num))
  }
}
