package elements;

import geometry.Box;
import geometry.Primitive;
import geometry.Triangle;
import materials.Lambertian;
import materials.Material;
import maths.ColorValue;
import maths.Vec3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Obj_read {
    ArrayList<Vec3> vertexList = new ArrayList<Vec3>();
    ArrayList<Primitive> triangleslist = new ArrayList<Primitive>();
    Material material;
    String path;
    double xMin=0,xMax=0,yMin=0,yMax=0,zMin=0,zMax=0;
    Vec3 basepoint;
    Box boundingBox;

    public Obj_read(String file_path, Material material) {
        this.path=file_path;
        this.material=material;
        importMesh();
        this.basepoint=new Vec3((xMax-xMin)/2,(yMax-yMin)/2,(zMax-zMin)/2);
        this.boundingBox=new Box(xMax-xMin,zMax-zMin,yMax-yMin,basepoint,material);
    }

    public void importMesh(){
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(
                    new FileReader(path));
            String line = bufferedReader.readLine();
            while(line != null) {

                if (line.startsWith("v ")){
                    String[] listOf=line.split(" ");

                    Vec3 vertexValue=new Vec3(Double.valueOf(listOf[1]),Double.valueOf(listOf[2]),Double.valueOf(listOf[3]));
                    vertexList.add(vertexValue);
                    if (vertexValue.x()<xMin) xMin=vertexValue.x();
                    if (vertexValue.x()>xMax) xMax=vertexValue.x();
                    if (vertexValue.y()<yMin) yMin=vertexValue.y();
                    if (vertexValue.y()>yMax) yMax=vertexValue.y();
                    if (vertexValue.z()<zMin) zMin=vertexValue.z();
                    if (vertexValue.z()>zMax) zMax=vertexValue.z();

                }else if(line.startsWith("f ")){
                    ArrayList<Integer> vertexListFace= new ArrayList<Integer>();
                    String[] listOfWithSpaces=line.split(" ");
                    for(int i=1;i<listOfWithSpaces.length; i++){
                        String[] faceElements=listOfWithSpaces[i].split("/");
                        vertexListFace.add(Integer.valueOf(faceElements[0]));
                        System.out.println(faceElements[0]);
                    }
                    for(int i=2; i<vertexListFace.size(); i++){
                        triangleslist.add(new Triangle(
                                vertexList.get(vertexListFace.get(0)-1),
                                vertexList.get(vertexListFace.get(i-1)-1),
                                vertexList.get(vertexListFace.get(i)-1),
                                material
                        ));
                    }

                    System.out.println("next face");
                }

                line = bufferedReader.readLine();
            }



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Primitive> getTriangleslist() {
        return triangleslist;
    }

    public Box getBoundingBox() {
        return boundingBox;
    }
}
