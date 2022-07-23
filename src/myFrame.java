
package Group_Cw;
import java.awt.Color;
import static java.awt.Color.BLACK;
import static java.awt.Color.CYAN;
import static java.awt.Color.RED;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author piyash, aashish, aashupa
 */
public class myFrame extends javax.swing.JFrame {
    /**
     * Creates new form myFrame
     */
    public myFrame() {
        initComponents();
        
        // Start an application at the maximized screen
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        //To remove broder around the scroller
        tableScrollPane.setBorder(null);
        tableScrollPane.setBackground(Color.decode("#CBAF87"));
        
        // Table HEADER UI Part
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(new Font("Arial",Font.BOLD,16));
        tableHeader.setBackground(Color.decode("#CBAF87"));
        tableHeader.setForeground(Color.decode("#30475E"));
        UIManager.getDefaults().put("TableHeader.cellBorder",BorderFactory.createEmptyBorder(8,10,8,10));
        
        // Table HEADER UI Part
        JTableHeader displayHeader = tableDisplay.getTableHeader();
        displayHeader.setFont(new Font("Arial",Font.BOLD,16));
        displayHeader.setBackground(Color.decode("#CBAF87"));
        displayHeader.setForeground(Color.decode("#30475E"));
        UIManager.getDefaults().put("TableHeader.cellBorder",BorderFactory.createEmptyBorder(8,10,8,10));
        
    } 
    // Validaton Boolean for checking conditions
    boolean nameValid = false,feeValid = false, contactValid = false,typeValid = false;
    //Function for Unique Name and Contact validation in data table
    private boolean isUniqueName(String name,String contact) {
        for (int row = 0; row < table.getRowCount(); row++) { 
           
            String hospitalName = (String) table.getValueAt(row, 0);
            String contactNumber = (String) table.getValueAt(row,1);
            if ((hospitalName != null) && (hospitalName.length() > 0) && (hospitalName.toUpperCase().equals(name.trim().toUpperCase()))){
               return false; 
            }
            if ((contactNumber != null) && (contactNumber.length() > 0) && (contactNumber.toUpperCase().equals(contact.trim().toUpperCase()))){
               return false;   
            }
        }
        return true;
    } 
    
    //Funtion for number format for opd Fee 
    private boolean opdFeeBoolean(String number) {
        return number.matches("[0-9]+([.][0-9]{1,2})?");
    }
    //Function for number Format For Contact Number
    private boolean contactNumberBoolean(String contacts){
        return contacts.matches("[0-9]+([-][0-9]{1,9})?") ;  
    }  
    //Function for Checkbox of insert form Validation
    private void setCheckBoxValidation(){
        if(eye_ckb.isSelected() || cancer_ckb.isSelected() || heart_ckb.isSelected()
                || skin_ckb.isSelected()|| women_ckb.isSelected() || general_ckb.isSelected() || orthopaedic_ckb.isSelected()){
            typeValid = true;
                    type_lb.setForeground(BLACK);
                    type_lb.setText("Type");
        }else{
            typeValid = false;
                    type_lb.setForeground(RED);
                    type_lb.setText("Type (Required)");
        }
        setInsertButtonVisible();
    }
    //Function for insert button visible when all condition true
    private void setInsertButtonVisible(){
        if (nameValid && feeValid && contactValid && typeValid) {
                    insertForm_btn.setVisible(true);
                } else {
                   insertForm_btn.setVisible(false);
                }
    }
    //Setter method to set location combobox for Kathmandu
    private void setkathmaduCombobox(){
        location_cb.removeAllItems();
        location_updateForm_cb.removeAllItems();
        String [] place = {"Ratna Park","Gaushala","Minbhawan","Harisiddhi",
                "Jagatjung Marga","Bansbari Rd","Baneshower","Koteshower","Panipokhari Marg","Gokarneshwor"};
            for (String string : place) {
             location_cb.addItem(string);
             location_updateForm_cb.addItem(string);
        }     
    } 
     //Setter method to set location combobox for Bhaktapur
    private void setBhaktapurCombobox(){
        location_updateForm_cb.removeAllItems();
        location_cb.removeAllItems();
        String [] place = {"Balkot","Jagati","Surybinayak","Madhyapur Thimi"};
            for (String string : place) {
             location_cb.addItem(string);
             location_updateForm_cb.addItem(string);
            }    
    }
     //Setter method to set location combobox for lalitpur
    private void setLalitpurCombobox(){
        location_cb.removeAllItems();
        location_updateForm_cb.removeAllItems();
        String [] place = {"Patan","Lagankhel","Jaulakhel","Ekantakuna"};
           for (String string : place) {
            location_updateForm_cb.addItem(string);
            location_cb.addItem(string);
            }
    }
    //Setting location combobox by checking condions
    private void setCombobox(){
        if(ktm_updateForm_rb.isSelected() || ktm_insertForm_rb.isSelected() ){
            setkathmaduCombobox();
        }
        if(bhktp_updateForm_rb.isSelected()||bhktp_insertForm_rb.isSelected()){
           setBhaktapurCombobox();
        }
        if(lalit_updateForm_rb.isSelected() || lalit_insertForm_rb.isSelected()){
            setLalitpurCombobox();
        }
    } 
    // Function to insert data intable when data is pass into it parameters
    private void insertTableData(Object [] tabledata){

        int rowCount = table.getRowCount();
        int columnCount = table.getColumnCount();
        int nextRow = 0;
        boolean emptyRowFlag = false;
        String s;
        do {
            s = (String)table.getValueAt(nextRow,0);
            if (s != null && s.length() != 0) nextRow++;
            else emptyRowFlag = true;
        } while (nextRow < rowCount && !emptyRowFlag);
        if(nextRow< rowCount){
            if(emptyRowFlag){
                for (int i=0; i<columnCount; i++){
                    table.setValueAt(tabledata[i],nextRow,i);
                }
            }
        }else{
            // inserting new value and row
          DefaultTableModel models = (DefaultTableModel)table.getModel();
          models.addRow(tabledata);  
        }
    }
    // Sort Function 
    private void sort(int column){
        //sorting in ascending order
        ArrayList<ArrayList<Object>> hospitalData = getData();
        int rowCount = table.getRowCount();
        Object columnData[] = new Object[rowCount];
        //adding data to array
        for (int i = 0; i<rowCount;i++){
            String data = (String) table.getValueAt(i,column);
            columnData[i] = data;
        }
        
        //finding minimum position
        for (int i = 0; i<columnData.length - 1;i++){
             int minPos = i;
             for (int j = i + 1; j < columnData.length; j++) {
                // for the string data we are using compareTo 
                if (columnData[j].toString().compareTo(columnData[minPos].toString())<0) {
                    minPos = j;
                }
             }

            //to swap values in array
            Object temp = columnData[minPos];
            columnData[minPos] = columnData[i];
            columnData[i] = temp;
            
            //swapping in table
            //name
            String name = (String) table.getValueAt(i,0);
            table.setValueAt(table.getValueAt(minPos,0),i,0);
            table.setValueAt(name, minPos, 0);
            //phone
            String phone = (String) table.getValueAt(i,1);
            table.setValueAt(table.getValueAt(minPos, 1), i, 1);
            table.setValueAt(phone, minPos, 1);
            //fee
            String fee = (String) table.getValueAt(i, 2);
            table.setValueAt(table.getValueAt(minPos, 2), i, 2);
            table.setValueAt(fee, minPos, 2);
            //category
            String category = (String) table.getValueAt(i, 3);
            table.setValueAt(table.getValueAt(minPos, 3), i, 3);
            table.setValueAt(category, minPos, 3);
            //location
            String location = (String) table.getValueAt(i, 4);
            table.setValueAt(table.getValueAt(minPos, 4), i, 4);
            table.setValueAt(location, minPos, 4);
           //type
            String type = (String) table.getValueAt(i, 5);
            table.setValueAt(table.getValueAt(minPos, 5), i, 5);
            table.setValueAt(type, minPos, 5);
            
            //swap in arraylist
            Collections.swap(hospitalData,minPos,i);
        }
    }
    
    public int binSearch(Object arr[], int low, int high, int price){
        if (low > high){
            return -1;
        }
        int mid = (low+high)/2;
        if((Integer)arr[mid] == price){
            //JOptionPane.showMessageDialog(rootPane, );
            return mid;
        }else if((Integer)arr[mid] > price){
            return binSearch(arr,low,mid-1,price);
        }else{
            return binSearch(arr,mid+1,high,price);
        }       
    }
    
    // Function to return opdFee
    public Object[] getPrice(){
         //getting values of price/opdFee
         int rowCount = table.getRowCount();
        Object opdFee[] = new Object[rowCount];
        for (int i = 0; i<rowCount;i++){
            String price = (String) table.getValueAt(i,2);
            opdFee[i] = Integer.parseInt(price);
        }
         return opdFee;
     }
    //Function to return hospital data
    public ArrayList<ArrayList<Object>> getData(){
         //getting all data in the table
         int rowCount = table.getRowCount();
        int columnCount = table.getColumnCount();
        ArrayList <ArrayList <Object>> hospitalData = new ArrayList<>(rowCount);
        for(int i=0; i < rowCount; i++) {
            hospitalData.add(new ArrayList());
        }

         for (int i = 0; i<rowCount;i++){
             for (int j = 0; j<columnCount;j++){
                hospitalData.get(i).add(table.getValueAt(i,j));
             }
         }
         
         return hospitalData;
     }
    // Function to Reset Form to default
    private void resetForm(){
        // combobox of update form 
        eye_updateForm_ckb.setSelected(false); 
        cancer_updateForm_ckb.setSelected(false);
        heart_updateForm_ckb.setSelected(false);
        skin_updateForm_ckb.setSelected(false); 
        orthopaedic_updateForm_ckb.setSelected(false);
        women_updateForm_ckb.setSelected(false);
        general_updateForm_ckb.setSelected(false);
        // combo box of insert form
        eye_ckb.setSelected(false); 
        cancer_ckb.setSelected(false);
        heart_ckb.setSelected(false);
        skin_ckb.setSelected(false); 
        orthopaedic_ckb.setSelected(false);
        women_ckb.setSelected(false);
        general_ckb.setSelected(false);
        // text field of insert form
        hospitalName_txt.setText(null);
        contactNumber_txt.setText(null);
        opdFee_txt.setText(null);
        // Boolean Validation 
        nameValid = false;
        feeValid = false;
        contactValid = false;
        typeValid = false;
        //label of insert form
        hospitalName_lb.setText("Hospital Name");
        hospitalName_lb.setForeground(BLACK);
        contactNumber_lb.setText("Contact Number");
        contactNumber_lb.setForeground(BLACK);
        opdFee_lb.setText("OPD Fee");
        opdFee_lb.setForeground(BLACK);
        type_lb.setText("Type");
        type_lb.setForeground(BLACK);
        // label of update form
        hospitalName_updateForm_lb.setText("Hospital Name");
        hospitalName_updateForm_lb.setForeground(BLACK);
        contactNumber_updateForm_lb.setText("Contact Number");
        contactNumber_updateForm_lb.setForeground(BLACK);
        opdFee_updateForm_lb.setText("OPD Fee");
        opdFee_updateForm_lb.setForeground(BLACK);   
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        categoryDisplay = new javax.swing.JFrame();
        categoryPanel = new javax.swing.JPanel();
        tableScrollPane1 = new javax.swing.JScrollPane();
        tableDisplay = new javax.swing.JTable();
        displayCancel_btn = new javax.swing.JButton();
        insertForm = new javax.swing.JDialog();
        insertForm_panel = new javax.swing.JPanel();
        hospitalName_lb = new javax.swing.JLabel();
        cancelForm_btn = new javax.swing.JButton();
        contactNumber_lb = new javax.swing.JLabel();
        location_lb = new javax.swing.JLabel();
        categoryInsert_cb = new javax.swing.JComboBox<>();
        contactNumber_txt = new javax.swing.JTextField();
        opdFee_txt = new javax.swing.JTextField();
        location_cb = new javax.swing.JComboBox<>();
        eye_ckb = new javax.swing.JCheckBox();
        cancer_ckb = new javax.swing.JCheckBox();
        heart_ckb = new javax.swing.JCheckBox();
        skin_ckb = new javax.swing.JCheckBox();
        orthopaedic_ckb = new javax.swing.JCheckBox();
        women_ckb = new javax.swing.JCheckBox();
        general_ckb = new javax.swing.JCheckBox();
        opdFee_lb = new javax.swing.JLabel();
        categoryInsert_lb = new javax.swing.JLabel();
        type_lb = new javax.swing.JLabel();
        insertForm_btn = new javax.swing.JButton();
        lalit_insertForm_rb = new javax.swing.JRadioButton();
        bhktp_insertForm_rb = new javax.swing.JRadioButton();
        ktm_insertForm_rb = new javax.swing.JRadioButton();
        hospitalName_txt = new javax.swing.JTextField();
        title_insertForm_lb = new javax.swing.JLabel();
        deleteForm = new javax.swing.JDialog();
        deleteForm_panel = new javax.swing.JPanel();
        contactNumber_deleteForm_lb = new javax.swing.JLabel();
        opdFee_deleteForm_lb = new javax.swing.JLabel();
        hospitalName_deleteForm3 = new javax.swing.JLabel();
        category_deleteForm_lb = new javax.swing.JLabel();
        type_deleteForm_lb = new javax.swing.JLabel();
        location_deleteForm_lb = new javax.swing.JLabel();
        location_deleteForm_txt = new javax.swing.JTextField();
        hospitalName_deleteForm_txt = new javax.swing.JTextField();
        category_deleteForm_txt = new javax.swing.JTextField();
        opdFee_deleteForm_txt = new javax.swing.JTextField();
        contactNumber_deleteForm_txt = new javax.swing.JTextField();
        delete_deleteForm_btn = new javax.swing.JButton();
        cancel_deleteForm_btn1 = new javax.swing.JButton();
        type_deleteForm_txt = new javax.swing.JTextField();
        title_deleteForm_lb = new javax.swing.JLabel();
        updateForm = new javax.swing.JDialog();
        updateForm_panel = new javax.swing.JPanel();
        hospitalName_updateForm_lb = new javax.swing.JLabel();
        cancelForm_updateForm_btn = new javax.swing.JButton();
        contactNumber_updateForm_lb = new javax.swing.JLabel();
        location_updateForm_lb = new javax.swing.JLabel();
        category_updateForm_cb = new javax.swing.JComboBox<>();
        contactNumber_updateForm_txt = new javax.swing.JTextField();
        opdFee_updateForm_txt = new javax.swing.JTextField();
        eye_updateForm_ckb = new javax.swing.JCheckBox();
        cancer_updateForm_ckb = new javax.swing.JCheckBox();
        heart_updateForm_ckb = new javax.swing.JCheckBox();
        skin_updateForm_ckb = new javax.swing.JCheckBox();
        orthopaedic_updateForm_ckb = new javax.swing.JCheckBox();
        women_updateForm_ckb = new javax.swing.JCheckBox();
        general_updateForm_ckb = new javax.swing.JCheckBox();
        opdFee_updateForm_lb = new javax.swing.JLabel();
        category_updateForm_lb = new javax.swing.JLabel();
        typev_updateForm_lb = new javax.swing.JLabel();
        updateForm_btn = new javax.swing.JButton();
        hospitalName_updateForm_txt = new javax.swing.JTextField();
        ktm_updateForm_rb = new javax.swing.JRadioButton();
        bhktp_updateForm_rb = new javax.swing.JRadioButton();
        lalit_updateForm_rb = new javax.swing.JRadioButton();
        title_updateForm_lb = new javax.swing.JLabel();
        location_updateForm_cb = new javax.swing.JComboBox<>();
        location_grb = new javax.swing.ButtonGroup();
        sidePanel = new javax.swing.JPanel();
        search_txt = new javax.swing.JTextField();
        update_btn = new javax.swing.JButton();
        delete_btn = new javax.swing.JButton();
        sort_cb = new javax.swing.JComboBox<>();
        search_txt_btn = new javax.swing.JButton();
        insert_btn = new javax.swing.JButton();
        search_cb = new javax.swing.JComboBox<>();
        tableScrollPane = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        titlePanel = new javax.swing.JPanel();
        title_lb = new javax.swing.JLabel();
        footerPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        openMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        about = new javax.swing.JMenuItem();

        categoryDisplay.setTitle("Display");
        categoryDisplay.setAlwaysOnTop(true);
        categoryDisplay.setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        categoryDisplay.setUndecorated(true);
        categoryDisplay.setSize(new java.awt.Dimension(1000, 400));

        categoryPanel.setBackground(new java.awt.Color(203, 175, 135));

        //table.getColumnModel().getColumn(0).setPreferredWidth(40);
        tableDisplay.setBackground(new java.awt.Color(231, 222, 199));
        tableDisplay.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        tableDisplay.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Hospital Name", "Contact", "OPD Fee", "Category", "Location", "Type"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableDisplay.setAlignmentX(0.0F);
        tableDisplay.setAlignmentY(0.0F);
        tableDisplay.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tableDisplay.setGridColor(new java.awt.Color(51, 51, 51));
        tableDisplay.setRowHeight(45);
        tableDisplay.setSelectionBackground(new java.awt.Color(48, 71, 94));
        tableDisplay.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tableDisplay.setShowGrid(true);
        tableDisplay.getTableHeader().setResizingAllowed(false);
        tableDisplay.getTableHeader().setReorderingAllowed(false);
        tableScrollPane1.setViewportView(tableDisplay);
        if (tableDisplay.getColumnModel().getColumnCount() > 0) {
            tableDisplay.getColumnModel().getColumn(0).setResizable(false);
            tableDisplay.getColumnModel().getColumn(0).setPreferredWidth(300);
            tableDisplay.getColumnModel().getColumn(1).setResizable(false);
            tableDisplay.getColumnModel().getColumn(2).setResizable(false);
            tableDisplay.getColumnModel().getColumn(3).setResizable(false);
            tableDisplay.getColumnModel().getColumn(4).setResizable(false);
            tableDisplay.getColumnModel().getColumn(4).setPreferredWidth(200);
            tableDisplay.getColumnModel().getColumn(5).setResizable(false);
        }

        displayCancel_btn.setBackground(new java.awt.Color(231, 222, 200));
        displayCancel_btn.setForeground(new java.awt.Color(0, 0, 0));
        displayCancel_btn.setText("Close");
        displayCancel_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                displayCancel_btnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout categoryPanelLayout = new javax.swing.GroupLayout(categoryPanel);
        categoryPanel.setLayout(categoryPanelLayout);
        categoryPanelLayout.setHorizontalGroup(
            categoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(categoryPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tableScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 976, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, categoryPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(displayCancel_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
        );
        categoryPanelLayout.setVerticalGroup(
            categoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(categoryPanelLayout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addComponent(tableScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(displayCancel_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout categoryDisplayLayout = new javax.swing.GroupLayout(categoryDisplay.getContentPane());
        categoryDisplay.getContentPane().setLayout(categoryDisplayLayout);
        categoryDisplayLayout.setHorizontalGroup(
            categoryDisplayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(categoryDisplayLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(categoryPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        categoryDisplayLayout.setVerticalGroup(
            categoryDisplayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, categoryDisplayLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(categoryPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        insertForm.setBounds(new java.awt.Rectangle(340, 310, 650, 410));
        insertForm.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        insertForm.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        insertForm.setResizable(false);

        insertForm_panel.setBackground(new java.awt.Color(203, 175, 135));
        insertForm_panel.setAlignmentX(0.0F);
        insertForm_panel.setAlignmentY(0.0F);

        hospitalName_lb.setForeground(new java.awt.Color(48, 71, 94));
        hospitalName_lb.setText("Hospital Name *");

        cancelForm_btn.setBackground(new java.awt.Color(231, 222, 200));
        cancelForm_btn.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        cancelForm_btn.setForeground(new java.awt.Color(0, 0, 0));
        cancelForm_btn.setText("Cancel");
        cancelForm_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelForm_btnActionPerformed(evt);
            }
        });

        contactNumber_lb.setForeground(new java.awt.Color(48, 71, 94));
        contactNumber_lb.setText("Contact Number *");

        location_lb.setForeground(new java.awt.Color(48, 71, 94));
        location_lb.setText("Location");

        categoryInsert_cb.setBackground(new java.awt.Color(231, 222, 200));
        categoryInsert_cb.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        categoryInsert_cb.setForeground(new java.awt.Color(0, 0, 0));
        categoryInsert_cb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Public", "Private" }));

        contactNumber_txt.setBackground(new java.awt.Color(255, 255, 255));
        contactNumber_txt.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        contactNumber_txt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                contactNumber_txtKeyReleased(evt);
            }
        });

        opdFee_txt.setBackground(new java.awt.Color(255, 255, 255));
        opdFee_txt.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        opdFee_txt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                opdFee_txtKeyReleased(evt);
            }
        });

        location_cb.setBackground(new java.awt.Color(231, 222, 200));
        location_cb.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        location_cb.setForeground(new java.awt.Color(0, 0, 0));
        location_cb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose Place" }));

        eye_ckb.setForeground(new java.awt.Color(51, 51, 51));
        eye_ckb.setText("Eye");
        eye_ckb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eye_ckbMouseClicked(evt);
            }
        });

        cancer_ckb.setForeground(new java.awt.Color(51, 51, 51));
        cancer_ckb.setText("Cancer");
        cancer_ckb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancer_ckbMouseClicked(evt);
            }
        });

        heart_ckb.setForeground(new java.awt.Color(51, 51, 51));
        heart_ckb.setText("Heart");
        heart_ckb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                heart_ckbMouseClicked(evt);
            }
        });

        skin_ckb.setForeground(new java.awt.Color(51, 51, 51));
        skin_ckb.setText("Skin");
        skin_ckb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                skin_ckbMouseClicked(evt);
            }
        });

        orthopaedic_ckb.setForeground(new java.awt.Color(51, 51, 51));
        orthopaedic_ckb.setText("Orthopaedic");
        orthopaedic_ckb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                orthopaedic_ckbMouseClicked(evt);
            }
        });

        women_ckb.setForeground(new java.awt.Color(51, 51, 51));
        women_ckb.setText("Women");
        women_ckb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                women_ckbMouseClicked(evt);
            }
        });

        general_ckb.setForeground(new java.awt.Color(51, 51, 51));
        general_ckb.setText("General");
        general_ckb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                general_ckbMouseClicked(evt);
            }
        });

        opdFee_lb.setForeground(new java.awt.Color(48, 71, 94));
        opdFee_lb.setText("OPD Fee");

        categoryInsert_lb.setBackground(new java.awt.Color(0, 0, 0));
        categoryInsert_lb.setForeground(new java.awt.Color(0, 0, 0));
        categoryInsert_lb.setText("Category");

        type_lb.setForeground(new java.awt.Color(48, 71, 94));
        type_lb.setText("Type *");

        insertForm_btn.setBackground(new java.awt.Color(231, 222, 200));
        insertForm_btn.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        insertForm_btn.setForeground(new java.awt.Color(0, 0, 0));
        insertForm_btn.setText("Insert");
        insertForm_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertForm_btnActionPerformed(evt);
            }
        });

        lalit_insertForm_rb.setActionCommand("Lalitpur");
        location_grb.add(lalit_insertForm_rb);
        lalit_insertForm_rb.setForeground(new java.awt.Color(0, 0, 0));
        lalit_insertForm_rb.setText("Lalitpur");
        lalit_insertForm_rb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lalit_insertForm_rbActionPerformed(evt);
            }
        });

        bhktp_insertForm_rb.setActionCommand("Bhaktapur");
        location_grb.add(bhktp_insertForm_rb);
        bhktp_insertForm_rb.setForeground(new java.awt.Color(0, 0, 0));
        bhktp_insertForm_rb.setText("Bhaktapur");
        bhktp_insertForm_rb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bhktp_insertForm_rbActionPerformed(evt);
            }
        });

        ktm_insertForm_rb .setActionCommand("Kathmandu");
        location_grb.add(ktm_insertForm_rb);
        ktm_insertForm_rb.setForeground(new java.awt.Color(0, 0, 0));
        ktm_insertForm_rb.setText("Kathmandu");
        ktm_insertForm_rb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ktm_insertForm_rbActionPerformed(evt);
            }
        });

        hospitalName_txt.setBackground(new java.awt.Color(255, 255, 255));
        hospitalName_txt.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        hospitalName_txt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                hospitalName_txtKeyReleased(evt);
            }
        });

        title_insertForm_lb.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        title_insertForm_lb.setForeground(new java.awt.Color(51, 51, 51));
        title_insertForm_lb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        title_insertForm_lb.setText("INSERT DATA");
        title_insertForm_lb.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout insertForm_panelLayout = new javax.swing.GroupLayout(insertForm_panel);
        insertForm_panel.setLayout(insertForm_panelLayout);
        insertForm_panelLayout.setHorizontalGroup(
            insertForm_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(insertForm_panelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(insertForm_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(title_insertForm_lb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(insertForm_panelLayout.createSequentialGroup()
                        .addComponent(eye_ckb)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cancer_ckb)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(heart_ckb)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(skin_ckb)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(orthopaedic_ckb)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(women_ckb)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(general_ckb))
                    .addGroup(insertForm_panelLayout.createSequentialGroup()
                        .addComponent(contactNumber_lb, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(opdFee_lb, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(categoryInsert_lb, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(hospitalName_lb, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(type_lb, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(location_lb)
                    .addGroup(insertForm_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(insertForm_panelLayout.createSequentialGroup()
                            .addComponent(insertForm_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(cancelForm_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(insertForm_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(hospitalName_txt, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(insertForm_panelLayout.createSequentialGroup()
                                .addComponent(contactNumber_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(14, 14, 14)
                                .addComponent(opdFee_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(categoryInsert_cb, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(insertForm_panelLayout.createSequentialGroup()
                                .addComponent(ktm_insertForm_rb)
                                .addGap(24, 24, 24)
                                .addComponent(bhktp_insertForm_rb)
                                .addGap(18, 18, 18)
                                .addComponent(lalit_insertForm_rb)
                                .addGap(18, 18, 18)
                                .addComponent(location_cb, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(19, 21, Short.MAX_VALUE))
        );
        insertForm_panelLayout.setVerticalGroup(
            insertForm_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(insertForm_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(title_insertForm_lb, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(hospitalName_lb)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(hospitalName_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(insertForm_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(contactNumber_lb)
                    .addComponent(opdFee_lb)
                    .addComponent(categoryInsert_lb))
                .addGap(4, 4, 4)
                .addGroup(insertForm_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(opdFee_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(categoryInsert_cb, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(contactNumber_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addComponent(location_lb)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(insertForm_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(location_cb, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ktm_insertForm_rb)
                    .addComponent(bhktp_insertForm_rb)
                    .addComponent(lalit_insertForm_rb))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(type_lb)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(insertForm_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(eye_ckb)
                    .addComponent(cancer_ckb)
                    .addComponent(heart_ckb)
                    .addComponent(skin_ckb)
                    .addComponent(orthopaedic_ckb)
                    .addComponent(women_ckb)
                    .addComponent(general_ckb))
                .addGap(22, 22, 22)
                .addGroup(insertForm_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(insertForm_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelForm_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout insertFormLayout = new javax.swing.GroupLayout(insertForm.getContentPane());
        insertForm.getContentPane().setLayout(insertFormLayout);
        insertFormLayout.setHorizontalGroup(
            insertFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(insertForm_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        insertFormLayout.setVerticalGroup(
            insertFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(insertForm_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        deleteForm.setBounds(new java.awt.Rectangle(310, 320, 600, 450));

        deleteForm_panel.setBackground(new java.awt.Color(203, 175, 135));
        deleteForm_panel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(0, 153, 255), new java.awt.Color(0, 153, 255), null));
        deleteForm_panel.setPreferredSize(new java.awt.Dimension(600, 500));

        contactNumber_deleteForm_lb.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        contactNumber_deleteForm_lb.setForeground(new java.awt.Color(48, 71, 94));
        contactNumber_deleteForm_lb.setText("Contact Number   :");

        opdFee_deleteForm_lb.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        opdFee_deleteForm_lb.setForeground(new java.awt.Color(48, 71, 94));
        opdFee_deleteForm_lb.setText("OPD Fee              :");

        hospitalName_deleteForm3.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        hospitalName_deleteForm3.setForeground(new java.awt.Color(48, 71, 94));
        hospitalName_deleteForm3.setText("Hospital Name     :");

        category_deleteForm_lb.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        category_deleteForm_lb.setForeground(new java.awt.Color(48, 71, 94));
        category_deleteForm_lb.setText("Category              :");

        type_deleteForm_lb.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        type_deleteForm_lb.setForeground(new java.awt.Color(48, 71, 94));
        type_deleteForm_lb.setText("Type                     :");

        location_deleteForm_lb.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        location_deleteForm_lb.setForeground(new java.awt.Color(48, 71, 94));
        location_deleteForm_lb.setText("Location                :");

        location_deleteForm_txt.setEditable(false);
        location_deleteForm_txt.setBackground(new java.awt.Color(203, 175, 135));
        location_deleteForm_txt.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        location_deleteForm_txt.setForeground(new java.awt.Color(0, 0, 0));
        location_deleteForm_txt.setText("Null");
        location_deleteForm_txt.setBorder(null);

        hospitalName_deleteForm_txt.setEditable(false);
        hospitalName_deleteForm_txt.setBackground(new java.awt.Color(203, 175, 135));
        hospitalName_deleteForm_txt.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        hospitalName_deleteForm_txt.setForeground(new java.awt.Color(0, 0, 0));
        hospitalName_deleteForm_txt.setText("Null");
        hospitalName_deleteForm_txt.setBorder(null);

        category_deleteForm_txt.setEditable(false);
        category_deleteForm_txt.setBackground(new java.awt.Color(203, 175, 135));
        category_deleteForm_txt.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        category_deleteForm_txt.setForeground(new java.awt.Color(0, 0, 0));
        category_deleteForm_txt.setText("Null");
        category_deleteForm_txt.setBorder(null);

        opdFee_deleteForm_txt.setEditable(false);
        opdFee_deleteForm_txt.setBackground(new java.awt.Color(203, 175, 135));
        opdFee_deleteForm_txt.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        opdFee_deleteForm_txt.setForeground(new java.awt.Color(0, 0, 0));
        opdFee_deleteForm_txt.setText("Null");
        opdFee_deleteForm_txt.setBorder(null);

        contactNumber_deleteForm_txt.setEditable(false);
        contactNumber_deleteForm_txt.setBackground(new java.awt.Color(203, 175, 135));
        contactNumber_deleteForm_txt.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        contactNumber_deleteForm_txt.setForeground(new java.awt.Color(0, 0, 0));
        contactNumber_deleteForm_txt.setText("Null");
        contactNumber_deleteForm_txt.setBorder(null);

        delete_deleteForm_btn.setBackground(new java.awt.Color(231, 222, 200));
        delete_deleteForm_btn.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        delete_deleteForm_btn.setForeground(new java.awt.Color(0, 0, 0));
        delete_deleteForm_btn.setText("Delete");
        delete_deleteForm_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delete_deleteForm_btnActionPerformed(evt);
            }
        });

        cancel_deleteForm_btn1.setBackground(new java.awt.Color(231, 222, 200));
        cancel_deleteForm_btn1.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        cancel_deleteForm_btn1.setForeground(new java.awt.Color(0, 0, 0));
        cancel_deleteForm_btn1.setText("Cancel");
        cancel_deleteForm_btn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancel_deleteForm_btn1ActionPerformed(evt);
            }
        });

        type_deleteForm_txt.setEditable(false);
        type_deleteForm_txt.setBackground(new java.awt.Color(203, 175, 135));
        type_deleteForm_txt.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        type_deleteForm_txt.setForeground(new java.awt.Color(0, 0, 0));
        type_deleteForm_txt.setText("Null");
        type_deleteForm_txt.setBorder(null);

        title_deleteForm_lb.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        title_deleteForm_lb.setForeground(new java.awt.Color(51, 51, 51));
        title_deleteForm_lb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        title_deleteForm_lb.setText("DELETE DATA");
        title_deleteForm_lb.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout deleteForm_panelLayout = new javax.swing.GroupLayout(deleteForm_panel);
        deleteForm_panel.setLayout(deleteForm_panelLayout);
        deleteForm_panelLayout.setHorizontalGroup(
            deleteForm_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, deleteForm_panelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(delete_deleteForm_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cancel_deleteForm_btn1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
            .addGroup(deleteForm_panelLayout.createSequentialGroup()
                .addGroup(deleteForm_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, deleteForm_panelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(title_deleteForm_lb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(deleteForm_panelLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(deleteForm_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(hospitalName_deleteForm3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(category_deleteForm_lb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(opdFee_deleteForm_lb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(type_deleteForm_lb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(contactNumber_deleteForm_lb, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(location_deleteForm_lb))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(deleteForm_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(location_deleteForm_txt, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(category_deleteForm_txt, javax.swing.GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE)
                            .addComponent(opdFee_deleteForm_txt, javax.swing.GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE)
                            .addComponent(hospitalName_deleteForm_txt, javax.swing.GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE)
                            .addComponent(contactNumber_deleteForm_txt)
                            .addComponent(type_deleteForm_txt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE))))
                .addContainerGap())
        );
        deleteForm_panelLayout.setVerticalGroup(
            deleteForm_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(deleteForm_panelLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(title_deleteForm_lb, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(deleteForm_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hospitalName_deleteForm3, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hospitalName_deleteForm_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(deleteForm_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(category_deleteForm_lb, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(category_deleteForm_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(deleteForm_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(opdFee_deleteForm_lb, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(opdFee_deleteForm_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(deleteForm_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(type_deleteForm_lb, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(type_deleteForm_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(deleteForm_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(contactNumber_deleteForm_lb, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(contactNumber_deleteForm_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(deleteForm_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(location_deleteForm_lb, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(location_deleteForm_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(deleteForm_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(delete_deleteForm_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancel_deleteForm_btn1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout deleteFormLayout = new javax.swing.GroupLayout(deleteForm.getContentPane());
        deleteForm.getContentPane().setLayout(deleteFormLayout);
        deleteFormLayout.setHorizontalGroup(
            deleteFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(deleteForm_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        deleteFormLayout.setVerticalGroup(
            deleteFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(deleteForm_panel, javax.swing.GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE)
        );

        updateForm.setBounds(new java.awt.Rectangle(340, 310, 650, 410));

        updateForm_panel.setBackground(new java.awt.Color(203, 175, 135));
        updateForm_panel.setAlignmentX(0.0F);
        updateForm_panel.setAlignmentY(0.0F);

        hospitalName_updateForm_lb.setForeground(new java.awt.Color(48, 71, 94));
        hospitalName_updateForm_lb.setText("Hospital Name");

        cancelForm_updateForm_btn.setBackground(new java.awt.Color(231, 222, 200));
        cancelForm_updateForm_btn.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        cancelForm_updateForm_btn.setForeground(new java.awt.Color(0, 0, 0));
        cancelForm_updateForm_btn.setText("Cancel");
        cancelForm_updateForm_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelForm_updateForm_btnActionPerformed(evt);
            }
        });

        contactNumber_updateForm_lb.setForeground(new java.awt.Color(48, 71, 94));
        contactNumber_updateForm_lb.setText("Contact Number");

        location_updateForm_lb.setForeground(new java.awt.Color(48, 71, 94));
        location_updateForm_lb.setText("Location");

        category_updateForm_cb.setBackground(new java.awt.Color(231, 222, 200));
        category_updateForm_cb.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        category_updateForm_cb.setForeground(new java.awt.Color(0, 0, 0));
        category_updateForm_cb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Public", "Private" }));

        contactNumber_updateForm_txt.setBackground(new java.awt.Color(255, 255, 255));
        contactNumber_updateForm_txt.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        contactNumber_updateForm_txt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                contactNumber_updateForm_txtKeyReleased(evt);
            }
        });

        opdFee_updateForm_txt.setBackground(new java.awt.Color(255, 255, 255));
        opdFee_updateForm_txt.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        opdFee_updateForm_txt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                opdFee_updateForm_txtKeyReleased(evt);
            }
        });

        eye_updateForm_ckb.setForeground(new java.awt.Color(51, 51, 51));
        eye_updateForm_ckb.setText("Eye");

        cancer_updateForm_ckb.setForeground(new java.awt.Color(51, 51, 51));
        cancer_updateForm_ckb.setText("Cancer");

        heart_updateForm_ckb.setForeground(new java.awt.Color(51, 51, 51));
        heart_updateForm_ckb.setText("Heart");

        skin_updateForm_ckb.setForeground(new java.awt.Color(51, 51, 51));
        skin_updateForm_ckb.setText("Skin");

        orthopaedic_updateForm_ckb.setForeground(new java.awt.Color(51, 51, 51));
        orthopaedic_updateForm_ckb.setText("Orthopaedic");

        women_updateForm_ckb.setForeground(new java.awt.Color(51, 51, 51));
        women_updateForm_ckb.setText("Women");

        general_updateForm_ckb.setForeground(new java.awt.Color(51, 51, 51));
        general_updateForm_ckb.setText("General");

        opdFee_updateForm_lb.setForeground(new java.awt.Color(48, 71, 94));
        opdFee_updateForm_lb.setText("OPD Fee");

        category_updateForm_lb.setForeground(new java.awt.Color(48, 71, 94));
        category_updateForm_lb.setText("Category");

        typev_updateForm_lb.setForeground(new java.awt.Color(48, 71, 94));
        typev_updateForm_lb.setText("Type");

        updateForm_btn.setBackground(new java.awt.Color(231, 222, 200));
        updateForm_btn.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        updateForm_btn.setForeground(new java.awt.Color(0, 0, 0));
        updateForm_btn.setText("Update");
        updateForm_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateForm_btnActionPerformed(evt);
            }
        });

        hospitalName_updateForm_txt.setBackground(new java.awt.Color(255, 255, 255));
        hospitalName_updateForm_txt.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        hospitalName_updateForm_txt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                hospitalName_updateForm_txtKeyReleased(evt);
            }
        });

        ktm_updateForm_rb.setActionCommand("Kathmandu");
        location_grb.add(ktm_updateForm_rb);
        ktm_updateForm_rb.setForeground(new java.awt.Color(0, 0, 0));
        ktm_updateForm_rb.setText("Kathmandu");
        ktm_updateForm_rb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ktm_updateForm_rbActionPerformed(evt);
            }
        });

        bhktp_updateForm_rb.setActionCommand("Bhaktapur");
        location_grb.add(bhktp_updateForm_rb);
        bhktp_updateForm_rb.setForeground(new java.awt.Color(0, 0, 0));
        bhktp_updateForm_rb.setText("Bhaktapur");
        bhktp_updateForm_rb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bhktp_updateForm_rbActionPerformed(evt);
            }
        });

        lalit_updateForm_rb.setActionCommand("Lalitpur");
        location_grb.add(lalit_updateForm_rb);
        lalit_updateForm_rb.setForeground(new java.awt.Color(0, 0, 0));
        lalit_updateForm_rb.setText("Lalitpur");
        lalit_updateForm_rb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lalit_updateForm_rbActionPerformed(evt);
            }
        });

        title_updateForm_lb.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        title_updateForm_lb.setForeground(new java.awt.Color(51, 51, 51));
        title_updateForm_lb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        title_updateForm_lb.setText("UPDATE DATA");
        title_updateForm_lb.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout updateForm_panelLayout = new javax.swing.GroupLayout(updateForm_panel);
        updateForm_panel.setLayout(updateForm_panelLayout);
        updateForm_panelLayout.setHorizontalGroup(
            updateForm_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(updateForm_panelLayout.createSequentialGroup()
                .addGroup(updateForm_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(updateForm_panelLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(title_updateForm_lb, javax.swing.GroupLayout.PREFERRED_SIZE, 585, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(updateForm_panelLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(updateForm_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(updateForm_panelLayout.createSequentialGroup()
                                .addGroup(updateForm_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(contactNumber_updateForm_txt, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                                    .addComponent(contactNumber_updateForm_lb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(updateForm_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(opdFee_updateForm_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(opdFee_updateForm_lb, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(updateForm_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(category_updateForm_lb, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(category_updateForm_cb, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(hospitalName_updateForm_txt)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, updateForm_panelLayout.createSequentialGroup()
                                .addComponent(updateForm_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cancelForm_updateForm_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(hospitalName_updateForm_lb, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(location_updateForm_lb)
                            .addComponent(typev_updateForm_lb, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(updateForm_panelLayout.createSequentialGroup()
                                .addGroup(updateForm_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(updateForm_panelLayout.createSequentialGroup()
                                        .addComponent(ktm_updateForm_rb, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(bhktp_updateForm_rb, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(updateForm_panelLayout.createSequentialGroup()
                                        .addComponent(eye_updateForm_ckb)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cancer_updateForm_ckb)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(heart_updateForm_ckb)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(updateForm_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(skin_updateForm_ckb, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lalit_updateForm_rb, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(updateForm_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(updateForm_panelLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(orthopaedic_updateForm_ckb)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(women_updateForm_ckb)
                                        .addGap(18, 18, 18)
                                        .addComponent(general_updateForm_ckb))
                                    .addGroup(updateForm_panelLayout.createSequentialGroup()
                                        .addGap(75, 75, 75)
                                        .addComponent(location_updateForm_cb, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addGap(19, 19, 19))
        );
        updateForm_panelLayout.setVerticalGroup(
            updateForm_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(updateForm_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(title_updateForm_lb, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(hospitalName_updateForm_lb)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(hospitalName_updateForm_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(updateForm_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(updateForm_panelLayout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(updateForm_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(contactNumber_updateForm_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(opdFee_updateForm_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(category_updateForm_cb, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(updateForm_panelLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(updateForm_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(opdFee_updateForm_lb)
                            .addComponent(contactNumber_updateForm_lb)
                            .addComponent(category_updateForm_lb))))
                .addGap(22, 22, 22)
                .addComponent(location_updateForm_lb)
                .addGroup(updateForm_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(updateForm_panelLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(updateForm_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ktm_updateForm_rb)
                            .addComponent(bhktp_updateForm_rb)
                            .addComponent(lalit_updateForm_rb)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, updateForm_panelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(location_updateForm_cb, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(9, 9, 9)
                .addComponent(typev_updateForm_lb)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(updateForm_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancer_updateForm_ckb)
                    .addComponent(heart_updateForm_ckb)
                    .addComponent(skin_updateForm_ckb)
                    .addComponent(orthopaedic_updateForm_ckb)
                    .addComponent(women_updateForm_ckb)
                    .addComponent(general_updateForm_ckb)
                    .addComponent(eye_updateForm_ckb))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(updateForm_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelForm_updateForm_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updateForm_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout updateFormLayout = new javax.swing.GroupLayout(updateForm.getContentPane());
        updateForm.getContentPane().setLayout(updateFormLayout);
        updateFormLayout.setHorizontalGroup(
            updateFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(updateForm_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        updateFormLayout.setVerticalGroup(
            updateFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(updateForm_panel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Hospital Information System");
        setBackground(new java.awt.Color(203, 175, 135));
        setBounds(new java.awt.Rectangle(0, 0, 1000, 700));

        sidePanel.setBackground(new java.awt.Color(203, 175, 135));
        sidePanel.setAlignmentX(0.0F);
        sidePanel.setAlignmentY(0.0F);

        search_txt.setBackground(new java.awt.Color(255, 255, 255));
        search_txt.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N

        update_btn.setBackground(new java.awt.Color(231, 222, 200));
        update_btn.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        update_btn.setForeground(new java.awt.Color(0, 0, 0));
        update_btn.setText("Update ");
        update_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                update_btnActionPerformed(evt);
            }
        });

        delete_btn.setBackground(new java.awt.Color(231, 222, 200));
        delete_btn.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        delete_btn.setForeground(new java.awt.Color(0, 0, 0));
        delete_btn.setText("Delete");
        delete_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delete_btnActionPerformed(evt);
            }
        });

        sort_cb.setBackground(new java.awt.Color(231, 222, 200));
        sort_cb.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        sort_cb.setForeground(new java.awt.Color(0, 0, 0));
        sort_cb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose your Sorting Order", "Price", "Name", "Category" }));
        sort_cb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sort_cbActionPerformed(evt);
            }
        });

        search_txt_btn.setBackground(new java.awt.Color(231, 222, 200));
        search_txt_btn.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        search_txt_btn.setForeground(new java.awt.Color(0, 0, 0));
        search_txt_btn.setText("Search");
        search_txt_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_txt_btnActionPerformed(evt);
            }
        });

        insert_btn.setBackground(new java.awt.Color(231, 222, 200));
        insert_btn.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        insert_btn.setForeground(new java.awt.Color(0, 0, 0));
        insert_btn.setText("Insert");
        insert_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insert_btnActionPerformed(evt);
            }
        });

        search_cb.setBackground(new java.awt.Color(231, 222, 200));
        search_cb.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        search_cb.setForeground(new java.awt.Color(0, 0, 0));
        search_cb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Search by Category", "Private", "Public" }));
        search_cb.setPreferredSize(new java.awt.Dimension(188, 22));
        search_cb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_cbActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout sidePanelLayout = new javax.swing.GroupLayout(sidePanel);
        sidePanel.setLayout(sidePanelLayout);
        sidePanelLayout.setHorizontalGroup(
            sidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(sidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sidePanelLayout.createSequentialGroup()
                        .addComponent(search_txt, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(search_txt_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(update_btn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(delete_btn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(insert_btn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sort_cb, 0, 229, Short.MAX_VALUE)
                    .addComponent(search_cb, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        sidePanelLayout.setVerticalGroup(
            sidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidePanelLayout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(sidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(search_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(search_txt_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addComponent(search_cb, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(sort_cb, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(insert_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(update_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(delete_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        //table.getColumnModel().getColumn(0).setPreferredWidth(40);
        table.setBackground(new java.awt.Color(231, 222, 199));
        table.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Civil Service Hospital", "01-4107002", "100", "Public", "Minbhawan-Kathmandu", "General"},
                {"Bir Hospital", "01-4230710", "100", "Public", "Ratna Park-Kathmandu", "General"},
                {"Tilganga Institute of Ophthalmology", "01-5970048", "120", "Private", "Gaushala-Kathmandu", "Eye"},
                {"Bhaktapur Eye Institute ", "01-5093228", "200", "Private", "Madhyapur Thimi-Bhaktapur", "Eye"},
                {"Nepal Cancer Hospital ", "01-5251312", "140", "Private", "Harisiddhi-Kathmandu", "Cancer"},
                {"Paropakar Maternity ", "01-5590676", "115", "Private", "Ekantakuna-Lalitpur ", "Women"},
                {"Gandhi Tulasi Manohara Community Hospital", "01-4990701", "160", "Public", "Jagatjung marga-Kathmandu", "General"},
                {"Gangalal Hospital", "01-4371322", "130", "Private", "Bansbari Rd-Kathmandu", "Heart"},
                {"Patan Hospital", "01-5522295", "100", "Public", "Lagankhel-Lalitpur ", "General"},
                {"Nepal Police Hospital", "01-4412430", "100", "Public", "Panipokhari Marg-Kathmandu", "General"},
                {"Bhaktapur Cancer Hospital", "01-6611532", "150", "Private", "Bharbacho-Bhaktapur", "Cancer"},
                {"Nepal Orthopaedic Hospital", "01-4911725", "135", "Private", "Gokarneshwor-Kathmandu", "Orthopaedic"},
                {"Nepal Korea Skin Care Hospital", " 01-4621996", "145", "Private", "Baneshower-Kathmandu", "Skin"}
            },
            new String [] {
                "Hospital Name", "Contact", "OPD Fee", "Category", "Location", "Type"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setAlignmentX(0.0F);
        table.setAlignmentY(0.0F);
        table.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        table.setGridColor(new java.awt.Color(51, 51, 51));
        table.setIntercellSpacing(new java.awt.Dimension(10, 0));
        table.setRowHeight(45);
        table.setSelectionBackground(new java.awt.Color(48, 71, 94));
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        table.setShowGrid(true);
        table.getTableHeader().setResizingAllowed(false);
        table.getTableHeader().setReorderingAllowed(false);
        tableScrollPane.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setResizable(false);
            table.getColumnModel().getColumn(0).setPreferredWidth(300);
            table.getColumnModel().getColumn(1).setResizable(false);
            table.getColumnModel().getColumn(2).setResizable(false);
            table.getColumnModel().getColumn(3).setResizable(false);
            table.getColumnModel().getColumn(4).setResizable(false);
            table.getColumnModel().getColumn(4).setPreferredWidth(200);
            table.getColumnModel().getColumn(5).setResizable(false);
        }

        titlePanel.setBackground(new java.awt.Color(48, 71, 94));

        title_lb.setBackground(new java.awt.Color(48, 71, 94));
        title_lb.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        title_lb.setForeground(new java.awt.Color(255, 255, 255));
        title_lb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        title_lb.setText("Hospital Information System");
        title_lb.setAlignmentY(0.0F);

        javax.swing.GroupLayout titlePanelLayout = new javax.swing.GroupLayout(titlePanel);
        titlePanel.setLayout(titlePanelLayout);
        titlePanelLayout.setHorizontalGroup(
            titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titlePanelLayout.createSequentialGroup()
                .addComponent(title_lb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(232, 232, 232))
        );
        titlePanelLayout.setVerticalGroup(
            titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, titlePanelLayout.createSequentialGroup()
                .addGap(0, 6, Short.MAX_VALUE)
                .addComponent(title_lb, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        footerPanel.setBackground(new java.awt.Color(48, 71, 94));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 0, 10)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("<html>  &ensp;  &ensp;  Hospital Information System is optimized for inserting,updating,deleting,searching and sorting.<br>\n While using this application,you agree to have read and accepted our term of use and privacy policy.<br>\n &ensp;&ensp; &ensp; &ensp; &ensp; &ensp; &ensp; &ensp; &ensp; &ensp; &ensp; &ensp; \tCopyright 2022-2022 by Islington Students.All Right Reserved. </html>");

        javax.swing.GroupLayout footerPanelLayout = new javax.swing.GroupLayout(footerPanel);
        footerPanel.setLayout(footerPanelLayout);
        footerPanelLayout.setHorizontalGroup(
            footerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(footerPanelLayout.createSequentialGroup()
                .addGap(122, 122, 122)
                .addComponent(jLabel1)
                .addGap(136, 136, 136))
        );
        footerPanelLayout.setVerticalGroup(
            footerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, footerPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );

        menuBar.setBackground(new java.awt.Color(245, 222, 200));
        menuBar.setBorder(null);
        menuBar.setForeground(new java.awt.Color(0, 0, 0));
        menuBar.setToolTipText("");
        menuBar.setAlignmentX(0.0F);
        menuBar.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        menuBar.setPreferredSize(new java.awt.Dimension(40, 33));

        fileMenu.setBackground(new java.awt.Color(48, 71, 94));
        fileMenu.setBorder(null);
        fileMenu.setText("   File  ");
        fileMenu.setAlignmentX(0.1F);
        fileMenu.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        fileMenu.setRequestFocusEnabled(false);
        fileMenu.setRolloverEnabled(false);

        openMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        openMenuItem.setText("Open");
        openMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(openMenuItem);

        exitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText("Help");
        helpMenu.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N

        about.setText("About");
        about.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutActionPerformed(evt);
            }
        });
        helpMenu.add(about);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(sidePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(titlePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 755, Short.MAX_VALUE)))
            .addComponent(footerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(titlePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE))
                    .addComponent(sidePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(footerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
// Action Performed for main Insert Button to Create insert Form
    private void insert_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insert_btnActionPerformed
        ktm_insertForm_rb.setSelected(true);
        setCombobox();
        resetForm();
        setInsertButtonVisible();
        insertForm.setVisible(true);    
    }//GEN-LAST:event_insert_btnActionPerformed
// Action Performed to insert data into table
    private void insertForm_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertForm_btnActionPerformed

            try {

            // Geting value from textfiled 
            String name = hospitalName_txt.getText().trim();
            String contact = contactNumber_txt.getText().trim();
            String opdFee = opdFee_txt.getText().trim();
            String category = (String)categoryInsert_cb.getSelectedItem();
            String location_dict = location_grb.getSelection().getActionCommand();
            String location_place = (String)location_cb.getSelectedItem();
            String location = location_place+"-"+location_dict;

            String types = "";
            if (eye_ckb.isSelected()){
                         types += "Eye,";
                     }
            if (cancer_ckb.isSelected()){
                         types += "Cancer,";
                     }
            if (heart_ckb.isSelected()){
                         types += "Heart,";
                     }
            if (skin_ckb.isSelected()){
                         types += "Skin,";
                     }
             if (orthopaedic_ckb.isSelected()){
                         types += "Orthopaedic,";
                     }
            if (women_ckb.isSelected()){
                         types += "Women,";
                     }
            if (general_ckb.isSelected()){
                         types += "General,";
              }
             types = types.replaceFirst(".$","");

             Object [] datas = {name,contact,opdFee,category,location,types};

            insertTableData(datas); // to insert data into table
            insertForm.dispose();
        }catch (Exception e)
            {JOptionPane.showMessageDialog(null,"Error");
        }
    }//GEN-LAST:event_insertForm_btnActionPerformed
    
// TO display detailed of selected in Delete Form
    private void delete_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete_btnActionPerformed
  
        // To get Selected data and display
        boolean flag = false;
        try {
        int row = table.getSelectedRow();
        if(row == -1){
               flag = true;
            }
        
        
        String name = table.getModel().getValueAt(row, 0).toString();
        String contact = table.getModel().getValueAt(row, 1).toString();
        String opdFee = table.getModel().getValueAt(row, 2).toString();
        String category = table.getModel().getValueAt(row, 3).toString();
        String location = table.getModel().getValueAt(row, 4).toString();
        String type = table.getModel().getValueAt(row, 5).toString();
        
        
        
        hospitalName_deleteForm_txt.setText(name);
        contactNumber_deleteForm_txt.setText(contact);
        opdFee_deleteForm_txt.setText(opdFee);
        category_deleteForm_txt.setText(category);
        location_deleteForm_txt.setText(location);
        type_deleteForm_txt.setText(type);
        
        deleteForm.setVisible(true);
        }catch (Exception e){
            deleteForm.dispose();
            if(flag){
                JOptionPane.showMessageDialog(null,"Please Select Any Row !"); 
            }else{
             JOptionPane.showMessageDialog(null,"Error");   
            }
            
        }

    }//GEN-LAST:event_delete_btnActionPerformed
// To cancel delete form
    private void cancel_deleteForm_btn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancel_deleteForm_btn1ActionPerformed

        
        // SETING TEXT VALUE TO DEFALUT 
        hospitalName_deleteForm_txt.setText(null);
        contactNumber_deleteForm_txt.setText(null);
        opdFee_deleteForm_txt.setText(null);
        category_deleteForm_txt.setText(null);
        location_deleteForm_txt.setText(null);
        type_deleteForm_txt.setText(null);
        
        deleteForm.dispose();  //Close Dialog Box
    }//GEN-LAST:event_cancel_deleteForm_btn1ActionPerformed
 // To delete selected row 
    private void delete_deleteForm_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete_deleteForm_btnActionPerformed

        
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        model.removeRow(table.getSelectedRow());
        
        deleteForm.dispose();  //Close Dialog Box
    }//GEN-LAST:event_delete_deleteForm_btnActionPerformed
// To open txt and cvs file 
    private void openMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openMenuItemActionPerformed
        // TODO add your handling code here:
        //File Chooser
        final JFileChooser fc = new JFileChooser();
        //result to current instance
        int result = fc.showOpenDialog(null);
        //chosen file by user
        if (result == fc.APPROVE_OPTION){
            //getting file choosen
            File file = fc.getSelectedFile();
            try {
                //to read the file
                BufferedReader reader = new BufferedReader(new FileReader(file));
                
                Object lines[] = reader.lines().toArray();

                //adding to table
                for (int i = 0; i < lines.length -1;i++) {
                    String line = lines[i].toString().trim();
                    Object dataRow[] = line.split(",");
                    String name = (String) dataRow[0];
                    String contact = (String) dataRow[1];
                    if (isUniqueName(name,contact)){
                        insertTableData(dataRow); // inserting data into table
                    }else{
                        JOptionPane.showMessageDialog(rootPane, "Duplicate Data Found Having Name: "+name,"Error",JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Error Occured", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }else{
            //if file not chosen
            JOptionPane.showMessageDialog(rootPane, "File Not Selected", "WARNING", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_openMenuItemActionPerformed
// this function open update Form from selected row
    private void update_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update_btnActionPerformed

        boolean flag = false;
   
       resetForm();
        try {
        int row = table.getSelectedRow();
        if(row == -1){
               flag = true;
            }
        String name = (String) table.getModel().getValueAt(row, 0);
        String contact = (String) table.getModel().getValueAt(row, 1);
        String opdFee = (String) table.getModel().getValueAt(row, 2);
        String category = (String) table.getModel().getValueAt(row, 3);
        String location = (String) table.getModel().getValueAt(row, 4);
        String type = (String) table.getModel().getValueAt(row, 5);
        
        if(location.contains("-")){
            String[] locationString = location.split("-", 2);//Array  for location
            String location_place = locationString[0].trim();
            String location_dict = locationString[1].trim();
            
            switch(location_dict){
                 
                case "Kathmandu" :
                 
                  ktm_updateForm_rb.setSelected(true);
                  setCombobox();
                  location_updateForm_cb.setSelectedItem(location_place);
                  
                    break;
                case "Bhaktapur" :
                 
                    bhktp_updateForm_rb.setSelected(true);
                     setCombobox();
                    location_updateForm_cb.setSelectedItem(location_place);
                    break;
                case "Lalitpur":
                   
                    lalit_updateForm_rb.setSelected(true);
                     setCombobox();
                    location_updateForm_cb.setSelectedItem(location_place);
                    break;
                default:
                break;
                }
            location_updateForm_cb.setSelectedItem(location_place); 
            
            }
        
        String[] typeString = type.split(",");//Array  for Types
        
        
        for(String types:typeString){
            if(types.equals("Eye")){
             eye_updateForm_ckb.setSelected(true);   
            }
            if(types.equals("Cancer")){
             cancer_updateForm_ckb.setSelected(true); 
            }
            if(types.equals("Heart")){
             heart_updateForm_ckb.setSelected(true);     
            }
            if(types.equals("Skin")){
             skin_updateForm_ckb.setSelected(true);       
            }
            if(types.equals("Orthopaedic")){
             orthopaedic_updateForm_ckb.setSelected(true);   
            }
            if(types.equals("Women")){
             women_updateForm_ckb.setSelected(true);      
            }
            if(types.equals("General")){
             general_updateForm_ckb.setSelected(true);  
            } 
               
        } 
        hospitalName_updateForm_txt.setText(name);
        contactNumber_updateForm_txt.setText(contact);
        opdFee_updateForm_txt.setText(opdFee);
        category_updateForm_cb.setSelectedItem(category);
        setCombobox();
        updateForm.setVisible(true);
       resetForm();
        
        }catch (Exception e){
            updateForm.dispose();
            if(flag){
                JOptionPane.showMessageDialog(null,"Please Select Any Row!","WARNING",JOptionPane.WARNING_MESSAGE); 
            }else{
                
             JOptionPane.showMessageDialog(null,"Error","Error",JOptionPane.ERROR_MESSAGE);   
            }
        }
    }//GEN-LAST:event_update_btnActionPerformed

    private void cancelForm_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelForm_btnActionPerformed
        // TODO add your handling code here:
        insertForm.dispose();
        
    }//GEN-LAST:event_cancelForm_btnActionPerformed
   
    private void ktm_insertForm_rbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ktm_insertForm_rbActionPerformed
       setkathmaduCombobox();   
    }//GEN-LAST:event_ktm_insertForm_rbActionPerformed

    private void bhktp_insertForm_rbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bhktp_insertForm_rbActionPerformed
        // TODO add your handling code here:
        setBhaktapurCombobox();
    }//GEN-LAST:event_bhktp_insertForm_rbActionPerformed

    private void lalit_insertForm_rbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lalit_insertForm_rbActionPerformed
        // TODO add your handling code here:
        setLalitpurCombobox();
    }//GEN-LAST:event_lalit_insertForm_rbActionPerformed
   // Action Perfromed for sorting
    private void sort_cbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sort_cbActionPerformed
        int selected = sort_cb.getSelectedIndex();
        if (selected == 0) {
            JOptionPane.showMessageDialog(rootPane,"Please Select a Sorting Order", "WARNING", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (selected == 1) {
            sort(2);
        } else if(selected == 2){
            sort(0);
        } else if (selected == 3) {
            sort(3);
        }
    }//GEN-LAST:event_sort_cbActionPerformed
// Validation for hospital Name Insert Form
    private void hospitalName_txtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_hospitalName_txtKeyReleased
       //Contact number validation
         if (isUniqueName(hospitalName_txt.getText(),"")) {
                    hospitalName_lb.setForeground(CYAN);
                    hospitalName_lb.setText("Hospital Name");
                    hospitalName_lb.setForeground(BLACK);
                    nameValid = true;
                    
                } else {
                    hospitalName_lb.setForeground(RED);
                    hospitalName_lb.setText("Hospital Name (Duplicate)");
                    hospitalName_lb.setForeground(RED);
                    nameValid = false;
                    
                }
        // condition for empty
                if (hospitalName_txt.getText().trim().equals("")) {
                    hospitalName_lb.setForeground(RED);
                    hospitalName_lb.setText("Hospital Name (Required)");
                    nameValid = false;
                }
        // methods for making create button visible
                setInsertButtonVisible();
        
        
    }//GEN-LAST:event_hospitalName_txtKeyReleased
// Validation for contact number Insert Form
    private void contactNumber_txtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_contactNumber_txtKeyReleased
       if (contactNumberBoolean(contactNumber_txt.getText()) || isUniqueName("",contactNumber_txt.getText())) {
                    contactNumber_lb.setForeground(BLACK);
                    contactNumber_lb.setText("Contact Number");
                    contactNumber_txt.setForeground(BLACK);
                    contactValid = true;
                } 
        if (! contactNumberBoolean(contactNumber_txt.getText())) {
                    contactNumber_lb.setForeground(RED);
                    contactNumber_lb.setText("Contact Number (Invalid)");
                    contactNumber_txt.setForeground(RED);
                    contactValid = false;
                } 
        if (! isUniqueName("",contactNumber_txt.getText())){
                    contactNumber_lb.setForeground(RED);
                    contactNumber_lb.setText("Contact Number (Duplicate)");
                    contactNumber_lb.setForeground(RED);
                    contactValid = false;
                }
        if (contactNumber_txt.getText().trim().equals("")){
                    contactNumber_lb.setForeground(RED);
                    contactNumber_lb.setText("Contact Number (Required)");
                    contactValid = false;
                }
        setInsertButtonVisible();
        
    }//GEN-LAST:event_contactNumber_txtKeyReleased
// Validation for opdfee Insert Form
    private void opdFee_txtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_opdFee_txtKeyReleased
       if (opdFeeBoolean(opdFee_txt.getText())){
            opdFee_lb.setForeground(BLACK);
            opdFee_lb.setText("OPD Fee");
            opdFee_txt.setForeground(BLACK);
            feeValid = true;
        }else{
            opdFee_lb.setForeground(RED);
            opdFee_lb.setText("OPD Fee (Invalid)");
            opdFee_txt.setForeground(RED);
            feeValid  = false;
        }
       setInsertButtonVisible();
    }//GEN-LAST:event_opdFee_txtKeyReleased
/*
    Mouse clicked Event For checkbox Insert Form
    */
    private void eye_ckbMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eye_ckbMouseClicked
        // TODO add your handling code here:
        setCheckBoxValidation();
    }//GEN-LAST:event_eye_ckbMouseClicked

    private void cancer_ckbMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancer_ckbMouseClicked
        // TODO add your handling code here:
        setCheckBoxValidation();
    }//GEN-LAST:event_cancer_ckbMouseClicked

    private void heart_ckbMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_heart_ckbMouseClicked
        // TODO add your handling code here:
        setCheckBoxValidation();
    }//GEN-LAST:event_heart_ckbMouseClicked

    private void skin_ckbMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_skin_ckbMouseClicked
        // TODO add your handling code here:
        setCheckBoxValidation();
    }//GEN-LAST:event_skin_ckbMouseClicked

    private void orthopaedic_ckbMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_orthopaedic_ckbMouseClicked
        // TODO add your handling code here:
        setCheckBoxValidation();
    }//GEN-LAST:event_orthopaedic_ckbMouseClicked

    private void women_ckbMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_women_ckbMouseClicked
        // TODO add your handling code here:
        setCheckBoxValidation();
    }//GEN-LAST:event_women_ckbMouseClicked

    private void general_ckbMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_general_ckbMouseClicked
        // TODO add your handling code here:
        setCheckBoxValidation();
    }//GEN-LAST:event_general_ckbMouseClicked
//Action performed for bhktp update form comboBox
    private void lalit_updateForm_rbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lalit_updateForm_rbActionPerformed
        // TODO add your handling code here:
        setLalitpurCombobox();
    }//GEN-LAST:event_lalit_updateForm_rbActionPerformed
//Action performed for bhktp update form comboBox
    private void bhktp_updateForm_rbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bhktp_updateForm_rbActionPerformed
        // TODO add your handling code here:
        setBhaktapurCombobox();
    }//GEN-LAST:event_bhktp_updateForm_rbActionPerformed
//Action performed for ktm update form comboBox
    private void ktm_updateForm_rbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ktm_updateForm_rbActionPerformed
        // TODO add your handling code here:
        setkathmaduCombobox();
    }//GEN-LAST:event_ktm_updateForm_rbActionPerformed
// validation for hospital name update form
    private void hospitalName_updateForm_txtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_hospitalName_updateForm_txtKeyReleased
        //Contact number validation
        if (isUniqueName(hospitalName_updateForm_txt.getText(),"")) {
            hospitalName_updateForm_lb.setForeground(CYAN);
            hospitalName_updateForm_lb.setText("Hospital Name");
            hospitalName_updateForm_lb.setForeground(BLACK);
            nameValid = true;

        } else {
            hospitalName_updateForm_lb.setForeground(RED);
            hospitalName_updateForm_lb.setText("Hospital Name (Duplicate)");
            hospitalName_updateForm_lb.setForeground(RED);
            nameValid = false;

        }
        // condition for empty
        if (hospitalName_updateForm_txt.getText().trim().equals("")) {
            hospitalName_updateForm_lb.setForeground(RED);
            hospitalName_updateForm_lb.setText("Hospital Name (Required)");
            nameValid = false;
        }
        // methods for making update button visible
        setInsertButtonVisible();

    }//GEN-LAST:event_hospitalName_updateForm_txtKeyReleased

// updating data into table 
    private void updateForm_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateForm_btnActionPerformed
       
        try {
            String name = hospitalName_updateForm_txt.getText().trim();
            String contact = contactNumber_updateForm_txt.getText().trim();
            String opdFee = opdFee_updateForm_txt.getText().trim();
            String category = (String)category_updateForm_cb.getSelectedItem();
            String location_dict = location_grb.getSelection().getActionCommand();
            String location_place = (String)location_updateForm_cb.getSelectedItem();
            String location = location_place+"-"+location_dict;

            String types = "";
            if (eye_updateForm_ckb.isSelected()){
                types += "Eye,";
            }
            if (cancer_updateForm_ckb.isSelected()){
                types += "Cancer,";
            }
            if (heart_updateForm_ckb.isSelected()){
                types += "Heart,";
            }
            if (skin_updateForm_ckb.isSelected()){
                types += "Skin,";
            }
            if (orthopaedic_updateForm_ckb.isSelected()){
                types += "Orthopaedic,";
            }
            if (women_updateForm_ckb.isSelected()){
                types += "Women,";
            }
            if (general_updateForm_ckb.isSelected()){
                types += "General,";
            }
            types = types.replaceFirst(".$","");

            DefaultTableModel tmodels = (DefaultTableModel)table.getModel();
            int i = table.getSelectedRow();
            tmodels.setValueAt(name, i, 0);
            tmodels.setValueAt(contact, i, 1);
            tmodels.setValueAt(opdFee, i, 2);
            tmodels.setValueAt(category, i, 3);
            tmodels.setValueAt(location, i, 4);
            tmodels.setValueAt(types, i, 5);
            updateForm.dispose();
        }catch (Exception e){JOptionPane.showMessageDialog(null,"Error");}

    }//GEN-LAST:event_updateForm_btnActionPerformed
// Validation for opd Fee for update Form
    private void opdFee_updateForm_txtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_opdFee_updateForm_txtKeyReleased
        if (opdFeeBoolean(opdFee_updateForm_txt.getText())){
            opdFee_updateForm_lb.setForeground(BLACK);
            opdFee_updateForm_lb.setText("OPD Fee");
            opdFee_updateForm_txt.setForeground(BLACK);
            feeValid = true;
        }else{
            opdFee_updateForm_lb.setForeground(RED);
            opdFee_updateForm_lb.setText("OPD Fee (Invalid)");
            opdFee_updateForm_txt.setForeground(RED);
            feeValid  = false;
        }
        setInsertButtonVisible();
    }//GEN-LAST:event_opdFee_updateForm_txtKeyReleased
// Validation for contact number of update form
    private void contactNumber_updateForm_txtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_contactNumber_updateForm_txtKeyReleased

        if (contactNumberBoolean(contactNumber_updateForm_txt.getText()) || isUniqueName("",contactNumber_updateForm_txt.getText())) {
            contactNumber_updateForm_lb.setForeground(BLACK);
            contactNumber_updateForm_lb.setText("Contact Number");
            contactNumber_updateForm_txt.setForeground(BLACK);
            contactValid = true;
        }
        if (! contactNumberBoolean(contactNumber_updateForm_txt.getText())) {
            contactNumber_updateForm_lb.setForeground(RED);
            contactNumber_updateForm_lb.setText("Contact Number (Invalid)");
            contactNumber_updateForm_lb.setForeground(RED);
            contactValid = false;
        }
        if (! isUniqueName("",contactNumber_updateForm_txt.getText())){
            contactNumber_updateForm_lb.setForeground(RED);
            contactNumber_updateForm_lb.setText("Contact Number (Duplicate)");
            contactNumber_updateForm_lb.setForeground(RED);
            contactValid = false;
        }
        if (contactNumber_updateForm_txt.getText().trim().equals("")){
            contactNumber_updateForm_lb.setForeground(RED);
            contactNumber_updateForm_lb.setText("Contact Number (Required)");
            contactValid = false;
        }
        setInsertButtonVisible();

    }//GEN-LAST:event_contactNumber_updateForm_txtKeyReleased
//Cancel the uodate form
    private void cancelForm_updateForm_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelForm_updateForm_btnActionPerformed

        updateForm.dispose();
        resetForm();

    }//GEN-LAST:event_cancelForm_updateForm_btnActionPerformed
// to close the jFrame
    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void search_cbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_cbActionPerformed
        // TODO add your handling code here:
        String selected = search_cb.getSelectedItem().toString();
        ArrayList<ArrayList<Object>> hospitalData = getData();
        DefaultTableModel model = (DefaultTableModel)tableDisplay.getModel();
        
        if (search_cb.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(rootPane,"Please Select a Category", "WARNING", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int count = 0;
        for (int i = 0; i<table.getRowCount();i++){
             for (int j = 0; j<table.getColumnCount();j++){
                 if (hospitalData.get(i).get(j).equals(selected)){
                     Object name = hospitalData.get(i).get(0);
                     Object phone = hospitalData.get(i).get(1);
                     Object fee = hospitalData.get(i).get(2);
                     Object category = hospitalData.get(i).get(3);
                     Object location = hospitalData.get(i).get(4);
                     Object type = hospitalData.get(i).get(5);
                     model.addRow(new Object[]{name,phone,fee,category,location,type});
                     count += 1;
                 }
             }
        }
        JOptionPane.showMessageDialog(rootPane, "There are " + count + " number of " + selected + " hospitals.","Hospital Information",JOptionPane.INFORMATION_MESSAGE);
        //show contents on table after category search
        categoryDisplay.setLocationRelativeTo(null);
        categoryDisplay.setVisible(true);
        
        
    }//GEN-LAST:event_search_cbActionPerformed

    private void search_txt_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_txt_btnActionPerformed
        
        sort(2);
        ArrayList<ArrayList<Object>> hospitalData = getData();
        Object opdFee[] = getPrice();
        try{
            int price = Integer.parseInt(search_txt.getText());
            int low = 0;
            int high = opdFee.length;
            
            int result = binSearch(opdFee,low,high,price);

            if (result == -1){
                JOptionPane.showMessageDialog(rootPane,"Data not found!");
            } else{
                //to show in optionpane
                String nameinfo = (String) hospitalData.get(result).get(0);
                String phoneinfo = (String) hospitalData.get(result).get(1);
                String feeinfo = (String) hospitalData.get(result).get(2);
                String categoryinfo = (String) hospitalData.get(result).get(3);
                String locationinfo = (String) hospitalData.get(result).get(4);
                String typeinfo = (String) hospitalData.get(result).get(5);
                String info = "Name: " + nameinfo + ", Phone: " + phoneinfo + ", OPD Fee: " + feeinfo + ", Category: " + categoryinfo + ", Location: " + locationinfo + ", Type: " + typeinfo;
                JOptionPane.showMessageDialog(rootPane,info, "Hospital Information",JOptionPane.INFORMATION_MESSAGE);
                search_txt.setText("");
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(rootPane, "Please Type a Price", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            search_txt.setText("");
        }
    }//GEN-LAST:event_search_txt_btnActionPerformed

    private void aboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutActionPerformed
        
        JOptionPane.showMessageDialog(rootPane, "<HTML> <Title>Help</Title><body><p>&ensp;A hospital management system is a computerized system that is designed and programmed to obtain hospital information based on the needs of the user<br><br>"
                + "&ensp;&ensp;Files can be imported using the Open Button<br> "
                + "&ensp;&ensp;Data can be added to the table using the Insert Button<br>"
                + "&ensp;&ensp;Data can be updated using the Update Button and Deleted using the delete button<br>"
                + "&ensp;&ensp;The search button can be used to search the hospital data according to Price and Category<br>"
                + "&ensp;&ensp;Data within the table can be sorted according to Hospital Name, Price, and Category<br>"
                + "</p><br>"
                + "<p>&ensp;&ensp;Thank you for using this system.</p></body></HTML>","Help",JOptionPane.PLAIN_MESSAGE);
    }//GEN-LAST:event_aboutActionPerformed

    private void displayCancel_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_displayCancel_btnActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel)tableDisplay.getModel();
        categoryDisplay.dispose();
        model.setRowCount(0);
    }//GEN-LAST:event_displayCancel_btnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new myFrame().setVisible(true);
                
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem about;
    private javax.swing.JRadioButton bhktp_insertForm_rb;
    private javax.swing.JRadioButton bhktp_updateForm_rb;
    private javax.swing.JButton cancelForm_btn;
    private javax.swing.JButton cancelForm_updateForm_btn;
    private javax.swing.JButton cancel_deleteForm_btn1;
    private javax.swing.JCheckBox cancer_ckb;
    private javax.swing.JCheckBox cancer_updateForm_ckb;
    private javax.swing.JFrame categoryDisplay;
    private javax.swing.JComboBox<String> categoryInsert_cb;
    private javax.swing.JLabel categoryInsert_lb;
    private javax.swing.JPanel categoryPanel;
    private javax.swing.JLabel category_deleteForm_lb;
    private javax.swing.JTextField category_deleteForm_txt;
    private javax.swing.JComboBox<String> category_updateForm_cb;
    private javax.swing.JLabel category_updateForm_lb;
    private javax.swing.JLabel contactNumber_deleteForm_lb;
    private javax.swing.JTextField contactNumber_deleteForm_txt;
    private javax.swing.JLabel contactNumber_lb;
    private javax.swing.JTextField contactNumber_txt;
    private javax.swing.JLabel contactNumber_updateForm_lb;
    private javax.swing.JTextField contactNumber_updateForm_txt;
    private javax.swing.JDialog deleteForm;
    private javax.swing.JPanel deleteForm_panel;
    private javax.swing.JButton delete_btn;
    private javax.swing.JButton delete_deleteForm_btn;
    private javax.swing.JButton displayCancel_btn;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JCheckBox eye_ckb;
    private javax.swing.JCheckBox eye_updateForm_ckb;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JPanel footerPanel;
    private javax.swing.JCheckBox general_ckb;
    private javax.swing.JCheckBox general_updateForm_ckb;
    private javax.swing.JCheckBox heart_ckb;
    private javax.swing.JCheckBox heart_updateForm_ckb;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JLabel hospitalName_deleteForm3;
    private javax.swing.JTextField hospitalName_deleteForm_txt;
    private javax.swing.JLabel hospitalName_lb;
    private javax.swing.JTextField hospitalName_txt;
    private javax.swing.JLabel hospitalName_updateForm_lb;
    private javax.swing.JTextField hospitalName_updateForm_txt;
    private javax.swing.JDialog insertForm;
    private javax.swing.JButton insertForm_btn;
    private javax.swing.JPanel insertForm_panel;
    private javax.swing.JButton insert_btn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JRadioButton ktm_insertForm_rb;
    private javax.swing.JRadioButton ktm_updateForm_rb;
    private javax.swing.JRadioButton lalit_insertForm_rb;
    private javax.swing.JRadioButton lalit_updateForm_rb;
    private javax.swing.JComboBox<String> location_cb;
    private javax.swing.JLabel location_deleteForm_lb;
    private javax.swing.JTextField location_deleteForm_txt;
    private javax.swing.ButtonGroup location_grb;
    private javax.swing.JLabel location_lb;
    private javax.swing.JComboBox<String> location_updateForm_cb;
    private javax.swing.JLabel location_updateForm_lb;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JLabel opdFee_deleteForm_lb;
    private javax.swing.JTextField opdFee_deleteForm_txt;
    private javax.swing.JLabel opdFee_lb;
    private javax.swing.JTextField opdFee_txt;
    private javax.swing.JLabel opdFee_updateForm_lb;
    private javax.swing.JTextField opdFee_updateForm_txt;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JCheckBox orthopaedic_ckb;
    private javax.swing.JCheckBox orthopaedic_updateForm_ckb;
    private javax.swing.JComboBox<String> search_cb;
    private javax.swing.JTextField search_txt;
    private javax.swing.JButton search_txt_btn;
    private javax.swing.JPanel sidePanel;
    private javax.swing.JCheckBox skin_ckb;
    private javax.swing.JCheckBox skin_updateForm_ckb;
    private javax.swing.JComboBox<String> sort_cb;
    private javax.swing.JTable table;
    private javax.swing.JTable tableDisplay;
    private javax.swing.JScrollPane tableScrollPane;
    private javax.swing.JScrollPane tableScrollPane1;
    private javax.swing.JPanel titlePanel;
    private javax.swing.JLabel title_deleteForm_lb;
    private javax.swing.JLabel title_insertForm_lb;
    private javax.swing.JLabel title_lb;
    private javax.swing.JLabel title_updateForm_lb;
    private javax.swing.JLabel type_deleteForm_lb;
    private javax.swing.JTextField type_deleteForm_txt;
    private javax.swing.JLabel type_lb;
    private javax.swing.JLabel typev_updateForm_lb;
    private javax.swing.JDialog updateForm;
    private javax.swing.JButton updateForm_btn;
    private javax.swing.JPanel updateForm_panel;
    private javax.swing.JButton update_btn;
    private javax.swing.JCheckBox women_ckb;
    private javax.swing.JCheckBox women_updateForm_ckb;
    // End of variables declaration//GEN-END:variables
}
