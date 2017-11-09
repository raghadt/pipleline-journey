/**
 * CPCS391 "Pipline Journy" Project 2017
 *
 *  DONE BY:
 *  Raneem Gharbi, 1333141 , IBR
 *  Raghad Taleb, 1421064 , IAR
 *  Fatema BinAfeef, 1420590 , IAR
 *
 */
package org.yourorghere;

import java.util.List;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import com.sun.opengl.util.Animator;
import com.sun.opengl.util.GLUT;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import java.awt.event.*;

public class GraphicsProject implements GLEventListener, KeyListener {

    /*-----------Train1 Identity----------*/
    float t1x = -5f; //translate x
    float t1y = 0.3f; //translate y
    float t1z = -8f; //translate z

    float r1x = 0;//Rotate x
    float r1y = 1;//Rotate y
    float r1z = 0;//Rotate z
    float angle1 = 80;// Rotate Angle

    float s1 = 0f; //scale 

    int a = 0; //movement counter used for animation and error checking

    /*-----------Train2 Identity----------*/
    float t2x = -5f; //Translate x
    float t2y = 0.3f; //Translate y
    float t2z = -8f; // Translate z

    float r2x = 0;//Rotate x
    float r2y = 1;//Rotate y
    float r2z = 0;//Rotate z
    float angle2 = 80;//Rotate Angle

    float s2 = 0f; //scale 

    int b = 0; //movement counter used for animation and error checking

    /*-----------Train3 Identity----------*/
    float t3x = -5f; //Translate x
    float t3y = 0.3f; //Translate y
    float t3z = -8f; // Translate z

    float r3x = 0;//Rotate x
    float r3y = 1;//Rotate y
    float r3z = 0;//Rotate z
    float angle3 = 80;//Rotate Angle

    float s3 = 0f; //scale 

    int c = 0; //movement counter used for animation and error checking

    /*-----------Train4 Identity----------*/
    float t4x = -5f; //Translate x
    float t4y = 0.3f; //Translate y
    float t4z = -8f; // Translate z

    float r4x = 0;//Rotate x
    float r4y = 1;//Rotate y
    float r4z = 0;//Rotate z
    float angle4 = 80;//Rotate Angle

    float s4 = 0f; //scale 

    int d = 0; //movement counter used for animation and error checking

    /*-----------Train5 Identity----------*/
    float t5x = -5f; //Translate x
    float t5y = 0.3f; //Translate y
    float t5z = -8f; // Translate z

    float r5x = 0;//Rotate x
    float r5y = 1;//Rotate y
    float r5z = 0;//Rotate z
    float angle5 = 80;//Rotate Angle

    float s5 = 0f; //scale 

    int f = 0; //movement counter used for animation and error checking

    /*-------------- Screen identity------------*/
    float screen1S = 1;//Scale of screen1 
    float screen2S = 0;//Scale of screen2
    float screen3S = 0;//Scale of screen3
    float byeS = 0; //Scale of Bye screen
    float errorS = 0; //Scale of Error screen
    int screenClicks = 0;//conunter used for screen animation

    /*-----------------Textures----------------------*/
    Texture train1;
    Texture train2;
    Texture train3;
    Texture train4;
    Texture train5;
    Texture train6;
    Texture floor;
    Texture sky;
    Texture screen1;
    Texture screen2;
    Texture screen3;
    Texture error;
    Texture bye;
    Texture sand;

    /*-------------------Train object from blender-----------------------*/
    //train (.obj file) specifications
    float[] verticesTrain;
    float[] texturesTrain;
    int[] indicesTrain;
    //Objects Variables
    static List<float[]> vertices = new ArrayList<float[]>();
    static List<float[]> textures = new ArrayList<float[]>();
    static List<float[]> normals = new ArrayList<float[]>();
    static List<Integer> indices = new ArrayList<Integer>();
    static List<float[]> textureCord = new ArrayList<float[]>();

    /*-----------------------------------------------------------START CCLASS METHODS-------------------------------------------------------------------------*/
    //--Class constructor, used mainly for moving blender object to openg-gl
    public GraphicsProject() {

        //call the method to read the .obj file
        //write the name of the .obj file between the parenthesis
        verticesTrain = getVerticesArray("trainz");
        texturesTrain = getTexturesArray("trainz");
        indicesTrain = getIndicesArray("trainz", verticesTrain, texturesTrain);

    }

    //--------------------------------------------------------------------------
    public static void main(String[] args) {

        Frame frame = new Frame("Pipline journy");//frame title
        GLCanvas canvas = new GLCanvas();

        /*---for interaction with keyboared-------*/
        GraphicsProject p = new GraphicsProject();
        canvas.addGLEventListener(p);
        frame.addKeyListener(p);
        canvas.addKeyListener(p);
        /*---------------------------------------*/

        frame.add(canvas);
        frame.setSize(1260, 750);//frame size

        final Animator animator = new Animator(canvas);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                // Run this on another thread than the AWT event queue to
                // make sure the call to Animator.stop() completes before
                // exiting
                new Thread(new Runnable() {

                    public void run() {
                        animator.stop();
                        System.exit(0);
                    }
                }).start();
            }
        });
        // Center frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        animator.start();

    }

    //-----------------------------------------
    public void init(GLAutoDrawable drawable) {

        GL gl = drawable.getGL();
        System.err.println("INIT GL IS: " + gl.getClass().getName());

        // Enable VSync
        gl.setSwapInterval(1);

        gl.glEnable(GL.GL_TEXTURE_2D); //activate texture mapping for 2D

        /*-----------------LOADING TEXTURES--------------------------*/
        try {
            //load floor texture
            floor = TextureIO.newTexture(new File("floor.png"), true);
        } catch (IOException ex) {
            System.err.println(ex);
        }

        try {
            //load sky texture
            sky = TextureIO.newTexture(new File("sky.jpg"), true);
        } catch (IOException ex) {
            System.err.println(ex);
        }

        try {
            //path to the GREEN trainz.obj texture image 
            train1 = TextureIO.newTexture(new File("GreenTEX.png"), true);
        } catch (IOException exc) {
            exc.printStackTrace();
            System.exit(1);
        }

        try {
            //path to the PINK trainz.obj texture image 
            train2 = TextureIO.newTexture(new File("PinkTEX.png"), true);
        } catch (IOException exc) {
            exc.printStackTrace();
            System.exit(1);
        }

        try {
            //path to the BLUE trainz.obj texture image 
            train3 = TextureIO.newTexture(new File("BlueTEX.png"), true);
        } catch (IOException exc) {
            exc.printStackTrace();
            System.exit(1);
        }

        try {
            //path to the PURPLE trainz.obj texture image 
            train4 = TextureIO.newTexture(new File("PurpleTEX.png"), true);
        } catch (IOException exc) {
            exc.printStackTrace();
            System.exit(1);
        }

        try {
            //path to the YELLOW trainz.obj texture image 
            train5 = TextureIO.newTexture(new File("YellowTEX.png"), true);
        } catch (IOException exc) {
            exc.printStackTrace();
            System.exit(1);
        }
        try {
            //path to the ORANGE trainz.obj texture image 
            train6 = TextureIO.newTexture(new File("OrangeTEX.png"), true);
        } catch (IOException exc) {
            exc.printStackTrace();
            System.exit(1);
        }
        try {
            //load screen1 texture
            screen1 = TextureIO.newTexture(new File("screen1.jpg"), true);
        } catch (IOException ex) {
            System.err.println(ex);
            System.exit(1);
        }
        try {
            //load screen2 texture
            screen2 = TextureIO.newTexture(new File("screen2.jpg"), true);
        } catch (IOException ex) {
            System.err.println(ex);
            System.exit(1);
        }

        try {
            //load screen3 texture
            screen3 = TextureIO.newTexture(new File("screen3.jpg"), true);
        } catch (IOException ex) {
            System.err.println(ex);
            System.exit(1);
        }

        try {
            //load bye screen texture
            bye = TextureIO.newTexture(new File("bye.jpg"), true);
        } catch (IOException ex) {
            System.err.println(ex);
            System.exit(1);
        }
        try {
            //load error screen texture
            error = TextureIO.newTexture(new File("error.jpg"), true);
        } catch (IOException ex) {
            System.err.println(ex);
            System.exit(1);
        }

        try {
            //load sand texture
            sand = TextureIO.newTexture(new File("sand.png"), true);
        } catch (IOException ex) {
            System.err.println(ex);
            System.exit(1);
        }
    }

    //--------------------------------------------------------------------------
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();
        GLU glu = new GLU();

        if (height <= 0) { // avoid a divide by zero error!

            height = 1;
        }
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0f, h, 1.0, 20.0);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    //--------------------------------------------------------------------------
    public void display(GLAutoDrawable drawable) {

        GL gl = drawable.getGL();
        GLUT glut = new GLUT();

        // Clear the drawing area
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        // Reset the current matrix to the "identity"
        gl.glLoadIdentity();

        //--------Draw floor----------
        floor(gl);

        //---------Draw sky-----------
        sky(gl);

        //--------Draw memory--------
        mem(gl, glut);

        //---------Draw ALU----------
        alu(gl, glut);

        //--------Write text-----------
        text(gl, glut);

        //--------Draw screens----------------
        screen1(gl); // screen1
        screen2(gl);// screen2
        screen3(gl);// screen3
        error(gl);// Error screen
        bye(gl);//Bye screen

        //----write text on wooden board--------
        displayMessage(gl, glut);

        //----Draw trains----------------
        gl.glTranslatef(-.7f, 0f, -1.2f);
        train(gl, drawable);

        /*-----------------------ERORR CONDITIONS---------------------------*/
        /*in this section we check errors made from user by the putting the
        conditon of the right actions in every if-statement, if none of
        condiontions are satisfied an error massage will appear and then 
        user will start again, also the last outer else-statment is the state 
        of winning and when it occures a congratuation-bye message will appear.
        
        a,b,c,d,f represent number of train movememts from train1 to train5 respectively
        and it helps in animation and error checking
         */
        if (a != 34) {

            if (a < 6) {
                if (b == 0 && c == 0 && d == 0 && f == 0) {
                } else {
                    errorS = 1;//Show error screen
                    //scale all trains to zero so it won't interfer with the error message
                    s1 = 0;
                    s2 = 0;
                    s3 = 0;
                    s4 = 0;
                    s5 = 0;
                }

            } else if (a == 6) {
                if ((b <= 1 && c == 0 && d == 0 && f == 0)) {
                } else {
                    errorS = 1;//Show error screen
                    //scale all trains to zero so it won't interfer with the error message
                    s1 = 0;
                    s2 = 0;
                    s3 = 0;
                    s4 = 0;
                    s5 = 0;
                }

            } else if (a > 6 && a < 11) {
                if (b == 1 && c == 0 && d == 0 && f == 0) {
                } else {
                    errorS = 1;//Show error screen
                    //scale all trains to zero so it won't interfer with the error message
                    s1 = 0;
                    s2 = 0;
                    s3 = 0;
                    s4 = 0;
                    s5 = 0;
                }

            } else if (a == 11) {
                if ((b >= 1 && b <= 6 && c == 0 && d == 0 && f == 0) || (b == 6 && c == 1 && d == 0 && f == 0)) {
                } else {
                    errorS = 1;//Show error screen
                    //scale all trains to zero so it won't interfer with the error message
                    s1 = 0;
                    s2 = 0;
                    s3 = 0;
                    s4 = 0;
                    s5 = 0;
                }

            } else if (a > 11 && a < 20) {
                if (b == 6 && c == 1 && d == 0 && f == 0) {
                } else {
                    errorS = 1;//Show error screen
                    //scale all trains to zero so it won't interfer with the error message
                    s1 = 0;
                    s2 = 0;
                    s3 = 0;
                    s4 = 0;
                    s5 = 0;
                }

            } else if (a == 20) {
                if ((b == 6 && c == 1 && d == 0 && f == 0) || (b > 6 && b <= 11 && c == 1 && d == 0 && f == 0)) {
                } else if ((b == 11 && c >= 1 && c <= 6 && d == 0 && f == 0) || (b == 11 && c == 6 && d == 1 && f == 0)) {
                } else {
                    errorS = 1;
                    //scale all trains to zero so it won't interfer with the error message
                    s1 = 0;
                    s2 = 0;
                    s3 = 0;
                    s4 = 0;
                    s5 = 0;
                }

            } else if (a > 20) {
                if (b == 11 && c == 6 && d == 1 && f == 0) {
                } else {
                    errorS = 1;//Show error screen
                    //scale all trains to zero so it won't interfer with the error message
                    s1 = 0;
                    s2 = 0;
                    s3 = 0;
                    s4 = 0;
                    s5 = 0;
                }
            }

        } else if (b != 34 && b >= 11) {
            if (b == 11) {
                if ((c >= 1 && c <= 6 && d == 0 && f == 0) || (c == 6 && d == 1 && f == 0)) {
                } else {
                    errorS = 1;//Show error screen
                    //scale all trains to zero so it won't interfer with the error message
                    s1 = 0;
                    s2 = 0;
                    s3 = 0;
                    s4 = 0;
                    s5 = 0;
                }

            } else if (b > 11 && b < 20) {
                if (c == 6 && d == 1 && f == 0) {
                } else {
                    errorS = 1;//Show error screen
                    //scale all trains to zero so it won't interfer with the error message
                    s1 = 0;
                    s2 = 0;
                    s3 = 0;
                    s4 = 0;
                    s5 = 0;
                }

            } else if (b == 20) {
                if ((c == 6 && d == 1 && f == 0) || (c > 6 && c <= 11 && d == 1 && f == 0)) {
                } else if ((c == 11 && d >= 1 && d <= 6 && f == 0) || (c == 11 && d == 6 && f == 1)) {
                } else {
                    errorS = 1;//Show error screen
                    //scale all trains to zero so it won't interfer with the error message
                    s1 = 0;
                    s2 = 0;
                    s3 = 0;
                    s4 = 0;
                    s5 = 0;
                }

            } else if (b > 20) {
                if (c == 11 && d == 6 && f == 1) {
                } else {
                    errorS = 1;//Show error screen
                    //scale all trains to zero so it won't interfer with the error message
                    s1 = 0;
                    s2 = 0;
                    s3 = 0;
                    s4 = 0;
                    s5 = 0;
                }
            }

        } else if (c != 34 && c >= 11) {
            if (c == 11) {
                if ((d >= 1 && d <= 6 && f == 0) || (d == 6 && f == 1)) {
                } else {
                    errorS = 1;//Show error screen
                    //scale all trains to zero so it won't interfer with the error message
                    s1 = 0;
                    s2 = 0;
                    s3 = 0;
                    s4 = 0;
                    s5 = 0;
                }

            } else if (c > 11 && c < 20) {
                if (d == 6 && f == 1) {
                } else {
                    errorS = 1;//Show error screen
                    //scale all trains to zero so it won't interfer with the error message
                    s1 = 0;
                    s2 = 0;
                    s3 = 0;
                    s4 = 0;
                    s5 = 0;
                }

            } else if (c == 20) {
                if ((d == 6 && f == 1) || (d > 6 && d <= 11 && f == 1)) {
                } else if ((d == 11 && f >= 1 && f <= 6) || (d == 11 && f == 6)) {
                } else {
                    errorS = 1;//Show error screen
                    //scale all trains to zero so it won't interfer with the error message
                    s1 = 0;
                    s2 = 0;
                    s3 = 0;
                    s4 = 0;
                    s5 = 0;
                }

            } else if (c > 20) {
                if (d == 11 && f == 6) {
                } else {
                    errorS = 1;//Show error screen
                    //scale all trains to zero so it won't interfer with the error message
                    s1 = 0;
                    s2 = 0;
                    s3 = 0;
                    s4 = 0;
                    s5 = 0;
                }
            }

        } else if (d != 34 && d >= 11) {
            if (d == 11) {
                if ((f >= 1 && f <= 6)) {
                } else {
                    errorS = 1;//Show error screen
                    //scale all trains to zero so it won't interfer with the error message
                    s1 = 0;
                    s2 = 0;
                    s3 = 0;
                    s4 = 0;
                    s5 = 0;
                }

            } else if (d > 11 && d < 20) {
                if (f == 6) {
                } else {
                    errorS = 1;//Show error screen
                    //scale all trains to zero so it won't interfer with the error message
                    s1 = 0;
                    s2 = 0;
                    s3 = 0;
                    s4 = 0;
                    s5 = 0;
                }

            } else if (d == 20) {
                if ((f == 6) || (f > 6 && f <= 11)) {
                } else if ((f == 11) || (f == 11)) {
                } else {
                    errorS = 1;//Show error screen
                    //scale all trains to zero so it won't interfer with the error message
                    s1 = 0;
                    s2 = 0;
                    s3 = 0;
                    s4 = 0;
                    s5 = 0;
                }

            } else if (d > 20) {
                if (f == 11) {
                } else {
                    errorS = 1;//Show error screen
                    //scale all trains to zero so it won't interfer with the error message
                    s1 = 0;
                    s2 = 0;
                    s3 = 0;
                    s4 = 0;
                    s5 = 0;
                }
            }

        } else if (f != 34 && f >= 11) {

        } else {//journy completed without errors
            byeS = 1;//Show Bye screen
        }

        /*-----------------------------FLUSH--------------------------------*/
        gl.glFlush();
    }

    //--------------------------------------------------------------------------
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

    //--Method to draw start-screen (screen1) by a Quad and applying texture of screen1----
    private void screen1(GL gl) {

        gl.glPushMatrix();
        screen1.enable();
        screen1.bind();
        gl.glTranslatef(-1f, -0.3f, -3.0f);
        gl.glRotated(15, 1, 0, 0);
        gl.glScalef(screen1S, screen1S, screen1S);
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        gl.glTexCoord2f(0, 1);
        gl.glVertex3f(0.0f, 0.0f, 0.0f);
        gl.glTexCoord2f(1, 1);
        gl.glVertex3f(2.0f, 0.0f, 0.0f);
        gl.glTexCoord2f(1, 0);
        gl.glVertex3f(2.0f, 1.0f, 0.0f);
        gl.glTexCoord2f(0, 0);
        gl.glVertex3f(0.0f, 1.0f, 0.0f);
        gl.glEnd();
        gl.glPopMatrix();
        screen1.disable();
    }

    //--Method to draw instruction-screen (screen2) by a Quad and applying texture of screen2----
    private void screen2(GL gl) {

        gl.glPushMatrix();
        screen2.enable();
        screen2.bind();
        gl.glTranslatef(-1f, -0.3f, -3.0f);
        gl.glRotated(15, 1, 0, 0);
        gl.glScalef(screen2S, screen2S, screen2S);
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        gl.glTexCoord2f(0, 1);
        gl.glVertex3f(0.0f, 0.0f, 0.0f);
        gl.glTexCoord2f(1, 1);
        gl.glVertex3f(2.0f, 0.0f, 0.0f);
        gl.glTexCoord2f(1, 0);
        gl.glVertex3f(2.0f, 1.0f, 0.0f);
        gl.glTexCoord2f(0, 0);
        gl.glVertex3f(0.0f, 1.0f, 0.0f);
        gl.glEnd();
        gl.glPopMatrix();
        screen2.disable();
    }

    //--Method to draw instruction-screen2 (screen3) by a Quad and applying texture of screen3----
    private void screen3(GL gl) {

        gl.glPushMatrix();
        screen3.enable();
        screen3.bind();
        gl.glTranslatef(-1f, -0.3f, -3.0f);
        gl.glRotated(15, 1, 0, 0);
        gl.glScalef(screen3S, screen3S, screen3S);
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        gl.glTexCoord2f(0, 1);
        gl.glVertex3f(0.0f, 0.0f, 0.0f);
        gl.glTexCoord2f(1, 1);
        gl.glVertex3f(2.0f, 0.0f, 0.0f);
        gl.glTexCoord2f(1, 0);
        gl.glVertex3f(2.0f, 1.0f, 0.0f);
        gl.glTexCoord2f(0, 0);
        gl.glVertex3f(0.0f, 1.0f, 0.0f);
        gl.glEnd();
        gl.glPopMatrix();
        screen3.disable();
    }
    
    //--Method to draw ending-winning screen (bye)by a Quad and applying texture of bye -------
    public void bye(GL gl) {

        gl.glPushMatrix();
        bye.enable();
        bye.bind();
        gl.glTranslatef(-1f, -0.3f, -3.0f);
        gl.glRotated(15, 1, 0, 0);
        gl.glScalef(byeS, byeS, byeS);
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        gl.glTexCoord2f(0, 1);
        gl.glVertex3f(0.0f, 0.0f, 0.0f);
        gl.glTexCoord2f(1, 1);
        gl.glVertex3f(2.0f, 0.0f, 0.0f);
        gl.glTexCoord2f(1, 0);
        gl.glVertex3f(2.0f, 1.0f, 0.0f);
        gl.glTexCoord2f(0, 0);
        gl.glVertex3f(0.0f, 1.0f, 0.0f);
        gl.glEnd();
        gl.glPopMatrix();
        bye.disable();

    }

    //--Method to display error screen (error)by a Quad and applying texture of error ------
    private void error(GL gl) {
        gl.glPushMatrix();
        error.enable();
        error.bind();
        gl.glTranslatef(-1f, -0.3f, -3.0f);
        gl.glRotated(15, 1, 0, 0);
        gl.glScalef(errorS, errorS, errorS);
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        gl.glTexCoord2f(0, 1);
        gl.glVertex3f(0.0f, 0.0f, 0.0f);
        gl.glTexCoord2f(1, 1);
        gl.glVertex3f(2.0f, 0.0f, 0.0f);
        gl.glTexCoord2f(1, 0);
        gl.glVertex3f(2.0f, 1.0f, 0.0f);
        gl.glTexCoord2f(0, 0);
        gl.glVertex3f(0.0f, 1.0f, 0.0f);
        gl.glEnd();
        gl.glPopMatrix();
        error.disable();
    }

    //--Method to draw floor by a Quad and applying texture of floor------------
    private void floor(GL gl) {

        floor.enable();
        floor.bind();
        gl.glPushMatrix();
        gl.glColor3f(1, 1, 1);
        gl.glTranslatef(-1.5f, -0.2f, -6.0f);
        gl.glRotatef(-70, 1, 0, 0);//the quad is rotated by x-axis to appear as floor
        gl.glBegin(GL.GL_QUADS);
        gl.glEnable(GL.GL_BLEND);
        gl.glTexCoord2f(0, 0);
        gl.glVertex3f(-5.03f, 3.0f, 0.0f);
        gl.glTexCoord2f(1, 0);
        gl.glVertex3f(8.03f, 3.0f, 0.0f);
        gl.glTexCoord2f(1, 1);
        gl.glVertex3f(8.03f, -3.5f, 0.0f);
        gl.glTexCoord2f(0, 1);
        gl.glVertex3f(-5.03f, -3.5f, 0.0f);
        gl.glEnd();
        gl.glPopMatrix();
        floor.disable();
    }

    //--Method to draw the sky by a Quad and applying texture of sky------------
    private void sky(GL gl) {

        sky.enable();
        sky.bind();
        gl.glPushMatrix();
        gl.glTranslatef(0f, -0.2f, -10.0f);
        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2f(0, 0);
        gl.glVertex3f(-20, 8.2f, -9);
        gl.glTexCoord2f(1, 0);
        gl.glVertex3f(20, 8.2f, -9);
        gl.glTexCoord2f(1, 1);
        gl.glVertex3f(20, 1.6f, -9);
        gl.glTexCoord2f(0, 1);
        gl.glVertex3f(-20, 1.6f, -9);
        gl.glEnd();
        gl.glPopMatrix();
        sky.disable();
    }

    //--Method to draw Regester-Memory-Station (blue cube) and Data-Memory-Station (yellow cube)--
    private void mem(GL gl, GLUT glut) {

        /*---------------Regester Memory Station------------------*/
        //Enable shading
        gl.glEnable(gl.GL_DEPTH_TEST);
        gl.glEnable(gl.GL_LIGHTING);
        gl.glEnable(gl.GL_LIGHT0);
        gl.glDisable(gl.GL_CULL_FACE);
        gl.glEnable(gl.GL_COLOR_MATERIAL);

        //Draw station as cube
        gl.glPushMatrix();
        gl.glColor3f(159 / 255f, 169 / 255f, 31 / 255f);
        gl.glTranslatef(3.6f, 0.6f, -7f);
        gl.glRotated(20, 1, 0, 0);
        gl.glRotated(5, 0, -1, 0);
        gl.glScaled(1, 0.7, 1);
        gl.glScaled(1.5, 1, 1);
        glut.glutSolidCube(1);

        //Draw the front enterance as a blqck quad
        gl.glPushMatrix();
        gl.glColor3f(0.0f, 0.0f, 0.0f);
        gl.glTranslatef(-4.7f, 0.02f, 0f);
        gl.glRotated(8, 0, 1, 0);
        gl.glBegin(GL.GL_QUADS);
        gl.glVertex3f(0.0f, 0.0f, 0.0f);
        gl.glVertex3f(0.7f, 0.0f, 0.0f);
        gl.glVertex3f(0.7f, -1.0f, 0.0f);
        gl.glVertex3f(0.0f, -1.0f, 0.0f);
        gl.glEnd();
        gl.glPopMatrix();

        //Draw the side exit as black quad
        gl.glPushMatrix();
        gl.glTranslatef(-3.5f, 0.25f, 0.0f);
        gl.glRotated(90, 0, 1, 0);
        gl.glColor3f(0.0f, 0.0f, 0.0f);
        gl.glBegin(GL.GL_QUADS);
        gl.glVertex3f(0.0f, 0.0f, 0.0f);
        gl.glVertex3f(0.3f, 0.0f, 0.0f);
        gl.glVertex3f(0.3f, -1.0f, 0.0f);
        gl.glVertex3f(0.0f, -1.0f, 0.0f);
        gl.glEnd();
        gl.glPopMatrix();

        //Disable shading 
        gl.glDisable(gl.GL_LIGHTING);
        gl.glDisable(gl.GL_DEPTH_TEST);
        gl.glDisable(gl.GL_LIGHTING);
        gl.glDisable(gl.GL_LIGHT0);
        gl.glDisable(gl.GL_COLOR_MATERIAL);
        gl.glPopMatrix();
        /*-------------END DRAWING REGESTER MEMORY STATION--------------------*/

 /*----------------Data Memory Station--------------------*/
        //Enable shading
        gl.glEnable(gl.GL_DEPTH_TEST);
        gl.glEnable(gl.GL_LIGHTING);
        gl.glEnable(gl.GL_LIGHT0);
        gl.glDisable(gl.GL_CULL_FACE);
        gl.glEnable(gl.GL_COLOR_MATERIAL);

        //Draw station as cube
        gl.glPushMatrix();
        gl.glColor3f(0, 0.50196078f, 128 / 255f);
        gl.glTranslatef(-2.9f, 1f, -9f);
        gl.glRotated(20, 1, 0, 0);
        gl.glRotated(-90, 0, 1, 0);
        gl.glScaled(1, 1.2, 1);
        gl.glScaled(1.5, 1, 2);
        glut.glutSolidCube(1);

        //Disable shading
        gl.glDisable(gl.GL_LIGHTING);
        gl.glDisable(gl.GL_DEPTH_TEST);
        gl.glDisable(gl.GL_LIGHTING);
        gl.glDisable(gl.GL_LIGHT0);
        gl.glDisable(gl.GL_COLOR_MATERIAL);
        gl.glPopMatrix();

        //Draw front exit as black quad
        gl.glPushMatrix();
        gl.glTranslatef(4f, 0.15f, -9f);
        gl.glRotated(5, 0, 1, 0);
        gl.glScaled(1.5, -0.7, 0);
        gl.glColor3f(0.0f, 0.0f, 0.0f);
        gl.glBegin(GL.GL_QUADS);
        gl.glVertex3f(0.0f, 0.0f, 0.0f);
        gl.glVertex3f(1.0f, 0.0f, 0.0f);
        gl.glVertex3f(1.1f, -1.0f, 0.0f);
        gl.glVertex3f(0.1f, -1.0f, 0.0f);
        gl.glEnd();
        gl.glPopMatrix();

        //Draw side enterance as black quad
        gl.glPushMatrix();
        gl.glTranslatef(3.2f, 1f, -9f);
        gl.glTranslatef(0.5f, 0.3f, 0.0f);
        gl.glRotated(-120, 0, 1, 0);
        gl.glScaled(1, -0.7, 1);
        gl.glTranslatef(0.0f, 1.3f, 0.0f);
        gl.glColor3f(0.0f, 0.0f, 0.0f);
        gl.glBegin(GL.GL_QUADS);
        gl.glVertex3f(0.0f, 0.2f, 0.0f);
        gl.glVertex3f(1.33f, 0.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, 0.0f);
        gl.glVertex3f(-1f, -1.0f, 0.0f);
        gl.glEnd();
        gl.glPopMatrix();
        /*-------------END DRAWING DATA MEMORY STATION------------------------*/
    }

    //--Mathod to Draw alu-Station as half cylinder-----------------------------
    private void alu(GL gl, GLUT glut) {

        //Enable shading
        gl.glEnable(gl.GL_DEPTH_TEST);
        gl.glEnable(gl.GL_LIGHTING);
        gl.glEnable(gl.GL_LIGHT0);
        gl.glDisable(gl.GL_CULL_FACE);
        gl.glEnable(gl.GL_COLOR_MATERIAL);

        //Draw the station as cylinder
        gl.glPushMatrix();
        gl.glTranslatef(0.05f, 0.3f, -10f);
        gl.glRotated(70, 0, 1, 0);
        gl.glRotated(1.6, 1, 0, 0);
        gl.glScaled(1, 1.3, 1);
        gl.glColor3f(254 / 255f, 80 / 255f, 0f);
        glut.glutSolidCylinder(0.9f, 2, 20, 10);
        gl.glColor3f(1.0f, 1f, 1f);

        //Disable shading
        gl.glDisable(gl.GL_LIGHTING);
        gl.glDisable(gl.GL_DEPTH_TEST);
        gl.glDisable(gl.GL_LIGHTING);
        gl.glDisable(gl.GL_LIGHT0);
        gl.glDisable(gl.GL_COLOR_MATERIAL);
        gl.glPopMatrix();

        //Draw the exit as a black circle
        gl.glPushMatrix();
        gl.glTranslatef(2.05f, 0.25f, -10f);
        gl.glRotated(-85, 0, 1, 0);
        gl.glScaled(1, 2, 1);
        float numPoints = 50;
        float Radius = 0.4f;
        gl.glBegin(GL.GL_POLYGON);
        gl.glColor3f(0.0f, 0.0f, 0.0f);

        for (int i = 0; i < numPoints; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints); //2.0*PI==360   
            double X = Math.cos(Angle) * Radius;
            double Y = Math.sin(Angle) * Radius;
            gl.glVertex2d(X, Y);
        }

        gl.glEnd();
        gl.glColor3f(1.0f, 1f, 1f);
        gl.glPopMatrix();

        /*to make the station appear as hlaf cylinder we coverd the lower half 
          with a quad with the texture of the floor sand*/
        sand.enable();
        sand.bind();
        gl.glPushMatrix();
        gl.glTranslatef(0f, 0.3f, -10f);
        gl.glBegin(GL.GL_QUADS);
        gl.glEnable(GL.GL_BLEND);
        gl.glTexCoord2f(0, 0);
        gl.glVertex3f(-1f, 0.0f, 0.0f);  // Top Left
        gl.glTexCoord2f(1, 0);
        gl.glVertex3f(2.4f, 0.0f, 0.0f);   // Top Right
        gl.glTexCoord2f(1, 1);
        gl.glVertex3f(2.4f, -1.3f, 0.0f);  // Bottom Right
        gl.glTexCoord2f(0, 1);
        gl.glVertex3f(-1f, -1.3f, 0.0f); // Bottom Left
        gl.glEnd();
        gl.glPopMatrix();
        sand.disable();
    }
    
        //--Method to display how many trains are left to fetch on the wooden sign--
    public void displayMessage(GL gl, GLUT glut) {
        //if the first train is fetched display a message with the train's color
        //if first train's key pressing counter =1 or more
        if (a >= 1 && b == 0 && c == 0 && d == 0 && f == 0) {
            gl.glColor3f(153 / 255f, 1f, 153 / 255f);
            gl.glPushMatrix();
            gl.glTranslatef(-4f, 1.5f, -6.0f);
            gl.glRasterPos2f(0f, 0f);
            gl.glColor3f(1f, 1.0f, 1.0f);

            char[] t1 = {'4', ' ', 'm', 'o', 'r', 'e', ' ', 't', 'r', 'a', 'i', 'n', 's'};
            for (int i = 0; i < t1.length; i++) {
                glut.glutBitmapCharacter(glut.BITMAP_TIMES_ROMAN_24, t1[i]);
            }

            gl.glColor3f(153 / 255f, 1f, 153 / 255f);
            gl.glPopMatrix();

            gl.glPushMatrix();
            gl.glTranslatef(-4f, 1.2f, -6.0f);
            gl.glRasterPos2f(0f, 0f);
            gl.glColor3f(1f, 1.0f, 1.0f);

            char[] t11 = {'t', 'o', ' ', 'f', 'e', 't', 'c', 'h'};
            for (int i = 0; i < t11.length; i++) {
                glut.glutBitmapCharacter(glut.BITMAP_TIMES_ROMAN_24, t11[i]);
            }

            gl.glColor3f(1f, 1.0f, 1.0f);
            gl.glPopMatrix();

        }
        //if the second train is fetched display a message with the train's color
        //if second train's key pressing counter =1 or more
        if (b >= 1 && c == 0 && d == 0 && f == 0) {
            gl.glColor3f(1f, 4 / 255f, 128 / 255f);
            gl.glPushMatrix();
            gl.glTranslatef(-4f, 1.5f, -6.0f);
            gl.glRasterPos2f(0f, 0f);
            gl.glColor3f(1f, 1.0f, 1.0f);

            char[] t1 = {'3', ' ', 'm', 'o', 'r', 'e', ' ', 't', 'r', 'a', 'i', 'n', 's'};
            for (int i = 0; i < t1.length; i++) {
                glut.glutBitmapCharacter(glut.BITMAP_TIMES_ROMAN_24, t1[i]);
            }

            gl.glColor3f(1f, 4 / 255f, 128 / 255f);
            gl.glPopMatrix();

            gl.glPushMatrix();
            gl.glTranslatef(-4f, 1.2f, -6.0f);
            gl.glRasterPos2f(0f, 0f);
            gl.glColor3f(1f, 1.0f, 1.0f);

            char[] t11 = {'t', 'o', ' ', 'f', 'e', 't', 'c', 'h'};
            for (int i = 0; i < t11.length; i++) {
                glut.glutBitmapCharacter(glut.BITMAP_TIMES_ROMAN_24, t11[i]);
            }

            gl.glColor3f(1f, 1.0f, 1.0f);
            gl.glPopMatrix();

        }
        //if the third train is fetched display a message with the train's color
        //if third train's key pressing counter =1 or more
        if (c >= 1 && d == 0 && f == 0) {
            gl.glColor3f(4 / 255f, 1f, 1f);

            gl.glPushMatrix();
            gl.glTranslatef(-4f, 1.5f, -6.0f);
            gl.glRasterPos2f(0f, 0f);
            gl.glColor3f(1f, 1.0f, 1.0f);

            char[] t1 = {'2', ' ', 'm', 'o', 'r', 'e', ' ', 't', 'r', 'a', 'i', 'n', 's'};
            for (int i = 0; i < t1.length; i++) {
                glut.glutBitmapCharacter(glut.BITMAP_TIMES_ROMAN_24, t1[i]);
            }
            gl.glColor3f(4 / 255f, 1f, 1f);
            gl.glPopMatrix();

            gl.glPushMatrix();
            gl.glTranslatef(-4f, 1.2f, -6.0f);
            gl.glRasterPos2f(0f, 0f);
            gl.glColor3f(1f, 1.0f, 1.0f);

            char[] t11 = {'t', 'o', ' ', 'f', 'e', 't', 'c', 'h'};
            for (int i = 0; i < t11.length; i++) {
                glut.glutBitmapCharacter(glut.BITMAP_TIMES_ROMAN_24, t11[i]);
            }

            gl.glColor3f(1f, 1.0f, 1.0f);
            gl.glPopMatrix();
        }
        //if the fourth train is fetched display a message with the train's color
        //if fourth train's key pressing counter =1 or more
        if (d >= 1 && f == 0) {

            gl.glColor3f(135 / 255f, 1 / 255f, 191 / 255f);

            gl.glPushMatrix();
            gl.glTranslatef(-4f, 1.5f, -6.0f);
            gl.glRasterPos2f(0f, 0f);
            gl.glColor3f(1f, 1.0f, 1.0f);

            char[] t1 = {'1', ' ', 'm', 'o', 'r', 'e', ' ', 't', 'r', 'a', 'i', 'n'};
            for (int i = 0; i < t1.length; i++) {
                glut.glutBitmapCharacter(glut.BITMAP_TIMES_ROMAN_24, t1[i]);
            }

            gl.glColor3f(135 / 255f, 1 / 255f, 191 / 255f);
            gl.glPopMatrix();

            gl.glPushMatrix();
            gl.glTranslatef(-4f, 1.2f, -6.0f);
            gl.glRasterPos2f(0f, 0f);
            gl.glColor3f(1f, 1.0f, 1.0f);

            char[] t11 = {'t', 'o', ' ', 'f', 'e', 't', 'c', 'h'};
            for (int i = 0; i < t11.length; i++) {
                glut.glutBitmapCharacter(glut.BITMAP_TIMES_ROMAN_24, t11[i]);
            }

            gl.glColor3f(1f, 1.0f, 1.0f);
            gl.glPopMatrix();
        }
        //if the fifth train is fetched display a message with the train's color
        //if fifth train's key pressing counter =1 or more
        if (f >= 1) {
            gl.glColor3f(252 / 255f, 220 / 255f, 2 / 255f);

            gl.glPushMatrix();
            gl.glTranslatef(-4.1f, 1.5f, -6.0f);
            gl.glRasterPos2f(0f, 0f);
            gl.glColor3f(0f, 0.0f, 1.0f);

            char[] t1 = {'n', 'o', ' ', 'm', 'o', 'r', 'e', ' ', 't', 'r', 'a', 'i', 'n', 's'};
            for (int i = 0; i < t1.length; i++) {
                glut.glutBitmapCharacter(glut.BITMAP_TIMES_ROMAN_24, t1[i]);
            }
            gl.glColor3f(252 / 255f, 220 / 255f, 2 / 255f);
            gl.glPopMatrix();

            gl.glPushMatrix();
            gl.glTranslatef(-4f, 1.2f, -6.0f);
            gl.glRasterPos2f(0f, 0f);
            gl.glColor3f(1f, 1.0f, 1.0f);

            char[] t11 = {'t', 'o', ' ', 'f', 'e', 't', 'c', 'h'};
            for (int i = 0; i < t11.length; i++) {
                glut.glutBitmapCharacter(glut.BITMAP_TIMES_ROMAN_24, t11[i]);
            }

            gl.glColor3f(1f, 1.0f, 1.0f);
            gl.glPopMatrix();

        }
    }

    //--Method to display text on the objects(stations)-------------------------
    private void text(GL gl, GLUT glut) {
        //---- register -----
        gl.glPushMatrix();
        gl.glTranslatef(-2.55f, 0.75f, -6.0f);
        gl.glRasterPos2f(0f, 0f);
        gl.glColor3f(1f, 1.0f, 1.0f);

        char[] register = {'R', 'e', 'g', 'i', 's', 't', 'e', 'r'};
        for (int i = 0; i < register.length; i++) {
            glut.glutBitmapCharacter(glut.BITMAP_TIMES_ROMAN_24, register[i]);
        }
        gl.glColor3f(1f, 1.0f, 1.0f);
        gl.glPopMatrix();

        //----- alu -----
        gl.glPushMatrix();
        gl.glTranslatef(0.3f, 0.4f, -6.0f);
        gl.glRasterPos2f(0f, 0f);
        gl.glColor3f(1.0f, 1.0f, 1.0f);

        char[] alu = {'A', 'L', 'U'};
        for (int i = 0; i < alu.length; i++) {
            glut.glutBitmapCharacter(glut.BITMAP_TIMES_ROMAN_24, alu[i]);
        }
        gl.glPopMatrix();

        //----- memory ----
        gl.glPushMatrix();
        gl.glTranslatef(3f, 0.57f, -6.0f);
        gl.glRasterPos2f(0f, 0f);
        gl.glColor3f(1.0f, 1.0f, 1.0f);

        char[] mem = {'M', 'e', 'm', 'o', 'r', 'y'};
        for (int i = 0; i < mem.length; i++) {
            glut.glutBitmapCharacter(glut.BITMAP_TIMES_ROMAN_24, mem[i]);
        }
        gl.glPopMatrix();

    }

    //--Method to draw the 5 trains and applying textures on them---------------
    private void train(GL gl, GLAutoDrawable drawable) {

        //shading
        gl.glEnable(gl.GL_DEPTH_TEST);
        gl.glEnable(gl.GL_LIGHT0);
        gl.glDisable(gl.GL_CULL_FACE);
        gl.glEnable(gl.GL_COLOR_MATERIAL);

        //------------------------------Draw the 1st train (green)--------------
        //start drawing train1(green)
        gl.glPushMatrix();
        train1.enable();
        train1.bind();
        gl.glTranslatef(t1x, t1y, t1z);
        gl.glRotated(angle1, r1x, r1y, r1z);
        gl.glScalef(s1, s1, s1);
        gl.glColor3f(1f, 1f, 1f);

        //draw the train
        draw(drawable, verticesTrain, texturesTrain, indicesTrain, gl);
        gl.glPopMatrix();
        train1.disable();

        //-------------------------------Draw the 2nd train (pink)--------------
        //start drawing train2 (pink)
        gl.glPushMatrix();
        train2.enable();
        train2.bind();
        gl.glTranslatef(t2x, t2y, t2z);
        gl.glRotated(angle2, r2x, r2y, r2x);
        gl.glScalef(s2, s2, s2);
        gl.glColor3f(1f, 1f, 1f);

        //draw the train
        draw(drawable, verticesTrain, texturesTrain, indicesTrain, gl);
        gl.glPopMatrix();
        train2.disable();
        //-------------------------------Draw the 3rd train(blue)---------------
        //start drawing train3 (blue)
        gl.glPushMatrix();
        train3.enable();
        train3.bind();
        gl.glTranslatef(t3x, t3y, t3z);
        gl.glRotated(angle3, r3x, r3y, r3x);
        gl.glScalef(s3, s3, s3);
        gl.glColor3f(1f, 1f, 1f);

        //draw the train
        draw(drawable, verticesTrain, texturesTrain, indicesTrain, gl);
        gl.glPopMatrix();
        train3.disable();

        //-------------------------------Draw the 4th train (purpul)------------   
        //start drawing train4 (purpul)
        gl.glPushMatrix();
        train4.enable();
        train4.bind();
        //translate then rotate
        gl.glTranslatef(t4x, t4y, t4z);
        gl.glRotated(angle4, r4x, r4y, r4x);
        gl.glScalef(s4, s4, s4);
        gl.glColor3f(1f, 1f, 1f);

        //draw the train
        draw(drawable, verticesTrain, texturesTrain, indicesTrain, gl);
        gl.glPopMatrix();
        train4.disable();

        //-------------------------------Draw the 5th train (yellow)------------
        //start drawing train5 (yellow)
        gl.glPushMatrix();
        train5.enable();
        train5.bind();
        gl.glTranslatef(t5x, t5y, t5z);
        gl.glRotated(angle5, r5x, r5y, r5x);
        gl.glScalef(s5, s5, s5);
        gl.glColor3f(1f, 1f, 1f);

        //Draw the train
        draw(drawable, verticesTrain, texturesTrain, indicesTrain, gl);
        gl.glPopMatrix();
        train5.disable();
        //----------------------------------------------------------------------

        //disable shading
        gl.glDisable(gl.GL_DEPTH_TEST);
        gl.glDisable(gl.GL_LIGHT0);
        gl.glDisable(gl.GL_COLOR_MATERIAL);
    }
    
    //-----------------------------------***Object Loader from blender Methods***-------------------------
    //--------------------------------------------------------------------------
    public static void draw(GLAutoDrawable drawable, float[] postions, float[] textureCod, int[] indices, GL gl) {
        //begin drawing triangles using the vertices and textures coordinates of the .obj blender file
        gl.glBegin(GL.GL_TRIANGLES);
        for (int i = 0, j = 0; i < indices.length; i++) {
            gl.glTexCoord2f(textureCod[indices[i] * 2], textureCod[indices[i] * 2 + 1]);
            gl.glVertex3f(postions[indices[i] * 3], postions[indices[i] * 3 + 1], postions[indices[i] * 3 + 2]);

        }
        gl.glEnd();

    }
     
    //-Method to Get Object vertices from .obj file-----------------------------
    public static float[] getVerticesArray(String fileName) {
        
        FileReader fr = null;
        
        try {
            //path to .obj file
            fr = new FileReader(new File(fileName + ".obj"));
        } catch (FileNotFoundException ex) {
            System.err.println("OBJ File not found");
            ex.printStackTrace();
        }
        
        //reading the .obj file vertices
        BufferedReader reader = new BufferedReader(fr);
        String line;

        float[] verticesArray = null;
        try {
            while (true) {
                line = reader.readLine();
                String[] currentLine = line.split(" ");
                if (line.startsWith("v ")) {

                    float[] vertex = {Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3])};
                    vertices.add(vertex);// add vertex to the vertices list
                } else if (line.startsWith("vt ")) {
                    break;
                }
            }

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        verticesArray = new float[vertices.size() * 3];

        int vertexPointer = 0;
        for (float[] verex : vertices) {
            verticesArray[vertexPointer++] = verex[0];
            verticesArray[vertexPointer++] = verex[1];
            verticesArray[vertexPointer++] = verex[2];
        }
        return verticesArray;
    }
    
    //-Method to Get Object texture vertices from .obj file --------------------
    public static float[] getTexturesArray(String fileName) {
        
        FileReader fr = null;
        
        try {
            //path to .obj file
            fr = new FileReader(new File(fileName + ".obj"));
        } catch (FileNotFoundException ex) {
            System.err.println("OBJ File not found");
            ex.printStackTrace();
        }
        
        //read texture coordinates from the .obj file
        BufferedReader reader = new BufferedReader(fr);
        String line;

        float[] texturesArray = null;
        try {
            while (true) {
                line = reader.readLine();
                String[] currentLine = line.split(" ");
                if (line.startsWith("vt ")) {
                    float[] texture = {Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2])};
                    textureCord.add(texture);//add texture coordinates to the texture coordinates list
                } else if (line.startsWith("f ")) {
                    texturesArray = new float[vertices.size() * 2];
                    break;
                }
            }

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return texturesArray;
    }

    //--Method to Get object indices -----------------------------------------------------
    public static int[] getIndicesArray(String fileName, float[] verticesArray, float[] texturesArray) {

        FileReader fr = null;
        try {
            //path to .obj file
            fr = new FileReader(new File(fileName + ".obj"));
        } catch (FileNotFoundException ex) {
            System.err.println("OBJ File not found");
            ex.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(fr);
        String line;

        List<Integer> indices = new ArrayList<Integer>();
        int[] indicesArray = null;
        try {
            while (true) {
                line = reader.readLine();
                String[] currentLine = line.split(" ");
                if (line.startsWith("f ")) {

                    break;
                }
            }
            while (line != null) {
                if (!line.startsWith("f ")) {
                    line = reader.readLine();
                    continue;
                }
                String[] currentLine = line.split(" ");
                String[] vertex1 = currentLine[1].split("/");
                String[] vertex2 = currentLine[2].split("/");
                String[] vertex3 = currentLine[3].split("/");
                processVertex(vertex1, indices, textureCord, texturesArray);
                processVertex(vertex2, indices, textureCord, texturesArray);
                processVertex(vertex3, indices, textureCord, texturesArray);
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        indicesArray = new int[indices.size()];

        for (int i = 0;
                i < indices.size();
                i++) {
            indicesArray[i] = indices.get(i);
        }
        textureCord.clear();
        vertices.clear();
        return indicesArray;
    }

    //--Method to process object vertex-----------------------------------------
    private static void processVertex(String[] vertexdata,List<Integer> indices, List<float[]> texture, float[] textureArray) {

        int currentVerexPointer = Integer.parseInt(vertexdata[0]) - 1;
        indices.add(currentVerexPointer);

        float[] currentTex = texture.get(Integer.parseInt(vertexdata[1]) - 1);
        textureArray[currentVerexPointer * 2] = currentTex[0];
        textureArray[currentVerexPointer * 2 + 1] = 1 - currentTex[1];

    }

    /*---------------------------------------------------------END MOVING OBJECT FROM BLENDER METHODS-------------------------------------------------------------------*/

    //-Not Used-----------------------------------------------------------------
    @Override
    public void keyTyped(KeyEvent e) {

    }

    //--Method for interaction with keyboared-----------------------------------
    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        /*--------------------------MOVING TRAINS---------------------------------*/
        /*  a,b,c,d,f represent number of train movememts from train1 to train5 respectively
        and it helps in animation and error checking.
        each time train makes a move its value increase by 1 and 
        accourding to the movement value train movement is determined*/
        //----------moving train1(green) by pressing key number1---------------
        if (key == KeyEvent.VK_1) {

            if (a < 10 || (a >= 12 && a < 19)) {//move right
                a++;
                s1 = 0.2f;
                t1x += 0.5;

            } else if (a == 10) {//disappear to enter alu
                a++;
                s1 = 0;

            } else if (a == 11) {//appear and continue moving right
                a++;
                t1x = 2.25f;
                s1 = 0.2f;

            } else if (a == 19) {//rotate to move down
                a++;
                r1x = 0;
                r1y = -1;
                r1z = 0;
                angle1 = 20;
                s1 = 0;

                t1z += 0.5;
                t1y -= 0.5f;
                t1x -= 0.2;

            } else if (a == 20) {//move down
                a++;
                s1 = 0.22f;
                t1z += 0.5;
                t1y -= 0.4f;
                t1x += 0.3;

            } else if (a == 21) {//rotate to move left
                a++;
                r1x = 0;
                r1y = -1;
                r1z = 0;
                angle1 = 90;
                t1y -= 0.5;
                t1x -= 1;

            } else if (a > 21 && a < 33) {//move left
                a++;
                t1x -= 0.7;

            } else if (a == 33) { //rotate to move up 
                a++;
                r1x = 0;
                r1y = 1f;
                r1z = 0;
                angle1 = 196;
                t1y = -0.6f;
                t1z = -8.0f;
                t1x -= 0.6;
                s1 = 0.25f;

            } else {// disappear to enrer regester for write back
                s1 = 0;
            }
        }//End train1 movement
        //-------moving train2(Pink) by pressing key number2---------------
        else if (key == KeyEvent.VK_2) {

            if (b < 10 || (b >= 12 && b < 19)) {//move right
                b++;
                s2 = 0.2f;
                t2x += 0.5;

            } else if (b == 10) {//disappear to enter alu
                b++;
                s2 = 0;

            } else if (b == 11) {//appear and continue moving right
                b++;
                t2x = 2.25f;
                s2 = 0.2f;

            } else if (b == 19) {//rotate to move down
                b++;
                r2x = 0;
                r2y = -1;
                r2z = 0;
                angle2 = 20;
                s2 = 0;

                t2z += 0.5;
                t2y -= 0.5f;
                t2x -= 0.2;

            } else if (b == 20) {//move down
                b++;
                s2 = 0.22f;
                t2z += 0.5;
                t2y -= 0.4f;
                t2x += 0.3;

            } else if (b == 21) {//rotate to move left
                b++;
                r2x = 0;
                r2y = -1;
                r2z = 0;
                angle2 = 90;
                t2y -= 0.5;
                t2x -= 1;

            } else if (b > 21 && b < 33) {//move left
                b++;
                t2x -= 0.7;

            } else if (b == 33) {  //rotate to move up
                b++;
                r2x = 0;
                r2y = 1f;
                r2z = 0;
                angle2 = 196;
                t2y = -0.6f;
                t2z = -8.0f;
                t2x -= 0.6;
                s2 = 0.25f;

            } else {// disappear to enrer regester for write back
                s2 = 0;
            }
        }//End train2 movement 
        //-----------moving train3(blue) by pressing key number3---------------
        else if (key == KeyEvent.VK_3) {

            if (c < 10 || (c >= 12 && c < 19)) {//move right
                c++;
                s3 = 0.2f;
                t3x += 0.5;

            } else if (c == 10) {//disappear to enter alu
                c++;
                s3 = 0;

            } else if (c == 11) {//appear and continue moving right
                c++;
                t3x = 2.25f;
                s3 = 0.2f;

            } else if (c == 19) {//rotate to move down
                c++;
                r3x = 0;
                r3y = -1;
                r3z = 0;
                angle3 = 20;
                s3 = 0;

                t3z += 0.5;
                t3y -= 0.5f;
                t3x -= 0.2;

            } else if (c == 20) {//move down
                c++;
                s3 = 0.22f;
                t3z += 0.5;
                t3y -= 0.4f;
                t3x += 0.3;

            } else if (c == 21) {//rotate to move left
                c++;
                r3x = 0;
                r3y = -1;
                r3z = 0;
                angle3 = 90;
                t3y -= 0.5;
                t3x -= 1;

            } else if (c > 21 && c < 33) {//move left
                c++;
                t3x -= 0.7;

            } else if (c == 33) {  //rotate to move up
                c++;
                r3x = 0;
                r3y = 1f;
                r3z = 0;
                angle3 = 196;
                t3y = -0.6f;
                t3z = -8.0f;
                t3x -= 0.6;
                s3 = 0.25f;

            } else {// disappear to enrer regester for write back
                s3 = 0;
            }
        }//End train3 movement 
        //----moving train4(Purpul)by pressing key number4---------------
        else if (key == KeyEvent.VK_4) {

            if (d < 10 || (d >= 12 && d < 19)) {//move right
                d++;
                s4 = 0.2f;
                t4x += 0.5;

            } else if (d == 10) {//disappear to enter alu
                d++;
                s4 = 0;

            } else if (d == 11) {//appear and continue moving right
                d++;
                t4x = 2.25f;
                s4 = 0.2f;

            } else if (d == 19) {//rotate to move down
                d++;
                r4x = 0;
                r4y = -1;
                r4z = 0;
                angle4 = 20;
                s4 = 0;

                t4z += 0.5;
                t4y -= 0.5f;
                t4x -= 0.2;

            } else if (d == 20) {//move down
                d++;
                s4 = 0.22f;
                t4z += 0.5;
                t4y -= 0.4f;
                t4x += 0.3;

            } else if (d == 21) {//rotate to move left
                d++;
                r4x = 0;
                r4y = -1;
                r4z = 0;
                angle4 = 90;
                t4y -= 0.5;
                t4x -= 1;

            } else if (d > 21 && d < 33) {//move left
                d++;
                t4x -= 0.7;

            } else if (d == 33) {  //rotate to move up
                d++;
                r4x = 0;
                r4y = 1f;
                r4z = 0;
                angle4 = 196;
                t4y = -0.6f;
                t4z = -8.0f;
                t4x -= 0.6;
                s4 = 0.25f;

            } else {// disappear to enrer regester for write back
                s4 = 0;
            }
        }//End train4 movement 
        //----moving train5(yellow)by pressing key number5---------------
        else if (key == KeyEvent.VK_5) {

            if (f < 10 || (f >= 12 && f < 19)) {//move right
                f++;
                s5 = 0.2f;
                t5x += 0.5;

            } else if (f == 10) {//disappear to enter alu
                f++;
                s5 = 0;

            } else if (f == 11) {//appear and continue moving right
                f++;
                t5x = 2.25f;
                s5 = 0.2f;

            } else if (f == 19) {//rotate to move down
                f++;
                r5x = 0;
                r5y = -1;
                r5z = 0;
                angle5 = 20;
                s5 = 0;

                t5z += 0.5;
                t5y -= 0.5f;
                t5x -= 0.2;

            } else if (f == 20) {//move down
                f++;
                s5 = 0.22f;
                t5z += 0.5;
                t5y -= 0.4f;
                t5x += 0.3;

            } else if (f == 21) {//rotate to move left
                f++;
                r5x = 0;
                r5y = -1;
                r5z = 0;
                angle5 = 90;
                t5y -= 0.5;
                t5x -= 1;

            } else if (f > 21 && f < 33) {//move left
                f++;
                t5x -= 0.7;

            } else if (f == 33) {  //rotate to move up
                f++;
                r5x = 0;
                r5y = 1f;
                r5z = 0;
                angle5 = 196;
                t5y = -0.6f;
                t5z = -8.0f;
                t5x -= 0.6;
                s5 = 0.25f;

            } else {// disappear to enrer regester for write back
                s5 = 0;
            }
        }//End train5 movement 
        /*-------------------END MOVING TRAINS--------------------------------*/ //----------Reset  position-------------------
        else if (key == KeyEvent.VK_R) {
            /*-----------Train1 Identity----------*/
            t1x = -5f; //translate x
            t1y = 0.3f; //translate y
            t1z = -8f; //translate z
            r1x = 0;//Rotate x
            r1y = 1;//Rotate y
            r1z = 0;//Rotate z
            angle1 = 80;// Rotate Angle
            s1 = 0;//Scale
            a = 0; //movement counter used for error checking

            /*-----------Train2 Identity----------*/
            t2x = -5f; //Translate x
            t2y = 0.3f; //Translate y
            t2z = -8f; // Translate z
            r2x = 0;//Rotate x
            r2y = 1;//Rotate y
            r2z = 0;//Rotate z
            angle2 = 80;//Rotate Angle
            s2 = 0;//Scale
            b = 0; //movement counter used for error checking

            /*-----------Train3 Identity----------*/
            t3x = -5f; //Translate x
            t3y = 0.3f; //Translate y
            t3z = -8f; // Translate z
            r3x = 0;//Rotate x
            r3y = 1;//Rotate y
            r3z = 0;//Rotate z
            angle3 = 80;//Rotate Angle
            s3 = 0;//Scale
            c = 0; //movement counter used for error checking

            /*-----------Train4 Identity----------*/
            t4x = -5f; //Translate x
            t4y = 0.3f; //Translate y
            t4z = -8f; // Translate z
            r4x = 0;//Rotate x
            r4y = 1;//Rotate y
            r4z = 0;//Rotate z
            angle4 = 80;//Rotate Angle
            s4 = 0;//Scale
            d = 0; //movement counter used for error checking

            /*-----------Train5 Identity----------*/
            t5x = -5f; //Translate x
            t5y = 0.3f; //Translate y
            t5z = -8f; // Translate z
            r5x = 0;//Rotate x
            r5y = 1;//Rotate y
            r5z = 0;//Rotate z
            angle5 = 80;//Rotate Angle
            s5 = 0;//Scale
            f = 0; //movement counter used for error checking

            /*------------error and bye screen reset-------------*/
            errorS = 0;
            byeS = 0;
        } //End reset positios
        /*------------screen appearance------------*/ 
       //press key S to control apperance and disappearance of start-screens
        else if (key == KeyEvent.VK_S) {
       
            screenClicks++;//count number of clicks to use it for controlling witch start-screen to appear and witch to disapper
       
            if (screenClicks == 1) {
                screen1S = 0;//first start-screen (screen1) disappear
                screen2S = 1;//second start-screen (screen2) appear
            }
 
            if (screenClicks == 2) {
                screen2S = 0;//second start-screen (screen2) disappear
                screen3S = 1;//third start-screen (screen3) appear
            }
  
            if (screenClicks == 3) {
                screen3S = 0;//third start-screen (screen3) disappear
            }
        }
    }

    //-Not Used-----------------------------------------------------------------
    @Override
    public void keyReleased(KeyEvent e) {

    }

}//END CLASS
