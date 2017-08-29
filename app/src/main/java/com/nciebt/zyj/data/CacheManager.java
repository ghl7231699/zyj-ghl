package com.nciebt.zyj.data;

import android.text.TextUtils;
import android.util.Base64;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.nciebt.zyj.App;
import com.nciebt.zyj.data.sql.DetailedListModule;
import com.nciebt.zyj.inter.FinalInter;
import com.nciebt.zyj.utils.TraceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 类名称：CacheManager
 * 类描述：(缓存菜单)
 * 创建人：xs
 * 创建时间：2017/5/4 11:10
 * 修改人：xs
 * 修改时间：2017/5/4 11:10
 * @version v1.0
 */
public class CacheManager extends BaseData implements FinalInter{

    /**
     * 缓存数据
     */
    private Map< String, String > mCacheDataMap;

    private String					mCacheDataStr;

    private String					mCacheUserDataMap;

    private JsonObject				jsonObj;
    @Override
    public void init() {
        String data = readLocalData();
        mCacheDataStr = decode( data );
        if ( mCacheDataStr != null )
        {
            try
            {
                JsonObject mapJson = App.getContext().getmJsonParser().parse( mCacheDataStr ).getAsJsonObject();
                List< String > listKey = toMap( mapJson );

                if ( listKey.size() > 10 )
                {
                    for ( int i = 0; i < listKey.size() - 10 ; i++ )
                    {
                        TraceUtils.e( "listKey:" +i );
                        mapJson.remove( listKey.get( i ) );
                    }
                    mCacheDataStr = mapJson.toString();
                    mCacheUserDataMap = mCacheDataStr;
                    // 再缓存到本地
                    boolean saveStatus = saveData();
                    TraceUtils.e( "存储到本地:" + saveStatus+ listKey );
                }else{
                    mCacheUserDataMap = mCacheDataStr;
                }
            }
            catch ( Exception e )
            {
                TraceUtils.e( "解析报错" );
            }
        }
    }
    /**
     * 缓存网络请求数据
     */
    public void cacheData( String valueData )
    {

        if ( mCacheDataMap == null )
            mCacheDataMap = new HashMap< String, String >();
        String AuthId = DataCenter.getInstance().getAuthId();
        // 先缓存到本地内存
        mCacheDataMap.put( AuthId, valueData );

        JsonObject mapJson = new JsonObject();
        if ( TextUtils.isEmpty( mCacheDataStr ) )
        {
            mapJson.addProperty( "" + AuthId, valueData );
            mCacheDataStr = mapJson.toString();
        }
        else
        {
            mapJson = App.getContext().getmJsonParser().parse( mCacheDataStr ).getAsJsonObject();
            JsonElement element = mapJson.get( AuthId );

            if ( element != null )
                mapJson.remove( AuthId );

            mapJson.addProperty( AuthId, valueData);
            mCacheDataStr = mapJson.toString();
        }
        // 再缓存到本地
        boolean saveStatus = saveData();
        TraceUtils.e( "存储到本地:" + saveStatus);
    }


    /**
     * 数据保存到本地
     */
    public boolean saveData()
    {
        if ( TextUtils.isEmpty( mCacheDataStr ) )
        {
            return false;
        }
        String data = mCacheDataStr;

        if ( !TextUtils.isEmpty( data ) )
        {
            data = encode( data );
        }
        String path = App.getLocalDataPath() + LOCAL_LOCAL_DATA_FILE_NAME;
        TraceUtils.e( "path:" + path );
        File file = new File( path );
        if ( !file.exists() )
        {
            file.getParentFile().mkdirs();
        }
        try
        {
            OutputStream out = new FileOutputStream( file );
            byte[] by = data.getBytes( "ISO-8859-1" );
            out.write( by );
            out.flush();
            out.close();
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
        return false;
    }


    /**
     * 根据本地数据加载菜单
     * */
    public boolean setMenuView( )
    {
        String AuthId = DataCenter.getInstance().getAuthId();
        if ( TextUtils.isEmpty( AuthId ) )
            return false;
        if ( TextUtils.isEmpty( mCacheUserDataMap ) )
            return false;
        try
        {
            JsonObject mapJson = App.getContext().getmJsonParser().parse( mCacheUserDataMap ).getAsJsonObject();
            JsonElement element = mapJson.get( AuthId );
            if ( element == null )
                return false;
            String objString = element.getAsString();
            if ( TextUtils.isEmpty( objString ) )
                return false;
            jsonObj = App.getContext().getmJsonParser().parse( objString ).getAsJsonObject();
            if ( jsonObj == null )
                return false;
        }
        catch ( Exception e )
        {
            return false;
        }

        return true;
    }

    /**
     * 清除缓存数据
     */
    public void clearCacheData()
    {

        try
        {
            String path = App.getLocalDataPath() + LOCAL_LOCAL_DATA_FILE_NAME;
            File file = new File( path );
            file.delete();
        }
        catch ( Exception e )
        {
        }

        mCacheDataStr = "";
        if ( mCacheDataMap != null )
            mCacheDataMap.clear();

    }

    /**
     * 从本地缓存读取
     */
    public String readCache( String AuthId )
    {

        if ( TextUtils.isEmpty( mCacheDataStr ) )
        {
            return null;
        }

        mCacheDataStr = mCacheDataStr.trim();
        if ( !mCacheDataStr.startsWith( "{" ) && !mCacheDataStr.endsWith( "}" ) )
        {
            // DataCenter.getInstance().getmCacheManager().clearCacheData();
            return null;
        }

        try
        {

            JsonObject object = App.getContext().getmJsonParser().parse( mCacheDataStr ).getAsJsonObject();
            JsonElement element = object.get( "" + AuthId );
            if ( null == element )
            {
                return null;
            }
            String data = element.getAsString();

            return data;
        }
        catch ( JsonIOException e )
        {
            e.printStackTrace();
        }
        catch ( JsonSyntaxException e )
        {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 读取本地数据
     */
    public String readLocalData()
    {
        String path = App.getLocalDataPath() + LOCAL_LOCAL_DATA_FILE_NAME;
        TraceUtils.e( "path:" + path );
        File file = new File( path );
        if ( !file.exists() )
        {
            return "";
        }

        try
        {
            // StringBuffer buffer = new StringBuffer();

            InputStream inp = new FileInputStream( file );
            byte[] by = new byte[ inp.available() ];
            // BufferedReader reader = new BufferedReader( new
            // InputStreamReader( new FileInputStream( file ) ) );
            // String line = null;
            // StringBuilder builder = new StringBuilder();

            inp.read( by );
            // while ( ( line = reader.readLine() ) != null )
            // {
            // buffer.append( line );
            // }

            // String data = buffer.toString();
            String data = new String( by, "ISO-8859-1" ).toString();

            // reader.close();
            inp.close();

            return data;
        }
        catch ( FileNotFoundException e )
        {
            e.printStackTrace();
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }

        return "";
    }


    /**
     * 解码
     **/
    public String decode( String data )
    {

        if ( TextUtils.isEmpty( data ) )
        {
            return null;
        }

        String result = null;
        try
        {
            result = new String( Base64.decode( Base64.decode( Base64.decode( data, Base64.DEFAULT ), Base64.DEFAULT ),
                    Base64.DEFAULT ) ).toString();
        }
        catch ( Exception e )
        {
        }

        return result;
    }

    /**
     * 编码
     **/
    public String encode( String data )
    {
        if ( TextUtils.isEmpty( data ) )
        {
            return null;
        }

        String result = null;
        try
        {
            result = Base64.encodeToString( Base64.encode( Base64.encode( data.getBytes(), Base64.DEFAULT ), Base64.DEFAULT ),
                    Base64.DEFAULT );
        }
        catch ( Exception e )
        {
        }

        return result;
    }

    /**
     * 将JSONObjec对象转换成Map-List集合
     *
     * @param json
     * @return
     */
    public static List< String > toMap( JsonObject json )
    {

        List< String > listKey = new ArrayList< String >();
        Set<Map.Entry< String, JsonElement>> entrySet = json.entrySet();
        Iterator<Map.Entry< String, JsonElement >> iter = entrySet.iterator();
        for ( int i = 0; i < entrySet.size(); i++ )
        {
            Map.Entry< String, JsonElement > entry = iter.next();
            String key = entry.getKey();
            listKey.add( key );
        }
        TraceUtils.e( "Iterator" + listKey.toString() );
        return listKey;
    }


    public String getmCacheDataStr()
    {
        return mCacheDataStr;
    }

    public void setmCacheDataStr( String mCacheDataStr )
    {
        this.mCacheDataStr = mCacheDataStr;
    }


    /**
     * 获取缓存数据
     * */
    public String getCacheData( String AuthId )
    {
        String data;
        if ( mCacheDataMap != null && mCacheDataMap.size() != 0 )
        {
            data = mCacheDataMap.get( AuthId );
            if ( !TextUtils.isEmpty( data ) )
                return data;
        }
        if ( jsonObj == null )
            return null;

        JsonElement element = jsonObj.get( AuthId );
        if ( element == null )
            return null;
        data = element.getAsString();

        return data;
    }

}
