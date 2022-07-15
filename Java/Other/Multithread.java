public class Multithread extends Thread {
	private int threadnumber;
	protected Multithread(int threadnumber){
		this.threadnumber = threadnumber;
	}
	@Override
	public void run(){
		for(int i = 1; i <= 5; i++ ){
			System.out.println(i + " from Thread number " + threadnumber);
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
			}
		}
	}
}
