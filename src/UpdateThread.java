import java.util.ArrayList;

/**
 * Created by Sandy on 2015/12/5.
 */
public class UpdateThread implements Runnable{
    int[][] map;
    ArrayList<Character> characterList;
    ThreadController threadController;

    public void setThreadController(ThreadController threadController){
        this.threadController = threadController;
    }

    public UpdateThread(int[][] map, ArrayList<Character> characterList) {
        this.map = map;
        this.characterList = characterList;
    }

    @Override
    public void run() {
        while (true){
            threadController.blockThread();

            for(int i=0; i<4; i++){
                Character character = characterList.get(i);//index = 0 or 1
                int[][] movement = {{0, -1}, {1, 0}, {0, 1}, {-1, 0}};
                character.x = (int) (character.x + movement[character.dir][0]*character.velocity*0.5);
                character.y = (int) (character.y + movement[character.dir][1]*character.velocity*0.5);
                character.updated = true;
            }
        }
    }
}
