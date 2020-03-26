package AssistantClass;

public class Speed {
    double v_x;
    double v_y;

    //public void changeSpeed() {}

    public Speed(double v_x, double v_y) {
        this.v_x = v_x;
        this.v_y = v_y;
    }

    public void setV_x(double v_x) {
        this.v_x = v_x;
    }

    public void setV_y(double v_y) {
        this.v_y = v_y;
    }

    public double getV_x() {
        return v_x;
    }

    public double getV_y() {
        return v_y;
    }

    /**
     *
     * @param inc
     * @return 返回水平位移
     */
    public double getShift_x(double inc){
        return v_x*inc;
    }

    /**
     *
     * @param inc
     * @return 返回竖直位移
     */
    public double getShift_y(double gravity,double inc){
        return v_y*inc+gravity*inc*inc/2;
    }

}
