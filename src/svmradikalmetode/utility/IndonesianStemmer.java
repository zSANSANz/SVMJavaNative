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
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IndonesianStemmer
{
  public ArrayList<String> dictionary = new ArrayList();
  public ArrayList<String> closewords_k = new ArrayList();
  public ArrayList<String> closewords_p = new ArrayList();
  public ArrayList<String> closewords_t = new ArrayList();
  public ArrayList<String> closewords_r = new ArrayList();
  public String possessivepronounsuffix = new String();
  public String particlesuffix = new String();
  public String derivationalsuffix = new String();
  public ArrayList<String> derivationalprefix = new ArrayList();
  
  public IndonesianStemmer()
  {
    this.dictionary.clear();
    this.closewords_k.clear();
    this.closewords_p.clear();
    this.closewords_t.clear();
    this.closewords_r.clear();
    
    this.possessivepronounsuffix = "";
    this.particlesuffix = "";
    this.derivationalsuffix = "";
    this.derivationalprefix.clear();
    
    BufferedReader reader = null;
    try
    {
      reader = new BufferedReader(new FileReader("./resource/stemming/dictionary.txt"));
      
      String line = null;
      while ((line = reader.readLine()) != null) {
        this.dictionary.add(line);
      }
      reader = new BufferedReader(new FileReader("./resource/stemming/close_words_k.txt"));
      
      line = null;
      while ((line = reader.readLine()) != null) {
        this.closewords_k.add(line);
      }
      reader = new BufferedReader(new FileReader("./resource/stemming/close_words_p.txt"));
      
      line = null;
      while ((line = reader.readLine()) != null) {
        this.closewords_p.add(line);
      }
      reader = new BufferedReader(new FileReader("./resource/stemming/close_words_t.txt"));
      
      line = null;
      while ((line = reader.readLine()) != null) {
        this.closewords_t.add(line);
      }
      reader = new BufferedReader(new FileReader("./resource/stemming/close_words_r.txt"));
      
      line = null;
      while ((line = reader.readLine()) != null) {
        this.closewords_r.add(line);
      }
      reader.close();
    }
    catch (FileNotFoundException ex)
    {
      Logger.getLogger(IndonesianStemmer.class.getName()).log(Level.SEVERE, null, ex);
    }
    catch (IOException ex)
    {
      Logger.getLogger(IndonesianStemmer.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  public boolean isBasicWord(String word)
  {
    return this.dictionary.contains(word);
  }
  
  public static void makeDictionary()
  {
    BufferedReader reader = null;
    try
    {
      reader = new BufferedReader(new FileReader("./resource/stemming/basicword.txt"));
      BufferedWriter writer = new BufferedWriter(new FileWriter("./resource/stemming/dictionary.txt"));
      
      String line = null;
      while ((line = reader.readLine()) != null)
      {
        String[] arr = new String[2];
        arr = line.split(" ");
        
        writer.write(arr[0] + "\n");
        writer.flush();
      }
    }
    catch (FileNotFoundException ex)
    {
      Logger.getLogger(IndonesianStemmer.class.getName()).log(Level.SEVERE, null, ex);
    }
    catch (IOException ex)
    {
      Logger.getLogger(IndonesianStemmer.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  public void makeCloseWordList()
  {
    try
    {
      BufferedWriter writer = new BufferedWriter(new FileWriter("./resource/stemming/close_words_k.txt"));
      BufferedWriter writer2 = new BufferedWriter(new FileWriter("./resource/stemming/close_words_r.txt"));
      BufferedWriter writer3 = new BufferedWriter(new FileWriter("./resource/stemming/close_words_p.txt"));
      BufferedWriter writer4 = new BufferedWriter(new FileWriter("./resource/stemming/close_words_t.txt"));
      for (int i = 0; i < this.dictionary.size(); i++)
      {
        String word1 = (String)this.dictionary.get(i);
        int count1 = 0;
        int count2 = 0;
        int count3 = 0;
        int count4 = 0;
        for (int j = 0; j < this.dictionary.size(); j++)
        {
          String word2 = (String)this.dictionary.get(j);
          if ((word2.startsWith("k")) && 
            (word2.endsWith(word1)) && (i != j) && (word2.length() == word1.length() + 1))
          {
            writer.write(word2 + "\n");
            count1 += 1;
          }
          if (word2.startsWith("r"))
          {
            if ((word2.endsWith(word1)) && (i != j) && (word2.length() == word1.length() + 1))
            {
              writer2.write(word2 + "\n");
              count2 += 1;
            }
          }
          else if ((word2.startsWith("p")) && (word1.startsWith("m")) && (word1.length() == word2.length()))
          {
            String temp_w1 = word1.substring(1, word1.length());
            String temp_w2 = word2.substring(1, word2.length());
            if (temp_w1.equals(temp_w2))
            {
              writer3.write(word2 + "\n");
              count3 += 1;
            }
          }
          else if ((word2.startsWith("t")) && (word1.startsWith("n")) && (word1.length() == word2.length()))
          {
            String temp_w1 = word1.substring(1, word1.length());
            String temp_w2 = word2.substring(1, word2.length());
            if (temp_w1.equals(temp_w2))
            {
              writer4.write(word2 + "\n");
              count4 += 1;
            }
          }
        }
        if (count1 > 0)
        {
          writer.write(word1 + "\n");
          writer.write("\n");
          writer.flush();
        }
        if (count2 > 0)
        {
          writer2.write(word1 + "\n");
          writer2.write("\n");
          writer2.flush();
        }
        if (count3 > 0)
        {
          writer3.write(word1 + "\n");
          writer3.write("\n");
          writer3.flush();
        }
        if (count4 > 0)
        {
          writer4.write(word1 + "\n");
          writer4.write("\n");
          writer4.flush();
        }
      }
    }
    catch (IOException ex)
    {
      Logger.getLogger(IndonesianStemmer.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  public String removeInflectionalSuffix(String word)
  {
    if (word.length() >= 4)
    {
      if (word.endsWith("lah"))
      {
        this.particlesuffix = "lah";
        word = word.substring(0, word.length() - 3);
        if (word.endsWith("ku"))
        {
          this.possessivepronounsuffix = "ku";
          word = word.substring(0, word.length() - 2);
        }
        else if (word.endsWith("mu"))
        {
          this.possessivepronounsuffix = "mu";
          word = word.substring(0, word.length() - 2);
        }
        else if (word.endsWith("nya"))
        {
          this.possessivepronounsuffix = "nya";
          word = word.substring(0, word.length() - 3);
        }
      }
      else if (word.endsWith("kah"))
      {
        this.particlesuffix = "kah";
        word = word.substring(0, word.length() - 3);
        if (word.endsWith("ku"))
        {
          this.possessivepronounsuffix = "ku";
          word = word.substring(0, word.length() - 2);
        }
        else if (word.endsWith("mu"))
        {
          this.possessivepronounsuffix = "mu";
          word = word.substring(0, word.length() - 2);
        }
        else if (word.endsWith("nya"))
        {
          this.possessivepronounsuffix = "nya";
          word = word.substring(0, word.length() - 3);
        }
      }
      else if (word.endsWith("pun"))
      {
        this.particlesuffix = "pun";
        word = word.substring(0, word.length() - 3);
        if (word.endsWith("ku"))
        {
          this.possessivepronounsuffix = "ku";
          word = word.substring(0, word.length() - 2);
        }
        else if (word.endsWith("mu"))
        {
          this.possessivepronounsuffix = "mu";
          word = word.substring(0, word.length() - 2);
        }
        else if (word.endsWith("nya"))
        {
          this.possessivepronounsuffix = "nya";
          word = word.substring(0, word.length() - 3);
        }
      }
      else if (word.endsWith("ku"))
      {
        this.possessivepronounsuffix = "ku";
        word = word.substring(0, word.length() - 2);
      }
      else if (word.endsWith("mu"))
      {
        this.possessivepronounsuffix = "mu";
        word = word.substring(0, word.length() - 2);
      }
      else if (word.endsWith("nya"))
      {
        this.possessivepronounsuffix = "nya";
        word = word.substring(0, word.length() - 3);
      }
      return word;
    }
    return word;
  }
  
  public String removeDerivationalSuffix(String word)
  {
    String initial = new String();
    initial = word;
    if (word.endsWith("an"))
    {
      this.derivationalsuffix = "an";
      word = word.substring(0, word.length() - 2);
      
      String stemmedword = removeDerivationalPrefix(word, 0);
      if (!word.equals(stemmedword)) {
        return stemmedword;
      }
      if (word.endsWith("k"))
      {
        this.derivationalsuffix = "kan";
        word = word.substring(0, word.length() - 1);
        if (isBasicWord(word)) {
          return word;
        }
        stemmedword = removeDerivationalPrefix(word, 0);
        if (!word.equals(stemmedword)) {
          return stemmedword;
        }
        this.derivationalsuffix = "";
        return initial;
      }
      if (isBasicWord(word)) {
        return word;
      }
    }
    else if (word.endsWith("i"))
    {
      this.derivationalsuffix = "i";
      word = word.substring(0, word.length() - 1);
      
      String stemmedword = removeDerivationalPrefix(word, 0);
      if (!word.equals(stemmedword)) {
        return stemmedword;
      }
      this.derivationalsuffix = "";
      return initial;
    }
    return initial;
  }
  
  public boolean isValidAffix(String prefix, String suffix)
  {
    if (((prefix.equals("be")) && (suffix.equals("i"))) || ((prefix.equals("di")) && (suffix.equals("an"))) || ((prefix.equals("ke")) && (suffix.equals("i"))) || ((prefix.equals("ke")) && (suffix.equals("kan"))) || ((prefix.equals("me")) && (suffix.equals("an"))) || ((prefix.equals("se")) && (suffix.equals("i"))) || ((prefix.equals("se")) && (suffix.equals("kan"))) || ((prefix.equals("te")) && (suffix.equals("an")))) {
      return false;
    }
    return true;
  }
  
  public boolean isPrefixExist(String prefix)
  {
    return this.derivationalprefix.contains(prefix);
  }
  
  public boolean isPrefixMaximum()
  {
    return this.derivationalprefix.size() >= 3;
  }
  
  public boolean isVocal(char c)
  {
    if ((c == 'a') || (c == 'i') || (c == 'u') || (c == 'e') || (c == 'o')) {
      return true;
    }
    return false;
  }
  
  public String processPrefix_Te(String word)
  {
    String prefix = "none";
    try
    {
      if (word.charAt(2) == 'r')
      {
        if (word.charAt(3) == 'r')
        {
          prefix = "none";
        }
        else if (isVocal(word.charAt(3)))
        {
          String temp_te = word.substring(2, word.length());
          String temp_ter = word.substring(3, word.length());
          if ((isBasicWord(temp_ter)) && (isBasicWord(temp_te)))
          {
            if (this.closewords_r.contains(temp_ter)) {
              prefix = "ter";
            } else if (this.closewords_r.contains(temp_te)) {
              prefix = "te";
            } else {
              prefix = "ter";
            }
          }
          else if (isBasicWord(temp_ter)) {
            prefix = "ter";
          } else if (isBasicWord(temp_te)) {
            prefix = "te";
          } else {
            prefix = "ter";
          }
        }
        else if ((word.charAt(4) == 'e') && (word.charAt(5) == 'r'))
        {
          if (isVocal(word.charAt(6))) {
            prefix = "ter";
          } else {
            prefix = "ter";
          }
        }
        else
        {
          prefix = "ter";
        }
      }
      else if (isVocal(word.charAt(2))) {
        prefix = "none";
      } else if ((word.charAt(3) == 'e') && (word.charAt(4) == 'r'))
      {
        if (isVocal(word.charAt(5))) {
          prefix = "none";
        } else {
          prefix = "te";
        }
      }
      else {
        prefix = "none";
      }
    }
    catch (StringIndexOutOfBoundsException ex)
    {
      return "none";
    }
    return prefix;
  }
  
  public String processPrefix_Be(String word)
  {
    String prefix = "none";
    try
    {
      if (word.charAt(2) == 'r')
      {
        if (word.charAt(3) == 'r')
        {
          prefix = "none";
        }
        else if (isVocal(word.charAt(3)))
        {
          String temp_be = word.substring(2, word.length());
          String temp_ber = word.substring(3, word.length());
          if ((isBasicWord(temp_ber)) && (isBasicWord(temp_be)))
          {
            if (this.closewords_r.contains(temp_ber)) {
              prefix = "ber";
            } else if (this.closewords_r.contains(temp_be)) {
              prefix = "be";
            } else {
              prefix = "ber";
            }
          }
          else if (isBasicWord(temp_ber)) {
            prefix = "ber";
          } else if (isBasicWord(temp_be)) {
            prefix = "be";
          } else {
            prefix = "ber";
          }
        }
        else if ((word.charAt(5) == 'e') && (word.charAt(6) == 'r') && (isVocal(word.charAt(7))))
        {
          if (isVocal(word.charAt(6))) {
            prefix = "ber";
          } else {
            prefix = "none";
          }
        }
        else if ((word.charAt(5) != 'e') || (word.charAt(5) != 'r'))
        {
          prefix = "ber";
        }
        else
        {
          prefix = "none";
        }
      }
      else if (isVocal(word.charAt(2))) {
        prefix = "none";
      } else if (word.equals("belajar")) {
        prefix = "bel";
      } else if ((word.charAt(2) != 'l') && (word.charAt(3) == 'e') && (word.charAt(4) == 'r') && (word.charAt(5) != 'l'))
      {
        if (isVocal(word.charAt(5))) {
          prefix = "none";
        } else {
          prefix = "be";
        }
      }
      else {
        prefix = "none";
      }
    }
    catch (StringIndexOutOfBoundsException ex)
    {
      return "none";
    }
    return prefix;
  }
  
  public String processPrefix_Pe(String word)
  {
    String prefix = "none";
    try
    {
      if (word.charAt(2) == 'r')
      {
        if (word.charAt(3) == 'r')
        {
          prefix = "none";
        }
        else if (isVocal(word.charAt(3)))
        {
          String temp_pe = word.substring(2, word.length());
          String temp_per = word.substring(3, word.length());
          if ((isBasicWord(temp_per)) && (isBasicWord(temp_pe)))
          {
            if (this.closewords_r.contains(temp_pe)) {
              prefix = "pe";
            } else if (this.closewords_r.contains(temp_per)) {
              prefix = "per";
            } else {
              prefix = "pe";
            }
          }
          else if (isBasicWord(temp_per)) {
            prefix = "per";
          } else if (isBasicWord(temp_pe)) {
            prefix = "pe";
          } else {
            prefix = "pe";
          }
        }
        else if ((word.charAt(5) == 'e') && (word.charAt(6) == 'r') && (isVocal(word.charAt(7))))
        {
          if (isVocal(word.charAt(6))) {
            prefix = "per";
          } else {
            prefix = "none";
          }
        }
        else if ((word.charAt(5) != 'e') || (word.charAt(5) != 'r'))
        {
          prefix = "per";
        }
        else
        {
          prefix = "none";
        }
      }
      else if (isVocal(word.charAt(2)))
      {
        prefix = "none";
      }
      else
      {
        if (word.startsWith("pel")) {
          if (word.charAt(3) == 'l')
          {
            if (word.equals("pelajar")) {
              prefix = "pel";
            } else {
              prefix = "pe";
            }
          }
          else {
            prefix = "none";
          }
        }
        if ((word.charAt(2) == 'l') && (isVocal(word.charAt(3))))
        {
          if (word.equals("pelajar")) {
            prefix = "pel";
          } else {
            prefix = "pe";
          }
        }
        else if (word.startsWith("peny"))
        {
          String temp_s = "s" + word.substring(4, word.length());
          String temp_se = temp_s.substring(2, temp_s.length());
          String temp_ny = word.substring(2, word.length());
          if (isBasicWord(temp_ny)) {
            prefix = "pe";
          } else if (isBasicWord(temp_s)) {
            prefix = "peny-s";
          } else if (isBasicWord(temp_se)) {
            prefix = "peny-s";
          } else {
            prefix = "none";
          }
        }
        else if (word.startsWith("peng"))
        {
          String temp = word.substring(4, word.length());
          String temp_k = "k" + word.substring(4, word.length());
          String temp_ng = word.substring(2, word.length());
          if (isBasicWord(temp_ng)) {
            prefix = "pe";
          } else if ((isBasicWord(temp)) && (isBasicWord(temp_k)))
          {
            if (this.closewords_k.contains(temp)) {
              prefix = "peng";
            } else if (this.closewords_k.contains(temp_k)) {
              prefix = "peng-k";
            } else {
              prefix = "peng";
            }
          }
          else if (isBasicWord(temp_k)) {
            prefix = "peng-k";
          } else if ((isBasicWord(temp)) && ((temp.charAt(0) == 'a') || (temp.charAt(0) == 'i') || (temp.charAt(0) == 'u') || (temp.charAt(0) == 'e') || (temp.charAt(0) == 'o') || (temp.charAt(0) == 'g') || (temp.charAt(0) == 'h') || (temp.charAt(0) == 'k') || (temp.charAt(0) == 'q'))) {
            prefix = "peng";
          } else if (temp.charAt(0) == 'e') {
            prefix = "penge";
          } else {
            prefix = "none";
          }
        }
        else if (word.startsWith("pem"))
        {
          String temp = word.substring(3, word.length());
          String temp_p = "p" + word.substring(3, word.length());
          String temp_m = word.substring(2, word.length());
          if ((isBasicWord(temp_m)) && (isBasicWord(temp_p)))
          {
            if (this.closewords_p.contains(temp_m)) {
              prefix = "pe";
            } else if (this.closewords_p.contains(temp_p)) {
              prefix = "pem-p";
            } else {
              prefix = "pe";
            }
          }
          else if (isBasicWord(temp_m)) {
            prefix = "pe";
          } else if (isBasicWord(temp_p)) {
            prefix = "pem-p";
          } else if ((isBasicWord(temp)) && ((temp.charAt(0) == 'b') || (temp.charAt(0) == 'f') || (temp.charAt(0) == 'p') || (temp.charAt(0) == 'v'))) {
            prefix = "pem";
          } else {
            prefix = "none";
          }
        }
        else if (word.startsWith("pen"))
        {
          String temp = word.substring(3, word.length());
          String temp_t = "t" + word.substring(3, word.length());
          String temp_n = word.substring(2, word.length());
          if ((isBasicWord(temp_n)) && (isBasicWord(temp_t)))
          {
            if (this.closewords_t.contains(temp_n)) {
              prefix = "pe";
            } else if (this.closewords_t.contains(temp_t)) {
              prefix = "pen-t";
            } else {
              prefix = "pe";
            }
          }
          else if (isBasicWord(temp_n)) {
            prefix = "pe";
          } else if (isBasicWord(temp_t)) {
            prefix = "pen-t";
          } else if ((isBasicWord(temp)) && ((temp.charAt(0) == 'c') || (temp.charAt(0) == 'd') || (temp.charAt(0) == 'j') || (temp.charAt(0) == 'z') || (temp.charAt(0) == 's') || (temp.charAt(0) == 't'))) {
            prefix = "pen";
          } else {
            prefix = "none";
          }
        }
        else if (word.startsWith("pe"))
        {
          if ((word.charAt(2) == 'w') || (word.charAt(2) == 'y'))
          {
            if (isVocal(word.charAt(3))) {
              prefix = "pe";
            } else {
              prefix = "none";
            }
          }
          else if ((!isVocal(word.charAt(2))) && (word.charAt(3) == 'e') && (word.charAt(4) == 'r') && (isVocal(word.charAt(5)))) {
            prefix = "pe";
          } else if ((!isVocal(word.charAt(2))) && ((word.charAt(3) != 'e') || (word.charAt(4) != 'r'))) {
            prefix = "pe";
          } else if ((!isVocal(word.charAt(2))) && ((word.charAt(3) == 'e') || (word.charAt(4) == 'r')) && (!isVocal(word.charAt(5)))) {
            prefix = "pe";
          } else {
            prefix = "none";
          }
        }
        else
        {
          prefix = "none";
        }
      }
    }
    catch (StringIndexOutOfBoundsException ex)
    {
      return "none";
    }
    return prefix;
  }
  
  public String processPrefix_Me(String word)
  {
    String prefix = "none";
    try
    {
      if (word.startsWith("meny"))
      {
        String temp_s = "s" + word.substring(4, word.length());
        String temp_se = temp_s.substring(2, temp_s.length());
        String temp_ny = word.substring(2, word.length());
        if (isBasicWord(temp_ny)) {
          prefix = "me";
        } else if (isBasicWord(temp_s)) {
          prefix = "meny-s";
        } else if (isBasicWord(temp_se)) {
          prefix = "meny-s";
        } else {
          prefix = "none";
        }
      }
      else if (word.startsWith("meng"))
      {
        String temp = word.substring(4, word.length());
        String temp_k = "k" + word.substring(4, word.length());
        String temp_ng = word.substring(2, word.length());
        if (isBasicWord(temp_ng)) {
          prefix = "me";
        } else if ((isBasicWord(temp)) && (isBasicWord(temp_k)))
        {
          if (this.closewords_k.contains(temp)) {
            prefix = "meng";
          } else if (this.closewords_k.contains(temp_k)) {
            prefix = "meng-k";
          } else {
            prefix = "meng";
          }
        }
        else if (isBasicWord(temp_k)) {
          prefix = "meng-k";
        } else if ((isBasicWord(temp)) && ((temp.charAt(0) == 'a') || (temp.charAt(0) == 'i') || (temp.charAt(0) == 'u') || (temp.charAt(0) == 'e') || (temp.charAt(0) == 'o') || (temp.charAt(0) == 'g') || (temp.charAt(0) == 'h') || (temp.charAt(0) == 'k') || (temp.charAt(0) == 'q'))) {
          prefix = "meng";
        } else if (temp.charAt(0) == 'e') {
          prefix = "menge";
        } else {
          prefix = "none";
        }
      }
      else if (word.startsWith("mem"))
      {
        String temp = word.substring(3, word.length());
        String temp_p = "p" + word.substring(3, word.length());
        String temp_m = word.substring(2, word.length());
        if ((isBasicWord(temp_m)) && (isBasicWord(temp_p)))
        {
          if (this.closewords_p.contains(temp_m)) {
            prefix = "me";
          } else if (this.closewords_p.contains(temp_p)) {
            prefix = "mem-p";
          } else {
            prefix = "me";
          }
        }
        else if (isBasicWord(temp_m)) {
          prefix = "me";
        } else if (isBasicWord(temp_p)) {
          prefix = "mem-p";
        } else if (processPrefix_Pe(temp).equals("per")) {
          prefix = "mem";
        } else if (processPrefix_Pe(temp).equals("pel")) {
          prefix = "mem";
        } else if ((isBasicWord(temp)) && ((temp.charAt(0) == 'b') || (temp.charAt(0) == 'f') || (temp.charAt(0) == 'p') || (temp.charAt(0) == 'v'))) {
          prefix = "mem";
        } else {
          prefix = "me";
        }
      }
      else if (word.startsWith("men"))
      {
        String temp = word.substring(3, word.length());
        String temp_t = "t" + word.substring(3, word.length());
        String temp_n = word.substring(2, word.length());
        if ((isBasicWord(temp_n)) && (isBasicWord(temp_t)))
        {
          if (this.closewords_t.contains(temp_n)) {
            prefix = "me";
          } else if (this.closewords_t.contains(temp_t)) {
            prefix = "men-t";
          } else {
            prefix = "me";
          }
        }
        else if (isBasicWord(temp_n)) {
          prefix = "me";
        } else if (isBasicWord(temp_t)) {
          prefix = "men-t";
        } else if ((isBasicWord(temp)) && ((temp.charAt(0) == 'c') || (temp.charAt(0) == 'd') || (temp.charAt(0) == 'j') || (temp.charAt(0) == 'z') || (temp.charAt(0) == 's') || (temp.charAt(0) == 't'))) {
          prefix = "men";
        } else {
          prefix = "me";
        }
      }
      else if (word.startsWith("me"))
      {
        String temp = word.substring(2, word.length());
        if (((temp.charAt(0) == 'l') || (temp.charAt(0) == 'r') || (temp.charAt(0) == 'w') || (temp.charAt(0) == 'y')) && (isVocal(word.charAt(1)))) {
          prefix = "me";
        } else {
          prefix = "none";
        }
      }
      else
      {
        prefix = "none";
      }
    }
    catch (StringIndexOutOfBoundsException ex)
    {
      return "none";
    }
    return prefix;
  }
  
  public String removePrefix(String prefix, String word)
  {
    String finalword = new String();
    finalword = word;
    if (prefix.equals("di")) {
      finalword = word.substring(2, word.length());
    } else if (prefix.equals("ke")) {
      finalword = word.substring(2, word.length());
    } else if (prefix.equals("se")) {
      finalword = word.substring(2, word.length());
    } else if (prefix.equals("te")) {
      finalword = word.substring(2, word.length());
    } else if (prefix.equals("ter")) {
      finalword = word.substring(3, word.length());
    } else if (prefix.equals("be")) {
      finalword = word.substring(2, word.length());
    } else if (prefix.equals("ber")) {
      finalword = word.substring(3, word.length());
    } else if (prefix.equals("bel")) {
      finalword = word.substring(3, word.length());
    } else if (prefix.equals("pe")) {
      finalword = word.substring(2, word.length());
    } else if (prefix.equals("per")) {
      finalword = word.substring(3, word.length());
    } else if (prefix.equals("pel")) {
      finalword = word.substring(3, word.length());
    } else if (prefix.equals("peny-s")) {
      finalword = "s" + word.substring(4, word.length());
    } else if (prefix.equals("peng")) {
      finalword = word.substring(4, word.length());
    } else if (prefix.equals("penge")) {
      finalword = word.substring(5, word.length());
    } else if (prefix.equals("peng-k")) {
      finalword = "k" + word.substring(4, word.length());
    } else if (prefix.equals("pem")) {
      finalword = word.substring(3, word.length());
    } else if (prefix.equals("pem-p")) {
      finalword = "p" + word.substring(3, word.length());
    } else if (prefix.equals("pen")) {
      finalword = word.substring(3, word.length());
    } else if (prefix.equals("pen-t")) {
      finalword = "t" + word.substring(3, word.length());
    } else if (prefix.equals("me")) {
      finalword = word.substring(2, word.length());
    } else if (prefix.equals("meny-s")) {
      finalword = "s" + word.substring(4, word.length());
    } else if (prefix.equals("meng")) {
      finalword = word.substring(4, word.length());
    } else if (prefix.equals("menge")) {
      finalword = word.substring(5, word.length());
    } else if (prefix.equals("meng-k")) {
      finalword = "k" + word.substring(4, word.length());
    } else if (prefix.equals("mem")) {
      finalword = word.substring(3, word.length());
    } else if (prefix.equals("mem-p")) {
      finalword = "p" + word.substring(3, word.length());
    } else if (prefix.equals("men")) {
      finalword = word.substring(3, word.length());
    } else if (prefix.equals("men-t")) {
      finalword = "t" + word.substring(3, word.length());
    }
    return finalword;
  }
  
  public boolean isPrefixBetter(String word)
  {
    if (((word.startsWith("ber")) && (word.endsWith("lah"))) || ((word.startsWith("ber")) && (word.endsWith("an"))) || ((word.startsWith("me")) && (word.endsWith("i"))) || ((word.startsWith("me")) && (word.endsWith("kan"))) || ((word.startsWith("di")) && (word.endsWith("i"))) || ((word.startsWith("ter")) && (word.endsWith("i"))) || ((word.startsWith("pe")) && (word.endsWith("i")))) {
      return true;
    }
    return false;
  }
  
  public String removeDerivationalPrefix(String word, int count)
  {
    String initial = new String();
    initial = word;
    
    String removedword = new String();
    
    String prefixType = null;
    String removedPrefix = null;
    if (word.startsWith("di"))
    {
      prefixType = "di";
      removedPrefix = "di";
    }
    else if (word.startsWith("ke"))
    {
      prefixType = "ke";
      removedPrefix = "ke";
    }
    else if (word.startsWith("se"))
    {
      prefixType = "se";
      removedPrefix = "se";
    }
    else if (word.startsWith("me"))
    {
      removedPrefix = processPrefix_Me(word);
      if (!removedPrefix.equals("none")) {
        prefixType = "me";
      } else {
        prefixType = "none";
      }
    }
    else if (word.startsWith("pe"))
    {
      removedPrefix = processPrefix_Pe(word);
      if (!removedPrefix.equals("none"))
      {
        if (removedPrefix.equals("per")) {
          prefixType = "per";
        } else {
          prefixType = "pe";
        }
      }
      else {
        prefixType = "none";
      }
    }
    else if (word.startsWith("te"))
    {
      removedPrefix = processPrefix_Te(word);
      if (!removedPrefix.equals("none")) {
        prefixType = "te";
      } else {
        prefixType = "none";
      }
    }
    else if (word.startsWith("be"))
    {
      removedPrefix = processPrefix_Be(word);
      if (!removedPrefix.equals("none")) {
        prefixType = "be";
      } else {
        prefixType = "none";
      }
    }
    else
    {
      return initial;
    }
    if (prefixType.equals("none")) {
      return initial;
    }
    if ((this.derivationalsuffix.length() > 0) && (count == 0) && 
      (!isValidAffix(prefixType, this.derivationalsuffix))) {
      return initial;
    }
    if (isPrefixExist(prefixType)) {
      return initial;
    }
    if (isPrefixMaximum()) {
      return initial;
    }
    removedword = removePrefix(removedPrefix, word);
    if (isBasicWord(removedword))
    {
      this.derivationalprefix.add(prefixType);
      return removedword;
    }
    count += 1;
    String rec = removeDerivationalPrefix(removedword, count);
    if (isBasicWord(rec))
    {
      this.derivationalprefix.add(0, prefixType);
      return rec;
    }
    return word;
  }
  
  public String stem(String word)
  {
    if (word.matches("[a-zA-Z]{1,}-[a-zA-Z]{1,}")) {
      return stemRepeatedWord(word);
    }
    String initial = word;
    
    word = word.toLowerCase();
    
    this.particlesuffix = "";
    this.possessivepronounsuffix = "";
    this.derivationalsuffix = "";
    this.derivationalprefix.clear();
    if (word.length() <= 3) {
      return word;
    }
    if (isBasicWord(word)) {
      return word;
    }
    if (isPrefixBetter(word))
    {
      String temp = removeDerivationalPrefix(word, 0);
      if (isBasicWord(temp)) {
        return temp;
      }
    }
    word = removeInflectionalSuffix(word);
    if (isBasicWord(word)) {
      return word;
    }
    word = removeDerivationalSuffix(word);
    if (isBasicWord(word)) {
      return word;
    }
    if (this.derivationalsuffix.length() == 0)
    {
      word = removeDerivationalPrefix(word, 0);
      if (isBasicWord(word)) {
        return word;
      }
    }
    return initial;
  }
  
  public String stemRepeatedWord(String word)
  {
    if (word.matches("[a-zA-Z]{1,}-[a-zA-Z]{1,}"))
    {
      String[] words = word.split("-");
      if (words[0].equals(words[1])) {
        return words[0];
      }
      String stemmedLeft = stem(words[0]);
      String stemmedRight = stem(words[1]);
      if (stemmedLeft.equals(stemmedRight)) {
        return stemmedLeft;
      }
      return word;
    }
    return word;
  }
  
  public String stemSentence(String sentence)
  {
    String[] words = sentence.replaceAll("[\\W&&[^-]&&[^\\s]]", "").split("\\s+");
    String stemmedSentence = "";
    for (int i = 0; i < words.length; i++)
    {
      String stemmedWord = stem(words[i]);
      stemmedSentence = stemmedSentence + stemmedWord + " ";
    }
    return stemmedSentence.trim();
  }
  
  public static void main(String[] args)
  {
    IndonesianStemmer stemmer = new IndonesianStemmer();
    
    String word = "diperbantukan";
    
    System.out.println("Basic word : " + stemmer.stem(word));
    for (int i = 0; i < stemmer.derivationalprefix.size(); i++) {
      System.out.println("Derivational Prefix : " + (String)stemmer.derivationalprefix.get(i));
    }
    System.out.println("Particle Suffix : " + stemmer.particlesuffix);
    
    System.out.println("Possessive Pronoun Suffix : " + stemmer.possessivepronounsuffix);
    
    System.out.println("Derivational Suffix : " + stemmer.derivationalsuffix);
  }
}

