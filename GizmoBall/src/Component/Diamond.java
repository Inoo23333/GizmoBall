package Component;

import AssistantClass.Speed;
import AssistantClass.Type;

import java.util.List;

public class Diamond extends Component {

    int limit = 5;//将差距设置在10以内，都认为是完全的水平或者竖直碰撞

    public Diamond(int scale,int angle, int grid_x, int grid_y) {
        super(scale, angle, grid_x, grid_y);
        super.type = Type.Diamond;
        super.updateImage(type,angle);
    }

    //重写父类函数
    public void handleBall(Ball ball,double incTime,Component[][] gridComponent,List<Component> components){

         int dx = ball.getCenter_x() - getCenter_x();//小球和钻石的水平圆心差
         int dy = ball.getCenter_y() - getCenter_y();//小球和钻石的竖直圆心差
         if (Math.abs(dx)<limit||Math.abs(dy)<limit){
             super.reverseBall_180(ball);
         }else{//其他情况下，有一半概率也直接选择反向
             if (randomHit())
                 super.reverseBall_180(ball);
             else//另一半概率做其他处理
                 complexReversionBall(ball,dx,dy);
         }
    }


    //处理其他情况，当小球的圆心和钻石的圆心不在一条水平或者竖直线上时，分四种情况处理
    private void complexReversionBall(Ball ball, int dx, int dy){

        Speed speed = ball.getSpeed();
        double v_x = speed.getV_x();
        double v_y = speed.getV_y();
        //非180度返回，而是根据二者的相对位置来判断小球的转向情况
        if ((dx<=0&&dy>0) || (dx>0&&dy<=0)){//小球在钻石的左上角or小球在钻石的右上角
            speed.setV_x(0-v_y);
            speed.setV_y(0-v_x);
        }else if ((dx>0&&dy>0)||(dx<=0&&dy<=0)){//小球在钻石的右上角or小球在钻石的左下角
            speed.setV_x(v_y);
            speed.setV_y(v_x);
        }
        ball.setSpeed(speed);
    }
}
