package GizmoLogic;

import AssistantClass.GridPosition;
import AssistantClass.PixelPosition;
import AssistantClass.Type;
import Component.Component;
import Component.Ball;
import Component.*;

import java.util.ArrayList;
import java.util.List;

import static AssistantClass.Type.Wall;

public class Board {
    Component[][] gridComponent;
    List<Component> components;

    /*
    下面是用在碰撞检测中的变量
     */
    Ball ball;
    Lflipper lflipper;
    Rflipper rflipper;
    Component willCollisionComponent;//即将与小球发生碰撞的物体

    public Board(){
       gridComponent = new Component[20][20];
       components = new ArrayList<>();
    }


   public void moveLeftFlipper(int moveDirection)
   {
        lflipper.move(moveDirection,rflipper,gridComponent);
   }
    public void moveRightFlipper(int moveDirection)
    {
        rflipper.move(moveDirection,lflipper,gridComponent);
    }

    public void addOneComponent(String type,int scale,int angle,int x,int y)
    {
        Component c;
        if (type.equals("Ball")){
            ball = new Ball(scale,angle,x,y);
            c = ball;
        }else if (type.equals("Diamond")){
            c = new Diamond(scale,angle,x,y);
        }else if (type.equals("Hole")){
            c = new Hole(scale,angle,x,y);
        }else if (type.equals("Lflipper")){
            lflipper = new Lflipper(scale,angle,x,y);
            c=lflipper;
        }else if (type.equals("Rflipper")){
            rflipper = new Rflipper(scale,angle,x,y);
            c=rflipper;
        }else if(type.equals("Opipe")){
            c = new Opipe(scale,angle,x,y);
        }else if (type.equals("Spipe")){
            c = new Spipe(scale,angle,x,y);
        }else if(type.equals("Square")){
            c = new Square(scale,angle,x,y);
        }else if (type.equals("Triangle")){
            c = new Triangle(scale,angle,x,y);
        }else if (type.equals("Wall")){
            c = new Wall(scale,angle,x,y);
        }else{
            c = new Component();
            System.out.println("Invalid Component Type");
            return ;
        }
        components.add(c);

        c.updateGridComponentAsSquare(gridComponent,scale);
    }



    public Ball getBall() {
        return ball;
    }

    /**
     *
     * @param incTime
     * @return 返回经过本次移动后，小球是否
     */
    public boolean moveBallOneStep(double incTime)
    {
        if (ball==null) return false;
        willCollisionComponent = getWillCollisionComponent(incTime);
        //如果不会发生碰撞，小球继续运动
        if (willCollisionComponent == null){
            ball.moveOneStep(incTime);
        }else{//若发生碰撞，则碰撞物体wcc处理小球的运动，完成之后，将wcc置为null
            willCollisionComponent.handleBall(ball,incTime,gridComponent,components);
            if (willCollisionComponent.getType()=="Hole"){
                ball=null;
                return false;
            }
            willCollisionComponent=null;
        }
        return true;
    }

    /**
     *
     * @return 计算ball的下一个中心像素，并判断如果做了移动，是否会产生碰撞。
     * 如果发生碰撞，返回将要和小于产生碰撞的物体，如果不发生碰撞，返回空！！！
     */
    public Component getWillCollisionComponent(double incTime) {
        if (ball==null) return null;
        Component will_cc;
        List<GridPosition> grids = ball.calNextStayGridPoses(incTime);
        //先检测是不是越界
        will_cc = checkOverBound(grids);
        if (will_cc!=null)return will_cc;
        //如果没有越界，再检测是不是和其他物体发生碰撞
        will_cc = checkOtherCollision(grids);
        return will_cc;
    }

    /**
     *
     * @param will_grids 是小球即将占据的格子坐标列表
     * @return
     */
    //如果小球即将越界，就返回给它一个Wall对象
    //Wall angel是和水平面的平角
    private Wall checkOverBound(List<GridPosition> will_grids) {
        for (GridPosition g :
                will_grids) {
            int x = g.getGrid_x();
            int y = g.getGrid_y();
            if (x < 0) {//和左墙壁发生碰撞
                return new Wall(1, 90, 0, y);
            } else if (x > 19) {//和右墙壁发生碰撞
                return new Wall(1, 90, 19, y);
            } else if (y < 0) {//和上墙壁发生碰撞
                return new Wall(1, 0, x, 0);
            } else if (y > 19) {//和下墙壁发生碰撞
                return new Wall(1, 0, x, 19);
            }
        }
        //不会越界，返回null
        return null;
    }
    /**
     *
     * @param will_grids 是小球即将占据的格子坐标
     * @return
     */
    private Component checkOtherCollision(List<GridPosition> will_grids){
        for (GridPosition g:
                will_grids) {
            int x = g.getGrid_x();
            int y = g.getGrid_y();
            if (gridComponent[x][y]!=null)//如果gridComponent初始化new了，但是没有赋值，这里是不是这里永远不会为空？不是哦~
                return gridComponent[x][y];
        }
        return null;
    }

    public List<Component> getComponents() {
        return components;
    }


    public void rotate(int x,int y) {
        Component c = gridComponent[x][y];
        if (c!=null&&c.canRotate(gridComponent))
            c.rotate(gridComponent);
    }

    public void zoomIn(int x,int y) {
        Component c = gridComponent[x][y];
        if (c!=null&&c.canZoomIn(gridComponent))
            c.zoomIn(gridComponent);
    }
    public void zoomOut(int x,int y) {
        Component c = gridComponent[x][y];
        if (c!=null&&c.canZoomOut(gridComponent))
            c.zoomOut(gridComponent);
    }
    public void deleteOneComponent(int x,int y){
        Component c = gridComponent[x][y];
        if (c!=null)
            c.deleteMyself(gridComponent,components);
    }
}
