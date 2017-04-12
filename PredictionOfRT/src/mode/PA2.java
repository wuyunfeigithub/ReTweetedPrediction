package mode;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import data.readFile;

public class PA2 {

	private double PARAMETER = 0.01;

	// private double[] weight = null;
	private double[][] X_vectors = null;
	private int[] Ys = null;
	private double[] maxNum; 
	// private boolean readable = false;

	BufferedReader reader = null;
	String path = "twitterData\\Result_Train.txt";
	String path1 = "twitterData\\MaxRecorder.txt";
	int rowNum = 0;

	int count = 0;
	int count_RT = 0;
	int count_noRT = 0;

	public PA2() {
		Recorder.weight = new double[Recorder.DIMENSION];
		X_vectors = new double[Recorder.MAX_ROWS][Recorder.DIMENSION];
		Ys = new int[Recorder.MAX_ROWS];
//		this.intializeWeight();
		
		maxNum = new double[10];
		BufferedReader reader_Max = null;
		try {
			reader_Max = readFile.createStream(path1);
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
//		for (int i = 0; i < maxNum.length; i++) {
//			System.out.println(maxNum[i]);
//		}
	}

	public double[] updateWeight() {
		// 循环学习更新权重
		this.intializeWeight();
		this.setPara_c(0.0107);

		// 批量加载数据
		while (this.hasData(path)) {
			for (int i = 0; i < X_vectors.length && (rowNum--) > 0; i++) {

				// int tmp_y = predict(weight, X_vectors[i]);//此行代码未起作用
				double tmp_loss = getSufferLoss(Recorder.weight, X_vectors[i], Ys[i]);
				double tmp_t = tmp_loss / (Math.norm2(X_vectors[i]) + 1 / (2 * PARAMETER));

				for (int j = 0; j < Recorder.weight.length; j++) {
					Recorder.weight[j] += tmp_t * Ys[i] * X_vectors[i][j];
				}
			}
		}

		System.out.println("******权重数据******");
		for (int i = 0; i < Recorder.weight.length; i++) {
			System.out.println("weight：\t"+Recorder.weight[i]);
		}
		System.out.println("******训练相关数据******");
		System.out.println("总数：\t\t"+count);
		System.out.println("转推总数：\t"+count_RT);
		System.out.println("未转推总数：\t"+count_noRT);
		
		
		return getWeight();
	}

	// private void receiveInstance(){
	//
	// }

	private int predict(double[] w_t, double[] x_t) {
		return Math.sgn(Math.multiplyVector(w_t, x_t));
	}

	// private void getCorrectLabel(){
	//
	// }

	private double getSufferLoss(double[] w_t, double[] x_t, int y_t) {
		return Math.max(1 - y_t * Math.multiplyVector(w_t, x_t));
	}

	private void intializeWeight() {
		for (int i = 0; i < Recorder.weight.length; i++) {
			Recorder.weight[i] = 0;
		}
	}

	private boolean hasData(String path) {
		// 注意记录加载数据记录，有数据加载true，无数据false
		boolean hasData = false;
		String data = "";
		this.rowNum = 0;
		int i = 0;
		if (reader == null)
			reader = readFile.createStream(path);
		try {
			while (i < Recorder.MAX_ROWS && ((data = reader.readLine()) != null)) {
				this.stringToArray(data, i);
				hasData = true;
				i++;
				rowNum++;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// if(hasData==false)
		// try {
		// this.reader.close();
		// } catch (Exception e) {
		// // TODO: handle exception
		// e.printStackTrace();
		// }
		return hasData;
	}

	private void stringToArray(String data, int index) {

		String[] tmp = data.split("\t");
		for (int i = 0; i < tmp.length - 1; i++) {
			X_vectors[index][i] = Double.parseDouble(tmp[i])/ maxNum[i];
		}
		Ys[index] = Integer.parseInt(tmp[Recorder.DIMENSION]);

		count++;
		if(Ys[index] == 1){
			count_RT++; 
		}
		else{
			count_noRT++;
		}
	}

	public double[] getWeight() {
		return Recorder.weight;
	}

	public void setWeight(double[] weight) {
		Recorder.weight = weight;
	}

	public void setPara_c(double para) {
		this.PARAMETER = para;
	}

}
