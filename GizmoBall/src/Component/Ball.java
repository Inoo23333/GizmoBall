package Component;

import AssistantClass.GridPosition;
import AssistantClass.PixelPosition;
import AssistantClass.Speed;
import AssistantClass.Type;
import Component.Component;

import java.util.ArrayList;
import java.util.List;

public class Ball extends Component {
    Speed speed;
    double gravity;
    int radius; //是按照pixel来算的

    public Ball(int scale,int angle, int grid_x, int grid_y) {
        super(scale, angle, grid_x, grid_y);
        super.type = Type.Ball;
        speed = new Speed(0,0);
        gravity = 10;
        radius = scale * 19;
        super.updateImage(type,angle);
    }

    //重写父类的放大方法
    public void zoomIn(Component[][] gridComponent){
        super.zoomIn(gridComponent);
        radius+=19;
    }

    //重写父类的缩小方法
    public void zoomOut(Component[][] gridComponent){
        super.zoomOut(gridComponent);
        radius-=19;
    }


    /**
     *
     * @param incTime
     * 更新小球的速度
     */
    private void updateSpeed(double incTime){
        double v_y = speed.getV_y();
        v_y = v_y+gravity*incTime;
        speed.setV_y(v_y);
    }

    /**
     *
     * @param incTime
     * @return 返回小球下一次运动后的圆心，并不更改小球本身属性
     */
    private PixelPosition calNextCenterPixelPos(double incTime){
        double d_x = speed.getV_x()*incTime;
        double d_y = speed.getV_y() + gravity*incTime*incTime/2;
        return centerPixelPos.getNewPixelPositionByShift((int)d_x,(int)d_y);
    }

    /**
     *
     * @param incTime
     * @return 返回小球将要占据的格子坐标的列表
     */
    public List<GridPosition> calNextStayGridPoses(double incTime){
        List<GridPosition> grids = new ArrayList<>();
        //先计算新的中心像素坐标，再根据这个计算小球将会占据哪些格子
        PixelPosition center = calNextCenterPixelPos(incTime);
        PixelPosition left = new PixelPosition(center.getPixel_x()-radius,center.getPixel_y());
        PixelPosition right = new PixelPosition(center.getPixel_x()+radius,center.getPixel_y());
        PixelPosition up = new PixelPosition(center.getPixel_x(),center.getPixel_y()-radius);
        PixelPosition down = new PixelPosition(center.getPixel_x(),center.getPixel_y()+radius);
        int l = left.getGrid_x();
        int r = right.getGrid_y();
        int u = up.getGrid_y();
        int d = down.getGrid_y();
        for (int i=l;i<=r;i++)
            for (int j=u;j<=d;j++)
                grids.add(new GridPosition(i,j));
        return grids;
    }


    /**
     *
     * @param incTime
     * @return 用当前小球的速度进行一次移动
     */
    public void moveOneStep(double incTime) {
        //先更新自身的坐标属性
        leftUpPixelPos.updatePixelPositionByShift((int)speed.getShift_x(incTime),
                (int)speed.getShift_y(gravity,incTime));
        gridPosition.updateGridPositionByPixelPosition(leftUpPixelPos);
        centerPixelPos = new PixelPosition((int)(leftUpPixelPos.getPixel_x()+radius),
                (int)(leftUpPixelPos.getPixel_y()+radius));
        updateSpeed(incTime);
    }


    public Speed getSpeed() {
        return speed;
    }
    public void setSpeed(Speed speed) {
        this.speed = speed;
    }
    public void setGravity(double gravity) {
        this.gravity = gravity;
    }
    public double getGravity() {
        return gravity;
    }
    public double getRadius() {
        return radius;
    }
    public void setRadius(int radius) {
        this.radius = radius;
    }

}
