
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author Javier Andres Herrera Manjarrez
 */
public class TablaDeEscuelas extends javax.swing.JFrame {
    private JPopupMenu emergente;
    private JMenuItem actualizar, eliminar, agregar, recargar, buscar;
    private JSeparator s1,s2,s3, s4;
    /**
     * Creates new form TablaDeEscuelas
     * [ WARN] (AWT-EventQueue-0) Error: 
     * 1062-23000: Duplicate entry 'primera@gmail.com' for key 'escuelas_un'
     */
//    private ventanaCrudEscuela e;
    private Connection conexion;
    private Statement sentencia;
    public TablaDeEscuelas() {
        emergente = new JPopupMenu();
        actualizar = new JMenuItem();eliminar = new JMenuItem();
        agregar = new JMenuItem();s1= new JSeparator();
        s2 = new JSeparator();s3= new JSeparator();recargar = new JMenuItem();
        buscar =new JMenuItem();s4 =new JSeparator();
        
        actualizar.setText("Actualizar");
        eliminar.setText("Eliminar");
        agregar.setText("Agregar");
        recargar.setText("Refrescar");
        buscar.setText("Buscar");
                
                
        actualizar.setForeground(Color.black);
        eliminar.setForeground(Color.red);
        agregar.setForeground(Color.black);
        recargar.setForeground(Color.green);
        buscar.setForeground(Color.black);
        
        actualizar.setFont(new Font("Arial",1,14));
        eliminar.setFont(new Font("Arial",1,14));
        agregar.setFont(new Font("Arial",1,14));
        recargar.setFont(new Font("Arial",1,14));
        buscar.setFont(new Font("Arial",1,14));
        
        emergente.add(agregar);
        emergente.add(s1);
        emergente.add(eliminar);
        emergente.add(s2);
        emergente.add(actualizar);
        emergente.add(s3);
        emergente.add(recargar);
        emergente.add(s4);
        emergente.add(buscar);
        
        actualizar.addActionListener((ActionEvent eve) ->{
            int confir = JOptionPane.showConfirmDialog(null,"Seguro de su decion", "confirme",
                    JOptionPane.YES_NO_OPTION, JOptionPane.DEFAULT_OPTION);
            if (confir == JOptionPane.YES_OPTION) {
                ventanaCrudEscuela e = new ventanaCrudEscuela(null, true);
                e.activos(false, true, false, true, true);
                e.setSize(520,600);
                e.setVisible(true);
            }
        });
        
        eliminar.addActionListener((ActionEvent eve) ->{
            int confir = JOptionPane.showConfirmDialog(null,"Seguro de su decion", "confirme",
                    JOptionPane.YES_NO_OPTION, JOptionPane.DEFAULT_OPTION);
            if (confir == JOptionPane.YES_OPTION) {
                ventanaCrudEscuela e = new ventanaCrudEscuela(null, true);
                e.activos(false, true, true, false, true);
                e.setSize(520,600);
                e.setVisible(true);
            }
        });
        
        agregar.addActionListener((ActionEvent eve) ->{
            int confir = JOptionPane.showConfirmDialog(null,"Seguro de su decion", "confirme",
                    JOptionPane.YES_NO_OPTION, JOptionPane.DEFAULT_OPTION);
            if (confir == JOptionPane.YES_OPTION) {
                ventanaCrudEscuela e = new ventanaCrudEscuela(null, true);
                e.activos(true, false, false, false, false);
                e.setSize(520,600);
                e.setVisible(true);
            }
        });
        
        
        recargar.addActionListener((ActionEvent eve) ->{
            int confir = JOptionPane.showConfirmDialog(null,"Seguro de su decion", "confirme",
                    JOptionPane.YES_NO_OPTION, JOptionPane.DEFAULT_OPTION);
            if (confir == JOptionPane.YES_OPTION) {
                tablaDeContenido.removeAll();
                tablaDeContenido = tabla();
                tablaDeContenido.setComponentPopupMenu(emergente);
                jScrollPane1.setViewportView(tablaDeContenido);
                jScrollPane1.setComponentPopupMenu(emergente);
            }
        });
        
        buscar.addActionListener((ActionEvent eve) ->{
            int confir = JOptionPane.showConfirmDialog(null,"Seguro de su decion", "confirme",
                    JOptionPane.YES_NO_OPTION, JOptionPane.DEFAULT_OPTION);
            if (confir == JOptionPane.YES_OPTION) {
                ventanaCrudEscuela e = new ventanaCrudEscuela(null, true);
                e.activos(false, true, false, false, true);
                e.setSize(520,600);
                e.setVisible(true);
            }
        });
        
        initComponents();
        getContentPane().setBackground(new Color(51, 255, 51));
        PrepararBasedatos();
        tablaDeContenido = tabla();
        tablaDeContenido.setComponentPopupMenu(emergente);
        jScrollPane1.setViewportView(tablaDeContenido);
        jScrollPane1.setComponentPopupMenu(emergente);
        
    }
    
    
    
    private void PrepararBasedatos() {
        try {
            String DSN = "jdbc:mariadb://localhost:3306/escuela?";
            String user = "user=root&";
            String password = "password=root";
            conexion = DriverManager.getConnection(DSN + user + password);
            JOptionPane.showMessageDialog(null, "Conexion exitosa");
        } catch (SQLException ded) {
            JOptionPane.showMessageDialog(null, "Error al realizar la conexión", "error", JOptionPane.ERROR_MESSAGE);
        }
        try {
            sentencia = conexion.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al realizar sentencia en la BD", "error", JOptionPane.ERROR_MESSAGE);
        }

    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnCerrera = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaDeContenido = new javax.swing.JTable();
        Report = new javax.swing.JButton();
        mensaje = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(51, 255, 51));

        btnCerrera.setBackground(new java.awt.Color(255, 255, 255));
        btnCerrera.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCerrera.setForeground(new java.awt.Color(255, 0, 0));
        btnCerrera.setText("Cerrar");
        btnCerrera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerreraActionPerformed(evt);
            }
        });

        tablaDeContenido.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tablaDeContenido);

        Report.setBackground(new java.awt.Color(255, 255, 255));
        Report.setForeground(new java.awt.Color(255, 0, 0));
        Report.setText("Reporte");
        Report.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReportActionPerformed(evt);
            }
        });

        mensaje.setBackground(new java.awt.Color(255, 255, 255));
        mensaje.setForeground(new java.awt.Color(255, 0, 0));
        mensaje.setText("Correo");
        mensaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mensajeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(btnCerrera)
                .addGap(165, 165, 165)
                .addComponent(Report)
                .addGap(89, 89, 89)
                .addComponent(mensaje)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 778, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCerrera)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(Report)
                        .addComponent(mensaje)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCerreraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerreraActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnCerreraActionPerformed

    private void ReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReportActionPerformed
        try {
            JasperReport reporte = null;
            String path ="src"+File.separator+File.separator+"report.jasper";
            
            // para que pueda cargar el archivo .jasper
            reporte = (JasperReport) JRLoader.loadObjectFromFile(path);
            
            // llenado del reporte
            JasperPrint jprint = JasperFillManager.fillReport(reporte,null,conexion);
            
            
            // vista del reporte
            JasperViewer view = new JasperViewer(jprint, false);
            view.setTitle("Reorte de escuelas");
            // Para que pueda cerrar este reporte
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            
            // hacer visible el reporte
           
            view.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(TablaDeEscuelas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_ReportActionPerformed

    private void mensajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mensajeActionPerformed
        Email m = new Email(this, true);
        m.setVisible(true);
               
    }//GEN-LAST:event_mensajeActionPerformed

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
                    UIManager.put("nimbusBlueGrey", new Color(227, 226, 237));
                    UIManager.put("control", new Color(246, 245, 247)); 
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TablaDeEscuelas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TablaDeEscuelas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TablaDeEscuelas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TablaDeEscuelas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TablaDeEscuelas().setVisible(true);
            }
        });
    }
    public JTable tabla() {

        JTable tabla = new JTable();
        DefaultTableModel modelo = new DefaultTableModel() {
            //  para que no se pueda editar la tabla
            @Override
            public boolean isCellEditable(int row, int column) {

                return false;
            }

        };

        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Fecha");
        modelo.addColumn("cantidad de alumnos");
        modelo.addColumn("Area de la escuela");
        modelo.addColumn("E-mail");
        modelo.addColumn("Telefono");
        modelo.addColumn("Direccion");

        String[] datos = new String[8];
        try {
            ResultSet r = sentencia.executeQuery("select * from escuelas order by nombre");
            while (r.next()) {
                datos[0] = r.getString("id");
                datos[1] = r.getString("nombre");
                datos[2] = r.getString("fecha");
                datos[3] = r.getString("cantidadalumnos");
                datos[4] = r.getString("area");
                datos[5] = r.getString("email");
                datos[6] = r.getString("telefono");
                datos[7] = r.getString("direccion");
                
                modelo.addRow(datos);
            }

        } catch (SQLException e) {

        }
//        if(modelo.getRowCount() < 10){
//            String mm[] = new String[8];
//            for(int g =0; g <=5; g++ ){
//                modelo.addRow(mm);
//            }
//        }

        int val[] = {200, 200, 200, 200, 200, 200, 200,200};

        //tabla.setGridColor(new Color(0,0,255));
        tabla.setForeground(new Color(0, 0, 0));
        tabla.setFont(new Font("calibri", 2, 16));

        tabla.setModel(modelo);

        tabla.setRowHeight(25);

        tabla.getTableHeader().setFont(new Font("Calibri", 2, 16));
        tabla.getTableHeader().setForeground(new Color(255, 255, 255));

        // apagar el auto redimencionaminto en JTable
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        // colocar un escroll al momento que las filas pasen el  tamaño del JTable
        tabla.setAutoscrolls(true);

        //   establese el ancho de las columnas
        for (int p = 0; p < 8; p++) {
            tabla.getColumnModel().getColumn(p).setPreferredWidth(val[p]);
        }
        return tabla;
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Report;
    private javax.swing.JButton btnCerrera;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton mensaje;
    private javax.swing.JTable tablaDeContenido;
    // End of variables declaration//GEN-END:variables
}
