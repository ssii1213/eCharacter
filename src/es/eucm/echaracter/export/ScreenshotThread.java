/*******************************************************************************
 * <eCharacter> is a research project of the <e-UCM>
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
 *          For more info please visit:  <http://echaracter.e-ucm.es>, 
 *          <http://e-adventure.e-ucm.es> or <http://www.e-ucm.es>
 *  
 *  ****************************************************************************
 *      <eCharacter> is free software: you can 
 *      redistribute it and/or modify it under the terms of the GNU Lesser 
 *      General Public License as published by the Free Software Foundation, 
 *      either version 3 of the License, or (at your option) any later version.
 *  
 *      <eCharacter> is distributed in the hope that it 
 *      will be useful, but WITHOUT ANY WARRANTY; without even the implied 
 *      warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 *      See the GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with <eCharacter>. If not, 
 *      see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package es.eucm.echaracter.export;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.LoopMode;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.ViewPort;
import es.eucm.echaracter.api.Callback;
import es.eucm.echaracter.control.SceneControl;
import es.eucm.echaracter.loader.Configuration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ScreenshotThread extends Thread{
    private static final Logger logger = Logger.getLogger(ScreenshotThread.class.getName());
    
    private SceneControl sc;
    private Configuration config;
    private Callback callback;
    private ScreenshotMyAppState screenShotState;
    private AnimChannel channel;
    private NiftyJmeDisplay niftyDisplay;    
    private ArrayList<String> listAnimations;
    private ArrayList<Integer> listQualities;
    private ArrayList<CameraValues> listCameras;
    private float stepAnimationTime;
    
    public ScreenshotThread(SceneControl sc,Configuration config, Callback callback,ScreenshotMyAppState screeShotState,AnimChannel channel,
            NiftyJmeDisplay niftyDisplay,ArrayList<String> listAnimations, 
            ArrayList<Integer> listQualities, ArrayList<CameraValues> listCameras){
        super();
        this.sc = sc;
        this.config = config;
        this.callback = callback;
        this.screenShotState = screeShotState;
        this.channel = channel;
        this.niftyDisplay = niftyDisplay;
        this.listAnimations = listAnimations; 
        this.listQualities = listQualities;
        this.listCameras = listCameras;
    }
    
    @Override
    public void run(){
        try {
            String dirScreenshots = config.getProperty(Configuration.DEFAULT_TEMP_PATH);
            GenerateAnimation.cleanDirectory(dirScreenshots);
            ZIPWritter zipWritter = new ZIPWritter(config.getProperty(Configuration.DEFAULT_EXPORT_PATH), "eCharacter.zip");
            //Recorremos las camaras
            Iterator<CameraValues> itCameras = listCameras.iterator();
            while(itCameras.hasNext()){
                CameraValues cameraValues = itCameras.next(); 
                sc.setCameraView(cameraValues.getPosition(),cameraValues.getDirection(),cameraValues.getUp());
                //Recorremos las animaciones
                Iterator<String> itAnimations = listAnimations.iterator();
                while(itAnimations.hasNext()){
                    String nameAnimation = itAnimations.next();
                    //Recorremos las calidades
                    Iterator<Integer> itQualities = listQualities.iterator();
                    while(itQualities.hasNext()){      
                        int quality = itQualities.next();    
                        String nameAnimationToSave = nameAnimation+cameraValues.getCameraName()+quality+"fps";
                        screenShotState.resetShotIndex();
                        screenShotState.resetMinMaxImage();
                        screenShotState.setAnimationName(nameAnimationToSave);
                        screenShotState.setFilePath(config.getProperty(Configuration.DEFAULT_TEMP_PATH));
                        //Para dar tiempo a que el setAnim se aplique
                        channel.setAnim(nameAnimation);
                        channel.setLoopMode(LoopMode.DontLoop);
                        sleep(60);
                        //Redondeo
                        int numScreenShots = Math.round(channel.getAnimMaxTime() * quality); 
                        stepAnimationTime = (channel.getAnimMaxTime() * 1000 / numScreenShots);
                        ArrayList<String> listAnimationsToSave = new ArrayList<String>();
                        float time = 0.0f;
                        for(int j= 1 ; j<=numScreenShots; j++){
                            System.out.println("Captura antes "+j+" con tiempo "+System.currentTimeMillis());
                            if (j!=1){
                                time = time+(stepAnimationTime/1000);
                            }
                            channel.setTime(time);
                            //Para dar tiempo a que el setTime se aplique
                            sleep(100);
                            synchronized (this){
                                screenShotState.takeScreenshot(this);
                                this.wait();
                            }
                            listAnimationsToSave.add(nameAnimationToSave+j+".png");
                            System.out.println("Captura despues "+j+" con tiempo "+System.currentTimeMillis());
                            sleep((long)stepAnimationTime);
                        }
                        ScreenshotWritter sw = new ScreenshotWritter(dirScreenshots, nameAnimationToSave, listAnimationsToSave, zipWritter,
                                screenShotState.getCutWmin(), screenShotState.getCutWmax(), screenShotState.getCutHmin(), screenShotState.getCutHmax());
                        sw.start();
                        sw.join();
                    }
                }
            }
            zipWritter.closeZip();
            if(callback != null){
                sc.quitGame();
            }
            else{
                niftyDisplay.getNifty().gotoScreen("popupScreen");
                sc.loadBackground();
            }
         } 
        catch (InterruptedException ex) {
                Logger.getLogger(ScreenshotThread.class.getName()).log(Level.SEVERE, null, ex);
        }      
    }
}