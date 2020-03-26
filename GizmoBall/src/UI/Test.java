package UI;

import Component.Component;
import Component.*;

import GizmoLogic.GizmoController;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
       Test test = new Test();
       test.testTools();

    }

    public void testGridComponentNull(){
        Component[][] gridComponent = new Component[20][20];
        System.out.println(gridComponent[0][0]==null);
        //true
    }
    public void testTools(){
        GizmoController controller = new GizmoController();
        String command;
        String type;
        int x,y;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input command:");
        command = scanner.next();
        while(!command.equals("quit")){
            switch (command){
                case "add":
                    System.out.println("INPUT: type px py");
                    type = scanner.next();
                    x = scanner.nextInt();
                    y = scanner.nextInt();
                    controller.addOneComponent(type,x,y);
                    break;
                case "zoomin":
                    System.out.println("INPUT: px py");
                    x = scanner.nextInt();
                    y = scanner.nextInt();
                    controller.zoomIn(x,y);
                    break;
                case "zoomout":
                    System.out.println("INPUT: px py");
                    x = scanner.nextInt();
                    y = scanner.nextInt();
                    controller.zoomOut(x,y);
                    break;
                case "delete":
                    System.out.println("INPUT: px py");
                    x = scanner.nextInt();
                    y = scanner.nextInt();
                    controller.remove(x,y);
                    break;
            }
            printComponents(controller.getAllComponents());
            printGridGomponent(controller.getGridComponent());
            System.out.println("Input command:");
            command = scanner.next();
        }

    }
    public void testFileHandler(){
        GizmoController controller = new GizmoController();

    }
    public void testToStringOfComponent(){
        List<Component> list = new ArrayList<>();
        list.add(new Ball(1,0,10,10));
        list.add(new Diamond(1,0,10,10));
        for (Component c:
             list) {
            System.out.println(c);
        }

    }

    public void testListIsNotNullAfterNewArrayList(){
        List<Integer> list = new ArrayList<>();
        list.add(1);
        System.out.println(list.size());
        list = new ArrayList<>();
        System.out.println(list.size());
    }

    private void printGridGomponent(Component[][] gridComponent){
        for (int i=0;i<20;i++){
            for (int j=0;j<20;j++){
                if (gridComponent[i][j]!=null)
                    System.out.printf("[%d][%d][%s]",i,j,gridComponent[i][j].getType());
                else
                    System.out.printf("[%d][%d]",i,j);
            }
            System.out.println();
        }
    }
    private void printComponents(List<Component> components){
        System.out.println(components.toString());
    }
}
