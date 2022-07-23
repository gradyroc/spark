package cn.grady.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Iterator;


/**
 * 本地测试的wordCount
 *
 * @author rociss
 * @version 1.0, on 22:12 2022/7/15.
 */
public class WordcountSubmit {

    public static void main(String[] args) {

        //submit 到集群中执行
        /**
         * step1:将本地文件上传hdfs
         * step2: pom.xml 里的maven插件，将spark工程打包
         * step3：将打包的spark的jar包，上传到机器
         * step4：编写spark-submit脚本，执行脚本，提交到spark集群执行
         */

        //step1:sparkconf
        // setMaster("local"):spark集群的master的url，如果是local，则是本地运行
        SparkConf conf = new SparkConf()
                .setAppName("WordcountLocal");

        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> lines = sc.textFile("hdfs://minimal126:9000/wordCount");
        JavaRDD<String> words = lines.flatMap(new FlatMapFunction<String, String>() {

            @Override
            public Iterator<String> call(String line) throws Exception {
                return Arrays.asList(line.split(" ")).iterator();
            }
        });

        JavaPairRDD<String, Integer> pairs = words.mapToPair(new PairFunction<String, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(String word) throws Exception {

                return new Tuple2<>(word, 1);
            }
        });
        JavaPairRDD<String, Integer> wordCounts = pairs.reduceByKey(
                new Function2<Integer, Integer, Integer>() {
                    @Override
                    public Integer call(Integer v1, Integer v2) throws Exception {
                        return v1 + v2;
                    }
                });

        wordCounts.foreach(new VoidFunction<Tuple2<String, Integer>>() {
            @Override
            public void call(Tuple2<String, Integer> wordCount) throws Exception {
                System.out.println(wordCount._1 + " appeared " + wordCount._2);
            }
        });

        sc.close();

    }


}
