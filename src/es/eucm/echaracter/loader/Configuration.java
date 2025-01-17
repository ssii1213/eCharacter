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
package es.eucm.echaracter.loader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Loading of the initial configuration from the .properties file.
 * In case the .properties file doesn´t exist, loading of a default configuration.
 */
public class Configuration { 
    
    public final static String USER_PATH = System.getProperty("user.home");
    
    public final static String APPLICATION_PATH = USER_PATH+File.separator+"eCharacter";
    
    /** Properties file name */
    public final static String PROPERTIES_FILE_NAME = APPLICATION_PATH+File.separator+"initConfig.properties";
 
    //Resolution
    public final static String RESOLUTION = "resolution";
 
    //Assets paths
    public final static String ASSETS_PATH = "assetsPath";
    
    //Families path
    public final static String FAMILIES_PATH = "familiesPath";    
    
    //Families repository path
    public final static String FAMILIES_REPO_PATH = "familiesRepoPath"; 
    
    //Locale path
    public final static String LOCALE_PATH = "localePath";
    
    //Default quality
    public final static String DEFAULT_QUALITY = "defaultQualityFps";
    
    //Default camera name
    public final static String DEFAULT_CAMERA_NAME = "defaultCameraName";
    
    //Default camera: Vector Position
    public final static String DEFAULT_VECTOR_POSITION = "defaultVectorPosition";
    
    //Default camera: Vector Direction
    public final static String DEFAULT_VECTOR_DIRECTION = "defaultVectorDirection";
    
     //Default camera: Vector Up
    public final static String DEFAULT_VECTOR_UP = "defaultVectorUp";
    
    //Default export path
    public final static String DEFAULT_EXPORT_PATH = "defaultExportPath";
    
    //Defult temp path
    public final static String DEFAULT_TEMP_PATH = "defaultTempPath";
    
    //FOR API
    public final static String INPUT_DEFAULT_LANG = "lang";
    public final static String INPUT_DEFAULT_FAMILY = "defaultFamily";
    public final static String INPUT_DEFAULT_MODEL = "defaultModel";
    public final static String INPUT_DEFAULT_STAGE = "defaultStage";
    public final static String INPUT_DEFAULT_CAMERA = "defaultCamera";
    public final static String INPUT_DEFAULT_ANIMATIONS = "defaultAnimations";
    public final static String INPUT_DEFAULT_QUALITY = "defaultQuality";
    
    Properties properties;
    
    
    public Configuration(){
        File f = new File(USER_PATH+File.separator+"eCharacter");
        if (!f.exists()){
            f.mkdirs();
        }
        properties = new Properties();
    }
    
    public String getProperty(String Key){
        return this.properties.getProperty(Key); 
    }
    
    public void setProperty(String Key,String value){
        this.properties.setProperty(Key, value);
        //Saves the changes in the .properties file
        try {            
            FileOutputStream output = new FileOutputStream(PROPERTIES_FILE_NAME);
            this.properties.store(output,null);
            output.close();
        } catch (IOException ex) {
            Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadPropertiesFile(InputStream input){
        try {
            properties.load(input);
            input.close();
        } catch (IOException ex) {
            Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadDefaultProperties(boolean saveFile){
        //Default configuration
        this.properties.setProperty(RESOLUTION, "1024x768");
        this.properties.setProperty(ASSETS_PATH, "assets");
        this.properties.setProperty(FAMILIES_PATH, "assets/Families");
        this.properties.setProperty(FAMILIES_REPO_PATH, APPLICATION_PATH+"/assets/Families");
        this.properties.setProperty(LOCALE_PATH,"assets/Locale/Gui");
        this.properties.setProperty(DEFAULT_QUALITY, "10");
        this.properties.setProperty(DEFAULT_CAMERA_NAME, "DefaultCamera");
        this.properties.setProperty(DEFAULT_VECTOR_POSITION, "0.0,0.0,11.0");
        this.properties.setProperty(DEFAULT_VECTOR_DIRECTION, "0.0,0.0,-1.0");
        this.properties.setProperty(DEFAULT_VECTOR_UP, "0.0,1.0,0.0");
        this.properties.setProperty(DEFAULT_EXPORT_PATH, APPLICATION_PATH+"/export");
        this.properties.setProperty(DEFAULT_TEMP_PATH, APPLICATION_PATH+"/temp");
        
        if(saveFile){
            //Saves the changes in the .properties file
            try {            
                FileOutputStream output = new FileOutputStream(PROPERTIES_FILE_NAME); 
                this.properties.store(output,null);
                output.close();
            } catch (IOException ex) {
                Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }
        
    }
}
