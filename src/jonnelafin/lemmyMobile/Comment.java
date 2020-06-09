/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jonnelafin.lemmyMobile;

import com.codename1.charts.util.ColorUtil;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.URLImage;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.plaf.Style;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import static jonnelafin.lemmyMobile.LemmyMain.cardCount;
import static jonnelafin.lemmyMobile.LemmyMain.commentView;
import static jonnelafin.lemmyMobile.LemmyMain.curr;

/**
 *
 * @author Elias Eskelinen <elias.eskelinen@protonmail.com>
 */
public class Comment extends Container{
    LinkedHashMap raw;
    Label creator_avatar;
    String creator_name;
    String content;
    int parent;
    int id;
    int upvotes;
    int downvotes;
    int rank;
    
    public LinkedList<Comment> children = new LinkedList<>();
    

    public Comment(LinkedHashMap raw) {
        this.raw = raw;
        Layout l = new BorderLayout();
        this.setLayout(l);
        Container c = this;
        c.setWidth(commentView.getWidth());
        
        creator_name = (String) raw.get("creator_name");
        content = (String) raw.get("content");
        
        parent = -1;
        try {
            parent = (int) Math.round((double) raw.get("parent_id"));
        } catch (Exception e) {
        }
        id = -1;
        try {
            id = (int) Math.round((double) raw.get("id"));
        } catch (Exception e) {
        }
        rank = -1;
        try {
            rank = (int) Math.round((double) raw.get("score"));
        } catch (Exception e) {
        }
        upvotes = -1;
        try {
            rank = (int) Math.round((double) raw.get("upvotes"));
        } catch (Exception e) {
        }
        downvotes = -1;
        try {
            rank = (int) Math.round((double) raw.get("downvotes"));
        } catch (Exception e) {
        }
        
        
        
        c.setUIID(id + "");
        c.setName(rank + "");
        if(parent != -1){
            c.setUIID(parent + "_" + id);
        }
        c.setFocusable(true);
        //c.setDraggable(true);
        //c.setHeight(1050);
        c.setTactileTouch(true);
        c.setRippleEffect(true);
        curr = raw;
        c.addPointerReleasedListener((evt) -> {
            //showPost(raw);
        });
        
        int bg = ColorUtil.rgb(0, 0, 0);
        if(true){
            bg = ColorUtil.rgb(25, 25, 25);
        }
        int fg = ColorUtil.rgb(255, 255, 255);
        Style card_style = new Style(fg, bg, Font.getDefaultFont(), Byte.MAX_VALUE);
        c.setSelectedStyle(card_style);
        c.setUnselectedStyle(card_style);
        
        creator_avatar = new Label("");
        creator_avatar.setSelectedStyle(card_style);
        creator_avatar.setUnselectedStyle(card_style);
            String imgUrl = (String) raw.get("creator_avatar");
            //System.out.println(imgUrl);
            try {
                EncodedImage p = EncodedImage.createFromImage(Image.createImage(100, 100, 0xffff0000), true);
                URLImage img = URLImage.createToStorage(p, "/comment_" + cardCount + "profile", imgUrl);
                creator_avatar.setIcon(img);
            } catch (Exception e) {
                FontImage.setMaterialIcon(creator_avatar, FontImage.MATERIAL_PERSON);
            }
        //c.add(BorderLayout.WEST, new SpanLabel(FontImage.MATERIAL_LINK+""));
        creator_avatar.setSize(new Dimension(300, 300));
        c.add(BorderLayout.WEST, creator_avatar);
        
        Label owner = new Label(creator_name);
        owner.setUnselectedStyle(card_style);
        owner.setSelectedStyle(card_style);
        c.add(BorderLayout.NORTH, owner);
        
        Style card_style2 = new Style(fg, bg, Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM), Byte.MAX_VALUE);
        TextArea name = new TextArea(content);
        name.setUnselectedStyle(card_style2);
        name.setSelectedStyle(card_style2);
        c.add(BorderLayout.CENTER, name);
        
        c.add(BorderLayout.EAST, new Label("                    "));
    }
    
}
