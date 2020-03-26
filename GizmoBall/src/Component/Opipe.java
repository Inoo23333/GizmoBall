package Component;

import AssistantClass.Speed;
import AssistantClass.Type;
import Component.Component;

import java.util.List;

public class Opipe extends Component {
    int limit = 5;

    public Opipe(int scale,int angle, int grid_x, int grid_y) {
        super(scale, angle, grid_x, grid_y);
        super.type = Type.Opipe;
        super.updateImage(type,angle);

    }

    //管道为0度时对应L型，即小球入口是上和右，其他角度是顺时针旋转90度后的新方向
    public void handleBall(Ball ball,double incTime,Component[][] gridComponent,List<Component> components)
    {
        int dx = ball.getCenter_x() - getCenter_x();//小球和弯管道的水平中心差
        int dy = ball.getCenter_y() - getCenter_y();//小球和弯管道的竖直中心差
        Speed speed = ball.getSpeed();
        double v_x = speed.getV_x();
        double v_y = speed.getV_y();
        if (angle==0){ //小球会有三种情况，从上面进入(速度从竖直->水平)，从右面进入（速度从水平->竖直），或者从其他地方产生直接的反向碰撞
            if (dy>0&&Math.abs(dx)<=limit){//近视认为小球从上面入口进入
                speed.setV_x(v_y);
                speed.setV_y(0);
                ball.setSpeed(speed);
            }else if (dx>0&&Math.abs(dy)<=limit){//近似认为小球从右面入口进入
                speed.setV_x(0);
                speed.setV_y(v_x);
                ball.setSpeed(speed);
            }else{//小球与管道外围产生碰撞
                super.reverseBall_180(ball);
            }
        }else if (angle == 90){//同上
            if (dy<0&&Math.abs(dx)<=limit){//近视认为小球从下面入口进入
                speed.setV_x(0-v_y);
                speed.setV_y(0);
                ball.setSpeed(speed);
            }else if (dx>0&&Math.abs(dy)<=limit){//近似认为小球从右面入口进入
                speed.setV_x(0);
                speed.setV_y(0-v_x);
                ball.setSpeed(speed);
            }else{//小球与管道外围产生碰撞
                super.reverseBall_180(ball);
            }
        }else if (angle ==270){//同上
            if (dy<0&&Math.abs(dx)<=limit){//近视认为小球从下面入口进入
                speed.setV_x(v_y);
                speed.setV_y(0);
                ball.setSpeed(speed);
            }else if (dx<0&&Math.abs(dy)<=limit){//近似认为小球从左面入口进入
                speed.setV_x(0);
                speed.setV_y(v_x);
                ball.setSpeed(speed);
            }else{//小球与管道外围产生碰撞
                super.reverseBall_180(ball);
            }
        }else {//同上
            if (dy>0&&Math.abs(dx)<=limit){//近视认为小球从上面入口进入
                speed.setV_x(0-v_y);
                speed.setV_y(0);
                ball.setSpeed(speed);
            }else if (dx<0&&Math.abs(dy)<=limit){//近似认为小球从左面入口进入
                speed.setV_x(0);
                speed.setV_y(0-v_x);
                ball.setSpeed(speed);
            }else{//小球与管道外围产生碰撞
                super.reverseBall_180(ball);
            }
        }

    }
}
