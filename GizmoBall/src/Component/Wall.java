package Component;

import AssistantClass.Type;

import java.util.List;

public class Wall extends Component {
    //这个属性在父类中有
    //angel 这面墙本身相对于水平线的角度 0和90度
    public Wall(int scale,int angle, int grid_x, int grid_y) {
        super(scale, angle, grid_x, grid_y);
        super.type = Type.Wall;
        super.updateImage(type,angle);
    }

    public void handleBall(Ball ball, double incTime, Type[][] pixels, Component[][] gridComponent, List<Component> components)
    {
        if (angle == 0){//水平墙壁
            super.reverseBall_horizontal(ball);
        }else{//竖直墙壁 90度
            super.reverseBall_vertical(ball);
        }
    }
}
