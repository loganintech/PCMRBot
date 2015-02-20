/*	  It's a Twitch bot, because we can.
 *    Copyright (C) 2015  Logan Ssaso, James Wolff, Angablade
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package me.jewsofhazzard.pcmrbot.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TFileWriter {
  static final Logger logger = Logger.getLogger(TFileWriter.class+"");
  
  public static void writeFile(File f, ArrayList<String> strings) {
    if (!f.exists()) {
      try
      {
        f.createNewFile();
      }
      catch (IOException e)
      {
        logger.log(Level.SEVERE, "Error writing the file at location: " + f.getName() + "\n" + e.toString());
      }
    } else {
      strings.addAll(0, TFileReader.readFile(f));
    }
    try
    {
      BufferedWriter writer = Files.newBufferedWriter(f.toPath(), StandardCharsets.UTF_8, new OpenOption[0]);
      for (String s : strings)
      {
        writer.write(s);
        writer.newLine();
      }
      writer.close();
    }
    catch (IOException e)
    {
      logger.log(Level.SEVERE, "Error writing the file at location: " + f.getName() + "\n" + e.toString());
    }
  }
  
  public static void writeFile(File f, String... output) {
    ArrayList<String> strings = new ArrayList<>();
    if (!f.exists()) {
      try
      {
        f.createNewFile();
      }
      catch (IOException e)
      {
        logger.log(Level.SEVERE, "Error writing the file at location: " + f.getName() + "\n" + e.toString());
      }
    } else {
      strings = TFileReader.readFile(f);
    }
    for(String s:output){
    	strings.add(s);
    }
    try
    {
      BufferedWriter writer = Files.newBufferedWriter(f.toPath(), StandardCharsets.UTF_8, new OpenOption[0]);
      for (String s : strings)
      {
        writer.write(s);
        writer.newLine();
      }
      writer.close();
    }
    catch (IOException e)
    {
      logger.log(Level.SEVERE, "Error writing the file at location: " + f.getName() + "\n" + e.toString(), e);
    }
  }
  
  public static void overWriteFile(File f, ArrayList<String> strings) {
    if (f.exists()) {
      f.delete();
    }
    try
    {
      f.createNewFile();
    }
    catch (IOException e)
    {
      logger.log(Level.SEVERE, "Error writing the file at location: " + f.getName() + "\n" + e.toString(), e);
    }
    try
    {
      BufferedWriter writer = Files.newBufferedWriter(f.toPath(), StandardCharsets.UTF_8, new OpenOption[0]);
      for (String s : strings)
      {
        writer.write(s);
        writer.newLine();
      }
      writer.close();
    }
    catch (IOException e)
    {
      logger.log(Level.SEVERE, "Error writing the file at location: " + f.getName() + "\n" + e.toString(), e);
    }
  }
  
  public static void overWriteFile(File f, String output) {
    if (f.exists()) {
      f.delete();
    }
    try
    {
      f.createNewFile();
    }
    catch (IOException e)
    {
      logger.log(Level.SEVERE, "Error writing the file at location: " + f.getName() + "\n" + e.toString(), e);
    }
    try
    {
      BufferedWriter writer = Files.newBufferedWriter(f.toPath(), StandardCharsets.UTF_8, new OpenOption[0]);
      writer.write(output);
      writer.newLine();
      writer.close();
    }
    catch (IOException e)
    {
      logger.log(Level.SEVERE, "Error writing the file at location: " + f.getName() + "\n" + e.toString(), e);
    }
  }
}