import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.UnsupportedEncodingException;

public class FileProperty {
    public JPanel rootpane;
    private JTextField namejtf;
    private JLabel typeLabel;
    private JLabel locationlabel;
    private JComboBox comboBox1;
    private String filename;
    public FileProperty(Component parent, String name,UFD u,FCB f,String path,String property)
    {
        if(u==null)
            return;
        filename=name;
        namejtf.setText(name);

        if(f!=null)
            typeLabel.setText("文件");
        else
            typeLabel.setText("文件夹");


        locationlabel.setText(path);
        comboBox1.addItem("只读");
        comboBox1.addItem("读取/写入");

        if(property.equals("a"))
            comboBox1.setSelectedIndex(1);
        else
            comboBox1.setSelectedIndex(0);

        comboBox1.addItemListener(e -> {
            int Index=comboBox1.getSelectedIndex();
            String newproperty;
            if(Index==0)
                newproperty="r";
            else
                newproperty="a";

            if(f==null)
            {
                u.property=newproperty;
            }
            else
                f.shuxing=newproperty;

        });
        JFrame frame = new JFrame(name);
        frame.setContentPane(rootpane);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                String newname=namejtf.getText();
                if(!newname.equals(filename))
                {
                    if(f==null)
                    {
                        if(!MFD.rename(u,newname));
                            JOptionPane.showMessageDialog(null, "存在相同名字的文件,重命名失败", "提示", JOptionPane.ERROR_MESSAGE);

                    }
                    else
                    {
                        if (u.renameFile(f.filename, newname) == -1) {
                            JOptionPane.showMessageDialog(null, "存在相同名字的文件,重命名失败", "提示", JOptionPane.ERROR_MESSAGE);

                        }
                    }
                }
                frame.dispose();
            }
        });
        frame.pack();
        frame.setLocationRelativeTo(parent);
        frame.setVisible(true);
    }
}