package Component;

import AssistantClass.PixelPosition;
import AssistantClass.Speed;
import AssistantClass.Type;

import java.util.List;

public class Triangle extends Component {
    public Triangle(int scale,int angle, int grid_x, int grid_y) {
        super(scale, angle, grid_x, grid_y);
        super.type = Type.Triangle;
        super.updateImage(Type.Triangle,angle);
    }




    //重写父类的是否可以放大方法
    public  boolean canZoomIn(Component[][] gridComponent){
        //不用调用父类方法，直接重写就行了

        //当前组件规模>=3,不允许再放大
        if(this.scale>=3)return false;

        //如果当前组件可以放大，计算它在放大后将会占据哪些位置(按照等腰直角三角形占据的位置算)，
        // 再对比这些位置是否已经有别的组件了，如果有，返回Flase
        int new_scale = this.scale+1;
        int grid_x = getGrid_x();
        int grid_y = getGrid_y();
        if (angle==0){
            for (int i=0;i<new_scale;i++)
                for (int j=0;j<=i;j++)//此处j的截止处和父类不同，其他都一样
                    if(gridComponent[grid_x+i][grid_y+j]!=null)
                        if(gridComponent[grid_x][grid_y].type!=this.type
                                &&gridComponent[grid_x][grid_y].type!=Type.Wall)
                            return false;
        }else if (angle ==90){
            for (int i=0;i<new_scale;i++)
                for (int j=0;j<new_scale-i;j++)//此处j的截止处和父类不同，其他都一样
                    if(gridComponent[grid_x+i][grid_y+j]!=null)
                        if(gridComponent[grid_x][grid_y].type!=this.type
                                &&gridComponent[grid_x][grid_y].type!=Type.Wall)
                            return false;
        }else if(angle == 180){
            for (int i=0;i<new_scale;i++)
                for (int j=i;j<new_scale;j++)//此处j的截止处和父类不同，其他都一样
                    if(gridComponent[grid_x+i][grid_y+j]!=null)
                        if(gridComponent[grid_x][grid_y].type!=this.type
                                &&gridComponent[grid_x][grid_y].type!=Type.Wall)
                            return false;
        }else{
            for (int i=0;i<new_scale;i++)
                for (int j=new_scale-i-1;j<new_scale;j++)//此处j的截止处和父类不同，其他都一样
                    if(gridComponent[grid_x+i][grid_y+j]!=null)
                        if(gridComponent[grid_x][grid_y].type!=this.type
                                &&gridComponent[grid_x][grid_y].type!=Type.Wall)
                            return false;
        }

        return true;
    }


    //重写父类的放大方法
    public void zoomIn(Component[][] gridComponent){
        //更新组件自己
        ++this.scale;
        centerPixelPos = new PixelPosition(gridPosition.getCentetPixelPosy_x(this.scale),
                gridPosition.getCentetPixelPosy_y(this.scale));

        //更新棋盘上的 组件盘
        updateGridComponentAsTriangle(gridComponent,this.scale,angle);
    }

    //重写父类的缩小方法
    public void zoomOut(Component[][] gridComponent){
        int old_scale = this.scale;

        //更新组件自己
        --this.scale;
        centerPixelPos = new PixelPosition(gridPosition.getCentetPixelPosy_x(this.scale),
                gridPosition.getCentetPixelPosy_y(this.scale));

        //更新棋盘上的 组件盘
        updateGridComponentAsTriangle(gridComponent,this.scale,angle);

    }

    public boolean canRotate(Component[][] gridComponent)
    {
        int grid_x = getGrid_x();
        int grid_y = getGrid_y();
        for (int i=0;i<scale;i++){
            for (int j=0;j<scale;j++){
                if (gridComponent[grid_x+i][grid_y+j].type!=Type.Triangle
                        &&gridComponent[grid_x+i][grid_y+j].type!=Type.Wall)
                    return false;
            }
        }
        return true;
    }
    public void rotate(Component[][] gridComponent){
        //如果三角形不能旋转，什么也不做
        if(!canRotate(gridComponent))return;

        super.rotate(gridComponent);

        //更新棋盘上的 组件盘
        //这里不需要先删除，直接更新就可以了
        updateGridComponentAsTriangle(gridComponent,scale,angle);
    }

    public void deleteMyself(Component[][] gridComponent, List<Component> components){
        //1.删除本身在component列表中的存在
        int myIndex = components.indexOf(this);
        components.remove(myIndex);
        //2.删除本身在组件盘上的存在
        deleteGridComponentAsTriangle(gridComponent,scale,angle);
    }

    /*
    全部更新
     */
    //更新棋盘上的 组件盘   按照三角形来添加引用
    private void updateGridComponentAsTriangle(Component[][] gridComponent, int new_scale, int angle){
        int grid_x = getGrid_x();
        int grid_y = getGrid_y();
        if (angle==0){
            for (int i=0;i<new_scale;i++)
                for (int j=0;j<=i;j++)//此处j的截止处和父类不同，其他都一样
                    gridComponent[grid_x][grid_y]=this;
        }else if (angle ==90){
            for (int i=0;i<new_scale;i++)
                for (int j=0;j<new_scale-i;j++)//此处j的截止处和父类不同，其他都一样
                    gridComponent[grid_x][grid_y]=this;
        }else if(angle == 180){
            for (int i=0;i<new_scale;i++)
                for (int j=i;j<new_scale;j++)//此处j的截止处和父类不同，其他都一样
                    if(gridComponent[grid_x+i][grid_y+j]!=null)
                        gridComponent[grid_x][grid_y]=this;
        }else{
            for (int i=0;i<new_scale;i++)
                for (int j=new_scale-i-1;j<this.scale;j++)//此处j的截止处和父类不同，其他都一样
                    if(gridComponent[grid_x+i][grid_y+j]!=null)
                        gridComponent[grid_x][grid_y]=this;
        }
    }


    /*
   全部删除
    */
    protected void deleteGridComponentAsTriangle(Component[][] gridComponent,int old_scale,int angle){
        int grid_x = getGrid_x();
        int grid_y = getGrid_y();
        for(int i=0;i<old_scale;i++){
           for (int j=0;j<old_scale;j++){
               if (gridComponent[grid_x][grid_y].type==Type.Triangle)
                   gridComponent[grid_x][grid_y].type=null;
           }
        }
    }

    public void handleBall(Ball ball,double incTime,Component[][] gridComponent,List<Component> components)
    {
        int dx = ball.getCenter_x() - getCenter_x();//小球和直管道的水平中心差
        int dy = ball.getCenter_y() - getCenter_y();//小球和直管道的竖直中心差
        int length = (int)(ball.getRadius()+getScale()*trans/2);//小球半径+三角形的半个边长，是两者直接接触时，两个中心的差距值

        Speed speed = ball.getSpeed();
        double v_x = speed.getV_x();
        double v_y = speed.getV_y();
        if (angle==0){
            if ((dx<0&&Math.abs(dx)>length))//小球从左边与三角形竖直边碰撞
                super.reverseBall_vertical(ball);
            else if (dy<0&&Math.abs(dy)>length)
                super.reverseBall_vertical(ball);//小球从下面与三角形水平边碰撞
            else{//小球与三角形斜边碰撞
                if (Math.abs(v_y)>Math.abs(v_x)){//小球从上方与斜边进行碰撞
                    speed.setV_x(v_y);
                    speed.setV_y(0);
                    ball.setSpeed(speed);
                }else{//小球从右方与斜边碰撞
                    speed.setV_x(0);
                    speed.setV_y(v_x);
                    ball.setSpeed(speed);
                }
            }
        }else if(angle==90){
            if ((dx<0&&Math.abs(dx)>length))//小球从左边与三角形竖直边碰撞
                super.reverseBall_vertical(ball);
            else if (dy>0&&Math.abs(dy)>length)
                super.reverseBall_vertical(ball);//小球从上面与三角形水平边碰撞
            else{//小球与三角形斜边碰撞
                if (Math.abs(v_y)>Math.abs(v_x)){//小球从下方与斜边进行碰撞
                    speed.setV_x(0-v_y);
                    speed.setV_y(0);
                    ball.setSpeed(speed);
                }else{//小球从右方与斜边碰撞
                    speed.setV_x(0);
                    speed.setV_y(0-v_x);
                    ball.setSpeed(speed);
                }
            }

        }else if (angle==180){
            if ((dx>0&&Math.abs(dx)>length))//小球从右边与三角形竖直边碰撞
                super.reverseBall_vertical(ball);
            else if (dy>0&&Math.abs(dy)>length)
                super.reverseBall_vertical(ball);//小球从上面与三角形水平边碰撞
            else{//小球与三角形斜边碰撞
                if (Math.abs(v_y)>Math.abs(v_x)){//小球从下方与斜边进行碰撞
                    speed.setV_x(v_y);
                    speed.setV_y(0);
                    ball.setSpeed(speed);
                }else{//小球从左方与斜边碰撞
                    speed.setV_x(0);
                    speed.setV_y(v_x);
                    ball.setSpeed(speed);
                }
            }

        }else if (angle ==270){
            if ((dx>0&&Math.abs(dx)>length))//小球从右边与三角形竖直边碰撞
                super.reverseBall_vertical(ball);
            else if (dy<0&&Math.abs(dy)>length)
                super.reverseBall_vertical(ball);//小球从下面与三角形水平边碰撞
            else{//小球与三角形斜边碰撞
                if (Math.abs(v_y)>Math.abs(v_x)){//小球从上方与斜边进行碰撞
                    speed.setV_x(0-v_y);
                    speed.setV_y(0);
                    ball.setSpeed(speed);
                }else{//小球从左方与斜边碰撞
                    speed.setV_x(0);
                    speed.setV_y(0-v_x);
                    ball.setSpeed(speed);
                }
            }
        }
    }




}
