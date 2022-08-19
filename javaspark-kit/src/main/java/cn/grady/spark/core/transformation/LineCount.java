package cn.grady.spark.core.transformation;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
import scala.Tuple2;

/**
 * @author rociss
 * @version 1.0, on 0:03 2022/8/5.
 *
 * 统计每行出现的次数
 */
public class LineCount {

    public static void main(String[] args) {
        SparkConf conf = new SparkConf()
                .setMaster("local")
                .setAppName("LineCount");

        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> lines = sc.textFile("D:\\Projects\\github\\spark-kit\\javaspark-kit\\src\\main\\resources\\LineCountFile");

        // 将每一行映射为<line,1> 这种格式
        JavaPairRDD<String, Integer> pairs = lines.mapToPair(new PairFunction<String, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(String s) throws Exception {
                return new Tuple2<>(s, 1);
            }
        });

        //对pairRDD 执行reduceByKey算子，统计出每一行出现的总次数
        JavaPairRDD<String, Integer> lineCounts = pairs.reduceByKey(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer v1, Integer v2) throws Exception {
                return v1 + v2;
            }
        });

        //执行一个action ，打印每一行出现的次数
//        lineCounts.foreach(System.out::println);
        lineCounts.foreach(new VoidFunction<Tuple2<String, Integer>>() {
            @Override
            public void call(Tuple2<String, Integer> tuple2) throws Exception {
                System.out.println(tuple2._1+" 出现 了 "+tuple2._2 +" 次");
            }
        });

        sc.close();


    }
}
