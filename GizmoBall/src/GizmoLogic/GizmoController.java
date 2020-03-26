package GizmoLogic;

import AssistantClass.PixelPosition;
import Component.Component;
import Component.*;

import java.util.List;


public class GizmoController {
    Board board;
    static boolean ended = false;

    FileHandler fileHandler;
    Tools tools;

    public GizmoController(){
        board = new Board();
        fileHandler = new FileHandler();
        tools = new Tools();
    }

    public Ball getBall()
    {
        return board.getBall();
    }
    public List<Component> getAllComponents()
    {
        return board.getComponents();
    }


    public void addOneComponent(String type, int pixel_x, int pixel_y)
    {
        PixelPosition pixelPosition = new PixelPosition(pixel_x,pixel_y);
        //像素点的y是横坐标，x才是纵坐标
        board.addOneComponent(type,1,0,pixelPosition.getGrid_y(),pixelPosition.getGrid_x());
    }

    public static boolean isEnded() {
        return ended;
    }
    public void moveOneStep(double incTime)
    {
        Boolean ball_exist = board.moveBallOneStep(incTime);
        ended = !ball_exist;
    }

    //0-left 1-right
    public void moveLeftFlipper(int moveDirection) { board.moveLeftFlipper(moveDirection); }
    //0-left 1-right
    public void moveRightFlipper(int moveDirection)
    {
        board.moveRightFlipper(moveDirection);
    }














    /**
     * file handler function
     */
    public void newGame(){
        ended =false;
        board = new Board();
    }
    public void saveBoard()
    {
        fileHandler.saveBoard(board);
    }

    public void loadBoard()
    {
        ended = false;
        this.board = fileHandler.loadBoard();
    }

    /**
     * four tools fuction
     */
    //像素点的y是横坐标，x才是纵坐标

    public void rotate(int screen_x, int screen_y)
    {
        tools.rotate(board,screen_y,screen_x);
    }

    public void remove(int screen_x, int screen_y)
    {
        tools.remove(board,screen_y,screen_x);
    }

    public void zoomIn(int screen_x, int screen_y)
    {
        tools.zoomIn(board,screen_y,screen_x);
    }

    public void zoomOut(int screen_x, int screen_y)
    {
        tools.zoomOut(board,screen_y,screen_x);
    }

    //TODO 用来测试，记得删除
    public Component[][] getGridComponent(){
        return board.gridComponent;
    }

}
