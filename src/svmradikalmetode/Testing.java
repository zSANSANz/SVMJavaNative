/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package svmradikalmetode;

import analisissentimenradikalmetode.utility.jdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Abd-Allah
 */
public class Testing extends javax.swing.JPanel {

    private static DefaultTableModel tblTesting;
    private static DefaultTableModel tblSigma;

    /**
     * Creates new form Testing
     */
    public Testing() throws SQLException {
        initComponents();
        
        tblTesting = new DefaultTableModel();
        TblTesting.setModel(tblTesting);
        tblTesting.addColumn("Id sv");
        tblTesting.addColumn("Testing ke N atau Id Doc");
        tblTesting.addColumn("k");
        tblTesting.addColumn("Alpha*y*k");

        tblSigma = new DefaultTableModel();
        TblSigma.setModel(tblSigma);
        tblSigma.addColumn("Id Doc");
        tblSigma.addColumn("Sigma");
        tblSigma.addColumn("f(x)");

        String sqlTruncateNormalisasiPositifNegatifKelas = "TRUNCATE tb_testing";
        Statement statementTruncateNormalisasiPositifNegatifKelas = (Statement) jdbc.getConnection().createStatement();
        statementTruncateNormalisasiPositifNegatifKelas.executeUpdate(sqlTruncateNormalisasiPositifNegatifKelas);

        float positifMaxAlpha, svPositif;

        String sqlSelectMaxPositif = "SELECT * FROM tb_normalisasi_alpha_kelas";
        Statement statementSelectMaxPositif = (Statement) jdbc.getConnection().createStatement();
        ResultSet resultSetSelectMaxPositif = statementSelectMaxPositif.executeQuery(sqlSelectMaxPositif);
        while (resultSetSelectMaxPositif.next()) {

            String sqlSelectAllNormalisasiAlphaPositifNegatif = "SELECT * FROM tb_normalisasi_alpha_kelas";
            Statement statementSelectAllNormalisasiAlphaPositifNegatif = (Statement) jdbc.getConnection().createStatement();
            ResultSet resultSelectAllNormalisasiAlphaPositifNegatif = statementSelectAllNormalisasiAlphaPositifNegatif.executeQuery(sqlSelectAllNormalisasiAlphaPositifNegatif);
            while (resultSelectAllNormalisasiAlphaPositifNegatif.next()) {
//              =(f1Terpilih*f1giliran+f2Terpilih*f2Giliran+f3Terpilih*f3Giliran+f4Terpilih*f4Giliran+f5Terpilih*f5Giliran+f6Terpilih*f6Giliran+alpha_terpilih*alpha_giliran)^2
                positifMaxAlpha = (float) Math.pow(((Float.parseFloat(resultSetSelectMaxPositif.getString("kbPlus").toString()) * Float.parseFloat(resultSelectAllNormalisasiAlphaPositifNegatif.getString("kbPlus").toString()))
                        + (Float.parseFloat(resultSetSelectMaxPositif.getString("kkPlus").toString()) * Float.parseFloat(resultSelectAllNormalisasiAlphaPositifNegatif.getString("kkPlus").toString()))
                        + (Float.parseFloat(resultSetSelectMaxPositif.getString("ksPlus").toString()) * Float.parseFloat(resultSelectAllNormalisasiAlphaPositifNegatif.getString("ksPlus").toString()))
                        + (Float.parseFloat(resultSetSelectMaxPositif.getString("kbMin").toString()) * Float.parseFloat(resultSelectAllNormalisasiAlphaPositifNegatif.getString("kbMin").toString()))
                        + (Float.parseFloat(resultSetSelectMaxPositif.getString("kkMin").toString()) * Float.parseFloat(resultSelectAllNormalisasiAlphaPositifNegatif.getString("kkMin").toString()))
                        + (Float.parseFloat(resultSetSelectMaxPositif.getString("ksMin").toString()) * Float.parseFloat(resultSelectAllNormalisasiAlphaPositifNegatif.getString("ksMin").toString()))), 2);
//              =H205*I205*C214
                svPositif = positifMaxAlpha * (Float.parseFloat(resultSelectAllNormalisasiAlphaPositifNegatif.getString("alpha_i").toString())) * (Float.parseFloat(resultSelectAllNormalisasiAlphaPositifNegatif.getString("kelas").toString()));

                Object[] objectPositifAlpha = new Object[4];
                objectPositifAlpha[0] = resultSelectAllNormalisasiAlphaPositifNegatif.getString("id");
                objectPositifAlpha[1] = resultSetSelectMaxPositif.getString("id");
                objectPositifAlpha[2] = positifMaxAlpha;
                objectPositifAlpha[3] = svPositif;
                tblTesting.addRow(objectPositifAlpha);

                String sqlInsertKPlus = "INSERT INTO tb_testing VALUES(null, '" + objectPositifAlpha[1] + "', '" + objectPositifAlpha[2] + "', '" + objectPositifAlpha[3] + "')";
                Statement statementInsertKPlus = (Statement) jdbc.getConnection().createStatement();
                statementInsertKPlus.executeUpdate(sqlInsertKPlus);
            }
        }

        float total_positif, total_negatif, total_b;

        String sqlJumlahDok = "SELECT MAX(id) AS max FROM tb_normalisasi_alpha_kelas";
        Statement statementJumlahDok = (Statement) jdbc.getConnection().createStatement();
        ResultSet resultSetJumlahDok = statementJumlahDok.executeQuery(sqlJumlahDok);
        while (resultSetJumlahDok.next()) {
            for (int kolom = 1; kolom <= Integer.parseInt(resultSetJumlahDok.getString("max").toString()); kolom++) {

                String sqlSumPositif = "SELECT SUM(positif) AS total_positif FROM tb_sv_positif";
                Statement statementSumPositif = (Statement) jdbc.getConnection().createStatement();
                ResultSet resultSetSumPositif = statementSumPositif.executeQuery(sqlSumPositif);

                while (resultSetSumPositif.next()) {

                    String sqlSumNegatif = "SELECT SUM(negatif) AS total_negatif FROM tb_sv_negatif ";
                    Statement statementSumNegatif = (Statement) jdbc.getConnection().createStatement();
                    ResultSet resultSetSumNegatif = statementSumNegatif.executeQuery(sqlSumNegatif);

                    while (resultSetSumNegatif.next()) {
                        String sqlSumTesting = "SELECT SUM(sigma) AS total_sigma FROM tb_testing WHERE id_doc = '" + kolom + "'";
                        Statement statementSumTesting = (Statement) jdbc.getConnection().createStatement();
                        ResultSet resultSetSumTesting = statementSumTesting.executeQuery(sqlSumTesting);

                        while (resultSetSumTesting.next()) {
                            Object[] objectTesting = new Object[3];
                            objectTesting[0] = kolom;
                            objectTesting[1] = resultSetSumTesting.getString("total_sigma");

                            total_positif = Float.parseFloat(resultSetSumPositif.getString("total_positif").toString());

                            total_negatif = Float.parseFloat(resultSetSumNegatif.getString("total_negatif").toString());

                            total_b = (float) (-0.5 * (total_positif + total_negatif));

                            objectTesting[2] = (Float.parseFloat(resultSetSumTesting.getString("total_sigma").toString())) + total_b;

                            tblSigma.addRow(objectTesting);
                        }
                    }
                }
            }
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

        jInternalFrame1 = new javax.swing.JInternalFrame();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane12 = new javax.swing.JScrollPane();
        TblTesting = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane13 = new javax.swing.JScrollPane();
        TblSigma = new javax.swing.JTable();

        jInternalFrame1.setVisible(true);

        jLabel4.setText("TESTING");

        TblTesting.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane12.setViewportView(TblTesting);

        jLabel7.setText("Sigma dan F(x)");

        TblSigma.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane13.setViewportView(TblSigma);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(320, 320, 320)
                                .addComponent(jLabel7))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(342, 342, 342)
                                .addComponent(jLabel4)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane12)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 725, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(72, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 507, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jInternalFrame1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jInternalFrame1)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TblSigma;
    private javax.swing.JTable TblTesting;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    // End of variables declaration//GEN-END:variables
}
