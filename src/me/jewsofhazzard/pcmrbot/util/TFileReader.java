package me.jewsofhazzard.pcmrbot.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TFileReader {
  static Logger logger = Logger.getLogger(TFileReader.class+"");
  
  @SuppressWarnings("resource")
  public static ArrayList<String> readFile(File f) {
    FileInputStream fis = null;
    try
    {
      fis = new FileInputStream(f);
    }
    catch (FileNotFoundException e)
    {
      logger.log(Level.SEVERE, "Error reading the file at location: " + f.getName() + "\n" + e.toString());
    }
    ArrayList<String> buffer = new ArrayList<>();
    Scanner scanner = new Scanner(fis);
    while (scanner.hasNext()) {
      buffer.add(scanner.nextLine());
    }
    return buffer;
  }
}