import java.util.*;

public class Search {
    final int width = Main.width;
    final int simulationHeight = Main.simulationHeight;
    int AITE_DEPTH = 3;
    static int BEAM_DEPTH = 15;
    int BEAM_WIDTH = 600;
    int MIN_FIRE_SIZE = 11;
    int FIRE_SIZE = MIN_FIRE_SIZE;
    int EX_DEPTH = 3;
    double RATE = 1.04;
    double BOMBER_RATE = 1.04;

    static Random rand = new Random(0);


    int[][][] packs = new int[Main.maxTurn][][];
    static int turn;
    static  int millitime, obstacleCount,skill, score;
    int[][] field;

    Diff diff = new Diff();
    Func func = new Func(width, simulationHeight);;
    Jiko jiko = new Jiko();

    String betterCommand[][] = new String[2][BEAM_DEPTH+4];
    String fireCommand[][] = new String[2][BEAM_DEPTH+4];
    List<State> fireState = new ArrayList<>();
    Map<String, State> oldMap = new HashMap<>();
    Map<String, State> newMap = new HashMap<>();
    static boolean justAttacked = false;
    static boolean justAttack = false;
    String oldOutCommand = "aaa";
    static boolean bomber;
    int justBomber;
    int lastFireTurn = 0;
    boolean pinch;
    int fireYotei = BEAM_DEPTH+EX_DEPTH;
    boolean yoyu;
    static int counterCount;
    static int mostNum;
    static List<Integer> mostNumList = new ArrayList<>();

    State maxScoreState;
    double maxMSS;
    int aiteMaxRensaTurn;


    String sas[]={
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaa",
            "aaaaaaaaaaaaaaaaaaaaaaaaaa",
            "aaaaaaaaaaaaaaaaaaaaaaaa",
            "aaaaaaaaaaaaaaaaaaaaaa",
            "aaaaaaaaaaaaaaaaaaaa",
            "aaaaaaaaaaaaaaaaaa",
            "aaaaaaaaaaaaaaaa",
            "aaaaaaaaaaaaaa",
            "aaaaaaaaaaaa",
            "aaaaaaaaaa",
            "aaaaaaaa",
            "aaaaaa",
            "aaaa",
            "aa",
    };

    Set<Integer> jikoClear = new HashSet<>();
    public static void main(String args[]){
        State s1 = new State();
        State s2 = new State();
        Search s = new Search();

//        bomber = true;
        if(bomber)s.RATE = s.BOMBER_RATE;
        s1.rensa =14;s1.turn=15;s.calcScore(s1);
//        turn++;
        s2.rensa = 14;s2.turn=14;s.calcScore(s2);
        Util.debugln(""+s1.score);
        Util.debugln(""+s2.score);

    }

    double yabaiScale;
    void calcScore(State next){
        if (next.rensa >= 3 || pinch) {

            double tmp = RATE;
//            if(bomber && next.rensa>=15){
//                tmp = 1.3;
//            }
            if (counterCount > 0) tmp = 1.01;
            next.score = next.rensa * Util.njo(tmp, BEAM_DEPTH - next.turn) * 10;
            if(next.rensa == 10)next.score *= 0.8;

            double scale = 0.97;
            double scale2 = 0.95;
//            if (bomber) {
//                scale = 1;
//                scale2 = 0.97;
//                if (next.rensa <= 13) next.score *= 0.9;
//            }
            int passTurn = turn + next.turn - lastFireTurn;

//            if(bomber)scale = 0.8;
            if(counterCount<=0) {
                if (passTurn >= 11) next.score *= scale;
                if (passTurn >= 12) next.score *= scale;
                if (passTurn >= 13) next.score *= scale;
                if (passTurn >= 14) next.score *= scale;
                if (passTurn >= 15) next.score *= scale;
                if (passTurn >= 16) next.score *= scale2;
                    if (passTurn >= 17) next.score *= scale2;
                    if (passTurn >= 18) next.score *= scale2;
                    if (passTurn >= 19) next.score *= scale2;
                    if (passTurn >= 20) next.score *= scale2;
            }
            else{
//                if (passTurn >= 8) next.score *= scale2;
                if (passTurn >= 1) next.score *= 1.008;
                if (passTurn >= 2) next.score *= 1.008;
                if (passTurn >= 3) next.score *= 1.008;
                if (passTurn >= 4) next.score *= 1.008;
                if (passTurn >= 5) next.score *= 1.008;
                if (passTurn >= 6) next.score *= 1.008;
                if (passTurn >= 7) next.score *= 1.008;
                if (passTurn >= 8) next.score *= 1.008;
                if (passTurn >= 9) next.score *= 0.93;
                if (passTurn >= 10) next.score *= 0.93;
                if (passTurn >= 11) next.score *= 0.93;
                if (passTurn >= 12) next.score *= 0.93;
                if (passTurn >= 13) next.score *= 0.93;
                if (passTurn >= 14) next.score *= 0.93;
                if (passTurn >= 15) next.score *= 0.93;
                if (passTurn >= 16) next.score *= 0.93;
                if(next.turn>=8) {
                    next.score*=0.92;
                    if (next.rensa >= 19) next.score *= 0.95;
                    if (next.rensa >= 20) next.score *= 0.95;
                    if (next.rensa >= 21) next.score *= 0.95;
                    if (next.rensa >= 22) next.score *= 0.95;
                    if (next.rensa >= 23) next.score *= 0.95;
                    if (next.rensa >= 24) next.score *= 0.95;
                    if (next.rensa >= 25) next.score *= 0.95;
                    if (next.rensa >= 26) next.score *= 0.95;
                    if (next.rensa >= 27) next.score *= 0.95;
                    if (next.rensa >= 28) next.score *= 0.95;
                    if (next.rensa >= 29) next.score *= 0.95;
                }
            }

            next.fire = 1;
            if (next.rensa < FIRE_SIZE) {
                next.score = next.rensa*0.3;
            }
        } else {
            next.score = next.poteChain *0.1;//- next.rensa;
            if(counterCount<=0 && ryusuigansaiken)next.score *= 1+1*(next.potePoint[1]+1);

            next.score += next.pattern*0.003;
            if(ryusuigansaiken && counterCount<=0 && next.height >= 10)next.score -= (next.height-9)*2.5;
        }
        next.score += next.rand;

    }

    Map<String, Integer> hashMap = new HashMap<>();

    ArrayList<State> calcNext(State now,  int nowDepth) {
        ArrayList<State> ret = new ArrayList<>();

        for (int r = 0; r < 4; r++) {
            int fallPack[][] = Util.genRotatePack(packs[turn + now.turn], r);//信じられる

            for (int x = 0; x < 9; x++) {
                State next = new State(now);
                allNum++;
                next.command += x + "" + r;
                boolean recall = false;
                String aaa = "";
//                if(next.turn!=15)aaa += sas[next.turn-1];
                if( oldMap.containsKey(oldOutCommand + next.command) && justAttack==false &&justAttacked==false && counterCount<0){
                    recall = true;
                    recallNum++;
                    next = oldMap.get(oldOutCommand +next.command);
                    next.update(now,x,r);
                    next.rand = (10*rand.nextDouble()) * 0.001;
                    calcScore(next);
                    if(hashMap.containsKey(next.key) && next.fire==0){
                        hashNum++;
                        continue;
                    }
                    else if(next.fire==0){
                        hashMap.put(next.key, 1);
                    }
                }
                else {

                    if (next.ojama >= 10) {
                        func.fallOjama(next.field);
                        next.ojama -= 10;
                    }
                    int firePoint[] = new int[width];
                    int fpf = func.fallPackFire(next.field, fallPack, x, firePoint,0);
                    next.fireHeight = fpf;
                    if (fpf >= 0) {
                        next.rensa = func.rensa(next.field, firePoint);
                    }

                    next.death = func.isDeath(next.field);
                    next.key = Util.calcKey(next.field);

                    if (hashMap.containsKey(next.key) && next.fire==0){
                        hashNum++;
                        continue;
                    } else if(next.fire==0){
                        hashMap.put(next.key, 1);
                    }

                    if(!next.death) {
                        boolean alpote = false;
                        if (next.potePoint[0] != -1) alpote = true;
                        int tmp = 0;
                        tmp = turn+next.turn-lastFireTurn;
                        if(next.fire==0 )next.poteChain = func.potentialChain(next.field, next.potePoint, tmp, next.turn);
                        next.pattern = func.pattern(next.field);
                        next.rand = (10*rand.nextDouble()) * 0.001;
                        next.height = Util.calcHight(next.field);

                        if (next.poteChain <= 8 && alpote == false) {
                            next.potePoint[0] = -1;
                        }
                        calcScore(next);

                    }
                }

                String key = next.command;
                if(!next.death) {
                    if(next.fire == 0 && maxScore < next.score){
                        max = next;
                        maxScore = next.score;
                    }
                    if (now.turn == 0) next.firstCommand = x * 10 + r;
                    if (next.turn < BEAM_DEPTH && fireYotei != nowDepth + turn + 1 && next.fire == 0 && !justAttacked && !justAttack) {
                        for (int i = 0; i < betterCommand[0].length; i++) {

                            int id = turn % 2;
                            if (betterCommand[id][i].substring(2, next.turn * 2 + 2).equals(next.command)) {
                                next.score += 10000;
                            }
                            if (fireCommand[id][i].substring(2, next.turn * 2 + 2).equals(next.command)) {
                                next.score += 10000;
                            }
                        }
                    }

                    if (next.fire == 1) {
                        fireNum++;
                        if (recall == false) {
                            next.command += sas[next.turn-1];//"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
                            next.fireX = x;
                            next.fireN = Func.maxn;
                        }

                            if (//maxRensa <= next.rensa &&
                                    maxRensaScore < next.score){
                            maxRensa = next.rensa;
                            maxRensaScore = next.score;
                            maxRensaBias = next.fireHeight;
                            maxRensaState = next;
                        }
                        fireState.add(next);
                    } else {
                        ret.add(next);
                    }


                }
                else{
                    deathNum ++;
                }
//                if(next.fire==1)key += sas[next.turn-1];
                if(next.turn>=2 )newMap.put(key, next);
            }
        }

        return ret;
    }
    State max = null;
    double maxScore;
    int maxRensa = 0;
    int maxRensaBias;
    double maxRensaScore;
    long allpct = 0;
    long allrt = 0;
    int beforeRensa = 0;
    boolean firstFired;
    static boolean ryusuigansaiken=true;

    int recallNum,deathNum,fireNum,hashNum,allNum;

    int yosokuOjama;
    State maxRensaState = null;

    int oldLFT =-1;
    String output() {
        long st = time();
        if(oldLFT!=lastFireTurn)mostNum = Util.mostNum(packs, lastFireTurn);
        oldLFT = lastFireTurn;
        maxRensa = 0;
        String ret = null;
        int oriField[][] = Util.genTField(field);
        State start = new State();
        start.ojama = obstacleCount;
        start.field = Util.genCopyField((oriField));
        List<State> nowList = new ArrayList<>();
        nowList.add(start);
        Map<Double, State> tree = new TreeMap<>(new Comparator<Double>() {
            public int compare(Double m, Double n) {
                return ((Double) m).compareTo(n) * -1;
            }
        });
        fireState.clear();
        long allscnt =allpct =  allrt = Diff.allt = Func.allft = Func.allfpf = Func.allpt = Func.allFallt = Func.allpct = Func.allpcct =  0;

        for(int j=0;j<BEAM_DEPTH;j++){
            betterCommand[(turn+1)%2][j]="\"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\"";
            fireCommand[(turn+1)%2][j]="\"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\"";
        }
        if(Util.calcOjamaNum(oriField) >= 50 && skill >= 80 && beforeRensa < 11){
            justAttack = true;
            return "S";
        }
        Func.maxn = Func.turnMaxChain = Func.maxkotei = 0;

        if(justAttacked){
            BEAM_DEPTH = 15;
            fireYotei = turn+BEAM_DEPTH;
            lastFireTurn = turn;
            counterCount = BEAM_DEPTH;
            firstFired = true;
            EX_DEPTH = 3;
        }
        double scale = 1;
        double scale2 = 1;
        double scale3 = 1;
        if(ryusuigansaiken && turn<=10)scale2 = 2;
        if(turn<=0) {
            scale =3.5;

        }
        else if(millitime>=30000){
            if(justAttacked ){
                scale = 3;
                scale2 = 1;
            }
            else if(justAttack )scale = 3;
            else if(justBomber==1  ){
                scale = 3;
                scale2 = 1;
            }
        }
        if(Util.isPinch(oriField) && counterCount<=0){
            FIRE_SIZE = 1;
            pinch = true;
        }
        else{
            FIRE_SIZE = MIN_FIRE_SIZE;
            pinch = false;
        }

//        if(!firstFired && turn!=0 && fireYotei!=turn)scale = 1+(double)turn/(fireYotei-turn)*5;

        long oldTime = time();



        if(ryusuigansaiken&& BEAM_DEPTH>10 && turn+BEAM_DEPTH-lastFireTurn>20 && counterCount<=0)BEAM_DEPTH--;
        else if(!ryusuigansaiken){
            if(millitime < 30000)BEAM_DEPTH = 10;
            else BEAM_DEPTH = 15;
        }


        boolean poteSkip = false;

        yabaiScale = 1;
        int yabaiRensa = 0;
        State yabaiState = null;
        State state12 = null;

        for (int i = 0; i < BEAM_DEPTH; i++) {
            if(fireYotei < i+turn+1)break;
//            if(turn==0 && i<8)scale3 = 6;
//            else scale3 = 1;

            maxMSS = 0;
            recallNum = deathNum = fireNum = hashNum = allNum = 0;
            long iTime = time();
            hashMap.clear();
            List<State> nextList = new ArrayList<>();
            maxRensa = 0; maxRensaScore = 0; maxScore = -999; maxRensaBias = 0;
            max = null;
            maxRensaState = null;


            for (State now : nowList) {
                long scnt = time();
                nextList.addAll(calcNext(now,  i));
                long ecnt = time();
                allscnt += ecnt-scnt;
            }



            tree.clear();
            for (State s : nextList) {
                tree.put(s.score, s);
            }
            nowList.clear();
            if(scale >= 2 && (time()-st) >= 10000 && i<10)scale -=1;
            else if(scale < 2 && (time()-st) >= 15000 && i<13)scale =0.5;
            else if(scale >= 3 && (time()-st) >= 10000 && i==10)scale -=1;
            else if(scale >= 2 && (time()-st) >= 6000 && i==5)scale -=1;
            else if(scale >= 2 && (time()-st) >= 15000)scale -=1;
            else if(scale < 2 && (time()-st) >= 10000 && i<10)scale =0.5;
            else if(time() - iTime >= 2500 && turn >0 && i>=4 && scale>=2)scale -=1;

            int count = 0;
            double randRate = 1;
            if(turn==0 && i<8)randRate = 1;
            List<State> randList = new ArrayList<>();
            for (State s : tree.values()) {
                if (count > BEAM_WIDTH*scale*scale2*scale3*randRate)randList.add(s);
                else nowList.add(s);
                count++;
            }
            if( fireYotei < i+turn && i <BEAM_DEPTH-1){
                tree.clear();
                for(State s: randList){
                    tree.put(rand.nextDouble(), s);
                }
                for(State s: tree.values()){
                    nowList.add(s);
                    count++;
                    if(count>=BEAM_WIDTH*scale*scale2*scale3)break;
                }
            }
//            Util.debug("next.size():"+nowList.size()+"\t");
            if(max!=null) {
                betterCommand[(turn + 1) % 2][i] = max.command + "\"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\"";
//                if(max.poteChain>=20 &&counterCount<=0 && !poteSkip){
//                    fireYotei = turn+max.turn+1;
//                    poteSkip = true;
//                    if(fireYotei - turn<10)fireYotei = turn+10;
//                }
            }


//            if(i==0 && aiteRensa[i] >= 11)yabaiScale
            if(i<AITE_DEPTH && max!=null && aiteRensa[i] >= 13 && yabaiState == null && aiteMaxRensaTurn==turn+i){
                yabaiState = max;
            }
            if(turn+i==12 && max!=null)state12 = max;

            if(counterCount==10 && fireYotei == i+turn){
                int maxRensa = 0;
                for(State s: fireState){
                    if(s.rensa>maxRensa)maxRensa = s.rensa;
                }
                if(maxRensa < FIRE_SIZE)fireYotei = lastFireTurn + BEAM_DEPTH +EX_DEPTH;

            }
            if(i<AITE_DEPTH && aiteRensa[i] > maxRensa && aiteRensa[i] >= 11 ){
                Util.debugln("やばい");
            }
//            Util.debugln("all:"+allNum+" recall:"+recallNum+" hash:"+hashNum+" death:"+deathNum+" fire:"+fireNum);
            if(
                    //(scale!=1||justAttacked||justAttack||turn==0 || justBomber==1) &&
                    max!=null
            )Util.debugln("i:"+i+" s:"+scale*scale2*scale3+"\ttime:"+(time()-st)+"\tr:"+maxRensa+" rs:"+Util.to6keta(maxRensaScore)+" rb:"+maxRensaBias+" p:"+max.poteChain +" s:"+Util.to6keta(max.score)+" b:"+max.potePoint[1]);


//            if(yabai && yabaiRensa <=maxRensa && maxRensaState!=null){
//                jikoClear.add(turn+maxRensa);
//                Util.debugln("自己ナグリモード終了");
//                break;
//            }
//            if(maxRensa >= 11 && !yabai){
//                yabai = true;
//                yabaiRensa = maxRensa;
//                Util.debugln("自己ナグリモード");
//                int ojama = Util.toOjama(maxRensa);
//                for(State s: nowList){
//                    s.ojama += ojama;
//                }
//            }
        }








        oldMap = newMap;
        newMap = new HashMap<>();
        if(counterCount>0 || !ryusuigansaiken) {
            for (State s : fireState) {
                tree.put(s.score, s);
            }
        }
        else{
            for (State s : fireState) {
                if (turn>20 && s.turn <= 5 && s.rensa >= 18) {
                    tree.put(s.score, s);
                }
            }
        }
        State out = null;
        justAttack = false;
        if(counterCount>0)Util.debugln("流水岩砕拳");
        for (State s : tree.values()) {
            out = s;
//            if(ryusuigansaiken && state12!=null)out = state12;
//            if(ryusuigansaiken && yabaiState!=null)out = yabaiState;
            beforeRensa = out.rensa;
            if(out.rensa >= FIRE_SIZE) {
                Func.kotei = out.potePoint[0];
                Func.kotein = Func.maxn;
                fireYotei = turn + out.turn+EX_DEPTH;
                if(out.rensa == 10) fireYotei = turn + out.turn+EX_DEPTH+3;
                if(counterCount==1)counterCount = 2;
            }
            else{
                if(counterCount>0)ryusuigansaiken = false;
                fireYotei = turn + BEAM_DEPTH+EX_DEPTH;
                Func.kotei = 0;
                counterCount =0 ;

                EX_DEPTH = 3;
            }

            if(s.fire==1 && s.turn==1){
                ryusuigansaiken = false;
                BEAM_DEPTH = 15;
                counterCount = 0;
                EX_DEPTH = 3;
                justAttack = true;
                lastFireTurn = turn;
                firstFired = true;
//                RATE = BOMBER_RATE;
                Func.kotei = 0;
                fireYotei = turn + BEAM_DEPTH+EX_DEPTH;
            }

            break;
        }
        if(out==null)ret="0 0";//out.command.charAt(0)+" "+out.command.charAt(1);
        else {
            ret = out.firstCommand / 10 + " " + out.firstCommand % 10;
            oldOutCommand = out.firstCommand / 10 + "" + out.firstCommand % 10;
        }
        tree.clear();

        for (State s : fireState) {
            tree.put(s.score, s);
        }

        int count = 0;
        for (State s : tree.values()) {
            fireCommand[(turn+1)%2][count] = s.command+"\"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\"";
            count++;
            if(count<fireCommand[0].length)break;
        }
        counterCount--;

        if(out!=null){

            Util.debugln(out.command + "\n  score:" + out.score+"\n rensa:"+out.rensa +" turn:"+(turn+out.turn) );
            if(pinch)Util.debugln("pinch");
        }

        long et = time();
        Util.debugln("aite() time:"+allat);
        Util.debugln("output() time:"+(et-st));
        Util.debugln("\tcalcNext() time:"+(allscnt));
//        Util.debugln("\t\trensa() time:"+(allrt));
        Util.debugln("\t\tpoteChain() time:"+(allpct));
        Util.debugln("\t\tpoteChain()only time:"+(Func.allpct));
        Util.debugln("\t\tpoteChain()copy time:"+(Func.allpcct));
        Util.debugln("\t\tfire() time:"+(Func.allft));
        Util.debugln("\t\tfall() time:"+(Func.allFallt));
        Util.debugln("\t\tfallPackFire() time:"+(Func.allfpf));
        Util.debugln("\t\tpattern() time:"+(Func.allpt));

//        Util.debugln("ki:"+Func.kotei+" kn:"+Func.kotein);
//        Util.debugln("ja:"+justAttack+" jaed:"+justAttacked);
        return ret;
    }

    int aiteRensa[] = new int[AITE_DEPTH];
    long allat;
    void aite(int field[][]){
        aiteMaxRensaTurn = 0;
        allat = 0;
        long s = time();
        int oriField[][] = Util.genTField(field);


        String ret = null;
        State start = new State();
        start.ojama = Main.enemyObstacleCount;
        start.field = Util.genCopyField((oriField));
        List<State> nowList = new ArrayList<>();
        nowList.add(start);

        for(int i=0; i<AITE_DEPTH;i++){
            aiteRensa[i] = 0;
        }

        for (int i = 0; i < AITE_DEPTH; i++) {
            List<State> nextList = new ArrayList<>();


            for (State now : nowList) {

                for (int r = 0; r < 4; r++) {
                    int fallPack[][] = Util.genRotatePack(packs[turn + now.turn], r);//信じられる

                    for (int x = 0; x < 9; x++) {
                        State next = new State(now);
                        if (next.ojama >= 10) {
                            func.fallOjama(next.field);
                            next.ojama -= 10;
                        }
                        int firePoint[] = new int[width];
                        int fpf = func.fallPackFire(next.field, fallPack, x, firePoint,0);
                        if (fpf >= 0) {
                            next.rensa = func.rensa(next.field, firePoint);
                        }

                        next.death = func.isDeath(next.field);


                        if(!next.death) {

                            if (next.rensa >= 3) {
                                if(aiteRensa[i] < next.rensa){
                                    aiteRensa[i] = next.rensa;
                                }
                            } else {
                                nextList.add(next);
                            }
                        }
                    }
                }
            }

            nowList.clear();
            nowList.addAll(nextList);
        }
        int max = 0;
        for(int i=0;i<AITE_DEPTH;i++){
            if(max<aiteRensa[i]) {
                max = aiteRensa[i];
                aiteMaxRensaTurn = turn + i;
            }
        }

        long e = time();
        allat = e-s;
        Util.debug("aite:");
        for(int a: aiteRensa)Util.debug(a+", ");
        Util.debugln("");
    }

    long time(){
        return System.currentTimeMillis();
    }

}
