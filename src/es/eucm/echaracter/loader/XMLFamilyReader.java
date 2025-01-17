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

import es.eucm.echaracter.data.family.Family;
import es.eucm.echaracter.types.XMLType;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class XMLFamilyReader {
    private ArrayList<FamilyWithPath> list;
    private String path1, path2;
    private XMLValidator xmlValidator;
    
    public XMLFamilyReader(String path1, String path2){
        this.path1 = path1;
        this.path2 = path2;
        list = new ArrayList<FamilyWithPath>();
        xmlValidator = new XMLValidator();
    }
    
    public ArrayList<FamilyWithPath> readXML(){
        /** Check the type of the XML */
        //Installation directory
        File dirPath = new File(path1);
        if (dirPath.isDirectory()){
            File[] ficheros = dirPath.listFiles();
            for (int x=0;x<ficheros.length;x++){
                File file = ficheros[x];
                /** Check if the file is a file, the extension is "xml" and validate the XML with the XSD */
                if (! file.isDirectory() && getExtension(file.getPath()).equals("xml") && xmlValidator.checkXML(file.getPath(), XMLType.family)) {
                    try {    
                        FamilyWithPath family = new FamilyWithPath(file.getPath(), unmarshal(ResourceLocator.getResource(file.getPath())));
                        list.add(family);
                    } catch (JAXBException ex) {
                        Logger.getLogger(XMLReader.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        else{
            if (getExtension(dirPath.getPath()).equals("xml") && xmlValidator.checkXML(dirPath.getPath(), XMLType.family)) {
                try {    
                    FamilyWithPath family = new FamilyWithPath(dirPath.getPath(), unmarshal(ResourceLocator.getResource(dirPath.getPath())));    
                    list.add(family);
                    } catch (JAXBException ex) {
                        Logger.getLogger(XMLReader.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
        }
        //User directory
        dirPath = new File(path2);
        if (dirPath.isDirectory()){
            File[] ficheros = dirPath.listFiles();
            for (int x=0;x<ficheros.length;x++){
                File file = ficheros[x];
                // Check if the file is a file, the extension is "xml" and validate the XML with the XSD
                if (! file.isDirectory() && getExtension(file.getPath()).equals("xml") && xmlValidator.checkXML(file.getPath(), XMLType.family)) {
                    try {    
                        FamilyWithPath family = new FamilyWithPath(file.getPath(), unmarshal(ResourceLocator.getResource(file.getPath())));
                        list.add(family);
                    } catch (JAXBException ex) {
                        Logger.getLogger(XMLReader.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        else{
            if (getExtension(dirPath.getPath()).equals("xml") && xmlValidator.checkXML(dirPath.getPath(), XMLType.family)) {
                try {    
                    FamilyWithPath family = new FamilyWithPath(dirPath.getPath(), unmarshal(ResourceLocator.getResource(dirPath.getPath())));    
                    list.add(family);
                    } catch (JAXBException ex) {
                        Logger.getLogger(XMLReader.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
        }
        return list;
    }
    
    private Family unmarshal(InputStream inputStream )throws JAXBException {
        String packageName = Family.class.getPackage().getName();
        JAXBContext jc = JAXBContext.newInstance( packageName );
        Unmarshaller u = jc.createUnmarshaller();
        Family doc = (Family) u.unmarshal( inputStream );
        return doc;
    } 
    
    private String getExtension(String filePath){
        int dot = filePath.lastIndexOf(".");
        return filePath.substring(dot + 1);
    }
}
