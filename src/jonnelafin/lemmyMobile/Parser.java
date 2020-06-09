/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jonnelafin.lemmyMobile;

import com.codename1.charts.util.ColorUtil;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import java.util.LinkedList;

/**
 *
 * @author Elias Eskelinen (elias.eskelinen@protonmail.com)
 */
public class Parser {
    
    //This was a bad idea.
    //[Later] Well, it seems as the browser doesn't work too well. I'll try this again.
    
    
    public static void main(String[] args) {
        String line = "Hi! [Here is a link](https://test.link) see, if it works! \n[here](is another) EOF";
        System.out.println(startsWith(line, "####."));
        
        
        
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
                if(pos3 != -1 && pos4 != -1){
                    String url = line.substring(pos3, pos4);
                    //links.add(new link(pos, title, url));
                    after = line.substring(pos4+1);
                    line = before + after;
                }
                else{
                    line = before + after;
                }
            }
        }
        System.out.println(line);
        
    }
    public static Container parse(String post){
        String post2 = post + "\n";
        FlowLayout layout = new FlowLayout();
        Container body = new Container(layout);
        for(String line : splitByDelimiter(post2, "\n")){
            LinkedList<link> links = new LinkedList<>();
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
                    if(pos3 != -1 && pos4 != -1){
                        String url = line.substring(pos3, pos4);
                        links.add(new link(pos, title, url));
                        after = line.substring(pos4+1);
                        line = before + after;
                    }
                    else{
                        line = before + after;
                    }
                }
            }
            
            /*
            //General style
            if(startsWith(line, "#")){
                SpanLabel header = new SpanLabel(op.substring(1));
                int bg = ColorUtil.rgb(0, 0, 0);
                int fg = ColorUtil.rgb(255, 255, 255);
                Style card_style = new Style(fg, bg, Font.create("SYSTEM-PLAIN-LARGE"), Byte.MAX_VALUE);
                header.setTextUnselectedStyle(card_style);
                body.add(header);
            }*/
            int i = 0;
            String buff = "";
            for(char c : line.toCharArray()){
                buff = buff + c;
                boolean isLink = false;
                for(link a : links){
                    if(a.pos == i){
                        isLink = true;
                        SpanLabel txt = new SpanLabel(buff);
                        body.add(txt);
                        buff = "";
                        
                        int bg = ColorUtil.rgb(0, 0, 0);
                        int fg = ColorUtil.rgb(25, 25, 255);
                        Style card_style = new Style(fg, bg, Font.getDefaultFont(), Byte.MAX_VALUE);
                        SpanLabel alink = new SpanLabel(a.title);
                        alink.setUnselectedStyle(card_style);
                        alink.addPointerReleasedListener((arg0) -> {
                            Display.getInstance().execute(a.url);
                        });
                        body.add(alink);
                    }
                }
                i++;
            }
            SpanLabel txt = new SpanLabel(buff);
            body.add(txt);
            /*
            int last = 0;
            int ind = 0;
            for(link i : links){
                boolean isLast = false;
                try {
                    link next = links.get(ind + 1);
                } catch (Exception e) {
                    isLast = true;
                }
                
                String before = line.substring(last, i.pos);
                body.add(new SpanLabel(before));
                int bg = ColorUtil.rgb(0, 0, 0);
                int fg = ColorUtil.rgb(25, 25, 255);
                Style card_style = new Style(fg, bg, Font.getDefaultFont(), Byte.MAX_VALUE);
                SpanLabel alink = new SpanLabel(i.title);
                alink.setUnselectedStyle(card_style);
                alink.addPointerReleasedListener((arg0) -> {
                    Display.getInstance().execute(i.url);
                });
                body.add(alink);
                if(isLast){
                    String next = line.substring(i.pos);
                    body.add(new SpanLabel(next));
                }
                ind++;
            }
            */
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
class link{
    public int pos;
    public String title;
    public String url;

    public link(int pos, String title, String url) {
        this.pos = pos;
        this.title = title;
        this.url = url;
    }
    
}