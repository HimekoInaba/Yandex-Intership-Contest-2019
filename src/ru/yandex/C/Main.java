package ru.yandex.C;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

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


    private static class Solution {
        public void solve(InputReader in, PrintWriter out) {
            String s = in.next();
            int q = in.nextInt();

            List<Pair> list = parseString(s);

            for (int i = 1; i < list.size(); ++i) {
                list.get(i).cnt += list.get(i - 1).cnt;
                list.get(i).fenceLen += list.get(i - 1).fenceLen;
            }

            for (int i = 0; i < q; ++i) {
                long l = in.nextLong();
                long r = in.nextLong();

                int li = getPosition(l, list);
                int ri = getPosition(r, list);

                if (li == ri) {
                    out.println((digLen(r - l + 1) + 1));
                    continue;
                }

                long ls = list.get(li).cnt, lr = l;
                if (li - 1 >= 0) {
                    long val = list.get(li - 1).cnt;
                    ls -= val;
                    lr -= val;
                }

                long rr = r;
                if (ri - 1 >= 0) {
                    long val = list.get(ri - 1).cnt;
                    rr -= val;
                }

                long result = 2 + digLen(ls - lr + 1) + digLen(rr);
                if (li + 1 < ri) {
                    result += list.get(ri - 1).fenceLen - list.get(li).fenceLen;
                }

                out.println(result);
            }
        }

        private int digLen(long d) {
            if (d == 1) return 0;
            int cnt = 0;
            while (d > 0) {
                d /= 10;
                ++cnt;
            }
            return cnt;
        }

        private int getPosition(long pos, List<Pair> list) {
            int l = 0, r = list.size() - 1;
            while (r - l > 1) {
                int m = (l + r) / 2;

                if (list.get(m).cnt > pos)
                    r = m;
                else l = m;
            }
            if (list.get(l).cnt >= pos) return l;
            return r;
        }

        private class Pair {
            public long cnt;
            public char c;
            public long fenceLen;

            public Pair(long cnt, char c, long fenceLen) {
                this.cnt = cnt;
                this.c = c;
                this.fenceLen = fenceLen;
            }

            @Override
            public String toString() {
                return "Pair{" +
                        "cnt=" + cnt +
                        ", c=" + c +
                        ", fenceLen=" + fenceLen +
                        '}';
            }
        }

        private List<Pair> parseString(String s) {
            List<Pair> result = new ArrayList<>();

            for (int i = 0; i < s.length(); ++i) {
                int cnt = 0;
                while (i < s.length() && Character.isDigit(s.charAt(i))) {
                    char q = s.charAt(i);
                    cnt = cnt * 10 + q - '0';
                    ++i;
                }
                if (cnt == 0)
                    cnt = 1;
                char c = s.charAt(i);

                result.add(new Pair(cnt, c, 1 + digLen(cnt)));
            }

            return result;
        }
    }

    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
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

        public long nextLong() {
            return Long.parseLong(next());
        }
    }
}
