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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Andri
 */
public class Pengujian extends javax.swing.JPanel {

    private static DefaultTableModel tblK1;
    private static DefaultTableModel tblK2;
    private static DefaultTableModel tblPositif;
    private static DefaultTableModel tblNegatif;
    private static DefaultTableModel tblTermNormalisasi;

    /**
     * Creates new form Data
     */
    public Pengujian() throws SQLException {
        initComponents();
        
        
        String sqlTruncateNormalisasiPositifNegatifKelas = "TRUNCATE tb_normalisasi_alpha_kelas";
        Statement statementTruncateNormalisasiPositifNegatifKelas = (Statement) jdbc.getConnection().createStatement();
        statementTruncateNormalisasiPositifNegatifKelas.executeUpdate(sqlTruncateNormalisasiPositifNegatifKelas);

        String sqlTruncateKPlus = "TRUNCATE tb_k_plus";
        Statement statementTruncateKPlus = (Statement) jdbc.getConnection().createStatement();
        statementTruncateKPlus.executeUpdate(sqlTruncateKPlus);

        String sqlTruncateKMin = "TRUNCATE tb_k_min";
        Statement statementTruncateKMin = (Statement) jdbc.getConnection().createStatement();
        statementTruncateKMin.executeUpdate(sqlTruncateKMin);

        String sqlTruncateSvPositif = "TRUNCATE tb_sv_positif";
        Statement statementTruncateSvPositif = (Statement) jdbc.getConnection().createStatement();
        statementTruncateSvPositif.executeUpdate(sqlTruncateSvPositif);

        String sqlTruncateSvNegatif = "TRUNCATE tb_sv_negatif";
        Statement statementTruncateSvNegatif = (Statement) jdbc.getConnection().createStatement();
        statementTruncateSvNegatif.executeUpdate(sqlTruncateSvNegatif);

        tblTermNormalisasi = new DefaultTableModel();
        TblTermNormalisasi.setModel(tblTermNormalisasi);
        tblTermNormalisasi.addColumn("id");
        tblTermNormalisasi.addColumn("kb+");
        tblTermNormalisasi.addColumn("kk+");
        tblTermNormalisasi.addColumn("ks+");
        tblTermNormalisasi.addColumn("kb-");
        tblTermNormalisasi.addColumn("kk-");
        tblTermNormalisasi.addColumn("ks-");
        tblTermNormalisasi.addColumn("Alpha I");
        tblTermNormalisasi.addColumn("Kelas");

        tblK1 = new DefaultTableModel();
        TblK1.setModel(tblK1);
        tblK1.addColumn("id sv");
        tblK1.addColumn("K(xi,x-)");

        tblK2 = new DefaultTableModel();
        TblK2.setModel(tblK2);
        tblK2.addColumn("id sv");
        tblK2.addColumn("K(xi,x+)");

        tblNegatif = new DefaultTableModel();
        TblNegatif.setModel(tblNegatif);
        tblNegatif.addColumn("id sv");
        tblNegatif.addColumn("Negatif");

        tblPositif = new DefaultTableModel();
        TblPositif.setModel(tblPositif);
        tblPositif.addColumn("id sv");
        tblPositif.addColumn("Positif");

        String sqlAmbilHasilLangkahC = "SELECT * FROM tb_langkah_nol_c_iterasi WHERE iterasi = (SELECT max(iterasi) as iterasiTerakhir FROM `tb_langkah_nol_c_iterasi`)";
        Statement statementAmbilHasilLangkahC = (Statement) jdbc.getConnection().createStatement();
        ResultSet resultAmbilHasilLangkahC = statementAmbilHasilLangkahC.executeQuery(sqlAmbilHasilLangkahC);
        while (resultAmbilHasilLangkahC.next()) {

            String sqlJumlahKolom = "SELECT MAX(id_doc1) AS max FROM tb_kernel_polinomial_degree";
            Statement statementJumlahKolom = (Statement) jdbc.getConnection().createStatement();
            ResultSet resultSetJumlahKolom = statementJumlahKolom.executeQuery(sqlJumlahKolom);
            while (resultSetJumlahKolom.next()) {
                for (int kolom = 3; kolom <= Integer.parseInt(resultSetJumlahKolom.getString("max").toString()) + 2; kolom++) {

                    String sqlKelas = "SELECT * FROM tb_normalisasi, tb_filtering WHERE tb_normalisasi.id = tb_filtering.id_filtering AND tb_filtering.id_filtering = '" + (kolom - 2) + "'";
                    Statement statementKelas = (Statement) jdbc.getConnection().createStatement();
                    ResultSet resultSetKelas = statementKelas.executeQuery(sqlKelas);
                    while (resultSetKelas.next()) {
                        Object[] objectNormalisasi = new Object[9];
                        objectNormalisasi[0] = resultSetKelas.getString("id");
                        objectNormalisasi[1] = resultSetKelas.getString("kbplus");
                        objectNormalisasi[2] = resultSetKelas.getString("kkplus");
                        objectNormalisasi[3] = resultSetKelas.getString("ksplus");
                        objectNormalisasi[4] = resultSetKelas.getString("kbmin");
                        objectNormalisasi[5] = resultSetKelas.getString("kkmin");
                        objectNormalisasi[6] = resultSetKelas.getString("ksmin");
                        objectNormalisasi[7] = resultAmbilHasilLangkahC.getString(kolom);
                        objectNormalisasi[8] = resultSetKelas.getString("kelas_dokumen");
                        tblTermNormalisasi.addRow(objectNormalisasi);

                        String sqlInsertNormalisasiKelasAlpha = "INSERT INTO tb_normalisasi_alpha_kelas("
                                + " kbPlus,"
                                + " kkPlus,"
                                + " ksPlus,"
                                + " kbMin,"
                                + " kkMin,"
                                + " ksMin,"
                                + " alpha_i,"
                                + " kelas)"
                                + " VALUES("
                                + "'" + objectNormalisasi[1] + "',"
                                + "'" + objectNormalisasi[2] + "',"
                                + "'" + objectNormalisasi[3] + "',"
                                + "'" + objectNormalisasi[4] + "',"
                                + "'" + objectNormalisasi[5] + "',"
                                + "'" + objectNormalisasi[6] + "',"
                                + "'" + objectNormalisasi[7] + "',"
                                + "'" + objectNormalisasi[8] + "')";
                        //System.out.println(sqlInsertNormalisasiKelasAlpha);
                        Statement statementInsertNormalisasiKelasAlpha = (Statement) jdbc.getConnection().createStatement();
                        statementInsertNormalisasiKelasAlpha.executeUpdate(sqlInsertNormalisasiKelasAlpha);
                    }
                }
            }
        }

        float positifMaxAlpha, svPositif;

        String sqlSelectMaxPositif = "SELECT * FROM tb_normalisasi_alpha_kelas WHERE alpha_i = (SELECT MAX(alpha_i) AS alphaMaxPositif FROM tb_normalisasi_alpha_kelas WHERE kelas = 1) ";
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

                Object[] objectPositifAlpha = new Object[2];
                objectPositifAlpha[0] = resultSelectAllNormalisasiAlphaPositifNegatif.getString("id");
                objectPositifAlpha[1] = positifMaxAlpha;
                tblK2.addRow(objectPositifAlpha);

                String sqlInsertKPlus = "INSERT INTO tb_k_plus VALUES(null, '" + objectPositifAlpha[1] + "')";
                Statement statementInsertKPlus = (Statement) jdbc.getConnection().createStatement();
                statementInsertKPlus.executeUpdate(sqlInsertKPlus);

                Object[] objectSvPositif = new Object[2];
                objectSvPositif[0] = resultSelectAllNormalisasiAlphaPositifNegatif.getString("id");
                objectSvPositif[1] = svPositif;
                tblPositif.addRow(objectSvPositif);

                String sqlInsertSvPositif = "INSERT INTO tb_sv_positif VALUES(null, '" + objectSvPositif[1] + "')";
                Statement statementSvPositif = (Statement) jdbc.getConnection().createStatement();
                statementSvPositif.executeUpdate(sqlInsertSvPositif);
            }
        }

        float negatifMaxAlpha, svNegatif;

        String sqlSelectMaxNegatif = "SELECT * FROM tb_normalisasi_alpha_kelas WHERE alpha_i = (SELECT MAX(alpha_i) AS alphaMaxPositif FROM tb_normalisasi_alpha_kelas WHERE kelas = -1) ";
        Statement statementSelectMaxNegatif = (Statement) jdbc.getConnection().createStatement();
        ResultSet resultSetSelectMaxNegatif = statementSelectMaxNegatif.executeQuery(sqlSelectMaxNegatif);
        while (resultSetSelectMaxNegatif.next()) {

            String sqlSelectAllNormalisasiAlphaNegatif = "SELECT * FROM tb_normalisasi_alpha_kelas";
            Statement statementSelectAllNormalisasiAlphaNegatif = (Statement) jdbc.getConnection().createStatement();
            ResultSet resultSelectAllNormalisasiAlphaNegatif = statementSelectAllNormalisasiAlphaNegatif.executeQuery(sqlSelectAllNormalisasiAlphaNegatif);
            while (resultSelectAllNormalisasiAlphaNegatif.next()) {
//              =(f1Terpilih*f1giliran+f2Terpilih*f2Giliran+f3Terpilih*f3Giliran+f4Terpilih*f4Giliran+f5Terpilih*f5Giliran+f6Terpilih*f6Giliran+alpha_terpilih*alpha_giliran)^2
// di revisi menjadi =(f1Terpilih*f1giliran+f2Terpilih*f2Giliran+f3Terpilih*f3Giliran+f4Terpilih*f4Giliran+f5Terpilih*f5Giliran+f6Terpilih*f6Giliran)^2
                negatifMaxAlpha = (float) Math.pow(((Float.parseFloat(resultSetSelectMaxNegatif.getString("kbPlus").toString()) * Float.parseFloat(resultSelectAllNormalisasiAlphaNegatif.getString("kbPlus").toString()))
                        + (Float.parseFloat(resultSetSelectMaxNegatif.getString("kkPlus").toString()) * Float.parseFloat(resultSelectAllNormalisasiAlphaNegatif.getString("kkPlus").toString()))
                        + (Float.parseFloat(resultSetSelectMaxNegatif.getString("ksPlus").toString()) * Float.parseFloat(resultSelectAllNormalisasiAlphaNegatif.getString("ksPlus").toString()))
                        + (Float.parseFloat(resultSetSelectMaxNegatif.getString("kbMin").toString()) * Float.parseFloat(resultSelectAllNormalisasiAlphaNegatif.getString("kbMin").toString()))
                        + (Float.parseFloat(resultSetSelectMaxNegatif.getString("kkMin").toString()) * Float.parseFloat(resultSelectAllNormalisasiAlphaNegatif.getString("kkMin").toString()))
                        + (Float.parseFloat(resultSetSelectMaxNegatif.getString("ksMin").toString()) * Float.parseFloat(resultSelectAllNormalisasiAlphaNegatif.getString("ksMin").toString()))), 2);

//              =H205*I205*C214
                svNegatif = negatifMaxAlpha * (Float.parseFloat(resultSelectAllNormalisasiAlphaNegatif.getString("alpha_i").toString())) * (Float.parseFloat(resultSelectAllNormalisasiAlphaNegatif.getString("kelas").toString()));

                Object[] objectNegatifAlpha = new Object[2];
                objectNegatifAlpha[0] = resultSelectAllNormalisasiAlphaNegatif.getString("id");
                objectNegatifAlpha[1] = negatifMaxAlpha;
                tblK1.addRow(objectNegatifAlpha);

                String sqlInsertKMin = "INSERT INTO tb_k_min VALUES(null, '" + objectNegatifAlpha[1] + "')";
                Statement statementInsertKMin = (Statement) jdbc.getConnection().createStatement();
                statementInsertKMin.executeUpdate(sqlInsertKMin);

                Object[] objectSvPositif = new Object[2];
                objectSvPositif[0] = resultSelectAllNormalisasiAlphaNegatif.getString("id");
                objectSvPositif[1] = svNegatif;
                tblNegatif.addRow(objectSvPositif);

                String sqlInsertSvNegatif = "INSERT INTO tb_sv_negatif VALUES(null, '" + objectSvPositif[1] + "')";
                Statement statementSvNegatif = (Statement) jdbc.getConnection().createStatement();
                statementSvNegatif.executeUpdate(sqlInsertSvNegatif);
            }
        }

        String sqlSumPositif = "SELECT SUM(positif) AS total_positif FROM tb_sv_positif";
        Statement statementSumPositif = (Statement) jdbc.getConnection().createStatement();
        ResultSet resultSetSumPositif = statementSumPositif.executeQuery(sqlSumPositif);

        float total_positif, total_negatif, total_b;

        while (resultSetSumPositif.next()) {

            String sqlSumNegatif = "SELECT SUM(negatif) AS total_negatif FROM tb_sv_negatif";
            Statement statementSumNegatif = (Statement) jdbc.getConnection().createStatement();
            ResultSet resultSetSumNegatif = statementSumNegatif.executeQuery(sqlSumNegatif);

            while (resultSetSumNegatif.next()) {
                lblSumPositif.setText(resultSetSumPositif.getString("total_positif"));
                total_positif = Float.parseFloat(resultSetSumPositif.getString("total_positif").toString());

                lblSumNegatif.setText(resultSetSumNegatif.getString("total_negatif"));
                total_negatif = Float.parseFloat(resultSetSumNegatif.getString("total_negatif").toString());
                
                total_b = (float) (-0.5 * (total_positif + total_negatif));
                lblB.setText(" jadi nilai b adalah : " + total_b);
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
        jLabel2 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        TblTermNormalisasi = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        TblK1 = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        TblK2 = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        TblPositif = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        TblNegatif = new javax.swing.JTable();
        lblSumPositif = new javax.swing.JLabel();
        lblSumNegatif = new javax.swing.JLabel();
        lblB = new javax.swing.JLabel();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jButton1 = new javax.swing.JButton();

        jInternalFrame1.setVisible(true);

        jLabel2.setText("TERM FREKUENSI");

        TblTermNormalisasi.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane8.setViewportView(TblTermNormalisasi);

        jLabel5.setText("Testing Machine");

        jLabel1.setText("K(xi,x-)");

        TblK1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane7.setViewportView(TblK1);

        jLabel3.setText("K(xi,x+)");

        TblK2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane9.setViewportView(TblK2);

        jLabel8.setText("positif");

        TblPositif.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane10.setViewportView(TblPositif);

        jLabel6.setText("negatif");

        TblNegatif.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane11.setViewportView(TblNegatif);

        lblSumPositif.setText("sigma");

        lblSumNegatif.setText("sigma");

        lblB.setText("b");

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 754, Short.MAX_VALUE)
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 454, Short.MAX_VALUE)
        );

        jButton1.setText("TEST");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(14, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane8)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addGap(245, 245, 245)
                                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addGap(313, 313, 313)
                                            .addComponent(jLabel5))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                    .addComponent(lblSumPositif, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(lblB))
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                            .addGap(59, 59, 59)
                                                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                            .addGap(21, 21, 21)
                                                            .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                            .addGap(85, 85, 85)
                                                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                            .addGap(71, 71, 71)
                                                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                    .addGap(85, 85, 85)
                                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                    .addGap(21, 21, 21)
                                                    .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(lblSumNegatif)
                                                    .addGap(8, 8, 8))))))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1)
                                .addComponent(jLabel3))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(22, 22, 22)
                            .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel6)
                                .addComponent(jLabel8))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(22, 22, 22)
                            .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblSumPositif)
                        .addComponent(lblB))
                    .addComponent(lblSumNegatif))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38))
        );

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1))
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        try {
            jDesktopPane1.removeAll();
            Testing ab = new Testing();
            ab.setBounds(0, 0, jDesktopPane1.getWidth(), jDesktopPane1.getHeight());
            jDesktopPane1.add(ab);
            ab.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(Pengujian.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TblK1;
    private javax.swing.JTable TblK2;
    private javax.swing.JTable TblNegatif;
    private javax.swing.JTable TblPositif;
    private javax.swing.JTable TblTermNormalisasi;
    private javax.swing.JButton jButton1;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JLabel lblB;
    private javax.swing.JLabel lblSumNegatif;
    private javax.swing.JLabel lblSumPositif;
    // End of variables declaration//GEN-END:variables
}
