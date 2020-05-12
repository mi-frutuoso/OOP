package metricsPackage;

public class Accuracy<T> implements Metrics<T> {
	
	private T[] trueC;
	private T[] res;
	//private T[] predC;
	private float acc;
	
	
	public Accuracy(T[] trueC, T[] res){
		this.trueC = trueC;
		this.res = res;
	}

	@Override
	public void evaluate() {
		// TODO Auto-generated method stub
		if(trueC.length != res.length) {
			System.out.println("Accuracy error: Predicted and test classes' array not consistent. Exiting...");
			System.exit(1);
		}
		int Ncorrect = 0;
		for(int i = 0; i < trueC.length; i++) {
			if(trueC[i].equals(res[i])) Ncorrect++;
		}
		
		acc = (float)Ncorrect/(trueC.length);
	}

	@Override
	public String toString() {
		return String.format("%.2f", acc);
	}
	
	

}
