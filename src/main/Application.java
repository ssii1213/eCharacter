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

package main;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.ScreenshotAppState;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.system.AppSettings;
import control.Control;
import de.lessvoid.nifty.Nifty;
import gui.Gui;
import java.io.File;
import loader.Configuration;

public class Application extends SimpleApplication{

    private Gui gui;
    private ScreenshotAppState screenShotState;
    private NiftyJmeDisplay niftyDisplay;
    private Control control;
    private Configuration config;
    
    
    public Application(int width,int height,Configuration config)
    {
        //Creation of the new configuration
        AppSettings newSettings = new AppSettings(true);
        newSettings.setResolution(width,height);
        this.setShowSettings(false);
        this.setSettings(newSettings);
        this.config = config;
    }
    
    @Override
    public void simpleInitApp() {
        initKeys();
        setDisplayFps(false);
        setDisplayStatView(false);
        // Register locator to assetManager
        assetManager.registerLocator("."+File.separator, FileLocator.class);      
        control = new Control(config,rootNode,assetManager);
        gui = new Gui(control,config);
        stateManager.attach(gui);
        screenShotState = new ScreenshotAppState();
        stateManager.attach(screenShotState);
        
        /**
        * Åctivate the Nifty-JME integration: 
        */
        niftyDisplay = new NiftyJmeDisplay(assetManager, inputManager, audioRenderer, guiViewPort);
        Nifty nifty = niftyDisplay.getNifty();
        guiViewPort.addProcessor(niftyDisplay);
        nifty.fromXml("assets/Interface/screen.xml", "start", gui);
        //nifty.setDebugOptionPanelColors(true);

        flyCam.setDragToRotate(true); // you need the mouse for clicking now
        flyCam.setEnabled(false);
    }
    
     public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) 
    {}
 
    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) 
    {}
    
     /** Custom Keybindings: Mapping a named action to a key input. */
    private void initKeys() {
        inputManager.addMapping("RotateLeft", new KeyTrigger(KeyInput.KEY_LEFT));
        inputManager.addMapping("RotateRight", new KeyTrigger(KeyInput.KEY_RIGHT));
        inputManager.addMapping("RestartRotate", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(actionListener, "RotateLeft");
        inputManager.addListener(actionListener, "RotateRight");
        inputManager.addListener(actionListener, "RestartRotate");
  }

    /** Definining the named action that can be triggered by key inputs. */
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean keyPressed, float tpf) {
            if (name.equals("RotateLeft") && !keyPressed) {
                control.rotateModel(-10.0f);                    
            }
            if (name.equals("RotateRight") && !keyPressed){
                control.rotateModel(10.0f);
            }
            if (name.equals("RestartRotate") && !keyPressed){
                control.restartRotateModel();
            }
        }
    };
}