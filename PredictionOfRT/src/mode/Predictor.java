package mode;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import data.readFile;

/**
 * 
 * @author NICE
 * 期望功能
 * 1.批量预测转推
 * 2.统计总数
 * 3.统计预测正确的数量
 * 4.正确率
 */
public class Predictor {

	private double[][] X_vectors = null;
	private int[] Ys = null;
	private double[] maxNum; 
	
	BufferedReader reader = null;
	String path = "twitterData\\datasets_predict.txt";
	String pathOfMaxRecorder = "twitterData\\MaxRecorder.txt";
	int rowNum = 0;
	
	

	//总推文数量
	int count = 0;
	//预测正确的数量
	int count_correct = 0;
	
	//转发的推文数
	int count_RT = 0;
	//转发的推文预测正确的数量
	int count_correct_RT =0;
	
	//未转发的推文数
	int count_noRT = 0;
	//未转发的推文预测正确的数量
	int count_correct_noRT = 0;
	
	public Predictor(){
		X_vectors = new double[Recorder.MAX_ROWS][Recorder.DIMENSION];	
		Ys = new int[Recorder.MAX_ROWS];

		maxNum = new double[10];
		BufferedReader reader_Max = null;
		try {
			reader_Max = readFile.createStream(pathOfMaxRecorder);
			String tempString = null;
			int i = 0;
			while ((tempString = reader_Max.readLine()) != null) {
				maxNum[i++] = Double.parseDouble(tempString);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			try {
				if (reader_Max != null) {
					reader_Max.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
//		Recorder.weight = new PA2().getWeight();
		
//		for (int i = 0; i < Recorder.weight.length; i++) {
//			System.out.println(Recorder.weight[i]);
//		}
	}
	
	public void predict(){
		//批量加载数据
		while(this.hasData(path)){
			//显示输入向量x和y
//			for (int i = 0; i < rowNum; i++) {
//				for (int j = 0; j < X_vectors[i].length; j++) {
//					System.out.print(X_vectors[i][j]+"\t");
//				}
//				System.out.println(Ys[i]);
//			}
//			
			
//			System.out.println(weight.length+"   "+X_vectors.length);
			
			for (int i = 0; i < X_vectors.length  && (rowNum--)>0; i++) {
//				System.out.println(this.predictMethod(Recorder.weight, X_vectors[i]));
				if (this.predictMethod(Recorder.weight, X_vectors[i]) == Ys[i]) {
//				if (this.ramdomPredictMethod() == Ys[i]){
					count_correct++;	
//					System.out.println(count_correct);
					if(Ys[i] == 1){
						count_correct_RT++; 
					}
					else{
						count_correct_noRT++;
					}
				}
				count++;
				if(Ys[i] == 1){
					count_RT++; 
				}
				else{
					count_noRT++;
				}
//				System.out.println(count);
			}
		}
		System.out.println("******预测相关数据******");
		System.out.println("总数：\t\t"+count);
		System.out.println("预测成功率：\t"+((double)count_correct)/count);
		System.out.println("转推总数：\t"+count_RT);
		System.out.println("转推预测成功率：\t"+((double)count_correct_RT)/count_RT);
		System.out.println("未转推总数：\t"+count_noRT);
		System.out.println("未转推预测成功率：\t"+((double)count_correct_noRT)/count_noRT);
	}
	
	private int ramdomPredictMethod(){
		if(((int)java.lang.Math.random()) > 0.5){
			return 1;
		}
		return -1;
	}
	
	private int predictMethod(double[] w_t, double[] x_t){
//		System.out.println(Math.sgn(Math.multiplyVector(w_t, x_t)));
		return Math.sgn(Math.multiplyVector(w_t, x_t));		
	}
	
	private boolean hasData(String path){
		//注意记录加载数据记录，有数据加载true，无数据false
		boolean hasData = false;
		String data = "";
		this.rowNum = 0;
		int i = 0;
		if(reader==null)
			reader = readFile.createStream(path);
		try {
			while (i<Recorder.MAX_ROWS && (data = reader.readLine()) != null) {
				this.stringToArray(data, i);
				hasData = true;
				i++;
				rowNum++;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		if(hasData==false)
//			try {
//				this.reader.close();
//			} catch (Exception e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
		return hasData;
	}
	
	private void stringToArray(String data, int index){
	
		String[] tmp = data.split("\t");
		for (int i = 0; i < tmp.length-1; i++) {
			X_vectors[index][i] = Double.parseDouble(tmp[i])/maxNum[i];
		}
		Ys[index] = Integer.parseInt(tmp[Recorder.DIMENSION]);
	}
}
