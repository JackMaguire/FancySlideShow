package benchmarking;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class TimerTest {

	public static void main( String[] args ) throws InterruptedException {
		final int delay = 100;

		final Timer timer = new Timer( delay, new Thing1() );
		timer.start();
		Thread.sleep( 1000 );
		timer.stop();
		
		System.out.println( "---" );
		
		final Timer timer2 = new Timer( delay, new Thing2() );
		timer2.start();
		Thread.sleep( 1000 );
		timer2.stop();
	}
	
	private final static class Thing1 implements ActionListener {

		private static long previous_ = System.currentTimeMillis();
		
		public void actionPerformed( ActionEvent e ) {
			try {
				Thread.sleep( 200 );
			}
			catch( InterruptedException e1 ) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println( System.currentTimeMillis() - previous_ );
			previous_ = System.currentTimeMillis();
		}
		
	}
	
	private final static class Thing2 extends Thread implements ActionListener {

		private static long previous_ = System.currentTimeMillis();
		
		public void run() {
			try {
				Thread.sleep( 200 );
			}
			catch( InterruptedException e1 ) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println( System.currentTimeMillis() - previous_ );
			previous_ = System.currentTimeMillis();
		}
		
		public void actionPerformed( ActionEvent e ) {
			Thread T = new Thing2();
			T.start();
			//start();
		}
		
	}

}
