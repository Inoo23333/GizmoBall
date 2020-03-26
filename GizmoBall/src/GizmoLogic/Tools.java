package GizmoLogic;

import AssistantClass.PixelPosition;

public class Tools {
    public Tools(){
    }
    public void rotate(Board board,int screen_x, int screen_y)
    {
        PixelPosition pixelPosition = new PixelPosition(screen_x,screen_y);
        board.rotate(pixelPosition.getGrid_x(),pixelPosition.getPixel_y());
    }

    public void remove(Board board,int screen_x, int screen_y)
    {
        PixelPosition pixelPosition = new PixelPosition(screen_x,screen_y);
        board.deleteOneComponent(pixelPosition.getGrid_x(),pixelPosition.getPixel_y());
    }

    public void zoomIn(Board board,int screen_x, int screen_y)
    {
        PixelPosition pixelPosition = new PixelPosition(screen_x,screen_y);
        board.zoomIn(pixelPosition.getGrid_x(),pixelPosition.getPixel_y());
    }

    public void zoomOut(Board board,int screen_x, int screen_y)
    {
        PixelPosition pixelPosition = new PixelPosition(screen_x,screen_y);
        board.zoomOut(pixelPosition.getGrid_x(),pixelPosition.getPixel_y());
    }
}
