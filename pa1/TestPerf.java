package pa1;

import edu.princeton.cs.algs4.*;


public class TestPerf {
    ST<String, Integer> st;
    SeparateChainingHashST<String, Integer> separateChainingHashST;
    LinearProbingHashST<String, Integer> linearProbingHashST;
    SequentialSearchST<String, Integer> sequentialSearchST;

    long stTime;
    long separateTime;
    long linearTime;
    long sequentialTime;

    public TestPerf(String fname) {
        st = new ST<String, Integer>();
        separateChainingHashST = new SeparateChainingHashST<String, Integer>();
        linearProbingHashST = new LinearProbingHashST<String, Integer>();
        sequentialSearchST = new SequentialSearchST<String , Integer>();

        In in = new In(fname);
        String[] allStrs = in.readAllStrings();
        long current = System.currentTimeMillis();
        for (String s : allStrs) {
            if (st.contains(s)) {
                st.put(s, st.get(s) + 1);
            } else {
                st.put(s, 1);
            }
        }
        stTime = System.currentTimeMillis() - current;
        current = System.currentTimeMillis();

        for (String s : allStrs) {
            if (separateChainingHashST.contains(s)) {
                separateChainingHashST.put(s, separateChainingHashST.get(s) + 1);
            } else {
                separateChainingHashST.put(s, 1);
            }
        }
        separateTime = System.currentTimeMillis() - current;
        current = System.currentTimeMillis();

        for (String s : allStrs) {
            if (linearProbingHashST.contains(s)) {
                linearProbingHashST.put(s, linearProbingHashST.get(s) + 1);
            } else {
                linearProbingHashST.put(s, 1);
            }
        }
        linearTime = System.currentTimeMillis() - current;
        current = System.currentTimeMillis();

        for (String s : allStrs) {
            if (sequentialSearchST.contains(s)) {
                sequentialSearchST.put(s, sequentialSearchST.get(s) + 1);
            } else {
                sequentialSearchST.put(s, 1);
            }
        }
        sequentialTime = System.currentTimeMillis() - current;

    }

    public int getTotalWords() {
        int result = 0;
        for (String key: st.keys()) {
            result += st.get(key);
        }
        return result;
    }

    public int getUniqueWords()
    {
        return st.size();
    }

    public String getMostUsedWord() {
        int max = 1;
        String result = null;
        for (String key: st.keys()) {
            if (st.get(key) > max) {
                max  = st.get(key);
                result = key;
            }
        }
        return result;
    }

    public int getMaxOccurrence() {
        return st.get(getMostUsedWord());
    }

    public void printStats() {
        System.out.println(stTime);
        System.out.println(separateTime);
        System.out.println(linearTime);
        System.out.println(sequentialTime);
        System.out.println(getTotalWords());
        System.out.println(getUniqueWords());
        System.out.println(getMostUsedWord() + " " + getMaxOccurrence());
    }

    public static void main(String[] args) {
        TestPerf perf = new TestPerf(args[0]);
        perf.printStats();
    }
}
