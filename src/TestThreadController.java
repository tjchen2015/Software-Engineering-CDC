/**
 * Created by Sandy on 2015/12/12.
 */
public class TestThreadController implements ThreadController {
    public boolean flag;

    @Override
    public void blockThread() {
        while (flag){
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {

            }
        }
    }
}
