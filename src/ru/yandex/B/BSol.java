package ru.yandex.B;

import java.io.*;
import java.util.*;

public class BSol {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        BSol.InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        BSol.Solution solution = new BSol.Solution();
        solution.solve(in, out);
        out.close();
    }

    private static class Solution {
        public void solve(InputReader in, PrintWriter out) {
            int N = Integer.parseInt(in.next());
            List<String> nums = new ArrayList<>();
            for (int i = 0; i < N; ++i) {
                nums.add(in.nextLine());
            }

            int M = Integer.parseInt(in.next());
            List<String> templates = new ArrayList<>();
            for (int i = 0; i < M; ++i) {
                templates.add(in.nextLine());
            }


            StringBuilder builder;
            Map<String, Pair> dict = new HashMap<>();
            for (String tmp : templates) {
                builder = new StringBuilder();
                int size = 0;
                for (int i = 0; i < tmp.length(); ++i) {
                    if ((Character.isDigit(tmp.charAt(i)))) {
                        builder.append(tmp.charAt(i));
                    }
                    if ((Character.isDigit(tmp.charAt(i))) || tmp.charAt(i) == 'X') {
                        size++;
                    }
                }
                dict.put(builder.toString(), new Pair(tmp, size));
            }

            List<String> filtered = new ArrayList<>();
            for (String num : nums) {
                builder = new StringBuilder();
                for (int i = 0; i < num.length(); ++i) {
                    if ((Character.isDigit(num.charAt(i)))) {
                        builder.append(num.charAt(i));
                    }
                }
                filtered.add(builder.toString());
            }

            for (Map.Entry entry : dict.entrySet()) {
                String key = (String) entry.getKey();
                for (String f : filtered) {
                    if (dict.containsKey(f.substring(0, key.length()))) {
                        Pair template = dict.get(f.substring(0, key.length()));
                        if (template.size == f.length()) {
                            buildFromTemplate(f, template.str, out);
                        }
                    }
                }
            }
        }

        private void buildFromTemplate(String f, String template, PrintWriter out) {
            out.println("str: " + f);
            out.println("temp: " + template);
            StringBuilder builder = new StringBuilder();
            int C = 0, N = 0;
            for (int i = 1; i < 5; ++i) {
                if ((template.charAt(i) == ' ')) {
                    C = i;
                    break;
                }
            }

            for (int i = C + 1; i < C + 4; i++) {
                if (template.charAt(i) == '(') {
                    N = i;
                }
            }

            int ad = 0;
            for (int i = N; i < template.length(); i++) {
                if (template.charAt(i) == '-') {
                    ad = i;
                }
            }

            builder.append('+');
            for (int i = 0; i < C; i++) {
                builder.append(f.charAt(i));
            }
            builder.append(" (");
            for (int i = C; i < N - 1; i++) {
                builder.append(f.charAt(i));
            }
            builder.append(") ");
            for (int i = N - 1; i < f.length(); ++i) {
                builder.append(f.charAt(i));
            }
            builder.append(template.substring(ad - 1));
            out.println(builder.toString());
        }
    }

    static class Pair {
        int size;
        String str;
        public Pair(String str, int size) {
            this.size = size;
            this.str = str;
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

        public String nextLine() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            StringBuilder res = new StringBuilder();
            while (tokenizer.hasMoreTokens()) {
                res.append(" ").append(tokenizer.nextToken());
            }
            return res.toString();
        }
    }
}
