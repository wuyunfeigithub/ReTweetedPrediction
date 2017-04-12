package mode;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		double[] x = new double[12];
		int y = -1;
		
		String data = "3807 3236 11411 55 299 1 4 0 1 1 15 0";
		
		String[] tmp = data.split(" ");
		for (int i = 0; i < tmp.length; i++) {
			x[i] = Double.parseDouble(tmp[i]);
		}
		y = Integer.parseInt(tmp[Recorder.DIMENSION]);
		
		for (int i = 0; i < x.length; i++) {
			System.out.print(tmp[i]+"\t");
		}
		System.out.print(y);
	}

}
