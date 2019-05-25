import java.util.List;

public class Diff {
    static long allt;

    public static void main(String args[]){
        Util.debugln(""+(10&15));
        Util.debugln(""+((10*256)>>8));
    }
    void addDiff(List<Integer> list, int i, int j, int before, int after){
        list.add(i+j*10+before*1000+after*100000);
    }

    void restoreField(State s, int copy[][]){
        restoreField(s.diffList, copy);
    }
    void restoreField(List<Integer> s, int copy[][]){
        long st = time();
        for(int a: s){
            int i = a%10;
            int j = a/10%100;
            int ori = a/1000%100;
                        copy[i][j] = ori;

        }
        long e = time();
        allt += e-st;
    }
    void cvtField(List<Integer> diff, int copy[][]){
        long st = time();
        for(int a: diff){
            int i = a%10;
            int j = a/10%100;
            int ori = a/1000%100;
            int after = a/100000%100;
            copy[i][j] = after;
        }

        long e = time();
        allt += e-st;
    }

    void calcDiff(State s, int oriField[][], int copy[][]){
        calcDiff(s.diffList, oriField, copy);
    }

    void calcDiff(List<Integer> diffList, int oriField[][], int copy[][]){
        long st = time();
        diffList.clear();
        for(int i=0; i<Main.width;i++){
            for(int j=0; oriField[i][j] + copy[i][j]!=0;j++){
                if(oriField[i][j] != copy[i][j]){
                    diffList.add(i+j*10+oriField[i][j]*1000+copy[i][j]*100000);
                }
            }
        }
        long e = time();
        allt += e-st;
    }
    long time(){
        return System.currentTimeMillis();
    }


}
