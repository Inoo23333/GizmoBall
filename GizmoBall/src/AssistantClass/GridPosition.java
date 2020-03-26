package AssistantClass;

/*
grid_x : 0-19
pixel_x: 0-799
 */
public class GridPosition {
    int grid_x;
    int grid_y;

    private int trans = 40;//每40个pixel是一格

    public GridPosition(int grid_x, int grid_y) {
        this.grid_x = grid_x;
        this.grid_y = grid_y;
    }

    public int getGrid_x() {
        return grid_x;
    }

    public int getGrid_y() {
        return grid_y;
    }

    public void setGrid_x(int grid_x) {
        this.grid_x = grid_x;
    }

    public void setGrid_y(int grid_y) {
        this.grid_y = grid_y;
    }

    public int getLeftUpPixelPos_x(){
        return grid_x*trans;
    }

    public int getLeftUpPixelPos_y(){
        return grid_y*trans;
    }

    public void updateGridPositionByPixelPosition(PixelPosition pixelPosition){
        grid_x = pixelPosition.getPixel_x()/trans;
        grid_y = pixelPosition.getPixel_y()/trans;
    }

    //通过传入规模参数，根据当前组件的左上角格子坐标来计算中心坐标，用于小球和钻石(其他组件也可以先加上)
    //要注意管道的中心像素，是按照长宽等比例放大后的距离来算的
    public int getCentetPixelPosy_x(int scale){
        return (grid_x+scale)*trans/2;
    }
    public int getCentetPixelPosy_y(int scale){
        return (grid_y+scale)*trans/2;
    }

    public boolean equals(GridPosition o) {
        return grid_x==o.grid_x && grid_y==o.grid_y;
    }
}
