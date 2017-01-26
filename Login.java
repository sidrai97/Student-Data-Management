import  javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class Login implements ActionListener,KeyListener
{
	GridLayout gridObj;
	JFrame frmObj;
	JLabel lblRollno,lblName,lblAdd,lblGen,lblQual,lblHob,lblSearch;
	JTextField txtRollno,txtName,txtAdd,txtS_Rollno,txtS_Name;
	JRadioButton btnM,btnFe;
	JComboBox cbx,cbxNameSearch;
	String[] st = {"Diploma","Under-Graduate","Post-Graduate"};
	String[] nameString = {""};
	JCheckBox cb1,cb2,cb3,cb4;
	JButton btnF,btnN,btnP,btnL,btnA,btnU,btnD,btnS,btnSave,btnCancel,btnSearch,btnS_rollno,btnS_name,btnGO_R,btnGO_N;
	JPanel pnlGen,pnlbtn1,pnlbtn2,pnlHob;
	JLabel pnlErr,errDet;
	ButtonGroup bg;
	static String err="123";
	JDialog sd;
	ArrayList<String> nameList;
	ArrayList<Integer> rollnoList;

	//database
	String Url="",Query="";
	Connection con;
	Statement stmt;
	PreparedStatement ps;
	ResultSet rs;
	int row_no=0;
	int rollno=1;
	String dmlOp="";

	private void createComp()
	{
		gridObj = new GridLayout(8,2,10,10);
		frmObj = new JFrame("E-Students Record Keeping System");

		lblRollno = new JLabel("Enter Roll No :");
		lblName = new JLabel("Enter Name :");
		lblAdd = new JLabel("Enter Address :");
		lblGen = new JLabel("Select Gender :");
		lblQual = new JLabel("Select Qualification :");
		lblHob = new JLabel("Select Hobbies :");
		lblSearch = new JLabel("Search Students via");

		txtRollno = new JTextField(10);
		txtName = new JTextField(10);
		txtAdd = new JTextField(10);
		txtS_Rollno = new JTextField(10);
		txtS_Name = new JTextField(10);
		
		bg = new ButtonGroup();
		btnM = new JRadioButton("MALE");
		btnM.setActionCommand("MALE");
		btnFe = new JRadioButton("FEMALE");
		btnFe.setActionCommand("FEMALE");		
	
		cbx = new JComboBox(st);	
		cbxNameSearch = new JComboBox(nameString);
		cbxNameSearch.setEditable(true);
		txtS_Name=(JTextField)cbxNameSearch.getEditor().getEditorComponent();
	
		cb1 = new JCheckBox("Chess");	
		cb2 = new JCheckBox("Cricket");
		cb3 = new JCheckBox("Football");
		cb4 = new JCheckBox("Tennis");
		
		btnF = new JButton("First");
		btnN = new JButton("Next");
		btnP = new JButton("Previous");
		btnL = new JButton("Last");
		btnA = new JButton("Add");
		btnU = new JButton("Update");
		btnD = new JButton("Delete");
		btnS = new JButton("Submit");
		btnSave = new JButton("Save");
		btnCancel = new JButton("Cancel");
		btnSearch = new JButton("Search");
	
		btnS_rollno = new JButton("Rollno");
		btnS_name = new JButton("Name");
		btnGO_R = new JButton("=>");
		btnGO_N = new JButton("=>");

		pnlGen = new JPanel();
		pnlbtn1 = new JPanel();
		pnlbtn2 = new JPanel();
		pnlHob = new JPanel();
		pnlErr = new JLabel("Error :");
		errDet = new JLabel("");
	}
	private void addComp()
	{
		frmObj.setLayout(gridObj);

		frmObj.add(lblRollno);
		frmObj.add(txtRollno);
		frmObj.add(lblName);
		frmObj.add(txtName);
		frmObj.add(lblAdd);
		frmObj.add(txtAdd);

		frmObj.add(lblGen);
		frmObj.add(pnlGen);
		bg.add(btnM);
		bg.add(btnFe);
		pnlGen.add(btnM);
		pnlGen.add(btnFe);

		frmObj.add(lblQual);		
		frmObj.add(cbx);

		frmObj.add(lblHob);
		frmObj.add(pnlHob);
		pnlHob.add(cb1);
		pnlHob.add(cb2);
		pnlHob.add(cb3);
		pnlHob.add(cb4);

		frmObj.add(pnlbtn1);
		frmObj.add(pnlbtn2);
		pnlbtn1.add(btnF);
		pnlbtn1.add(btnL);
		pnlbtn1.add(btnP);
		pnlbtn1.add(btnN);
		
		pnlbtn2.add(btnA);
		//pnlbtn2.add(btnS);
		pnlbtn2.add(btnU);
		pnlbtn2.add(btnD);
		pnlbtn2.add(btnSearch);
		pnlbtn2.add(btnSave);
		pnlbtn2.add(btnCancel);

		//frmObj.add(pnlErr);		
		//frmObj.add(errDet);

		frmObj.setSize(1200,700);
		frmObj.setVisible(true);
		btnP.setEnabled(false);
		btnSave.setEnabled(false);
		btnCancel.setEnabled(false);
		btnU.setEnabled(false);
		btnD.setEnabled(false);
		txtRollno.setEditable(false); 
		db_connect();
	}
	private void addListeners()
	{
		btnF.addActionListener(this);
		btnN.addActionListener(this);
		btnP.addActionListener(this);
		btnL.addActionListener(this);
		btnA.addActionListener(this);
		btnU.addActionListener(this);
		btnD.addActionListener(this);
		btnSearch.addActionListener(this);
		btnS_rollno.addActionListener(this);
		btnS_name.addActionListener(this);
		btnGO_R.addActionListener(this);
		btnGO_N.addActionListener(this);
		btnS.addActionListener(this);
		btnSave.addActionListener(this);
		btnCancel.addActionListener(this);
		btnM.addActionListener(this);
		btnFe.addActionListener(this);
		txtRollno.addKeyListener(this);
		txtS_Rollno.addKeyListener(this);
		txtS_Name.addKeyListener(this);
	}
	public void actionPerformed(ActionEvent ae) 
	{
		if(ae.getSource().equals(btnSearch))
		{
			searchDb(ae);
		}
		else if(ae.getSource().equals(btnS_rollno))
		{
			btnS_rollno.setEnabled(false);
			btnS_name.setEnabled(true);
			//txtS_Name.setEditable(false);
			cbxNameSearch.setEditable(false);
			cbxNameSearch.setEnabled(false);
			btnGO_N.setEnabled(false);
			txtS_Rollno.setEditable(true);
			btnGO_R.setEnabled(true);
		}
		else if(ae.getSource().equals(btnS_name))
		{
			btnS_rollno.setEnabled(true);
			btnS_name.setEnabled(false);
			txtS_Rollno.setEditable(false);
			btnGO_R.setEnabled(false);
			//txtS_Name.setEditable(true);
			cbxNameSearch.setEditable(true);
			cbxNameSearch.setEnabled(true);cbxNameSearch.showPopup();
			btnGO_N.setEnabled(true);
			
			nameList = new ArrayList<String>();nameList.add("");
			rollnoList = new ArrayList<Integer>();rollnoList.add(new Integer(0));
		try{	
				rs.beforeFirst();
				while(rs.next())
				{
						nameList.add(rs.getString(2));
						rollnoList.add(rs.getInt(1));
				}
			}catch(Exception e){System.out.println(e);}
			String[] temparr=Arrays.asList(nameList.toArray()).toArray(new String[nameList.toArray().length]);			
			DefaultComboBoxModel model = new DefaultComboBoxModel(temparr);
			cbxNameSearch.setModel(model);
			cbxNameSearch.showPopup();
		}
		if(ae.getSource().equals(btnSave))
		{
			String choice="";
			err="";
			try{
			choice = bg.getSelection().getActionCommand();
			}catch(Exception e){}
			/*if(txtRollno.getText().equals(""))
			{
				err = err+"Roll no required!!  ";
			}*/
			if(txtName.getText().equals(""))
			{
				err = err+"Name required!!  ";
			}
			if(txtAdd.getText().equals(""))
			{
				err = err+"Address required!!  ";
			}
			if(choice.equals("MALE") == false && choice.equals("FEMALE") == false)
			{
				err = err+"Select Gender!!  ";		
			}
			if(cb1.isSelected() == false && cb2.isSelected() == false && cb3.isSelected() == false && cb4.isSelected() == false)
			{
				err = err+"Select atleast 1 Hobby!!  ";
			}
			//errDet.setText(err);
			if(err.equals("")){}
			else
			{
				Object[] options = {"OK"};
				int n = JOptionPane.showOptionDialog(frmObj,err,"ERROR",JOptionPane.PLAIN_MESSAGE,JOptionPane.ERROR_MESSAGE,null,options,options[0]);
				return;
			}			
		}

		if(ae.getSource().equals(btnF) || ae.getSource().equals(btnN) || ae.getSource().equals(btnP) || ae.getSource().equals(btnL))
		{
			db_retriveAll(ae);
		}

		else if(ae.getSource().equals(btnA) || ae.getSource().equals(btnU) || ae.getSource().equals(btnD))
		{
			doDml(ae);
		}

		else if(ae.getSource().equals(btnSave))
		{
			saveDb(ae);
		}
		else if(ae.getSource().equals(btnCancel))
		{
			cancelDb(ae);
		}
		else if(ae.getSource().equals(btnGO_R))
		{
			searchRollno();
		}
		else if(ae.getSource().equals(btnGO_N))
		{
			searchName();
		}
	}
	public void keyPressed(KeyEvent ke)
	{
		err="";
		if(ke.getSource().equals(txtS_Rollno))
		{
		if(ke.getKeyCode() == KeyEvent.VK_BACK_SPACE || ke.getKeyCode() >= KeyEvent.VK_0 && ke.getKeyCode() <= KeyEvent.VK_9 || ke.getKeyCode() >= KeyEvent.VK_NUMPAD0 && ke.getKeyCode() <= KeyEvent.VK_NUMPAD9)
		{		
			err="";
		}
		else
		{		
			err= err+"Only numbers allowed in Roll no field!! ";
		}
		//errDet.setText(err);
		}
	}
	public void keyTyped(KeyEvent ke)
	{
		if(ke.getSource().equals(txtS_Rollno))
		{
		if(ke.getKeyChar ( ) >= 'a' && ke.getKeyChar ( ) <= 'z' || ke.getKeyChar ( ) >= 'A' && ke.getKeyChar ( ) <= 'Z')
		{		ke.consume();
		}
		}
		
		
	}
	
	public void keyReleased(KeyEvent ke)
	{
		if(ke.getSource().equals(txtS_Name))
		{
		try{
			String tempName=txtS_Name.getText();
			nameList = new ArrayList<String>();nameList.add(tempName);
			rollnoList = new ArrayList<Integer>();rollnoList.add(new Integer(0));
			
			rs.beforeFirst();
			while(rs.next())
			{
				if(rs.getString(2).matches("(.*)"+tempName+"(.*)"))
				{
					nameList.add(rs.getString(2));
					rollnoList.add(rs.getInt(1));
				}
			}
			
			String[] temparr=Arrays.asList(nameList.toArray()).toArray(new String[nameList.toArray().length]);			
			DefaultComboBoxModel model = new DefaultComboBoxModel(temparr);
			cbxNameSearch.setModel(model);
			cbxNameSearch.showPopup();
		}catch(Exception e){System.out.println(e);}
		}
	}			

	public void db_connect()
	{
		Url="jdbc:sqlserver://ACE_4;databaseName=sid_db;user=sa;password=Apt@1234";//sql server
		try
		{
		Class.forName("oracle.jdbc.driver.OracleDriver");//oracle 10g
		con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","jarvis");//oracle 10g
		//con=DriverManager.getConnection(Url);//sql server
		stmt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		System.out.println("connected");
		Query="select * from student_details";
		rs=stmt.executeQuery(Query);	
		}
		catch(Exception e){System.out.println(e);}
	}
	public void db_retriveAll(ActionEvent ae)
	{
		try
		{
			if(ae.getSource().equals(btnF))
			{
				if(rs.first()){
				btnP.setEnabled(false);
				btnN.setEnabled(true);
				btnU.setEnabled(true);
				btnD.setEnabled(true);
				}
			}
			else if(ae.getSource().equals(btnN) && rs.next())
			{
				if(rs.getRow()==1){btnP.setEnabled(false);}
				else{btnP.setEnabled(true);}
				if(rs.next())
				{}
				else
				{
					btnN.setEnabled(false);
				}
				rs.previous();
				btnU.setEnabled(true);
				btnD.setEnabled(true);
			}
			else if(ae.getSource().equals(btnP) && rs.previous())
			{
				btnN.setEnabled(true);
				if(rs.previous())
				{}
				else
				{
					btnP.setEnabled(false);
				}
				rs.next();
				btnU.setEnabled(true);
				btnD.setEnabled(true);
			}
			else if(ae.getSource().equals(btnL))
			{
				if(rs.last()){
				row_no=rs.getRow();
				btnN.setEnabled(false);
				btnP.setEnabled(true);
				}
				btnU.setEnabled(true);
				btnD.setEnabled(true);
			}
			else{return;}
			txtRollno.setText(new Integer(rs.getInt(1)).toString());
			txtName.setText(rs.getString(2));
			txtAdd.setText(rs.getString(3));
			bg.clearSelection();
			if(rs.getString(4).equalsIgnoreCase("M"))
			{
				btnM.setSelected(true);
			}
			else
			{
				btnFe.setSelected(true);
			}
			cbx.setSelectedItem(rs.getString(5));
			String[] hob=rs.getString(6).split(",");
			cb1.setSelected(false);cb2.setSelected(false);cb3.setSelected(false);cb4.setSelected(false);
			for(String iter:hob)
			{
				if(iter.equalsIgnoreCase("Chess")) cb1.setSelected(true);
				if(iter.equalsIgnoreCase("Cricket")) cb2.setSelected(true);
				if(iter.equalsIgnoreCase("Tennis")) cb4.setSelected(true);
				if(iter.equalsIgnoreCase("Football")) cb3.setSelected(true);
			}
		//con.close();
		}
		catch(Exception e){System.out.println(e);}	
	}

	public void doDml(ActionEvent ae)
	{
		btnSave.setEnabled(true);
		btnCancel.setEnabled(true);
		btnF.setEnabled(false);
		btnL.setEnabled(false);
		btnP.setEnabled(false);
		btnN.setEnabled(false);
		btnA.setEnabled(false);
		btnU.setEnabled(false);
		btnD.setEnabled(false);
		btnSearch.setEnabled(false);
		if(ae.getSource().equals(btnA))
		{
			dmlOp="add";
			try
			{
			txtRollno.setText("");
			txtName.setText("");
			txtAdd.setText("");
			bg.clearSelection();
			cbx.setSelectedItem("Diploma");
			cb1.setSelected(false);cb2.setSelected(false);cb3.setSelected(false);cb4.setSelected(false);
			}
			catch(Exception e){System.out.println(e);}
			try{
			rs.last();
			row_no=rs.getRow();
			}catch(Exception e){System.out.println(e);}
		}
		else if(ae.getSource().equals(btnU))
		{
			dmlOp="update";
			try{
			rollno=rs.getInt(1);
			row_no=rs.getRow();
			}catch(Exception e){System.out.println(e);}
		}
		else if(ae.getSource().equals(btnD))
		{
			dmlOp="delete";
			try{
			row_no=rs.getRow();
			rollno=rs.getInt(1);
			}catch(Exception e){System.out.println(e);}
		}			
	}
	
	public void saveDb(ActionEvent ae)
	{
	try{
		if(dmlOp.equalsIgnoreCase("add"))
		{
			rs.last();
			rollno=rs.getInt(1)+1;
			txtRollno.setText(new Integer(rollno).toString());
			
			Query="insert into student_details values(?,?,?,?,?,?)";
			ps=con.prepareStatement(Query);
			ps.setInt(1,rollno);
			ps.setString(2,txtName.getText());
			ps.setString(3,txtAdd.getText());
			if(btnM.isSelected())
			{
				ps.setString(4,"M");
			}
			else if(btnFe.isSelected())
			{
				ps.setString(4,"F");
			}
			ps.setString(5,(String)cbx.getSelectedItem());		
			String nHob="";
			if(cb1.isSelected())
			{
				nHob+="Chess,";
			}
			if(cb2.isSelected())
			{
				nHob+="Cricket,";	
			}
			if(cb3.isSelected())
			{
				nHob+="Football,";
			}
			if(cb4.isSelected())
			{
				nHob+="Tennis,";
			}
			ps.setString(6,nHob);
			int i=ps.executeUpdate();
		}
		else if(dmlOp.equalsIgnoreCase("update"))
		{
			Query="update student_details set st_name=?,st_address=?,st_gender=?,st_quali=?,st_hobbies=? where rollno="+rollno;
			ps=con.prepareStatement(Query);
			ps.setString(1,txtName.getText());
			ps.setString(2,txtAdd.getText());
			if(btnM.isSelected())
			{
				ps.setString(3,"M");
			}
			else if(btnFe.isSelected())
			{
				ps.setString(3,"F");
			}
			ps.setString(4,(String)cbx.getSelectedItem());		
			String nHob="";
			if(cb1.isSelected())
			{
				nHob+="Chess,";
			}
			if(cb2.isSelected())
			{
				nHob+="Cricket,";	
			}
			if(cb3.isSelected())
			{
				nHob+="Football,";
			}
			if(cb4.isSelected())
			{
				nHob+="Tennis,";
			}
			ps.setString(5,nHob);
			int i=ps.executeUpdate();	
		}
		else if(dmlOp.equalsIgnoreCase("delete"))
		{
			Query="delete from student_details where rollno="+rollno;
			int i=stmt.executeUpdate(Query);
		}
		Object[] options = {"OK"};
    		int n = JOptionPane.showOptionDialog(frmObj,dmlOp+" operation 	completed successfully","",JOptionPane.PLAIN_MESSAGE,JOptionPane.INFORMATION_MESSAGE,null,options,options[0]);
		Query="select * from student_details";
		rs=stmt.executeQuery(Query);
	}
	catch(Exception e){System.out.println(e);}	
	try{

	if(dmlOp.equals("add"))
	{
		rs.absolute(row_no+1);
	}
	else if(dmlOp.equals("update"))
	{
		rs.absolute(row_no);
	}
	else if(dmlOp.equals("delete"))
	{
		rs.absolute(row_no-1);
	}
	txtRollno.setText(new Integer(rs.getInt(1)).toString());
	txtName.setText(rs.getString(2));
	txtAdd.setText(rs.getString(3));
	bg.clearSelection();
	if(rs.getString(4).equalsIgnoreCase("M"))
	{
		btnM.setSelected(true);
	}
	else
	{
		btnFe.setSelected(true);
	}
	cbx.setSelectedItem(rs.getString(5));
	String[] hob=rs.getString(6).split(",");
	cb1.setSelected(false);cb2.setSelected(false);cb3.setSelected(false);cb4.setSelected(false);
	for(String iter:hob)
	{
		if(iter.equalsIgnoreCase("Chess")) cb1.setSelected(true);
		if(iter.equalsIgnoreCase("Cricket")) cb2.setSelected(true);
		if(iter.equalsIgnoreCase("Tennis")) cb4.setSelected(true);
		if(iter.equalsIgnoreCase("Football")) cb3.setSelected(true);
	}

	}
	catch(Exception e){System.out.println(e);}
		btnSave.setEnabled(false);
		btnCancel.setEnabled(false);
		btnF.setEnabled(true);
		btnL.setEnabled(true);
		btnP.setEnabled(true);
		btnN.setEnabled(true);
		try{
			if(rs.getRow()==1){btnP.setEnabled(false);}
			if(rs.next())
			{}
			else{btnN.setEnabled(false);}
			rs.previous();
		}catch(Exception e){System.out.println(e);}
		btnA.setEnabled(true);
		btnU.setEnabled(true);
		btnD.setEnabled(true);
		btnSearch.setEnabled(true);
	}
	
	public void cancelDb(ActionEvent ae)
	{
		if(dmlOp.equals("add") || dmlOp.equals("update") || dmlOp.equals("delete"))
		{
			try
			{
				if(rs.absolute(row_no))
				{
					txtRollno.setText(new Integer(rs.getInt(1)).toString());
					txtName.setText(rs.getString(2));
					txtAdd.setText(rs.getString(3));
					bg.clearSelection();
					if(rs.getString(4).equalsIgnoreCase("M"))
					{
						btnM.setSelected(true);
					}
					else
					{
						btnFe.setSelected(true);
					}
					cbx.setSelectedItem(rs.getString(5));
					String[] hob=rs.getString(6).split(",");
					cb1.setSelected(false);cb2.setSelected(false);cb3.setSelected(false);cb4.setSelected(false);
					for(String iter:hob)
					{
						if(iter.equalsIgnoreCase("Chess")) cb1.setSelected(true);
						if(iter.equalsIgnoreCase("Cricket")) cb2.setSelected(true);
					if(iter.equalsIgnoreCase("Tennis")) cb4.setSelected(true);
					if(iter.equalsIgnoreCase("Football")) cb3.setSelected(true);
					}	
				}
			}
			catch(Exception e){System.out.println(e);}
		}
		/*txtRollno.setText("");
		txtName.setText("");
		txtAdd.setText("");
		bg.clearSelection();
		cbx.setSelectedItem("Diploma");
		cb1.setSelected(false);cb2.setSelected(false);cb3.setSelected(false);cb4.setSelected(false);*/

		btnSave.setEnabled(false);
		btnCancel.setEnabled(false);
		btnF.setEnabled(true);
		btnL.setEnabled(true);
		btnP.setEnabled(true);
		btnN.setEnabled(true);
		try{
			if(rs.getRow()==1){btnP.setEnabled(false);}
			if(rs.next())
			{}
			else{btnN.setEnabled(false);}
			rs.previous();
		}catch(Exception e){System.out.println(e);}		
		btnA.setEnabled(true);
		btnU.setEnabled(true);
		btnD.setEnabled(true);	
		btnSearch.setEnabled(true);
	}
	public void searchDb(ActionEvent ae)
	{
		sd=new JDialog(frmObj,"Search");
		sd.setSize(400,200);
		sd.setLayout(new GridLayout(3,1,20,20));
		sd.add(lblSearch);
		sd.add(btnS_rollno);btnS_rollno.setEnabled(false);
		sd.add(btnS_name);btnS_name.setEnabled(true);
		sd.add(lblRollno);
		sd.add(txtS_Rollno);
		sd.add(btnGO_R);
		sd.add(lblName);
		//sd.add(txtS_Name);
		sd.add(cbxNameSearch);
		sd.add(btnGO_N);
		
		txtS_Rollno.setEditable(true);
		btnGO_R.setEnabled(true);
		//txtS_Name.setEditable(false);
		cbxNameSearch.setEditable(false);
		cbxNameSearch.setEnabled(false);
		btnGO_N.setEnabled(false);
		
		sd.setLocation(450, 150);
		sd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		sd.setVisible(true);
		
	}
	public void searchRollno()
	{	
	try{
		int s_roll=Integer.parseInt(txtS_Rollno.getText());
		rs.last();
		if(s_roll > rs.getInt(1) || s_roll <= 0)
		{
			Object[] options = {"OK"};
    		int n = JOptionPane.showOptionDialog(frmObj,"No Records found for Rollno : "+s_roll,"",JOptionPane.PLAIN_MESSAGE,JOptionPane.INFORMATION_MESSAGE,null,options,options[0]);
			return;
		}
		else
		{
			rs.absolute(s_roll);		
			txtRollno.setText(new Integer(rs.getInt(1)).toString());
			txtName.setText(rs.getString(2));
			txtAdd.setText(rs.getString(3));
			bg.clearSelection();
			if(rs.getString(4).equalsIgnoreCase("M"))
			{
				btnM.setSelected(true);
			}
			else
			{
				btnFe.setSelected(true);
			}
			cbx.setSelectedItem(rs.getString(5));
			String[] hob=rs.getString(6).split(",");
			cb1.setSelected(false);cb2.setSelected(false);cb3.setSelected(false);cb4.setSelected(false);
			for(String iter:hob)
			{
				if(iter.equalsIgnoreCase("Chess")) cb1.setSelected(true);
				if(iter.equalsIgnoreCase("Cricket")) cb2.setSelected(true);
				if(iter.equalsIgnoreCase("Tennis")) cb4.setSelected(true);
				if(iter.equalsIgnoreCase("Football")) cb3.setSelected(true);
			}
			
			sd.dispose();
		}
	}
	catch(Exception e){System.out.println(e);}
	}
	public void searchName()
	{
		if(cbxNameSearch.getSelectedIndex() == 0)
		{
			cbxNameSearch.setSelectedIndex(1);
		}
		String searchName=(String)cbxNameSearch.getSelectedItem();
		int i=nameList.indexOf(searchName);
		int ret_rollno=(Integer)rollnoList.get(i);
		//System.out.println(ret_rollno);
		try{
			rs.absolute(ret_rollno);		
			txtRollno.setText(new Integer(rs.getInt(1)).toString());
			txtName.setText(rs.getString(2));
			txtAdd.setText(rs.getString(3));
			bg.clearSelection();
			if(rs.getString(4).equalsIgnoreCase("M"))
			{
				btnM.setSelected(true);
			}
			else
			{
				btnFe.setSelected(true);
			}
			cbx.setSelectedItem(rs.getString(5));
			String[] hob=rs.getString(6).split(",");
			cb1.setSelected(false);cb2.setSelected(false);cb3.setSelected(false);cb4.setSelected(false);
			for(String iter:hob)
			{
				if(iter.equalsIgnoreCase("Chess")) cb1.setSelected(true);
				if(iter.equalsIgnoreCase("Cricket")) cb2.setSelected(true);
				if(iter.equalsIgnoreCase("Tennis")) cb4.setSelected(true);
				if(iter.equalsIgnoreCase("Football")) cb3.setSelected(true);
			}
			
			sd.dispose();
			
		}catch(Exception e){System.out.println(e);}
	}
	public static void main(String args[])
	{
		Login lg = new Login();
		lg.createComp();
		lg.addComp();
		lg.addListeners();
	}
}
/*
use 

create table student_details(
rollno int,
st_name varchar(30),
st_address varchar(50),
st_gender varchar(1),
st_quali varchar(30),
st_hobbies varchar(50)
);

select * from student_details;

insert into student_details values(1,'sid','mira road','M','Under-Graduate','Chess,Tennis');

insert into student_details values(2,'shreya','khar road','F','Diploma','Chess,Cicket');
insert into student_details values(3,'raj','bhiwandi road','M','Post-Graduate','Chess,Football');
insert into student_details values(4,'nick','vasai road','M','Under-Graduate','Cricket,Tennis');
insert into student_details values(5,'seema','vile parle','F','Under-Graduate','Cricket,Football');

*/