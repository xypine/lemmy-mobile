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
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.util.regex.RE;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author Elias Eskelinen (elias.eskelinen@protonmail.com)
 */
public class Parser {
    
    //This was a bad idea.
    //[Later] Well, it seems as the browser doesn't work too well. I'll try this again.
    
    public static ArrayList<String> getNames(String line){
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> links = new ArrayList<>();
        
        RE r = new RE("(?:__|[*#])|\\[(.*?)\\]\\(.*?\\)");
        boolean d = r.match(line);
        int i = 0;
        while(d){
            String rid = r.getParen(0);
            String name = r.getParen(1);
            String link = rid.substring(name.length()+3, rid.length()-1);
            links.add(link);
            names.add(name);
            int where = line.indexOf(rid);
            line = line.substring(0, where) + "[" + i + "]" + line.substring(where+rid.length());
            i++;
            d = r.match(line);
        }
        return names;
    }
    public static ArrayList<String> getLinks(String line){
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> links = new ArrayList<>();
        
        RE r = new RE("(?:__|[*#])|\\[(.*?)\\]\\(.*?\\)");
        boolean d = r.match(line);
        int i = 0;
        while(d){
            String rid = r.getParen(0);
            String name = r.getParen(1);
            String link = rid.substring(name.length()+3, rid.length()-1);
            links.add(link);
            names.add(name);
            int where = line.indexOf(rid);
            line = line.substring(0, where) + "[" + i + "]" + line.substring(where+rid.length());
            i++;
            d = r.match(line);
        }
        return links;
    }
    public static String getClean(String line){
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> links = new ArrayList<>();
        
        RE r = new RE("(?:__|[*#])|\\[(.*?)\\]\\(.*?\\)");
        boolean d = r.match(line);
        int i = 0;
        while(d){
            String rid = r.getParen(0);
            String name = r.getParen(1);
            String link = rid.substring(name.length()+3, rid.length()-1);
            links.add(link);
            names.add(name);
            int where = line.indexOf(rid);
            line = line.substring(0, where) + "[" + i + "]" + line.substring(where+rid.length());
            i++;
            d = r.match(line);
        }
        return line;
    }
    
    public static void main(String[] args) {
        String line = "Hi! [Here is a link](https://test.link) see, if it works! \n [here](is another) EOF";
        System.out.println(getNames(line));
        System.out.println(getLinks(line));
    }
    public static Container parse(String post){
        String post2 = post + "\n";
        ArrayList<String> names = getNames(post2);
        ArrayList<String> links = getLinks(post2);
        
        FlowLayout layout = new FlowLayout();
        Container body = new Container(layout);
        
        TextArea posts = new TextArea(getClean(post2));
        body.add(posts);
        //body.setEditable()
        
        body.add(new Label("Links:"));
        int i = 0;
        for(String name : names){
            String link = links.get(i);

            int bg = ColorUtil.rgb(0, 0, 0);
            int fg = ColorUtil.rgb(25, 25, 255);
            Style card_style = new Style(fg, bg, Font.getDefaultFont(), Byte.MAX_VALUE);
            SpanLabel alink = new SpanLabel("   [" + i + "]:"+ name);
            alink.setUnselectedStyle(card_style);
            alink.addPointerReleasedListener((arg0) -> {
                Display.getInstance().execute(link);
            });
            body.add(alink);
            i++;
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