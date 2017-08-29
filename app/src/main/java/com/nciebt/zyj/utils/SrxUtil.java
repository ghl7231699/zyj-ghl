package com.nciebt.zyj.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;

import com.nciebt.zyj.App;

/**
 * 
 * @项目名称：NCIEBT
 * @类名称：SrxUtil
 * 				@类描述： 工具类
 * @创建人：xs
 * @创建时间：2015-9-18
 * @修改人：
 * @修改时间：
 * @修改备注：
 * @version
 * 
 */
public class SrxUtil
{

	private static Pattern floatNumericPattern = Pattern.compile( "^[0-9\\-\\.]+$" );

	
	/**
	 * 身份证校验
	 */
	public final static Map<Integer, Character> map = new HashMap<Integer, Character>();
	static{
		map.put(0, '1');
		map.put(1, '0');
		map.put(2, 'X');
		map.put(3, '9');
		map.put(4, '8');
		map.put(5, '7');
		map.put(6, '6');
		map.put(7, '5');
		map.put(8, '4');
		map.put(9, '3');
		map.put(10, '2');
	}
	
	public static boolean checkIdCard(String IdCard) {
		if(IdCard==null||IdCard.length()!=18){
			return false;
		}
		int wi[] = {7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2};
		int sum = 0;
		for(int i = 0 ; i<wi.length ; i++){
			try {
				sum+= wi[i]*(Integer.parseInt(""+IdCard.charAt(i)));
			} catch (Exception e) {
				return false;
			}
			
		}
		return map.get(sum%11)==IdCard.charAt(17);
	}

	
	/**
	 * 
	 * @Title: getAge
	 * @Description:根据生日得年龄
	 * @param birdth
	 * @return int
	 * @throws
	 */
	@SuppressWarnings( "deprecation" )
	public static int getAge( Date birdth )
	{
		int iAge = 0;
		if ( birdth != null )
		{
			Date nDate = new Date();
			int iNowYear = nDate.getYear() + 1900;
			int iNowMonth = nDate.getMonth() + 1;
			int iNowDay = nDate.getDate();
			// System.out.println(iNowYear+" "+iNowMonth+" "+iNowDay);

			int iYear = birdth.getYear() + 1900;
			int iMonth = birdth.getMonth() + 1;
			int iDay = birdth.getDate();

			iAge = iNowYear - iYear - 1;
			if ( iNowMonth >= iMonth )
			{
				if ( iNowMonth > iMonth )
					iAge = iAge + 1;
				else
				{
					if ( iNowDay >= iDay )
					{

						iAge = iAge + 1;
					}
				}

			}

		}
		// System.out.println("age="+iAge);
		return iAge;
	}


	/** 
	 * 返回当前程序版本名 
	 */  
	public static String[] getAppVersionName(Context context) {  
	    String versionName = "";  
	    String versioncode = "";  
	    try {  
	        // ---get the package info---  
	        PackageManager pm = context.getPackageManager();  
	        PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);  
	        versionName = pi.versionName;  
	        versioncode = "" + pi.versionCode;
	        if (versionName == null || versionName.length() <= 0) {  
	            return new String[]{"", ""};  
	        }  
	    } catch (Exception e) {  
	    	TraceUtils.e("VersionInfo", "Exception" + e);  
	    }  
	    return new String[]{ versionName, versioncode};  
	}  

	
	/**
	 * 判断是否浮点数字表示
	 * 
	 * @param src
	 *            源字符串
	 * @return 是否数字的标志
	 */
	public static boolean isFloatNumeric( String src )
	{
		boolean return_value = false;
		if ( src != null && src.length() > 0 )
		{
			Matcher m = floatNumericPattern.matcher( src );
			if ( m.find() )
			{
				return_value = true;
			}
		}
		return return_value;
	}

	/**
	 * 
	 * @Title: checkMobilePhone
	 * @Description: 检查移动电话格式
	 * @param mobilePhone
	 * @return boolean
	 * @throws
	 */
	public static boolean checkMobilePhone( String mobilePhone )
	{
		boolean b = false;
		String regex = "^1\\d{10}$";// 移动电话正则
		b = mobilePhone.matches( regex );
		return b;
	}

	/**
	 * 
	 * @Title: checkMobilePhone
	 * @Description: 检查固定电话格式
	 * @param workPhone
	 * @return boolean
	 * @throws
	 */
	public static boolean checkWorkPhone( String workPhone )
	{
		boolean b = false;
		String regex = "^(\\d{3,4}\\-)(\\d{6,8})(\\-\\d{1,6})?$";
		b = workPhone.matches( regex );
		return b;
	}

	/**
	 * email正则表达式
	 * 
	 * @param email
	 * @return
	 */
	public static boolean checkEmail( String email )
	{
		boolean falg = false;
		String regex = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$";
		falg = email.matches( regex );
		return falg;
	}

	/**
	 * MSN格式
	 * 
	 * @param msn
	 * @return
	 */
	public static boolean checkMSN( String msn )
	{
		return Pattern.matches( "(\\S)+[@]{1}(\\S)+[.]{1}(\\w)+", msn );
	}

	/**
	 * 复制对象
	 * 
	 * @param src
	 * @param dest
	 */
	public static void copyObject( Object src, Object dest )
	{
		Class< ? extends Object > classSrc = src.getClass();
		Class< ? extends Object > classDest = dest.getClass();
		Field[] arySrcFields = classSrc.getFields();
		Field[] aryDestFields = classDest.getFields();
		for ( int i = 0; i < arySrcFields.length; i++ )
		{
			Field fieldSrc = arySrcFields[ i ];
			for ( int j = 0; j < aryDestFields.length; j++ )
			{
				Field fieldDest = aryDestFields[ j ];

				if ( fieldSrc.getName().equalsIgnoreCase( fieldDest.getName() ) )
				{
					try
					{
						fieldDest.set( dest, fieldSrc.get( src ) );
					}
					catch ( IllegalArgumentException e )
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					catch ( IllegalAccessException e )
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
			}
		}
	}

	/**
	 * 删除指定目录下的所有文件并 根绝条件来决定是否删除根文件夹
	 * 
	 * @param dir
	 *            文件对象
	 * @param falg
	 *            是否删除根文件夹
	 * @return
	 */
	@SuppressWarnings( "unused" )
	private static boolean deleteFolder( File dir, boolean falg )
	{
		File filelist[] = dir.listFiles();
		int listlen = filelist.length;
		for ( int i = 0; i < listlen; i++ )
		{
			if ( filelist[ i ].isDirectory() )
			{
				deleteFolder( filelist[ i ], true );
			}
			else
			{
				if ( !filelist[ i ].delete() )
					return false;
			}
		}
		if ( falg )
		{
            return dir.delete();
		}
		else
		{
			return true;
		}
	}

	/***
	 * 限制数值小数位数
	 * 
	 * @param dvar 要格式化小数位数的数值
	 * @param sca 保留小数位数
	 * @return 保留dvar位小数的数值
	 */
	public static double FormatDouble( double dvar, int sca )
	{
		return BigDecimal.valueOf( dvar ).setScale( sca, BigDecimal.ROUND_HALF_UP ).doubleValue();
	}

	/***
	 * 返回非科学计数法格式数值字符串
	 * 
	 * @param d
	 *            科学计数法格式数值
	 * @return 非科学计数法格式数值字符串
	 */
	public static String NumberFormat( double d )
	{
		java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
		nf.setGroupingUsed( false );
		return nf.format( d );
	}

	/**
	 * 判断sd卡是否存在
	 * 
	 * @return
	 */
	public static boolean isSDCardExist()
	{
		boolean sdCardExist = Environment.getExternalStorageState().equals( Environment.MEDIA_MOUNTED );
		// 判断sd卡是否存在
		return sdCardExist;
	}

	/**
	 * 判断路径是否存在如果不存在则创建
	 * 
	 * @param sPath
	 *            需要检索路径
	 * @param isCreate
	 *            如果不存在是否创建
	 * @return
	 */
	public static boolean isPathExist( String sPath, boolean isCreate )
	{
		File file = new File( sPath );
		if ( file.exists() )
		{
			return true;
		}
		else
		{
			if ( isCreate )
				file.mkdirs();
			return false;
		}
	}

	/**
	 * MD5加密
	 */
	public static String md5Digest( String src ) throws Exception
	{
		MessageDigest md = MessageDigest.getInstance( "MD5" );
		byte[] b = md.digest( src.getBytes( "gb2312" ) );
		return byte2HexStr( b );
	}

	/**
	 * MD5加密
	 */
	public static String md5Digest( String src, String encoding ) throws Exception
	{
		MessageDigest md = MessageDigest.getInstance( "MD5" );
		byte[] b = md.digest( src.getBytes( encoding ) );
		return byte2HexStr( b );
	}


	/**
	 * 将二进制转为String
	 */
	private static String byte2HexStr( byte[] b )
	{
		StringBuilder sb = new StringBuilder();
		for ( int i = 0; i < b.length; i++ )
		{
			String s = Integer.toHexString( b[ i ] & 0xFF );
			if ( s.length() == 1 )
			{
				sb.append( "0" );
			}
			sb.append( s.toUpperCase() );
		}
		return sb.toString();
	}

	



	

	/**
	 * 验证字符串长度是否符合要求，一个汉字等于3个字符
	 * 
	 * @param inputStr
	 *            要验证的字符串
	 * @param iMaxLength
	 *            验证的长度
	 * @return 符合长度true 超出范围false
	 */
	public static boolean validateStrLength( String inputStr, int iMaxLength )
	{
		if ( isNullOrEmpty( inputStr ) )
			return true;
		int valueLength = 0;
		String chinese = "[\u0391-\uFFE5]";
		for ( int i = 0; i < inputStr.length(); i++ )
		{
			String temp = inputStr.substring( i, i + 1 );
			if ( temp.matches( chinese ) )
			{
				valueLength += 3;
			}
			else
			{
				valueLength += 1;
			}
		}
        return valueLength <= iMaxLength;
    }

	/**
	 * 判断传入的字符串是否是 NULL 或“”
	 * 
	 * @param s
	 *            字符串
	 * @return
	 */
	public static boolean isNullOrEmpty( String s )
	{
        return s == null || "".equals(s);
    }

	/**
	 * 判断当前是否有网络连接
	 * 
	 * @param context
	 * @return
	 */
	public static boolean networkDetector( Context context )
	{
		ConnectivityManager manager = ( ConnectivityManager ) context.getApplicationContext()
				.getSystemService( Context.CONNECTIVITY_SERVICE );
		if ( manager == null )
		{
			return false;
		}
		NetworkInfo networkinfo = manager.getActiveNetworkInfo();
        return !(networkinfo == null || !networkinfo.isAvailable());
    }

	/***
	 * 复制文件
	 * 
	 * @param sor
	 *            源文件
	 * @param des
	 *            目标文件
	 * @return 成功返回true 失败false
	 */
	public static boolean copyFile( String sor, String des )
	{
		int length = 2097152;
		FileInputStream in = null;
		FileOutputStream out = null;
		try
		{
			in = new FileInputStream( sor );
			out = new FileOutputStream( des );
			byte[] buffer = new byte[ length ];
			int ins = in.read( buffer );
			while ( ins != -1 )
			{
				out.write( buffer, 0, ins );
				ins = in.read( buffer );
			}
			out.flush();
			return true;
		}
		catch ( FileNotFoundException e )
		{
			e.printStackTrace();
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				in.close();
				out.close();
			}
			catch ( IOException e )
			{
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     * 
     * @param pxValue
     * @return
     */ 
    public static int px2dip( float pxValue) { 
        final float scale = App.getContext().getResources().getDisplayMetrics().density; 
        return (int) (pxValue / scale + 0.5f); 
    } 
   
    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     * 
     * @param dipValue
     * @return
     */ 
    public static int dip2px( float dipValue) { 
        final float scale = App.getContext().getResources().getDisplayMetrics().density; 
        return (int) (dipValue * scale + 0.5f); 
    } 
   
    /**
     * 将px值转换为sp值，保证文字大小不变
     * 
     * @param pxValue
     * @return
     */ 
    public static int px2sp( float pxValue) { 
        final float fontScale = App.getContext().getResources().getDisplayMetrics().scaledDensity; 
        return (int) (pxValue / fontScale + 0.5f); 
    } 
   
    /**
     * 将sp值转换为px值，保证文字大小不变
     * 
     * @param spValue
     * @return
     */ 
    public static int sp2px( float spValue) { 
        final float fontScale = App.getContext().getResources().getDisplayMetrics().scaledDensity; 
        return (int) (spValue * fontScale + 0.5f); 
    }

	/**
	 * 根据名称查找对应的值信息
	 *
	 * @param id
	 *            名称
	 * @param args
	 *            值中要反填的参数
	 * @return
	 */
	public static String getStringById(int id, Object... args) {
		Context context = App.getContext();
		if (context == null)
			return "";
		String rtn = "";
		try {
			String format = context.getString(id);
			rtn = String.format(format, args);
		} catch (Exception e) {
			TraceUtils.e("Messages:", id + " not found!");
		}
		return rtn;
	}

}
