package mode;

public class Math {
	
	
	/**
	 * 求向量的数量积
	 * @param vector1
	 * @param vector2
	 */
	public static double multiplyVector(double[] vector1, double[] vector2)
	{
		double result = 0;
		if(vector1.length == vector2.length){
			for (int i = 0; i < vector1.length; i++) {
				result += vector1[i]*vector2[i];
			}
		}
		return result;
	}
	
	/**
	 * 分段函数，把正数、零、负数分别映射成1、0、-1
	 * @param parameter
	 * @return
	 */
	public static int sgn(double parameter){
		if(parameter > 0){
			return 1;
		}
		if(parameter < 0){
			return -1;
		}
		return 0;
	}
	
	/**
	 * 返回参数和0较大的数
	 * @param parameter
	 * @return
	 */
	public static double max(double parameter){
		if(parameter > 0)
			return parameter;
		return 0;
			
	}
	
	/**
	 * 求数组形式的向量的二范数
	 * @param vector
	 * @return
	 */
	public static double norm2(double[] vector){
		double sum = 0;
		for (int i = 0; i < vector.length; i++) {
			sum += java.lang.Math.pow(vector[i], 2);
		}
		
		return java.lang.Math.sqrt(sum);
	}
	
	
	

}
