/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package svmradikalmetode;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;
import svmradikalmetode.utility.jdbc;

/**
 *
 * @author Abd-Allah
 */
public class Iterasi extends javax.swing.JPanel {

    private static DefaultTableModel tblLangkahA;
    private static DefaultTableModel tblLangkahB;
    private static DefaultTableModel tblLangkahB1;
    private static DefaultTableModel tblLangkahC;

    /**
     * Creates new form Iterasi
     */
    public Iterasi() throws SQLException {
        initComponents();

        Statement st = (Statement) jdbc.getConnection().createStatement();

        //TRUNCATE dan Siapkan semua tabel
        //mulai metode untuk perhitungan matrix hessian
        //hapus dulu tabel matrix hessian
        String sqlClearColumnLangkahNolA = "DROP TABLE tb_langkah_nol_a_iterasi";
        Statement stClearColumnLangkahNolA = (Statement) jdbc.getConnection().createStatement();
        stClearColumnLangkahNolA.executeUpdate(sqlClearColumnLangkahNolA);

        String sqlCreateLangkahNolA = "CREATE TABLE tb_langkah_nol_a_iterasi ("
                + "ID INT AUTO_INCREMENT,"
                + " id_doc int,"
                + " iterasi int,"
                + " PRIMARY KEY (ID))";
        Statement stCreateLangkahNolA = (Statement) jdbc.getConnection().createStatement();
        stCreateLangkahNolA.executeUpdate(sqlCreateLangkahNolA);

        String sqlCountKernel3 = "SELECT MAX(id_doc1) AS max FROM tb_kernel_polinomial_degree";
        Statement stCountKernel3 = (Statement) jdbc.getConnection().createStatement();
        ResultSet rsCountKernel3 = stCountKernel3.executeQuery(sqlCountKernel3);

        //System.out.println(sqlCountKernel);
        while (rsCountKernel3.next()) {
            //System.out.println(rsCountKernel.getString("max"));

            for (int cc = 1; cc <= Integer.parseInt(rsCountKernel3.getString("max").toString()); cc++) {
                String sqlCreateColumnKernel1 = "ALTER TABLE tb_langkah_nol_a_iterasi ADD `" + cc + "` FLOAT";
                //System.out.println(sqlCreateColumnKernel1);
                Statement stCreateColumnKernel1 = (Statement) jdbc.getConnection().createStatement();
                stCreateColumnKernel1.executeUpdate(sqlCreateColumnKernel1);
            }
        }

        //mulai metode untuk perhitungan matrix hessian
        //hapus dulu tabel matrix hessian
        String sqlClearColumnLangkahNolB = "DROP TABLE tb_langkah_nol_b_iterasi";
        Statement stClearColumnLangkahNolB = (Statement) jdbc.getConnection().createStatement();
        stClearColumnLangkahNolB.executeUpdate(sqlClearColumnLangkahNolB);

        String sqlCreateLangkahNolB = "CREATE TABLE tb_langkah_nol_b_iterasi ("
                + "ID INT AUTO_INCREMENT,"
                + " iterasi int,"
                + " PRIMARY KEY (ID))";
        Statement stCreateLangkahNolB = (Statement) jdbc.getConnection().createStatement();
        stCreateLangkahNolB.executeUpdate(sqlCreateLangkahNolB);

        String sqlCountKernel31 = "SELECT MAX(id_doc1) AS max FROM tb_kernel_polinomial_degree";
        Statement stCountKernel31 = (Statement) jdbc.getConnection().createStatement();
        ResultSet rsCountKernel31 = stCountKernel31.executeQuery(sqlCountKernel31);

        while (rsCountKernel31.next()) {
            for (int jumlah = 1; jumlah <= Integer.parseInt(rsCountKernel31.getString("max").toString()); jumlah++) {
                String sqlCreateColumnKernel11 = "ALTER TABLE tb_langkah_nol_b_iterasi ADD `" + jumlah + "` FLOAT;";
                //System.out.println(sqlCreateColumnKernel1);
                Statement stCreateColumnKernel11 = (Statement) jdbc.getConnection().createStatement();
                stCreateColumnKernel11.executeUpdate(sqlCreateColumnKernel11);
            }
        }

        //membuat tabel tb_langkah_nol_b_2
        String sqlClearColumnLangkahNolB2 = "DROP TABLE tb_langkah_nol_b_1_iterasi";
        Statement stClearColumnLangkahNolB2 = (Statement) jdbc.getConnection().createStatement();
        stClearColumnLangkahNolB2.executeUpdate(sqlClearColumnLangkahNolB2);

        String sqlCreateLangkahNolB2 = "CREATE TABLE tb_langkah_nol_b_1_iterasi ("
                + "ID INT AUTO_INCREMENT,"
                + " iterasi int,"
                + " PRIMARY KEY (ID))";

        Statement stCreateLangkahNolB2 = (Statement) jdbc.getConnection().createStatement();
        stCreateLangkahNolB2.executeUpdate(sqlCreateLangkahNolB2);

        String sqlCountKernel3121 = "SELECT MAX(id_doc1) AS max FROM tb_kernel_polinomial_degree";
        Statement stCountKernel3121 = (Statement) jdbc.getConnection().createStatement();
        ResultSet rsCountKernel3121 = stCountKernel3121.executeQuery(sqlCountKernel3121);

        while (rsCountKernel3121.next()) {
            for (int jumlah = 1; jumlah <= Integer.parseInt(rsCountKernel3121.getString("max").toString()); jumlah++) {
                String sqlCreateColumnKernel112 = "ALTER TABLE tb_langkah_nol_b_1_iterasi ADD `" + jumlah + "` FLOAT;";
                //System.out.println(sqlCreateColumnKernel1);
                Statement stCreateColumnKernel112 = (Statement) jdbc.getConnection().createStatement();
                stCreateColumnKernel112.executeUpdate(sqlCreateColumnKernel112);
            }

        }

        String sqlClearColumnLangkahNolAPerhitungan = "TRUNCATE tb_langkah_nol_a_perhitungan_iterasi";
        st.executeUpdate(sqlClearColumnLangkahNolAPerhitungan);

        String sqlSelectIterasi = "SELECT * FROM tb_parameter";
        Statement stSelectIterasi = (Statement) jdbc.getConnection().createStatement();
        ResultSet rsSelectIterasi = stSelectIterasi.executeQuery(sqlSelectIterasi);

        float hasilLangkahNol;
        int no;

        while (rsSelectIterasi.next()) {

            for (int iterasi = 1; iterasi <= Integer.parseInt(rsSelectIterasi.getString("itermax").toString()); iterasi++) {
                //mengosongkan table tb_langkah_nol_a_perhitungan terlebih dahulu

                String sqlKernelPerhitungan = "SELECT * FROM tb_matrix_hessian_perhitungan";

                Statement stKernelPerhitungan = (Statement) jdbc.getConnection().createStatement();
                ResultSet rsKernelPerhitungan = stKernelPerhitungan.executeQuery(sqlKernelPerhitungan);
                while (rsKernelPerhitungan.next()) {
                    //memanggil nilai dari Y yang berada di parameter
                    String sqlSelectParameterY = "SELECT * FROM tb_langkah_nol_c_iterasi WHERE id='" + iterasi + "'";

                    //System.out.println(sqlSelectParameterY);
                    Statement stSelectParameterY = (Statement) jdbc.getConnection().createStatement();
                    ResultSet rsSelectParameterY = stSelectParameterY.executeQuery(sqlSelectParameterY);
                    while (rsSelectParameterY.next()) {
                        //=$I$5*I5*(B23+($B$13^2))
                        no = Integer.parseInt(rsKernelPerhitungan.getString("id_doc_2"));

                        hasilLangkahNol = (float) (Float.parseFloat(rsKernelPerhitungan.getString("nilai"))
                                * (Float.parseFloat(rsSelectParameterY.getString(no + 2))));
                        //System.out.println("test");
                        String sqlInsertHessianMatrixPerhitungan = "INSERT INTO tb_langkah_nol_a_perhitungan_iterasi VALUES (null,'" + rsKernelPerhitungan.getString("id_doc_1") + "','" + rsKernelPerhitungan.getString("id_doc_2") + "','" + hasilLangkahNol + "','" + iterasi + "')";
                        //System.out.println(Float.parseFloat(rsSelectParameterY.getString(no+2)));
                        st.executeUpdate(sqlInsertHessianMatrixPerhitungan);
                    }
                }

                String sqlCountKernel3Iterasi = "SELECT MAX(id_doc1) AS max FROM tb_kernel_polinomial_degree";
                Statement stCountKernel3Iterasi = (Statement) jdbc.getConnection().createStatement();
                ResultSet rsCountKernel3Iterasi = stCountKernel3Iterasi.executeQuery(sqlCountKernel3Iterasi);

                //System.out.println(sqlCountKernel);
                while (rsCountKernel3Iterasi.next()) {
                    //System.out.println(rsCountKernel.getString("max"));
                    for (int cc = 1; cc <= Integer.parseInt(rsCountKernel3Iterasi.getString("max").toString()); cc++) {
                        String sqlInsertKernel = "INSERT INTO tb_langkah_nol_a_iterasi(id, iterasi, id_doc) VALUES(null, '" + iterasi + "', '" + cc + "')";
                        //System.out.println(sqlInsertKernel);
                        stCreateLangkahNolA.executeUpdate(sqlInsertKernel);
                    }

                }

                //telah terbentuk tabel lankah A iterasi 0 dengan jumlah sesuai dengan tabel kernel
                //memasukkan nilai ke dalam tabel yang akan di tampilkan
                String sqlMatrixHessian = "SELECT * FROM tb_langkah_nol_a_perhitungan_iterasi";
                Statement stMatrixHessian = (Statement) jdbc.getConnection().createStatement();
                ResultSet rsMatrixHessian = stMatrixHessian.executeQuery(sqlMatrixHessian);
                int a = 0;
                while (rsMatrixHessian.next()) {
                    a = a + 1;

                    Object[] oSelectKernel = new Object[8];
                    oSelectKernel[0] = rsMatrixHessian.getString("id");
                    oSelectKernel[1] = rsMatrixHessian.getString("id_doc_1");
                    oSelectKernel[2] = rsMatrixHessian.getString("id_doc_2");
                    oSelectKernel[3] = rsMatrixHessian.getString("nilai");
                    //System.out.println(oSelectKernel[0] + " " + oSelectKernel[1] + " " + oSelectKernel[2] + " " + oSelectKernel[3]);

                    String sqlUpdateKernel = "UPDATE tb_langkah_nol_a_iterasi SET `" + oSelectKernel[2] + "` = '" + oSelectKernel[3] + "' WHERE id_doc = '" + oSelectKernel[1] + "' AND iterasi='" + iterasi + "'";
                    //System.out.println(sqlUpdateKernel);
                    st.executeUpdate(sqlUpdateKernel);

                }

                //menampilkan hasil perhitungan ke dalam tabel
                int c = 0;

                tblLangkahA = new DefaultTableModel();
                TblLangkahA.setModel(tblLangkahA);
                tblLangkahA.addColumn("Id");
                tblLangkahA.addColumn("kalimat yang di eksekusi");
                tblLangkahA.addColumn("Iterasi Ke");

                String sqlCountKernel = "SELECT MAX(id_doc1) AS max FROM tb_kernel_polinomial_degree";
                Statement stCountKernel = (Statement) jdbc.getConnection().createStatement();
                ResultSet rsCountKernel = stCountKernel.executeQuery(sqlCountKernel);

                while (rsCountKernel.next()) {
                    for (c = 1; c <= Integer.parseInt(rsCountKernel.getString("max").toString()); c++) {
                        tblLangkahA.addColumn(c);
                    }
                }

                //kernel tampilkan tabel
                String sqlSelectAllJumlahKata = "SELECT * FROM tb_langkah_nol_a_iterasi, tb_filtering WHERE tb_langkah_nol_a_iterasi.id_doc= tb_filtering.id_filtering ";
                Statement stJumlahKata = (Statement) jdbc.getConnection().createStatement();
                ResultSet rsJumlahKata = stJumlahKata.executeQuery(sqlSelectAllJumlahKata);
                while (rsJumlahKata.next()) {
                    Object[] oJumlahKata = new Object[c + 3];
                    oJumlahKata[0] = rsJumlahKata.getString("id_filtering");
                    oJumlahKata[1] = rsJumlahKata.getString("deskripsi_filtering");

                    String sqlCountKernel1 = "SELECT MAX(id_doc1) AS max FROM tb_kernel_polinomial_degree";
                    Statement stCountKernel1 = (Statement) jdbc.getConnection().createStatement();
                    ResultSet rsCountKernel1 = stCountKernel1.executeQuery(sqlCountKernel1);

                    while (rsCountKernel1.next()) {
                        for (int d = 2; d <= Integer.parseInt(rsCountKernel1.getString("max").toString()) + 2; d++) {
                            oJumlahKata[d] = rsJumlahKata.getString(d + 1);
                        }
                    }
                    tblLangkahA.addRow(oJumlahKata);
                }

                String sqlInsertKernelB = "INSERT INTO tb_langkah_nol_b_iterasi(id, iterasi) VALUES(null ,'" + iterasi + "')";
                //System.out.println(sqlInsertKernelB);
                stCreateLangkahNolB.executeUpdate(sqlInsertKernelB);

                float hasilLangkahB;
                float yParameter, sum, alphaI, cParameter;

                String sqlCountKernel311 = "SELECT MAX(id_doc1) AS max FROM tb_kernel_polinomial_degree";
                Statement stCountKernel311 = (Statement) jdbc.getConnection().createStatement();
                ResultSet rsCountKernel311 = stCountKernel311.executeQuery(sqlCountKernel311);

                while (rsCountKernel311.next()) {
                    for (int jumlah = 1; jumlah <= Integer.parseInt(rsCountKernel311.getString("max").toString()); jumlah++) {
                        //mencari nilai ei dengan menjumlahkan nilai langkah a setiap kolom
                        String sqlSumEi = "SELECT SUM(`" + jumlah + "`) as Ei FROM tb_langkah_nol_a_iterasi WHERE iterasi = '" + iterasi + "'";
                        //System.out.println(sqlSumEi);
                        Statement stSumEi = (Statement) jdbc.getConnection().createStatement();
                        ResultSet rsSumEi = stSumEi.executeQuery(sqlSumEi);
                        while (rsSumEi.next()) {
                            //mengambil nilai y, alpha i, dan c dari parameter
                            String sqlAmbilParameter = "SELECT * FROM tb_parameter";
                            //System.out.println(sqlAmbilParameter);
                            Statement stAmbilParameter = (Statement) jdbc.getConnection().createStatement();
                            ResultSet rsAmbilParameter = stAmbilParameter.executeQuery(sqlAmbilParameter);
                            while (rsAmbilParameter.next()) {
                                //mengambil nilai y, alpha i, dan c dari parameter
                                String sqlAmbilParameterIterasi = "SELECT * FROM tb_langkah_nol_c_iterasi WHERE iterasi = '" + (iterasi - 1) + "'";
                                //System.out.println(sqlAmbilParameterIterasi);
                                Statement stAmbilParameterIterasi = (Statement) jdbc.getConnection().createStatement();
                                ResultSet rsAmbilParameterIterasi = stAmbilParameterIterasi.executeQuery(sqlAmbilParameterIterasi);
                                while (rsAmbilParameterIterasi.next()) {
                                    //=MIN(MAX(y*(1-sum);-alphai);c-alphai)
                                    yParameter = Float.parseFloat(rsAmbilParameter.getString("y"));
                                    sum = Float.parseFloat(rsSumEi.getString("ei"));
                                    alphaI = Float.parseFloat(rsAmbilParameterIterasi.getString("1"));
                                    cParameter = Float.parseFloat(rsAmbilParameter.getString("c"));

//                              System.out.println(yParameter);
//                              System.out.println(sum);
                                    //System.out.println(alphaI);
//                              System.out.println(cParameter);
                                    hasilLangkahB = Math.min(Math.max(yParameter * (1 - sum), -alphaI), cParameter - alphaI);
                                    //System.out.println(hasilLangkahB);

                                    String sqlUpdateKernel12 = "UPDATE tb_langkah_nol_b_iterasi SET `" + jumlah + "` = '" + hasilLangkahB + "' WHERE id='" + iterasi + "' AND iterasi ='" + iterasi + "'";
                                    //System.out.println(sqlUpdateKernel12);
                                    st.executeUpdate(sqlUpdateKernel12);
                                }
                            }
                        }
                    }
                }

                //menampilkan pada tabel  langkah b ke 1 iterasi 0
                tblLangkahB = new DefaultTableModel();
                TblLangkahB.setModel(tblLangkahB);
                tblLangkahB.addColumn("Iterasi ke");

                int ca = 0;

                String sqlCountKernelca = "SELECT MAX(id_doc1) AS max FROM tb_kernel_polinomial_degree";
                Statement stCountKernelca = (Statement) jdbc.getConnection().createStatement();
                ResultSet rsCountKernelca = stCountKernelca.executeQuery(sqlCountKernelca);

                while (rsCountKernelca.next()) {
                    for (ca = 1; ca <= Integer.parseInt(rsCountKernelca.getString("max").toString()); ca++) {
                        tblLangkahB.addColumn(ca);
                    }
                }

                //memasukkan data pada tabel  langkah b ke 1 iterasi 0
                String sqlSelectAllJumlahKataca = "SELECT * FROM tb_langkah_nol_b_iterasi";
                Statement stJumlahKataca = (Statement) jdbc.getConnection().createStatement();
                ResultSet rsJumlahKataca = stJumlahKataca.executeQuery(sqlSelectAllJumlahKataca);
                while (rsJumlahKataca.next()) {
                    Object[] oJumlahKata = new Object[ca + 2];
                    String sqlCountKernel1cab = "SELECT MAX(id) AS max FROM tb_langkah_nol_a";
                    Statement stCountKernel1cab = (Statement) jdbc.getConnection().createStatement();
                    ResultSet rsCountKernel1cab = stCountKernel1cab.executeQuery(sqlCountKernel1cab);
                    int d = 2;
                    while (rsCountKernel1cab.next()) {
                        while (d <= Integer.parseInt(rsCountKernel1cab.getString("max").toString()) + 2) {
                            oJumlahKata[d - 2] = rsJumlahKataca.getString(d);
                            d++;
                        }
                    }
                    tblLangkahB.addRow(oJumlahKata);
                }

                //menampilkan pada tabel  langkah b ke 2 iterasi 0
                tblLangkahB1 = new DefaultTableModel();
                TblLangkahB1.setModel(tblLangkahB1);
                tblLangkahB1.addColumn("Iterasi ke");

                int cd = 0;

                String sqlCountKernel312 = "SELECT MAX(id_doc1) AS max FROM tb_kernel_polinomial_degree";
                Statement stCountKernel312 = (Statement) jdbc.getConnection().createStatement();
                ResultSet rsCountKernel312 = stCountKernel312.executeQuery(sqlCountKernel312);

                while (rsCountKernel312.next()) {
                    String sqlInsertKernelB2 = "INSERT INTO tb_langkah_nol_b_1_iterasi(id, iterasi) VALUES(null, '" + iterasi + "')";
                    //System.out.println(sqlInsertKernel);
                    stCreateLangkahNolB.executeUpdate(sqlInsertKernelB2);
                    String sqlCountKernelcd = "SELECT MAX(id_doc1) AS max FROM tb_kernel_polinomial_degree";
                    Statement stCountKernelcd = (Statement) jdbc.getConnection().createStatement();
                    ResultSet rsCountKernelcd = stCountKernelcd.executeQuery(sqlCountKernelcd);

                    while (rsCountKernelcd.next()) {
                        for (cd = 1; cd <= Integer.parseInt(rsCountKernelcd.getString("max").toString()); cd++) {
                            tblLangkahB1.addColumn(cd);
                        }
                    }
                }

                //memasukkan pada tabel  langkah b ke 2 iterasi 0
                String sqlSelectAllJumlahKatacd = "SELECT * FROM tb_langkah_nol_c_iterasi WHERE ID='" + iterasi + "'";
                Statement stJumlahKatacd = (Statement) jdbc.getConnection().createStatement();
                ResultSet rsJumlahKatacd = stJumlahKatacd.executeQuery(sqlSelectAllJumlahKatacd);
                while (rsJumlahKatacd.next()) {
                    Object[] oJumlahKatad = new Object[cd + 3];

                    String sqlCountKernel1cabd = "SELECT MAX(id) AS max FROM tb_langkah_nol_a";
                    Statement stCountKernel1cabd = (Statement) jdbc.getConnection().createStatement();
                    ResultSet rsCountKernel1cabd = stCountKernel1cabd.executeQuery(sqlCountKernel1cabd);
                    int d = 2;
                    while (rsCountKernel1cabd.next()) {
                        while (d <= Integer.parseInt(rsCountKernel1cabd.getString("max").toString()) + 1) {
                            oJumlahKatad[0] = rsJumlahKatacd.getString("iterasi");
                            oJumlahKatad[d - 1] = rsJumlahKatacd.getString(d + 1);

                            String sqlUpdateKernel122 = "UPDATE tb_langkah_nol_b_1_iterasi SET `" + (d - 1) + "` = '" + oJumlahKatad[d - 1] + "' WHERE iterasi ='" + iterasi + "'";
                            //System.out.println(sqlUpdateKernel122);
                            st.executeUpdate(sqlUpdateKernel122);

                            d++;
                        }
                    }
                    //tblLangkahB1.addRow(oJumlahKatad);
                }

                //memasukkan pada tabel  langkah b ke 2 iterasi 0
                String sqlSelectAllJumlahKatacdTampil = "SELECT * FROM tb_langkah_nol_b_1_iterasi";
                Statement stJumlahKatacdTampil = (Statement) jdbc.getConnection().createStatement();
                ResultSet rsJumlahKatacdTampil = stJumlahKatacdTampil.executeQuery(sqlSelectAllJumlahKatacdTampil);
                while (rsJumlahKatacdTampil.next()) {
                    Object[] oJumlahKatadTampil = new Object[cd + 3];

                    String sqlCountKernel1cabd = "SELECT MAX(id) AS max FROM tb_langkah_nol_a";
                    Statement stCountKernel1cabd = (Statement) jdbc.getConnection().createStatement();
                    ResultSet rsCountKernel1cabd = stCountKernel1cabd.executeQuery(sqlCountKernel1cabd);
                    int d = 2;
                    while (rsCountKernel1cabd.next()) {
                        while (d <= Integer.parseInt(rsCountKernel1cabd.getString("max").toString()) + 1) {
                            oJumlahKatadTampil[0] = rsJumlahKatacdTampil.getString("iterasi");
                            oJumlahKatadTampil[d - 1] = rsJumlahKatacdTampil.getString(d + 1);

                            d++;
                        }
                    }
                    tblLangkahB1.addRow(oJumlahKatadTampil);
                }

                Statement stCreateLangkahNolc = (Statement) jdbc.getConnection().createStatement();

                String sqlInsertKernelc = "INSERT INTO tb_langkah_nol_c_iterasi(id, iterasi) VALUES(null, '" + iterasi + "')";
                //System.out.println(sqlInsertKernel);
                stCreateLangkahNolc.executeUpdate(sqlInsertKernelc);

                //menampilkan pada tabel  langkah c iterasi 0
                tblLangkahC = new DefaultTableModel();
                TblLangkahC.setModel(tblLangkahC);
                tblLangkahC.addColumn("Iterasi ke");

                int cdcd = 0;

                String sqlCountKernelcdcd = "SELECT MAX(id_doc1) AS max FROM tb_kernel_polinomial_degree";
                Statement stCountKernelcdcd = (Statement) jdbc.getConnection().createStatement();
                ResultSet rsCountKernelcdcd = stCountKernelcdcd.executeQuery(sqlCountKernelcdcd);

                while (rsCountKernelcdcd.next()) {
                    for (cdcd = 1; cdcd <= Integer.parseInt(rsCountKernelcdcd.getString("max").toString()); cdcd++) {
                        tblLangkahC.addColumn(cdcd);
                    }
                }

                float langkah_c, b, b1;

                //memasukkan pada tabel  langkah c iterasi 0
                String sqlSelectAllJumlahKatacdcc = "SELECT * FROM tb_langkah_nol_b_iterasi WHERE iterasi =  '" + iterasi + "'";
                Statement stJumlahKatacdcc = (Statement) jdbc.getConnection().createStatement();
                ResultSet rsJumlahKatacdcc = stJumlahKatacdcc.executeQuery(sqlSelectAllJumlahKatacdcc);
                while (rsJumlahKatacdcc.next()) {

                    String sqlSelectAllJumlahKatacdccc = "SELECT * FROM tb_langkah_nol_b_1_iterasi WHERE iterasi =  '" + iterasi + "'";
                    Statement stJumlahKatacdccc = (Statement) jdbc.getConnection().createStatement();
                    ResultSet rsJumlahKatacdccc = stJumlahKatacdccc.executeQuery(sqlSelectAllJumlahKatacdccc);
                    while (rsJumlahKatacdccc.next()) {
                        Object[] oJumlahKataadc = new Object[cd + 2];

                        String sqlCountKernel1cabdc = "SELECT MAX(id) AS max FROM tb_langkah_nol_a";
                        Statement stCountKernel1cabdc = (Statement) jdbc.getConnection().createStatement();
                        ResultSet rsCountKernel1cabdc = stCountKernel1cabdc.executeQuery(sqlCountKernel1cabdc);
                        int dad = 2;
                        while (rsCountKernel1cabdc.next()) {
                            while (dad <= Integer.parseInt(rsCountKernel1cabdc.getString("max").toString()) + 1) {
                                b1 = Float.parseFloat(rsJumlahKatacdcc.getString(dad + 1));
                                //System.out.print(b1 + " ");
                                b = Float.parseFloat(rsJumlahKatacdccc.getString(dad + 1));
                                //System.out.println(b);

                                langkah_c = b + b1;

                                oJumlahKataadc[0] = rsJumlahKatacdcc.getString("iterasi");
                                oJumlahKataadc[dad - 1] = langkah_c;

                                String sqlUpdateKernel12c = "UPDATE tb_langkah_nol_c_iterasi SET `" + (dad - 1) + "` = '" + langkah_c + "' WHERE iterasi = '" + iterasi + "'";
                                //System.out.println(sqlUpdateKernel12c);
                                st.executeUpdate(sqlUpdateKernel12c);

                                dad++;

                            }

                        }
                        //tblLangkahC.addRow(oJumlahKataadc);
                    }
                }

                //memasukkan pada tabel  langkah b ke 2 iterasi 0
                String sqlSelectAllJumlahKatacdTampilC = "SELECT * FROM tb_langkah_nol_c_iterasi";
                //System.out.println(sqlSelectAllJumlahKatacdTampilC);
                Statement stJumlahKatacdTampilC = (Statement) jdbc.getConnection().createStatement();
                ResultSet rsJumlahKatacdTampilC = stJumlahKatacdTampilC.executeQuery(sqlSelectAllJumlahKatacdTampilC);
                while (rsJumlahKatacdTampilC.next()) {
                    Object[] oJumlahKatadTampilC = new Object[cd + 3];

                    String sqlCountKernel1cabddd = "SELECT MAX(id) AS max FROM tb_langkah_nol_a";
                    Statement stCountKernel1cabddd = (Statement) jdbc.getConnection().createStatement();
                    ResultSet rsCountKernel1cabddd = stCountKernel1cabddd.executeQuery(sqlCountKernel1cabddd);
                    int d1 = 2;
                    while (rsCountKernel1cabddd.next()) {
                        while (d1 <= Integer.parseInt(rsCountKernel1cabddd.getString("max").toString()) + 1) {
                            oJumlahKatadTampilC[0] = rsJumlahKatacdTampilC.getString("iterasi");
                            oJumlahKatadTampilC[d1 - 1] = rsJumlahKatacdTampilC.getString(d1 + 1);

                            d1++;

                        }
                        tblLangkahC.addRow(oJumlahKatadTampilC);
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
        jLabel1 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        TblLangkahA = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        TblLangkahB = new javax.swing.JTable();
        jScrollPane10 = new javax.swing.JScrollPane();
        TblLangkahB1 = new javax.swing.JTable();
        jScrollPane9 = new javax.swing.JScrollPane();
        TblLangkahC = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();

        jInternalFrame1.setVisible(true);

        jLabel4.setText("iterasi 1+");

        jLabel1.setText("LANGKAH A");

        TblLangkahA.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane7.setViewportView(TblLangkahA);

        jLabel2.setText("LANGKAH B");

        TblLangkahB.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane8.setViewportView(TblLangkahB);

        TblLangkahB1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane10.setViewportView(TblLangkahB1);

        TblLangkahC.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane9.setViewportView(TblLangkahC);

        jLabel5.setText("LANGKAH C");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 858, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(170, 170, 170))
            .addComponent(jScrollPane10)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 858, Short.MAX_VALUE)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addGap(0, 0, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(154, 154, 154))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(152, 152, 152)))
                    .addContainerGap()))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(379, Short.MAX_VALUE)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(16, 16, 16)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(343, Short.MAX_VALUE)))
        );

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 762, Short.MAX_VALUE)
                .addContainerGap())
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addContainerGap())
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
    private javax.swing.JTable TblLangkahA;
    private javax.swing.JTable TblLangkahB;
    private javax.swing.JTable TblLangkahB1;
    private javax.swing.JTable TblLangkahC;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    // End of variables declaration//GEN-END:variables
}
