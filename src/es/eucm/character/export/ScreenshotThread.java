/*******************************************************************************
 * <eAdventure Character Configurator> is a research project of the <e-UCM>
 *          research group.
 *
 *    Developed by: Alejandro Muñoz del Rey, Sergio de Luis Nieto and David González
 *    Ledesma.
 *    Under the supervision of Baltasar Fernández-Manjón and Javier Torrente
 * 
 *    Copyright 2012-2013 <e-UCM> research group.
 *  
 *     <e-UCM> is a research group of the Department of Software Engineering
 *          and Artificial Intelligence at the Complutense University of Madrid
 *          (School of Computer Science).
 *  
 *          C Profesor Jose Garcia Santesmases sn,
 *          28040 Madrid (Madrid), Spain.
 *  
 *          For more info please visit:  <http://character.e-ucm.es>, 
 *          <http://e-adventure.e-ucm.es> or <http://www.e-ucm.es>
 *  
 *  ****************************************************************************
 *      <eAdventure Character Configurator> is free software: you can 
 *      redistribute it and/or modify it under the terms of the GNU Lesser 
 *      General Public License as published by the Free Software Foundation, 
 *      either version 3 of the License, or (at your option) any later version.
 *  
 *      <eAdventure Character Configurator> is distributed in the hope that it 
 *      will be useful, but WITHOUT ANY WARRANTY; without even the implied 
 *      warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 *      See the GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with <eAdventure Character Configurator>. If not, 
 *      see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package es.eucm.character.export;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.LoopMode;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.ViewPort;
import es.eucm.character.loader.Configuration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ScreenshotThread extends Thread{
    private static final Logger logger = Logger.getLogger(ScreenshotThread.class.getName());
    
    private static final int quality = 10;
    
    private Configuration config;
    private ScreenshotMyAppState screenShotState;
    private AnimChannel channel;
    private ViewPort guiViewPort;
    private NiftyJmeDisplay niftyDisplay;    
    private ArrayList<String> listAnimations;
    private ArrayList<Integer> listQualities;
    private ArrayList<CameraValues> listCameras;
    private float stepAnimationTime;
    
    public ScreenshotThread(Configuration config, ScreenshotMyAppState screeShotState,AnimChannel channel,
            ViewPort guiViewPort,NiftyJmeDisplay niftyDisplay,ArrayList<String> listAnimations, 
            ArrayList<Integer> listQualities, ArrayList<CameraValues> listCameras){
        super();
        this.config = config;
        this.screenShotState = screeShotState;
        this.channel = channel;
        this.guiViewPort = guiViewPort;
        this.niftyDisplay = niftyDisplay;
        this.listAnimations = listAnimations; 
        this.listQualities = listQualities;
        this.listCameras = listCameras;
    }
    
    @Override
    public void run(){
        try {
            String dirScreenshots = config.getProperty(Configuration.DEFAULT_EXPORT_PATH);
            int cont = 1;
            GenerateAnimation generateAnimation = new GenerateAnimation();
            generateAnimation.cleanDirectory(dirScreenshots);
            
            ArrayListQueue<ScreenshotData> queue = new ArrayListQueue();
            screenShotState.setQueue(queue);
            ScreenshotWritter sw = new ScreenshotWritter(queue, config.getProperty(Configuration.DEFAULT_EXPORT_PATH));
            sw.start();
            
            Iterator<String> it = listAnimations.iterator();
            while(it.hasNext()){
                String nameAnimation = it.next();
                
                screenShotState.setAnimationName(nameAnimation);
                screenShotState.resetShotIndex();
                
                channel.setAnim(nameAnimation);
                channel.setLoopMode(LoopMode.DontLoop);
                //Redondeo
                int numScreenShots = Math.round(channel.getAnimMaxTime() * quality); 
                stepAnimationTime = (channel.getAnimMaxTime() * 1000 / numScreenShots);
                /*ScreenshotWritter sw = new ScreenshotWritter(queue, config.getProperty(Configuration.DEFAULT_EXPORT_PATH));
                sw.start();*/
                float time = 0.0f;
                for(int j= 1 ; j<=numScreenShots; j++){
                    System.out.println("Captura antes "+j+" con tiempo "+System.currentTimeMillis());
                    time = time+(stepAnimationTime/1000);
                    channel.setTime(time);
                    synchronized (this){
                        screenShotState.takeScreenshot(this);
                        wait();
                    }
                    System.out.println("Captura despues "+j+" con tiempo "+System.currentTimeMillis());
                    cont++;
                    sleep((long)stepAnimationTime);
                    //time = channel.getTime();
                }
                /*sw.setTerminate(true);
                sw.join();//Para que de tiempo a guardar las que quedan (sino esta, cuando sale el mensaje de cerrar la app, sigue guardando)*/
                //generateAnimation.createAnimation(dirScreenshots, nameAnimation, imagesNames);
            }
            
            sw.setTerminate(true);
            sw.join();//Para que de tiempo a guardar las que quedan (sino esta, cuando sale el mensaje de cerrar la app, sigue guardando)
                
                
            //generateAnimation.saveZIP("assets/Textures/Screenshots.zip", dirScreenshots);
            System.out.println("ScreenshotThread OK");
            //guiViewPort.addProcessor(niftyDisplay);            
         } 
        catch (InterruptedException ex) {
                Logger.getLogger(ScreenshotThread.class.getName()).log(Level.SEVERE, null, ex);
        }      
    }
}