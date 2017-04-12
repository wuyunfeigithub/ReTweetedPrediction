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
 * ��������
 * 1.����Ԥ��ת��
 * 2.ͳ������
 * 3.ͳ��Ԥ����ȷ������
 * 4.��ȷ��
 */
public class Predictor {

	private double[][] X_vectors = null;
	private int[] Ys = null;
	private double[] maxNum; 
	
	BufferedReader reader = null;
	String path = "twitterData\\datasets_predict.txt";
	String pathOfMaxRecorder = "twitterData\\MaxRecorder.txt";
	int rowNum = 0;
	
	

	//����������
	int count = 0;
	//Ԥ����ȷ������
	int count_correct = 0;
	
	//ת����������
	int count_RT = 0;
	//ת��������Ԥ����ȷ������
	int count_correct_RT =0;
	
	//δת����������
	int count_noRT = 0;
	//δת��������Ԥ����ȷ������
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
		//������������
		while(this.hasData(path)){
			//��ʾ��������x��y
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
		System.out.println("******Ԥ���������******");
		System.out.println("������\t\t"+count);
		System.out.println("Ԥ��ɹ��ʣ�\t"+((double)count_correct)/count);
		System.out.println("ת��������\t"+count_RT);
		System.out.println("ת��Ԥ��ɹ��ʣ�\t"+((double)count_correct_RT)/count_RT);
		System.out.println("δת��������\t"+count_noRT);
		System.out.println("δת��Ԥ��ɹ��ʣ�\t"+((double)count_correct_noRT)/count_noRT);
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
		//ע���¼�������ݼ�¼�������ݼ���true��������false
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
