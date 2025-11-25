/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package svmradikalmetode.utility;

/**
 *
 * @author Imam
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IndonesianSentenceTokenizer {

    public ArrayList<String> listAkronim;
    public ArrayList<String> listMajemuk;

    public IndonesianSentenceTokenizer() {
        this.listAkronim = new ArrayList();
        this.listMajemuk = new ArrayList();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("./resource/tokenizer/acronym.txt"));

            String line = null;
            while ((line = reader.readLine()) != null) {
                this.listAkronim.add(line);
            }
            reader.close();

            reader = new BufferedReader(new FileReader("./resource/tokenizer/compositewords.txt"));

            line = null;
            while ((line = reader.readLine()) != null) {
                this.listMajemuk.add(line);
            }
            reader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(IndonesianSentenceTokenizer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(IndonesianSentenceTokenizer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<String> tokenizeSentence(String sentence) {
        ArrayList<String> token = new ArrayList();

        String[] tokens = sentence.split("\\s+");

        String pattern1 = "\\w{1}.*\\w{1}";
        String pattern2 = "\\W{1}.*\\W{1}";
        String pattern3 = "\\W{1}.{1,}";
        String pattern4 = ".{1,}\\W{1}";
        String pattern5 = "[\\w{1}\\W{1}]";
        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].matches(pattern1)) {
                token.add(tokens[i]);
            } else if (tokens[i].matches(pattern2)) {
                if (tokens[i].length() <= 2) {
                    token.add(tokens[i]);
                } else {
                    String opunc = tokens[i].substring(0, 1);
                    String word = tokens[i].substring(1, tokens[i].length() - 1);
                    String epunc = tokens[i].substring(tokens[i].length() - 1);

                    token.add(opunc);
                    token.add(word);
                    token.add(epunc);
                }
            } else if (tokens[i].matches(pattern3)) {
                if (tokens[i].matches("-[^a-zA-Z]+")) {
                    token.add(tokens[i]);
                } else {
                    token.add(tokens[i].substring(0, 1));

                    token.add(tokens[i].substring(1));
                }
            } else if (tokens[i].matches(pattern4)) {
                if (this.listAkronim.contains(tokens[i])) {
                    token.add(tokens[i]);
                } else {
                    String word = tokens[i].substring(0, tokens[i].length() - 1);
                    String punc = tokens[i].substring(tokens[i].length() - 1);

                    token.add(word);

                    token.add(punc);
                }
            } else if (tokens[i].matches(pattern5)) {
                token.add(tokens[i]);
            } else {
                token.add(tokens[i]);
            }
        }
        return token;
    }

    public ArrayList<String> tokenizeSentenceWithCompositeWords(String sentence) {
        ArrayList<String> token = new ArrayList();

        String[] tokens = sentence.split("\\s+");

        String pattern1 = "\\w{1}.*\\w{1}";
        String pattern2 = "\\W{1}.*\\W{1}";
        String pattern3 = "\\W{1}.{1,}";
        String pattern4 = ".{1,}\\W{1}";
        String pattern5 = "[\\w{1}\\W{1}]";
        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].matches(pattern1)) {
                token.add(tokens[i]);
            } else if (tokens[i].matches(pattern2)) {
                if (tokens[i].length() <= 2) {
                    token.add(tokens[i]);
                } else {
                    String opunc = tokens[i].substring(0, 1);
                    String word = tokens[i].substring(1, tokens[i].length() - 1);
                    String epunc = tokens[i].substring(tokens[i].length() - 1);

                    token.add(opunc);
                    token.add(word);
                    token.add(epunc);
                }
            } else if (tokens[i].matches(pattern3)) {
                if (tokens[i].matches("-[^a-zA-Z]+")) {
                    token.add(tokens[i]);
                } else {
                    token.add(tokens[i].substring(0, 1));

                    token.add(tokens[i].substring(1));
                }
            } else if (tokens[i].matches(pattern4)) {
                if (this.listAkronim.contains(tokens[i])) {
                    token.add(tokens[i]);
                } else {
                    String word = tokens[i].substring(0, tokens[i].length() - 1);
                    String punc = tokens[i].substring(tokens[i].length() - 1);

                    token.add(word);

                    token.add(punc);
                }
            } else if (tokens[i].matches(pattern5)) {
                token.add(tokens[i]);
            } else {
                token.add(tokens[i]);
            }
        }
        int i = 0;
        while (i < token.size()) {
            if (i < token.size() - 1) {
                String kataMajemuk = (String) token.get(i) + " " + (String) token.get(i + 1);
                if (this.listMajemuk.contains(kataMajemuk)) {
                    token.set(i, kataMajemuk);
                    token.remove(i + 1);
                }
                i++;
            } else {
                i++;
            }
        }
        return token;
    }

    public static void main(String[] args) {
        IndonesianSentenceTokenizer tokenizer = new IndonesianSentenceTokenizer();
        ArrayList<String> token = tokenizer.tokenizeSentence("Alvin pergi ke bagian kanan ruangan Prof. Iping.");
        for (int i = 0; i < token.size(); i++) {
            System.out.println((String) token.get(i));
        }
        System.out.println("==================================");

        token = tokenizer.tokenizeSentenceWithCompositeWords("Alvin pergi ke bagian kanan ruangan Prof. Iping.");
        for (int i = 0; i < token.size(); i++) {
            System.out.println((String) token.get(i));
        }
    }

    public ArrayList<String> tokenizeSentenceWithCompositeWords(ArrayList<String> text_tokenizing) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
