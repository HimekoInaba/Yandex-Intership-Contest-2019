import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        Solution solution = new Solution();
        solution.solve(in, out);
        out.close();
    }

    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 42768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

    }

    private static class Solution {
        public void solve(InputReader in, PrintWriter out) {
            int t = in.nextInt();

            Map<String, Integer> m = new HashMap<>();
            Map<Pair, Integer> g = new HashMap<>();
            Map<Integer, String> rm = new HashMap<>();
            Set <String> set = new HashSet<>();

            for (int i = 0; i < t; ++i) {
                String s = (in.next());
                int len = s.length() - 1;
                String x = "" + s.charAt(len - 3) + s.charAt(len - 2) + s.charAt(len - 1);
                construct(m, rm, x, set);
                for (int j = 0; j + 3 < s.length(); ++j) {
                    String a = "" + s.charAt(j) + s.charAt(j + 1) + s.charAt(j + 2);
                    String b = "" + s.charAt(j + 1) + s.charAt(j + 2) + s.charAt(j + 3);
                    int aa = construct(m, rm, a, set);
                    int bb = construct(m, rm, b, set);

                    Pair p = new Pair(aa, bb);

                    if (!g.containsKey(p)) {
                        g.put(p, 1);
                    } else {
                        g.put(p, g.get(p) + 1);
                    }
                }
            }

            out.println(set.size());
            out.println(g.size());
            g.forEach((pair, integer) -> out.println(rm.get(pair.v) + " " + rm.get(pair.u) + " " + integer));
        }

        private int construct(Map<String, Integer> m, Map<Integer, String> rm, String b, Set <String> set) {
            int bb;
            if (m.containsKey(b)) {
                bb = m.get(b);
            } else {
                m.put(b, bb = m.size());
                rm.put(bb, b);
            }
            set.add(b);
            return bb;
        }

        private class Pair {
            public int v, u;

            public Pair() {
            }

            public Pair(int v, int u) {
                this.v = v;
                this.u = u;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                Pair pair = (Pair) o;

                if (v != pair.v) return false;
                return u == pair.u;
            }

            @Override
            public int hashCode() {
                int result = v;
                result = 31 * result + u;
                return result;
            }
        }
    }
}
