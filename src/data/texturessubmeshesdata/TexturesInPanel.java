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
package data.texturessubmeshesdata;

import data.model.TextureType;
import imageprocessing.ColoringImage;
import imageprocessing.ImagesProcessing;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;


public class TexturesInPanel 
{
    private boolean multiSelection;
    //HashMap<Texture, boolean = if this texture is selected>
    //private HashMap<TextureType,Boolean> textures;
    private HashMap<TextureType,AttrTexture> textures;
    
    public TexturesInPanel(boolean multiSelection)
    {
        this.multiSelection = multiSelection;
        textures = new HashMap<TextureType,AttrTexture>();
    }  
    
    public void addTexture(TextureType texture, boolean isCheck)
    {
        textures.put(texture, new AttrTexture(isCheck));
    }
    
    /*
     * Return a list with the activated textures in this panel
     */
    public ArrayList<TextureType> getCheckedTextures()
    {
        ArrayList<TextureType> listCheckedTextures = new ArrayList<TextureType>();
        Set<TextureType> setTextureType = textures.keySet();
        Iterator<TextureType> it = setTextureType.iterator();
        while(it.hasNext())
        {
            TextureType texture = it.next();
            if (textures.get(texture).isSelected()){
               listCheckedTextures.add(texture);
           } 
        }
        return listCheckedTextures;
    }
    
    public boolean ischecked(String idTexture){
        boolean checked = false;
        Set<TextureType> setTextureType = textures.keySet();
        Iterator<TextureType> it = setTextureType.iterator();
        while(it.hasNext()){
            TextureType texture = it.next();
            if (texture.getIdTexture().equals(idTexture)){
                checked = textures.get(texture).isSelected();
            }
        }
        return checked;
    }
    
    /*
     * Change the texture with idTexture.
     * If the panel has multiselection, swap the value of this texture.
     * Else we set the value of the other textures to false
     */
    public void changeTexture(String idTexture)
    {
        Set<TextureType> keySet = textures.keySet();
        Iterator<TextureType> it = keySet.iterator();
        while(it.hasNext())
        {
            TextureType texture = it.next();
            if (texture.getIdTexture().equals(idTexture))
            {
                if (multiSelection){
                    //boolean b = textures.get(texture).isSelected();
                    //textures.put(texture,!b);
                    AttrTexture attrTexture = textures.get(texture);
                    attrTexture.setSelected(!attrTexture.isSelected());
                    //textures.put(texture,attrTexture);
                }
                else {
                    //textures.put(texture,true);
                    AttrTexture attrTexture = textures.get(texture);
                    attrTexture.setSelected(true);
                    //textures.put(texture,attrTexture);
                }
            }
            else if (!multiSelection) {
                //textures.put(texture,false);
                AttrTexture attrTexture = textures.get(texture);
                attrTexture.setSelected(false);
                //textures.put(texture,attrTexture);
            }
        }        
    }
    
    public void changeColor(String idTexture, float red, float green, float blue){
        Set<TextureType> keySet = textures.keySet();
        Iterator<TextureType> it = keySet.iterator();
        while(it.hasNext()){
            TextureType texture = it.next();
            if (texture.getIdTexture().equals(idTexture)){
                BufferedImage bi = ColoringImage.coloringImage(texture, new Color(red, green, blue));
                AttrTexture attrTexture = textures.get(texture);
                attrTexture.setBufferedImage(bi);
            }
        }        
    }
    
    //Dada una textura, devuelvo el BufferedImage asociado  
    
    //Comprobar que si es null, hay que generarlo
    public BufferedImage getBufferedImage(TextureType texture){
        if (textures.get(texture).getBufferedImage() == null){
            //Si el BufferedImage es null, es porque no se ha creado todavía. Hay que crearlo con imageProcessing.
            textures.get(texture).setBufferedImage(ImagesProcessing.createBufferedImage(texture));
        }
        return textures.get(texture).getBufferedImage();
    }
}