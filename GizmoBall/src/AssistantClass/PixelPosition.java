package AssistantClass;

/*
grid_x : 0-19
pixel_x: 0-799
 */
public class PixelPosition {
    int pixel_x; //pixel_x
    int pixel_y;
    private int trans = 40;

    public PixelPosition(int pixel_x, int pixel_y) {
        this.pixel_x = pixel_x;
        this.pixel_y = pixel_y;
    }

    public int getPixel_x() {
        return pixel_x;
    }

    public int getPixel_y() {
        return pixel_y;
    }

    public void setPixel_x(int pixel_x) {
        this.pixel_x = pixel_x;
    }

    public void setPixel_y(int pixel_y) {
        this.pixel_y = pixel_y;
    }

    public int getGrid_x()
    {
        return pixel_x/trans;
    }

    public int getGrid_y()
    {
        return pixel_y/trans;
    }

    public boolean equals(PixelPosition o){
        return pixel_x==o.pixel_x && pixel_y==o.pixel_y;
    }


    public PixelPosition getNewPixelPositionByShift(int x,int y){
       return new PixelPosition(pixel_x+x,pixel_y+y);
    }
    public void updatePixelPositionByShift(int x,int y){
        pixel_x+=x;
        pixel_y+=y;
    }

    public GridPosition getGridPos(){
        return new GridPosition(getGrid_x(),getGrid_y());
    }
}
