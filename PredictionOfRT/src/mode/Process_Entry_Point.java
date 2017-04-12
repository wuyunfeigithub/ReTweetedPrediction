package mode;

import data.JsonHelper;

public class Process_Entry_Point {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

//		new JsonHelper().loadUersAndTextProperty();
		Recorder recorder = new Recorder();
		PA2 p = new PA2();
		p.updateWeight();
		Predictor predictor = new Predictor();
		predictor.predict();
	}

}
