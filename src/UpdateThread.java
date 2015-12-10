import java.util.ArrayList;

/**
 * Created by Sandy on 2015/12/5.
 */
public class UpdateThread implements Runnable{
    int[][] map;
    ArrayList<Character> characterList;

    public UpdateThread(int[][] map, ArrayList<Character> characterList) {
        this.map = map;
        this.characterList = characterList;
    }

    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(500);

                for(int i=0; i<4; i++){
                    Character character = characterList.get(i);//index = 0 or 1
                    int[][] movement = {{0, -1}, {1, 0}, {0, 1}, {-1, 0}};
                    character.x = (int) (character.x + movement[character.dir][0]*character.velocity*0.5);
                    character.y = (int) (character.y + movement[character.dir][1]*character.velocity*0.5);
                    character.updated = true;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
