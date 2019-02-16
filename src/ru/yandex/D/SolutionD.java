package ru.yandex.D;

import java.io.*;
import java.util.StringTokenizer;


//WORKED
public class SolutionD {
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

    private static class Solution {

        double [][] d = new double[5000][10];

        public void solve(InputReader in, PrintWriter out) {
            String v = in.next();
            int k = in.nextInt();

            int sum = 0;

            for (int i = 0; i < v.length(); ++i) {
                sum += v.charAt(i) - '0';
            }

            int []cnt = new int[3];

            for (int i = 0; i < v.length(); ++i) {
                char x = v.charAt(i);

                if (x == '5' || (sum % 3 == 0 && (x - '0') % 2 == 0))
                    cnt[1]++;
                else cnt[0]++;
            }

            char x = v.charAt(v.length() - 1);

            if ((x == '5' || (sum % 3 == 0 && (x - '0') % 2 == 0))) {
                d[0][1] = 1;
                d[0][0] = 0;
            } else {
                d[0][1] = 0;
                d[0][0] = 1;
            }
            double total = (int)((v.length()) * (v.length() - 1) / 2);
            for(int i = 1; i <= k; ++i) {
                d[i][0] += d[i - 1][0] * (1 - (cnt[1] / total));
                d[i][0] += d[i - 1][1] * (cnt[0] / total);
                d[i][1] += d[i - 1][0] * (cnt[1] / total);
                d[i][1] += d[i - 1][1] * (1 - (cnt[0] / total));
            }

            out.println(String.format("%.9f", d[k][1]).replace(',', '.'));
        }
    }

}
