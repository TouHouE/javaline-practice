import java.time.temporal.Temporal;
import java.util.*;


public class YourHomework {

    private static final Scanner INPUT = new Scanner(System.in);
    private static final Random rand = new Random(System.currentTimeMillis());

    public static void main(String[] args) {
        //getData();
        //test();
        regexTest();
    }

    private static void regexTest() {
        String data = "\\\\fada" ;

        if(data.matches("\\\\\\\\.")) {
            System.out.printf("True   %s", data);
        } else {
            System.out.printf("FXXK");
        }
    }

    private static void test() {
        int father[] = new int[10];
        System.out.printf("father[] : ");

        for(int i = 0; i < 10; ++i) {
            father[i] = rand.nextInt(10000) + 1;
            System.out.printf("%d ", father[i]);
        }
        System.out.println();

    }

    private static int getFather(int v, int[] father) {
        if(father[v] == v) {
            return v;
        } else {
            father[v] = getFather(father[v], father);
            return father[v];
        }
    }


    private static void getData() {
        int len = INPUT.nextInt();
        int subLen = INPUT.nextInt();
        ArrayList<Integer> father = new ArrayList<>();
        //HashMap father = new HashMap<Integer, Integer>();
        for(int i = 0; i < len; ++i) {
            //father.put(i, INPUT.nextInt());
            father.add(INPUT.nextInt());
        }
        work(len, subLen, father);
    }

    private static void work(int len, int subLen, ArrayList<Integer> data) {
        ArrayList<Integer> temp = new ArrayList<Integer>();
        int min = 0, ans = Integer.MAX_VALUE;
/*
        for(int i = 0; i < len; ++i) {
            temp.clear();
            if(i + subLen > len) {
                for(int a : data.subList(0, i + subLen - len)) {
                    temp.add(a);
                }
                for(int a : data.subList(i, len)) {
                    temp.add(a);
                }

            } else {
                for(int a : data.subList(i, i + subLen)) {
                    temp.add(a);
                }

            }

            int oddMax = 0, evenMax = 0;
            //get one subSequence min
            for(int j = 0; j < temp.size(); ++j) {
                int tempNum = temp.get(j);

                if(j % 2 == 0) {
                    if(evenMax < tempNum) {
                        evenMax = tempNum;
                    }
                } else {
                    if(oddMax < tempNum) {
                        oddMax = tempNum;
                    }
                }
                min = Math.min(oddMax, evenMax);
            }

            if(ans > min) {
                ans = min;
            }
        }
*/


        System.out.printf("%d", ans);
    }
}

class Remove {
    public Remove(int removeNum) {
        this.removeNum = removeNum;
    }

    public int removeNum;
}