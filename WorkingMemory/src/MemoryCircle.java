import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class MemoryCircle extends JLabel{
			
			int x, y, r, Xc, Yc,sR;
			Color current;
			StopWatch sw;
			float activity;
			boolean active;
			protected Timer timer = new Timer();
			protected int decayTime;
			float decayRate = .8f;
			
			public MemoryCircle(int x,  int y, int r, int sensitivity) {
				this.x = x;
				this.y = y;
				this.r = r;
				this.sR = this.r + sensitivity;
				this.active = true;
				Xc = x + r/2;
				Yc = y + r/2;
				current = Color.black;
				sw = new StopWatch();
				this.decayTime = 3;
				timer.schedule(new MemoryCirlceUpdateTask(this),  0, 250);
			}
			
		    private void doDrawing(Graphics g) {

		        Graphics2D g2d = (Graphics2D) g;

		        g2d.setColor(current);

		        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
		                RenderingHints.VALUE_ANTIALIAS_ON);

		        rh.put(RenderingHints.KEY_RENDERING,
		               RenderingHints.VALUE_RENDER_QUALITY);

		        g2d.setRenderingHints(rh);
		        g2d.fillOval(0, 0, r, r);   
		   } 

		    @Override
		    public void paintComponent(Graphics g) {
		        
		        super.paintComponent(g);
		        doDrawing(g);
		    }

		    public void mouseMoved(MouseEvent e){
		    	
		    }

		    public void prime(float amount){
				float hsbVals[] = Color.RGBtoHSB(current.getRed(), current.getGreen(), current.getBlue(), null);
				current = Color.getHSBColor(hsbVals[0], hsbVals[1], amount + hsbVals[2]);
		    }

		     public void log(Object o){
		    	 System.out.println(o.toString());
		     }
		     
		    class MemoryCirlceUpdateTask extends TimerTask {
		    	
		    	MemoryCircle m;
		    	public MemoryCirlceUpdateTask(MemoryCircle m) {
					this.m = m;
				}

				@Override
				public void run() {
					EventQueue.invokeLater(new Runnable(){
						public void run() {
							if(!active){
								float hsbVals[] = Color.RGBtoHSB(current.getRed(), current.getGreen(), current.getBlue(), null);
								if(hsbVals[2] > 0.05){
									hsbVals[2] = (hsbVals[2] < .7) ? (float) (hsbVals[2] * hsbVals[2]) : (float) (.8f * hsbVals[2]);
									current = Color.getHSBColor(hsbVals[0], hsbVals[1], hsbVals[2]);
								}
								else {
									sw.stop();
									current = new Color(82,4,199);
								}
								m.repaint();
							}
						}
					});
					
				}
		    	
		    }
		}
