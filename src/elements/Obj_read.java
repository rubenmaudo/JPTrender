package elements;

import geometry.AABB;
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
    ArrayList<Vec3> vertexNormalList = new ArrayList<Vec3>();
    ArrayList<Primitive> triangleslist = new ArrayList<Primitive>();
    Material material;
    String path;
    double xMin=0,xMax=0,yMin=0,yMax=0,zMin=0,zMax=0;
    Vec3 basepoint;
    Box oldBoundingBox;

    AABB boundingBox;

    public Obj_read(String file_path, Material material) {
        this.path=file_path;
        this.material=material;
        importMesh();
        this.basepoint=new Vec3(((xMax-xMin)/2)+xMin,(yMin),((zMax-zMin)/2)+zMin);
        this.oldBoundingBox=new Box(xMax-xMin,zMax-zMin,yMax-yMin,basepoint,material); //Old system of creating a bounding box


        this.boundingBox=new AABB(new Vec3(xMin,yMin,zMin),new Vec3(xMax,yMax,zMax));
    }

    public void importMesh(){
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(
                    new FileReader(path));
            String line = bufferedReader.readLine();
            while(line != null) {

                //We capture the vertex values
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

                }

                //We capture the vertex normal values
                else if (line.startsWith("vn")){
                    String[] listOf=line.split(" ");

                    Vec3 vertexNormalValue=new Vec3(Double.valueOf(listOf[1]),Double.valueOf(listOf[2]),Double.valueOf(listOf[3]));
                    vertexNormalList.add(vertexNormalValue);
                }

                //We capture the faces values
                else if(line.startsWith("f ")){
                    ArrayList<Integer> vertexListFace= new ArrayList<Integer>();
                    ArrayList<Integer> vertexNormalListFace= new ArrayList<Integer>();
                    String[] listOfWithSpaces=line.split(" ");
                    for(int i=1;i<listOfWithSpaces.length; i++){
                        String[] faceElements=listOfWithSpaces[i].split("/");
                        vertexListFace.add(Integer.valueOf(faceElements[0]));
                        vertexNormalListFace.add(Integer.valueOf(faceElements[2]));
                    }
                    for(int i=2; i<vertexListFace.size(); i++){
                        triangleslist.add(new Triangle(
                                vertexList.get(vertexListFace.get(0)-1),
                                vertexList.get(vertexListFace.get(i-1)-1),
                                vertexList.get(vertexListFace.get(i)-1),
                                vertexNormalList.get(vertexNormalListFace.get(0)-1),
                                vertexNormalList.get(vertexNormalListFace.get(i-1)-1),
                                vertexNormalList.get(vertexNormalListFace.get(i)-1),
                                material
                        ));
                    }
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

    public Box getOldBoundingBox() {
        return oldBoundingBox;
    }

    public AABB getBoundingBox() {
        return boundingBox;
    }


}
