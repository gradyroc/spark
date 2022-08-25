package cn.grady.spark.core.transformation;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
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
//        groupByKey();

//        reduceBykey();

//        sortByKey();

        joinAndCogroup();
        close();
    }

    /**
     * join 和Cogroup：打印学生成绩
     */
    private static void joinAndCogroup() {
        List<Tuple2<Integer,String>> studentList = Arrays.asList(
                new Tuple2<Integer,String>(1,"leo"),
                new Tuple2<Integer,String>(2,"tom"),
                new Tuple2<Integer,String>(3,"marry"),
                new Tuple2<Integer,String>(4,"jack")
        );
        List<Tuple2<Integer,Integer>> scoresList = Arrays.asList(
                new Tuple2<Integer,Integer>(1,80),
                new Tuple2<Integer,Integer>(1,70),
                new Tuple2<Integer,Integer>(2,90),
                new Tuple2<Integer,Integer>(2,80),
                new Tuple2<Integer,Integer>(3,100),
                new Tuple2<Integer,Integer>(3,50),
                new Tuple2<Integer,Integer>(4,60)
        );
        JavaPairRDD<Integer, String> students = sc.parallelizePairs(studentList);
        JavaPairRDD<Integer, Integer> scores = sc.parallelizePairs(scoresList);

        //join 算子关联两个RDD
        /**
         *根据key进行join，并返回JavaPairRDD
         * 但是JavaPairRDD的第一个泛型类型，之前两个JavaPairRDD的key类型是通过key进行join的
         * 第二个泛型，是Tuple2<v1,v2>的类型，Tuple2的两个泛型类型分别为原始RDD的value的类型
         *
         *join 就返回RDD的每一个元素，就是 通过key join之后的一个pair
         * etc：(1,1)(1,2) (1,3)的一个RDD
         * 另一个RDD：(1,4)(2,1) (2,2)
         * join 之后的结果：(1,(1,4)) (1,(2,4))(1,(3,4)) 结果的key是两个RDD的交集
         */
        JavaPairRDD<Integer, Tuple2<String, Integer>> studentScores = students.join(scores);

//        studentScores.foreach(new VoidFunction<Tuple2<Integer, Tuple2<String, Integer>>>() {
//            @Override
//            public void call(Tuple2<Integer, Tuple2<String, Integer>> studentScore) throws Exception {
//                System.out.println("student id :"+studentScore._1);
//                System.out.println("student name:"+studentScore._2._1);
//                System.out.println("student score:"+studentScore._2._2);
//                System.out.println("==================================");
//            }
//        });

        System.out.println("==================cogroup    ==========================");

        /**
         * cogroup: 与join 不同
         * 相当于是，一个key join 上的所有value，都给放到一个Iterable 里面去了
         *
         */

        JavaPairRDD<Integer, Tuple2<Iterable<String>, Iterable<Integer>>> cogroup = students.cogroup(scores);

        cogroup.foreach(new VoidFunction<Tuple2<Integer, Tuple2<Iterable<String>, Iterable<Integer>>>>() {
            @Override
            public void call(Tuple2<Integer, Tuple2<Iterable<String>, Iterable<Integer>>> integerTuple2Tuple2) throws Exception {
                System.out.println("student id :"+integerTuple2Tuple2._1);
                System.out.println("student name:"+integerTuple2Tuple2._2._1);
                System.out.println("student score:"+integerTuple2Tuple2._2._2);
                System.out.println("==================================");
            }
        });

    }

    /**
     * sortByKey 按照学生分数排序
     */
    private static void sortByKey() {
        List<Tuple2<Integer,String>> scoresList = Arrays.asList(
                new Tuple2<Integer,String>(80,"leo"),
                new Tuple2<Integer,String>(90,"tom"),
                new Tuple2<Integer,String>(100,"marry"),
                new Tuple2<Integer,String>(60,"jack")
        );
        JavaPairRDD<Integer,String> scores = sc.parallelizePairs(scoresList);
        /**
         * 根据key 排序，可以指定升序还是降序
         * 返回的还是JavaPairRDD，其中的元素内容，跟原始的RDD一模一样
         * 只是RDD中的顺序不同
         */
//        JavaPairRDD<Integer, String> sortRDD = scores.sortByKey();
        JavaPairRDD<Integer, String> sortRDD = scores.sortByKey(false);

        sortRDD.foreach(new VoidFunction<Tuple2<Integer, String>>() {
            @Override
            public void call(Tuple2<Integer, String> score) throws Exception {
                System.out.println(score._1+":"+score._2);
            }
        });
    }

    /**
     * reduceByKey:统计班级的总分
     */
    private static void reduceBykey() {
        List<Tuple2<String,Integer>> scoresList = Arrays.asList(
                new Tuple2<String,Integer>("class1",80),
                new Tuple2<String,Integer>("class2",90),
                new Tuple2<String,Integer>("class1",100),
                new Tuple2<String,Integer>("class2",60)
        );

        JavaPairRDD<String,Integer> scores = sc.parallelizePairs(scoresList);

        /**
         * Function2,3个泛型参数，
         * 第一个和第二个泛型类型，代表了原始RDD中的元素value的类型
         * 因此对每个key进行reduce，都会一次将第一个，第二个value传入，将值再与第三个value 传入
         *
         * 因此此处，会自动定义两个泛型类型，代表call()方法的两个传入参数的类型
         * 第三个泛型类型，代表了每次reduceByKey操作返回值的类型，默认也是与原始RDD的value类型相同的
         *返回的RDD还是JavaPairRDD<key,value>
         */

        JavaPairRDD<String, Integer> totalScores = scores.reduceByKey(new Function2<Integer, Integer, Integer>() {

            /**
             * 对每个key，都会将其value，依次传入call方法
             * 从而聚合出每个key对应的一个value
             * 然后，每个key对应的一个value，组合成一个Tuple2,作为新的RDD元素
             * @param v1
             * @param v2
             * @return
             * @throws Exception
             */
            @Override
            public Integer call(Integer v1, Integer v2) throws Exception {
                return v1 + v2;
            }
        });

        totalScores.foreach(new VoidFunction<Tuple2<String, Integer>>() {
            @Override
            public void call(Tuple2<String, Integer> tuple2) throws Exception {
                System.out.println(tuple2._1+":"+tuple2._2);
            }
        });

    }


    /**
     * groupByKey : 每个班级的成绩进行分组
     *
     *
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
