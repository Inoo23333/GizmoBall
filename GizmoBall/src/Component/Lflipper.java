package Component;

import AssistantClass.Type;
import Component.Component;

import java.util.List;

public class Lflipper extends Component {
    public Lflipper(int scale,int angle, int grid_x, int grid_y) {
        super(scale, angle, grid_x, grid_y);
        super.type = Type.Lflipper;
        super.updateImage(type,angle);

    }

    //移动一个格子
    public void move(int moveDirection,Rflipper rflipper,Component[][] gridComponent){

        int new_gx=0;
        if (moveDirection==0){//向左移动
            new_gx = getGrid_x()-1;
            if (new_gx<0) return;
        }else{//向右移动
            //先检测是否可以向右移动
            new_gx = getGrid_x()+1;
            if (new_gx>19)return;
            if (rflipper!=null){
                if (new_gx+scale > rflipper.getGrid_x())return;
            }
        }
        deleteGridComponentAsSquare(gridComponent,getScale());
        setGrid_x(new_gx);
        updateGridComponentAsSquare(gridComponent,getScale());
    }

    public void handleBall(Ball ball,double incTime,Component[][] gridComponent,List<Component> components)
    {
        super.reverseBall_horizontal(ball);
    }
}
