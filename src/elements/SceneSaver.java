package elements;

import geometry.Primitive;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;

/**
 * @author : Ruben Maudo
 * @since : 15/05/2021
 **/
public class SceneSaver {

    ArrayList<Primitive> geometry;
    Camera camera;
    String path;

    /**
     * Constructor that receive geometry, camera and path, to create an xml from the instances created previously
     * @param geometry
     * @param camera
     * @param path
     */
    public SceneSaver( ArrayList<Primitive> geometry,Camera camera,String path) {

        this.geometry = geometry;
        this.camera = camera;
        this.path=path;

        initialise();
    }

    private void initialise() {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            //We add element to Document
            Element rootElement = doc.createElement("Scene");
            doc.appendChild(rootElement);

            rootElement.appendChild(camera.getCamera(doc));

            for(Primitive geometry:geometry){
                rootElement.appendChild(geometry.saveGeomety(doc));
            }


            //for output to file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            //for pretty print
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);

            //write to console
            //StreamResult console = new StreamResult(System.out);

            StreamResult file = new StreamResult(new File(path));

            //write data in to console
            //transformer.transform(source, console);
            transformer.transform(source, file);

        } catch (ParserConfigurationException | TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
