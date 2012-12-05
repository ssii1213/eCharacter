package loader;
 
import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.LoopMode;
import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Renderer;
import com.jme3.scene.Spatial;
import com.jme3.texture.FrameBuffer;
import com.jme3.util.BufferUtils;
import com.jme3.util.Screenshots;
import imagesProcessing.ColoringImage;
import imagesProcessing.ImagesProcessing;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;

 
/** Ejemplo de carga de modelos */
public class LoadModel extends SimpleApplication
{
    private AnimChannel channel;
    private AnimControl control;
    private Spatial chico;
    private Material mat;
    private ImagesProcessing img;
    private String skinsPath,trousersPath,tShirtsPath,eyesPath,shoesPath,destinationPath;
    
    private int indexCaptura = 0;
    private int indexImage = 0;
    
    String [] arraySkin, arrayShoes, arrayTrouser, arrayTShirt, arrayEyes;
    private int indexSkin, indexShoes, indexTrouser, indexTShirt, indexEyes = 0;
    private int numSkins = 4;
    private int numShoes = 5;
    private int numTrousers = 7;
    private int numTShirts = 6;
    private int numEyes = 4;
    
    public static void main(String[] args) {
      LoadModel app = new LoadModel();
      app.setShowSettings(false);
      app.start();
      
    }
 
    public void simpleInitApp() 
    {
        flyCam.setEnabled(false);
        
        setDisplayFps(false);       // to hide the FPS
        setDisplayStatView(false);  // to hide the statistics 
        
        initKeys();
        initPaths();

        DirectionalLight dl = new DirectionalLight();
        dl.setDirection(new Vector3f(-0.1f, -1f, -1).normalizeLocal());
        rootNode.addLight(dl);

        skinsPath = arraySkin[indexSkin];
        indexSkin++;
        shoesPath = arrayShoes[indexShoes];
        indexShoes++;
        trousersPath = arrayTrouser[indexTrouser];
        indexTrouser++;
        tShirtsPath = arrayTShirt[indexTShirt];
        indexTShirt++;
        eyesPath = arrayEyes[indexEyes];
        indexEyes++;

        img = new ImagesProcessing(skinsPath, trousersPath, tShirtsPath, eyesPath, shoesPath);
        destinationPath = "assets/Textures/OriginalTexture.png";
        img.fusionaImagenes(destinationPath);

        //CHICO
        //Andar
        //Spatial chico = assetManager.loadModel("Models/eAdventure/Animation Boy/Andar/polySurfaceShape4.mesh.xml");
        //Coger
        //Spatial chico = assetManager.loadModel("Models/eAdventure/Animation Boy/Coger/polySurfaceShape4.mesh.xml");
        //Hablar
        //Spatial chico = assetManager.loadModel("Models/eAdventure/Animation Boy/Hablar/polySurfaceShape4.mesh.xml");
        //Parado
        //Spatial chico = assetManager.loadModel("Models/eAdventure/Animation Boy/Parado/polySurfaceShape4.mesh.xml");

        //CHICA
        //Andar
        //Spatial chico = assetManager.loadModel("Models/eAdventure/Animation Girl/Andar/polySurfaceShape12.mesh.xml");
        //Coger
        //Spatial chico = assetManager.loadModel("Models/eAdventure/Animation Girl/Coger/polySurfaceShape12.mesh.xml");
        //Hablar
        //Spatial chico = assetManager.loadModel("Models/eAdventure/Animation Girl/Hablar/polySurfaceShape12.mesh.xml");
        //Parada
        //Spatial chico = assetManager.loadModel("Models/eAdventure/Animation Girl/Parada/polySurfaceShape12.mesh.xml");

        //Animacion cambiada con blender
        chico = assetManager.loadModel("Models/prueba/polySurfaceShape4.mesh.xml");
        mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setTexture("ColorMap",assetManager.loadTexture("Textures/OriginalTexture.png"));  
        chico.setMaterial(mat);
        
        //Borrar la imagen
        Path file = Paths.get(destinationPath);
        try {
            Files.delete(file);
        } 
        catch (IOException ex) {
            System.out.println("Error al borrar el fichero");
        }

        chico.rotate(1.5f, 0.0f, 0.0f);
        chico.setLocalTranslation(-0.5f, -2.5f, 0.0f);
        rootNode.attachChild(chico);

        control = chico.getControl(AnimControl.class);
        channel = control.createChannel();
        channel.setAnim("my_animation");
    }
 
    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) 
    {
        if (animName.equals("my_animation")) {
            channel.setAnim("my_animation");
            channel.setLoopMode(LoopMode.DontLoop);
            channel.setSpeed(1f);
        }
    }
 
    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) 
    {
      // unused
    }
 
    public void changeSkin()
    {
        skinsPath = arraySkin[indexSkin%numSkins];
        indexSkin++;
        //Creat the image
               
        img = new ImagesProcessing(skinsPath, trousersPath, tShirtsPath, eyesPath, shoesPath);                
        indexImage++;                
        destinationPath = "assets/Textures/FinalTexture"+indexImage+".png";                
        img.fusionaImagenes(destinationPath);                
        //Set the texture to the material                
        mat.setTexture("ColorMap", assetManager.loadTexture("Textures/FinalTexture"+indexImage+".png"));                  
        chico.setMaterial(mat);                
        //Delete the file                
        Path file = Paths.get(destinationPath);                
        try {                   
            Files.delete(file);                
        } catch (IOException ex) {                    
            System.out.println("Failed deleting file");                
        }            
    }
    
    public void changeShoes()
    {
        shoesPath = arrayShoes[indexShoes%numShoes];                
        indexShoes++;                
        //Creat the image                
        img = new ImagesProcessing(skinsPath, trousersPath, tShirtsPath, eyesPath, shoesPath);                
        indexImage++;                
        destinationPath = "assets/Textures/FinalTexture"+indexImage+".png";                
        img.fusionaImagenes(destinationPath);                
        //Set the texture to the material               
        mat.setTexture("ColorMap", assetManager.loadTexture("Textures/FinalTexture"+indexImage+".png"));                
        chico.setMaterial(mat);               
        //Delete the file               
        Path file = Paths.get(destinationPath);               
        try {                    
            Files.delete(file);                
        } catch (IOException ex) {                    
            System.out.println("Failed deleting file");                
        }
    }
    
    public void changeTrousers()
    {
        trousersPath = arrayTrouser[indexTrouser%numTrousers];                
        indexTrouser++;               
        //Creat the image                
        img = new ImagesProcessing(skinsPath, trousersPath, tShirtsPath, eyesPath, shoesPath);         
        indexImage++;        
        destinationPath = "assets/Textures/FinalTexture"+indexImage+".png";        
        img.fusionaImagenes(destinationPath);        
        //Set the texture to the material        
        mat.setTexture("ColorMap", assetManager.loadTexture("Textures/FinalTexture"+indexImage+".png"));        
        chico.setMaterial(mat);        
        //Delete the file        
        Path file = Paths.get(destinationPath);        
        try {        
            Files.delete(file);            
        } catch (IOException ex) {        
            System.out.println("Failed deleting file");            
        }
    }
    
    public void changeTShirt()
    {
        tShirtsPath = arrayTShirt[indexTShirt%numTShirts];                
        indexTShirt++;               
        //Creat the image        
        img = new ImagesProcessing(skinsPath, trousersPath, tShirtsPath, eyesPath, shoesPath);        
        indexImage++;        
        destinationPath = "assets/Textures/FinalTexture"+indexImage+".png";        
        img.fusionaImagenes(destinationPath);        
        //Set the texture to the material        
        mat.setTexture("ColorMap", assetManager.loadTexture("Textures/FinalTexture"+indexImage+".png"));          
        chico.setMaterial(mat);        
        //Delete the file        
        Path file = Paths.get(destinationPath);        
        try {        
            Files.delete(file);            
        } catch (IOException ex) {        
            System.out.println("Failed deleting file");            
        }
    }
    
    public void changeEyes()
    {
        eyesPath = arrayEyes[indexEyes%numEyes];                
        indexEyes++;               
        //Creat the image        
        img = new ImagesProcessing(skinsPath, trousersPath, tShirtsPath, eyesPath, shoesPath);        
        indexImage++;        
        destinationPath = "assets/Textures/FinalTexture"+indexImage+".png";        
        img.fusionaImagenes(destinationPath);        
        //Set the texture to the material        
        mat.setTexture("ColorMap", assetManager.loadTexture("Textures/FinalTexture"+indexImage+".png"));          
        chico.setMaterial(mat);        
        //Delete the file        
        Path file = Paths.get(destinationPath);        
        try {        
            Files.delete(file);            
        } catch (IOException ex) {        
            System.out.println("Failed deleting file");            
        }        
    }
    
    public void screenshot()
    {
        int height = this.viewPort.getCamera().getHeight();               
        int width = this.viewPort.getCamera().getWidth();   
        
        
        BufferedImage rawFrame = new BufferedImage(width, height,BufferedImage.TYPE_4BYTE_ABGR);        
        ByteBuffer byteBuffer = BufferUtils.createByteBuffer(width * height * 4 ); 
        
        FrameBuffer fram = this.viewPort.getOutputFrameBuffer();
        Renderer renderer1 = this.renderManager.getRenderer();
        renderer1.readFrameBuffer(fram, byteBuffer);        
        Screenshots.convertScreenShot(byteBuffer, rawFrame);       
        
        
        indexCaptura++;
        try {        
            if (indexCaptura < 10) 
            {
                ImageIO.write(rawFrame, "png", new File("assets/Textures/screenshots/Captura_0"+indexCaptura+".png"));
            } 
            else 
            {
                ImageIO.write(rawFrame, "png", new File("assets/Textures/screenshots/Captura_"+indexCaptura+".png"));
            }            
        } catch (IOException e) {        
            System.out.println("Error");            
        }
    }
    
    /** Custom Keybinding: Map named actions to inputs. */
    private void initKeys() 
    {
        inputManager.addMapping("ChangeSkin", new KeyTrigger(KeyInput.KEY_NUMPAD2));
        inputManager.addListener(actionListener, "ChangeSkin");
        inputManager.addMapping("ChangeShoes", new KeyTrigger(KeyInput.KEY_NUMPAD0));
        inputManager.addListener(actionListener, "ChangeShoes");
        inputManager.addMapping("ChangeTrouser", new KeyTrigger(KeyInput.KEY_NUMPAD1));
        inputManager.addListener(actionListener, "ChangeTrouser");
        inputManager.addMapping("ChangeTShirt", new KeyTrigger(KeyInput.KEY_NUMPAD4));
        inputManager.addListener(actionListener, "ChangeTShirt"); 
        inputManager.addMapping("ChangeEyes", new KeyTrigger(KeyInput.KEY_NUMPAD7));
        inputManager.addListener(actionListener, "ChangeEyes"); 
        inputManager.addMapping("Capture", new KeyTrigger(KeyInput.KEY_NUMPAD5));
        inputManager.addListener(actionListener, "Capture"); 
        inputManager.addMapping("Special", new KeyTrigger(KeyInput.KEY_NUMPAD9));
        inputManager.addListener(actionListener, "Special"); 
    }
  
    public ActionListener actionListener = new ActionListener() 
    {
        public void onAction(String name, boolean keyPressed, float tpf) 
        {
            if (name.equals("ChangeSkin") && !keyPressed) {
                changeSkin();
            }
            else if (name.equals("ChangeShoes") && !keyPressed) {
                changeShoes();
            }
            else if (name.equals("ChangeTrouser") && !keyPressed) {
                changeTrousers();
            }
            else if (name.equals("ChangeTShirt") && !keyPressed) {
                changeTShirt();
            }
             else if (name.equals("ChangeEyes") && !keyPressed) {
                changeEyes();
            }
            else if (name.equals("Capture") && !keyPressed) {          
                    screenshot();
                }
            else if (name.equals("Special") && !keyPressed){
                String trousersPath = "assets/Textures/Textures Boy/Photoshop/PantalonCortoChico.png";
                String shirtPath = "assets/Textures/Textures Boy/Photoshop/CamisaLargaChico.png";
                String socksPath = "assets/Textures/Textures Boy/Photoshop/CalcetinesChico.png";
                String clockPath = "assets/Textures/Textures Boy/Photoshop/RelojChico.png";
                String tiePath = "assets/Textures/Textures Boy/Photoshop/CorbataAzulChico.png";
                String beardPath = "assets/Textures/Textures Boy/Photoshop/BarbaChico.png";
                img = new ImagesProcessing(skinsPath, trousersPath, shirtPath, eyesPath, socksPath, shoesPath, clockPath, tiePath, beardPath);
                indexImage++;
                destinationPath = "assets/Textures/FinalTexture"+indexImage+".png";        
                img.fusionaImagenes(destinationPath);        
                //Set the texture to the material        
                mat.setTexture("ColorMap", assetManager.loadTexture("Textures/FinalTexture"+indexImage+".png"));          
                chico.setMaterial(mat);        
                //Delete the file        
                Path file = Paths.get(destinationPath);        
                try {        
                    Files.delete(file);            
                } catch (IOException ex) {        
                    System.out.println("Failed deleting file");            
                }        
            }
        }
    };

    private void initPaths() 
    {
        initPathsSkins();
        initPathsShoes();
        initPathsTrousers();
        initPathsTShirts();
        initPathsEyes();
    }

    private void initPathsSkins() 
    {
        //Paths of skin
        String skinPath1 = "assets/Textures/Textures Boy/Skin/PielBlancaChico.png";
        String skinPath2 = "assets/Textures/Textures Boy/Skin/PielAmarillaChico.png";
        String skinPath3 = "assets/Textures/Textures Boy/Skin/PielIndiaChico.png";
        String skinPath4 = "assets/Textures/Textures Boy/Skin/PielNegraChico.png";
        arraySkin = new String[numSkins];
        arraySkin[0] = skinPath1;
        arraySkin[1] = skinPath2;
        arraySkin[2] = skinPath3;
        arraySkin[3] = skinPath4;
    }

    private void initPathsShoes() 
    {
        //Paths of shoes
        String shoesPath1 = "assets/Textures/Textures Boy/Shoes/ZapatosAzulClaroChico.png";
        String shoesPath2 = "assets/Textures/Textures Boy/Shoes/ZapatosMarronChico.png";
        String shoesPath3 = "assets/Textures/Textures Boy/Shoes/ZapatosNegrosChico.png";
        String shoesPath4 = "assets/Textures/Textures Boy/Shoes/ZapatosRojosChico.png";
        String shoesPath5 = "assets/Textures/Textures Boy/Shoes/ZapatosVerdeChico.png";
        arrayShoes = new String[numShoes];
        arrayShoes[0] = shoesPath1;
        arrayShoes[1] = shoesPath2;
        arrayShoes[2] = shoesPath3;
        arrayShoes[3] = shoesPath4;
        arrayShoes[4] = shoesPath5;
    }

    private void initPathsTrousers() 
    {
        //Paths of trousers
        String trousersPath1 = "assets/Textures/Textures Boy/Trousers/PantalonAmarilloChico.png";
        String trousersPath2 = "assets/Textures/Textures Boy/Trousers/PantalonAzulChico.png";
        String trousersPath3 = "assets/Textures/Textures Boy/Trousers/PantalonBlancoChico.png";
        String trousersPath4 = "assets/Textures/Textures Boy/Trousers/PantalonMarronChico.png";
        String trousersPath5 = "assets/Textures/Textures Boy/Trousers/PantalonNegroChico.png";
        String trousersPath6 = "assets/Textures/Textures Boy/Trousers/PantalonRojoChico.png";
        String trousersPath7 = "assets/Textures/Textures Boy/Trousers/PantalonVerdeChico.png";
        arrayTrouser = new String[numTrousers];
        arrayTrouser[0] = trousersPath1;
        arrayTrouser[1] = trousersPath2;
        arrayTrouser[2] = trousersPath3;
        arrayTrouser[3] = trousersPath4;
        arrayTrouser[4] = trousersPath5;
        arrayTrouser[5] = trousersPath6;
        arrayTrouser[6] = trousersPath7;
    }

    private void initPathsTShirts() 
    {
        //Paths of tshirts
        //String tshirtsPath1 = "assets/Textures/Textures Boy/TShirts/CamisetaAmarillaChico.png";
        String tshirtsPath1 = "assets/Textures/Textures Boy/final.png";
        String tshirtsPath2 = "assets/Textures/Textures Boy/TShirts/CamisetaAzulChico.png";
        String tshirtsPath3 = "assets/Textures/Textures Boy/TShirts/CamisetaBlancaChico.png";
        String tshirtsPath4 = "assets/Textures/Textures Boy/TShirts/CamisetaNegraChico.png";
        String tshirtsPath5 = "assets/Textures/Textures Boy/TShirts/CamisetaRojaChico.png";
        String tshirtsPath6 = "assets/Textures/Textures Boy/TShirts/CamisetaVerdeChico.png";
        arrayTShirt = new String[numTShirts];
        arrayTShirt[0] = tshirtsPath1;
        arrayTShirt[1] = tshirtsPath2;
        arrayTShirt[2] = tshirtsPath3;
        arrayTShirt[3] = tshirtsPath4;
        arrayTShirt[4] = tshirtsPath5;
        arrayTShirt[5] = tshirtsPath6;
    }

    private void initPathsEyes() 
    {
        //Paths of eyes
        String eyesPath1 = "assets/Textures/Textures Boy/Eyes/OjosAzulChico.png";
        String eyesPath2 = "assets/Textures/Textures Boy/Eyes/OjosMarronChico.png";
        String eyesPath3 = "assets/Textures/Textures Boy/Eyes/OjosNegrosChico.png";
        String eyesPath4 = "assets/Textures/Textures Boy/Eyes/OjosVerdesChico.png";
        arrayEyes = new String[numEyes];
        arrayEyes[0] = eyesPath1;
        arrayEyes[1] = eyesPath2;
        arrayEyes[2] = eyesPath3;
        arrayEyes[3] = eyesPath4;
    }
}