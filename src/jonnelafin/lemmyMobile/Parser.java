/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jonnelafin.lemmyMobile;

import com.codename1.charts.util.ColorUtil;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Container;
import com.codename1.ui.Font;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;

/**
 *
 * @author Elias Eskelinen (elias.eskelinen@protonmail.com)
 */
public class Parser {
    
    //This was a bad idea.
    
    
    
    public static void main(String[] args) {
        String line = "####.--";
        System.out.println(startsWith(line, "####."));
        
        
        int pos = line.indexOf(".");
        int pos2 = line.indexOf("]");
        String before = line.substring(0, pos);
        String after = line.substring(pos+1);
        line = before + after;
        System.out.println(line);
        
    }
    public static Container parse(String post){
        String post2 = post + "\n";
        FlowLayout layout = new FlowLayout();
        Container body = new Container(layout);
        for(String line : splitByDelimiter(post2, "\n")){
            String op = line + "";
            
            //Links
            while(line.contains("[")){
                int pos = line.indexOf("[");
                int pos2 = line.indexOf("]");
                String before = line.substring(0, pos);
                String after = line.substring(pos+1);
                if(pos2 == -1){
                    line = before + after;
                }
                else{
                    String title = line.substring(pos, pos2);
                    int pos3 = line.indexOf("(");
                    int pos4 = line.indexOf(")");
                }
            }
            
            //General style
            if(startsWith(line, "#")){
                SpanLabel header = new SpanLabel(op.substring(1));
                int bg = ColorUtil.rgb(0, 0, 0);
                int fg = ColorUtil.rgb(255, 255, 255);
                Style card_style = new Style(fg, bg, Font.create("SYSTEM-PLAIN-LARGE"), Byte.MAX_VALUE);
                header.setTextUnselectedStyle(card_style);
                body.add(header);
            }
            
        }
        return body;
    }
    static boolean startsWith(String line, String with){
        int progress = 0;
        for(char i : line.toCharArray()){
            if(i == ' ' || i == '\n'){}
            else if(i == with.charAt(progress)){
                progress++;
                if(progress == with.length()){
                    return true;
                }
            }
            else{
                return false;
            }
        }
        return false;
    }
    
    
    
    public static String[] splitByDelimiter(String fullString, String delimiter){
        // Calculate number of words 
        int index = 0;
        int[] delimiterIndices = new int[fullString.length()];
        int wordCount = 0;
        do{
            if(delimiter.equals(fullString.charAt(index)+"")){
                delimiterIndices[wordCount++] = index;
            }
            index++;
        } while(index < fullString.length());

        // Correction for strings not ending in a delimiter
        if(!fullString.endsWith(delimiter)){
            delimiterIndices[wordCount++] = fullString.length();
        } 

        // Now create the words array
        String words[] = new String[wordCount];
        int startIndex = 0;
        int endIndex = 0;

        for(int i=0; i<wordCount; i++){
            endIndex = delimiterIndices[i];
            words[i] = fullString.substring(startIndex, endIndex);
            startIndex = endIndex+1;            
        }       
        return words;
    }
}
