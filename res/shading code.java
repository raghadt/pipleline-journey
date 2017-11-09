package org.yourorghere;

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
import static javax.media.opengl.GL.GL_POLYGON;
import static javax.media.opengl.GL.GL_TEXTURE_2D;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

/**
 * SimpleJOGL.java <BR>
 * author: Brian Paul (converted to Java by Ron Cemer and Sven Goethel)
 * <P>
 *
 * This version is equal to Brian Paul's version 1.2 1999/10/21
 */
public class SimpleJOGL implements GLEventListener {

    Texture tex; // create object
    Texture sky;
    Texture circle;
    Texture wood;
    Texture memory;
    Texture alu;

    public static void main(String[] args) {
        Frame frame = new Frame("Simple JOGL Application");
        GLCanvas canvas = new GLCanvas();

        canvas.addGLEventListener(new SimpleJOGL());
        frame.add(canvas);
        frame.setSize(1260, 750);
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

    public void init(GLAutoDrawable drawable) {
        // Use debug pipeline
        // drawable.setGL(new DebugGL(drawable.getGL()));

        GL gl = drawable.getGL();
        GLUT glut = new GLUT();
        System.err.println("INIT GL IS: " + gl.getClass().getName());

        // Enable VSync
        gl.setSwapInterval(1);

        // Setup the drawing area and shading mode
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glShadeModel(GL.GL_SMOOTH); // try setting this to GL_FLAT and see what happens.
        gl.glEnable(GL.GL_TEXTURE_2D); //activate texture mapping for 2D

        try {
            //load floor texture
            tex = TextureIO.newTexture(new File("f.png"), true);
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
            //load sky texture
            circle = TextureIO.newTexture(new File("circle.png"), true);
        } catch (IOException ex) {
            System.err.println(ex);
        }

        try {
            //load sky texture
            wood = TextureIO.newTexture(new File("wood.jpeg"), true);
        } catch (IOException ex) {
            System.err.println(ex);
        }

        try {
            //load sky texture
            memory = TextureIO.newTexture(new File("mem.jpg"), true);
        } catch (IOException ex) {
            System.err.println(ex);
        }

        try {
            //load sky texture
            alu = TextureIO.newTexture(new File("alu.png"), true);
        } catch (IOException ex) {
            System.err.println(ex);
        }

    }

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

    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        GLUT glut = new GLUT();
        // Clear the drawing area
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        // Reset the current matrix to the "identity"
        gl.glLoadIdentity();

//-----------------------floor----------------------------------
        tex.enable();
        tex.bind();
        gl.glPushMatrix();
        gl.glColor3f(1, 1, 1);
        gl.glTranslatef(-1.5f, -0.2f, -6.0f);
        gl.glRotatef(-55, 1, 0, 0);
        gl.glBegin(GL.GL_QUADS);
        gl.glEnable(GL.GL_BLEND);
        gl.glTexCoord2f(0, 0);
        gl.glVertex3f(-5.03f, 3.0f, 0.0f);
        gl.glTexCoord2f(1, 0);
        gl.glVertex3f(8.03f, 3.0f, 0.0f);
        gl.glTexCoord2f(1, 1);
        gl.glVertex3f(8.03f, -2.5f, 0.0f);
        gl.glTexCoord2f(0, 1);
        gl.glVertex3f(-5.03f, -2.5f, 0.0f);
        gl.glEnd();
        gl.glPopMatrix();
        tex.disable();

//-----------------------sky----------------------
        sky(sky, gl);

//--------------------------------Circle Rails----------------------------------     
        rail(wood, gl);

        //---------------- memory------------------------------
        mem(memory, gl, glut);

        //---------------- ALU----------------------------------
        
             gl.glEnable(gl.GL_DEPTH_TEST);
        gl.glEnable(gl.GL_LIGHTING);
        gl.glEnable(gl.GL_LIGHT0);
        gl.glDisable(gl.GL_CULL_FACE);
        gl.glEnable(gl.GL_COLOR_MATERIAL);
        
        
        gl.glPushMatrix();
        // Move the "drawing cursor" around
        gl.glTranslatef(1.7f, 2f, -10f);
        gl.glRotated(190, 0, -1, 0);
        gl.glScalef(1, 1, 2);
        gl.glColor3f(0, 0, 1);
        gl.glBegin(GL.GL_TRIANGLES);           // Begin drawing the pyramid with 4 triangles
        // Front
        
        
        //  gl.glColor3f(0.118f, 0.565f, 1.000f);     // Red
        gl.glVertex3f(0.0f, 1.0f, 0.0f);
        //gl.glColor3f(0.118f, 0.565f, 1.000f);     // Green
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);
        //gl.glColor3f(0.118f, 0.565f, 1.000f);    // Blue
        gl.glVertex3f(1.0f, -1.0f, 1.0f);

        // Right
        //    gl.glColor3f(0.255f, 0.412f, 0.882f);     // Red
        gl.glVertex3f(0.0f, 1.0f, 0.0f);
        //   gl.glColor3f(0.255f, 0.412f, 0.882f);     // Blue
        gl.glVertex3f(1.0f, -1.0f, 1.0f);
        //  gl.glColor3f(0.255f, 0.412f, 0.882f);      // Green
        gl.glVertex3f(1.0f, -1.0f, -1.0f);

        gl.glVertex3f(0.0f, 1.0f, 0.0f);
        //gl.glColor3f(0.118f, 0.565f, 1.000f);         // Blue
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        // gl.glColor3f(0.118f, 0.565f, 1.000f);        // Green
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);
        gl.glEnd();   // Done drawing the pyramid

        gl.glTranslatef(-1.5f, -1.4f, -6.0f);

        gl.glPopMatrix();
        
  

        //-----------------------------  REGISTER  ---------------------------------
        gl.glPushMatrix();

        gl.glTranslatef(-4.8f, 1.85f, -11.0f);
        gl.glRotated(15, 1, 0, 0);

        //----begin drawing
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(0.0f, 0.0f, 0.0f);    // Set the current drawing color to light blue 
        gl.glVertex3f(0.0f, 0.0f, 0.0f);  // Top Left
        gl.glVertex3f(1.0f, 0.0f, 0.0f);   // Top Right
        gl.glVertex3f(1.0f, -1.0f, 0.0f);  // Bottom Right
        gl.glVertex3f(0.0f, -1.0f, 0.0f); // Bottom Left

        gl.glTranslatef(4.8f, -1.85f, 11.0f);

        gl.glPopMatrix();

        // Done Drawing The Quad
        gl.glEnd();

        gl.glPushMatrix();

        gl.glTranslatef(1.4f, 0.25f, 0f);
        gl.glRotated(100, 0, 1, 0);
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(0.0f, 0.0f, 0.0f);    // Set the current drawing color to light blue

        gl.glVertex3f(0.0f, 0.0f, 0.0f);  // Top Left
        gl.glVertex3f(1.0f, 0.0f, 0.0f);   // Top Right
        gl.glVertex3f(1.0f, -1.0f, 0.0f);  // Bottom Right
        gl.glVertex3f(0.0f, -1.0f, 0.0f); // Bottom Left
        gl.glPopMatrix();
        // Done Drawing The Quad
        gl.glEnd();

//------------------------------------------------------------------------------        
        // Flush all drawing operations to the graphics card
        gl.glFlush();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

    private void sky(Texture sky1, GL gl) {

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
        gl.glVertex3f(20, 3.6f, -9);
        gl.glTexCoord2f(0, 1);
        gl.glVertex3f(-20, 3.6f, -9);
        gl.glEnd();
        gl.glPopMatrix();
        sky.disable();

    }

    private void rail(Texture wood, GL gl) {
        //----Mid Circle--
        gl.glPushMatrix();
        circle.enable();
        circle.bind();
        gl.glTranslatef(-0.5f, -0.2f, -6.0f);
        gl.glRotatef(55, -1, 0, 0);
        float numPoints1 = 30;
        float Radius1 = 0.33f;
        gl.glBegin(GL.GL_POLYGON);
        for (int i = 0; i < numPoints1; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints1);
            //2.0*PI==360
            double X = Math.cos(Angle) * Radius1;
            double Y = Math.sin(Angle) * Radius1;
            gl.glVertex2d(X, Y);
        }
        gl.glEnd();
        gl.glPopMatrix();

        circle.disable();
        //----------Mid Rails--
        gl.glPushMatrix();
        wood.enable();
        wood.bind();
        gl.glTranslatef(0.15f, 6.9f, -11.0f);
        gl.glRotatef(50, -1, 0, 0);
        gl.glScalef(1f, 0.3f, 1);
        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2f(0, 0);
        gl.glVertex3f(-2, -2f, -9);
        gl.glTexCoord2f(1, 0);
        gl.glVertex3f(-1, -2f, -9);
        gl.glTexCoord2f(1, 1);
        gl.glVertex3f(-1, -1f, -9);
        gl.glTexCoord2f(0, 1);
        gl.glVertex3f(-2, -1f, -9);
        gl.glEnd();
        gl.glPopMatrix();
        wood.disable();

        gl.glPushMatrix();
        wood.enable();
        wood.bind();
        gl.glTranslatef(0.15f, 6.66f, -10.5f);
        gl.glRotatef(50, -1, 0, 0);
        gl.glScalef(1f, 0.3f, 1);
        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2f(0, 0);
        gl.glVertex3f(-2, -2f, -9);
        gl.glTexCoord2f(1, 0);
        gl.glVertex3f(-1, -2f, -9);
        gl.glTexCoord2f(1, 1);
        gl.glVertex3f(-1, -1f, -9);
        gl.glTexCoord2f(0, 1);
        gl.glVertex3f(-2, -1f, -9);
        gl.glEnd();
        gl.glPopMatrix();
        wood.disable();

        gl.glPushMatrix();
        wood.enable();
        wood.bind();
        gl.glTranslatef(0.15f, 6.4f, -10.5f);
        gl.glRotatef(50, -1, 0, 0);
        gl.glScalef(1f, 0.3f, 1);
        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2f(0, 0);
        gl.glVertex3f(-2, -2f, -9);
        gl.glTexCoord2f(1, 0);
        gl.glVertex3f(-1, -2f, -9);
        gl.glTexCoord2f(1, 1);
        gl.glVertex3f(-1, -1f, -9);
        gl.glTexCoord2f(0, 1);
        gl.glVertex3f(-2, -1f, -9);
        gl.glEnd();
        gl.glPopMatrix();
        wood.disable();

        //Rail Line---
        gl.glPushMatrix();
        // gl.glColor3f(1,0,0);
        gl.glBegin(GL.GL_LINES);
        gl.glTranslatef(0.1f, 8f, -6.5f);
        gl.glVertex2f(0.1f, 2.52f);
        gl.glVertex2f(2.1f, 3.52f);
        gl.glEnd();
        gl.glPopMatrix();

        //----Right Circle--
        gl.glPushMatrix();
        circle.enable();
        circle.bind();
        gl.glTranslatef(-2.72f, -0.22f, -6.0f);
        gl.glRotatef(55, -1, 0, 0);
        float numPoints2 = 30;
        float Radius2 = 0.33f;
        gl.glBegin(GL.GL_POLYGON);
        for (int i = 0; i < numPoints2; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints2);
            //2.0*PI==360
            double X = Math.cos(Angle) * Radius2;
            double Y = Math.sin(Angle) * Radius2;
            gl.glVertex2d(X, Y);
        }
        gl.glEnd();
        gl.glPopMatrix();
        circle.disable();

        //-----Right Rail----------------------
        gl.glPushMatrix();
        wood.enable();
        wood.bind();
        gl.glTranslatef(-5.72f, 6.9f, -11.0f);
        gl.glRotatef(50, -1, 0, 0);
        gl.glScalef(1f, 0.3f, 1);
        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2f(0, 0);
        gl.glVertex3f(-2, -2f, -9);
        gl.glTexCoord2f(1, 0);
        gl.glVertex3f(-1, -2f, -9);
        gl.glTexCoord2f(1, 1);
        gl.glVertex3f(-1, -1f, -9);
        gl.glTexCoord2f(0, 1);
        gl.glVertex3f(-2, -1f, -9);
        gl.glEnd();
        gl.glPopMatrix();
        wood.disable();

        gl.glPushMatrix();
        wood.enable();
        wood.bind();
        gl.glTranslatef(-5.72f, 6.63f, -10.5f);
        gl.glRotatef(50, -1, 0, 0);
        gl.glScalef(1f, 0.3f, 1);
        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2f(0, 0);
        gl.glVertex3f(-2, -2f, -9);
        gl.glTexCoord2f(1, 0);
        gl.glVertex3f(-1, -2f, -9);
        gl.glTexCoord2f(1, 1);
        gl.glVertex3f(-1, -1f, -9);
        gl.glTexCoord2f(0, 1);
        gl.glVertex3f(-2, -1f, -9);
        gl.glEnd();
        gl.glPopMatrix();
        wood.disable();

        gl.glPushMatrix();
        wood.enable();
        wood.bind();
        gl.glTranslatef(-6f, 6.3f, -10.5f);
        gl.glRotatef(50, -1, 0, 0);
        gl.glScalef(1f, 0.3f, 1);
        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2f(0, 0);
        gl.glVertex3f(-2, -2f, -9);
        gl.glTexCoord2f(1, 0);
        gl.glVertex3f(-1, -2f, -9);
        gl.glTexCoord2f(1, 1);
        gl.glVertex3f(-1, -1f, -9);
        gl.glTexCoord2f(0, 1);
        gl.glVertex3f(-2, -1f, -9);
        gl.glEnd();
        gl.glPopMatrix();
        wood.disable();

        //----Up Circle--
        gl.glPushMatrix();
        circle.enable();
        circle.bind();
        gl.glTranslatef(-0.67f, 1.07f, -10.0f);
        gl.glRotatef(55, -1, 0, 0);
        float numPoints = 30;
        float Radius = 0.47f;
        gl.glBegin(GL.GL_POLYGON);
        for (int i = 0; i < numPoints; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints);
            //2.0*PI==360
            double X = Math.cos(Angle) * Radius;
            double Y = Math.sin(Angle) * Radius;
            gl.glVertex2d(X, Y);
        }
        gl.glEnd();
        gl.glPopMatrix();
        circle.disable();
        //----Up Rail--------------------
        gl.glPushMatrix();
        wood.enable();
        wood.bind();
        gl.glTranslatef(0.4f, 9.05f, -11.0f);
        gl.glRotatef(50, -1, 0, 0);
        gl.glScalef(1f, 0.3f, 1);
        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2f(0, 0);
        gl.glVertex3f(-2, -2f, -9);
        gl.glTexCoord2f(1, 0);
        gl.glVertex3f(-1, -2f, -9);
        gl.glTexCoord2f(1, 1);
        gl.glVertex3f(-1, -1f, -9);
        gl.glTexCoord2f(0, 1);
        gl.glVertex3f(-2, -1f, -9);
        gl.glEnd();
        gl.glPopMatrix();
        wood.disable();

        gl.glPushMatrix();
        wood.enable();
        wood.bind();
        gl.glTranslatef(0.4f, 8.83f, -11.0f);
        gl.glRotatef(50, -1, 0, 0);
        gl.glScalef(1f, 0.3f, 1);
        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2f(0, 0);
        gl.glVertex3f(-2, -2f, -9);
        gl.glTexCoord2f(1, 0);
        gl.glVertex3f(-1, -2f, -9);
        gl.glTexCoord2f(1, 1);
        gl.glVertex3f(-1, -1f, -9);
        gl.glTexCoord2f(0, 1);
        gl.glVertex3f(-2, -1f, -9);
        gl.glEnd();
        gl.glPopMatrix();
        wood.disable();

    }

    private void mem(Texture memory, GL gl, GLUT glut) {

        //---------------------mem--------------
        //shading
        gl.glEnable(gl.GL_DEPTH_TEST);
        gl.glEnable(gl.GL_LIGHTING);
        gl.glEnable(gl.GL_LIGHT0);
        gl.glDisable(gl.GL_CULL_FACE);
        gl.glEnable(gl.GL_COLOR_MATERIAL);

        gl.glPushMatrix();
        memory.enable();
        memory.bind();

        gl.glTranslatef(3.76f, 1.05f, -7f);
        gl.glRotated(20, 1, 0, 0);
        gl.glRotated(-90, 0, 1, 0);
        gl.glScaled(1, 0.7, 1);
        gl.glScaled(1.5, 1, 1);

        glut.glutSolidCube(1);

        gl.glDisable(gl.GL_LIGHTING);
        gl.glDisable(gl.GL_DEPTH_TEST);
        gl.glDisable(gl.GL_LIGHTING);
        gl.glDisable(gl.GL_LIGHT0);

        gl.glDisable(gl.GL_COLOR_MATERIAL);

        gl.glPopMatrix();
        memory.disable();

        //--------------Regmem----------------------------------
        gl.glEnable(gl.GL_DEPTH_TEST);
        gl.glEnable(gl.GL_LIGHTING);
        gl.glEnable(gl.GL_LIGHT0);
        gl.glDisable(gl.GL_CULL_FACE);
        gl.glEnable(gl.GL_COLOR_MATERIAL);

        gl.glPushMatrix();
        gl.glColor3f(1, 0, 0);

        gl.glTranslatef(-3.3f, 1.5f, -9f);
        gl.glRotated(20, 1, 0, 0);
        gl.glRotated(-90, 0, 1, 0);
        gl.glScaled(1, 1.2, 1);
        gl.glScaled(1.5, 1, 1);

        glut.glutSolidCube(1);

        gl.glDisable(gl.GL_LIGHTING);
        gl.glDisable(gl.GL_DEPTH_TEST);
        gl.glDisable(gl.GL_LIGHTING);
        gl.glDisable(gl.GL_LIGHT0);

        gl.glDisable(gl.GL_COLOR_MATERIAL);
        gl.glPopMatrix();

    }
}
