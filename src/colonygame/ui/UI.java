/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package colonygame.ui;

import colonygame.resources.Sprite;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import SASLib.Geom.Vector;
import colonygame.GameManager;
import colonygame.game.Building;
import colonygame.Main;
import colonygame.uitool.Bulldoze;
import colonygame.uitool.Tool;
import colonygame.resources.BuildingType;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author TOM
 */
public class UI extends javax.swing.JFrame {

    int x, y, z;
    public int RENDER_WIDTH = 4;
    public int RENDER_HEIGHT = 4;
    public static final int MARGIN_X = 10;
    public static final int MARGIN_Y = 35;
    public static final int TEXT_AREA_WIDTH = 180;
    public static final int TEXT_AREA_HEIGHT = 120;
    public static final int MAP_COLOR = 0x00F00000;
    public static final int MAP_COLOR_SELECTED = 0x99ff69b4;
    public static final int BUTTON_COLOR = 0x00FF0000;
    public static final int BUILDMENU_WIDTH = 120;
    public static final int BUILDMENU_HEIGHT = 30;
    public static final int BUTTON_COLOR_TEXT = 0x60c52f;
    public static final int BUTTON_COLOR_BACKGROUND = 0x593104;
    public static final int BACKGROUND_COLOR = 0x00000000;
    public static final int GRIDLINE_COLOR = 0x00d3eec6;
    public static final int POPULATION_COLOR = 0x00ffffff;
    public static final int FOOD_COLOR = 0x429557;
    public static final int POWER_COLOR = 0xfff80b;
    public static final int ORE_COLOR = 0x593104;
    public static final int INFO_BOX_COLOR = 0x6b6b6b;
    public static final int RESOURCE_BOX_COLOR = INFO_BOX_COLOR;
    public static final int BUTTON_HEIGHT = 25;
    public static final int BUTTON_WIDTH_1 = 80;
    public static final int BUTTON_WIDTH_2 = 50;
    public static final int PADDING_DEFAULT = 2;
    private Vector touch = null;
    int[][] hitBoxRef;
    BufferedImage hitBox;
    BufferedImage gridLines;
    int lastColor;
    boolean bResized = true;
    ArrayList<UIButton> buttons;
    ArrayList<Color> buttonsColors;
    int nextButtonColor = BUTTON_COLOR;
    Tool currentTool;
    BuildMenu buildmenu;
    boolean bDebug = false;
    String desc;
    ColonistTracker cTracker;
    EventLog eLog;

    // <editor-fold defaultstate="collapsed" desc=" Constructor ">   
    /**
     * Creates new form UI
     */
    public UI() {
        initComponents();
        lastColor = x = y = z = 0;


        //touch vector
        touch = new Vector();


        hitBox = new BufferedImage(jpCanvas.getWidth(),
                jpCanvas.getHeight(), BufferedImage.TYPE_INT_ARGB);

        gridLines = new BufferedImage(jpCanvas.getWidth(),
                jpCanvas.getHeight(), BufferedImage.TYPE_INT_ARGB);


        bResized = true;


        //BUILD MENU
        buildmenu = new BuildMenu(
                PADDING_DEFAULT,
                PADDING_DEFAULT * 3 + 2 * BUTTON_HEIGHT,
                BUILDMENU_WIDTH, BUILDMENU_HEIGHT);


        //speed controls


        //create buttons

        buttons = new ArrayList<>();
        buttonsColors = new ArrayList<>();



        //make bulldoze button
        final UIButton bull;
        bull = addButton(PADDING_DEFAULT, PADDING_DEFAULT,
                BUTTON_WIDTH_1, BUTTON_HEIGHT, "Bulldoze",
                new Color(BUTTON_COLOR_BACKGROUND),
                new Color(BUTTON_COLOR_TEXT),
                null);

        bull.setActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.ui.setCurrentTool(new Bulldoze(bull));
            }
        });

        //add build button
        final UIButton build;
        build = addButton(PADDING_DEFAULT, PADDING_DEFAULT * 2 + BUTTON_HEIGHT,
                BUTTON_WIDTH_1, BUTTON_HEIGHT, "Build",
                new Color(BUTTON_COLOR_BACKGROUND),
                new Color(BUTTON_COLOR_TEXT),
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.ui.setBuildmenuVisible(!Main.ui.getBuildmenuVisible());
                Main.ui.getBuildmenu().update();
            }
        });

        final UIButton pause;
        pause = addButton(PADDING_DEFAULT * 2 + BUTTON_WIDTH_1, PADDING_DEFAULT,
                BUTTON_WIDTH_2, BUTTON_HEIGHT, "Pause",
                new Color(BUTTON_COLOR_BACKGROUND),
                new Color(BUTTON_COLOR_TEXT),
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.manager.stop();
            }
        });

        final UIButton norm;
        norm = addButton(PADDING_DEFAULT * 3 + BUTTON_WIDTH_1 + BUTTON_WIDTH_2, PADDING_DEFAULT,
                BUTTON_WIDTH_2, BUTTON_HEIGHT, "Normal",
                new Color(BUTTON_COLOR_BACKGROUND),
                new Color(BUTTON_COLOR_TEXT),
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.manager.setDelay(GameManager.normal);
                Main.manager.start();
            }
        });

        final UIButton x2;
        x2 = addButton(PADDING_DEFAULT * 4 + BUTTON_WIDTH_1 + BUTTON_WIDTH_2 * 2, PADDING_DEFAULT,
                BUTTON_WIDTH_2, BUTTON_HEIGHT, "Fast",
                new Color(BUTTON_COLOR_BACKGROUND),
                new Color(BUTTON_COLOR_TEXT),
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.manager.setDelay(GameManager.fast);
                Main.manager.start();
            }
        });

        final UIButton x3;
        x3 = addButton(PADDING_DEFAULT * 5 + BUTTON_WIDTH_1 + BUTTON_WIDTH_2 * 3, PADDING_DEFAULT,
                BUTTON_WIDTH_2, BUTTON_HEIGHT, "Fastest",
                new Color(BUTTON_COLOR_BACKGROUND),
                new Color(BUTTON_COLOR_TEXT),
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.manager.setDelay(GameManager.fastest);
                Main.manager.start();
            }
        });
    }
    // </editor-fold>   

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpCanvas = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(620, 480));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jpCanvas.setBackground(new java.awt.Color(0, 0, 0));
        jpCanvas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jCanvasMouseClicked(evt);
            }
        });
        jpCanvas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jpCanvasLayout = new javax.swing.GroupLayout(jpCanvas);
        jpCanvas.setLayout(jpCanvasLayout);
        jpCanvasLayout.setHorizontalGroup(
            jpCanvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 655, Integer.MAX_VALUE)
        );
        jpCanvasLayout.setVerticalGroup(
            jpCanvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 457, Integer.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jpCanvas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Integer.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jpCanvas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Integer.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b); //To change body of generated methods, choose Tools | Templates.

        renderFrame();
    }

    // <editor-fold defaultstate="collapsed" desc=" Event Listeners ">   
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        if (evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_F12) {
            Main.game.run();
        }
        if (evt.getKeyCode() == KeyEvent.VK_PAUSE) {
            Main.manager.toggle();
        }
        if (evt.getKeyCode() == KeyEvent.VK_F2) {
            cTracker = new ColonistTracker();
            cTracker.setVisible(true);
        }
        if (evt.getKeyCode() == KeyEvent.VK_F3) {
            eLog = new EventLog();
            eLog.setVisible(true);
        }
        
        if (evt.getKeyCode() == KeyEvent.VK_RIGHT) {


            if (x < Main.game.getMap().getWidth() - RENDER_WIDTH) {
                x++;
            }

        }
        if (evt.getKeyCode() == KeyEvent.VK_LEFT) {

            if (x > 0) {
                x--;
            }

        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {


            if (y < Main.game.getMap().getHeight() - 1 - RENDER_HEIGHT) {
                y++;
            }

        }
        if (evt.getKeyCode() == KeyEvent.VK_DOWN) {

            if (y > 0) {
                y--;
            }

        }



    }//GEN-LAST:event_formKeyPressed

    private void jCanvasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCanvasMouseClicked
        //clean up last click
        desc = null;

        //is left or right click?

        if (evt.getButton() == MouseEvent.BUTTON1) {

            //\/\/\/\/\/\/\/\/\/\/\/\/\//
            //   get clicked tile      //
            //\/\/\/\/\/\/\/\/\/\/\/\/\//
            int c = hitBox.getRGB(evt.getX(), evt.getY()) & 0x00ffffff;

            //ensure not a black click
            if (c == 0) {
                //do nothing
            } else if (c == MAP_COLOR) {
                //map click
                Image img = Main.game.getMap().getImage();

                x = evt.getX() - jpCanvas.getWidth() + img.getWidth(null);
                y = evt.getY();
            } else if ((c & 1) == 0) {
                //this is a button probably
                //get button

                for (int i = buttonsColors.size() - 1; i >= 0; i--) {
                    if ((buttonsColors.get(i).getRGB() & 0x00ffffff) == c) {

                        UIButton b = buttons.get(i);
                        //found the button
                        b.click(null);

                        break;
                    }
                }

            } else {
                for (int i = 0; i < hitBoxRef.length; i++) {
                    for (int j = 0; j < hitBoxRef[0].length; j++) {
                        if (c == hitBoxRef[i][j]) {
                            touch = new Vector(i + x, j + y);

                            //CHECK IF A TOOL WAS USED
                            if (currentTool != null) {
                                currentTool.interact(
                                        (int) touch.getX(),
                                        (int) touch.getY(),
                                        z);
                            }
                        }
                    }
                }
            }
        } else {
            //cancel current tool
            if (currentTool != null) {
                currentTool.unselect();
                currentTool = null;
            }


            //if we unselected on a tile

            //\/\/\/\/\/\/\/\/\/\/\/\/\//
            //   get clicked tile      //
            //\/\/\/\/\/\/\/\/\/\/\/\/\//
            int c = hitBox.getRGB(evt.getX(), evt.getY()) & 0x00ffffff;

            for (int i = 0; i < hitBoxRef.length; i++) {
                for (int j = 0; j < hitBoxRef[0].length; j++) {
                    if (c == hitBoxRef[i][j]) {
                        touch = new Vector(i + x, j + y);

                        //add 
                        desc = Main.game.getMap().toString(
                                (int) touch.getX(),
                                (int) touch.getY(),
                                z);
                    }
                }
            }

        }
    }//GEN-LAST:event_jCanvasMouseClicked

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        // TODO add your handling code here:
        jpCanvas.setBounds(0, 0,
                evt.getComponent().getWidth() - MARGIN_X,
                evt.getComponent().getHeight() - MARGIN_Y);

        bResized = true;
    }//GEN-LAST:event_formComponentResized
    // </editor-fold>   

    //<editor-fold defaultstate="collapsed" desc=" Main ">
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new UI().setVisible(true);
            }
        });
    }
    //</editor-fold>
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jpCanvas;
    // End of variables declaration//GEN-END:variables

    //<editor-fold defaultstate="collapsed" desc=" Main Render Block ">
    /**
     * get the current map cell and render it to the appropriate frame
     */
    public synchronized void renderFrame() {

        //
        if(cTracker!=null)
            cTracker.renderFrame();
        
        //
        if(eLog!=null){
            eLog.renderFrame();
        }
        
        //Sprite
        Sprite tile = Main.game.getMap().getWorld().getTile();
        Sprite temp;
        BuildingType tType;


        //calc size to pull
        //    |margin y
        //  / |
        //- - -
        //X
        // 
        RENDER_WIDTH = (jpCanvas.getBounds().width - 2 * MARGIN_X)
                / tile.getCellWidth();
        RENDER_HEIGHT = (jpCanvas.getBounds().height - 2 * MARGIN_Y)
                / tile.getCellHeight();

        RENDER_WIDTH = RENDER_HEIGHT = (int) Math.sqrt(RENDER_WIDTH * RENDER_HEIGHT);

        //get map subset to render
        char[][] mapBlock = Main.game.getMap().getBlock(x, y, z,
                RENDER_WIDTH, RENDER_HEIGHT);

        Building[][] buildingBlock = Main.game.getMap().getBlockBuildings(
                x, y, z, RENDER_WIDTH, RENDER_HEIGHT);







        BufferedImage tempImg = new BufferedImage(jpCanvas.getWidth(),
                jpCanvas.getHeight(), BufferedImage.TYPE_INT_ARGB);


        //create all the graphics we need
        //g for generic/the canvas
        Graphics gFinal = jpCanvas.getGraphics();
        Graphics g = tempImg.createGraphics();

        //gHit for for mouse click box, update on resize
        Graphics gHit = null;

        //gGrid for grid lines over tiles, update on resize
        Graphics gGrid = null;

        //
        g.setColor(new Color(BACKGROUND_COLOR));
        g.fillRect(0, 0,
                jpCanvas.getWidth(),
                jpCanvas.getHeight());

        if (bResized) {

            //create image size as needed
            hitBox = new BufferedImage(jpCanvas.getWidth(),
                    jpCanvas.getHeight(), BufferedImage.TYPE_INT_ARGB);
            gridLines = new BufferedImage(jpCanvas.getWidth(),
                    jpCanvas.getHeight(), BufferedImage.TYPE_INT_ARGB);

            //get graphis
            gHit = hitBox.createGraphics();
            gGrid = gridLines.createGraphics();

            //create storage
            hitBoxRef = new int[RENDER_WIDTH][RENDER_HEIGHT];
        }


        //clear box
        //g.setColor(Color.BLACK);
        //g.drawRect(0, 0, jpCanvas.getWidth(), jpCanvas.getHeight());


        //the diamond approach
        //http://stackoverflow.com/questions/892811/drawing-isometric-game-worlds
        int cellx, celly;

        //draw tiles
        for (int i = 0; i < RENDER_WIDTH; i++) {

            for (int j = RENDER_HEIGHT - 1; j >= 0; j--) {
                // Changed loop condition here.
                cellx = (j * tile.getCellWidth() / 2)
                        + (i * tile.getCellWidth() / 2)
                        + MARGIN_X;
                celly = (i * tile.getCellHeight() / 2)
                        - (j * tile.getCellHeight() / 2)
                        + jpCanvas.getHeight() / 2
                        + MARGIN_Y;


                g.drawImage(tile.getCell(mapBlock[i][j]),
                        cellx,
                        celly, null);
                



                if (bResized) {
                    //debug
//                g.setColor(new Color(hitBoxRef[i][j]));               
//                g.fillPolygon(xPoints, yPoints, 4);

                    //draw poly
                    int[] xPoints, yPoints;
                    xPoints = new int[]{0 + cellx,
                        tile.getCellWidth() / 2 + cellx,
                        tile.getCellWidth() + cellx,
                        tile.getCellWidth() / 2 + cellx};
                    yPoints = new int[]{
                        tile.getCellHeight() / 2 + celly,
                        celly,
                        tile.getCellHeight() / 2 + celly,
                        tile.getCellHeight() + celly};

                    //gridlines!
                    gGrid.setColor(new Color(GRIDLINE_COLOR));
                    gGrid.drawPolygon(xPoints, yPoints, 4);


                    //save color
                    hitBoxRef[i][j] = i << 8 | j << 1 | 1;

                    //draw ref
                    gHit.setColor(new Color(hitBoxRef[i][j]));

                    gHit.fillPolygon(xPoints, yPoints, 4);
                }
            }
        }


        //paint grid lines
        g.drawImage(gridLines, 0, 0, null);


        //draw buildings
        for (int i = 0; i < RENDER_WIDTH; i++) {

            for (int j = RENDER_HEIGHT - 1; j >= 0; j--) {
                // Changed loop condition here.
                cellx = (j * tile.getCellWidth() / 2)
                        + (i * tile.getCellWidth() / 2)
                        + MARGIN_X;
                celly = (i * tile.getCellHeight() / 2)
                        - (j * tile.getCellHeight() / 2)
                        + jpCanvas.getHeight() / 2
                        + MARGIN_Y;


                if (buildingBlock[i][j] != null) {
                    //get type
                    tType = buildingBlock[i][j].getType();

                    //get cell
                    temp = tType.getSprite();

                    //draw that spr
                    g.drawImage(
                            temp.getCell(tType.getSpriteX(), tType.getSpriteY()),
                            cellx + tType.getDeltaX(),
                            celly + tType.getDeltaY(),
                            null);
                    
                    //are we online?
                    if(!buildingBlock[i][j].isOnline()){
                        //if not put a red dot over the building
                        g.setColor(Color.red);
                        g.fillOval(cellx+tile.getCellWidth()/ 2-3, celly+tile.getCellHeight() / 2-3, 5, 5);
                    }
                    
                    else if(!buildingBlock[i][j].isConected()){
                        //if not connected put a yellow dot over the building
                        g.setColor(Color.yellow);
                        g.fillOval(cellx+tile.getCellWidth()/ 2-3, celly+tile.getCellHeight() / 2-3, 5, 5);
                    }
                }
            }
        }

        drawInfoBox(g);

        drawResourceBox(g);

        //draw map
        drawMap(g, gHit);

        //draw buttons
        renderButtons(g);

        //if i need to draw bnutton boxes
        if (bResized) {
            renderButtonsHit(gHit);
        }


        if (bDebug) {
            g.drawImage(hitBox, 0, 0, null);
        }

        //resized repainting off
        bResized = false;

        //and draw buffer to panel
        gFinal.drawImage(tempImg, 0, 0, null);


    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc=" Utility Render Functions ">
    private void writeText(Graphics g, String string, int i, boolean left) {
        int x = 0;
        if (left) {
            x = jpCanvas.getWidth() - TEXT_AREA_WIDTH;
        }
        g.drawString(string,
                x + 5,
                jpCanvas.getHeight() - TEXT_AREA_HEIGHT + 14 * (1 + i));

    }

    private void drawMap(Graphics g, Graphics gHit) {

        Image img = Main.game.getMap().getImage();

        //check if i need to update hitbox
        if (bResized) {
            gHit.setColor(new Color(MAP_COLOR));
            gHit.fillRect(jpCanvas.getWidth() - img.getWidth(null), 0,
                    img.getWidth(null),
                    img.getHeight(null));

        }

        g.drawImage(img, jpCanvas.getWidth() - img.getWidth(null), 0, null);

        g.setColor(new Color(MAP_COLOR_SELECTED));
        g.drawRect(jpCanvas.getWidth() - img.getWidth(null) + x, y, RENDER_WIDTH, RENDER_HEIGHT);

    }

    public void renderButtons(Graphics g) {
        Iterator<UIButton> iterButton = buttons.iterator();

        UIButton tempButton;

        while (iterButton.hasNext()) {
            //get next
            tempButton = iterButton.next();


            //draws!
            tempButton.render(g);

        }

    }

    public void renderButtonsHit(Graphics g) {
        Iterator<UIButton> iterButton = buttons.iterator();
        Iterator<Color> iterBColor = buttonsColors.iterator();

        UIButton tempButton;
        Color tempColor;

        while (iterBColor.hasNext() && iterButton.hasNext()) {
            //get next
            tempButton = iterButton.next();
            tempColor = iterBColor.next();


            //draws!
            tempButton.renderHit(g, tempColor);

        }

    }

    private void drawResourceBox(Graphics g) {
        int i = 0;
        //DRAW TEXT BOX
        g.setColor(new Color(RESOURCE_BOX_COLOR));
        g.fillRect(0,
                jpCanvas.getHeight() - TEXT_AREA_HEIGHT,
                TEXT_AREA_WIDTH, TEXT_AREA_HEIGHT);

        g.setColor(new Color(POPULATION_COLOR));
        writeText(g, "Population::" + Main.game.getPopulation() + "/"
                + Main.game.getHousingTotal(), i++, false);

        writeText(g, "Workers::" + Main.game.getWorkers(), i++,false);
        
        g.setColor(new Color(FOOD_COLOR));
        writeText(g, "Food::" + Main.game.getAgriculture() + "/"
                + Main.game.getAgrigultureStored(), i++, false);

        g.setColor(new Color(POWER_COLOR));
        writeText(g, "Power::" + Main.game.getPower() + "/"
                + Main.game.getPowerTotal(), i++, false);

        g.setColor(new Color(ORE_COLOR));
        writeText(g, "Ore::" + Main.game.getOre(), i++, false);
    }

    private void drawInfoBox(Graphics g) {


        //DRAW TEXT BOX
        g.setColor(new Color(INFO_BOX_COLOR));
        g.fillRect(jpCanvas.getWidth() - TEXT_AREA_WIDTH,
                jpCanvas.getHeight() - TEXT_AREA_HEIGHT,
                TEXT_AREA_WIDTH, TEXT_AREA_HEIGHT);

        //write in it

        g.setColor(Color.white);
        writeText(g, "Tick::" + Main.game.getTimeStamp(), 0, true);

        g.setColor(Color.red);
        writeText(g, "Map::x:" + x + " y:" + y, 1, true);


        writeText(g,
                "Click::x:" + (int) touch.getX()
                + " y:" + (int) touch.getY(), 2, true);

        g.setColor(Color.MAGENTA);
        if (currentTool != null) {
            writeText(g,
                    "Tool::" + currentTool.toString(), 3, true);

        } else {
            writeText(g,
                    "No Tool", 3, true);
        }

        if (desc != null) {
            int i = 4;
            String[] s = desc.split("\n");
            for (int j = 0; j < s.length; j++) {
                g.setColor(Color.white);
                writeText(g, s[j], i + j, true);
            }
        }



    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc=" Utility Functions ">
    void error(InterruptedException e) {
        java.util.logging.Logger.getLogger(
                UI.class.getName()).log(
                java.util.logging.Level.SEVERE, null, e);
    }

    /**
     *
     * @param x
     * @param y
     * @param w
     * @param h
     * @param txt
     * @param bg
     * @param cTxt
     */
    public UIButton addButton(int x, int y, int w, int h, String txt,
            Color cBg, Color cTxt, ActionListener e) {

        //make the button
        UIButton b = new UIButton(x, y, w, h, txt, cBg, cTxt, e);

        return addButton(b);
    }

    public void setCurrentTool(Tool currentTool) {
        this.currentTool = currentTool;
    }

    public Tool getCurrentTool() {
        return currentTool;
    }

    public void setBuildmenu(BuildMenu bldmenu) {
        this.buildmenu = bldmenu;
    }

    public BuildMenu getBuildmenu() {
        return buildmenu;
    }

    public boolean getBuildmenuVisible() {
        return buildmenu.isVisible();
    }

    public void setBuildmenuVisible(boolean b) {
        buildmenu.setVisible(b);
    }

    public ArrayList<UIButton> getButtons() {
        return buttons;
    }

    public synchronized void forceRepaint() {
        bResized = true;
    }

    public UIButton addButton(UIButton b) {
        //add button to ref
        buttons.add(b);
        buttonsColors.add(new Color(nextButtonColor));

        //add 2
        nextButtonColor += 2;

        return b;
    }
    //</editor-fold>
}
