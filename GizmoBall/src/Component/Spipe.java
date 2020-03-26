package Component;

import AssistantClass.Speed;
import AssistantClass.Type;
import Component.Component;

import java.util.List;

public class Spipe extends Component {
    int limit=2;
    public Spipe(int scale,int angle, int grid_x, int grid_y) {
        super(scale, angle, grid_x, grid_y);
        super.type = Type.Spipe;
        super.updateImage(type,angle);

    }

    //TODO 不太确定方向角度对应关系
    //0是水平管道，90是竖直管道（角度是相对于地面的角度，和墙壁Wall组件保持一致）
    public void handleBall(Ball ball,double incTime,Component[][] gridComponent,List<Component> components)
    {
        int dx = ball.getCenter_x() - getCenter_x();//小球和直管道的水平中心差
        int dy = ball.getCenter_y() - getCenter_y();//小球和直管道的竖直中心差
        Speed speed = ball.getSpeed();
        double v_x = speed.getV_x();
        double v_y = speed.getV_y();

        if (angle==0){//水平管道
            if (Math.abs(dy)<limit){//当小球和管道中心的高度差在limit以内，认为小球可以水平通过该管道
                double x = v_x*incTime;//小球的下一次水平位移
                double ball_new_left_x = ball.getLeftUp_x()+x;
                double ball_new_right_x = ball.getLeftUp_x()+trans*ball.getScale()+x;
                double pipe_left_x = getLeftUp_x();
                double pipe_right_x = getLeftUp_x()+trans*scale;
                if (ball_new_left_x<=pipe_left_x||ball_new_right_x>=pipe_right_x){//小球即将离开管道
                    ball.setGravity(10);
                    return;
                }
                //小球正常通过管道前进
                ball.setGravity(0);//进入管道要将重力置0
                speed.setV_y(0);//仅保留水平方向的速度
                ball.setSpeed(speed);
            }else{//否则，认为小球不会通过管道，而是产生碰撞
                super.reverseBall_180(ball);
            }
        }else if (angle==90){//竖直管道
            double x = v_y*incTime;//小球的下一次竖直位移
            double ball_new_left_y = ball.getLeftUp_y()+x;
            double pipe_left_x = getLeftUp_x();
            double pipe_right_x = getLeftUp_x()+trans*scale;
            if (Math.abs(dx)<limit){//当小球和管道中心的水平差在limit以内，认为小球可以竖直通过该管道
                double y = v_y*incTime;//小球的下一次竖直位移
                double ball_new_up_y = ball.getLeftUp_y()+x;
                double ball_new_down_y = ball.getLeftUp_y()+trans*ball.getScale()+x;
                double pipe_up_y = getLeftUp_y();
                double pipe_down_y = getLeftUp_y()+trans*scale;
                if (ball_new_up_y+y<=pipe_left_x||ball_new_down_y+y>=pipe_right_x){//小球即将离开管道
                    ball.setGravity(10);
                    return;
                }
                //小球正常通过管道前进
                ball.setGravity(0);//进入管道要将重力置0
                speed.setV_x(0);//仅保留竖直方向的速度
                ball.setSpeed(speed);
            }else{//否则，认为小球不会通过管道，而是产生碰撞
                super.reverseBall_180(ball);
            }
        }

    }
}
