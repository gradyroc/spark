package cn.grady.spark.core.initrdd;

/**
 * @author rociss
 * @version 1.0, on 23:28 2022/7/28.
 */

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;

import java.util.Arrays;
import java.util.List;

/**
 * 并行化集合创建RDD
 */
public class ParallelizeCollection {

    public static void main(String[] args) {

        SparkConf conf = new SparkConf()
                .setMaster("local")
                .setAppName("ParallelizeCollection");

        JavaSparkContext sc = new JavaSparkContext(conf);

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        //要通过并行化集合的方式创建RDD，就要调用SparkContext 及其子类的parallelize() 方法
        JavaRDD<Integer> numberRDD = sc.parallelize(numbers,5);

        // reduce 算子
        //=》 1+2=3；then 3+3=6；then 6+4=10.。。。以此类推
        Integer result = numberRDD.reduce(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer v1, Integer v2) throws Exception {
                return v1 + v2;
            }
        });

        System.out.println(result);

        //关闭JavaSparkContext
        sc.close();
    }
}
