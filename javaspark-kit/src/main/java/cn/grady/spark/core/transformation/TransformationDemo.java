package cn.grady.spark.core.transformation;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.VoidFunction;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author rociss
 * @version 1.0, on 0:25 2022/8/5.
 */
public class TransformationDemo {

    static SparkConf conf = new SparkConf()
            .setMaster("local")
            .setAppName("LineCount");

    static JavaSparkContext sc = new JavaSparkContext(conf);

    public static void main(String[] args) {

//        map();
//        filter();
//        flatMap();
        groupByKey();

        close();
    }


    /**
     * groupByKey : 每个班级的成绩进行分组
     */

    public static void groupByKey(){
        List<Tuple2<String,Integer>> scoresList = Arrays.asList(
                new Tuple2<String,Integer>("class1",80),
                new Tuple2<String,Integer>("class2",90),
                new Tuple2<String,Integer>("class1",100),
                new Tuple2<String,Integer>("class2",60)
        );

        JavaPairRDD<String,Integer> scores = sc.parallelizePairs(scoresList);
        /**
         * groupByKey 返回的还是 JavaPairRDD，
         * 但是JavaPairRDD的第一个泛型类型不变，第二个泛型类型变成 Iterable这种集合类型
         * 按照key分组了，每个key可能都对应会有多个value，此时多个value 聚合成Iterable
         * 所以，分组后，处理key对应的所有分组内的数据
         *
         */
        JavaPairRDD<String, Iterable<Integer>> groupScores = scores.groupByKey();

        groupScores.foreach(new VoidFunction<Tuple2<String, Iterable<Integer>>>() {
            @Override
            public void call(Tuple2<String, Iterable<Integer>> stringIterableTuple2) throws Exception {
                System.out.println("class :"+stringIterableTuple2._1);
                Iterator<Integer> ite = stringIterableTuple2._2.iterator();
                while (ite.hasNext()){
                    System.out.println(ite.next());
                }
                System.out.println("================");
            }
        });
    }

    /**
     * flatMap: 将文本行拆分为多个单词
     */
    public static void flatMap(){
        List<String> lines = Arrays.asList("hello you", "hello me ", "hello world");

        JavaRDD<String> linesRdd = sc.parallelize(lines);

        /**
         * 入参：FlatMapFunction ，需要自定义第二个返回的参数 U，即返回的新元素类型
         * call() 方法返回的类型是  Iterator<U>，
         * flatMap的功能：对接收的每个RDD元素，处理之后返回多个元素，封装在Iterator 集合中，可以使用集合操作
         * 返回值封装了所有元素，新的RDD 集合大小一定大于原来的元素
         */
        JavaRDD<String> lineRDD = linesRdd.flatMap(new FlatMapFunction<String, String>() {

            /**
             *
             * @param s ：hello you
             * @return Iterable<String>(hello,you)
             * @throws Exception
             */
            @Override
            public Iterator<String> call(String s) throws Exception {
                return Arrays.stream(s.split(" ")).iterator();
            }
        });

        lineRDD.foreach(new VoidFunction<String>() {
            @Override
            public void call(String s) throws Exception {
                System.out.println(s);
            }
        });
    }


    /**
     * filter，过滤欧数值
     */

    public static void filter() {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

        JavaRDD<Integer> numberRDD = sc.parallelize(integers);

        /**
         * call的返回值是boolean,为true的才返回
         */
        JavaRDD<Integer> evenRDD = numberRDD.filter(new Function<Integer, Boolean>() {
            @Override
            public Boolean call(Integer v1) throws Exception {
                return v1 % 2 == 0;
            }
        });
        evenRDD.foreach(new VoidFunction<Integer>() {
            @Override
            public void call(Integer integer) throws Exception {
                System.out.println(integer);
            }
        });

    }

    /**
     * map 算子，将集合中每个元素都乘以2
     */
    public static void map() {


        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        JavaRDD<Integer> numberRDD = sc.parallelize(numbers);

        /**
         *
         * 对任何rdd都可以调用，java中map算子的入参是Function
         * 创建Function 时，第二个泛型参数就是返回值的类型
         * call()方法的返回类型，也必须与第二个泛型类型参数类型同步
         */
        JavaRDD<Integer> mapRDD = numberRDD.map(new Function<Integer, Integer>() {
            @Override
            public Integer call(Integer v1) throws Exception {
                return v1 * 2;
            }
        });

        mapRDD.foreach(new VoidFunction<Integer>() {
            @Override
            public void call(Integer integer) throws Exception {
                System.out.println(integer);
            }
        });


    }

    public static void close() {
        sc.close();
    }
}
