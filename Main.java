import java.awt.*;  
import java.awt.event.*; 
import javax.swing.*; 
import java.awt.Color;
import java.awt.Font;
import javax.swing.border.Border;
import PackUnpack.*;

public class Main extends JFrame implements ActionListener
{  
	JTextField tfDirectoryPath, tfPackedFileName;
	JTextField tfPackedFileInput;

	JButton btnPack, btnUnpack;
	
	JLabel lbDirectoryPath,lbPackedFileName;
	JLabel lbPackedFile;
	JLabel lbPackStatus, lbUnpackStatus;

	Main()
	{  
		JFrame f = new JFrame("File Packer and Unpacker");

		tfDirectoryPath = new JTextField();		 	// Accept directory
		tfPackedFileName = new JTextField();		// Accept packed file name
		tfPackedFileInput = new JTextField();
		lbPackedFile = new JLabel("Packed File Name:  ");	// Label for accepting generated packed file name 
		lbDirectoryPath = new JLabel("Directory Path: "); 
		lbPackedFileName = new JLabel("Packed File Name: "); 
		lbPackStatus = new JLabel();
		lbUnpackStatus = new JLabel();
		btnPack = new JButton("Pack Files");  
		btnUnpack = new JButton("Unpack Files");

		// Set JLabels for packing files inputs
		lbDirectoryPath.setBounds(80,50,140,20);
		lbPackedFileName.setBounds(80,80,140,20);

		// Set text fields for packing files inputs
		tfDirectoryPath.setBounds(250,50,170,20);
		tfPackedFileName.setBounds(250,80,170,20); 
		
		// Set JButton for packing files
		btnPack.setBounds(270,120,120,30);
		lbPackStatus.setBounds(200,170,270,30);
		
		// Set JLabels for unpacking files
		lbPackedFile.setBounds(80,250,140,20);
		// Set text fields for unpacking files
		tfPackedFileInput.setBounds(250,250,170,20);

		// Set JButton for unpacking files
		btnUnpack.setBounds(270,290,130,30);
		lbUnpackStatus.setBounds(200,330,270,30);
		// Add action listeners on JButtons
		btnPack.addActionListener(this);	  
		btnUnpack.addActionListener(this);

		Font font = new Font("Verdana", Font.BOLD, 12);
		lbUnpackStatus.setFont(font);
		lbDirectoryPath.setFont(font);
		lbPackStatus.setFont(font);
		lbPackedFile.setFont(font);
		lbPackedFileName.setFont(font);

		lbDirectoryPath.setForeground(Color.ORANGE);
		lbUnpackStatus.setForeground(Color.ORANGE);
		lbPackStatus.setForeground(Color.ORANGE);
		lbPackedFile.setForeground(Color.ORANGE);
		lbPackedFileName.setForeground(Color.ORANGE);

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().setBackground(Color.BLUE);
		    

		f.add(btnPack); f.add(btnUnpack);
	 	f.add(tfDirectoryPath); f.add(tfPackedFileName); f.add(tfPackedFileInput);
	 	f.add(lbDirectoryPath); f.add(lbPackedFile); f.add(lbPackedFileName);
	 	f.add(lbPackStatus); f.add(lbUnpackStatus);

		f.setSize(600,450);  
		f.setLayout(null);  
		f.setVisible(true);  
	}  
	
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource() == btnPack)
		{
			String dirPath = tfDirectoryPath.getText();
			String packFName = tfPackedFileName.getText();
			if(dirPath.equals("") || packFName.equals(""))
			{
				JOptionPane.showMessageDialog(null, "One or more fields empty for packing files");
			}
			else
			{	
				try
				{
					// Creating object directly packs all files in given directory
					// Not a good way, implement a seperate method later for calling pack()
					FilePacker fpack = new FilePacker(dirPath, packFName);
					lbPackStatus.setText("All Files packed into "+packFName+" file");
					tfDirectoryPath.setText("");
					tfPackedFileName.setText("");
				}
				catch(Exception e)
				{
					System.out.println(e);
				}
			}
		}
		else if(ae.getSource() == btnUnpack)
		{
			String packedFile = tfPackedFileInput.getText();
			if(packedFile.equals(""))
			{
				JOptionPane.showMessageDialog(null, "Please provide a file to unpack");
			}
			else
			{
				try
				{
					FileUnpacker unpackObj = new FileUnpacker();
					// Unpack given file
					boolean result = unpackObj.Unpack(packedFile);
					
					if(result)
					{
						lbUnpackStatus.setText("All Files unpacked in current directory");
					}
					else
					{
						//lbUnpackStatus.setText("Invalid packed file "+packedFile);
						JOptionPane.showMessageDialog(null, "Invalid packed file "+packedFile);
					}
					tfPackedFileInput.setText("");
				}
				catch(Exception e)
				{
					System.out.println(e);
				}
			}
		}
		// Reset status labels after a time interval
		ResetLabels();
	} 
	
	private void ResetLabels()
	{
		int delay = 3000; //milliseconds
 		ActionListener taskPerformer = new ActionListener() {
      		public void actionPerformed(ActionEvent evt) {
          		lbPackStatus.setText("");
        		lbUnpackStatus.setText("");
      		}
  		};
  		new Timer(delay, taskPerformer).start();
	}

	public static void main(String args[])
	{  
		Main obj = new Main();  
	}  
}
