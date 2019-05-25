public class Func {
    final int width;
    final int simulationHeight;
    Func(int aw, int ah){
        width = aw;
        simulationHeight = ah;
    }
    int dxy[][]={
            {-1,-1},
            {0,-1},
            {1,-1},
            {-1,0},
            {-1,1},
            {0,1},
            {1,1}

    };


    void fallOjama(int field[][]){
        for(int i=0;i<width;i++){
            int j=0;
            for(j=0;field[i][j]!=0;j++){

            }
            field[i][j] = 11;
        }
    }

    static long allft;

    boolean fire(int field[][], int fallPoint[], int firePoint[]){
        long s = time();
        for(int i=0;i<width;i++){
            fallPoint[i] = simulationHeight-1;
        }
        int dxy[][] ={
                {-1,0},
                {-1,1},
                {0,1},
                {1,1}
        };
        boolean ret = false;
        for(int i=0;i<width-1;i++){
            for (int j = firePoint[i]; field[i][j] != 0; j++) {
                for (int d[] : dxy) {
                    int tx = i + d[1], ty = j + d[0];
                    if (((field[i][j] + field[tx][ty]) & 15) == 10) {
                        ret = true;
                        field[i][j] += 256;
                        field[tx][ty] += 256;
                        if(fallPoint[i]>j){
                            fallPoint[i] = j;
                        }
                        if(fallPoint[tx]>ty){
                            fallPoint[tx] = ty;
                        }
                    }
                }

            }

            if(((field[i][0]+field[i+1][1])&15)==10){
                ret = true;
                field[i][0]+=256;
                field[i+1][1] += 256;

                if(fallPoint[i]>0){
                    fallPoint[i] = 0;
                }
                if(fallPoint[i+1]>1){
                    fallPoint[i+1] = 1;
                }
            }
            if(((field[i][0]+field[i+1][0])&15)==10){
                ret = true;
                field[i][0]+=256;
                field[i+1][0] += 256;
                if(fallPoint[i]>0){
                    fallPoint[i] = 0;
                }
                if(fallPoint[i+1]>0){
                    fallPoint[i+1] = 0;
                }
            }
        }

        for(int j=firePoint[width-1];field[width-1][j]!=0;j++){
            if(((field[width-1][j]+field[width-1][j-1])&15)==10){
                ret = true;
                field[width-1][j]+=256;
                field[width-1][j-1] += 256;

                if(fallPoint[width-1]>j){
                    fallPoint[width-1] = j;
                }
                if(fallPoint[width-1]>j-1){
                    fallPoint[width-1] = j-1;
                }
            }
        }
        long e = time();
        allft += e-s;
        return ret;
    }

    public static void main(String args[]){
        Func f = new Func(Main.width, Main.simulationHeight);
        int s[][]={
                {11,7,4, 0,0,0,0, 0,0,0,0,0,0,0,0,0,0,0,0,0},
                {8,8,4,2,11,11,11,11,4,8,0,0,0,0,0,0,0,0,0,0},
                {1,3,1,2,9,6,7,11,11,11,1,1,11,4,0,0,0,0,0,0},
                {2,3,4,11,11,8,11,9,9,0,0,0,0,0,0,0,0,0,0,0},
                {5,3,1,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {1,6,5,6,5,4,11,11,11,11,11,6,0,0,0,0,0,0,0,0},
                {2,6,9,8,7,2,5,4,7,11,11,11,11,11,3,0,0,0,0,0},
                {3,2,3,4,4,1,3,4,1,0,0,0,0,0,0,0,0,0,0,0},
                {11,11,11,11,11,6,4,9,0,0,0,0,0,0,0,0,0,0,0,0},
                {11,11,11,11,11,6,9,5,7,0,0,0,0,0,0,0,0,0,0,0}
        };
        Util.printField(s);
        int tmp[] = {0,2,2,2,2,2,2,2,2,2};
//        f.fall(s,tmp);
        int firePoint[] = new int[10];
        for(int i=0;i<10;i++)firePoint[i] = 1;
        Util.debugln(""+new Func(10,20).rensa(s,firePoint));
        Util.printField(s);
    }
    static long allFallt;
    void fall(int field[][], int fallPoint[], int firePoint[]){
        long s = time();
        firePoint[0] = fallPoint[0];
        for(int i=1; i<width;i++){
            firePoint[i] = fallPoint[i];
            if(firePoint[i]<1)firePoint[i]=1;
            if(firePoint[i-1] > fallPoint[i]-1) firePoint[i-1] = fallPoint[i]-1;
            if(firePoint[i-1]<1)firePoint[i-1]=1;
        }
//        Util.debugln("------------------------");
//        Util.printField(field);
        for(int i=0;i<width;i++){
            int j=fallPoint[i];
            for(;field[i][j]!=0 && field[i][j]<256;j++){
            }
            if(field[i][j]==0)continue;
            int diff=1;
            for(diff=1; field[i][diff+j] >= 256; diff++){

            }
            if(field[i][diff+j]==0){

            }else {

                for (; /*diff+j<simulationHeight &&*/ field[i][diff + j] != 0; j++) {
                    for (; field[i][j + diff] >= 256; diff++) {
                    }

                    field[i][j] = field[i][j + diff];
                    field[i][j + diff] = 256;

                }
            }
            for(int k=j;field[i][k]>=256;k++){
                field[i][k] = 0;
//                if(k==0)break;
            }
        }
        long e = time();
        allFallt += e-s;
//        Util.printField(field);
    }

    int rensa(int field[][], int firePoint[]){

        int chain = 1;
        int fallPoint[] = new int[width];
        while(fire(field, fallPoint, firePoint)){
//            Util.printField(field);
            chain++;
            fall(field,fallPoint,firePoint);
//            Util.printField(field);
        }
//        debugln("chain:"+chain);

        return chain;
    }

    static long allfpf;
    int fallPackFire(int oriField[][], int pack[][], int x, int afp[], int ojamah){
        long s = time();
        for(int i=0;i<width;i++)afp[i] = simulationHeight-1;
//        Util.debugln(oriField[0].length+"");
        int j=0;
        int ret = 0;
        if(pack[0][0]==0){
            pack[0][0] = pack[0][1];
            pack[0][1] = 0;
        }
        if(pack[1][0]==0){
            pack[1][0] = pack[1][1];
            pack[1][1] = 0;
        }
        for(j=0; oriField[x][j]!=0;j++){

        }


//        oriField[x][j] = 11;
//        oriField[x][j+1] = pack[0][0];
//        oriField[x][j+2] = pack[0][1];
        int packPoint2[] = new int[4];
        oriField[x][j + 0] = pack[0][0];
        oriField[x][j + 1] = pack[0][1];
        packPoint2[0] = x*100+j + 0;
        packPoint2[1] = x*100+j + 1;
        ret = j * 100;

        for(j=0; oriField[x+1][j]!=0;j++){
        }
//        oriField[x+1][j] = 11;
//        oriField[x+1][j+1] = pack[1][0];
//        oriField[x+1][j+2] = pack[1][1];
        oriField[x+1][j+0] = pack[1][0];
        oriField[x+1][j+1] = pack[1][1];
        packPoint2[2] = (x+1)*100+j + 0;
        packPoint2[3] = (x+1)*100+j + 1;
        ret += j;

        int firePoint[][] = {
                {ret/100, x },
                {ret/100+1, x},
                {ret%100, x+1},
                {ret%100+1, x+1}
        };
        int packPoint[][] = {
                {0, 0 },
                {0, 1},
                {1, 0},
                {1, 1}
        };
        ret = -1;
        int pi = 0;
        for(int fp[]: firePoint){
            for (int d[] : dxy) {
                int tx = d[1]+fp[1], ty = d[0]+fp[0];
                if (tx >= 0 && tx < width && ty >= 0 && ty < simulationHeight) {
                } else continue;

                int sum = oriField[tx][ty] + pack[packPoint[pi][0]][packPoint[pi][1]];
                if((sum&255) == 10){
                    oriField[tx][ty] += 256;
                    oriField[ fp[1] ][ fp[0] ] += 256;
                    ret = 1;
                }
            }
            pi++;
        }

        int fallPoint[] = new int[width];
        for(int i=0;i<width;i++){
            fallPoint[i] = simulationHeight-1;
        }
        for(int i=0; i<width;i++){
            for(j=0;oriField[i][j]!=0;j++){
                if(oriField[i][j]>=256){
                    oriField[i][j] = 256;
                    if(fallPoint[i]>j){
                        fallPoint[i] = j;
                    }
                }
            }
        }
        long e = time();
        allfpf += e-s;
        if(ret==1){
            int minPackFire = 99;
            ret = 99;
            LOOP:
            for(int i=0;i<10;i++){
                if(fallPoint[i]==simulationHeight-1)continue;
                boolean tmp = false;
                for(int a: packPoint2){
                    if(a/100==i && a%100==fallPoint[i]){
                        tmp = true;
                    }
                }
                if(!tmp &&ret>fallPoint[i])ret = fallPoint[i];
            }

            for(int a: packPoint2) {
                if (oriField[a/100][a%100]==256 && minPackFire > a%100) minPackFire = a%100;
            }
            ret = ret-minPackFire+1;
            if(ret >= 90)ret = 0;
        }
//        printField(oriField);
        if(ret>=0)fall(oriField,fallPoint,afp);
//        printField(oriField);
        return ret;

    }

    static int kotei = 0;
    static int kotein = 1;
    static int maxn;
    static int turnMaxChain = 0;
    static int maxkotei = 0;

    int poteCount;
    int maxPoteCount;
    static long allpct;
    static long allpcct;
    int potentialChain(int field[][], int potePoint[], int passTurn, int nextTurn){
        long s = time();
        int maxChain = 0;
        int sumChain = 0;
        maxPoteCount = 0;
        int backup[][] = Util.genCopyField(field);
        int checkPoint[] = new int[width];
        for(int i=0;i<width;i++){
            checkPoint[i] = simulationHeight-1;
        }
        for(int i=0;i<width;i++){
            int j=0;
            for(;field[i][j]!=0;j++){}
            checkPoint[i] = j;
        }
        int fallPoint[] = new int[width];
        int firePoint[] = new int[width];
        int add = 1;
        int addn = 1;
        int si = 1;
        int sn = 1;
        int ei = 9;
//        add = 7;
//        addn=1;


//        if(kotei > 0){
//            si = kotei;
//            add = 77;
//        }
        si=1;
        ei=10;
        add=7;
        if(potePoint[0] != -1){
            si = potePoint[0];
//            sn = potePoint[1];
            add = 99;
//            addn= 99;
//            si -= 3;
//            ei += 3;
//            if(si<0)si=0;
//            if(ei>10)ei=10;
        }
//        si = potePoint[0];
//        if(add>=10) {
//            sn = 1;
//            add = 1;
//            addn = 1;
//            si -= 3;
//            ei = si + 6;
//            if (si < 1) si = 1;
//            if (ei > 9) ei = 9;
//        }
//        si=0;
//        ei=10;
//        add=1;
        if(Search.ryusuigansaiken==false || Search.counterCount > 0){
            si=0;
            ei=10;
            add=1;

        }
        int ko[] = {1,2,7,8};
        potePoint[1] = -22;
        int maxa = -11;
        int bias = 4;
//        if(passTurn >=5)bias = 4;

        for(int i=si;i<ei;i+=add){
//            int j=checkPoint[i]+poteBias[i];
            int j=checkPoint[i]+bias;
//            if(passTurn<10)j=checkPoint[i]+3;
            if(!Search.ryusuigansaiken)j=checkPoint[i]+2;
            if(Search.counterCount > 0){
                int tmp = bias- (Search.BEAM_DEPTH - Search.counterCount+nextTurn);
                if(tmp < 0)tmp =0;
                j=checkPoint[i]+tmp;
            }
            if(j>=simulationHeight)j=simulationHeight-1;

            if( !Search.ryusuigansaiken || Search.counterCount > 0) {
                boolean flag[] = new boolean[7];
                int q = 0;
                for (int d[] : dxy) {
                    if (flag[q] || q == 3) continue;

                    boolean fired = false;
                    int tx = i + d[1], ty = j + d[0];
                    if (ty < 0 || tx < 0 || tx >= 10 || field[tx][ty] == 0|| field[tx][ty] == 11) continue;
                    int tar = field[tx][ty];
                    for (int k = 0; k < width; k++) {
                        fallPoint[k] = simulationHeight - 1;
                        firePoint[k] = simulationHeight - 1;
                    }
                    int q2 = 0;
                    for (int d2[] : dxy) {
                        int tx2 = i + d2[1], ty2 = j + d2[0];
                        if (ty2 < 0 || tx2 < 0 || tx2 >= 10) continue;
                        if (field[tx2][ty2] == tar) {
                            flag[q2] = true;
                            field[tx2][ty2] = 256;
                            fired = true;
                            if (fallPoint[tx2] > ty2) {
                                fallPoint[tx2] = ty2;
                            }
                        }
                        q2++;
                    }

                    if (fired) {
                        poteCount = 0;
                        long s1 = time();
                        fall(field, fallPoint, firePoint);
                        int tmp = rensa(field, firePoint);
                        long e1 = time();
                        allpct -= e1 - s1;
                        if (maxChain < tmp) {
                            maxChain = tmp;
                            maxa = -11;
                            potePoint[0] = i;
                            for (int k = i - 1; k <= i + 1; k += 2) {
                                if (k < 0 || k >= 10 || fallPoint[k] == simulationHeight - 1) continue;
                                int a = fallPoint[k] - j;
                                if (a > maxa) maxa = a;
                            }
                            maxPoteCount = poteCount;
                        }
                        s1 = time();
                        copyField(backup, field);
                        e1 = time();
                        allpcct += e1 - s1;
                    }
                    q++;
                }

            }
            else {
//                for (int n = Search.mostNum; n < 10; n += 99) {
                for (int n : Search.mostNumList) {
                    boolean fired = false;

                    for (int k = 0; k < width; k++) {
                        fallPoint[k] = simulationHeight - 1;
                        firePoint[k] = simulationHeight - 1;
                    }
                    for (int d[] : dxy) {
                        int tx = i + d[1], ty = j + d[0];
                        if (inField(tx, ty) == false) continue;
                        if (field[tx][ty] + n == 10) {
                            field[tx][ty] = 256;
                            fired = true;
                            if (fallPoint[tx] > ty) {
                                fallPoint[tx] = ty;
                            }
                        }
                    }


                    if (fired) {
                        poteCount = 0;
                        long s1 = time();
                        fall(field, fallPoint, firePoint);
                        int tmp = rensa(field, firePoint);
                        long e1 = time();
                        allpct -= e1 - s1;
                        if (maxChain < tmp) {
                            maxChain = tmp;
                            maxa = -11;
                            potePoint[0] = i;
                            for (int k = i - 1; k <= i + 1; k += 2) {
                                if (k < 0 || k >= 10 || fallPoint[k] == simulationHeight - 1) continue;
                                int a = fallPoint[k] - j;
                                if (a > maxa) maxa = a;
                            }
                            maxPoteCount = poteCount;
                        }
                        s1 = time();
                        copyField(backup, field);
                        e1 = time();
                        allpcct += e1 - s1;
                    }
                }
            }

//            if(passTurn>=10  && poteBias[i]<2) {
//            if(poteBias[i]<(passTurn)/3 && poteBias[i]<5) {
            potePoint[1] = maxa;
//                if (maxa - poteBias[i] >= 0) poteBias[i]++;
//                if (maxa - poteBias[i] < 0) poteBias[i]--;
//            }

        }

        long e = time();
        allpct += e-s;
//        return sumChain/10;
        return maxChain;
    }
    int sigma(int a){
        int ret =0 ;
        for(int i=1;i<=a;i++){
            ret += a;
        }
        return ret;
    }

    boolean inField(int x, int y){
        return x>=0 && x<width && y>=0 && y<simulationHeight;
    }
    void copyField(int a[][], int b[][]){
        for(int i=0;i<a.length;i++){
            for(int j=0;a[i][j] +b[i][j]!= 0;j++){
                b[i][j] = a[i][j];
            }
        }
    }


    boolean isDeath(int field[][]){
        for(int i=0; i<width;i++){
            if(field[i][Main.height]!=0)return true;
        }
        return false;
    }
    static long allpt;
    double pattern(int f[][]) {
        long s = time();
        double ret = 0;
        int kMax = 15;
        for(int i=0; i<width;i++){
            for(int j=0;f[i][j]!=0;j++){

                for(int k=2;k<kMax;k++) {
                    if (f[i][j + k]==0) break;
                    int a = f[i][j],d = f[i][j + k];

                    if (a + d == 10) {
                        ret += 1;
                        f[i][j] += 100;
                        f[i][j + k] += 100;
                    }
                }
                if (i != 0) {
                    for(int k=2;k<kMax;k++) {
                        if (f[i-1][j + k]==0 || f[i][j] == 0)break;
                        int b = f[i - 1][j + k];
                        if (f[i][j] + b == 10) {
                            ret += 1;
                            f[i][j] += 100;
                            f[i - 1][j + k] += 100;
                        }

                    }
                }
                if (i != width-1) {
                    for(int k=2;k<kMax;k++) {
                        if (f[i+1][j + k]==0 || f[i][j] == 0)break;
                        int b = f[i + 1][j + k];
                        if (f[i][j] + b == 10) {
                            ret += 1;
                            f[i][j] += 100;
                            f[i + 1][j + k] += 100;
                        }

                    }
                }

            }
        }
        for(int i=0; i<width;i++) {
            for (int j = 0; f[i][j] != 0; j++) {
                if(f[i][j] >= 100)f[i][j] -=100;
            }
        }
        long e = time();
        allpt += e-s;
        return ret;

    }

    long time(){
        return System.currentTimeMillis();
    }

}
