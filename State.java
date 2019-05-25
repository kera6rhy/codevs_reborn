import java.util.ArrayList;
import java.util.List;

public class State {
    List<Integer> diffList = new ArrayList<>();
    int turn = 0;
    double score = 0;
    int firstCommand;
    int fire;
    int ojama;
    int rensa;
    int poteChain;
    boolean death;
    int fireX;
    int fireN;
    String command = "";
    int potePoint[] = {-1,-1};
    int field[][];
    String key;
    double rand;
    double pattern;
    int height;
    int fireHeight;
    //int field[][] = new int[width][simulationHeight];
    State(State s){
        turn = s.turn+1;
        firstCommand = s.firstCommand;
        command = s.command;
        ojama = s.ojama;
        potePoint[0] = s.potePoint[0];
        potePoint[1] = s.potePoint[1];
        field = Util.genCopyField(s.field);
        height = s.height;

    }
    State(){}
    void update(State now ,int x, int r){
        command = now.command + x + "" + r;
        turn = now.turn+1;
        firstCommand = now.firstCommand;

    }

}
