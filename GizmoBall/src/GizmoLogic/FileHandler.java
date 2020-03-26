package GizmoLogic;

import Component.Component;

import javax.swing.*;
import java.io.*;

public class FileHandler {

    public FileHandler() { }

    /*
     * 调用打印函数，（component已经重写toString:将每个组件的构造属性用"｜"连接成字符串)
     * 通过循环逐行写入，最后保存为".gizmo"文件
     */
    public static void saveBoard(Board board) {
        if (board != null) {
            JFileChooser jf = new JFileChooser("./");
            jf.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            jf.showDialog(null, null);

            File doc = jf.getSelectedFile();
            if (doc != null) {
                String str = doc.getAbsolutePath() + "/" + System.currentTimeMillis() + ".gizmo";
                File file = new File(str);
                try {
                    file.createNewFile();
                    FileWriter writer = new FileWriter(file);
                    for (Component c : board.getComponents())
                        if ( !c.getType() .equals("Ball"))
                            writer.write(c.toString()+ "\n");
                    writer.flush();
                    writer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 将每个组件的属性逐行解析
     * type|scale|angel|x|y
     * @return 返回新的board对象
     */

    public static Board loadBoard() {
        Board board = new Board();
        JFileChooser jf = new JFileChooser("./");
        jf.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jf.showOpenDialog(null);
        File file = jf.getSelectedFile();
        BufferedReader bufferedReader = null;

        if (file != null) {
            String loadPath = file.getAbsolutePath();
            try {
                bufferedReader = new BufferedReader(new FileReader(loadPath));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            String line = null;
            String attributes[];
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    attributes = line.split("|");
                    if (attributes.length == 5) {
                        String type = attributes[0];
                        int scale = Integer.parseInt(attributes[1]),
                                angel = Integer.parseInt(attributes[2]),
                                x = Integer.parseInt(attributes[3]),
                                y = Integer.parseInt(attributes[4]);
                        board.addOneComponent(type, scale, angel, x, y);
                        }
                    }
                } catch (IOException e) {
                e.printStackTrace();
            }
            return board;
        }
        return null;
    }

}
