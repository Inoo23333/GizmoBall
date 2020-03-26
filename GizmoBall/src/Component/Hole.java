package Component;

import AssistantClass.Type;
import Component.Component;

import java.util.List;

public class Hole extends Component {
    public Hole(int scale,int angle, int grid_x, int grid_y) {
        super(scale, angle, grid_x, grid_y);
        super.type = Type.Hole;
        super.updateImage(type,angle);

    }

    public void handleBall(Ball ball,double incTime,Component[][] gridComponent,List<Component> components){
        ball.deleteMyself(gridComponent,components);
    }
}
