/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventaris_ukk;


public class userSession {
    private static String u_ID;
    private static String u_password;
    private static String u_Nama;
    private static String u_alamat;
    private static String u_telp;
    private static String u_kelas;
    private static String u_level;
    private static String u_Status;
    
    public static String getU_ID() {
        return u_ID;
    }
    
    public static void setU_ID(String u_ID) {
        userSession.u_ID = u_ID;
    }
    
    public static String getU_password() {
        return u_password;
    }
    
    public static void setU_password(String u_password) {
        userSession.u_password = u_password;
    }
    
    public static String getU_Nama() {
        return u_Nama;
    }
    
    public static void setU_Nama(String u_Nama) {
        userSession.u_Nama = u_Nama;
    }
    
    public static String getU_alamat() {
        return u_alamat;
    }
    
    public static void setU_alamat(String u_alamat) {
        userSession.u_alamat = u_alamat;
    }
    
    public static String getU_telp() {
        return u_telp;
    }
    
    public static void setU_telp(String u_telp) {
        userSession.u_telp = u_telp;
    }
    
    public static String getU_kelas() {
        return u_kelas;
    }
    
    public static void setU_kelas(String u_kelas) {
        userSession.u_kelas = u_kelas;
    }
    
    public static String getU_level() {
        return u_level;
    }
    
    public static void setU_level(String u_level) {
        userSession.u_level = u_level;
    }
    
    public static String getU_status(){
        return u_Status;
    }
    public static void setU_status(String u_Status){
        userSession.u_Status = u_Status;
    }

    static String getU_jmlh() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    static String getU_pass() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    static String getU_idb() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    static String getU_Kode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
