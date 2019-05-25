import java.util.stream.Collectors;
import java.util.*;

public class Util {

    public static void main(String args[]){
        debugln(""+calcScore(11));
    }
    static double calcScore(int rensa){
        double ret = 0;
        double tmp = 1.3;
        for(int i=0; i<rensa;i++){
            ret += tmp;
            tmp *= 1.3;
        }
        return ret;
    }
    static int[][] genCopyField(int a[][]){
        int ret[][] = new int[a.length][a[0].length];
        for(int i=0;i<a.length;i++){
            for(int j=0;a[i][j] != 0;j++){
                ret[i][j] = a[i][j];
            }
        }
        return ret;
    }

    static void debugln(String str){
        System.err.println(str);
    }
    static void debug(String str){
        System.err.print(str);
    }
    static double njo(double a, int n){
        double ret = 1;
        for(int i=0;i<n;i++){
            ret *= a;
        }
        return ret;
    }




    // 標準エラー出力に盤面の情報を出力します
    static void printField(int[][] field) {
        System.err.println(
                Arrays.stream(field).map(row -> {
                    return Arrays.stream(row)
                            .mapToObj(block -> String.format("%2d", block))
                            .collect(Collectors.joining(" "));
                }).collect(Collectors.joining("\n"))
        );
        debugln("");
        System.err.flush();
    }

    static int[][] genTField(int a[][]){
        int ret[][] = new int[a[0].length][a.length];

        for(int i=0;i<a.length;i++){
            for(int j=0;j<a[0].length ;j++){
                ret[j][ret[0].length-i-1] = a[i][j];
            }
        }

        return ret;
    }

    // 標準エラー出力にパックの情報を出力します
    static void printPack(int[][] pack) {
        System.err.println(
                Arrays.stream(pack).map(row -> {
                    return Arrays.stream(row)
                            .mapToObj(block -> String.format("%2d", block))
                            .collect(Collectors.joining(" "));
                }).collect(Collectors.joining("\n"))
        );
        System.err.flush();
    }

    static String calcKey(int a[][]){
        StringBuilder ret = new StringBuilder();
        for(int i=0;i<a.length;i++){
            for(int j=0;a[i][j]!=0;j++){
                ret.append(a[i][j]);
            }
            ret.append(0);
        }
        return ret.toString();
    }
    static int[][] genRotatePack(int pack[][], int r){
        int ret[][] = new int[2][2];
        switch(r) {
            case 3:
                ret[0][0] = pack[0][0];
                ret[0][1] = pack[0][1];
                ret[1][0] = pack[1][0];
                ret[1][1] = pack[1][1];
                break;
            case 0:
                ret[0][0] = pack[1][0];
                ret[0][1] = pack[0][0];
                ret[1][0] = pack[1][1];
                ret[1][1] = pack[0][1];
                break;
            case 1:
                ret[0][0] = pack[1][1];
                ret[0][1] = pack[1][0];
                ret[1][0] = pack[0][1];
                ret[1][1] = pack[0][0];
                break;
            case 2:
                ret[0][0] = pack[0][1];
                ret[0][1] = pack[1][1];
                ret[1][0] = pack[0][0];
                ret[1][1] = pack[1][0];
                break;
        }
        return ret;
    }

    static int calcOjamaNum(int f[][]){
        int ret = 0;
        for(int i=0;i<10;i++){
            for(int j=0;f[i][j]!=0;j++){
                if(f[i][j]==11)ret++;
            }
        }
        return ret;
    }

    static int calcHight(int f[][]){
        int ret = 0;

        for(int i=0;i<10;i++) {
            int j=0;
            for (; f[i][j] != 0; j++) {
            }
            if(ret < j)
                ret = j;

        }
        return ret-1;
    }
    static boolean isPinch(int f[][]){
        int sum = 0;
        int maxh = 0;
        for(int i=0;i<10;i++){
            int ojamah = 0;
            for(int j=0;f[i][j]!=0;j++){
                if(f[i][j]==11){
                    ojamah = j;
                    if(maxh < j)maxh = j;
                }
            }
            sum += ojamah;
        }
        int five = 0;
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                if(f[i][j]==5){
                    for(int k=j+1;f[i][k]!=0;k++){
                        if(f[i][j] == 11){
                            five++;
                            break;
                        }

                    }
                }
            }
        }
        if(sum*0.1+Search.obstacleCount/10 >= 11)return true;
        if(sum*0.1+Search.obstacleCount/10 >= 9 && maxh+Search.obstacleCount/10>= 13)return true;
        return false;
    }

    static boolean isSkill(int f[][]){
        int sum = 0;
        int maxh = 0;
        for(int i=0;i<10;i++){
            int ojamah = 0;
            for(int j=0;f[i][j]!=0;j++){
                if(f[i][j]==11){
                    ojamah = j;
                    if(maxh < j)maxh = j;
                }
            }
            sum += ojamah;
        }
        int five = 0;
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                if(f[i][j]==5){
                    for(int k=j+1;f[i][k]!=0;k++){
                        if(f[i][j] == 11){
                            five++;
                            break;
                        }

                    }
                }
            }
        }
        if(five == 0)return false;
        if(sum*0.1+Search.obstacleCount/10 >= 11)return true;
        if(sum*0.1+Search.obstacleCount/10 >= 9 && maxh+Search.obstacleCount/10>= 13)return true;
        return false;
    }
    static double to6keta(double a){
        return (int)(a*100000)/100000.0;
    }

    static int toOjama(int r){
        int ret = 0;
        double tmp = 1;
        for(int i=0; i<r;i++){
            tmp *= 1.3;
            ret += (int)tmp;
        }
        return ret;
    }

    static int mostNum(int pack[][][], int lastFireTurn){
        double a[]= new double[10];
        int bias = 15, end=23;
        for(int i=lastFireTurn+bias; i<lastFireTurn+end;i++){
            int f[]= new int[10];
            for(int j=0;j<2;j++){
                for(int k=0;k<2;k++){
                    f[pack[i][j][k]]=1;
                }
            }
            for(int j=1;j<10;j++){
                a[j] += f[j];
            }
        }
        Map<Double, Integer> tree = new TreeMap<>(new Comparator<Double>() {
            public int compare(Double m, Double n) {
                return ((Double) m).compareTo(n) * -1;

            }
        });
        double max = -1;
        int maxId = -1;

        for(int i=1;i<10;i++){
            double tmp = a[i] + Search.rand.nextDouble()*0.1;
            tree.put(tmp,i);
            if(max<tmp){

                max = a[i];
                maxId = i;
            }
        }
        int c = 0;
        Search.mostNumList.clear();
        for(int q: tree.values())Util.debug(q+" ");
        Util.debugln("");
        for(int q: tree.values()){

            Search.mostNumList.add(q);
            c++;
            if(c>=3)break;
        }

        return maxId;
    }
}
