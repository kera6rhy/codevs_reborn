import java.util.*;
import java.util.stream.Collectors;

public class Main {
    static final int width = 10;
    static final int height = 16;
    static final int packSize = 2;
    static final int summation = 10;
    static final int maxTurn = 500;
    static int simulationHeight = height + packSize + 8;
    static final int EMPTY_BLOCK = 0;
    static int OBSTACLE_BLOCK = summation + 1;
    static final String AI_NAME = "kera";
    Scanner in = new Scanner(System.in);

    public static void main(String[] args)
    {
        new Main().run();
    }



    //int turn;


    int enemyMillitime;
    static int enemyObstacleCount;
    int enemySkill;
    int enemyScore;
    int[][] enemyField ;

    Search search = new Search();



    void init(){
        for(int i=0; i<search.betterCommand.length;i++)
            for(int j=0;j<search.betterCommand[i].length;j++) {
                search.betterCommand[i][j] = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
                search.fireCommand[i][j] = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
            }

        System.out.println(AI_NAME);
        System.out.flush();
        for (int i = 0; i < maxTurn; i++) {
            search.packs[i] = inputPack(in);
        }
    }

    void loopInput(){
        // 1ターン分のデータを受け取る
        search.turn = search.turn = in.nextInt();

        search.millitime = in.nextInt();
        search.obstacleCount = in.nextInt();
        search.skill = in.nextInt();
        search.score = in.nextInt();
        search.field = inputField(in);
//        search.field = fallObstacle(search.field, search.obstacleCount);

        enemyMillitime = in.nextInt();
        enemyObstacleCount = in.nextInt();
        enemySkill = in.nextInt();
        enemyScore = in.nextInt();
        enemyField = inputField(in);
        enemyField = fallObstacle(enemyField, enemyObstacleCount);
    }










    void run() {
        // ターンの処理
        init();
        while (true) {
            int oldOjama = search.obstacleCount;
            loopInput();
            search.aite(enemyField);
//            if(search.turn>=20){
//
//                System.out.println("aa");
//                System.out.flush();
//            }
            if(search.millitime < 30000){
                search.BEAM_DEPTH = 15;
                search.BEAM_WIDTH = 100;
                search.EX_DEPTH = 1;
            }
            if(search.turn >=35)search.FIRE_SIZE = 9;
            if(oldOjama/10 < search.obstacleCount/10){
                search.justAttacked = true;
                Util.debugln("Attacked");
            }
//            if(search.turn==20)search.ryusuigansaiken = false;
            if(enemyScore-search.score >=3 && search.turn <= 8 ||  enemySkill-search.skill>=40 && search.turn <= 30){
                if(search.justBomber==0) {
                    search.bomber = true;
                    search.RATE = search.BOMBER_RATE;
//                search.FIRE_SIZE = 13;
//                    search.EX_DEPTH = 4;
                    search.fireYotei = search.turn + search.BEAM_DEPTH;
                    if (search.turn >= 10) search.fireYotei = search.turn + 10;
//                search.BEAM_DEPTH = 15;
                    search.justBomber = 1;
                }
            }

            Util.debugln("turn:"+search.turn+" bomber:"+search.bomber+" score:"+search.score);

            String out = search.output();
            search.justAttacked = false;

//            if(turn==0)in.nextLine();in.nextLine();
            // 操作を出力する
            System.out.println(out);
            System.out.flush();
            if(search.justBomber==1) search.justBomber =2;
        }

    }


    // 標準入力からパックを得ます
    int[][] inputPack(Scanner in) {
        int[][] pack = new int[packSize][packSize];
        for (int i = 0; i < packSize; i++) {
            for (int j = 0; j < packSize; j++) {
                pack[i][j] = in.nextInt();
            }
        }
        in.next(); // END
        return pack;
    }



    // 標準入力から盤面を得ます
    int[][] inputField(Scanner in) {
        int[][] field = new int[simulationHeight][width];

        for (int i = 0; i < simulationHeight - height; i++) {
            for (int j = 0; j < width; j++) {
                field[i][j] = EMPTY_BLOCK;
            }
        }
        for (int i = simulationHeight - height; i < simulationHeight; i++) {
            for (int j = 0; j < width; j++) {
                field[i][j] = in.nextInt();
            }
        }
        in.next(); // END
        return field;
    }

    // お邪魔カウントに応じて、盤面にお邪魔ブロックを落とします
    int[][] fallObstacle(int[][] field, int obstacleCount) {
        int[][] after = Arrays.stream(field)
                .map(row -> Arrays.copyOf(row, width))
                .toArray(int[][]::new);
        if (obstacleCount < width) return after;
        for (int j = 0; j < width; j++) {
            for (int i = simulationHeight - 1; i >= 0; i--) {
                if (field[i][j] == EMPTY_BLOCK) {
                    field[i][j] = OBSTACLE_BLOCK;
                    break;
                }
            }
        }
        return after;
    }


}
