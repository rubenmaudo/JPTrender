package elements;

import geometry.*;
import materials.*;
import maths.ColorValue;
import maths.Vec3;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


/**
 * @author : Ruben Maudo
 * @since : 12/05/2021
 **/
public class SceneLoader {

    final int STARTING=0;
    final int LOADED=1;
    final int NONELOADED=2;
    int flagControlState =0;

    //CAMERA FIELDS
    Vec3 lookfrom;
    Vec3 lookat;
    Vec3 vup;
    double vfov;
    double aperture;
    int autofocus;
    double focus_dist;

    //GEOMETRY FIELDS
    ArrayList<Primitive> geometry = new ArrayList<>();
    ArrayList<Primitive> samplerList = new ArrayList<>();

    //TODO add all the details of the scene,  including render size, etc

    /**
     * Constructor to read a xml and load all the camera, objects and materials
     * @param filePath
     */
    public SceneLoader(String filePath){

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            flagControlState =NONELOADED;
        }

        Document document = null;

        //Depending on fresh start or selected scene it will load one file or another
        try {
            if(filePath.equals("startScene")){

                document = builder.parse(SceneLoader.class.getResourceAsStream("/resources/CornellBox1Light3SpheresFuzz.xml"));
                flagControlState =STARTING;

            }else{
                document = builder.parse(new File(filePath));
                flagControlState =LOADED;
            }

            Element root = document.getDocumentElement();

            //CAMERA PARAMETERS
            Element elementCamera= (Element) document.getElementsByTagName("Camera").item(0);
            vfov = Double.parseDouble(elementCamera.getAttribute("vfov"));
            aperture= Double.parseDouble(elementCamera.getAttribute("aperture"));
            focus_dist=Double.parseDouble(elementCamera.getAttribute("focus_dist"));
            autofocus= Integer.parseInt(elementCamera.getAttribute("autofocus"));

            Element elementLookFrom = (Element) elementCamera.getElementsByTagName("lookfrom").item(0);
            lookfrom=new Vec3(
                    Double.parseDouble(elementLookFrom.getAttribute("X")),
                    Double.parseDouble(elementLookFrom.getAttribute("Y")),
                    Double.parseDouble(elementLookFrom.getAttribute("Z"))
            );

            Element elementLookAt = (Element) elementCamera.getElementsByTagName("lookat").item(0);
            lookat=new Vec3(
                    Double.parseDouble(elementLookAt.getAttribute("X")),
                    Double.parseDouble(elementLookAt.getAttribute("Y")),
                    Double.parseDouble(elementLookAt.getAttribute("Z"))
            );

            Element elementVup = (Element) elementCamera.getElementsByTagName("vup").item(0);
            vup=new Vec3(
                    Double.parseDouble(elementVup.getAttribute("X")),
                    Double.parseDouble(elementVup.getAttribute("Y")),
                    Double.parseDouble(elementVup.getAttribute("Z"))
            );

        /*PRINTING CHECK
        System.out.println("Lookfrom: "+lookfrom);
        System.out.println("Lookat: "+lookat);
        System.out.println("Vup: "+vup);
        System.out.println("vfov: "+vfov);
        System.out.println("aperture: "+aperture);
        System.out.println("autofocus: "+autofocus);
        System.out.println("focus_dist: "+focus_dist);
        */


            //LOADING SPHERES
            NodeList nList = document.getElementsByTagName("Sphere");

            for (int temp = 0; temp < nList.getLength(); temp++)
            {
                Node node = nList.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE)
                {
                    //Print each employee's detail
                    Element sphereElement = (Element) node;
                    Sphere sphere= new Sphere(
                            new Vec3(
                                    Double.parseDouble(sphereElement.getAttribute("CentreX")),
                                    Double.parseDouble(sphereElement.getAttribute("CentreY")),
                                    Double.parseDouble(sphereElement.getAttribute("CentreZ"))
                            ),
                            Double.parseDouble(sphereElement.getAttribute("radius")),
                            this.scanMaterial(sphereElement)
                    );
                    geometry.add(sphere);

                /*PRINTING CHECK
                System.out.println("Sphere:");
                System.out.println("Radius " + Double.parseDouble(sphereElement.getAttribute("radius")));
                System.out.println("CentreX "  + Double.parseDouble(sphereElement.getAttribute("CentreX")));
                System.out.println("CentreY "  + Double.parseDouble(sphereElement.getAttribute("CentreY")));
                System.out.println("CentreZ "  + Double.parseDouble(sphereElement.getAttribute("CentreZ")));
                */
                }
            }

            //LOADING BOXES
            nList = document.getElementsByTagName("Box");

            for (int temp = 0; temp < nList.getLength(); temp++)
            {
                Node node = nList.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element boxElement = (Element) node;

                    double rotationAngle=0;
                    if (!boxElement.getAttribute("rotationAngle").isEmpty()){
                        rotationAngle= Double.parseDouble(boxElement.getAttribute("rotationAngle"));
                    }
                    Box box= new Box(
                            Double.parseDouble(boxElement.getAttribute("width")),
                            Double.parseDouble(boxElement.getAttribute("depth")),
                            Double.parseDouble(boxElement.getAttribute("height")),
                            new Vec3(
                                    Double.parseDouble(boxElement.getAttribute("centreBasePointX")),
                                    Double.parseDouble(boxElement.getAttribute("centreBasePointY")),
                                    Double.parseDouble(boxElement.getAttribute("centreBasePointZ"))
                            ),
                            this.scanMaterial(boxElement),
                            rotationAngle
                    );
                    geometry.add(box);

                /*PRINTING CHECK
                System.out.println("Box:");
                System.out.println("width " + Double.parseDouble(boxElement.getAttribute("width")));
                System.out.println("depth "  + Double.parseDouble(boxElement.getAttribute("depth")));
                System.out.println("height "  + Double.parseDouble(boxElement.getAttribute("height")));
                System.out.println("centreBasePointX "  + Double.parseDouble(boxElement.getAttribute("centreBasePointX")));
                System.out.println("centreBasePointY "  + Double.parseDouble(boxElement.getAttribute("centreBasePointY")));
                System.out.println("centreBasePointZ "  + Double.parseDouble(boxElement.getAttribute("centreBasePointZ")));
                 */
                }
            }

            //LOADING PLANE XY
            nList = document.getElementsByTagName("PlaneXY");

            for (int temp = 0; temp < nList.getLength(); temp++)
            {
                Node node = nList.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE)
                {
                    //Print each employee's detail
                    Element planeXYElement = (Element) node;
                    Plane_xy planeXY= new Plane_xy(
                            Double.parseDouble(planeXYElement.getAttribute("width")),
                            Double.parseDouble(planeXYElement.getAttribute("height")),
                            new Vec3(
                                    Double.parseDouble(planeXYElement.getAttribute("centreBasePointX")),
                                    Double.parseDouble(planeXYElement.getAttribute("centreBasePointY")),
                                    Double.parseDouble(planeXYElement.getAttribute("centreBasePointZ"))
                            ),
                            Boolean.parseBoolean(planeXYElement.getAttribute("flipped")),
                            this.scanMaterial(planeXYElement)
                    );
                    geometry.add(planeXY);


                /*PRINTING CHECK
                System.out.println("PlaneXY:");
                System.out.println("width " + Double.parseDouble(planeXYElement.getAttribute("width")));
                System.out.println("height "  + Double.parseDouble(planeXYElement.getAttribute("height")));
                System.out.println("centreBasePointX "  + Double.parseDouble(planeXYElement.getAttribute("centreBasePointX")));
                System.out.println("centreBasePointY "  + Double.parseDouble(planeXYElement.getAttribute("centreBasePointY")));
                System.out.println("centreBasePointZ "  + Double.parseDouble(planeXYElement.getAttribute("centreBasePointZ")));
                System.out.println("flipped "  + Boolean.parseBoolean(planeXYElement.getAttribute("flipped")));
                */
                }
            }

            //LOADING PLANE XZ
            nList = document.getElementsByTagName("PlaneXZ");

            for (int temp = 0; temp < nList.getLength(); temp++)
            {
                Node node = nList.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE)
                {
                    //Print each employee's detail
                    Element planeXZElement = (Element) node;
                    Plane_xz planeXZ= new Plane_xz(
                            Double.parseDouble(planeXZElement.getAttribute("width")),
                            Double.parseDouble(planeXZElement.getAttribute("depth")),
                            new Vec3(
                                    Double.parseDouble(planeXZElement.getAttribute("centreBasePointX")),
                                    Double.parseDouble(planeXZElement.getAttribute("centreBasePointY")),
                                    Double.parseDouble(planeXZElement.getAttribute("centreBasePointZ"))
                            ),
                            Boolean.parseBoolean(planeXZElement.getAttribute("flipped")),
                            Boolean.parseBoolean(planeXZElement.getAttribute("isSampled")),
                            this.scanMaterial(planeXZElement)
                    );
                    geometry.add(planeXZ);

                /*PRINTING CHECK
                System.out.println("PlaneXZ:");
                System.out.println("width " + Double.parseDouble(planeXZElement.getAttribute("width")));
                System.out.println("height "  + Double.parseDouble(planeXZElement.getAttribute("depth")));
                System.out.println("centreBasePointX "  + Double.parseDouble(planeXZElement.getAttribute("centreBasePointX")));
                System.out.println("centreBasePointY "  + Double.parseDouble(planeXZElement.getAttribute("centreBasePointY")));
                System.out.println("centreBasePointZ "  + Double.parseDouble(planeXZElement.getAttribute("centreBasePointZ")));
                System.out.println("flipped "  + Boolean.parseBoolean(planeXZElement.getAttribute("flipped")));
                */
                }
            }

            //LOADING PLANE YZ
            nList = document.getElementsByTagName("PlaneYZ");

            for (int temp = 0; temp < nList.getLength(); temp++)
            {
                Node node = nList.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE)
                {
                    //Print each employee's detail
                    Element planeYZElement = (Element) node;
                    Plane_yz planeYZ= new Plane_yz(
                            Double.parseDouble(planeYZElement.getAttribute("width")),
                            Double.parseDouble(planeYZElement.getAttribute("height")),
                            new Vec3(
                                    Double.parseDouble(planeYZElement.getAttribute("centreBasePointX")),
                                    Double.parseDouble(planeYZElement.getAttribute("centreBasePointY")),
                                    Double.parseDouble(planeYZElement.getAttribute("centreBasePointZ"))
                            ),
                            Boolean.parseBoolean(planeYZElement.getAttribute("flipped")),
                            this.scanMaterial(planeYZElement)
                    );
                    geometry.add(planeYZ);

                /*PRINTING CHECK
                System.out.println("PlaneXZ:");
                System.out.println("width " + Double.parseDouble(planeYZElement.getAttribute("width")));
                System.out.println("height "  + Double.parseDouble(planeYZElement.getAttribute("height")));
                System.out.println("centreBasePointX "  + Double.parseDouble(planeYZElement.getAttribute("centreBasePointX")));
                System.out.println("centreBasePointY "  + Double.parseDouble(planeYZElement.getAttribute("centreBasePointY")));
                System.out.println("centreBasePointZ "  + Double.parseDouble(planeYZElement.getAttribute("centreBasePointZ")));
                System.out.println("flipped "  + Boolean.parseBoolean(planeYZElement.getAttribute("flipped")));
                 */
                }
            }

        } catch (SAXException e) {
            e.printStackTrace();
            flagControlState =NONELOADED;
        } catch (IOException e) {
            e.printStackTrace();
            flagControlState =NONELOADED;
        }
    }


    /**
     * Method that receive an element of the XML readed and return the Material instance
     * @param element
     * @return
     */
    private Material scanMaterial(@NotNull Element element){

        Material materialToReturn = null;
        Element materialElement = (Element) element.getElementsByTagName("Material").item(0);
        String materialType= materialElement.getAttribute("type");

        //DEFINE LAMBERTIAN MATERIAL
        if(materialType.equals("lambertian")){
            materialToReturn= new Lambertian( new ColorValue(
                    Double.parseDouble(materialElement.getAttribute("ColorR")),
                    Double.parseDouble(materialElement.getAttribute("ColorG")),
                    Double.parseDouble(materialElement.getAttribute("ColorB")))
            );
        }

        //DEFINE METAL MATERIAL
        else if(materialType.equals("metal")){
            materialToReturn= new Metal( new ColorValue(
                    Double.parseDouble(materialElement.getAttribute("ColorR")),
                    Double.parseDouble(materialElement.getAttribute("ColorG")),
                    Double.parseDouble(materialElement.getAttribute("ColorB"))),
                    Double.parseDouble(materialElement.getAttribute("fuzziness"))
            );
        }

        //DEFINE DIELECTRIC MATERIAL
        else if(materialType.equals("dielectric")){
            materialToReturn= new Dielectric( new ColorValue(
                    Double.parseDouble(materialElement.getAttribute("ColorR")),
                    Double.parseDouble(materialElement.getAttribute("ColorG")),
                    Double.parseDouble(materialElement.getAttribute("ColorB"))),
                    Double.parseDouble(materialElement.getAttribute("ri")),
                    Double.parseDouble(materialElement.getAttribute("fuzziness"))
            );
        }

        //DEFINE LIGHT MATERIAL
        else if(materialType.equals("light")){
            materialToReturn= new Diffuse_light( new ColorValue(
                    Double.parseDouble(materialElement.getAttribute("ColorR")),
                    Double.parseDouble(materialElement.getAttribute("ColorG")),
                    Double.parseDouble(materialElement.getAttribute("ColorB")))
            );
        }
        return materialToReturn;
    }

    public Vec3 getLookfrom() {
        return lookfrom;
    }

    public Vec3 getLookat() {
        return lookat;
    }

    public Vec3 getVup() {
        return vup;
    }

    public double getVfov() {
        return vfov;
    }

    public double getAperture() {
        return aperture;
    }

    public int getAutofocus() {
        return autofocus;
    }

    public double getFocus_dist() {
        return focus_dist;
    }

    public ArrayList<Primitive> getGeometry() {
        return geometry;
    }

    public Camera getCamera(Double aspect_ratio){
        return new Camera(lookfrom,lookat,vup,vfov,aspect_ratio,aperture,focus_dist,autofocus);
    }

    public int getFlagControlState(){
        return flagControlState;
    }
}
