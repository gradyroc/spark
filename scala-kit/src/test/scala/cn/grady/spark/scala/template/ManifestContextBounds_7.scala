package cn.grady.spark.scala.template

/**
 * @author rociss 
 * @version 1.0, on 1:28 2022/7/6.
 */
object ManifestContextBounds_7 {
  /**
   * scala 中，如果要实例化一个泛型数组，必须使用 Manifest Context Bounds
   * 如果数组元素类型为T的话，需要为类或者函数定义 [T:Manifest] 泛型类型，才能实例化Array[T]这种泛型数组
   *
   * etc:打包饭菜
   */

  class Meat(val name: String)

  class Vegetable(val name: String)

  // 变长参数 T*
  def packageFood[T: Manifest](foods: T*) = {
    val foodPackage = new Array[T](foods.length)
    for (i <- 0 until foods.length)
      foodPackage(i) = foods(i)

    foodPackage
  }

  def main(args: Array[String]): Unit = {
    val gongbaojiding = new Meat("gongbaojiding")
    val shousiyangpai = new Meat("shousiyangpai")
    val yuxiangrousi = new Vegetable("yuxiangrousi")

    val packageMeat = packageFood(gongbaojiding, shousiyangpai, yuxiangrousi)
    packageMeat.foreach(println(_))

    //指定类型时 编译报错：
    //    val packageMeat1 = packageFood[Meat](gongbaojiding,shousiyangpai,yuxiangrousi)
  }

}
