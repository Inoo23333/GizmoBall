package Component;

import AssistantClass.GridPosition;
import AssistantClass.PixelPosition;
import AssistantClass.Speed;
import AssistantClass.Type;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.List;
import java.util.Random;

public class Component {
    private static int next_id;//用来标注不同组件
    protected int trans = 40;//用来计算像素布局

    int id;
    int scale = 1;
    int angle=0; // 0 90 180 270 四个方向 0是正常的初始方向，顺时针旋转
    Type type;
    GridPosition gridPosition;
    PixelPosition leftUpPixelPos;
    PixelPosition centerPixelPos;

    Image componentImage;


    /*
    PART1 constructor
     */
    public Component(){

    }

    //每个组件的id都不同
    //构造组件的时候，只传格子坐标，不用像素坐标
    public Component(int scale, int angle, int grid_x, int grid_y) {
        ++this.next_id;
        id = next_id;
        this.scale = scale;
        this.angle = angle;
        this.gridPosition = new GridPosition(grid_x,grid_y);
        leftUpPixelPos = new PixelPosition(gridPosition.getLeftUpPixelPos_x(),gridPosition.getLeftUpPixelPos_y());
        centerPixelPos = new PixelPosition(gridPosition.getCentetPixelPosy_x(scale),gridPosition.getCentetPixelPosy_y(scale));
    }




    /*
    PART2 给子类的函数，部分子类会重写
     */
    //父类判断能否放大的方法基本上是 方形/圆形等都满足的，只有三角形需要重写这个函数
    //三角形重写这个函数
    public  boolean canZoomIn(Component[][] gridComponent){
        //当前组件规模>=3,不允许再放大
        if(this.scale>=3)return false;
        //如果当前组件可以放大，计算它在放大后将会占据哪些位置(按照正方形和圆形占据的位置算)，
        // 再对比这些位置是否已经有别的组件了，如果有，返回Flase
        int new_scale = this.scale+1;
        int grid_x = getGrid_x();
        int grid_y = getGrid_y();
        for (int i=0;i<new_scale;i++)
            for (int j=0;j<new_scale;j++)
                if(gridComponent[grid_x+i][grid_y+j]!=null)
                    if (gridComponent[grid_x+i][grid_y+j].type!=this.type
                            &&gridComponent[grid_x+i][grid_y+j].type!=Type.Wall)
                    return false;
        return true;
    }


    //按照正方形来更新组件盘
    //小球和三角形需要重写这个方法 (diamond当作正方形来放，没有圆心参数 ，不需要重写)
    public void zoomIn(Component[][] gridComponent){
        //更新组件自己
        ++scale;
        centerPixelPos = new PixelPosition(gridPosition.getCentetPixelPosy_x(this.scale),
                gridPosition.getCentetPixelPosy_y(this.scale));
        //更新棋盘上的 组件盘
        updateGridComponentAsSquare(gridComponent,scale);

    }


    //父类判断能否缩小 子类不用重写
    public boolean canZoomOut(Component[][] gridComponent)
    {
        if(this.scale<=1)return false;

        //只要当前组件的Scale不小于等于1，那就不存在影响缩小会影响其他组件的情况，永远都是可以缩小的，所以永远返回true
        return true;
    }

    //小球 钻石 和三角形需要重写这个方法 (diamond当作正方形来放，没有圆心参数 ，不需要重写)(diamond当作正方形来放，没有圆心参数 ，不需要重写)
    public void zoomOut(Component[][] gridComponent){
        int old_scale = this.scale;

        //更新组件自己
        --this.scale;
        centerPixelPos = new PixelPosition(gridPosition.getCentetPixelPosy_x(this.scale),
                gridPosition.getCentetPixelPosy_y(this.scale));

        //更新棋盘上的 组件盘
        deleteExtraGridComponentAsSquare(gridComponent,old_scale);

    }

    //父类，三角形重写
    public boolean canRotate(Component[][] gridComponent)
    {
        return true;
    }

    //三角形的旋转要重写  如果是其他的组件，只要更改图片显示就可以了，不需要重写 （三角形 DONE）
    public void rotate(Component[][] gridComponent){
        angle = (angle+90)%360;
        updateImage(type,angle);
    }

    //三角形需要重写 (三角形 DONE)
    public void deleteMyself(Component[][] gridComponent,List<Component> components){
        //1.删除本身在component列表中的存在
        int myIndex = components.indexOf(this);
        components.remove(myIndex);
        //2.删除本身在组件盘上的存在
        deleteGridComponentAsSquare(gridComponent,scale);
    }

    //由子类重写
    public void handleBall(Ball ball,double incTime,Component[][] gridComponent,List<Component> components){
        //donothing 全部由子类重写
    }


    /*
    PART3 GETTR SETTER函数
     */

    public String getType(){
        return type.toString();
    }
    //在面板i上面画组件对象
    public void drawComponent(Graphics g, JPanel i){
        g.drawImage(componentImage, getLeftUp_x(), getLeftUp_y(),
                trans*scale, trans*scale,(ImageObserver) i);
    }
    //普通属性的Getter Setter方法
    public int getAngle() {
        return angle;
    }

    public int getScale() {
        return scale;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }




    //以下函数是对格子坐标、左上角像素坐标和中心像素坐标的Getter Setter
    //这里没有直接对封装坐标用Getter Setter 因为这样可以在其他类中使用组件的时候，不用层层跳入封装的坐标类
    public void setGrid_x(int grid_x) {
        this.gridPosition.setGrid_x(grid_x);
    }
    public void setGrid_y(int grid_y) {
        this.gridPosition.setGrid_y(grid_y);
    }
    public int getGrid_x() {
        return this.gridPosition.getGrid_x();
    }
    public int getGrid_y() {
        return this.gridPosition.getGrid_y();
    }

    public int getLeftUp_x() {
        return leftUpPixelPos.getPixel_x();
    }
    public int getLeftUp_y() {
        return leftUpPixelPos.getPixel_y();
    }

    public int getCenter_x() {
        return centerPixelPos.getPixel_x();
    }
    public int getCenter_y() {
        return centerPixelPos.getPixel_y();
    }








    /*
   PART4 用于更新棋盘格子、删除棋盘格子、更新图片、翻转小球等通用函数
    */
    //更新棋盘上的 组件盘   按照正方形来添加引用
    public void updateGridComponentAsSquare(Component[][] gridComponent,int new_scale){

        int grid_x = getGrid_x();
        int grid_y = getGrid_y();
        for (int i=0;i<new_scale;i++)
            for (int j=0;j<new_scale;j++)
                gridComponent[grid_x+i][grid_y+j]=this;
    }

    //更新棋盘上的 组件盘 按照正方形来进行全部删除
    protected void deleteGridComponentAsSquare(Component[][] gridComponent,int old_scale){
        /*if(gridComponent!=null){
            for (int i=0;i<20;i++)
                for (int j=0;j<20;j++)
                    if(gridComponent[i][j]==this)
                        gridComponent[i][j]=null;
        }*/
        int grid_x = getGrid_x();
        int grid_y = getGrid_y();
        for (int i=0;i<old_scale;i++)
            for (int j=0;j<old_scale;j++)
                    gridComponent[grid_x+i][grid_y+j]=null;
    }

    //更新棋盘上的 组件盘 //删除多余的部分，只是删除圈引用
    protected void deleteExtraGridComponentAsSquare(Component[][] gridComponent,int old_scale){
        int grid_x = getGrid_x();
        int grid_y = getGrid_y();
        for (int i=0;i<old_scale;i++)
            for (int j=0;j<old_scale;j++)
                if (i>=scale||j>scale)
                    gridComponent[grid_x+i][grid_y+j]=null;
    }

    //按照类型和角度来获取对应图片
    //TODO 图片命令按照 格式+角度的方式来
    protected void updateImage(Type type,int angle){
        if (type==Type.Triangle||type==Type.Square||type==Type.Opipe)
            componentImage = Toolkit.getDefaultToolkit().
                getImage("src/Images/"+type+angle+".png");
        else
            componentImage = Toolkit.getDefaultToolkit().
                    getImage("src/Images/"+type+".png");
    }

    protected boolean randomHit(){
        //使用元参构造函数，随机生成0-9内随机整数
        Random random = new Random();
        //如果随机数大于4，则让它们碰撞
        return random.nextInt()>4;
    }

    /**
     * 将小球速度直接反向 180度
     * @param ball
     */
    protected void reverseBall_180(Ball ball){
        Speed speed = ball.getSpeed();
        double v_x = speed.getV_x();
        double v_y = speed.getV_y();
        speed.setV_x(0-v_x);
        speed.setV_y(0-v_y);
        ball.setSpeed(speed);
    }
    //小球与水平的物体碰撞，水平速度不变，竖直速度反向
    protected void reverseBall_horizontal(Ball ball){
        Speed speed = ball.getSpeed();
        double v_y = speed.getV_y();
        speed.setV_y(0-v_y);
        ball.setSpeed(speed);
    }
    //小球与竖直的物体碰撞，水平速度反向，竖直速度不变
    protected void reverseBall_vertical(Ball ball){
        Speed speed = ball.getSpeed();
        double v_x = speed.getV_x();
        speed.setV_x(0-v_x);
        ball.setSpeed(speed);
    }

    @Override
    public String toString() {
        return
                "" + type +
                "|" + scale +
                "|" + angle +
                "|" + getGrid_x() +
                "|" + getGrid_y()
                ;
    }
}

