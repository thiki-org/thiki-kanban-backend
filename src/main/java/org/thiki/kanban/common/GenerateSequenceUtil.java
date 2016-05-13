package org.thiki.kanban.common;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import org.apache.log4j.Logger;


public class GenerateSequenceUtil{

    /** .log */
    private static final Logger logger = Logger.getLogger(GenerateSequenceUtil.class);

    /** The FieldPosition. */
    private static final FieldPosition HELPER_POSITION = new FieldPosition(0);

    /** This Format for format the data to special format. */
    private final static Format dateFormat = new SimpleDateFormat("yyMMddHHmmss");

    /** This Format for format the number to special format. */
    private final static NumberFormat numberFormat = new DecimalFormat("00000");
    /** This Format for format the number to special format. */
    private final static NumberFormat servernumberFormat = new DecimalFormat("00");
    /** This int is the sequence number ,the default value is 0. */
    private static int seq = 0;
    public static int serverid =0;
    private static final int MAX = 9999;
    public static String server_id;
    static  {
        Properties prop =  new Properties();
        InputStream in = ClassLoader.getSystemResourceAsStream("kanban.properties");
        try  {
            prop.load(in);
            server_id = prop.getProperty( "server.id" ).trim();
        }  catch  (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 时间格式生成序列
     * @return String
     */
    public static synchronized String generateSequenceNo() {

        Calendar rightNow = Calendar.getInstance();

        StringBuffer sb = new StringBuffer();

        dateFormat.format(rightNow.getTime(), sb, HELPER_POSITION);
        servernumberFormat.format(Integer.parseInt(server_id), sb, HELPER_POSITION);
        //System.out.println(rightNow.getTime().toString());
        numberFormat.format(seq, sb, HELPER_POSITION);

        if (seq == MAX) {
            seq = 0;
        } else {
            seq++;
        }

        logger.info("THE SQUENCE IS :" + sb.toString());

        return sb.toString();
    }
//    public static void main(String args[]) {
//    	String id=generateSequenceNo();
//        System.out.println(id);
//    }
}