/**
 * A class for timing code.
 *
 * @author  Terry Sergeant
 *
*/

public class Timer
{
  private long starttime,stoptime;

  public Timer() { reset(); }
  public void reset() { starttime= stoptime= 0; }
  public void start() { starttime= System.currentTimeMillis(); }
  public void stop() { stoptime= System.currentTimeMillis(); }
  public long milliseconds()
  {
    if (stoptime < starttime) return 0;
    return stoptime-starttime;
  }
  public double seconds() { return milliseconds() / 1000.0; }
  public String toString()
  {
    if (starttime==0) return "timer not started";
    if (stoptime==0) stop();
    if (stoptime >= starttime)
      return String.format("%1.3f",(stoptime-starttime)/1000.0);
    else
      return "invalid timer setting";  // should never happen
  }
}

