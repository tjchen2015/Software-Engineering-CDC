/**
 * Created by Sandy on 2015/12/12.
 */
public class GameThreadController implements ThreadController {
    @Override
    public void blockThread() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {

        }
    }
}
