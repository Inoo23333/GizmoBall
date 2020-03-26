package Component;

import AssistantClass.Speed;
import AssistantClass.Type;
import Component.Component;

import java.util.List;

public class Square extends Component {
    public Square(int scale,int angle, int grid_x, int grid_y) {
        super(scale, angle, grid_x, grid_y);
        super.type = Type.Square;
        super.updateImage(type,angle);

    }

    public void handleBall(Ball ball, double incTime,Component[][] gridComponent, List<Component> components)
    {
        int dx = ball.getCenter_x() - getCenter_x();//小球和方块的水平中心差
        int dy = ball.getCenter_y() - getCenter_y();//小球和方块的竖直中心差
        int length = (int)(ball.getRadius()+getScale()*trans/2);//小球半径+方块的半个边长，是两者直接接触时，两个中心的差距值
        if (Math.abs(dy)>length)//小球将与方块的水平面碰撞
            super.reverseBall_horizontal(ball);
        else if (Math.abs(dx)>length)//小球将与方块的竖直面碰撞
            super.reverseBall_vertical(ball);
        else//其他情况，让小球原路返回
            super.reverseBall_180(ball);
    }
}
