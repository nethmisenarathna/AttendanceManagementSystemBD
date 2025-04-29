
package forms;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import dao.ConnectionProvider;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.Timer;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import utility.BDUtility;


/**
 *
 * @author N.Wijesundara
 */



public class MarkAttendance extends javax.swing.JFrame implements Runnable, ThreadFactory {
    private JLabel backgroundLabel;


   private WebcamPanel panel=null;
   private Webcam webcam =null;
   private ExecutorService executor = Executors.newSingleThreadExecutor(this);   
   private volatile boolean running =true;
   
    
    
    public MarkAttendance() {
        initComponents();
        
        BDUtility.setImage(this, "images/abc1.jpg", 5609, 683);
       this.getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.BLACK));
       startClock(); 
       initWebcam();


    }
    private void startClock() {
    Timer timer = new Timer(1000, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd             HH:mm:ss");
            lblTime.setText(sdf.format(new Date()));
        }
    });
    timer.start();
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnExit = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        webCamPanel = new javax.swing.JPanel();
        lblImage = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblTime = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblCheckinCheckout = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1366, 768));
        setUndecorated(true);
        setSize(new java.awt.Dimension(322, 286));

        btnExit.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnExit.setText("X");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Mark Attendance ");

        webCamPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Date");

        lblTime.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lblTime.setText("Time");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Time");

        lblCheckinCheckout.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        lblName.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(webCamPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4746, 4746, 4746)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(109, 109, 109)
                                .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4853, 4853, 4853)
                        .addComponent(lblCheckinCheckout, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(336, 336, 336)
                        .addComponent(jLabel3))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(95, 95, 95)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGap(1055, 1055, 1055)
                            .addComponent(btnExit)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(834, 834, 834)
                        .addComponent(lblTime, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(514, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(64, 64, 64)
                .addComponent(webCamPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel4))
                .addGap(60, 60, 60)
                .addComponent(lblTime, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(250, 250, 250)
                        .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addComponent(lblCheckinCheckout, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnExitActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MarkAttendance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MarkAttendance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MarkAttendance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MarkAttendance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MarkAttendance().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExit;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel lblCheckinCheckout;
    private javax.swing.JLabel lblImage;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblTime;
    private javax.swing.JPanel webCamPanel;
    // End of variables declaration//GEN-END:variables
    
    Map<String, String> resultMap = new HashMap<String, String>();
    
    @Override
    public void run() {
        
        do {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {}

            try {
                Result result = null;
                BufferedImage image = null;

                if (webcam.isOpen()) {
                    if ((image = webcam.getImage()) == null) {
                        continue;
                    }
                }

                LuminanceSource source = new BufferedImageLuminanceSource(image);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

                try {
                    result = new MultiFormatReader().decode(bitmap);
                } catch (NotFoundException ex) {}

                if (result != null) {
                    String jsonString = result.getText();
                    Gson gson = new Gson();
                    java.lang.reflect.Type type = new TypeToken<Map<String, String>>() {}.getType();
                    resultMap = gson.fromJson(jsonString, type);

                    String finalPath = BDUtility.getPath("images\\" + resultMap.get("email") + ".jpg");
                    CircularImageFrame(finalPath);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } while (running);
    }

    @Override
    public Thread newThread(Runnable r) {
         Thread t =new Thread(r, "My Thread");
         t.setDaemon(true);
         return t ;
    }
    private void stopWebcam(){
        if(webcam!=null && webcam.isOpen()){
            webcam.close();
        }
    }

   private void initWebcam() {
    webcam = Webcam.getDefault();
    if (webcam != null) {
        Dimension[] resolutions = webcam.getCustomViewSizes();
        Dimension selectedResolution;

        if (resolutions.length > 0) {
            selectedResolution = resolutions[resolutions.length - 1];
        } else {
            // 🔧 fallback if no custom sizes are available
            selectedResolution = new Dimension(640, 480);
        }

        if (webcam.isOpen()) {
            webcam.close();
        }

        webcam.setViewSize(selectedResolution); // ✅ Use setViewSize instead of setCustomViewSizes
        webcam.open();

        panel = new WebcamPanel(webcam);
        panel.setPreferredSize(selectedResolution);
        panel.setFPSDisplayed(true);

        webCamPanel.add(panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 689, 518));

        executor.execute(this);
        executor.shutdown(); // Optional
    } else {
        System.out.println("Issue with the webcam");
    }
}

  private void CircularImageFrame(String finalPath) {
    try {
        Connection con = ConnectionProvider.getCon();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM userdetails WHERE email = ?");
        ps.setString(1, resultMap.get("email"));
        ResultSet rs = ps.executeQuery();  // ✅ Used PreparedStatement to avoid SQL injection

        if (!rs.next()) {
            showPopUpForCertainDuration("User is not Registered", "Invalid Qr", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // ✅ Declared imagee
        BufferedImage imagee = null;
        File imageFile = new File(finalPath);  // ✅ Used finalPath instead of undefined imagePath

        if (imageFile.exists()) {
            try {
                imagee = ImageIO.read(new File(finalPath));  // ✅ Corrected from imagepath
                imagee = createCircularImage(imagee);
                ImageIcon icon = new ImageIcon(imagee);
                lblImage.setIcon(icon);  // ✅ Used the icon properly
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            BufferedImage imageeee = new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = imageeee.createGraphics();

            g2d.setColor(Color.BLACK);  // ✅ Fixed: Color.BLACK instead of BLACK
            g2d.fillOval(25, 25, 250, 250);

            g2d.setFont(new Font("Serif", Font.BOLD, 250));
            g2d.setColor(Color.WHITE);  // ✅ Fixed: Color.WHITE instead of WHITE
            g2d.drawString(String.valueOf(resultMap.get("name").charAt(0)).toUpperCase(), 75, 225);
            g2d.dispose();

            ImageIcon imageIconn = new ImageIcon(imageeee);
            lblImage.setIcon(imageIconn);

            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.pack();
            this.setLocationRelativeTo(null);  // ✅ Fixed: setLocation(null) to setLocationRelativeTo(null)
            this.setVisible(true);
        }

        lblName.setHorizontalAlignment(JLabel.CENTER);  // ✅ Fixed: JLabel (was Jlabel)
        lblName.setText(resultMap.get("name"));

        if (!CheckInCheckOut()) {
            return;
        }

    } catch (Exception ex) {
        ex.printStackTrace();
    }
}

private boolean CheckInCheckOut() throws HeadlessException, SQLException {
    String popUpHeader = null;
    String popUpMessage = null;
    Color color = null;

    Connection con = ConnectionProvider.getCon();
    Statement st = con.createStatement();

    LocalDate currentDate = LocalDate.now();
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    LocalDateTime currentDateTime = LocalDateTime.now();
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // ✅ Fixed: removed space in SQL, added PreparedStatement
    PreparedStatement ps = con.prepareStatement(
        "SELECT * FROM userattendance WHERE date = ? AND userid = ?");
    ps.setString(1, currentDate.format(dateFormatter));
    ps.setInt(2, Integer.parseInt(resultMap.get("id")));
    ResultSet rs = ps.executeQuery();

    if (rs.next()) {
        String checkOutDateTime = rs.getString(4);
        if (checkOutDateTime != null) {
            popUpMessage = "Already Checked out for the Day.";
            popUpHeader = "Invalid Checkout";
            showPopUpForCertainDuration(popUpMessage, popUpHeader, JOptionPane.ERROR_MESSAGE);
            return false;
        }
        String checkInDateTime = rs.getString(3);
        LocalDateTime CheckInLocalDateTime = LocalDateTime.parse(checkInDateTime, dateTimeFormatter);
        Duration duration = Duration.between(CheckInLocalDateTime, currentDateTime);

        long hours = duration.toHours();
        long minutes = duration.minusHours(hours).toMinutes();
        long seconds = duration.minusHours(hours).minusMinutes(minutes).getSeconds();

        if (!(hours > 0 || (hours == 0 && minutes >= 5))) {
            long remainingMinutes = 4 - minutes;
            long remainingSeconds = 60 - seconds;

            popUpMessage = String.format(
                "Your work duration is less than 5 minutes.\nYou can check out after: %d minutes and %d seconds",
                remainingMinutes, remainingSeconds);
            popUpHeader = "Duration warning";

            showPopUpForCertainDuration(popUpMessage, popUpHeader, JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // ✅ UPDATE attendance
        PreparedStatement updateStmt = con.prepareStatement(
            "UPDATE userattendance SET checkout = ?, workduration = ? WHERE date = ? AND userid = ?");
        updateStmt.setString(1, currentDateTime.format(dateTimeFormatter));
        updateStmt.setString(2, hours + " Hours and " + minutes + " Minutes");
        updateStmt.setString(3, currentDate.format(dateFormatter));
        updateStmt.setInt(4, Integer.parseInt(resultMap.get("id")));

        updateStmt.executeUpdate();

        popUpHeader = "CheckOut";
        popUpMessage = "Checked out at " + currentDateTime.format(dateTimeFormatter)
            + "\nWork duration: " + hours + " Hours and " + minutes + " Minutes";
        color = Color.RED;

    } else {
        // ✅ INSERT new attendance record
        PreparedStatement insertStmt = con.prepareStatement(
            "INSERT INTO userattendance (userid, date, checkin) VALUES (?, ?, ?)");
        insertStmt.setInt(1, Integer.parseInt(resultMap.get("id")));
        insertStmt.setString(2, currentDate.format(dateFormatter));
        insertStmt.setString(3, currentDateTime.format(dateTimeFormatter));

        insertStmt.executeUpdate();

        popUpHeader = "CheckIn";
        popUpMessage = "Checked in at " + currentDateTime.format(dateTimeFormatter);
        color = Color.GREEN;
    }

    lblCheckinCheckout.setHorizontalAlignment(JLabel.CENTER);
    lblCheckinCheckout.setText(popUpHeader);
    lblCheckinCheckout.setForeground(color);
    lblCheckinCheckout.setBackground(Color.DARK_GRAY);
    lblCheckinCheckout.setOpaque(true);

    showPopUpForCertainDuration(popUpMessage, popUpHeader, JOptionPane.INFORMATION_MESSAGE);
    return true;
}

private void showPopUpForCertainDuration(String popUpMessage, String popUpHeader, Integer iconId)
        throws HeadlessException {
    final JOptionPane optionPane = new JOptionPane(popUpMessage, iconId);
    final JDialog dialog = optionPane.createDialog(popUpHeader);

    Timer timer = new Timer(5000, e -> {
        dialog.dispose();
        clearUserDetails();
    });
    timer.setRepeats(false);
    timer.start();
    dialog.setVisible(true);
}

private void clearUserDetails() {
    lblCheckinCheckout.setText("");
    lblCheckinCheckout.setForeground(null);
    lblCheckinCheckout.setBackground(null);
    lblCheckinCheckout.setOpaque(false);
    lblName.setText("");
    lblImage.setIcon(null);
}

// ✅ Fixed typo in paint method
@Override
public void paint(Graphics g) {
    super.paint(g);
    //if (imagee != null) {
       // g.drawImage(imagee, 0, 0, null);  // ✅ Fixed missing parenthesis
    //}
}

private BufferedImage createCircularImage(BufferedImage image) {
    int diameter = 285;
    BufferedImage resizedImage = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2 = resizedImage.createGraphics();
    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    g2.drawImage(image, 0, 0, diameter, diameter, null);
    g2.dispose();

    BufferedImage circularImage = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
    g2 = circularImage.createGraphics();
    Ellipse2D.Double circle = new Ellipse2D.Double(0, 0, diameter, diameter);
    g2.setClip(circle);
    g2.drawImage(resizedImage, 0, 0, null);
    g2.dispose();

    return circularImage;
}

     
    
}
