package cn.grady.spark.core.wordcount;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.*;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Iterator;


/**
 * 本地测试的wordCount
 *
 * @author rociss
 * @version 1.0, on 22:12 2022/7/15.
 */
public class WordcountLocal {

    public static void main(String[] args) {

        //本地执行可以在idea 中直接执行


        //step1:sparkconf
        // setMaster("local"):spark集群的master的url，如果是local，则是本地运行
        SparkConf conf = new SparkConf()
                .setAppName("WordcountLocal")
                .setMaster("local");

        //step2: JavaSparkContext,spark中，context是所有功能的入口，
        //主要功能，初始化spark应用程序的核心组件，调度器（DAGSchedule，TaskScheduler），去spark master节点注册，是最重要的对象
        // 不同的语言，context名字不同，
        // java-> JavaSparkContext;
        // sparkSql-> SQLContext,HiveContext
        // spark streaming-> SparkContext

        JavaSparkContext sc = new JavaSparkContext(conf);

        //step3: 对输入源创建初始RDD（hdfs，本地文件，等等）
        // 输入源中的数据会打散，分配到RDD的每个partition中，从而形成一个分布式的数据集
        //本地文件-> textFile(),java 中都叫JavaRDD
        // 每个元素就相当于文件里的一行
        JavaRDD<String> lines = sc.textFile("D:\\Projects\\github\\spark-kit\\scala-kit\\src\\main\\resources\\wordCountFile");

        /**
         *step4:  对初始RDD 进行transformation 操作，计算？
         * 通常操作会通过创建 function，并配合RDD的map，flapmap等算子来执行
         * function通常比较简单，则创建指定Function的匿名内部类
         * 但是如果function 比较复杂，则会单独创建一个类，作为实现这个function接口的类
         *
         * FlatMapFunction： 两个泛型参数，分别代表输入和输出
         *
         * flapMap算子的作用，将RDD的一个元素拆分成一个或者多个元素
         */
        JavaRDD<String> words = lines.flatMap(new FlatMapFunction<String, String>() {

            @Override
            public Iterator<String> call(String line) throws Exception {
                return Arrays.asList(line.split(" ")).iterator();
            }
        });

        /**
         * 将每个单词映射为（word，1）,从而对每个单词进行次数的累加
         * mapToPair 将每个元素映射为《v1，v2》这样的tuple2类型的元素
         *
         * PairFunction:第一个参数：输入参数
         * 第二第三个《v1，v2》
         */

        JavaPairRDD<String, Integer> pairs = words.mapToPair(new PairFunction<String, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(String word) throws Exception {

                return new Tuple2<>(word, 1);
            }
        });

        /**
         * reduceByKey：对每个key对应的value都进行reduce操作
         * （hello，1）（hello，1），（word，1）
         * ==》 1+1 = 2
         * 1=1
         *
         */
        JavaPairRDD<String, Integer> wordCounts = pairs.reduceByKey(
                new Function2<Integer, Integer, Integer>() {
                    @Override
                    public Integer call(Integer v1, Integer v2) throws Exception {
                        return v1 + v2;
                    }
                });

        /**
         * 上述均为transform 操作，需执行action操作，对数据进行保存
         */
        wordCounts.foreach(new VoidFunction<Tuple2<String, Integer>>() {
            @Override
            public void call(Tuple2<String, Integer> wordCount) throws Exception {
                System.out.println(wordCount._1 + " appeared " + wordCount._2);
            }
        });

        sc.close();

    }


}
